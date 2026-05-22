package io.ygdrasil.wgsl.ir

import io.ygdrasil.wgsl.arena.Arena
import io.ygdrasil.wgsl.arena.Equatable
import io.ygdrasil.wgsl.arena.Handle
import io.ygdrasil.wgsl.arena.Range
import kotlinx.serialization.Serializable

/**
 * A function in the module.
 */
@Serializable
data class Function(
    /**
     * The name of the function.
     */
    val name: String,

    /**
     * The parameters of the function.
     */
    val parameters: List<FunctionParameter>,

    /**
     * The return type of the function (if any).
     */
    val returnType: Handle<Type>? = null,

    /**
     * The local variables used in the function.
     */
    val localVariables: Arena<LocalVariable>,

    /**
     * The expressions in the function.
     */
    val expressions: Arena<Expression>,

    /**
     * The blocks in the function.
     */
    val blocks: Arena<Block>,

    /**
     * The body of the function (a block of statements).
     */
    val body: Handle<Block>,

    /**
     * The result expression (if the function returns a value).
     */
    val result: Handle<Expression>? = null,
) {
    override fun toString(): String =
        "fn $name(${parameters.joinToString { it.toString() }})${returnType?.let { " -> ${it.index}" } ?: ""}"
}

/**
 * A parameter of a function.
 */
@Serializable
data class FunctionParameter(
    /**
     * The name of the parameter.
     */
    val name: String,

    /**
     * The type of the parameter.
     */
    val type: Handle<Type>,

    /**
     * The binding for this parameter (if it's a shader I/O parameter).
     */
    val binding: BindingAttribute? = null,
) {
    override fun toString(): String = name
}

/**
 * A block of statements.
 */
@Serializable
data class Block(
    /**
     * The statements in the block.
     */
    val statements: List<Statement>,
) {
    override fun toString(): String = "{ ${statements.joinToString("; ")} }"
}

/**
 * Built-in values for shader I/O.
 */
@Serializable
enum class BuiltinValue {
    Position,
    VertexIndex,
    InstanceIndex,
    GlobalInvocationId,
    LocalInvocationId,
    WorkgroupId,
    NumWorkgroups,
    LocalInvocationIndex,
    WorkgroupSize,
    BaseVertex,
    BaseInstance,
    DrawIndex,
    ClipDistance,
    CullDistance,
    PointSize,
    PointCoord,
    FrontFacing,
    PrimitiveIndex,
    ViewportIndex,
    MultiViewportIndex,
    IsFrontFace,
    SampleIndex,
    SampleMask,
    SamplePosition,
    ViewIndex,
    HelperInvocation,
}

/**
 * A statement in the Naga IR.
 */
@Serializable
sealed class Statement {

    /**
     * Empty statement (no-op).
     */
    @Serializable
    object Nop : Statement()

    /**
     * Block of statements.
     */
    @Serializable
    data class Block(val block: Handle<io.ygdrasil.wgsl.ir.Block>) : Statement()

    /**
     * Variable declaration statement.
     */
    @Serializable
    data class Declare(val variable: Handle<LocalVariable>) : Statement()

    /**
     * Initialize a variable with an expression.
     */
    @Serializable
    data class Init(val variable: Handle<LocalVariable>) : Statement()

    /**
     * Assign a value to a pointer.
     */
    @Serializable
    data class Assign(val pointer: Handle<Expression>, val value: Handle<Expression>) : Statement()

    /**
     * Emit a range of expressions.
     */
    @Serializable
    data class Emit(val range: Range<Expression>) : Statement()

    /**
     * If statement.
     */
    @Serializable
    data class If(
        val condition: Handle<Expression>,
        val accept: Handle<io.ygdrasil.wgsl.ir.Block>,
        val reject: Handle<io.ygdrasil.wgsl.ir.Block>? = null
    ) : Statement()

    /**
     * Switch statement.
     */
    @Serializable
    data class Switch(
        val selector: Handle<Expression>,
        val body: Handle<io.ygdrasil.wgsl.ir.Block>,
        val default: Handle<io.ygdrasil.wgsl.ir.Block>? = null,
        val cases: List<Case>
    ) : Statement()

    /**
     * Loop statement.
     */
    @Serializable
    data class Loop(
        val body: Handle<io.ygdrasil.wgsl.ir.Block>,
        val continuing: Handle<io.ygdrasil.wgsl.ir.Block>? = null
    ) : Statement()

    /**
     * Break statement.
     */
    @Serializable
    object Break : Statement()

    /**
     * Continue statement.
     */
    @Serializable
    object Continue : Statement()

    /**
     * Return statement.
     */
    @Serializable
    data class Return(val value: Handle<Expression>? = null) : Statement()

    /**
     * Discard statement.
     */
    @Serializable
    object Discard : Statement()

    /**
     * Kill statement.
     */
    @Serializable
    object Kill : Statement()
}

/**
 * A case in a switch statement.
 */
@Serializable
data class Case(
    /**
     * The selector value for this case.
     */
    val selector: CaseSelector,

    /**
     * The body of the case.
     */
    val body: Handle<io.ygdrasil.wgsl.ir.Block>,
)

/**
 * A selector for a case in a switch statement.
 */
@Serializable
sealed class CaseSelector : Equatable {
    @Serializable
    class Value(val value: ScalarValue) : CaseSelector()
    @Serializable
    class Default : CaseSelector()

    override fun isEquivalentTo(other: Any): Boolean {
        if (this === other) return true
        if (other !is CaseSelector) return false
        return when (this) {
            is Value -> other is Value && value == other.value
            is Default -> other is Default
        }
    }
}

/**
 * A builtin function.
 */
@Serializable
enum class BuiltinFunction {
    // Math
    Abs,
    Acos,
    Acosh,
    Asin,
    Asinh,
    Atan,
    Atan2,
    Atanh,
    Ceil,
    Clamp,
    Cos,
    Cosh,
    CountLeadingZeros,
    CountOneBits,
    CountTrailingZeros,
    Cross,
    Degrees,
    Determinant,
    Distance,
    Dot,
    Exp,
    Exp2,
    Floor,
    Fma,
    Fract,
    Frexp,
    InverseSqrt,
    Ldexp,
    Length,
    Ln,
    Log2,
    Max,
    Min,
    Mix,
    Modf,
    Normalize,
    Pow,
    QuantizeToF16,
    Radians,
    Reflect,
    Refract,
    ReverseBits,
    Round,
    Sign,
    Sin,
    Sinh,
    Smoothstep,
    Sqrt,
    Step,
    Tan,
    Tanh,
    Trunc,

    // Vector
    All,
    Any,

    // Texture
    TextureDimensions,
    TextureNumLayers,
    TextureNumLevels,
    TextureNumSamples,
    TextureSample,
    TextureSampleBias,
    TextureSampleCompare,
    TextureSampleCompareLevel,
    TextureSampleGrad,
    TextureSampleLevel,
    TextureLoad,
    TextureStore,
    TexelBufferLoad,
    TexelBufferStore,

    // Atomic
    AtomicAdd,
    AtomicAnd,
    AtomicCompareExchangeWeak,
    AtomicExchange,
    AtomicLoad,
    AtomicMax,
    AtomicMin,
    AtomicOr,
    AtomicStore,
    AtomicSubtract,
    AtomicUmad,
    AtomicUmax,
    AtomicUmin,
    AtomicXor,
}
