package io.ygdrasil.wgsl.ast

import io.ygdrasil.wgsl.ir.Span

/**
 * An expression in WGSL.
 * 
 * Expressions represent computations that produce values.
 */
sealed class Expression {
    /** The span of this expression. */
    abstract val span: Span
}

/**
 * A literal value.
 */
sealed class Literal : Expression()

/**
 * An integer literal.
 */
data class IntLiteral(
    /** The value. */
    val value: Long,
    /** The suffix (i, u, or null). */
    val suffix: String?,
    override val span: Span,
) : Literal()

/**
 * A float literal.
 */
data class FloatLiteral(
    /** The value. */
    val value: Double,
    /** The suffix (f, F, or null). */
    val suffix: String?,
    override val span: Span,
) : Literal()

/**
 * A boolean literal.
 */
data class BoolLiteral(
    /** The value. */
    val value: Boolean,
    override val span: Span,
) : Literal()

/**
 * A string literal.
 */
data class StringLiteral(
    /** The value (without quotes). */
    val value: String,
    override val span: Span,
) : Literal()

/**
 * An identifier expression.
 */
data class IdentExpr(
    /** The identifier name. */
    val name: String,
    override val span: Span,
) : Expression()

/**
 * A call expression (function or type constructor call).
 */
data class CallExpr(
    /** The function or type being called. */
    val callee: Expression,
    /** The arguments. */
    val args: List<Expression>,
    /** The template arguments (if any). */
    val templateArgs: List<TypeDecl>?,
    override val span: Span,
) : Expression()

/**
 * A member access expression (e.g., `foo.bar`).
 */
data class MemberAccessExpr(
    /** The object. */
    val objectExpr: Expression,
    /** The member name. */
    val member: String,
    override val span: Span,
) : Expression()

/**
 * An array/struct indexing expression (e.g., `foo[0]`).
 */
data class IndexExpr(
    /** The object. */
    val objectExpr: Expression,
    /** The index expression. */
    val index: Expression,
    override val span: Span,
) : Expression()

/**
 * A unary expression (e.g., `-x`, `!x`, `~x`).
 */
data class UnaryExpr(
    /** The operator. */
    val op: UnaryOperator,
    /** The operand. */
    val operand: Expression,
    override val span: Span,
) : Expression()

/**
 * Unary operators.
 */
enum class UnaryOperator {
    MINUS,
    PLUS,
    NOT,
    BITWISE_NOT,
    DEREF,
    ADDRESS_OF,
}

/**
 * A binary expression (e.g., `x + y`, `a && b`).
 */
data class BinaryExpr(
    /** The left operand. */
    val left: Expression,
    /** The operator. */
    val op: BinaryOperator,
    /** The right operand. */
    val right: Expression,
    override val span: Span,
) : Expression()

/**
 * Binary operators.
 */
enum class BinaryOperator {
    // Arithmetic
    ADD,
    SUBTRACT,
    MULTIPLY,
    DIVIDE,
    MODULO,

    // Bitwise
    BITWISE_AND,
    BITWISE_OR,
    BITWISE_XOR,
    LEFT_SHIFT,
    RIGHT_SHIFT,

    // Comparison
    EQ,
    NEQ,
    LT,
    LTE,
    GT,
    GTE,

    // Logical
    LOGICAL_AND,
    LOGICAL_OR,

    // Other
    DOT,
    ARROW,
}

/**
 * A ternary (conditional) expression (e.g., `a ? b : c`).
 */
data class TernaryExpr(
    /** The condition. */
    val condition: Expression,
    /** The true branch. */
    val trueExpr: Expression,
    /** The false branch. */
    val falseExpr: Expression,
    override val span: Span,
) : Expression()

/**
 * A type cast expression (e.g., `f32(x)` or `x : f32`).
 */
data class TypeCastExpr(
    /** The expression being cast. */
    val expr: Expression,
    /** The target type. */
    val type: TypeDecl,
    override val span: Span,
) : Expression()

/**
 * A vector/matrix swizzle or selection expression.
 */
data class SwizzleExpr(
    /** The vector/matrix. */
    val objectExpr: Expression,
    /** The component names. */
    val components: List<String>,
    override val span: Span,
) : Expression()

/**
 * A bitcast expression (e.g., `bitcast<f32>(x)`).
 */
data class BitcastExpr(
    /** The expression being cast. */
    val expr: Expression,
    /** The target type. */
    val type: TypeDecl,
    override val span: Span,
) : Expression()

/**
 * Represents a reference to a predeclared enumerant value.
 * 
 * For example: AddressMode.clamp_to_edge, FilterMode.nearest
 * 
 * @property category The category/type of the enumerant (e.g., "AddressMode")
 * @property enumerant The actual predeclared enumerant value
 * @property span The source span of this expression
 */
data class PredeclaredEnumerantExpr(
    /** The category/type of the enumerant (e.g., "AddressMode"). */
    val category: String,
    /** The actual predeclared enumerant value. */
    val enumerant: PredeclaredEnumerant,
    override val span: Span,
) : EnumValueExpr() {
    override val enumValue: EnumValue by lazy { enumerant.toEnumValue() }
    override fun getEnumTypeName(): String = category
}
