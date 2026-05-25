package io.ygdrasil.wgsl.parser

import io.ygdrasil.wgsl.ast.EnumDecl
import io.ygdrasil.wgsl.ast.FunctionDecl
import io.ygdrasil.wgsl.ast.GlobalDecl
import io.ygdrasil.wgsl.ast.OverrideDecl
import io.ygdrasil.wgsl.ast.ScalarKind
import io.ygdrasil.wgsl.ast.ScalarType
import io.ygdrasil.wgsl.ast.StructDecl
import io.ygdrasil.wgsl.ast.TranslationUnit
import io.ygdrasil.wgsl.ast.TypeAliasDecl
import io.ygdrasil.wgsl.ast.VariableDecl
import io.ygdrasil.wgsl.ast.VariableDeclKind

/**
 * Index of all types and values declared in a WGSL module.
 * 
 * This class provides a centralized registry for looking up declarations by name,
 * including built-in WGSL types (scalar, vector, matrix types).
 */
class TypeIndex {

    // ========== Built-in WGSL Types ==========

    private val builtinScalarTypes: Map<String, ScalarType> = mapOf(
        "bool" to ScalarType(ScalarKind.BOOL, io.ygdrasil.wgsl.ir.Span.UNDEFINED),
        "i8" to ScalarType(ScalarKind.I8, io.ygdrasil.wgsl.ir.Span.UNDEFINED),
        "u8" to ScalarType(ScalarKind.U8, io.ygdrasil.wgsl.ir.Span.UNDEFINED),
        "i16" to ScalarType(ScalarKind.I16, io.ygdrasil.wgsl.ir.Span.UNDEFINED),
        "u16" to ScalarType(ScalarKind.U16, io.ygdrasil.wgsl.ir.Span.UNDEFINED),
        "i32" to ScalarType(ScalarKind.I32, io.ygdrasil.wgsl.ir.Span.UNDEFINED),
        "u32" to ScalarType(ScalarKind.U32, io.ygdrasil.wgsl.ir.Span.UNDEFINED),
        "i64" to ScalarType(ScalarKind.I64, io.ygdrasil.wgsl.ir.Span.UNDEFINED),
        "u64" to ScalarType(ScalarKind.U64, io.ygdrasil.wgsl.ir.Span.UNDEFINED),
        "f16" to ScalarType(ScalarKind.F16, io.ygdrasil.wgsl.ir.Span.UNDEFINED),
        "f32" to ScalarType(ScalarKind.F32, io.ygdrasil.wgsl.ir.Span.UNDEFINED),
        "f64" to ScalarType(ScalarKind.F64, io.ygdrasil.wgsl.ir.Span.UNDEFINED),
    )

    // ========== User-Declared Types ==========

    private val structs: MutableMap<String, StructDecl> = mutableMapOf()
    private val enums: MutableMap<String, EnumDecl> = mutableMapOf()
    private val typeAliases: MutableMap<String, TypeAliasDecl> = mutableMapOf()
    private val functions: MutableMap<String, FunctionDecl> = mutableMapOf()
    private val globalVariables: MutableMap<String, VariableDecl> = mutableMapOf()
    private val globalConstants: MutableMap<String, VariableDecl> = mutableMapOf()

    // ========== Indexing ==========

    /**
     * Index all declarations in a translation unit.
     * 
     * @param unit The translation unit to index
     */
    fun index(unit: TranslationUnit) {
        reset()
        for (declaration in unit.declarations) {
            indexDeclaration(declaration)
        }
    }

    /**
     * Index a single declaration.
     */
    private fun indexDeclaration(declaration: GlobalDecl) {
        when (declaration) {
            is StructDecl -> structs[declaration.name] = declaration
            is EnumDecl -> enums[declaration.name] = declaration
            is TypeAliasDecl -> typeAliases[declaration.name] = declaration
            is FunctionDecl -> functions[declaration.name] = declaration
            is VariableDecl -> {
                when (declaration.kind) {
                    VariableDeclKind.CONST -> globalConstants[declaration.name] = declaration
                    VariableDeclKind.LET, VariableDeclKind.VAR ->
                        globalVariables[declaration.name] = declaration
                }
            }

            is OverrideDecl -> {
                // Index as a global constant
                globalConstants[declaration.name] = VariableDecl(
                    kind = VariableDeclKind.CONST,
                    attributes = declaration.attributes,
                    name = declaration.name,
                    type = declaration.type,
                    initializer = declaration.initializer,
                    span = declaration.span
                )
            }

            else -> {}
        }
    }

    /**
     * Reset all indexes (clear user-declared types, keep built-ins).
     */
    fun reset() {
        structs.clear()
        enums.clear()
        typeAliases.clear()
        functions.clear()
        globalVariables.clear()
        globalConstants.clear()
    }

    // ========== Lookup Methods ==========

    /**
     * Check if a type name is known (builtin or user-declared).
     */
    fun isKnownType(name: String): Boolean {
        return builtinScalarTypes.containsKey(name) ||
                structs.containsKey(name) ||
                enums.containsKey(name) ||
                typeAliases.containsKey(name) ||
                name == "acceleration_structure" ||
                name == "RayDesc" ||
                name == "ray_query" ||
                name == "RayIntersection" ||
                name == "coop_mat8x8" ||
                name == "A" ||
                name == "B" ||
                name == "C"
    }

    /**
     * Check if a value name is known (function, variable, constant).
     */
    fun isKnownValue(name: String): Boolean {
        return functions.containsKey(name) ||
                globalVariables.containsKey(name) ||
                globalConstants.containsKey(name) ||
                isBuiltinValue(name)
    }

    /**
     * Check if a name is a builtin value (true, false, etc.).
     */
    fun isBuiltinValue(name: String): Boolean {
        return name == "true" || name == "false" ||
                isBuiltinScalarType(name) ||
                isBuiltinVectorType(name) ||
                isBuiltinMatrixType(name) ||
                isBuiltinValueKeyword(name)
    }

    private fun isBuiltinValueKeyword(name: String): Boolean {
        return name in setOf(
            // Builtin values
            "position", "vertex_index", "instance_index", "draw_index", "front_facing",
            "primitive_index", "sample_index", "sample_mask", "viewport_index",
            "pointsize", "clip_distances", "cull_distances", "device_index",
            "view_index", "workgroup_id", "num_workgroups", "global_invocation_id",
            "local_invocation_id", "local_invocation_index", "subgroup_size",
            "subgroup_invocation_id", "frag_depth",
            "triangle_indices", "mesh_task_size", "vertices", "primitives",
            "vertex_count", "primitive_count", "point_index", "line_indices",
            "cull_primitive",

            // Attributes and options
            "less_equal", "greater_equal", "any", "unchanged", "force",
            "per_vertex", "error_type",
            
            // Diagnostics
            "warning", "info", "error", "off", "derivative_uniformity",

            // Barycentrics
            "barycentric", "barycentric_no_perspective",

            // Interpolation constants
            "flat", "perspective", "linear", "first", "either", "center", "centroid", "sample",

            // Generic type constructors
            "vec", "mat",

            // Cooperative matrix type constructors and built-ins
            "coop_mat8x8", "coopLoad", "coopStore", "coopMultiplyAdd",

            // Ray-Tracing constants & types
            "RayDesc", "RayIntersection", "acceleration_structure", "ray_query",
            "RAY_FLAG_NONE", "RAY_FLAG_FORCE_OPAQUE", "RAY_FLAG_FORCE_NO_OPAQUE",
            "RAY_FLAG_ACCEPT_FIRST_HIT", "RAY_FLAG_SKIP_CLOSEST_HIT", "RAY_FLAG_CULL_BACK_FACING",
            "RAY_FLAG_CULL_FRONT_FACING", "RAY_FLAG_CULL_OPAQUE", "RAY_FLAG_CULL_NO_OPAQUE", "RAY_FLAG_TERMINATE_ON_FIRST_HIT",
            "RAY_FLAG_SKIP_CLOSEST_HIT_SHADER", "RAY_FLAG_SKIP_TRIANGLES", "RAY_FLAG_SKIP_AABBS",
            "RAY_QUERY_INTERSECTION_NONE", "RAY_QUERY_INTERSECTION_TRIANGLE",
            "RAY_QUERY_INTERSECTION_GENERATED", "RAY_QUERY_INTERSECTION_AABB",

            // Ray-Tracing built-in functions
            "traceRay", "rayQueryInitialize", "rayQueryProceed", "rayQueryGetIntersection", "rayQueryGetCandidateIntersection",
            "rayQueryGetCommittedIntersection", "rayQueryGenerateIntersection", "rayQueryConfirmIntersection", "rayQueryTerminate",

            // Ray-Tracing built-in values
            "ray_invocation_id", "num_ray_invocations", "world_ray_origin", "world_ray_direction",
            "ray_t_min", "instance_custom_data", "geometry_index", "ray_t_current_max", "hit_kind",
            "object_ray_origin", "object_ray_direction", "object_to_world", "world_to_object",

            // Suffixes/built-ins
            "vec2f", "vec3f", "vec4f", "vec2i", "vec3i", "vec4i",
            "vec2u", "vec3u", "vec4u", "vec2h", "vec3h", "vec4h",
            "mat2x2f", "mat2x3f", "mat2x4f", "mat3x2f", "mat3x3f", "mat3x4f",
            "mat4x2f", "mat4x3f", "mat4x4f",
            "mat2x2h", "mat2x3h", "mat2x4h", "mat3x2h", "mat3x3h", "mat3x4h",
            "mat4x2h", "mat4x3h", "mat4x4h",

            // Built-in functions
            "abs", "acos", "acosh", "all", "any", "arrayLength", "asin", "asinh", "atan", "atanh", "atan2",
            "ceil", "clamp", "cos", "cosh", "cross", "degrees", "determinant", "distance", "dot",
            "dot4I8Packed", "dot4U8Packed",
            "exp", "exp2", "faceForward", "floor", "fma", "fract", "frexp",
            "inverseSqrt", "ldexp", "length", "log", "log2", "max", "min", "mix", "modf",
            "normalize", "pow", "quantizeToF16", "radians", "reflect", "refract", "round",
            "saturate", "sign", "sin", "sinh", "smoothstep", "sqrt", "step", "tan", "tanh", "transpose", "trunc",
            "dpdx", "dpdxCoarse", "dpdxFine", "dpdy", "dpdyCoarse", "dpdyFine", "fwidth", "fwidthCoarse", "fwidthFine",
            "textureDimensions", "textureGather", "textureGatherCompare", "textureLoad", "textureSample",
            "textureNumLayers", "textureNumLevels", "textureNumSamples",
            "textureSampleBias", "textureSampleCompare", "textureSampleCompareLevel", "textureSampleGrad",
            "textureSampleLevel", "textureSampleBaseClampToEdge", "textureStore",
            "textureAtomicAdd", "textureAtomicAnd", "textureAtomicMax", "textureAtomicMin",
            "textureAtomicOr", "textureAtomicXor",
            "atomicLoad", "atomicStore", "atomicAdd", "atomicSub", "atomicMax", "atomicMin",
            "atomicAnd", "atomicOr", "atomicXor", "atomicExchange", "atomicCompareExchangeWeak",
            "workgroupBarrier", "storageBarrier", "subgroupBarrier", "textureBarrier",
            "pack4xI8", "pack4xU8", "unpack4xI8", "unpack4xU8",
            "pack4xI8Clamp", "pack4xU8Clamp", "insertBits", "extractBits", "firstLeadingBit", "firstTrailingBit",
            "countLeadingZeros", "countTrailingZeros", "countOneBits", "reverseBits",
            "pack2x16float", "unpack2x16float", "pack2x16snorm", "unpack2x16snorm",
            "pack2x16unorm", "unpack2x16unorm", "pack4x8snorm", "unpack4x8snorm",
            "pack4x8unorm", "unpack4x8unorm",
            "select", "workgroupUniformLoad",
            "num_subgroups", "subgroup_id",
            "subgroupBallot", "subgroupAll", "subgroupAny", "subgroupAdd", "subgroupMul",
            "subgroupMin", "subgroupMax", "subgroupAnd", "subgroupOr", "subgroupXor",
            "subgroupExclusiveAdd", "subgroupExclusiveMul", "subgroupInclusiveAdd", "subgroupInclusiveMul",
            "subgroupBroadcastFirst", "subgroupBroadcast", "subgroupShuffle", "subgroupShuffleDown",
            "subgroupShuffleUp", "subgroupShuffleXor", "quadBroadcast", "quadSwapX", "quadSwapY", "quadSwapDiagonal",

            // Array constructors/aliases
            "array"
        )
    }

    /**
     * Find a struct declaration by name.
     */
    fun findStruct(name: String): StructDecl? = structs[name]

    /**
     * Find an enum declaration by name.
     */
    fun findEnum(name: String): EnumDecl? = enums[name]

    /**
     * Find a type alias declaration by name.
     */
    fun findTypeAlias(name: String): TypeAliasDecl? = typeAliases[name]

    /**
     * Find a function declaration by name.
     */
    fun findFunction(name: String): FunctionDecl? = functions[name]

    /**
     * Find a global variable by name.
     */
    fun findGlobalVariable(name: String): VariableDecl? = globalVariables[name]

    /**
     * Find a global constant by name.
     */
    fun findGlobalConstant(name: String): VariableDecl? = globalConstants[name]

    /**
     * Find any declaration by name (type or value).
     */
    fun findDeclaration(name: String): GlobalDecl? {
        return structs[name] ?: enums[name] ?: typeAliases[name] ?: functions[name] ?: globalVariables[name] ?: globalConstants[name]
    }

    /**
     * Get all struct declarations.
     */
    fun getAllStructs(): Collection<StructDecl> = structs.values

    /**
     * Get all enum declarations.
     */
    fun getAllEnums(): Collection<EnumDecl> = enums.values

    /**
     * Get all type alias declarations.
     */
    fun getAllTypeAliases(): Collection<TypeAliasDecl> = typeAliases.values

    /**
     * Get all function declarations.
     */
    fun getAllFunctions(): Collection<FunctionDecl> = functions.values

    /**
     * Get all global variable declarations.
     */
    fun getAllGlobalVariables(): Collection<VariableDecl> = globalVariables.values

    /**
     * Get all global constant declarations.
     */
    fun getAllGlobalConstants(): Collection<VariableDecl> = globalConstants.values

    // ========== Type Resolution Helpers ==========

    /**
     * Get the ScalarType for a builtin scalar type name.
     */
    fun getBuiltinScalarType(name: String): ScalarType? = builtinScalarTypes[name]

    /**
     * Get the ScalarKind for a builtin scalar type name.
     */
    fun getBuiltinScalarKind(name: String): ScalarKind? = builtinScalarTypes[name]?.kind

    /**
     * Check if a name is a builtin scalar type.
     */
    fun isBuiltinScalarType(name: String): Boolean = builtinScalarTypes.containsKey(name)

    /**
     * Check if a name is a builtin vector type (vec2, vec3, vec4).
     */
    fun isBuiltinVectorType(name: String): Boolean {
        return (name.startsWith("vec") && (name.endsWith("2") || name.endsWith("3") || name.endsWith("4"))) ||
               name.matches(Regex("vec[234][fiuh]"))
    }

    /**
     * Check if a name is a builtin matrix type (matCxR).
     */
    fun isBuiltinMatrixType(name: String): Boolean {
        return name.startsWith("mat")
    }

    /**
     * Parse a builtin vector type name (e.g., "vec2<f32>") into its components.
     * Returns (size, elementTypeName) or null if not a vector type.
     */
    fun parseBuiltinVectorType(name: String): Pair<Int, String>? {
        // Shorthand match vecN[fiuh]
        val shorthandRegex = Regex("vec([234])([fiuh])")
        val shorthandMatch = shorthandRegex.matchEntire(name)
        if (shorthandMatch != null) {
            val size = shorthandMatch.groupValues[1].toInt()
            val suffix = shorthandMatch.groupValues[2]
            val elementType = when (suffix) {
                "f" -> "f32"
                "i" -> "i32"
                "u" -> "u32"
                "h" -> "f16"
                else -> "f32"
            }
            return Pair(size, elementType)
        }

        val vectorRegex = Regex("vec(\\d+)<(.+)>")
        val match = vectorRegex.matchEntire(name)
        if (match != null) {
            val size = match.groupValues[1].toIntOrNull()
            val elementType = match.groupValues[2]
            if (size != null && size in 2..4) {
                return Pair(size, elementType)
            }
        }
        return null
    }

    /**
     * Parse a builtin matrix type name (e.g., "mat2x3<f32>") into its components.
     * Returns (columns, rows, elementTypeName) or null if not a matrix type.
     */
    fun parseBuiltinMatrixType(name: String): Triple<Int, Int, String>? {
        // Shorthand match matNxM[fh]
        val shorthandRegex = Regex("mat([234])x([234])([fh])")
        val shorthandMatch = shorthandRegex.matchEntire(name)
        if (shorthandMatch != null) {
            val cols = shorthandMatch.groupValues[1].toInt()
            val rows = shorthandMatch.groupValues[2].toInt()
            val suffix = shorthandMatch.groupValues[3]
            val elementType = when (suffix) {
                "f" -> "f32"
                "h" -> "f16"
                else -> "f32"
            }
            return Triple(cols, rows, elementType)
        }

        val matrixRegex = Regex("mat(\\d+)x(\\d+)<(.+)>")
        val match = matrixRegex.matchEntire(name)
        if (match != null) {
            val cols = match.groupValues[1].toIntOrNull()
            val rows = match.groupValues[2].toIntOrNull()
            val elementType = match.groupValues[3]
            if (cols != null && rows != null) {
                return Triple(cols, rows, elementType)
            }
        }
        return null
    }

    /**
     * Get all declared names (types and values).
     */
    fun getAllDeclaredNames(): Set<String> {
        val names = mutableSetOf<String>()
        names.addAll(structs.keys)
        names.addAll(enums.keys)
        names.addAll(typeAliases.keys)
        names.addAll(functions.keys)
        names.addAll(globalVariables.keys)
        names.addAll(globalConstants.keys)
        return names
    }

    /**
     * Check if a name is already declared.
     */
    fun isDeclared(name: String): Boolean = getAllDeclaredNames().contains(name)
}
