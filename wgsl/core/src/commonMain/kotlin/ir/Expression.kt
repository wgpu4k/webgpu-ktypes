package io.ygdrasil.wgsl.ir

import io.ygdrasil.wgsl.arena.Handle
import kotlinx.serialization.Serializable

/**
 * An expression in the Naga IR.
 * 
 * Expressions represent computations that produce values.
 */
@Serializable
data class Expression(
    /**
     * The kind of expression.
     */
    val kind: ExpressionKind,
) {
    override fun toString(): String = kind.toString()
}

/**
 * The kind of an expression.
 */
@Serializable
sealed class ExpressionKind {

    // Literal values
    @Serializable
    data class Literal(val value: LiteralValue) : ExpressionKind()

    // Variable access
    @Serializable
    data class GlobalVar(val handle: Handle<io.ygdrasil.wgsl.ir.GlobalVariable>) : ExpressionKind()
    @Serializable
    data class LocalVar(val handle: Handle<io.ygdrasil.wgsl.ir.LocalVariable>) : ExpressionKind()
    @Serializable
    data class FunctionArgument(val index: Int) : ExpressionKind()

    // Type constructors
    @Serializable
    data class TypeConstructor(val type: Handle<io.ygdrasil.wgsl.ir.Type>, val arguments: List<Handle<Expression>>) : ExpressionKind()

    // Unary operations
    @Serializable
    data class Unary(val operator: UnaryOperator, val expr: Handle<Expression>) : ExpressionKind()

    // Binary operations
    @Serializable
    data class Binary(val operator: BinaryOperator, val left: Handle<Expression>, val right: Handle<Expression>) :
        ExpressionKind()

    // Ternary operation
    @Serializable
    data class Select(
        val condition: Handle<Expression>,
        val accept: Handle<Expression>,
        val reject: Handle<Expression>
    ) : ExpressionKind()

    // Function calls
    @Serializable
    data class Call(
        val function: Handle<io.ygdrasil.wgsl.ir.Function>,
        val arguments: List<Handle<Expression>>
    ) : ExpressionKind()

    // Built-in function calls
    @Serializable
    data class BuiltinCall(
        val function: BuiltinFunction,
        val arguments: List<Handle<Expression>>
    ) : ExpressionKind()

    // Constant access
    @Serializable
    data class ConstantExpr(val handle: Handle<io.ygdrasil.wgsl.ir.Constant>) : ExpressionKind()

    // Splat
    @Serializable
    data class Splat(val size: VectorSize, val value: Handle<Expression>) : ExpressionKind()

    // Swizzle
    @Serializable
    data class Swizzle(
        val size: VectorSize,
        val vector: Handle<Expression>,
        val pattern: List<Int>
    ) : ExpressionKind()

    // Load from pointer
    @Serializable
    data class Load(val pointer: Handle<Expression>) : ExpressionKind()

    // Store to pointer
    @Serializable
    data class Store(val pointer: Handle<Expression>, val value: Handle<Expression>) : ExpressionKind()

    // Access index (dynamic or constant)
    @Serializable
    data class Access(
        val expr: Handle<Expression>,
        val index: Handle<Expression>
    ) : ExpressionKind()

    // Access constant index (for structs or optimized constant access)
    @Serializable
    data class AccessIndex(
        val expr: Handle<Expression>,
        val index: UInt
    ) : ExpressionKind()

    // Texture sampling
    @Serializable
    data class Sample(
        val texture: Handle<Expression>,
        val sampler: Handle<Expression>?,
        val coordinate: Handle<Expression>,
        val level: SampleLevel? = null,
        val depthRef: Handle<Expression>? = null
    ) : ExpressionKind()

    // Texture query
    @Serializable
    data class TextureQuery(val texture: Handle<Expression>, val query: TextureQueryKind) : ExpressionKind()

    // Array length
    @Serializable
    data class ArrayLength(val expr: Handle<Expression>) : ExpressionKind()

    // Cast operations
    @Serializable
    data class As(val expr: Handle<Expression>, val target: Handle<Type>) : ExpressionKind()
    @Serializable
    data class Bitcast(val expr: Handle<Expression>) : ExpressionKind()

    // Relational operations
    @Serializable
    data class Relational(val fun_: RelationalFunction, val arguments: List<Handle<Expression>>) : ExpressionKind()

    // Atomic operations
    @Serializable
    data class Atomic(
        val pointer: Handle<Expression>,
        val fun_: AtomicFunction,
        val arguments: List<Handle<Expression>>
    ) : ExpressionKind()

    // Value pointer
    @Serializable
    data class ValuePointer(val base: Handle<Expression>) : ExpressionKind()

    // Ray query
    @Serializable
    data class RayQuery(val query: RayQueryKind, val arguments: List<Handle<Expression>>) : ExpressionKind()
}

/**
 * Sample level for texture sampling.
 */
@Serializable
sealed class SampleLevel : io.ygdrasil.wgsl.arena.Equatable {
    @Serializable
    class Zero : SampleLevel()
    @Serializable
    data class MIPMAP(val level: Handle<Expression>) : SampleLevel()
    @Serializable
    data class AUTOMATIC(val level: Handle<Expression>) : SampleLevel()

    override fun isEquivalentTo(other: Any): Boolean {
        if (this === other) return true
        if (other !is SampleLevel) return false
        return when (this) {
            is Zero -> other is Zero
            is MIPMAP -> other is MIPMAP && level == other.level
            is AUTOMATIC -> other is AUTOMATIC && level == other.level
        }
    }
}

/**
 * Texture query kinds.
 */
@Serializable
enum class TextureQueryKind {
    NumSamples,
    NumLevels,
    NumLayers,
    Size,
    SizeLevel,
}

/**
 * Relational functions.
 */
@Serializable
enum class RelationalFunction {
    All,
    Any,
    IsNan,
    IsInf,
    IsFinite,
    IsNormal,
    SignBit,
}

/**
 * Atomic functions.
 */
@Serializable
enum class AtomicFunction {
    Add,
    Subtract,
    And,
    Or,
    Xor,
    Min,
    Max,
    Exchange,
    CompSwap,
}

/**
 * Ray query kinds.
 */
@Serializable
enum class RayQueryKind {
    // ... ray query kinds
}

/**
 * Literal value for expressions.
 */
@Serializable
sealed class LiteralValue {
    @Serializable
    data class Scalar(val value: ScalarValue) : LiteralValue()
    @Serializable
    data class Vector(val components: List<ScalarValue>) : LiteralValue()
    @Serializable
    data class Matrix(val columns: List<List<ScalarValue>>) : LiteralValue()
}

/**
 * Scalar value for literals.
 */
@Serializable
sealed class ScalarValue {
    @Serializable
    data class Bool(val value: Boolean) : ScalarValue()
    @Serializable
    data class I8(val value: Byte) : ScalarValue()
    @Serializable
    data class U8(val value: Short) : ScalarValue()
    @Serializable
    data class I16(val value: Short) : ScalarValue()
    @Serializable
    data class U16(val value: Int) : ScalarValue()
    @Serializable
    data class I32(val value: Int) : ScalarValue()
    @Serializable
    data class U32(val value: Long) : ScalarValue()
    @Serializable
    data class I64(val value: Long) : ScalarValue()
    @Serializable
    data class U64(val value: ULong) : ScalarValue()
    @Serializable
    data class F16(val value: Float) : ScalarValue()
    @Serializable
    data class F32(val value: Float) : ScalarValue()
    @Serializable
    data class F64(val value: Double) : ScalarValue()
    @Serializable
    data class AbstractInt(val value: Long) : ScalarValue()
    @Serializable
    data class AbstractFloat(val value: Double) : ScalarValue()
}
