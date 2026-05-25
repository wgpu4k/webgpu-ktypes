package io.ygdrasil.wgsl.parser

import io.ygdrasil.wgsl.ast.AbstractFloatType
import io.ygdrasil.wgsl.ast.AbstractIntType
import io.ygdrasil.wgsl.ast.ArrayType
import io.ygdrasil.wgsl.ast.PredeclaredEnumerantExpr
import io.ygdrasil.wgsl.ast.EnumMemberExpr
import io.ygdrasil.wgsl.ast.AssignmentStatement
import io.ygdrasil.wgsl.ast.AtomicType
import io.ygdrasil.wgsl.ast.Attribute
import io.ygdrasil.wgsl.ast.BinaryExpr
import io.ygdrasil.wgsl.ast.BitcastExpr
import io.ygdrasil.wgsl.ast.BlockStatement
import io.ygdrasil.wgsl.ast.BoolLiteral
import io.ygdrasil.wgsl.ast.BreakIfStatement
import io.ygdrasil.wgsl.ast.BreakStatement
import io.ygdrasil.wgsl.ast.CallExpr
import io.ygdrasil.wgsl.ast.Case
import io.ygdrasil.wgsl.ast.ConstAssertDecl
import io.ygdrasil.wgsl.ast.ConstAssertStatement
import io.ygdrasil.wgsl.ast.ConstantType
import io.ygdrasil.wgsl.ast.ContinueStatement
import io.ygdrasil.wgsl.ast.DefaultCase
import io.ygdrasil.wgsl.ast.DiagnosticDirective
import io.ygdrasil.wgsl.ast.DiagnosticStatement
import io.ygdrasil.wgsl.ast.DiscardStatement
import io.ygdrasil.wgsl.ast.EmptyStatement
import io.ygdrasil.wgsl.ast.EnableDirective
import io.ygdrasil.wgsl.ast.EnumDecl
import io.ygdrasil.wgsl.ast.EnumMember
import io.ygdrasil.wgsl.ast.EnumType
import io.ygdrasil.wgsl.ast.Expression
import io.ygdrasil.wgsl.ast.ExpressionStatement
import io.ygdrasil.wgsl.ast.FloatLiteral
import io.ygdrasil.wgsl.ast.ForStatement
import io.ygdrasil.wgsl.ast.FunctionDecl
import io.ygdrasil.wgsl.ast.GlobalDecl
import io.ygdrasil.wgsl.ast.IdentExpr
import io.ygdrasil.wgsl.ast.IfStatement
import io.ygdrasil.wgsl.ast.IncDecStatement
import io.ygdrasil.wgsl.ast.IndexExpr
import io.ygdrasil.wgsl.ast.IntLiteral
import io.ygdrasil.wgsl.ast.LoopStatement
import io.ygdrasil.wgsl.ast.MatrixType
import io.ygdrasil.wgsl.ast.MemberAccessExpr
import io.ygdrasil.wgsl.ast.NamedType
import io.ygdrasil.wgsl.ast.OverrideDecl
import io.ygdrasil.wgsl.ast.Param
import io.ygdrasil.wgsl.ast.PhonyAssignmentStatement
import io.ygdrasil.wgsl.ast.PointerType
import io.ygdrasil.wgsl.ast.RayQueryType
import io.ygdrasil.wgsl.ast.ReferenceType
import io.ygdrasil.wgsl.ast.RequiresDirective
import io.ygdrasil.wgsl.ast.ReturnStatement
import io.ygdrasil.wgsl.ast.SamplerType
import io.ygdrasil.wgsl.ast.ScalarKind
import io.ygdrasil.wgsl.ast.ScalarType
import io.ygdrasil.wgsl.ast.isFloat
import io.ygdrasil.wgsl.ast.isInteger
import io.ygdrasil.wgsl.ast.Statement
import io.ygdrasil.wgsl.ast.StringLiteral
import io.ygdrasil.wgsl.ast.StructDecl
import io.ygdrasil.wgsl.ast.StructMember
import io.ygdrasil.wgsl.ast.StructType
import io.ygdrasil.wgsl.ast.SwitchBody
import io.ygdrasil.wgsl.ast.SwitchStatement
import io.ygdrasil.wgsl.ast.SwizzleExpr
import io.ygdrasil.wgsl.ast.TemplateType
import io.ygdrasil.wgsl.ast.TernaryExpr
import io.ygdrasil.wgsl.ast.TextureType
import io.ygdrasil.wgsl.ast.TranslationUnit
import io.ygdrasil.wgsl.ast.TypeAliasDecl
import io.ygdrasil.wgsl.ast.TypeCastExpr
import io.ygdrasil.wgsl.ast.TypeDecl
import io.ygdrasil.wgsl.ast.UnaryExpr
import io.ygdrasil.wgsl.ast.VariableDecl
import io.ygdrasil.wgsl.ast.VariableDeclStatement
import io.ygdrasil.wgsl.ast.VectorType
import io.ygdrasil.wgsl.ast.WhileStatement
import io.ygdrasil.wgsl.ir.Span

/**
 * Resolves type references and identifier references in a WGSL AST.
 * 
 * This class takes a parsed AST and resolves:
 * - NamedType references to their actual TypeDecl
 * - IdentExpr references to their actual VariableDecl/FunctionDecl/Param
 * - Template type instantiations
 * 
 * It produces a resolved AST where all references are concrete.
 */
class TypeResolver(
    private val typeIndex: TypeIndex = TypeIndex(),
    private val moduleIndexer: ModuleIndexer = ModuleIndexer()
) {
    private val localScopes = mutableListOf<MutableSet<String>>()

    private fun pushScope() {
        localScopes.add(mutableSetOf())
    }

    private fun popScope() {
        if (localScopes.isNotEmpty()) {
            localScopes.removeAt(localScopes.size - 1)
        }
    }

    private fun declareLocal(name: String) {
        if (localScopes.isNotEmpty()) {
            localScopes.last().add(name)
        }
    }

    private fun isKnownLocal(name: String): Boolean {
        return localScopes.any { it.contains(name) }
    }

    /**
     * Results of type resolution.
     */
    data class ResolutionResult(
        /** The resolved translation unit (with forward references resolved). */
        val resolvedUnit: TranslationUnit,
        /** List of unresolved references (errors). */
        val unresolvedReferences: List<UnresolvedReferenceError>,
        /** Whether resolution was successful (no unresolved references). */
        val isSuccess: Boolean
    ) {
        companion object {
            fun success(unit: TranslationUnit): ResolutionResult {
                return ResolutionResult(unit, emptyList(), true)
            }

            fun failure(errors: List<UnresolvedReferenceError>): ResolutionResult {
                return ResolutionResult(TranslationUnit.empty(), errors, false)
            }
        }
    }

    /**
     * Error for unresolved references.
     */
    data class UnresolvedReferenceError(
        val name: String,
        val kind: ReferenceKind,
        val span: Span,
        val message: String
    ) {
        enum class ReferenceKind {
            TYPE, VALUE, FUNCTION
        }
    }

    // ========== Main Resolution Methods ==========

    /**
     * Resolve all references in a translation unit.
     * 
     * This performs:
     * 1. Indexing of all declarations
     * 2. Topological sorting to handle forward references
     * 3. Type resolution for all NamedType references
     * 4. Identifier resolution for all IdentExpr references
     * 
     * @param unit The translation unit to resolve
     * @return ResolutionResult with resolved unit and any errors
     */
    fun resolve(unit: TranslationUnit): ResolutionResult {
        localScopes.clear()
        
        // First, index all declarations
        typeIndex.index(unit)

        // Build the set of all declared names
        val allNames = typeIndex.getAllDeclaredNames()

        // Reorder declarations to handle forward references
        val reorderedUnit = try {
            moduleIndexer.reorderDeclarations(unit)
        } catch (e: CycleDetectedException) {
            return ResolutionResult.failure(
                listOf(
                    UnresolvedReferenceError(
                        name = "cycle",
                        kind = UnresolvedReferenceError.ReferenceKind.VALUE,
                        span = Span.UNDEFINED,
                        message = e.message ?: "Cycle detected in dependencies"
                    )
                )
            )
        }

        // Now resolve all references in the reordered unit
        val unresolved = mutableListOf<UnresolvedReferenceError>()
        val resolvedDeclarations = mutableListOf<GlobalDecl>()

        for (decl in reorderedUnit.declarations) {
            val resolvedDecl = resolveDeclaration(decl, unresolved)
            resolvedDeclarations.add(resolvedDecl)
        }

        val resolvedUnit = TranslationUnit(resolvedDeclarations, reorderedUnit.span)

        return if (unresolved.isEmpty()) {
            ResolutionResult.success(resolvedUnit)
        } else {
            ResolutionResult(resolvedUnit, unresolved, false)
        }
    }

    /**
     * Resolve a single declaration and its children.
     */
    private fun resolveDeclaration(
        decl: GlobalDecl,
        unresolved: MutableList<UnresolvedReferenceError>
    ): GlobalDecl {
        return when (decl) {
            is FunctionDecl -> resolveFunctionDecl(decl, unresolved)
            is StructDecl -> resolveStructDecl(decl, unresolved)
            is VariableDecl -> resolveVariableDecl(decl, unresolved)
            is TypeAliasDecl -> resolveTypeAliasDecl(decl, unresolved)
            is OverrideDecl -> resolveOverrideDecl(decl, unresolved)
            is ConstAssertDecl -> resolveConstAssertDecl(decl, unresolved)
            is EnumDecl -> resolveEnumDecl(decl, unresolved)
            is EnableDirective, is RequiresDirective, is DiagnosticDirective -> decl
        }
    }

    // ========== Declaration Resolution ==========

    private fun resolveFunctionDecl(
        decl: FunctionDecl,
        unresolved: MutableList<UnresolvedReferenceError>
    ): FunctionDecl {
        val resolvedAttributes = decl.attributes.map { resolveAttribute(it, unresolved) }
        
        pushScope()
        val resolvedParams = decl.parameters.map { 
            val resolved = resolveParam(it, unresolved)
            declareLocal(resolved.name)
            resolved
        }
        
        val resolvedReturnAttributes = decl.returnAttributes.map { resolveAttribute(it, unresolved) }
        val resolvedReturnType = decl.returnType?.let { resolveTypeDecl(it, unresolved) }
        val resolvedBody = decl.body?.let { resolveBlockStatement(it, unresolved) }
        popScope()

        return decl.copy(
            attributes = resolvedAttributes,
            parameters = resolvedParams,
            returnAttributes = resolvedReturnAttributes,
            returnType = resolvedReturnType,
            body = resolvedBody
        )
    }

    private fun resolveStructDecl(
        decl: StructDecl,
        unresolved: MutableList<UnresolvedReferenceError>
    ): StructDecl {
        val resolvedMembers = decl.members.map { resolveStructMember(it, unresolved) }
        return decl.copy(members = resolvedMembers)
    }

    private fun resolveVariableDecl(
        decl: VariableDecl,
        unresolved: MutableList<UnresolvedReferenceError>
    ): VariableDecl {
        val resolvedType = decl.type?.let { resolveTypeDecl(it, unresolved) }
        val resolvedInitializer = decl.initializer?.let { resolveExpression(it, unresolved) }
        return decl.copy(type = resolvedType, initializer = resolvedInitializer)
    }

    private fun resolveTypeAliasDecl(
        decl: TypeAliasDecl,
        unresolved: MutableList<UnresolvedReferenceError>
    ): TypeAliasDecl {
        val resolvedType = resolveTypeDecl(decl.type, unresolved)
        return decl.copy(type = resolvedType)
    }

    private fun resolveOverrideDecl(
        decl: OverrideDecl,
        unresolved: MutableList<UnresolvedReferenceError>
    ): OverrideDecl {
        val resolvedType = decl.type?.let { resolveTypeDecl(it, unresolved) }
        val resolvedInitializer = decl.initializer?.let { resolveExpression(it, unresolved) }
        return decl.copy(type = resolvedType, initializer = resolvedInitializer)
    }

    private fun resolveConstAssertDecl(
        decl: ConstAssertDecl,
        unresolved: MutableList<UnresolvedReferenceError>
    ): ConstAssertDecl {
        val resolvedExpr = resolveExpression(decl.expression, unresolved)
        return decl.copy(expression = resolvedExpr)
    }

    private fun resolveEnumDecl(
        decl: EnumDecl,
        unresolved: MutableList<UnresolvedReferenceError>
    ): EnumDecl {
        val resolvedAttributes = decl.attributes.map { resolveAttribute(it, unresolved) }
        val resolvedMembers = decl.members.map { member ->
            val memberAttrs = member.attributes.map { resolveAttribute(it, unresolved) }
            val memberValue = member.value?.let { resolveExpression(it, unresolved) }
            member.copy(attributes = memberAttrs, value = memberValue)
        }
        return decl.copy(attributes = resolvedAttributes, members = resolvedMembers)
    }

    // ========== Type Resolution ==========

    /**
     * Resolve a type declaration, replacing NamedType references with concrete types.
     */
    fun resolveTypeDecl(
        type: TypeDecl,
        unresolved: MutableList<UnresolvedReferenceError>
    ): TypeDecl {
        return when (type) {
            is ScalarType -> type
            is VectorType -> {
                val resolvedElement = resolveTypeDecl(type.elementType, unresolved)
                type.copy(elementType = resolvedElement)
            }

            is MatrixType -> {
                val resolvedElement = resolveTypeDecl(type.elementType, unresolved)
                type.copy(elementType = resolvedElement)
            }

            is ArrayType -> {
                val resolvedElement = resolveTypeDecl(type.elementType, unresolved)
                val resolvedLength = type.length?.let { resolveExpression(it, unresolved) }
                type.copy(elementType = resolvedElement, length = resolvedLength)
            }

            is StructType -> {
                // Try to resolve struct reference
                val structDecl = typeIndex.findStruct(type.name)
                if (structDecl != null) {
                    // Return a reference to the actual struct (kept as StructType)
                    type
                } else if (typeIndex.isBuiltinScalarType(type.name)) {
                    // This is actually a scalar type misrepresented as StructType
                    typeIndex.getBuiltinScalarType(type.name) ?: type
                } else {
                    unresolved.add(
                        UnresolvedReferenceError(
                            name = type.name,
                            kind = UnresolvedReferenceError.ReferenceKind.TYPE,
                            span = type.span,
                            message = "Unknown struct type: ${type.name}"
                        )
                    )
                    type
                }
            }

            is NamedType -> {
                resolveNamedType(type, unresolved)
            }

            is PointerType -> {
                val resolvedElement = resolveTypeDecl(type.elementType, unresolved)
                type.copy(elementType = resolvedElement)
            }

            is ReferenceType -> {
                val resolvedElement = resolveTypeDecl(type.elementType, unresolved)
                type.copy(elementType = resolvedElement)
            }

            is TemplateType -> {
                // For template types, we need to substitute the template parameters
                // For now, we'll keep them as-is since full template resolution is complex
                val resolvedArgs = type.args.map { resolveTypeDecl(it, unresolved) }
                type.copy(args = resolvedArgs)
            }

            is AtomicType -> {
                val resolvedElement = resolveTypeDecl(type.elementType, unresolved)
                type.copy(elementType = resolvedElement)
            }

            is SamplerType -> type
            is TextureType -> {
                val resolvedElement = type.elementType?.let { resolveTypeDecl(it, unresolved) }
                type.copy(elementType = resolvedElement)
            }

            is ConstantType -> type
            is RayQueryType -> type
            is EnumType -> type
            is AbstractIntType -> type
            is AbstractFloatType -> type
        }
    }

    /**
     * Resolve a NamedType to its concrete type.
     */
    private fun resolveNamedType(
        type: NamedType,
        unresolved: MutableList<UnresolvedReferenceError>
    ): TypeDecl {
        val name = type.name

        // Check if it's a built-in texture format
        val textureFormats = setOf(
            "r8unorm", "r8snorm", "r8uint", "r8sint",
            "r16uint", "r16sint", "r16float",
            "rg8unorm", "rg8snorm", "rg8uint", "rg8sint",
            "r32uint", "r32sint", "r32float",
            "rg16uint", "rg16sint", "rg16float",
            "rgba8unorm", "rgba8unorm-srgb", "rgba8snorm", "rgba8uint", "rgba8sint",
            "bgra8unorm", "bgra8unorm-srgb",
            "rgb9e5ufloat", "rg11b10ufloat",
            "rg32uint", "rg32sint", "rg32float",
            "rgba16uint", "rgba16sint", "rgba16float",
            "rgba32uint", "rgba32sint", "rgba32float",
            "r64uint", "r64sint"
        )
        if (name in textureFormats) {
            return type
        }

        if (name == "acceleration_structure" || name == "ray_query" || name == "RayDesc" || name == "RayIntersection" ||
            name == "coop_mat8x8" || name == "A" || name == "B" || name == "C") {
            return type
        }

        // Check if it's a built-in scalar type
        if (typeIndex.isBuiltinScalarType(name)) {
            return typeIndex.getBuiltinScalarType(name) ?: type
        }

        // Check if it's a built-in vector type (vec2, vec3, vec4)
        if (typeIndex.isBuiltinVectorType(name)) {
            val parsed = typeIndex.parseBuiltinVectorType(name)
            if (parsed != null) {
                val (size, elementTypeName) = parsed
                val elementType = if (typeIndex.isBuiltinScalarType(elementTypeName)) {
                    typeIndex.getBuiltinScalarType(elementTypeName)!!
                } else {
                    unresolved.add(
                        UnresolvedReferenceError(
                            name = elementTypeName,
                            kind = UnresolvedReferenceError.ReferenceKind.TYPE,
                            span = type.span,
                            message = "Unknown element type in vector: $elementTypeName"
                        )
                    )
                    ScalarType(ScalarKind.F32, type.span)
                }
                return VectorType(size, elementType, type.span)
            }
        }

        // Check if it's a built-in matrix type (matCxR)
        if (typeIndex.isBuiltinMatrixType(name)) {
            val parsed = typeIndex.parseBuiltinMatrixType(name)
            if (parsed != null) {
                val (cols, rows, elementTypeName) = parsed
                val elementType = if (typeIndex.isBuiltinScalarType(elementTypeName)) {
                    typeIndex.getBuiltinScalarType(elementTypeName)!!
                } else {
                    unresolved.add(
                        UnresolvedReferenceError(
                            name = elementTypeName,
                            kind = UnresolvedReferenceError.ReferenceKind.TYPE,
                            span = type.span,
                            message = "Unknown element type in matrix: $elementTypeName"
                        )
                    )
                    ScalarType(ScalarKind.F32, type.span)
                }
                return MatrixType(cols, rows, elementType, type.span)
            }
        }

        // Check if it's a user-defined type alias
        val typeAlias = typeIndex.findTypeAlias(name)
        if (typeAlias != null) {
            // Return a copy of the aliased type (already resolved during indexing)
            return resolveTypeDecl(typeAlias.type, unresolved)
        }

        // Check if it's a user-defined struct
        val structDecl = typeIndex.findStruct(name)
        if (structDecl != null) {
            return StructType(name, type.span)
        }

        // Check if it's a user-defined enum
        val enumDecl = typeIndex.findEnum(name)
        if (enumDecl != null) {
            return EnumType(name, type.span)
        }

        // Unknown type
        unresolved.add(
            UnresolvedReferenceError(
                name = name,
                kind = UnresolvedReferenceError.ReferenceKind.TYPE,
                span = type.span,
                message = "Unknown type: $name"
            )
        )
        return type
    }

    // ========== Expression Resolution ==========

    /**
     * Resolve an expression, replacing IdentExpr references with resolved identifiers.
     */
    fun resolveExpression(
        expr: Expression,
        unresolved: MutableList<UnresolvedReferenceError>
    ): Expression {
        return when (expr) {
            is IntLiteral -> expr
            is FloatLiteral -> expr
            is BoolLiteral -> expr
            is StringLiteral -> expr
            is IdentExpr -> resolveIdentExpr(expr, unresolved)
            is CallExpr -> resolveCallExpr(expr, unresolved)
            is MemberAccessExpr -> {
                val resolvedObject = resolveExpression(expr.objectExpr, unresolved)
                expr.copy(objectExpr = resolvedObject)
            }

            is IndexExpr -> {
                val resolvedObject = resolveExpression(expr.objectExpr, unresolved)
                val resolvedIndex = resolveExpression(expr.index, unresolved)
                expr.copy(objectExpr = resolvedObject, index = resolvedIndex)
            }

            is UnaryExpr -> {
                val resolvedOperand = resolveExpression(expr.operand, unresolved)
                expr.copy(operand = resolvedOperand)
            }

            is BinaryExpr -> {
                val resolvedLeft = resolveExpression(expr.left, unresolved)
                val resolvedRight = resolveExpression(expr.right, unresolved)
                expr.copy(left = resolvedLeft, right = resolvedRight)
            }

            is TernaryExpr -> {
                val resolvedCondition = resolveExpression(expr.condition, unresolved)
                val resolvedTrue = resolveExpression(expr.trueExpr, unresolved)
                val resolvedFalse = resolveExpression(expr.falseExpr, unresolved)
                expr.copy(
                    condition = resolvedCondition,
                    trueExpr = resolvedTrue,
                    falseExpr = resolvedFalse
                )
            }

            is TypeCastExpr -> {
                val resolvedExpr = resolveExpression(expr.expr, unresolved)
                val resolvedType = resolveTypeDecl(expr.type, unresolved)
                expr.copy(expr = resolvedExpr, type = resolvedType)
            }

            is SwizzleExpr -> {
                val resolvedObject = resolveExpression(expr.objectExpr, unresolved)
                expr.copy(objectExpr = resolvedObject)
            }

            is BitcastExpr -> {
                val resolvedExpr = resolveExpression(expr.expr, unresolved)
                val resolvedType = resolveTypeDecl(expr.type, unresolved)
                expr.copy(expr = resolvedExpr, type = resolvedType)
            }

            is PredeclaredEnumerantExpr -> expr
            is EnumMemberExpr -> {
                val memberExists = expr.enumDecl.members.any { it.name == expr.memberName }
                if (!memberExists) {
                    unresolved.add(
                        UnresolvedReferenceError(
                            name = expr.memberName,
                            kind = UnresolvedReferenceError.ReferenceKind.VALUE,
                            span = expr.span,
                            message = "Unknown enum member: ${expr.memberName} in enum ${expr.enumDecl.name}"
                        )
                    )
                }
                expr
            }
        }
    }

    /**
     * Resolve an identifier expression to its actual reference.
     */
    private fun resolveIdentExpr(
        expr: IdentExpr,
        unresolved: MutableList<UnresolvedReferenceError>
    ): Expression {
        val name = expr.name

        // Check if it's a known local (parameter or local variable)
        if (isKnownLocal(name)) {
            return expr
        }

        // Check if it's a built-in value
        if (typeIndex.isBuiltinValue(name)) {
            return when (name) {
                "true" -> BoolLiteral(true, expr.span)
                "false" -> BoolLiteral(false, expr.span)
                else -> expr
            }
        }

        // Check if it's a global constant
        val globalConst = typeIndex.findGlobalConstant(name)
        if (globalConst != null) {
            return expr // Keep as IdentExpr but it's resolved
        }

        // Check if it's a global variable
        val globalVar = typeIndex.findGlobalVariable(name)
        if (globalVar != null) {
            return expr // Keep as IdentExpr but it's resolved
        }

        // Check if it's a function
        val function = typeIndex.findFunction(name)
        if (function != null) {
            return expr // Keep as IdentExpr but it's resolved
        }

        // Check if it's a user-defined struct
        val struct = typeIndex.findStruct(name)
        if (struct != null) {
            return expr // Keep as IdentExpr but it's resolved
        }

        // Check if it's a user-defined type alias
        val typeAlias = typeIndex.findTypeAlias(name)
        if (typeAlias != null) {
            return expr // Keep as IdentExpr but it's resolved
        }

        // Unknown identifier
        unresolved.add(
            UnresolvedReferenceError(
                name = name,
                kind = UnresolvedReferenceError.ReferenceKind.VALUE,
                span = expr.span,
                message = "Unknown identifier: $name"
            )
        )
        return expr
    }

    /**
     * Resolve a call expression.
     */
    private fun resolveCallExpr(
        expr: CallExpr,
        unresolved: MutableList<UnresolvedReferenceError>
    ): CallExpr {
        val resolvedCallee = resolveExpression(expr.callee, unresolved)
        val resolvedArgs = expr.args.map { resolveExpression(it, unresolved) }
        val resolvedTemplateArgs = expr.templateArgs?.map { resolveTypeDecl(it, unresolved) }
        return expr.copy(
            callee = resolvedCallee,
            args = resolvedArgs,
            templateArgs = resolvedTemplateArgs
        )
    }

    // ========== Other Resolution Methods ==========

    private fun resolveParam(
        param: Param,
        unresolved: MutableList<UnresolvedReferenceError>
    ): Param {
        val resolvedAttributes = param.attributes.map { resolveAttribute(it, unresolved) }
        val resolvedType = resolveTypeDecl(param.type, unresolved)
        val resolvedDefault = param.defaultValue?.let { resolveExpression(it, unresolved) }
        return param.copy(
            attributes = resolvedAttributes,
            type = resolvedType,
            defaultValue = resolvedDefault
        )
    }

    private fun resolveStructMember(
        member: StructMember,
        unresolved: MutableList<UnresolvedReferenceError>
    ): StructMember {
        val resolvedAttributes = member.attributes.map { resolveAttribute(it, unresolved) }
        val resolvedType = resolveTypeDecl(member.type, unresolved)
        val resolvedDefault = member.defaultValue?.let { resolveExpression(it, unresolved) }
        return member.copy(
            attributes = resolvedAttributes,
            type = resolvedType,
            defaultValue = resolvedDefault
        )
    }

    private fun resolveAttribute(
        attr: Attribute,
        unresolved: MutableList<UnresolvedReferenceError>
    ): Attribute {
        val resolvedArgs = attr.args.map { resolveExpression(it, unresolved) }
        return attr.copy(args = resolvedArgs)
    }

    private fun resolveBlockStatement(
        block: BlockStatement,
        unresolved: MutableList<UnresolvedReferenceError>
    ): BlockStatement {
        pushScope()
        val resolvedBlock = resolveBlockStatementInCurrentScope(block, unresolved)
        popScope()
        return resolvedBlock
    }

    private fun resolveBlockStatementInCurrentScope(
        block: BlockStatement,
        unresolved: MutableList<UnresolvedReferenceError>
    ): BlockStatement {
        val resolvedStatements = block.statements.map { resolveStatement(it, unresolved) }
        return block.copy(statements = resolvedStatements)
    }

    private fun resolveStatement(
        stmt: Statement,
        unresolved: MutableList<UnresolvedReferenceError>
    ): Statement {
        return when (stmt) {
            is BlockStatement -> resolveBlockStatement(stmt, unresolved)
            is IfStatement -> {
                val resolvedCondition = resolveExpression(stmt.condition, unresolved)
                val resolvedThen = resolveStatement(stmt.thenBranch, unresolved)
                val resolvedElse = stmt.elseBranch?.let { resolveStatement(it, unresolved) }
                stmt.copy(
                    condition = resolvedCondition,
                    thenBranch = resolvedThen,
                    elseBranch = resolvedElse
                )
            }

            is SwitchStatement -> {
                val resolvedExpr = resolveExpression(stmt.expression, unresolved)
                val resolvedBody = resolveSwitchBody(stmt.body, unresolved)
                stmt.copy(expression = resolvedExpr, body = resolvedBody)
            }

            is LoopStatement -> {
                pushScope()
                val resolvedBody = resolveBlockStatementInCurrentScope(stmt.body, unresolved)
                val resolvedContinuing = stmt.continuing?.let { resolveBlockStatementInCurrentScope(it, unresolved) }
                popScope()
                stmt.copy(body = resolvedBody, continuing = resolvedContinuing)
            }

            is WhileStatement -> {
                val resolvedCondition = resolveExpression(stmt.condition, unresolved)
                val resolvedBody = resolveBlockStatement(stmt.body, unresolved)
                val resolvedContinuing = stmt.continuing?.let { resolveBlockStatement(it, unresolved) }
                stmt.copy(
                    condition = resolvedCondition,
                    body = resolvedBody,
                    continuing = resolvedContinuing
                )
            }

            is ForStatement -> {
                val resolvedInit = stmt.init?.let { resolveStatement(it, unresolved) }
                val resolvedCondition = stmt.condition?.let { resolveExpression(it, unresolved) }
                val resolvedUpdate = stmt.update?.let { resolveStatement(it, unresolved) }
                val resolvedBody = resolveBlockStatement(stmt.body, unresolved)
                stmt.copy(
                    init = resolvedInit,
                    condition = resolvedCondition,
                    update = resolvedUpdate,
                    body = resolvedBody
                )
            }

            is BreakStatement -> stmt
            is ContinueStatement -> stmt
            is ReturnStatement -> {
                val resolvedValue = stmt.value?.let { resolveExpression(it, unresolved) }
                stmt.copy(value = resolvedValue)
            }

            is DiscardStatement -> stmt
            is VariableDeclStatement -> {
                val resolvedType = stmt.type?.let { resolveTypeDecl(it, unresolved) }
                val resolvedInitializer = stmt.initializer?.let { resolveExpression(it, unresolved) }
                declareLocal(stmt.name)
                stmt.copy(type = resolvedType, initializer = resolvedInitializer)
            }

            is AssignmentStatement -> {
                val resolvedLhs = resolveExpression(stmt.lhs, unresolved)
                val resolvedRhs = resolveExpression(stmt.rhs, unresolved)
                stmt.copy(lhs = resolvedLhs, rhs = resolvedRhs)
            }

            is IncDecStatement -> {
                val resolvedExpr = resolveExpression(stmt.expr, unresolved)
                stmt.copy(expr = resolvedExpr)
            }

            is BreakIfStatement -> {
                val resolvedCondition = resolveExpression(stmt.condition, unresolved)
                stmt.copy(condition = resolvedCondition)
            }

            is PhonyAssignmentStatement -> {
                val resolvedExpr = resolveExpression(stmt.expression, unresolved)
                stmt.copy(expression = resolvedExpr)
            }

            is ExpressionStatement -> {
                val resolvedExpr = resolveExpression(stmt.expr, unresolved)
                stmt.copy(expr = resolvedExpr)
            }

            is ConstAssertStatement -> {
                val resolvedExpr = resolveExpression(stmt.expression, unresolved)
                ConstAssertStatement(resolvedExpr, stmt.span)
            }

            is DiagnosticStatement -> stmt

            is EmptyStatement -> stmt
        }
    }

    private fun resolveSwitchBody(
        body: SwitchBody,
        unresolved: MutableList<UnresolvedReferenceError>
    ): SwitchBody {
        val resolvedCases = body.cases.map { case ->
            when (case) {
                is Case -> {
                    val resolvedSelectors = case.selectors.map { resolveExpression(it, unresolved) }
                    val resolvedBody = resolveBlockStatement(case.body, unresolved)
                    case.copy(selectors = resolvedSelectors, body = resolvedBody)
                }

                is DefaultCase -> {
                    val resolvedBody = resolveBlockStatement(case.body, unresolved)
                    case.copy(body = resolvedBody)
                }
            }
        }
        return body.copy(cases = resolvedCases)
    }

    // ========== Validation ==========

    /**
     * Validate that all references in a resolved translation unit are valid.
     */
    fun validateResolution(unit: TranslationUnit): List<UnresolvedReferenceError> {
        val errors = mutableListOf<UnresolvedReferenceError>()
        validateTranslationUnit(unit, errors)
        return errors
    }

    private fun validateTranslationUnit(
        unit: TranslationUnit,
        errors: MutableList<UnresolvedReferenceError>
    ) {
        for (decl in unit.declarations) {
            validateDeclaration(decl, errors)
        }
    }

    private fun validateDeclaration(
        decl: GlobalDecl,
        errors: MutableList<UnresolvedReferenceError>
    ) {
        when (decl) {
            is FunctionDecl -> validateFunctionDecl(decl, errors)
            is StructDecl -> validateStructDecl(decl, errors)
            is VariableDecl -> validateVariableDecl(decl, errors)
            is TypeAliasDecl -> validateTypeAliasDecl(decl, errors)
            is OverrideDecl -> validateOverrideDecl(decl, errors)
            is ConstAssertDecl -> validateConstAssertDecl(decl, errors)
            is EnumDecl -> validateEnumDecl(decl, errors)
            is EnableDirective, is RequiresDirective, is DiagnosticDirective -> {}
        }
    }

    private fun validateFunctionDecl(
        decl: FunctionDecl,
        errors: MutableList<UnresolvedReferenceError>
    ) {
        for (param in decl.parameters) {
            validateTypeDecl(param.type, errors)
            param.defaultValue?.let { validateExpression(it, errors) }
        }
        decl.returnType?.let { validateTypeDecl(it, errors) }
        decl.body?.let { validateBlockStatement(it, errors) }
    }

    private fun validateStructDecl(
        decl: StructDecl,
        errors: MutableList<UnresolvedReferenceError>
    ) {
        for (member in decl.members) {
            validateTypeDecl(member.type, errors)
            member.defaultValue?.let { validateExpression(it, errors) }
        }
    }

    private fun validateEnumDecl(
        decl: EnumDecl,
        errors: MutableList<UnresolvedReferenceError>
    ) {
        for (member in decl.members) {
            member.value?.let { validateExpression(it, errors) }
        }
    }

    private fun validateVariableDecl(
        decl: VariableDecl,
        errors: MutableList<UnresolvedReferenceError>
    ) {
        decl.type?.let { validateTypeDecl(it, errors) }
        decl.initializer?.let { validateExpression(it, errors) }
    }

    private fun validateTypeAliasDecl(
        decl: TypeAliasDecl,
        errors: MutableList<UnresolvedReferenceError>
    ) {
        validateTypeDecl(decl.type, errors)
    }

    private fun validateOverrideDecl(
        decl: OverrideDecl,
        errors: MutableList<UnresolvedReferenceError>
    ) {
        decl.type?.let { validateTypeDecl(it, errors) }
        decl.initializer?.let { validateExpression(it, errors) }
    }

    private fun validateConstAssertDecl(
        decl: ConstAssertDecl,
        errors: MutableList<UnresolvedReferenceError>
    ) {
        validateExpression(decl.expression, errors)
    }

    private fun validateTypeDecl(
        type: TypeDecl,
        errors: MutableList<UnresolvedReferenceError>
    ) {
        when (type) {
            is ScalarType -> {}
            is VectorType -> validateTypeDecl(type.elementType, errors)
            is MatrixType -> validateTypeDecl(type.elementType, errors)
            is ArrayType -> {
                validateTypeDecl(type.elementType, errors)
                type.length?.let { validateExpression(it, errors) }
            }

            is StructType -> {
                if (!typeIndex.isKnownType(type.name)) {
                    errors.add(
                        UnresolvedReferenceError(
                            name = type.name,
                            kind = UnresolvedReferenceError.ReferenceKind.TYPE,
                            span = type.span,
                            message = "Unresolved struct type: ${type.name}"
                        )
                    )
                }
            }

            is NamedType -> {
                if (!typeIndex.isKnownType(type.name)) {
                    errors.add(
                        UnresolvedReferenceError(
                            name = type.name,
                            kind = UnresolvedReferenceError.ReferenceKind.TYPE,
                            span = type.span,
                            message = "Unresolved type: ${type.name}"
                        )
                    )
                }
            }

            is PointerType -> validateTypeDecl(type.elementType, errors)
            is ReferenceType -> validateTypeDecl(type.elementType, errors)
            is TemplateType -> {
                if (!typeIndex.isDeclared(type.name)) {
                    errors.add(
                        UnresolvedReferenceError(
                            name = type.name,
                            kind = UnresolvedReferenceError.ReferenceKind.TYPE,
                            span = type.span,
                            message = "Unresolved template type: ${type.name}"
                        )
                    )
                }
                for (arg in type.args) {
                    validateTypeDecl(arg, errors)
                }

                // Basic validation for built-in template types
                val argCount = type.args.size
                when {
                    type.name.startsWith("vec") || type.name.startsWith("mat") || type.name == "atomic" -> {
                        if (argCount != 1) {
                            errors.add(
                                UnresolvedReferenceError(
                                    name = type.name,
                                    kind = UnresolvedReferenceError.ReferenceKind.TYPE,
                                    span = type.span,
                                    message = "Template type '${type.name}' expects exactly 1 argument, got $argCount"
                                )
                            )
                        }
                    }
                    type.name == "array" -> {
                        if (argCount !in 1..2) {
                            errors.add(
                                UnresolvedReferenceError(
                                    name = type.name,
                                    kind = UnresolvedReferenceError.ReferenceKind.TYPE,
                                    span = type.span,
                                    message = "Template type 'array' expects 1 or 2 arguments, got $argCount"
                                )
                            )
                        }
                    }
                    type.name == "ptr" -> {
                        if (argCount !in 2..3) {
                            errors.add(
                                UnresolvedReferenceError(
                                    name = type.name,
                                    kind = UnresolvedReferenceError.ReferenceKind.TYPE,
                                    span = type.span,
                                    message = "Template type 'ptr' expects 2 or 3 arguments, got $argCount"
                                )
                            )
                        }
                    }
                }
            }

            is AtomicType -> validateTypeDecl(type.elementType, errors)
            is SamplerType -> {}
            is TextureType -> type.elementType?.let { validateTypeDecl(it, errors) }
            is ConstantType -> validateExpression(type.expression, errors)
            is RayQueryType -> {}
            is EnumType -> {}
            is AbstractIntType -> {}
            is AbstractFloatType -> {}
        }
    }

    private fun validateExpression(
        expr: Expression,
        errors: MutableList<UnresolvedReferenceError>
    ) {
        when (expr) {
            is IntLiteral -> {}
            is FloatLiteral -> {}
            is BoolLiteral -> {}
            is StringLiteral -> {}
            is IdentExpr -> {
                if (!typeIndex.isKnownValue(expr.name) && !typeIndex.isBuiltinValue(expr.name)) {
                    errors.add(
                        UnresolvedReferenceError(
                            name = expr.name,
                            kind = UnresolvedReferenceError.ReferenceKind.VALUE,
                            span = expr.span,
                            message = "Unresolved identifier: ${expr.name}"
                        )
                    )
                }
            }

            is CallExpr -> {
                validateExpression(expr.callee, errors)
                for (arg in expr.args) {
                    validateExpression(arg, errors)
                }
                expr.templateArgs?.forEach { validateTypeDecl(it, errors) }
            }

            is MemberAccessExpr -> {
                validateExpression(expr.objectExpr, errors)
                validateMemberAccess(expr, errors)
            }
            is IndexExpr -> {
                validateExpression(expr.objectExpr, errors)
                validateExpression(expr.index, errors)
            }

            is UnaryExpr -> validateExpression(expr.operand, errors)
            is BinaryExpr -> {
                validateExpression(expr.left, errors)
                validateExpression(expr.right, errors)
            }

            is TernaryExpr -> {
                validateExpression(expr.condition, errors)
                validateExpression(expr.trueExpr, errors)
                validateExpression(expr.falseExpr, errors)
            }

            is TypeCastExpr -> {
                validateExpression(expr.expr, errors)
                validateTypeDecl(expr.type, errors)
            }

            is SwizzleExpr -> validateExpression(expr.objectExpr, errors)
            is BitcastExpr -> {
                validateExpression(expr.expr, errors)
                validateTypeDecl(expr.type, errors)
            }

            is PredeclaredEnumerantExpr -> {}
            is EnumMemberExpr -> {
                val memberExists = expr.enumDecl.members.any { it.name == expr.memberName }
                if (!memberExists) {
                    errors.add(
                        UnresolvedReferenceError(
                            name = expr.memberName,
                            kind = UnresolvedReferenceError.ReferenceKind.VALUE,
                            span = expr.span,
                            message = "Unknown enum member: ${expr.memberName} in enum ${expr.enumDecl.name}"
                        )
                    )
                }
            }
        }
    }

    /**
     * Validate a member access expression (e.g., struct member or enum member).
     */
    private fun validateMemberAccess(
        expr: MemberAccessExpr,
        errors: MutableList<UnresolvedReferenceError>
    ) {
        if (expr.objectExpr is IdentExpr) {
            val typeName = expr.objectExpr.name
            val enumDecl = typeIndex.findEnum(typeName)
            if (enumDecl != null) {
                val memberName = expr.member
                val memberExists = enumDecl.members.any { it.name == memberName }
                if (!memberExists) {
                    errors.add(
                        UnresolvedReferenceError(
                            name = memberName,
                            kind = UnresolvedReferenceError.ReferenceKind.VALUE,
                            span = expr.span,
                            message = "Unknown enum member: $memberName in enum $typeName"
                        )
                    )
                }
            }
        }
    }

    private fun validateBlockStatement(
        block: BlockStatement,
        errors: MutableList<UnresolvedReferenceError>
    ) {
        for (stmt in block.statements) {
            validateStatement(stmt, errors)
        }
    }

    private fun validateStatement(
        stmt: Statement,
        errors: MutableList<UnresolvedReferenceError>
    ) {
        when (stmt) {
            is BlockStatement -> validateBlockStatement(stmt, errors)
            is IfStatement -> {
                validateExpression(stmt.condition, errors)
                validateStatement(stmt.thenBranch, errors)
                stmt.elseBranch?.let { validateStatement(it, errors) }
            }

            is SwitchStatement -> {
                validateExpression(stmt.expression, errors)
                validateSwitchBody(stmt.body, errors)
            }

            is LoopStatement -> {
                validateBlockStatement(stmt.body, errors)
                stmt.continuing?.let { validateBlockStatement(it, errors) }
            }

            is WhileStatement -> {
                validateExpression(stmt.condition, errors)
                validateBlockStatement(stmt.body, errors)
                stmt.continuing?.let { validateBlockStatement(it, errors) }
            }

            is ForStatement -> {
                stmt.init?.let { validateStatement(it, errors) }
                stmt.condition?.let { validateExpression(it, errors) }
                stmt.update?.let { validateStatement(it, errors) }
                validateBlockStatement(stmt.body, errors)
            }

            is BreakStatement -> {}
            is ContinueStatement -> {}
            is ReturnStatement -> stmt.value?.let { validateExpression(it, errors) }
            is DiscardStatement -> {}
            is VariableDeclStatement -> {
                stmt.type?.let { validateTypeDecl(it, errors) }
                stmt.initializer?.let { validateExpression(it, errors) }
            }

            is AssignmentStatement -> {
                validateExpression(stmt.lhs, errors)
                validateExpression(stmt.rhs, errors)
            }

            is IncDecStatement -> validateExpression(stmt.expr, errors)
            is BreakIfStatement -> validateExpression(stmt.condition, errors)
            is PhonyAssignmentStatement -> validateExpression(stmt.expression, errors)
            is ExpressionStatement -> validateExpression(stmt.expr, errors)
            is ConstAssertStatement -> validateExpression(stmt.expression, errors)
            is EmptyStatement -> {}
            is DiagnosticStatement -> {}
        }
    }

    private fun validateSwitchBody(
        body: SwitchBody,
        errors: MutableList<UnresolvedReferenceError>
    ) {
        for (case in body.cases) {
            when (case) {
                is Case -> {
                    for (selector in case.selectors) {
                        validateExpression(selector, errors)
                    }
                    validateBlockStatement(case.body, errors)
                }

                is DefaultCase -> validateBlockStatement(case.body, errors)
            }
        }
    }

    // ========== Type Compatibility ==========

    /**
     * Check if two type declarations are compatible.
     * 
     * Compatibility rules:
     * - AbstractIntType is compatible with all integer scalar types (i8, i16, i32, i64, u8, u16, u32, u64)
     * - AbstractFloatType is compatible with all float scalar types (f16, f32, f64)
     * - AbstractIntType and AbstractFloatType are NOT compatible with each other
     * - Concrete types are compatible with their abstract counterparts
     * - Same types are always compatible with themselves
     * 
     * @param type1 The first type declaration
     * @param type2 The second type declaration
     * @return true if the types are compatible, false otherwise
     */
    fun areTypesCompatible(type1: TypeDecl, type2: TypeDecl): Boolean {
        // Same type (by reference equality or structural equality)
        if (type1 == type2) return true
        
        // If both are the same concrete type
        if (type1 is ScalarType && type2 is ScalarType) {
            return type1.kind == type2.kind
        }
        
        // Abstract int compatibility
        val isAbstractInt1 = type1 is AbstractIntType
        val isAbstractInt2 = type2 is AbstractIntType
        
        // Abstract float compatibility
        val isAbstractFloat1 = type1 is AbstractFloatType
        val isAbstractFloat2 = type2 is AbstractFloatType
        
        // If both are abstract int
        if (isAbstractInt1 && isAbstractInt2) return true
        
        // If both are abstract float
        if (isAbstractFloat1 && isAbstractFloat2) return true
        
        // Abstract int is compatible with concrete integer types
        if (isAbstractInt1 && type2 is ScalarType) {
            return type2.kind.isInteger
        }
        
        if (isAbstractInt2 && type1 is ScalarType) {
            return type1.kind.isInteger
        }
        
        // Abstract float is compatible with concrete float types
        if (isAbstractFloat1 && type2 is ScalarType) {
            return type2.kind.isFloat
        }
        
        if (isAbstractFloat2 && type1 is ScalarType) {
            return type1.kind.isFloat
        }
        
        // Abstract types are not compatible with each other
        if ((isAbstractInt1 || isAbstractInt2) && (isAbstractFloat1 || isAbstractFloat2)) {
            return false
        }
        
        // Default: not compatible
        return false
    }
}
