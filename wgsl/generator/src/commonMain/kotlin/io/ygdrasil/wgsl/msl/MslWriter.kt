package io.ygdrasil.wgsl.generator.msl

import io.ygdrasil.wgsl.arena.Handle
import io.ygdrasil.wgsl.back.MslOptions
import io.ygdrasil.wgsl.back.WriterBase
import io.ygdrasil.wgsl.back.BackendWriter
import io.ygdrasil.wgsl.ir.*
import io.ygdrasil.wgsl.ir.Function
import io.ygdrasil.wgsl.proc.Layouter
import io.ygdrasil.wgsl.proc.Namer
import io.ygdrasil.wgsl.valid.ModuleInfo

class MslWriter(
    output: StringBuilder,
    module: Module,
    moduleInfo: ModuleInfo,
    options: MslOptions,
    namer: Namer,
    layouter: Layouter
) : WriterBase<MslOptions>(output, module, moduleInfo, options, namer, layouter), BackendWriter<MslOptions> {

    override fun write(module: Module, moduleInfo: ModuleInfo): String {
        return MslWriter(StringBuilder(), module, moduleInfo, options, namer, layouter).write()
    }

    override fun withOptions(options: MslOptions): BackendWriter<MslOptions> {
        return MslWriter(StringBuilder(), module, moduleInfo, options, namer, layouter)
    }

    override fun canHandle(module: Module, moduleInfo: ModuleInfo): Boolean = true

    override fun writeHeader() {
        writeLine("#include <metal_stdlib>")
        writeLine("using namespace metal;")
    }

    override fun writeStructType(handle: Handle<Type>, structInner: TypeInner.Struct, name: String) {
        writeLine("struct $name {")
        indent {
            var currentOffset = 0
            for (member in structInner.members) {
                val memberLayout = layouter[member.type]
                val offset = member.offset
                
                if (offset > currentOffset) {
                    val padding = offset - currentOffset
                    writeLine("char _pad$currentOffset[$padding];")
                }
                
                val memberName = member.name
                val typeName = getTypeName(member.type)
                writeLine("$typeName $memberName;")
                currentOffset = offset + memberLayout.size
            }
        }
        writeLine("};")
    }

    override fun writeFunctionSignature(func: Function, name: String) {
        val returnType = func.returnType?.let { getTypeName(it) } ?: "void"
        write("$returnType $name(")
        func.parameters.forEachIndexed { i, param ->
            if (i > 0) write(", ")
            val typeName = getTypeName(param.type)
            write("$typeName ${param.name}")
            // MSL attributes for params would go here
        }
        write(")")
    }

    override fun writeGlobalVariables() {
        module.globalVariables.forEachWithHandle { handle, variable ->
            if (variable.binding == null) {
                val name = getGlobalVariableName(handle)
                val typeName = getTypeName(variable.type)
                val init = variable.init?.let { " = ${writeExpression(it)}" } ?: ""
                writeLine("$typeName $name$init;")
            }
        }
    }

    override fun writeFunction(func: Function, handle: Handle<Function>) {
        val ep = module.entryPoints.find { it.function == handle }
        if (ep != null) {
            writeEntryPoint(ep, 0)
        } else {
            super.writeFunction(func, handle)
        }
    }

    override fun writeEntryPoints() {
        // Do nothing, handled in writeFunction
    }

    override fun writeEntryPoint(ep: EntryPoint, index: Int) {
        writeLine()
        val stageAttr = when (ep.stage) {
            ShaderStage.Vertex -> "[[vertex]]"
            ShaderStage.Fragment -> "[[fragment]]"
            ShaderStage.Compute -> "[[kernel]]"
        }
        
        val inputStructName = writeInputStruct(ep)
        val outputStructName = writeOutputStruct(ep)
        
        writeLine("$stageAttr")
        val func = module.functions[ep.function]
        val returnType = outputStructName ?: (func.returnType?.let { getTypeName(it) } ?: "void")
        
        write("$returnType ${ep.name}(")
        
        val args = mutableListOf<String>()
        if (inputStructName != null) {
            args.add("$inputStructName in [[stage_in]]")
        }
        
        // 1. Built-ins that are not in stage_in
        func.parameters.forEach { param ->
            val binding = param.binding
            if (binding is BindingAttribute.Builtin) {
                val mslBuiltin = getMslBuiltin(binding.builtin)
                val typeName = getMslBuiltinType(binding.builtin)
                args.add("$typeName ${param.name} [[$mslBuiltin]]")
            }
        }

        // 2. Resources (Global variables with bindings)
        module.globalVariables.forEachWithHandle { handle, variable ->
            val binding = variable.binding
            if (binding != null) {
                val name = getGlobalVariableName(handle)
                val type = module.types[variable.type]
                val typeName = getTypeName(variable.type)
                val target = options.bindingMap[binding]
                
                val mslAttr = when (val inner = type.inner) {
                    is TypeInner.Pointer -> {
                        val bufferIndex = target?.buffer ?: binding.index
                        "[[buffer($bufferIndex)]]"
                    }
                    is TypeInner.Opaque -> {
                        if (inner.name.contains("texture")) {
                            val textureIndex = target?.texture ?: binding.index
                            "[[texture($textureIndex)]]"
                        } else if (inner.name.contains("sampler")) {
                            val samplerIndex = target?.sampler ?: binding.index
                            "[[sampler($samplerIndex)]]"
                        } else {
                            "[[buffer(${binding.index})]]"
                        }
                    }
                    else -> "[[buffer(${binding.index})]]"
                }
                args.add("$typeName $name $mslAttr")
            }
        }

        write(args.joinToString(", "))
        writeLine(") {")
        indent {
            writeArgumentAssignments(ep)
            writeBlock(func.body)
        }
        writeLine("}")
    }

    protected fun writeInputStruct(ep: EntryPoint): String? {
        val func = module.functions[ep.function]
        val inputs = func.parameters.filter { it.binding is BindingAttribute.Location }
        if (inputs.isEmpty()) return null
        
        val structName = "${ep.name}_Input"
        writeLine("struct $structName {")
        indent {
            inputs.forEach { param ->
                val loc = (param.binding as BindingAttribute.Location).location
                val typeName = getTypeName(param.type)
                val mslAttr = if (ep.stage == ShaderStage.Vertex) "attribute($loc)" else "user(loc$loc)"
                writeLine("$typeName ${param.name} [[$mslAttr]];")
            }
        }
        writeLine("};")
        return structName
    }

    protected fun writeOutputStruct(ep: EntryPoint): String? {
        val func = module.functions[ep.function]
        val returnTypeHandle = func.returnType ?: return null
        val returnType = module.types[returnTypeHandle]
        val inner = returnType.inner
        
        if (inner is TypeInner.Struct) {
            val structName = "${ep.name}_Output"
            writeLine("struct $structName {")
            indent {
                inner.members.forEach { member ->
                    val typeName = getTypeName(member.type)
                    val mslAttr = when (val binding = member.binding) {
                        is BindingAttribute.Builtin -> if (binding.builtin == BuiltinValue.Position) "[[position]]" else ""
                        is BindingAttribute.Location -> "[[user(loc${binding.location})]]"
                        else -> ""
                    }
                    val attrSuffix = if (mslAttr.isNotEmpty()) " $mslAttr" else ""
                    writeLine("$typeName ${member.name}$attrSuffix;")
                }
            }
            writeLine("};")
            return structName
        } else if (ep.stage == ShaderStage.Vertex && func.returnType != null) {
             // Vertex shader must return a struct or we wrap it (simplified here)
             val structName = "${ep.name}_Output"
             writeLine("struct $structName {")
             indent {
                 writeLine("float4 position [[position]];")
             }
             writeLine("};")
             return structName
        }
        return null
    }

    protected fun writeArgumentAssignments(ep: EntryPoint) {
        val func = module.functions[ep.function]
        val inputs = func.parameters.filter { it.binding is BindingAttribute.Location }
        inputs.forEach { param ->
             writeLine("${getTypeName(param.type)} ${param.name} = in.${param.name};")
        }
    }

    override fun writeLiteralValue(value: LiteralValue): String {
        return when (value) {
            is LiteralValue.Scalar -> writeScalarValue(value.value)
            is LiteralValue.Vector -> {
                val first = value.components.first()
                val prefix = when (first) {
                    is ScalarValue.F32 -> "float"
                    is ScalarValue.U32 -> "uint"
                    is ScalarValue.I32 -> "int"
                    is ScalarValue.Bool -> "bool"
                    else -> "float"
                }
                "$prefix${value.components.size}(${value.components.joinToString { writeScalarValue(it) }})"
            }
            is LiteralValue.Matrix -> "mat(...)"
        }
    }

    override fun writeSample(
        texture: String,
        sampler: String?,
        coordinate: String,
        level: SampleLevel?,
        depthRef: Handle<Expression>?
    ): String {
        val args = mutableListOf<String>()
        args.add(sampler ?: "/* error */")
        args.add(coordinate)
        
        when (level) {
            is SampleLevel.Zero -> args.add("level(0)")
            is SampleLevel.MIPMAP -> {
                val l = writeExpression(level.level)
                args.add("level($l)")
            }
            else -> {}
        }
        
        if (depthRef != null) {
            val d = writeExpression(depthRef)
            args.add("compare_value($d)")
        }
        
        val method = if (depthRef != null) "sample_compare" else "sample"
        return "$texture.$method(${args.joinToString()})"
    }

    override fun writeTextureQuery(texture: String, query: TextureQueryKind): String {
        val method = when (query) {
            TextureQueryKind.Size -> "get_width(), $texture.get_height()" // simplified
            TextureQueryKind.SizeLevel -> "get_width($texture), $texture.get_height()"
            TextureQueryKind.NumLevels -> "get_num_mip_levels()"
            TextureQueryKind.NumLayers -> "get_array_size()"
            TextureQueryKind.NumSamples -> "get_num_samples()"
        }
        return "$texture.$method"
    }

    override fun writeRelational(function: RelationalFunction, arguments: List<String>): String {
        return when (function) {
            RelationalFunction.Any -> "any(${arguments.joinToString()})"
            RelationalFunction.All -> "all(${arguments.joinToString()})"
            RelationalFunction.IsNan -> "isnan(${arguments.joinToString()})"
            RelationalFunction.IsInf -> "isinf(${arguments.joinToString()})"
            RelationalFunction.IsFinite -> "isfinite(${arguments.joinToString()})"
            RelationalFunction.IsNormal -> "isnormal(${arguments.joinToString()})"
            RelationalFunction.SignBit -> "signbit(${arguments.joinToString()})"
        }
    }

    override fun writeBitcast(expr: String, targetType: Type): String {
        val typeName = getTypeName(module.types.append(targetType))
        return "as_type<$typeName>($expr)"
    }

    override fun writeAtomic(pointer: String, function: AtomicFunction, arguments: List<String>): String {
        val mslFunc = when (function) {
            AtomicFunction.Add -> "atomic_fetch_add_explicit"
            AtomicFunction.Subtract -> "atomic_fetch_sub_explicit"
            AtomicFunction.And -> "atomic_fetch_and_explicit"
            AtomicFunction.Or -> "atomic_fetch_or_explicit"
            AtomicFunction.Xor -> "atomic_fetch_xor_explicit"
            AtomicFunction.Min -> "atomic_fetch_min_explicit"
            AtomicFunction.Max -> "atomic_fetch_max_explicit"
            AtomicFunction.Exchange -> "atomic_exchange_explicit"
            AtomicFunction.CompSwap -> "atomic_compare_exchange_weak_explicit"
        }
        // Note: MSL atomics usually require a memory order argument
        return "$mslFunc($pointer, ${arguments.joinToString()}, memory_order_relaxed)"
    }

    override fun getBuiltinFunctionName(function: BuiltinFunction): String = when (function) {
        BuiltinFunction.Ln -> "log"
        BuiltinFunction.Fract -> "fract"
        BuiltinFunction.Mix -> "mix"
        BuiltinFunction.Atan2 -> "atan2"
        BuiltinFunction.Determinant -> "determinant"
        BuiltinFunction.Modf -> "modf"
        BuiltinFunction.Frexp -> "frexp"
        BuiltinFunction.Ldexp -> "ldexp"
        BuiltinFunction.InverseSqrt -> "rsqrt"
        BuiltinFunction.Log2 -> "log2"
        BuiltinFunction.Fma -> "fma"
        else -> super.getBuiltinFunctionName(function)
    }

    private fun toSnakeCase(s: String): String {
        return s.mapIndexed { index, c ->
            if (c.isUpperCase()) {
                if (index > 0) "_${c.lowercase()}" else c.lowercase()
            } else {
                c.toString()
            }
        }.joinToString("")
    }

    private fun getMslBuiltin(builtin: BuiltinValue): String = when (builtin) {
        BuiltinValue.Position -> "position"
        BuiltinValue.VertexIndex -> "vertex_id"
        BuiltinValue.InstanceIndex -> "instance_id"
        BuiltinValue.FrontFacing -> "front_facing"
        BuiltinValue.LocalInvocationId -> "thread_position_in_threadgroup"
        BuiltinValue.LocalInvocationIndex -> "thread_index_in_threadgroup"
        BuiltinValue.GlobalInvocationId -> "thread_position_in_grid"
        BuiltinValue.WorkgroupId -> "threadgroup_position_in_grid"
        BuiltinValue.NumWorkgroups -> "threadgroups_per_grid"
        BuiltinValue.SampleIndex -> "sample_id"
        BuiltinValue.SampleMask -> "sample_mask"
        else -> builtin.name.lowercase()
    }

    private fun getMslBuiltinType(builtin: BuiltinValue): String = when (builtin) {
        BuiltinValue.Position -> "float4"
        BuiltinValue.VertexIndex, BuiltinValue.InstanceIndex, BuiltinValue.SampleIndex -> "uint"
        BuiltinValue.FrontFacing -> "bool"
        BuiltinValue.LocalInvocationId, BuiltinValue.GlobalInvocationId, 
        BuiltinValue.WorkgroupId, BuiltinValue.NumWorkgroups -> "uint3"
        BuiltinValue.LocalInvocationIndex, BuiltinValue.SampleMask -> "uint"
        else -> "uint"
    }

    override fun getScalarTypeName(scalar: TypeInner.Scalar): String {
        return when (scalar.kind) {
            ScalarKind.Bool -> "bool"
            ScalarKind.Sint -> when (scalar.width) {
                1 -> "char"
                2 -> "short"
                4 -> "int"
                8 -> "long"
                else -> "int"
            }
            ScalarKind.Uint -> when (scalar.width) {
                1 -> "uchar"
                2 -> "ushort"
                4 -> "uint"
                8 -> "ulong"
                else -> "uint"
            }
            ScalarKind.F32 -> "float"
            ScalarKind.F16 -> "half"
            ScalarKind.F64 -> "double"
            else -> "/* unknown scalar */ void"
        }
    }

    override fun getTypeName(handle: Handle<Type>): String {
        val type = module.types[handle]
        return when (val inner = type.inner) {
            is TypeInner.Scalar -> getScalarTypeName(inner)
            is TypeInner.Vector -> {
                val scalarType = module.types[inner.scalar]
                val scalarName = getScalarTypeName(scalarType.inner as TypeInner.Scalar)
                "$scalarName${inner.size.ordinal + 2}"
            }
            is TypeInner.Matrix -> {
                val scalarType = module.types[inner.scalar]
                val scalarName = getScalarTypeName(scalarType.inner as TypeInner.Scalar)
                "$scalarName${inner.columns.ordinal + 2}x${inner.rows.ordinal + 2}"
            }
            is TypeInner.Struct -> "Struct_${handle.index}"
            is TypeInner.Pointer -> {
                val baseName = getTypeName(inner.base)
                val spaceName = when (inner.addressSpace) {
                    AddressSpace.Uniform -> "constant"
                    AddressSpace.Storage -> "device"
                    AddressSpace.Private -> "thread"
                    AddressSpace.Function -> "thread"
                    AddressSpace.Workgroup -> "threadgroup"
                }
                "$spaceName $baseName*"
            }
            is TypeInner.Opaque -> {
                when {
                    inner.name == "sampler" -> "sampler"
                    inner.name == "comparison_sampler" -> "sampler"
                    inner.name == "external_texture" -> "/* expanded external_texture */ void"
                    inner.name == "texture_1d<f32>" -> "texture1d<float>"
                    inner.name == "texture_2d<f32>" -> "texture2d<float>"
                    inner.name == "texture_2d_array<f32>" -> "texture2d_array<float>"
                    inner.name == "texture_3d<f32>" -> "texture3d<float>"
                    inner.name == "texture_cube<f32>" -> "texturecube<float>"
                    inner.name == "texture_cube_array<f32>" -> "texturecube_array<float>"
                    inner.name == "texture_multisampled_2d<f32>" -> "texture2d_ms<float>"
                    inner.name == "texture_2d<u32>" -> "texture2d<uint>"
                    inner.name == "texture_2d<i32>" -> "texture2d<int>"
                    inner.name.startsWith("texture_depth") -> "depth2d<float>"
                    inner.name.startsWith("texture") -> {
                         "texture2d<float>" 
                    }
                    else -> inner.name
                }
            }
            else -> "/* unknown type */ void"
        }
    }
}
