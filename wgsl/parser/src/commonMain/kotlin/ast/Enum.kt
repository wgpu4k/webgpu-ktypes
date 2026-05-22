package io.ygdrasil.wgsl.ast

import io.ygdrasil.wgsl.ir.Span

/**
 * Common interface for all enum value declarations (both user-defined and predeclared).
 * 
 * This interface provides a unified way to access enum value properties regardless
 * of whether the enum is user-defined or predeclared.
 */
sealed interface EnumValue {
    /** The name of this enum value (e.g., "clamp_to_edge", "VALUE1"). */
    val name: String
    
    /** The value of this enum value as a string for predeclared, or expression for user-defined. */
    val value: String?
    
    /** The source span of this enum value. */
    val span: Span
    
    /**
     * Returns the fully qualified name of this enum value.
     */
    fun getQualifiedName(): String
}

/**
 * Extension to make existing EnumMember compatible with EnumValue interface.
 * This allows EnumMember to be treated as an EnumValue without modifying its original definition.
 */
fun EnumMember.toEnumValue(): EnumValue = object : EnumValue {
    override val name: String = this@toEnumValue.name
    override val value: String? = this@toEnumValue.value?.toString()
    override val span: Span = this@toEnumValue.span
    override fun getQualifiedName(): String = name
}

/**
 * Extension to make existing PredeclaredEnumerant compatible with EnumValue interface.
 * This allows PredeclaredEnumerant to be treated as an EnumValue without modifying its original definition.
 */
fun PredeclaredEnumerant.toEnumValue(): EnumValue = object : EnumValue {
    override val name: String = this@toEnumValue.value
    override val value: String? = this@toEnumValue.value
    override val span: Span = this@toEnumValue.span
    override fun getQualifiedName(): String = "${this@toEnumValue.category}.${this@toEnumValue.value}"
}

/**
 * Base class for all expressions that reference enum values.
 * 
 * This provides a common interface for both user-defined enum member references
 * and predeclared enumerant references.
 */
sealed class EnumValueExpr : Expression() {
    /** The underlying enum value being referenced. */
    abstract val enumValue: EnumValue
    
    /**
     * Returns the type name of this enum value.
     * For user-defined enums: the enum declaration name
     * For predeclared enumerants: the category name
     */
    abstract fun getEnumTypeName(): String
    
    /**
     * Returns the qualified name of this enum value reference.
     */
    fun getQualifiedName(): String = enumValue.getQualifiedName()
}

/**
 * Reference to a user-defined enum member.
 * 
 * This is created when accessing a member of a user-defined enum type.
 * For example, in `MyEnum.VALUE`, this represents the `MyEnum.VALUE` reference.
 */
data class EnumMemberExpr(
    /** The enum declaration. */
    val enumDecl: EnumDecl,
    /** The member name being accessed. */
    val memberName: String,
    override val span: Span,
) : EnumValueExpr() {
    override val enumValue: EnumValue by lazy {
        enumDecl.members.find { it.name == memberName }
            ?.toEnumValue()
            ?: throw IllegalStateException("Member $memberName not found in enum ${enumDecl.name}")
    }
    
    override fun getEnumTypeName(): String = enumDecl.name
}

/**
 * Resolves a MemberAccessExpr to an appropriate EnumValueExpr if it references an enum.
 * 
 * This function checks if the member access is accessing a user-defined enum member
 * or a predeclared enumerant, and returns the appropriate expression type.
 * 
 * @param expr The MemberAccessExpr to resolve
 * @param translationUnit The translation unit containing all declarations
 * @return An EnumValueExpr (either EnumMemberExpr or PredeclaredEnumerantExpr) if this
 *         is an enum access, otherwise returns the original MemberAccessExpr
 */
fun MemberAccessExpr.tryResolveToEnumExpr(translationUnit: TranslationUnit): Expression {
    // Check if the object is an identifier referencing a user-defined enum
    if (objectExpr is IdentExpr) {
        val enumName = objectExpr.identifier
        
        // Check if there's a user-defined enum with this name
        val enumDecl = translationUnit.declarations
            .filterIsInstance<EnumDecl>()
            .find { it.name == enumName }
        
        if (enumDecl != null) {
            // This is a user-defined enum member access
            return EnumMemberExpr(enumDecl, member, span)
        }
    }
    
    // Not an enum access, keep as MemberAccessExpr
    return this
}

/**
 * Base class for expression transformers.
 * 
 * Subclasses can override specific transform methods to modify expressions.
 * The default implementation returns the expression unchanged.
 */
abstract class ExpressionTransformer {
    
    /**
     * Transform a translation unit by transforming all expressions within it.
     */
    fun transform(unit: TranslationUnit): TranslationUnit {
        val declarations = unit.declarations.map { transformDeclaration(it) }
        return TranslationUnit(declarations, unit.span)
    }
    
    private fun transformDeclaration(decl: GlobalDecl): GlobalDecl {
        return when (decl) {
            is VariableDeclStatement -> {
                val initializer = decl.initializer?.let { transform(it) }
                decl.copy(initializer = initializer)
            }
            is FunctionDecl -> {
                val body = decl.body?.let { transformStatement(it) }
                decl.copy(body = body)
            }
            is StructDecl -> {
                val members = decl.members.map { member ->
                    val defaultValue = member.defaultValue?.let { transform(it) }
                    member.copy(defaultValue = defaultValue)
                }
                decl.copy(members = members)
            }
            is EnumDecl -> decl
            is TypeDecl -> decl
            is TypeAliasDecl -> decl
            is OverrideDecl -> decl
            is ConstAssertDecl -> decl
            is DiagnosticDirective -> decl
            is EnableDirective -> decl
            is RequiresDirective -> decl
            else -> decl
        }
    }
    
    private fun transformStatement(stmt: Statement): Statement {
        return when (stmt) {
            is ExpressionStatement -> {
                val expr = transform(stmt.expression)
                stmt.copy(expression = expr)
            }
            is VariableDeclStatement -> {
                val initializer = stmt.initializer?.let { transform(it) }
                stmt.copy(initializer = initializer)
            }
            is BlockStatement -> {
                val statements = stmt.statements.map { transformStatement(it) }
                stmt.copy(statements = statements)
            }
            is IfStatement -> {
                val condition = transform(stmt.condition)
                val thenBody = stmt.thenBody?.let { transformStatement(it) }
                val elseBody = stmt.elseBody?.let { transformStatement(it) }
                stmt.copy(condition = condition, thenBody = thenBody, elseBody = elseBody)
            }
            is ReturnStatement -> {
                val expression = stmt.expression?.let { transform(it) }
                stmt.copy(expression = expression)
            }
            is AssignmentStatement -> {
                val lhs = transform(stmt.lhs)
                val rhs = transform(stmt.rhs)
                stmt.copy(lhs = lhs, rhs = rhs)
            }
            is ForStatement -> {
                val init = stmt.init?.let { transformStatement(it) }
                val condition = stmt.condition?.let { transform(it) }
                val update = stmt.update?.let { transformStatement(it) }
                val body = stmt.body?.let { transformStatement(it) }
                stmt.copy(init = init, condition = condition, update = update, body = body)
            }
            is WhileStatement -> {
                val condition = transform(stmt.condition)
                val body = stmt.body?.let { transformStatement(it) }
                stmt.copy(condition = condition, body = body)
            }
            is DiscardStatement -> stmt
            is BreakStatement -> stmt
            is ContinueStatement -> stmt
            is BreakIfStatement -> {
                val condition = transform(stmt.condition)
                stmt.copy(condition = condition)
            }
            is EmptyStatement -> stmt
            is ConstAssertStatement -> stmt
            is PhonyAssignmentStatement -> stmt
            is IncDecStatement -> {
                val expression = transform(stmt.expression)
                stmt.copy(expression = expression)
            }
            else -> stmt
        }
    }
    
    /**
     * Transform an expression.
     * Override this method in subclasses to handle specific expression types.
     */
    open fun transform(expr: Expression): Expression {
        return when (expr) {
            is MemberAccessExpr -> transform(expr)
            is BinaryExpr -> {
                val left = transform(expr.left)
                val right = transform(expr.right)
                expr.copy(left = left, right = right)
            }
            is UnaryExpr -> {
                val expression = transform(expr.expression)
                expr.copy(expression = expression)
            }
            is CallExpr -> {
                val arguments = expr.arguments.map { transform(it) }
                val callee = transform(expr.callee)
                expr.copy(callee = callee, arguments = arguments)
            }
            is IndexExpr -> {
                val expression = transform(expr.expression)
                val index = transform(expr.index)
                expr.copy(expression = expression, index = index)
            }
            is TernaryExpr -> {
                val condition = transform(expr.condition)
                val trueExpr = transform(expr.trueExpr)
                val falseExpr = transform(expr.falseExpr)
                expr.copy(condition = condition, trueExpr = trueExpr, falseExpr = falseExpr)
            }
            is BitcastExpr -> {
                val expression = transform(expr.expression)
                expr.copy(expression = expression)
            }
            is TypeCastExpr -> {
                val expression = transform(expr.expression)
                expr.copy(expression = expression)
            }
            is SwizzleExpr -> {
                val expression = transform(expr.expression)
                expr.copy(expression = expression)
            }
            is IdentExpr -> expr
            is IntLiteral -> expr
            is FloatLiteral -> expr
            is BoolLiteral -> expr
            is StringLiteral -> expr
            is PredeclaredEnumerantExpr -> expr
            is EnumMemberExpr -> expr
            else -> expr
        }
    }
    
    /**
     * Transform a MemberAccessExpr.
     * Override this to handle member access resolution (e.g., to EnumMemberExpr).
     */
    open fun transform(expr: MemberAccessExpr): Expression = expr
}
