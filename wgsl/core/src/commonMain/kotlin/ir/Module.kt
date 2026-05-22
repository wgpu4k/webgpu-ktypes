package io.ygdrasil.wgsl.ir

import io.ygdrasil.wgsl.arena.Arena
import io.ygdrasil.wgsl.arena.Handle
import io.ygdrasil.wgsl.arena.UniqueArena
import kotlinx.serialization.Serializable

/**
 * The root of the Naga IR representation.
 * 
 * A Module contains all the types, functions, global variables, and entry points
 * needed to represent a shader.
 */
@Serializable
data class Module(
    /**
     * All types used in this module.
     * Uses UniqueArena to ensure identical types share the same Handle.
     */
    val types: UniqueArena<Type> = UniqueArena(),

    /**
     * Constants defined in this module.
     */
    val constants: Arena<Constant> = Arena(),

    /**
     * Global expressions used in constants initializers.
     */
    val globalExpressions: Arena<Expression> = Arena(),

    /**
     * Global variables in this module.
     */
    val globalVariables: Arena<GlobalVariable> = Arena(),

    /**
     * Functions in this module.
     */
    val functions: Arena<Function> = Arena(),

    /**
     * Entry points for this module (shader entry points).
     */
    val entryPoints: MutableList<EntryPoint> = mutableListOf(),

    /**
     * Special types used in this module.
     */
    val specialTypes: SpecialTypes = SpecialTypes(),

    /**
     * Diagnostic filter nodes.
     */
    val diagnosticFilters: Arena<DiagnosticFilterNode> = Arena(),

    /**
     * Root diagnostic filter node (if any).
     */
    val diagnosticFilterLeaf: Handle<DiagnosticFilterNode>? = null,

    /**
     * Documentation comments.
     */
    val docComments: DocComments? = null,
) {

    /**
     * Creates a new Module with the given components.
     */
    companion object {
        fun create(
            types: UniqueArena<Type> = UniqueArena(),
            constants: Arena<Constant> = Arena(),
            globalExpressions: Arena<Expression> = Arena(),
            globalVariables: Arena<GlobalVariable> = Arena(),
            functions: Arena<Function> = Arena(),
            entryPoints: MutableList<EntryPoint> = mutableListOf(),
            specialTypes: SpecialTypes = SpecialTypes(),
        ): Module {
            return Module(
                types = types,
                constants = constants,
                globalExpressions = globalExpressions,
                globalVariables = globalVariables,
                functions = functions,
                entryPoints = entryPoints.toMutableList(),
                specialTypes = specialTypes,
            )
        }
    }

    override fun toString(): String = buildString {
        appendLine("Module(")
        appendLine("  types: ${types.size}")
        appendLine("  constants: ${constants.size}")
        appendLine("  globalVariables: ${globalVariables.size}")
        appendLine("  functions: ${functions.size}")
        appendLine("  entryPoints: ${entryPoints.size}")
        appendLine(")")
    }
}

/**
 * Special types used in the module.
 */
@Serializable
data class SpecialTypes(
    /**
     * The any type (if used).
     */
    val any: Handle<Type>? = null,
)

/**
 * An entry point for a shader.
 */
@Serializable
data class EntryPoint(
    /**
     * The name of the entry point.
     */
    val name: String,

    /**
     * The function that implements this entry point.
     */
    val function: Handle<Function>,

    /**
     * The stage of this entry point.
     */
    val stage: ShaderStage,

    /**
     * The workgroup size for compute shaders.
     */
    val workgroupSize: List<Int>? = null,

    /**
     * Early depth test for fragment shaders.
     */
    val earlyDepthTest: EarlyDepthTest? = null,

    /**
     * The binding attributes for this entry point.
     */
    val bindings: List<BindingAttribute> = emptyList(),
) {
    override fun toString(): String = "@${stage.name} $name"
}

/**
 * Shader stages.
 */
@Serializable
enum class ShaderStage {
    Vertex,
    Fragment,
    Compute,
}

/**
 * Early depth test configuration.
 */
@Serializable
sealed class EarlyDepthTest {
    @Serializable
    object Default : EarlyDepthTest()
    @Serializable
    object ForceDepthGreater : EarlyDepthTest()
    @Serializable
    object ForceDepthGreaterEqual : EarlyDepthTest()
    @Serializable
    object ForceDepthLess : EarlyDepthTest()
    @Serializable
    object ForceDepthLessEqual : EarlyDepthTest()
    @Serializable
    data class ForceDepthUnchanged(val value: Handle<Expression>) : EarlyDepthTest()
}

/**
 * Binding attribute for entry points.
 */
@Serializable
sealed class BindingAttribute {
    @Serializable
    data class Builtin(val builtin: BuiltinValue) : BindingAttribute()
    @Serializable
    data class Location(val location: Int) : BindingAttribute()
    @Serializable
    data class Interpolate(val sampling: Sampling, val type: InterpolationType) : BindingAttribute()
}

/**
 * Sampling for interpolation.
 */
@Serializable
enum class Sampling {
    Center,
    Centroid,
    Sample,
}

/**
 * Interpolation type.
 */
@Serializable
enum class InterpolationType {
    Perspective,
    Linear,
    Flat,
}

/**
 * A diagnostic filter node.
 */
@Serializable
sealed class DiagnosticFilterNode {
    @Serializable
    data class All(val children: List<Handle<DiagnosticFilterNode>>) : DiagnosticFilterNode()
    @Serializable
    data class Any(val children: List<Handle<DiagnosticFilterNode>>) : DiagnosticFilterNode()
    @Serializable
    data class Operand(val expression: Handle<Expression>) : DiagnosticFilterNode()
    @Serializable
    data class Severity(val severity: DiagnosticSeverity, val children: List<Handle<DiagnosticFilterNode>>) :
        DiagnosticFilterNode()
}


/**
 * Documentation comments.
 */
@Serializable
data class DocComments(
    val comments: Map<Int, String> = emptyMap(),
)
