package io.ygdrasil.wgsl.ast

import io.ygdrasil.wgsl.ir.Span

/**
 * A statement in WGSL.
 * 
 * Statements represent executable code that can appear in function bodies.
 */
sealed class Statement {
    /** The span of this statement. */
    abstract val span: Span
}

/**
 * An empty statement (just a semicolon).
 */
data class EmptyStatement(
    override val span: Span,
) : Statement()

/**
 * A block statement (compound statement).
 */
data class BlockStatement(
    /** The statements in the block. */
    val statements: List<Statement>,
    override val span: Span,
) : Statement()

/**
 * An if statement.
 */
data class IfStatement(
    /** The condition. */
    val condition: Expression,
    /** The then branch. */
    val thenBranch: Statement,
    /** The else branch (null if not present). */
    val elseBranch: Statement?,
    override val span: Span,
) : Statement()

/**
 * A switch statement.
 */
data class SwitchStatement(
    /** The expression to switch on. */
    val expression: Expression,
    /** The switch body. */
    val body: SwitchBody,
    override val span: Span,
) : Statement()

/**
 * The body of a switch statement.
 */
data class SwitchBody(
    /** The cases in the switch. */
    val cases: List<SwitchCase>,
    val span: Span,
)

/**
 * A case in a switch statement.
 */
sealed class SwitchCase {
    abstract val span: Span
}

/**
 * A normal case with values.
 */
data class Case(
    /** The values to match. */
    val selectors: List<Expression>,
    /** Whether this case includes the default selector. */
    val isDefault: Boolean,
    /** The body of the case. */
    val body: BlockStatement,
    override val span: Span,
) : SwitchCase()

/**
 * The default case.
 */
data class DefaultCase(
    /** The body of the default case. */
    val body: BlockStatement,
    override val span: Span,
) : SwitchCase()

/**
 * A loop statement.
 */
data class LoopStatement(
    /** The body of the loop. */
    val body: BlockStatement,
    /** The continuing block (executed before each iteration continues). */
    val continuing: BlockStatement?,
    override val span: Span,
) : Statement()

/**
 * A while loop statement.
 */
data class WhileStatement(
    /** The condition. */
    val condition: Expression,
    /** The body of the loop. */
    val body: BlockStatement,
    /** The continuing block (executed before each iteration continues). */
    val continuing: BlockStatement?,
    override val span: Span,
) : Statement()

/**
 * A for loop statement.
 */
data class ForStatement(
    /** The init expression (executed once at the start). */
    val init: Statement?,
    /** The condition (checked before each iteration). */
    val condition: Expression?,
    /** The update expression (executed after each iteration). */
    val update: Expression?,
    /** The body of the loop. */
    val body: BlockStatement,
    override val span: Span,
) : Statement()

/**
 * A break statement.
 */
data class BreakStatement(
    override val span: Span,
) : Statement()

/**
 * A break if statement (used in continuing blocks).
 */
data class BreakIfStatement(
    val condition: Expression,
    override val span: Span,
) : Statement()

/**
 * A continue statement.
 */
data class ContinueStatement(
    override val span: Span,
) : Statement()

/**
 * A return statement.
 */
data class ReturnStatement(
    /** The return value (null for functions that don't return a value). */
    val value: Expression?,
    override val span: Span,
) : Statement()

/**
 * A discard statement.
 */
data class DiscardStatement(
    override val span: Span,
) : Statement()

/**
 * A variable declaration statement (local let, const, var).
 */
data class VariableDeclStatement(
    /** The declaration kind. */
    val kind: VariableDeclKind,
    /** The name of the variable. */
    val name: String,
    /** The storage class (null if not specified). */
    val storageClass: String? = null,
    /** The access mode (null if not specified). */
    val accessMode: String? = null,
    /** The type annotation (null if inferred). */
    val type: TypeDecl?,
    /** The initializer expression. */
    val initializer: Expression?,
    override val span: Span,
) : Statement()

/**
 * An assignment statement.
 */
data class AssignmentStatement(
    /** The left-hand side. */
    val lhs: Expression,
    /** The right-hand side. */
    val rhs: Expression,
    /** The operator (null for simple assignment). */
    val op: BinaryOperator?,
    override val span: Span,
) : Statement()

/**
 * A phony assignment statement (_ = expr).
 */
data class PhonyAssignmentStatement(
    val expression: Expression,
    override val span: Span,
) : Statement()

/**
 * An increment or decrement statement.
 */
data class IncDecStatement(
    /** The expression to increment/decrement. */
    val expr: Expression,
    /** Whether to increment or decrement. */
    val isIncrement: Boolean,
    override val span: Span,
) : Statement()

/**
 * An expression statement (an expression evaluated for its side effects).
 */
data class ExpressionStatement(
    /** The expression. */
    val expr: Expression,
    override val span: Span,
) : Statement()

/**
 * A const assertion statement.
 */
data class ConstAssertStatement(
    /** The expression to assert. */
    val expression: Expression,
    override val span: Span,
) : Statement()
