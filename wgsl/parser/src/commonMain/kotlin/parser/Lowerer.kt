package io.ygdrasil.wgsl.parser

import io.ygdrasil.wgsl.ast.*
import io.ygdrasil.wgsl.arena.Handle
import io.ygdrasil.wgsl.arena.Arena
import io.ygdrasil.wgsl.arena.rangeOf
import io.ygdrasil.wgsl.ir.ScalarKind as IrScalarKind
import io.ygdrasil.wgsl.ir.Block as IrBlock
import io.ygdrasil.wgsl.ir.Expression as IrExpression
import io.ygdrasil.wgsl.ir.Statement as IrStatement
import io.ygdrasil.wgsl.ir.Function as IrFunction
import io.ygdrasil.wgsl.ir.Type as IrType
import io.ygdrasil.wgsl.ir.GlobalVariable as IrGlobalVariable
import io.ygdrasil.wgsl.ir.LocalVariable as IrLocalVariable
import io.ygdrasil.wgsl.ir.Module as IrModule
import io.ygdrasil.wgsl.ir.TypeInner as IrTypeInner
import io.ygdrasil.wgsl.ir.ArraySize as IrArraySize
import io.ygdrasil.wgsl.ir.VectorSize as IrVectorSize
import io.ygdrasil.wgsl.ir.AddressSpace as IrAddressSpace
import io.ygdrasil.wgsl.ir.StorageClass as IrStorageClass
import io.ygdrasil.wgsl.ir.AccessMode as IrAccessMode
import io.ygdrasil.wgsl.ir.EntryPoint as IrEntryPoint
import io.ygdrasil.wgsl.ir.ShaderStage as IrShaderStage
import io.ygdrasil.wgsl.ir.FunctionParameter as IrFunctionParameter
import io.ygdrasil.wgsl.ir.StructMember as IrStructMember
import io.ygdrasil.wgsl.ir.ScalarValue as IrScalarValue
import io.ygdrasil.wgsl.ir.ExpressionKind as IrExpressionKind
import io.ygdrasil.wgsl.ir.LiteralValue as IrLiteralValue
import io.ygdrasil.wgsl.ir.BinaryOperator as IrBinaryOperator
import io.ygdrasil.wgsl.ir.UnaryOperator as IrUnaryOperator
import io.ygdrasil.wgsl.ir.isComparison

/**
 * Error thrown during lowering when something cannot be resolved.
 */
class LoweringError(message: String) : RuntimeException(message)

/**
 * Lowers a resolved WGSL AST to the IR (Module).
 */
class Lowerer {

    private lateinit var module: IrModule
    private val typeMap = mutableMapOf<TypeDecl, Handle<IrType>>()
    private val structNameMap = mutableMapOf<String, Handle<IrType>>()
    private val typeAliasMap = mutableMapOf<String, Handle<IrType>>()
    private val globalVarMap = mutableMapOf<String, Handle<IrGlobalVariable>>()
    private val functionMap = mutableMapOf<String, Handle<IrFunction>>()

    private var currentExpressions: Arena<IrExpression>? = null
    private var currentBlocks: Arena<IrBlock>? = null
    private var currentLocalVars: Arena<IrLocalVariable>? = null
    private val localVariablesMap = mutableMapOf<String, Handle<IrLocalVariable>>()
    private val functionParamsMap = mutableMapOf<String, Int>()
    private val structMemberIndexMap = mutableMapOf<String, Map<String, UInt>>()
    private val structHandleToNameMap = mutableMapOf<Handle<IrType>, String>()
    private var currentFunction: Handle<IrFunction>? = null

    fun lower(unit: TranslationUnit): IrModule {
        // Create a fresh module for this lowering pass
        module = IrModule()
        
        // Reset all state for a new lowering pass
        typeMap.clear()
        structNameMap.clear()
        typeAliasMap.clear()
        globalVarMap.clear()
        functionMap.clear()
        localVariablesMap.clear()
        functionParamsMap.clear()
        structMemberIndexMap.clear()
        structHandleToNameMap.clear()
        currentExpressions = null
        currentBlocks = null
        currentLocalVars = null
        
        // 1. Lower structs and types
        for (decl in unit.declarations) {
            when (decl) {
                is StructDecl -> lowerStruct(decl)
                is TypeAliasDecl -> lowerTypeAlias(decl)
                is EnumDecl -> {}
                else -> {}
            }
        }

        // 2. Lower global variables and overrides
        for (decl in unit.declarations) {
            when (decl) {
                is VariableDecl -> lowerGlobalVariable(decl)
                is OverrideDecl -> lowerOverride(decl)
                else -> {}
            }
        }

        // 3. Lower functions and identify entry points
        for (decl in unit.declarations) {
            if (decl is FunctionDecl) {
                val functionHandle = lowerFunction(decl)
                
                // Identify entry points
                for (attr in decl.attributes) {
                    val stage = when (attr.name) {
                        "vertex" -> IrShaderStage.Vertex
                        "fragment" -> IrShaderStage.Fragment
                        "compute" -> IrShaderStage.Compute
                        else -> null
                    }
                    
                    if (stage != null) {
                        module.entryPoints.add(IrEntryPoint(
                            name = decl.name,
                            function = functionHandle,
                            stage = stage,
                        ))
                    }
                }
            }
        }

        return module
    }

    private fun lowerType(typeDecl: TypeDecl): Handle<IrType> {
        // Check if we have a struct by name
        val name = when (typeDecl) {
            is StructType -> typeDecl.name
            is NamedType -> typeDecl.name
            else -> null
        }
        if (name != null) {
            val handle = structNameMap[name] ?: typeAliasMap[name]
            if (handle != null) {
                return handle
            }
        }
        
        return typeMap.getOrPut(typeDecl) {
            val inner = when (typeDecl) {
                is ScalarType -> {
                    IrTypeInner.Scalar(lowerScalarKind(typeDecl.kind), lowerScalarWidth(typeDecl.kind))
                }
                is VectorType -> IrTypeInner.Vector(lowerVectorSize(typeDecl.size), lowerType(typeDecl.elementType))
                is MatrixType -> IrTypeInner.Matrix(lowerVectorSize(typeDecl.columns), lowerVectorSize(typeDecl.rows), lowerType(typeDecl.elementType))
                is ArrayType -> IrTypeInner.Array(lowerType(typeDecl.elementType), lowerArraySize(typeDecl.length))
                is StructType -> {
                    throw LoweringError("Unknown struct type: '${typeDecl.name}'")
                }
                is PointerType -> IrTypeInner.Pointer(
                    lowerType(typeDecl.elementType),
                    lowerAddressSpace(typeDecl.storageClass),
                    lowerAccessModeText(typeDecl.accessMode)
                )
                is NamedType -> {
                    val name = typeDecl.name
                    when {
                        name == "f32" -> IrTypeInner.Scalar(IrScalarKind.F32, 4)
                        name == "f16" -> IrTypeInner.Scalar(IrScalarKind.F16, 2)
                        name == "f64" -> IrTypeInner.Scalar(IrScalarKind.F64, 8)
                        name == "i8" -> IrTypeInner.Scalar(IrScalarKind.Sint, 1)
                        name == "u8" -> IrTypeInner.Scalar(IrScalarKind.Uint, 1)
                        name == "i16" -> IrTypeInner.Scalar(IrScalarKind.Sint, 2)
                        name == "u16" -> IrTypeInner.Scalar(IrScalarKind.Uint, 2)
                        name == "i32" -> IrTypeInner.Scalar(IrScalarKind.Sint, 4)
                        name == "u32" -> IrTypeInner.Scalar(IrScalarKind.Uint, 4)
                        name == "i64" -> IrTypeInner.Scalar(IrScalarKind.Sint, 8)
                        name == "u64" -> IrTypeInner.Scalar(IrScalarKind.Uint, 8)
                        name == "bool" -> IrTypeInner.Scalar(IrScalarKind.Bool, 1)
                        name == "acceleration_structure" || name == "ray_query" || name == "RayDesc" || name == "RayIntersection" -> {
                            IrTypeInner.Opaque(name)
                        }
                        else -> lowerBuiltinShorthandType(name)
                            ?: throw LoweringError("Unknown named type: '$name'")
                    }
                }
                is AbstractIntType -> IrTypeInner.Abstract(IrScalarKind.AbstractInt)
                is AbstractFloatType -> IrTypeInner.Abstract(IrScalarKind.AbstractFloat)
                is TemplateType -> {
                    val name = typeDecl.name
                    when (name) {
                        "array" -> {
                            val elemType = typeDecl.args.getOrNull(0)?.let { lowerType(it) }
                                ?: throw LoweringError("array template requires an element type")
                            val arraySize = typeDecl.args.getOrNull(1)?.let { lowerArraySizeArgument(it) } ?: IrArraySize.Dynamic(Handle(0))
                            IrTypeInner.Array(elemType, arraySize)
                        }
                        "vec2", "vec3", "vec4" -> {
                            val size = name.substring(3).toInt()
                            val elemType = typeDecl.args.getOrNull(0)?.let { lowerType(it) }
                                ?: throw LoweringError("$name template requires an element type")
                            IrTypeInner.Vector(lowerVectorSize(size), elemType)
                        }
                        "mat2x2", "mat2x3", "mat2x4", "mat3x2", "mat3x3", "mat3x4", "mat4x2", "mat4x3", "mat4x4" -> {
                            val cols = name.substring(3, 4).toInt()
                            val rows = name.substring(5, 6).toInt()
                            val elemType = typeDecl.args.getOrNull(0)?.let { lowerType(it) }
                                ?: throw LoweringError("$name template requires an element type")
                            IrTypeInner.Matrix(lowerVectorSize(cols), lowerVectorSize(rows), elemType)
                        }
                        "atomic" -> {
                            val elemType = typeDecl.args.getOrNull(0)?.let { lowerType(it) }
                                ?: throw LoweringError("atomic template requires an element type")
                            module.types[elemType].inner
                        }
                        else -> throw LoweringError("Unsupported template type: '$name'")
                    }
                }
                is AtomicType -> {
                    val elemType = lowerType(typeDecl.elementType)
                    module.types[elemType].inner
                }
                is SamplerType -> {
                    val name = if (typeDecl.isComparison) "sampler_comparison" else "sampler"
                    IrTypeInner.Opaque(name)
                }
                is TextureType -> {
                    val kindName = when (typeDecl.kind) {
                        TextureKind.TEXTURE_1D -> "texture_1d"
                        TextureKind.TEXTURE_1D_ARRAY -> "texture_1d_array"
                        TextureKind.TEXTURE_2D -> "texture_2d"
                        TextureKind.TEXTURE_2D_ARRAY -> "texture_2d_array"
                        TextureKind.TEXTURE_3D -> "texture_3d"
                        TextureKind.TEXTURE_CUBE -> "texture_cube"
                        TextureKind.TEXTURE_CUBE_ARRAY -> "texture_cube_array"
                        TextureKind.TEXTURE_MULTISAMPLED_2D -> "texture_multisampled_2d"
                        TextureKind.TEXTURE_DEPTH_2D -> "texture_depth_2d"
                        TextureKind.TEXTURE_DEPTH_2D_ARRAY -> "texture_depth_2d_array"
                        TextureKind.TEXTURE_DEPTH_CUBE -> "texture_depth_cube"
                        TextureKind.TEXTURE_DEPTH_CUBE_ARRAY -> "texture_depth_cube_array"
                        TextureKind.TEXTURE_DEPTH_MULTISAMPLED_2D -> "texture_depth_multisampled_2d"
                        TextureKind.TEXTURE_EXTERNAL -> "texture_external"
                        TextureKind.TEXTURE_STORAGE_1D -> "texture_storage_1d"
                        TextureKind.TEXTURE_STORAGE_2D -> "texture_storage_2d"
                        TextureKind.TEXTURE_STORAGE_2D_ARRAY -> "texture_storage_2d_array"
                        TextureKind.TEXTURE_STORAGE_3D -> "texture_storage_3d"
                    }
                    val fullTextureName = buildString {
                        append(kindName)
                        if (typeDecl.elementType != null || typeDecl.accessMode != null) {
                            append("<")
                            val parts = mutableListOf<String>()
                            typeDecl.elementType?.let { parts.add(getTypeDeclName(it)) }
                            typeDecl.accessMode?.let { parts.add(it) }
                            append(parts.joinToString(", "))
                            append(">")
                        }
                    }
                    IrTypeInner.Opaque(fullTextureName)
                }
                is RayQueryType -> {
                    IrTypeInner.Opaque("ray_query")
                }
                is ReferenceType -> IrTypeInner.ValuePointer(lowerType(typeDecl.elementType))
                is EnumType -> IrTypeInner.Opaque(typeDecl.name)
                else -> throw LoweringError("Unsupported type declaration: ${typeDecl::class.simpleName}")
            }
            module.types.append(IrType(inner))
        }
    }

    private fun lowerArraySize(length: Expression?): IrArraySize {
        return when (length) {
            null -> IrArraySize.Dynamic(Handle(0))
            is IntLiteral -> IrArraySize.Constant(length.value.toInt())
            is IdentExpr -> length.name.toIntOrNull()?.let { IrArraySize.Constant(it) } ?: IrArraySize.Dynamic(Handle(0))
            else -> IrArraySize.Dynamic(Handle(0))
        }
    }

    private fun lowerArraySizeArgument(typeDecl: TypeDecl): IrArraySize {
        return when (typeDecl) {
            is ConstantType -> lowerArraySize(typeDecl.expression)
            is NamedType -> typeDecl.name.toIntOrNull()?.let { IrArraySize.Constant(it) } ?: IrArraySize.Dynamic(Handle(0))
            else -> IrArraySize.Dynamic(Handle(0))
        }
    }

    private fun lowerBuiltinShorthandType(name: String): IrTypeInner? {
        val vectorMatch = Regex("""vec([234])([fiuh])?""").matchEntire(name)
        if (vectorMatch != null) {
            val size = vectorMatch.groupValues[1].toInt()
            val scalar = lowerShorthandScalar(vectorMatch.groupValues.getOrNull(2).orEmpty())
            return IrTypeInner.Vector(lowerVectorSize(size), lowerType(ScalarType(scalar, io.ygdrasil.wgsl.ir.Span.UNDEFINED)))
        }

        val matrixMatch = Regex("""mat([234])x([234])([fh])?""").matchEntire(name)
        if (matrixMatch != null) {
            val cols = matrixMatch.groupValues[1].toInt()
            val rows = matrixMatch.groupValues[2].toInt()
            val scalar = lowerShorthandScalar(matrixMatch.groupValues.getOrNull(3).orEmpty())
            return IrTypeInner.Matrix(lowerVectorSize(cols), lowerVectorSize(rows), lowerType(ScalarType(scalar, io.ygdrasil.wgsl.ir.Span.UNDEFINED)))
        }

        return null
    }

    private fun lowerShorthandScalar(suffix: String): ScalarKind {
        return when (suffix) {
            "", "f" -> ScalarKind.F32
            "h" -> ScalarKind.F16
            "i" -> ScalarKind.I32
            "u" -> ScalarKind.U32
            else -> throw LoweringError("Unsupported shorthand scalar suffix: '$suffix'")
        }
    }

    private fun isBuiltinConstructorType(name: String): Boolean {
        return name in setOf(
            "bool",
            "i8", "u8",
            "i16", "u16",
            "i32", "u32",
            "i64", "u64",
            "f16", "f32", "f64",
            "array",
        ) || Regex("""vec([234])([fiuh])?""").matches(name) ||
                Regex("""mat([234])x([234])([fh])?""").matches(name)
    }

    private fun getTypeDeclName(typeDecl: TypeDecl): String {
        return when (typeDecl) {
            is ScalarType -> typeDecl.kind.name.lowercase()
            is VectorType -> "vec${typeDecl.size}<${getTypeDeclName(typeDecl.elementType)}>"
            is MatrixType -> "mat${typeDecl.columns}x${typeDecl.rows}<${getTypeDeclName(typeDecl.elementType)}>"
            is ArrayType -> "array<${getTypeDeclName(typeDecl.elementType)}${typeDecl.length?.let { ", $it" } ?: ""}>"
            is NamedType -> typeDecl.name
            is PointerType -> "ptr<${typeDecl.storageClass.name.lowercase()}, ${getTypeDeclName(typeDecl.elementType)}>"
            is TemplateType -> "${typeDecl.name}<${typeDecl.args.joinToString(", ") { getTypeDeclName(it) }}>"
            is AtomicType -> "atomic<${getTypeDeclName(typeDecl.elementType)}>"
            is SamplerType -> if (typeDecl.isComparison) "sampler_comparison" else "sampler"
            is TextureType -> {
                val kindName = typeDecl.kind.name.lowercase()
                buildString {
                    append(kindName)
                    if (typeDecl.elementType != null || typeDecl.accessMode != null) {
                        append("<")
                        val parts = mutableListOf<String>()
                        typeDecl.elementType?.let { parts.add(getTypeDeclName(it)) }
                        typeDecl.accessMode?.let { parts.add(it) }
                        append(parts.joinToString(", "))
                        append(">")
                    }
                }
            }
            is RayQueryType -> "ray_query"
            is AbstractIntType -> "abstract_int"
            is AbstractFloatType -> "abstract_float"
            is StructType -> typeDecl.name
            is EnumType -> typeDecl.name
            else -> throw LoweringError("Unsupported type declaration name: ${typeDecl::class.simpleName}")
        }
    }

    private fun lowerScalarKind(kind: ScalarKind): IrScalarKind = when (kind) {
        ScalarKind.BOOL -> IrScalarKind.Bool
        ScalarKind.U8, ScalarKind.U16, ScalarKind.U32, ScalarKind.U64 -> IrScalarKind.Uint
        ScalarKind.I8, ScalarKind.I16, ScalarKind.I32, ScalarKind.I64 -> IrScalarKind.Sint
        ScalarKind.F16 -> IrScalarKind.F16
        ScalarKind.F32 -> IrScalarKind.F32
        ScalarKind.F64 -> IrScalarKind.F64
    }

    private fun lowerScalarWidth(kind: ScalarKind): Int = when (kind) {
        ScalarKind.BOOL -> 1
        ScalarKind.I8, ScalarKind.U8 -> 1
        ScalarKind.I16, ScalarKind.U16, ScalarKind.F16 -> 2
        ScalarKind.I32, ScalarKind.U32, ScalarKind.F32 -> 4
        ScalarKind.I64, ScalarKind.U64, ScalarKind.F64 -> 8
    }

    private fun lowerVectorSize(size: Int): IrVectorSize = when (size) {
        2 -> IrVectorSize.Bi
        3 -> IrVectorSize.Tri
        4 -> IrVectorSize.Quad
        else -> throw LoweringError("Unsupported vector size: $size")
    }

    private fun lowerAddressSpace(storageClass: io.ygdrasil.wgsl.ast.StorageClass): IrAddressSpace = when (storageClass) {
        io.ygdrasil.wgsl.ast.StorageClass.FUNCTION -> IrAddressSpace.Function
        io.ygdrasil.wgsl.ast.StorageClass.PRIVATE -> IrAddressSpace.Private
        io.ygdrasil.wgsl.ast.StorageClass.WORKGROUP -> IrAddressSpace.Workgroup
        io.ygdrasil.wgsl.ast.StorageClass.UNIFORM -> IrAddressSpace.Uniform
        io.ygdrasil.wgsl.ast.StorageClass.STORAGE -> IrAddressSpace.Storage
        io.ygdrasil.wgsl.ast.StorageClass.HANDLE -> IrAddressSpace.Private // Fallback
        io.ygdrasil.wgsl.ast.StorageClass.PUSH_CONSTANT -> IrAddressSpace.Private // Fallback
    }

    private fun lowerStorageClass(storageClass: io.ygdrasil.wgsl.ast.StorageClass): IrStorageClass = when (storageClass) {
        io.ygdrasil.wgsl.ast.StorageClass.FUNCTION -> IrStorageClass.Function
        io.ygdrasil.wgsl.ast.StorageClass.PRIVATE -> IrStorageClass.Private
        io.ygdrasil.wgsl.ast.StorageClass.WORKGROUP -> IrStorageClass.Workgroup
        io.ygdrasil.wgsl.ast.StorageClass.UNIFORM -> IrStorageClass.Uniform
        io.ygdrasil.wgsl.ast.StorageClass.STORAGE -> IrStorageClass.Storage
        io.ygdrasil.wgsl.ast.StorageClass.HANDLE -> IrStorageClass.Handle
        io.ygdrasil.wgsl.ast.StorageClass.PUSH_CONSTANT -> IrStorageClass.PushConstant
    }

    private fun lowerBuiltinBinding(name: String?): io.ygdrasil.wgsl.ir.BindingAttribute.Builtin? {
        val builtin = when (name) {
            "draw_index" -> io.ygdrasil.wgsl.ir.BuiltinValue.DrawIndex
            else -> io.ygdrasil.wgsl.ir.BuiltinValue.entries.find { it.name.lowercase() == name?.lowercase() }
        }
        return builtin?.let { io.ygdrasil.wgsl.ir.BindingAttribute.Builtin(it) }
    }

    private fun lowerStruct(decl: StructDecl) {
        val members = decl.members.map { member ->
            val binding = member.attributes.find { it.name == "location" }?.let {
                val loc = (it.args.firstOrNull() as? IntLiteral)?.value?.toInt() ?: 0
                io.ygdrasil.wgsl.ir.BindingAttribute.Location(loc)
            } ?: member.attributes.find { it.name == "builtin" }?.let {
                val builtinName = (it.args.firstOrNull() as? IdentExpr)?.name
                lowerBuiltinBinding(builtinName)
            }

            IrStructMember(
                name = member.name,
                type = lowerType(member.type),
                binding = binding,
                offset = 0
            )
        }
        val type = IrType(IrTypeInner.Struct(members))
        val handle = module.types.append(type)
        
        // Peupler structMemberIndexMap
        structMemberIndexMap[decl.name] = members.withIndex().associate { (idx, member) ->
            member.name to idx.toUInt()
        }
        
        // Peupler structHandleToNameMap pour résoudre le nom à partir du handle
        structHandleToNameMap[handle] = decl.name
        
        typeMap[StructType(decl.name, decl.span)] = handle
        structNameMap[decl.name] = handle
    }

    private fun lowerTypeAlias(decl: TypeAliasDecl) {
        val handle = lowerType(decl.type)
        typeAliasMap[decl.name] = handle
        typeMap[NamedType(decl.name, decl.span)] = handle
    }

    private fun lowerGlobalVariable(decl: VariableDecl) {
        val initHandle = decl.initializer?.let { initializerExpr ->
            val savedExpressions = currentExpressions
            try {
                currentExpressions = module.globalExpressions
                lowerExpression(initializerExpr, decl.type?.let { lowerType(it) })
            } finally {
                currentExpressions = savedExpressions
            }
        }

        val type = decl.type?.let { lowerType(it) }
            ?: initHandle?.let { handle ->
                val savedExpressions = currentExpressions
                try {
                    currentExpressions = module.globalExpressions
                    resolveExpressionType(handle)
                } finally {
                    currentExpressions = savedExpressions
                }
            }
            ?: return

        val storageClass = if (decl.storageClass != null) {
            lowerStorageClassText(decl.storageClass)
        } else {
            lowerStorageClass(decl.kind.toStorageClass())
        }

        val accessMode = lowerAccessModeText(decl.accessMode)

        val group = decl.attributes.find { it.name == "group" }?.let {
            (it.args.firstOrNull() as? IntLiteral)?.value?.toInt()
        }
        val bindingIndex = decl.attributes.find { it.name == "binding" }?.let {
            (it.args.firstOrNull() as? IntLiteral)?.value?.toInt()
        }
        val binding = if (group != null && bindingIndex != null) {
            io.ygdrasil.wgsl.ir.Binding(group, bindingIndex)
        } else {
            null
        }

        val variable = IrGlobalVariable(
            name = decl.name,
            storageClass = storageClass,
            accessMode = accessMode,
            binding = binding,
            type = type,
            `init` = initHandle
        )
        globalVarMap[decl.name] = module.globalVariables.append(variable)
    }

    private fun lowerOverride(decl: OverrideDecl) {
        val savedExpressions = currentExpressions
        val initHandle = try {
            currentExpressions = module.globalExpressions
            decl.initializer?.let { lowerExpression(it) }
        } catch (e: Exception) {
            throw e
        } finally {
            currentExpressions = savedExpressions
        }

        val type = decl.type?.let { lowerType(it) }
            ?: initHandle?.let { handle ->
                val saved = currentExpressions
                try {
                    currentExpressions = module.globalExpressions
                    resolveExpressionType(handle)
                } finally {
                    currentExpressions = saved
                }
            }
            ?: return

        val storageClass = IrStorageClass.Private
        val accessMode = IrAccessMode.Read

        val variable = IrGlobalVariable(
            name = decl.name,
            storageClass = storageClass,
            accessMode = accessMode,
            binding = null,
            type = type,
            `init` = initHandle
        )
        globalVarMap[decl.name] = module.globalVariables.append(variable)
    }

    private fun lowerStorageClassText(text: String?): IrStorageClass = when (text) {
        "storage" -> IrStorageClass.Storage
        "uniform" -> IrStorageClass.Uniform
        "workgroup" -> IrStorageClass.Workgroup
        "private" -> IrStorageClass.Private
        "function" -> IrStorageClass.Function
        "push_constant" -> IrStorageClass.PushConstant
        else -> IrStorageClass.Private
    }

    private fun lowerAccessModeText(text: String?): IrAccessMode? = when (text) {
        "read" -> IrAccessMode.Read
        "write" -> IrAccessMode.Write
        "read_write" -> IrAccessMode.ReadWrite
        else -> null
    }

    private fun VariableDeclKind.toStorageClass(): io.ygdrasil.wgsl.ast.StorageClass = when (this) {
        VariableDeclKind.VAR -> io.ygdrasil.wgsl.ast.StorageClass.PRIVATE
        VariableDeclKind.LET -> io.ygdrasil.wgsl.ast.StorageClass.FUNCTION
        VariableDeclKind.CONST -> io.ygdrasil.wgsl.ast.StorageClass.PRIVATE
    }

    private fun lowerFunction(decl: FunctionDecl): Handle<IrFunction> {
        val expressions = Arena<IrExpression>()
        val blocks = Arena<IrBlock>()
        val localVars = Arena<IrLocalVariable>()
        
        currentExpressions = expressions
        currentBlocks = blocks
        currentLocalVars = localVars
        localVariablesMap.clear()
        functionParamsMap.clear()

        val parameters = decl.parameters.mapIndexed { index, param ->
            functionParamsMap[param.name] = index
            val binding = param.attributes.find { it.name == "location" }?.let {
                val loc = (it.args.firstOrNull() as? IntLiteral)?.value?.toInt() ?: 0
                io.ygdrasil.wgsl.ir.BindingAttribute.Location(loc)
            } ?: param.attributes.find { it.name == "builtin" }?.let {
                val builtinName = (it.args.firstOrNull() as? IdentExpr)?.name
                lowerBuiltinBinding(builtinName)
            }

            IrFunctionParameter(
                name = param.name,
                type = lowerType(param.type),
                binding = binding
            )
        }

        // Set current function before lowering body
        val func = IrFunction(
            name = decl.name,
            parameters = parameters,
            returnType = decl.returnType?.let { lowerType(it) },
            expressions = expressions,
            localVariables = localVars,
            blocks = blocks,
            body = blocks.append(IrBlock(emptyList())) // Placeholder, will be updated
        )
        
        val handle = module.functions.append(func)
        currentFunction = handle
        functionMap[decl.name] = handle
        
        // Now lower the body with currentFunction set
        val bodyHandle = if (decl.body != null) {
            lowerBlock(decl.body)
        } else {
            blocks.append(IrBlock(emptyList()))
        }
        
        // Update the function body
        module.functions[handle] = func.copy(body = bodyHandle)
        
        currentFunction = null
        currentExpressions = null
        currentBlocks = null
        currentLocalVars = null
        
        return handle
    }

    private fun lowerBlock(astBlock: BlockStatement): Handle<IrBlock> {
        val statements = astBlock.statements.map { lowerStatement(it) }
        return currentBlocks!!.append(IrBlock(statements))
    }

    private fun lowerStatement(astStmt: Statement): IrStatement {
        return when (astStmt) {
            is ReturnStatement -> {
                val expectedReturnType = currentFunction?.let { module.functions[it].returnType }
                IrStatement.Return(astStmt.value?.let { lowerExpression(it, expectedReturnType) })
            }
            is AssignmentStatement -> {
                val lhs = astStmt.lhs
                if (lhs is UnaryExpr && lhs.op == io.ygdrasil.wgsl.ast.UnaryOperator.DEREF) {
                    IrStatement.Assign(lowerExpression(lhs.operand), lowerExpression(astStmt.rhs))
                } else {
                    IrStatement.Assign(lowerExpression(lhs), lowerExpression(astStmt.rhs))
                }
            }
            is IfStatement -> {
                val cond = lowerExpression(astStmt.condition)
                val accept = lowerBlock(astStmt.thenBranch as? BlockStatement ?: BlockStatement(listOf(astStmt.thenBranch), astStmt.thenBranch.span))
                val reject = astStmt.elseBranch?.let { 
                    lowerBlock(it as? BlockStatement ?: BlockStatement(listOf(it), it.span))
                }
                IrStatement.If(cond, accept, reject)
            }
            is BlockStatement -> IrStatement.Block(lowerBlock(astStmt))
            is LoopStatement -> {
                val bodyBlock = lowerBlock(astStmt.body)
                val continuingBlock = astStmt.continuing?.let { lowerBlock(it) }
                IrStatement.Loop(bodyBlock, continuingBlock)
            }
            is BreakIfStatement -> {
                val condition = lowerExpression(astStmt.condition)
                val breakBlock = currentBlocks!!.append(IrBlock(listOf(IrStatement.Break)))
                IrStatement.If(condition, breakBlock)
            }
            is ConstAssertStatement -> {
                // Const assertions are evaluated at compile time and produce no runtime code.
                // We still lower the expression to ensure it's valid, but we don't emit any statement.
                lowerExpression(astStmt.expression)
                IrStatement.Nop
            }
            is VariableDeclStatement -> {
                val explicitType = astStmt.type?.let { lowerType(it) }
                val initHandle = astStmt.initializer?.let { lowerExpression(it, explicitType) }
                    val type = explicitType
                        ?: initHandle?.let { resolveExpressionType(it) }
                    ?: lowerInferredType()
                val localVar = IrLocalVariable(
                    name = astStmt.name,
                    type = type,
                    init = initHandle
                )
                val handle = currentLocalVars!!.append(localVar)
                localVariablesMap[astStmt.name] = handle
                if (astStmt.initializer != null) {
                    IrStatement.Init(handle)
                } else {
                    IrStatement.Declare(handle)
                }
            }
            is WhileStatement -> {
                // P008 fix: Handle while loops
                val condition = lowerExpression(astStmt.condition)
                
                // Create body block with condition check
                // The IR Loop statement expects a body block that contains the loop body
                // The continuing block is optional and runs before checking the condition again
                val bodyBlockHandle = lowerBlock(astStmt.body)
                
                // Create a block that checks the condition and breaks if false
                val conditionCheckBlock = currentBlocks!!.append(
                    IrBlock(listOf(
                        IrStatement.If(
                            condition,
                            bodyBlockHandle,
                            currentBlocks!!.append(IrBlock(listOf(IrStatement.Break)))
                        )
                    ))
                )
                
                IrStatement.Loop(conditionCheckBlock)
            }
            is ForStatement -> {
                // P008 fix: Handle for loops
                // for (init; condition; update) body
                // Lower as: init; while(condition) { body; update; }
                
                val statements = mutableListOf<IrStatement>()
                
                // Lower init if present
                astStmt.init?.let { initStmt ->
                    statements.add(lowerStatement(initStmt))
                }
                
                // Create the body with update at the end
                val bodyStatements = mutableListOf<IrStatement>()
                
                // Add the original body
                val bodyBlock = lowerBlock(astStmt.body)
                bodyStatements.add(IrStatement.Block(bodyBlock))
                
                // Add update if present
                astStmt.update?.let { update ->
                    bodyStatements.add(lowerStatement(update))
                }
                
                // Create the body block for the loop
                val loopBodyBlock = currentBlocks!!.append(IrBlock(bodyStatements))
                
                // Create the condition check
                val condition = astStmt.condition?.let { lowerExpression(it) }
                    ?: currentExpressions!!.append(IrExpression(IrExpressionKind.Literal(IrLiteralValue.Scalar(IrScalarValue.Bool(true)))))
                
                // Create the loop with condition check
                val conditionCheckBlock = currentBlocks!!.append(
                    IrBlock(listOf(
                        IrStatement.If(
                            condition,
                            loopBodyBlock,
                            currentBlocks!!.append(IrBlock(listOf(IrStatement.Break)))
                        )
                    ))
                )
                
                statements.add(IrStatement.Loop(conditionCheckBlock))
                
                // If we have multiple statements (init + loop), we need to wrap them in a block
                if (statements.size == 1) {
                    statements.first()
                } else {
                    IrStatement.Block(currentBlocks!!.append(IrBlock(statements)))
                }
            }
            is BreakStatement -> {
                // P009: Handle break statement
                IrStatement.Break
            }
            is ContinueStatement -> {
                // P009: Handle continue statement  
                IrStatement.Continue
            }
            is ExpressionStatement -> {
                IrStatement.Emit(rangeOf(lowerExpression(astStmt.expr)))
            }
            is PhonyAssignmentStatement -> {
                IrStatement.Emit(rangeOf(lowerExpression(astStmt.expression)))
            }
            is DiagnosticStatement -> IrStatement.Nop
            is DiscardStatement -> IrStatement.Discard
            is SwitchStatement -> {
                val selector = lowerExpression(astStmt.expression)
                
                var defaultBlock: Handle<io.ygdrasil.wgsl.ir.Block>? = null
                val irCases = mutableListOf<io.ygdrasil.wgsl.ir.Case>()
                
                for (case in astStmt.body.cases) {
                    when (case) {
                        is DefaultCase -> {
                            defaultBlock = lowerBlock(case.body)
                            irCases.add(io.ygdrasil.wgsl.ir.Case(io.ygdrasil.wgsl.ir.CaseSelector.Default(), defaultBlock))
                        }
                        is Case -> {
                            val bodyHandle = lowerBlock(case.body)
                            
                            if (case.isDefault) {
                                defaultBlock = bodyHandle
                            }
                            
                            for (selectorExpr in case.selectors) {
                                val irSelector = when (selectorExpr) {
                                    is IntLiteral -> {
                                        val scalar = if (selectorExpr.suffix == "u") {
                                            IrScalarValue.U32(selectorExpr.value)
                                        } else {
                                            IrScalarValue.I32(selectorExpr.value.toInt())
                                        }
                                        io.ygdrasil.wgsl.ir.CaseSelector.Value(scalar)
                                    }
                                    is BoolLiteral -> {
                                        io.ygdrasil.wgsl.ir.CaseSelector.Value(IrScalarValue.Bool(selectorExpr.value))
                                    }
                                    is IdentExpr -> {
                                        if (selectorExpr.name == "default") {
                                            io.ygdrasil.wgsl.ir.CaseSelector.Default()
                                        } else {
                                            throw LoweringError("Unsupported case selector identifier: '${selectorExpr.name}'")
                                        }
                                    }
                                    else -> throw LoweringError("Unsupported case selector expression type: ${selectorExpr::class.simpleName}")
                                }
                                
                                irCases.add(io.ygdrasil.wgsl.ir.Case(irSelector, bodyHandle))
                            }
                            
                            if (case.selectors.isEmpty() && case.isDefault) {
                                irCases.add(io.ygdrasil.wgsl.ir.Case(io.ygdrasil.wgsl.ir.CaseSelector.Default(), bodyHandle))
                            }
                        }
                    }
                }
                
                val switchBodyBlock = currentBlocks!!.append(io.ygdrasil.wgsl.ir.Block(emptyList()))
                IrStatement.Switch(selector, switchBodyBlock, defaultBlock, irCases)
            }
            is IncDecStatement -> {
                val pointer = lowerExpression(astStmt.expr)
                
                // 1. Resolve type of pointer
                val objExprKind = currentExpressions!![pointer].kind
                val objTypeHandle = when (objExprKind) {
                    is IrExpressionKind.LocalVar -> currentLocalVars!![objExprKind.handle].type
                    is IrExpressionKind.GlobalVar -> module.globalVariables[objExprKind.handle].type
                    is IrExpressionKind.FunctionArgument -> {
                        val func = currentFunction?.let { module.functions[it] }
                            ?: throw LoweringError("Function context missing for IncDecStatement argument")
                        func.parameters[objExprKind.index].type
                    }
                    else -> null
                }
                
                // 2. Choose correct 1 literal based on variable type
                val scalarValue = when (val inner = objTypeHandle?.let { module.types[it].inner }) {
                    is IrTypeInner.Scalar -> when (inner.kind) {
                        IrScalarKind.Uint -> IrScalarValue.U32(1L)
                        IrScalarKind.F32 -> IrScalarValue.F32(1.0f)
                        else -> IrScalarValue.I32(1)
                    }
                    else -> IrScalarValue.I32(1)
                }
                
                val oneExpr = currentExpressions!!.append(
                    IrExpression(IrExpressionKind.Literal(IrLiteralValue.Scalar(scalarValue)))
                )
                
                // 3. Create Assign statement with Binary addition/subtraction
                val op = if (astStmt.isIncrement) IrBinaryOperator.Add else IrBinaryOperator.Subtract
                val binaryExpr = currentExpressions!!.append(
                    IrExpression(IrExpressionKind.Binary(op, pointer, oneExpr))
                )
                
                IrStatement.Assign(pointer, binaryExpr)
            }
            else -> throw LoweringError("Unsupported statement type: ${astStmt::class.simpleName}")
        }
    }

    private fun lowerExpression(astExpr: Expression, expectedType: Handle<IrType>? = null): Handle<IrExpression> {
        // Code existant commence ici...
        val kind = when (astExpr) {
            is IntLiteral -> {
                val scalar = when (astExpr.suffix?.lowercase()) {
                    "u" -> IrScalarValue.U32(astExpr.value)
                    "li" -> IrScalarValue.I64(astExpr.value)
                    "lu" -> IrScalarValue.U64(astExpr.value.toULong())
                    else -> IrScalarValue.I32(astExpr.value.toInt())
                }
                IrExpressionKind.Literal(IrLiteralValue.Scalar(scalar))
            }
            is FloatLiteral -> IrExpressionKind.Literal(IrLiteralValue.Scalar(IrScalarValue.F32(astExpr.value.toFloat())))
            is BoolLiteral -> IrExpressionKind.Literal(IrLiteralValue.Scalar(IrScalarValue.Bool(astExpr.value)))
            is IdentExpr -> {
                val name = astExpr.name
                if (localVariablesMap.containsKey(name)) {
                    IrExpressionKind.LocalVar(localVariablesMap[name]!!)
                } else if (functionParamsMap.containsKey(name)) {
                    IrExpressionKind.FunctionArgument(functionParamsMap[name]!!)
                } else if (globalVarMap.containsKey(name)) {
                    IrExpressionKind.GlobalVar(globalVarMap[name]!!)
                } else if (name == "RAY_FLAG_NONE") {
                    IrExpressionKind.Literal(IrLiteralValue.Scalar(IrScalarValue.U32(0L)))
                } else if (name == "RAY_FLAG_FORCE_OPAQUE") {
                    IrExpressionKind.Literal(IrLiteralValue.Scalar(IrScalarValue.U32(1L)))
                } else if (name == "RAY_FLAG_FORCE_NO_OPAQUE") {
                    IrExpressionKind.Literal(IrLiteralValue.Scalar(IrScalarValue.U32(2L)))
                } else if (name == "RAY_FLAG_ACCEPT_FIRST_HIT") {
                    IrExpressionKind.Literal(IrLiteralValue.Scalar(IrScalarValue.U32(4L)))
                } else if (name == "RAY_FLAG_SKIP_CLOSEST_HIT") {
                    IrExpressionKind.Literal(IrLiteralValue.Scalar(IrScalarValue.U32(8L)))
                } else if (name == "RAY_FLAG_CULL_BACK_FACING") {
                    IrExpressionKind.Literal(IrLiteralValue.Scalar(IrScalarValue.U32(16L)))
                } else if (name == "RAY_FLAG_CULL_FRONT_FACING") {
                    IrExpressionKind.Literal(IrLiteralValue.Scalar(IrScalarValue.U32(32L)))
                } else if (name == "RAY_FLAG_CULL_OPAQUE") {
                    IrExpressionKind.Literal(IrLiteralValue.Scalar(IrScalarValue.U32(64L)))
                } else if (name == "RAY_FLAG_CULL_NO_OPAQUE") {
                    IrExpressionKind.Literal(IrLiteralValue.Scalar(IrScalarValue.U32(128L)))
                } else if (name == "RAY_FLAG_TERMINATE_ON_FIRST_HIT") {
                    IrExpressionKind.Literal(IrLiteralValue.Scalar(IrScalarValue.U32(256L)))
                } else {
                    // P004 fix: Throw error instead of silent fallback
                    throw LoweringError("Undefined variable: '$name'")
                }
            }
            is BinaryExpr -> IrExpressionKind.Binary(
                lowerBinaryOperator(astExpr.op),
                lowerExpression(astExpr.left),
                lowerExpression(astExpr.right)
            )
            is UnaryExpr -> {
                when (astExpr.op) {
                    io.ygdrasil.wgsl.ast.UnaryOperator.DEREF -> IrExpressionKind.Load(lowerExpression(astExpr.operand))
                    io.ygdrasil.wgsl.ast.UnaryOperator.ADDRESS_OF -> IrExpressionKind.ValuePointer(lowerExpression(astExpr.operand))
                    else -> IrExpressionKind.Unary(
                        lowerUnaryOperator(astExpr.op),
                        lowerExpression(astExpr.operand)
                    )
                }
            }
            is CallExpr -> {
                // Heuristic: if callee is IdentExpr and matches a function name, it's a call
                // If it matches a type name, it's a TypeConstructor
                val calleeName = (astExpr.callee as? IdentExpr)?.name
                val isBuiltinType = calleeName != null && isBuiltinConstructorType(calleeName)
                val isStructType = calleeName != null && structNameMap.containsKey(calleeName)
                val isAliasType = calleeName != null && typeAliasMap.containsKey(calleeName)
                
                if (calleeName != null && functionMap.containsKey(calleeName)) {
                    IrExpressionKind.Call(functionMap[calleeName]!!, astExpr.args.map { lowerExpression(it) })
                } else if (calleeName == "array" && astExpr.templateArgs == null) {
                    val loweredArgs = astExpr.args.map { lowerExpression(it) }
                    val expectedArray = expectedType?.let { module.types[it].inner as? IrTypeInner.Array }
                    if (expectedArray != null) {
                        val expectedSize = expectedArray.size
                        if (expectedSize is IrArraySize.Constant && expectedSize.value != loweredArgs.size) {
                            throw LoweringError(
                                "Array constructor argument count ${loweredArgs.size} does not match expected array size ${expectedSize.value}"
                            )
                        }
                    }
                    val elemType = expectedArray?.element ?: if (loweredArgs.isNotEmpty()) {
                        resolveExpressionType(loweredArgs[0])
                    } else {
                        throw LoweringError("Cannot infer element type for empty array constructor")
                    }
                    val type = expectedType?.takeIf { expectedArray != null } ?: run {
                        val arraySize = IrArraySize.Constant(loweredArgs.size)
                        val arrayTypeInner = IrTypeInner.Array(elemType, arraySize)
                        module.types.append(IrType(arrayTypeInner))
                    }
                    IrExpressionKind.TypeConstructor(type, loweredArgs)
                } else if (isBuiltinType || isStructType || isAliasType) {
                    val typeDecl = if (astExpr.templateArgs != null) {
                        TemplateType(calleeName, astExpr.templateArgs, astExpr.span)
                    } else {
                        NamedType(calleeName, astExpr.span)
                    }
                    val type = lowerType(typeDecl)
                    IrExpressionKind.TypeConstructor(type, astExpr.args.map { lowerExpression(it) })
                } else {
                    // It's a builtin function or unresolved function, emit Call instead of TypeConstructor
                    val funcName = calleeName ?: "unknown_func"
                    val loweredArgs = astExpr.args.map { lowerExpression(it) }
                    val stubExpressions = Arena<IrExpression>()
                    val stubBlocks = Arena<IrBlock>()
                    val stubLocalVars = Arena<IrLocalVariable>()
                    
                    val returnType = inferBuiltinLikeReturnType(funcName, loweredArgs)
                    
                    val dummyFunc = module.functions.append(
                        IrFunction(
                            name = funcName,
                            parameters = loweredArgs.mapIndexed { idx, argHandle ->
                                IrFunctionParameter(name = "arg_$idx", type = resolveExpressionType(argHandle), binding = null)
                            },
                            returnType = returnType,
                            expressions = stubExpressions,
                            localVariables = stubLocalVars,
                            blocks = stubBlocks,
                            body = stubBlocks.append(IrBlock(emptyList()))
                        )
                    )
                    IrExpressionKind.Call(dummyFunc, loweredArgs)
                }
            }
            is MemberAccessExpr -> {
                val objExpr = lowerExpression(astExpr.objectExpr)
                val memberName = astExpr.member
                
                // Résoudre le type de l'objet de manière récursive
                val objTypeHandle = resolveExpressionType(objExpr)
                val objType = module.types[objTypeHandle]
                var inner = objType.inner
                var currentTypeHandle = objTypeHandle
                while (inner is IrTypeInner.Pointer || inner is IrTypeInner.ValuePointer) {
                    val base = when (inner) {
                        is IrTypeInner.Pointer -> inner.base
                        is IrTypeInner.ValuePointer -> inner.base
                    }
                    currentTypeHandle = base
                    inner = module.types[base].inner
                }
                
                if (inner is IrTypeInner.Vector) {
                    // Vector swizzling/component access
                    val pattern = memberName.map { char ->
                        when (char) {
                            'x', 'r' -> 0
                            'y', 'g' -> 1
                            'z', 'b' -> 2
                            'w', 'a' -> 3
                            else -> throw LoweringError("Invalid vector component: '$char'")
                        }
                    }
                    
                    if (pattern.size == 1) {
                        IrExpressionKind.AccessIndex(objExpr, pattern[0].toUInt())
                    } else {
                        val size = IrVectorSize.fromInt(pattern.size)
                        IrExpressionKind.Swizzle(size, objExpr, pattern)
                    }
                } else {
                    val structName = structHandleToNameMap[currentTypeHandle]
                        ?: throw LoweringError("Cannot access member on non-struct type or type not found in struct map")
                    
                    // Récupérer l'index du membre
                    val memberIndex = structMemberIndexMap[structName]?.get(memberName)
                        ?: run {
                            // DEBUG: Afficher le contenu de structMemberIndexMap
                            println("DEBUG: Member '$memberName' not found in struct '$structName'")
                            println("  Available structs: ${structMemberIndexMap.keys}")
                            println("  Members of '$structName': ${structMemberIndexMap[structName]?.keys}")
                            if (structName == "VertexOutput") {
                                println("  Searching for VertexOutput in structNameMap...")
                                structNameMap.forEach { (name, handle) ->
                                    if (name == "VertexOutput") {
                                        val type = module.types[handle]
                                        println("  Found VertexOutput: handle=$handle, type=$type")
                                        val structInner = type.inner as? IrTypeInner.Struct
                                        if (structInner != null) {
                                            println("  Members: ${structInner.members.map { it.name }}")
                                        }
                                    }
                                }
                                println("  VertexOutput search complete")
                            }
                            throw LoweringError("Member '$memberName' not found in struct '$structName'")
                        }
                    
                    IrExpressionKind.AccessIndex(objExpr, memberIndex)
                }
            }
            is IndexExpr -> {
                val index = astExpr.index
                if (index is IntLiteral) {
                    IrExpressionKind.AccessIndex(
                        lowerExpression(astExpr.objectExpr),
                        index.value.toUInt()
                    )
                } else {
                    IrExpressionKind.Access(
                        lowerExpression(astExpr.objectExpr),
                        lowerExpression(index)
                    )
                }
            }
            is BitcastExpr -> {
                val exprHandle = lowerExpression(astExpr.expr)
                val targetTypeHandle = lowerType(astExpr.type)
                IrExpressionKind.Bitcast(exprHandle, targetTypeHandle)
            }
            else -> throw LoweringError("Unsupported expression type: ${astExpr::class.simpleName}")
        }
        return currentExpressions!!.append(IrExpression(kind))
    }

    private fun lowerBinaryOperator(op: BinaryOperator): IrBinaryOperator = when (op) {
        BinaryOperator.ADD -> IrBinaryOperator.Add
        BinaryOperator.SUBTRACT -> IrBinaryOperator.Subtract
        BinaryOperator.MULTIPLY -> IrBinaryOperator.Multiply
        BinaryOperator.DIVIDE -> IrBinaryOperator.Divide
        BinaryOperator.MODULO -> IrBinaryOperator.Modulo
        BinaryOperator.EQ -> IrBinaryOperator.Equal
        BinaryOperator.NEQ -> IrBinaryOperator.NotEqual
        BinaryOperator.LT -> IrBinaryOperator.Less
        BinaryOperator.LTE -> IrBinaryOperator.LessOrEqual
        BinaryOperator.GT -> IrBinaryOperator.Greater
        BinaryOperator.GTE -> IrBinaryOperator.GreaterOrEqual
        BinaryOperator.LOGICAL_AND -> IrBinaryOperator.LogicalAnd
        BinaryOperator.LOGICAL_OR -> IrBinaryOperator.LogicalOr
        BinaryOperator.BITWISE_AND -> IrBinaryOperator.BitAnd
        BinaryOperator.BITWISE_OR -> IrBinaryOperator.BitOr
        BinaryOperator.BITWISE_XOR -> IrBinaryOperator.BitXor
        BinaryOperator.LEFT_SHIFT -> IrBinaryOperator.ShiftLeft
        BinaryOperator.RIGHT_SHIFT -> IrBinaryOperator.ShiftRight
        else -> IrBinaryOperator.Add
    }

    private fun lowerUnaryOperator(op: io.ygdrasil.wgsl.ast.UnaryOperator): IrUnaryOperator = when (op) {
        io.ygdrasil.wgsl.ast.UnaryOperator.MINUS -> IrUnaryOperator.Negate
        io.ygdrasil.wgsl.ast.UnaryOperator.NOT -> IrUnaryOperator.Not
        io.ygdrasil.wgsl.ast.UnaryOperator.BITWISE_NOT -> IrUnaryOperator.BitNot
        io.ygdrasil.wgsl.ast.UnaryOperator.PLUS -> IrUnaryOperator.Negate // Plus is a no-op, use Negate as dummy
        else -> IrUnaryOperator.Not
    }

    private fun lowerInferredType(): Handle<IrType> {
        throw LoweringError("Cannot infer variable type without an initializer")
    }

    private fun resolveExpressionType(expr: Handle<IrExpression>): Handle<IrType> {
        val kind = currentExpressions!![expr].kind
        return when (kind) {
            is IrExpressionKind.LocalVar -> {
                currentLocalVars!![kind.handle].type
            }
            is IrExpressionKind.GlobalVar -> {
                module.globalVariables[kind.handle].type
            }
            is IrExpressionKind.FunctionArgument -> {
                val func = currentFunction?.let { module.functions[it] }
                    ?: throw LoweringError("Function argument without current function context")
                val paramIndex = kind.index
                if (paramIndex >= 0 && paramIndex < func.parameters.size) {
                    func.parameters[paramIndex].type
                } else {
                    throw LoweringError("Invalid function argument index: $paramIndex")
                }
            }
            is IrExpressionKind.TypeConstructor -> {
                kind.type
            }
            is IrExpressionKind.Access -> {
                val baseTypeHandle = resolveExpressionType(kind.expr)
                val baseType = module.types[baseTypeHandle]
                var inner = baseType.inner
                while (inner is IrTypeInner.Pointer || inner is IrTypeInner.ValuePointer) {
                    inner = module.types[when (inner) {
                        is IrTypeInner.Pointer -> inner.base
                        is IrTypeInner.ValuePointer -> inner.base
                    }].inner
                }
                
                val elemTypeHandle = when (inner) {
                    is IrTypeInner.Array -> inner.element
                    is IrTypeInner.Vector -> inner.scalar
                    is IrTypeInner.Matrix -> {
                        val vectorInner = IrTypeInner.Vector(inner.rows, inner.scalar)
                        module.types.append(IrType(vectorInner))
                    }
                    else -> throw LoweringError("Cannot index into non-aggregate type: ${inner::class.simpleName}")
                }
                
                val originalInner = baseType.inner
                if (originalInner is IrTypeInner.Pointer) {
                    val ptrInner = IrTypeInner.Pointer(elemTypeHandle, originalInner.addressSpace, originalInner.accessMode)
                    module.types.append(IrType(ptrInner))
                } else if (originalInner is IrTypeInner.ValuePointer) {
                    val ptrInner = IrTypeInner.ValuePointer(elemTypeHandle)
                    module.types.append(IrType(ptrInner))
                } else {
                    elemTypeHandle
                }
            }
            is IrExpressionKind.AccessIndex -> {
                val baseTypeHandle = resolveExpressionType(kind.expr)
                val baseType = module.types[baseTypeHandle]
                var inner = baseType.inner
                while (inner is IrTypeInner.Pointer || inner is IrTypeInner.ValuePointer) {
                    inner = module.types[when (inner) {
                        is IrTypeInner.Pointer -> inner.base
                        is IrTypeInner.ValuePointer -> inner.base
                    }].inner
                }
                
                val elemTypeHandle = when (inner) {
                    is IrTypeInner.Array -> inner.element
                    is IrTypeInner.Vector -> inner.scalar
                    is IrTypeInner.Matrix -> {
                        val vectorInner = IrTypeInner.Vector(inner.rows, inner.scalar)
                        module.types.append(IrType(vectorInner))
                    }
                    is IrTypeInner.Struct -> {
                        inner.members[kind.index.toInt()].type
                    }
                    else -> throw LoweringError("Cannot index into non-aggregate type: ${inner::class.simpleName}")
                }
                
                val originalInner = baseType.inner
                if (originalInner is IrTypeInner.Pointer) {
                    val ptrInner = IrTypeInner.Pointer(elemTypeHandle, originalInner.addressSpace, originalInner.accessMode)
                    module.types.append(IrType(ptrInner))
                } else if (originalInner is IrTypeInner.ValuePointer) {
                    val ptrInner = IrTypeInner.ValuePointer(elemTypeHandle)
                    module.types.append(IrType(ptrInner))
                } else {
                    elemTypeHandle
                }
            }
            is IrExpressionKind.Swizzle -> {
                val baseTypeHandle = resolveExpressionType(kind.vector)
                val baseType = module.types[baseTypeHandle]
                var inner = baseType.inner
                while (inner is IrTypeInner.Pointer || inner is IrTypeInner.ValuePointer) {
                    inner = module.types[when (inner) {
                        is IrTypeInner.Pointer -> inner.base
                        is IrTypeInner.ValuePointer -> inner.base
                    }].inner
                }
                val scalarHandle = when (inner) {
                    is IrTypeInner.Vector -> inner.scalar
                    else -> throw LoweringError("Swizzle on non-vector type: ${inner::class.simpleName}")
                }
                val vectorInner = IrTypeInner.Vector(kind.size, scalarHandle)
                module.types.append(IrType(vectorInner))
            }
            is IrExpressionKind.Load -> {
                val baseTypeHandle = resolveExpressionType(kind.pointer)
                val baseType = module.types[baseTypeHandle]
                when (val inner = baseType.inner) {
                    is IrTypeInner.Pointer -> inner.base
                    is IrTypeInner.ValuePointer -> inner.base
                    else -> throw LoweringError("Load on non-pointer type: ${inner::class.simpleName}")
                }
            }
            is IrExpressionKind.ValuePointer -> {
                val baseTypeHandle = resolveExpressionType(kind.base)
                val ptrInner = IrTypeInner.ValuePointer(baseTypeHandle)
                module.types.append(IrType(ptrInner))
            }
            is IrExpressionKind.Literal -> {
                when (val value = kind.value) {
                    is IrLiteralValue.Scalar -> {
                        val (scalarKind, width) = when (value.value) {
                            is IrScalarValue.Bool -> IrScalarKind.Bool to 1
                            is IrScalarValue.I8 -> IrScalarKind.Sint to 1
                            is IrScalarValue.U8 -> IrScalarKind.Uint to 1
                            is IrScalarValue.I16 -> IrScalarKind.Sint to 2
                            is IrScalarValue.U16 -> IrScalarKind.Uint to 2
                            is IrScalarValue.I32 -> IrScalarKind.Sint to 4
                            is IrScalarValue.U32 -> IrScalarKind.Uint to 4
                            is IrScalarValue.I64 -> IrScalarKind.Sint to 8
                            is IrScalarValue.U64 -> IrScalarKind.Uint to 8
                            is IrScalarValue.F16 -> IrScalarKind.F16 to 2
                            is IrScalarValue.F32 -> IrScalarKind.F32 to 4
                            is IrScalarValue.F64 -> IrScalarKind.F64 to 8
                            is IrScalarValue.AbstractInt -> IrScalarKind.AbstractInt to 8
                            is IrScalarValue.AbstractFloat -> IrScalarKind.AbstractFloat to 8
                        }
                        val scalarInner = IrTypeInner.Scalar(scalarKind, width)
                        module.types.append(IrType(scalarInner))
                    }
                    else -> throw LoweringError("Unsupported literal value type: ${value::class.simpleName}")
                }
            }
            is IrExpressionKind.Call -> {
                module.functions[kind.function].returnType
                    ?: if (kind.arguments.isNotEmpty()) {
                        resolveExpressionType(kind.arguments[0])
                    } else {
                        throw LoweringError("Function ${module.functions[kind.function].name} has no return type")
                    }
            }
            is IrExpressionKind.BuiltinCall -> {
                if (kind.arguments.isNotEmpty()) {
                    resolveExpressionType(kind.arguments[0])
                } else {
                    throw LoweringError("Builtin call to ${kind.function} has no arguments to infer type from")
                }
            }
            is IrExpressionKind.Bitcast -> {
                kind.target
            }
            is IrExpressionKind.Binary -> {
                val leftType = resolveExpressionType(kind.left)
                val rightType = resolveExpressionType(kind.right)
                val leftInner = module.types[leftType].inner
                val rightInner = module.types[rightType].inner
                if (kind.operator.isComparison) {
                    val boolType = module.types.append(IrType(IrTypeInner.Scalar(IrScalarKind.Bool, 1)))
                    if (leftInner is IrTypeInner.Vector) {
                        module.types.append(IrType(IrTypeInner.Vector(leftInner.size, boolType)))
                    } else if (rightInner is IrTypeInner.Vector) {
                        module.types.append(IrType(IrTypeInner.Vector(rightInner.size, boolType)))
                    } else {
                        boolType
                    }
                } else if (leftInner is IrTypeInner.Scalar && rightInner is IrTypeInner.Scalar) {
                    if (leftInner.kind == IrScalarKind.F32 || rightInner.kind == IrScalarKind.F32) {
                        if (leftInner.kind == IrScalarKind.F32) leftType else rightType
                    } else if (leftInner.kind == IrScalarKind.F16 || rightInner.kind == IrScalarKind.F16) {
                        if (leftInner.kind == IrScalarKind.F16) leftType else rightType
                    } else if (leftInner.kind == IrScalarKind.F64 || rightInner.kind == IrScalarKind.F64) {
                        if (leftInner.kind == IrScalarKind.F64) leftType else rightType
                    } else if (leftInner.kind == IrScalarKind.AbstractFloat || rightInner.kind == IrScalarKind.AbstractFloat) {
                        if (leftInner.kind == IrScalarKind.AbstractFloat) leftType else rightType
                    } else {
                        leftType
                    }
                } else if (leftInner is IrTypeInner.Vector || rightInner is IrTypeInner.Vector) {
                    if (leftInner is IrTypeInner.Vector) leftType else rightType
                } else if (leftInner is IrTypeInner.Matrix || rightInner is IrTypeInner.Matrix) {
                    if (leftInner is IrTypeInner.Matrix) leftType else rightType
                } else {
                    leftType
                }
            }
            is IrExpressionKind.Unary -> {
                resolveExpressionType(kind.expr)
            }
            is IrExpressionKind.Select -> {
                resolveExpressionType(kind.accept)
            }
            is IrExpressionKind.ConstantExpr -> {
                module.constants[kind.handle].type
            }
            else -> throw LoweringError("Cannot resolve member access object type for expression kind: ${kind::class.simpleName}")
        }
    }

    private fun inferBuiltinLikeReturnType(
        functionName: String,
        arguments: List<Handle<IrExpression>>
    ): Handle<IrType>? {
        return when (functionName) {
            "textureSample",
            "textureSampleBias",
            "textureSampleGrad",
            "textureSampleLevel",
            "textureSampleBaseClampToEdge" -> arguments.firstOrNull()?.let { inferSampledTextureReturnType(it) }
            "textureSampleCompare",
            "textureSampleCompareLevel" -> scalarType("f32")
            "atomicCompareExchangeWeak" -> inferAtomicCompareExchangeResultType(arguments)
            else -> arguments.firstOrNull()?.let { resolveExpressionType(it) }
        }
    }

    private fun inferAtomicCompareExchangeResultType(arguments: List<Handle<IrExpression>>): Handle<IrType> {
        val valueType = arguments.firstOrNull()
            ?.let { resolveAtomicPointerValueType(it) }
            ?: arguments.getOrNull(1)?.let { resolveExpressionType(it) }
            ?: throw LoweringError("atomicCompareExchangeWeak requires an atomic pointer argument")
        val boolType = scalarType("bool")
        val resultType = module.types.append(
            IrType(
                IrTypeInner.Struct(
                    listOf(
                        IrStructMember("old_value", valueType, null, 0),
                        IrStructMember("exchanged", boolType, null, 4)
                    )
                )
            )
        )
        val valueTypeName = atomicCompareExchangeValueTypeName(valueType)
        val resultName = "__AtomicCompareExchangeResult_$valueTypeName"
        structHandleToNameMap[resultType] = resultName
        structMemberIndexMap[resultName] = mapOf(
            "old_value" to 0u,
            "exchanged" to 1u
        )
        return resultType
    }

    private fun resolveAtomicPointerValueType(pointer: Handle<IrExpression>): Handle<IrType> {
        var typeHandle = resolveExpressionType(pointer)
        while (true) {
            val inner = module.types[typeHandle].inner
            typeHandle = when (inner) {
                is IrTypeInner.Pointer -> inner.base
                is IrTypeInner.ValuePointer -> inner.base
                else -> return typeHandle
            }
        }
    }

    private fun atomicCompareExchangeValueTypeName(type: Handle<IrType>): String {
        return when (val inner = module.types[type].inner) {
            is IrTypeInner.Scalar -> when (inner.kind) {
                IrScalarKind.Sint -> "i${inner.width * 8}"
                IrScalarKind.Uint -> "u${inner.width * 8}"
                IrScalarKind.Bool -> "bool"
                IrScalarKind.F32 -> "f32"
                IrScalarKind.F16 -> "f16"
                IrScalarKind.F64 -> "f64"
                else -> "scalar_${inner.width}"
            }
            else -> "value_${type.index}"
        }
    }

    private fun inferSampledTextureReturnType(textureExpression: Handle<IrExpression>): Handle<IrType> {
        val textureType = module.types[resolveExpressionType(textureExpression)].inner
        val elementTypeName = (textureType as? IrTypeInner.Opaque)
            ?.name
            ?.substringAfter("<", missingDelimiterValue = "f32")
            ?.substringBefore(",")
            ?.substringBefore(">")
            ?.trim()
            ?.takeIf { it.isNotEmpty() }
            ?: "f32"
        return module.types.append(IrType(IrTypeInner.Vector(IrVectorSize.Quad, scalarType(elementTypeName))))
    }

    private fun scalarType(typeName: String): Handle<IrType> {
        val inner = when (typeName) {
            "i32" -> IrTypeInner.Scalar(IrScalarKind.Sint, 4)
            "u32" -> IrTypeInner.Scalar(IrScalarKind.Uint, 4)
            "f16" -> IrTypeInner.Scalar(IrScalarKind.F16, 2)
            "f32" -> IrTypeInner.Scalar(IrScalarKind.F32, 4)
            "bool" -> IrTypeInner.Scalar(IrScalarKind.Bool, 1)
            else -> IrTypeInner.Scalar(IrScalarKind.F32, 4)
        }
        return module.types.append(IrType(inner))
    }
}
