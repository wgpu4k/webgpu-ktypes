package io.ygdrasil.wgsl.ast

import io.ygdrasil.wgsl.ir.Span

/**
 * The root of the WGSL Abstract Syntax Tree.
 * 
 * A TranslationUnit contains all the top-level declarations in a WGSL source file.
 */
data class TranslationUnit(
    /** The list of top-level declarations in this translation unit. */
    val declarations: List<GlobalDecl>,
    /** The span covering the entire translation unit. */
    val span: Span,
) {
    companion object {
        /** Creates an empty translation unit. */
        fun empty(): TranslationUnit = TranslationUnit(emptyList(), Span.UNDEFINED)
    }
}

/**
 * A top-level declaration in a WGSL source file.
 * 
 * This is the base class for all declarations that can appear at the top level
 * of a WGSL module (functions, structs, variables, type aliases, etc.).
 */
sealed class GlobalDecl {
    /** The span of this declaration. */
    abstract val span: Span
}

/**
 * An enable directive.
 */
data class EnableDirective(
    /** The list of enabled extensions. */
    val extensions: List<String>,
    override val span: Span,
) : GlobalDecl()

/**
 * A requires directive.
 */
data class RequiresDirective(
    /** The list of required features. */
    val features: List<String>,
    override val span: Span,
) : GlobalDecl()

/**
 * A diagnostic directive.
 */
data class DiagnosticDirective(
    /** The severity level. */
    val severity: String,
    /** The diagnostic rule. */
    val rule: String,
    override val span: Span,
) : GlobalDecl()

/**
 * A function declaration.
 */
data class FunctionDecl(
    /** Attributes on this function. */
    val attributes: List<Attribute>,
    /** The name of the function. */
    val name: String,
    /** The template parameters (if any). */
    val templateParams: List<TemplateParam>,
    /** The function parameters. */
    val parameters: List<Param>,
    /** Attributes on the return type. */
    val returnAttributes: List<Attribute> = emptyList(),
    /** The return type (null for functions that don't return a value). */
    val returnType: TypeDecl?,
    /** The function body (null for function declarations without a body). */
    val body: BlockStatement?,
    override val span: Span,
) : GlobalDecl()

/**
 * A struct declaration.
 */
data class StructDecl(
    /** Attributes on this struct. */
    val attributes: List<Attribute>,
    /** The name of the struct. */
    val name: String,
    /** The template parameters (if any). */
    val templateParams: List<TemplateParam>,
    /** The struct members. */
    val members: List<StructMember>,
    override val span: Span,
) : GlobalDecl()

/**
 * A struct member declaration.
 */
data class StructMember(
    /** Attributes on this member. */
    val attributes: List<Attribute>,
    /** The name of the member. */
    val name: String,
    /** The type of the member. */
    val type: TypeDecl,
    /** The default value (if any). */
    val defaultValue: Expression?,
    val span: Span,
)

/**
 * A global variable declaration (let, const, var).
 */
data class VariableDecl(
    /** The declaration kind (let, const, var). */
    val kind: VariableDeclKind,
    /** Attributes on this variable. */
    val attributes: List<Attribute>,
    /** The name of the variable. */
    val name: String,
    /** The storage class (null if not specified). */
    val storageClass: String? = null,
    /** The access mode (null if not specified). */
    val accessMode: String? = null,
    /** The type annotation (null if inferred). */
    val type: TypeDecl?,
    /** The initializer expression (required for const, optional for let/var). */
    val initializer: Expression?,
    override val span: Span,
) : GlobalDecl()

/**
 * The kind of variable declaration.
 */
enum class VariableDeclKind {
    LET,
    CONST,
    VAR,
}

/**
 * A type alias declaration.
 */
data class TypeAliasDecl(
    /** Attributes on this type alias. */
    val attributes: List<Attribute>,
    /** The name of the type alias. */
    val name: String,
    /** The template parameters (if any). */
    val templateParams: List<TemplateParam>,
    /** The type being aliased. */
    val type: TypeDecl,
    override val span: Span,
) : GlobalDecl()

/**
 * An override declaration (pipeline constant).
 */
data class OverrideDecl(
    /** Attributes on this override. */
    val attributes: List<Attribute>,
    /** The name of the override. */
    val name: String,
    /** The type annotation (null if inferred). */
    val type: TypeDecl?,
    /** The initializer expression (optional). */
    val initializer: Expression?,
    override val span: Span,
) : GlobalDecl()

/**
 * An enum declaration.
 */
data class EnumDecl(
    /** Attributes on this enum. */
    val attributes: List<Attribute>,
    /** The name of the enum. */
    val name: String,
    /** The enum members. */
    val members: List<EnumMember>,
    override val span: Span,
) : GlobalDecl()

/**
 * An enum member declaration.
 */
data class EnumMember(
    /** Attributes on this member. */
    val attributes: List<Attribute>,
    /** The name of the member. */
    val name: String,
    /** The value expression (if explicitly assigned). */
    val value: Expression?,
    val span: Span,
)

/**
 * A const assertion declaration.
 */
data class ConstAssertDecl(
    /** The expression to assert. */
    val expression: Expression,
    override val span: Span,
) : GlobalDecl()

/**
 * Entry point attributes.
 */
sealed class EntryPointAttribute {
    data object Compute : EntryPointAttribute()
    data class Fragment(val inputs: List<FragmentInput>) : EntryPointAttribute()
    data class Vertex(val outputs: List<VertexOutput>) : EntryPointAttribute()
}

/**
 * Fragment shader input.
 */
data class FragmentInput(
    val location: Int?,
    val builtin: BuiltinValue?,
    val type: TypeDecl,
)

/**
 * Vertex shader output.
 */
data class VertexOutput(
    val location: Int?,
    val builtin: BuiltinValue?,
    val type: TypeDecl,
)

/**
 * Built-in values.
 */
enum class BuiltinValue {
    POSITION,
    VERTEX_INDEX,
    INSTANCE_INDEX,
    DRAW_INDEX,
    FRONT_FACING,
    PRIMITIVE_INDEX,
    SAMPLE_INDEX,
    SAMPLE_MASK,
    VIEWPORT_INDEX,
    POINTSIZE,
    CLIP_DISTANCES,
    CULL_DISTANCES,
    DEVICE_INDEX,
    VIEW_INDEX,
    WORKGROUP_ID,
    NUM_WORKGROUPS,
    GLOBAL_INVOCATION_ID,
    LOCAL_INVOCATION_ID,
    LOCAL_INVOCATION_INDEX,
}

/**
 * A parameter declaration.
 */
data class Param(
    /** Attributes on this parameter. */
    val attributes: List<Attribute>,
    /** The name of the parameter. */
    val name: String,
    /** The type of the parameter. */
    val type: TypeDecl,
    /** The default value (if any). */
    val defaultValue: Expression?,
    val span: Span,
)

/**
 * A template parameter.
 */
data class TemplateParam(
    /** The name of the template parameter. */
    val name: String,
    /** The constraint (if any). */
    val constraint: TypeDecl?,
    val span: Span,
)

/**
 * An attribute.
 */
data class Attribute(
    /** The attribute name (without @). */
    val name: String,
    /** The attribute arguments (if any). */
    val args: List<Expression>,
    val span: Span,
)
