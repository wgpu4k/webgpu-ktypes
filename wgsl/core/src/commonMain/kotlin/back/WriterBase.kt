package io.ygdrasil.wgsl.back

import io.github.oshai.kotlinlogging.KotlinLogging
import io.ygdrasil.wgsl.arena.Handle
import io.ygdrasil.wgsl.ir.*
import io.ygdrasil.wgsl.ir.Function
import io.ygdrasil.wgsl.proc.Layouter
import io.ygdrasil.wgsl.proc.Namer
import io.ygdrasil.wgsl.valid.ModuleInfo

private val logger = KotlinLogging.logger {}

/**
 * Classe de base pour tous les writers de backend.
 */
abstract class WriterBase<T : BackendOptions>(
    protected val output: StringBuilder,
    protected val module: Module,
    protected val moduleInfo: ModuleInfo,
    protected val options: T,
    protected val namer: Namer,
    protected val layouter: Layouter
) {

    protected var indentLevel: Int = 0
    protected var currentFunction: Function? = null

    /**
     * Génère le code complet pour le module.
     */
    open fun write(): String {
        output.clear()
        indentLevel = 0

        writeHeader()
        writePreamble()
        writeTypes()
        writeConstants()
        writeGlobalVariables()
        writeFunctions()
        writeEntryPoints()

        return output.toString()
    }

    protected abstract fun writeHeader()

    protected open fun writePreamble() {}

    protected open fun writeTypes() {
        module.types.forEachWithHandle { handle, type ->
            if (type.inner is TypeInner.Struct) {
                writeStructType(handle, type.inner, getTypeName(handle))
            }
        }
    }

    protected abstract fun writeStructType(
        handle: Handle<Type>,
        structInner: TypeInner.Struct,
        name: String
    )

    protected open fun writeConstants() {
        module.constants.forEachWithHandle { handle, constant ->
            val name = getConstantName(handle)
            val typeName = getTypeName(constant.type)
            val init = writeConstantInner(constant.inner)
            writeLine("const $typeName $name = $init;")
        }
    }

    protected open fun writeGlobalVariables() {
        module.globalVariables.forEachWithHandle { handle, variable ->
            val name = getGlobalVariableName(handle)
            val typeName = getTypeName(variable.type)
            val init = variable.init?.let { " = ${writeExpression(it)}" } ?: ""
            writeLine("$typeName $name$init;")
        }
    }

    protected open fun writeFunctions() {
        logger.debug { "Writing ${module.functions.size} functions" }
        module.functions.forEachWithHandle { handle, func ->
            logger.debug { "Writing function ${func.name}" }
            writeFunction(func, handle)
        }
    }

    protected open fun writeFunction(func: Function, handle: Handle<Function>) {
        val name = getFunctionName(handle)
        currentFunction = func
        writeLine()
        writeFunctionSignature(func, name)
        writeLine(" {")
        indent {
            writeBlock(func.body)
        }
        writeLine("}")
        currentFunction = null
    }

    protected abstract fun writeFunctionSignature(func: Function, name: String)

    protected open fun writeEntryPoints() {
        module.entryPoints.forEachIndexed { index, ep ->
            writeEntryPoint(ep, index)
        }
    }

    protected abstract fun writeEntryPoint(ep: EntryPoint, index: Int)

    protected open fun writeBlock(handle: Handle<io.ygdrasil.wgsl.ir.Block>) {
        val blocks = currentFunction?.blocks ?: return
        val block = blocks[handle]
        block.statements.forEach { writeStatement(it) }
    }

    @Suppress("UNCHECKED_CAST")
    protected open fun writeStatement(stmt: Statement) {
        when (stmt) {
            is Statement.Nop -> {}
            is Statement.Block -> {
                writeLine("{")
                indent { writeBlock(stmt.block) }
                writeLine("}")
            }
            is Statement.Declare -> {
                val variable = currentFunction!!.localVariables[stmt.variable]
                val name = getLocalVariableName(stmt.variable)
                val typeName = getTypeName(variable.type)
                writeLine("$typeName $name;")
            }
            is Statement.Init -> {
                val variable = currentFunction!!.localVariables[stmt.variable]
                val name = getLocalVariableName(stmt.variable)
                val typeName = getTypeName(variable.type)
                val init = variable.init?.let { writeExpression(it) } ?: "/* error: no init */"
                writeLine("$typeName $name = $init;")
            }
            is Statement.Assign -> {
                val pointer = writeExpression(stmt.pointer)
                val value = writeExpression(stmt.value)
                writeLine("$pointer = $value;")
            }
            is Statement.Emit -> {
                // In some backends, Emit might be a no-op or might trigger something
                // For now, we skip it as it's IR metadata for expression ranges
            }
            is Statement.If -> {
                val cond = writeExpression(stmt.condition)
                writeLine("if ($cond) {")
                indent { writeBlock(stmt.accept) }
                if (stmt.reject != null) {
                    writeLine("} else {")
                    indent { writeBlock(stmt.reject) }
                }
                writeLine("}")
            }
            is Statement.Switch -> {
                val selector = writeExpression(stmt.selector)
                writeLine("switch ($selector) {")
                indent {
                    stmt.cases.forEach { case ->
                        case.selector.let { sel ->
                            when (sel) {
                                is CaseSelector.Value -> writeLine("case ${writeScalarValue(sel.value)}:")
                                is CaseSelector.Default -> writeLine("default:")
                            }
                        }
                        indent {
                            writeBlock(case.body)
                            writeLine("break;")
                        }
                    }
                }
                writeLine("}")
            }
            is Statement.Loop -> {
                writeLine("while (true) {")
                indent {
                    writeBlock(stmt.body)
                    if (stmt.continuing != null) {
                        // Continuing block is tricky in C-like languages
                        // For now we just write it at the end
                        writeBlock(stmt.continuing)
                    }
                }
                writeLine("}")
            }
            is Statement.Return -> {
                val value = stmt.value?.let { " ${writeExpression(it)}" } ?: ""
                writeLine("return$value;")
            }
            is Statement.Break -> writeLine("break;")
            is Statement.Continue -> writeLine("continue;")
            is Statement.Kill -> writeLine("kill;")
            is Statement.Discard -> writeLine("discard;")
        }
    }

    // Expressions
    protected open fun writeExpression(handle: Handle<Expression>): String {
        val expr = if (currentFunction != null) {
            currentFunction!!.expressions[handle]
        } else {
            module.globalExpressions[handle]
        }

        return when (val kind = expr.kind) {
            is ExpressionKind.Literal -> writeLiteralValue(kind.value)
            is ExpressionKind.GlobalVar -> getGlobalVariableName(kind.handle)
            is ExpressionKind.LocalVar -> getLocalVariableName(kind.handle)
            is ExpressionKind.FunctionArgument -> getFunctionArgumentName(kind.index)
            is ExpressionKind.ConstantExpr -> getConstantName(kind.handle)
            is ExpressionKind.Binary -> {
                val left = writeExpression(kind.left)
                val right = writeExpression(kind.right)
                "($left ${getBinaryOperator(kind.operator)} $right)"
            }
            is ExpressionKind.Unary -> {
                val e = writeExpression(kind.expr)
                "${getUnaryOperator(kind.operator)}($e)"
            }
            is ExpressionKind.Select -> {
                val cond = writeExpression(kind.condition)
                val accept = writeExpression(kind.accept)
                val reject = writeExpression(kind.reject)
                "($cond ? $accept : $reject)"
            }
            is ExpressionKind.Call -> {
                val name = getFunctionName(kind.function)
                val args = kind.arguments.joinToString { writeExpression(it) }
                "$name($args)"
            }
            is ExpressionKind.BuiltinCall -> {
                val args = kind.arguments.joinToString { writeExpression(it) }
                "${getBuiltinFunctionName(kind.function)}($args)"
            }
            is ExpressionKind.AccessIndex -> {
                val e = writeExpression(kind.expr)
                val type = getExpressionType(kind.expr)
                if (type.inner is TypeInner.Struct) {
                    val member = type.inner.members[kind.index.toInt()]
                    "$e.${member.name}"
                } else {
                    "$e[${kind.index}]"
                }
            }
            is ExpressionKind.Access -> {
                val e = writeExpression(kind.expr)
                val i = writeExpression(kind.index)
                "$e[$i]"
            }
            is ExpressionKind.Swizzle -> {
                val e = writeExpression(kind.vector)
                val components = kind.pattern.joinToString("") { "xyzw"[it].toString() }
                "$e.$components"
            }
            is ExpressionKind.Splat -> {
                val e = writeExpression(kind.value)
                val type = getExpressionType(kind.value)
                val scalarName = if (type.inner is TypeInner.Scalar) getScalarTypeName(type.inner) else "/* error */"
                val vectorName = "$scalarName${kind.size.ordinal + 2}"
                "$vectorName($e)"
            }
            is ExpressionKind.Load -> writeExpression(kind.pointer)
            is ExpressionKind.Store -> {
                val p = writeExpression(kind.pointer)
                val v = writeExpression(kind.value)
                "($p = $v)"
            }
            is ExpressionKind.As -> {
                val e = writeExpression(kind.expr)
                val typeName = getTypeName(kind.target)
                "($typeName)($e)"
            }
            is ExpressionKind.TypeConstructor -> {
                val typeName = getTypeName(kind.type)
                val args = kind.arguments.joinToString { writeExpression(it) }
                "$typeName($args)"
            }
            is ExpressionKind.ArrayLength -> {
                val e = writeExpression(kind.expr)
                "($e).length()" // HLSL/GLSL style. WGSL uses arrayLength()
            }
            is ExpressionKind.Sample -> {
                val t = writeExpression(kind.texture)
                val s = kind.sampler?.let { writeExpression(it) }
                val c = writeExpression(kind.coordinate)
                writeSample(t, s, c, kind.level, kind.depthRef)
            }
            is ExpressionKind.TextureQuery -> {
                val t = writeExpression(kind.texture)
                writeTextureQuery(t, kind.query)
            }
            is ExpressionKind.Atomic -> {
                val p = writeExpression(kind.pointer)
                val args = kind.arguments.map { writeExpression(it) }
                writeAtomic(p, kind.fun_, args)
            }
            is ExpressionKind.Relational -> {
                val args = kind.arguments.map { writeExpression(it) }
                writeRelational(kind.fun_, args)
            }
            is ExpressionKind.Bitcast -> {
                val e = writeExpression(kind.expr)
                val targetType = getExpressionType(handle)
                writeBitcast(e, targetType)
            }
            else -> "/* unsupported expression: ${kind::class.simpleName} */"
        }
    }

    protected open fun writeAtomic(pointer: String, function: AtomicFunction, arguments: List<String>): String {
        return "atomic_${function.name.lowercase()}($pointer, ${arguments.joinToString()})"
    }

    protected open fun writeRelational(function: RelationalFunction, arguments: List<String>): String {
        return "${function.name.lowercase()}(${arguments.joinToString()})"
    }

    protected open fun writeBitcast(expr: String, targetType: Type): String {
        return "bitcast<${getTypeName(module.types.append(targetType))}>($expr)"
    }

    protected open fun writeSample(
        texture: String,
        sampler: String?,
        coordinate: String,
        level: SampleLevel?,
        depthRef: Handle<Expression>?
    ): String {
        return "textureSample($texture, ${sampler ?: "/* no sampler */"}, $coordinate)"
    }

    protected open fun writeTextureQuery(texture: String, query: TextureQueryKind): String {
        return "textureQuery($texture, ${query.name})"
    }

    protected open fun getExpressionType(handle: Handle<Expression>): Type {
        val expr = if (currentFunction != null) {
            currentFunction!!.expressions[handle]
        } else {
            module.globalExpressions[handle]
        }
        return when (val kind = expr.kind) {
            is ExpressionKind.Literal -> when (val value = kind.value) {
                is LiteralValue.Scalar -> when (val scalar = value.value) {
                    is ScalarValue.Bool -> Type(TypeInner.Scalar(ScalarKind.Bool, 1))
                    is ScalarValue.F32 -> Type(TypeInner.Scalar(ScalarKind.F32, 4))
                    is ScalarValue.U32 -> Type(TypeInner.Scalar(ScalarKind.U32, 4))
                    is ScalarValue.I32 -> Type(TypeInner.Scalar(ScalarKind.S32, 4))
                    else -> Type(TypeInner.Error)
                }
                is LiteralValue.Vector -> {
                   val first = value.components.first()
                   val scalar = when(first) {
                       is ScalarValue.Bool -> Type(TypeInner.Scalar(ScalarKind.Bool, 1))
                       is ScalarValue.F32 -> Type(TypeInner.Scalar(ScalarKind.F32, 4))
                       is ScalarValue.U32 -> Type(TypeInner.Scalar(ScalarKind.U32, 4))
                       is ScalarValue.I32 -> Type(TypeInner.Scalar(ScalarKind.S32, 4))
                       else -> Type(TypeInner.Error)
                   }
                   val scalarHandle = module.types.append(scalar)
                   Type(TypeInner.Vector(VectorSize.fromInt(value.components.size), scalarHandle))
                }
                else -> Type(TypeInner.Error)
            }
            is ExpressionKind.LocalVar -> currentFunction!!.localVariables[kind.handle].type.let { module.types[it] }
            is ExpressionKind.GlobalVar -> module.globalVariables[kind.handle].type.let { module.types[it] }
            is ExpressionKind.FunctionArgument -> currentFunction!!.parameters[kind.index].type.let { module.types[it] }
            is ExpressionKind.AccessIndex -> {
                val baseType = getExpressionType(kind.expr)
                when (val inner = baseType.inner) {
                    is TypeInner.Struct -> module.types[inner.members[kind.index.toInt()].type]
                    is TypeInner.Vector -> module.types[inner.scalar]
                    is TypeInner.Matrix -> module.types[inner.scalar] // Should be vector
                    is TypeInner.Array -> module.types[inner.element]
                    else -> Type(TypeInner.Error)
                }
            }
            is ExpressionKind.Access -> {
                val baseType = getExpressionType(kind.expr)
                when (val inner = baseType.inner) {
                    is TypeInner.Vector -> module.types[inner.scalar]
                    is TypeInner.Matrix -> module.types[inner.scalar]
                    is TypeInner.Array -> module.types[inner.element]
                    else -> Type(TypeInner.Error)
                }
            }
            is ExpressionKind.Swizzle -> {
                val baseType = getExpressionType(kind.vector)
                when (val inner = baseType.inner) {
                    is TypeInner.Vector -> {
                        if (kind.size.ordinal == -1) { // scalar
                             module.types[inner.scalar]
                        } else {
                            val scalarHandle = inner.scalar
                            Type(TypeInner.Vector(kind.size, scalarHandle))
                        }
                    }
                    else -> Type(TypeInner.Error)
                }
            }
            is ExpressionKind.Binary -> getExpressionType(kind.left) // Simplified
            is ExpressionKind.Unary -> getExpressionType(kind.expr)
            is ExpressionKind.Load -> {
                val ptrType = getExpressionType(kind.pointer)
                when (val inner = ptrType.inner) {
                    is TypeInner.Pointer -> module.types[inner.base]
                    is TypeInner.ValuePointer -> module.types[inner.base]
                    else -> Type(TypeInner.Error)
                }
            }
            is ExpressionKind.Splat -> {
                val scalarType = getExpressionType(kind.value)
                val scalarHandle = module.types.append(scalarType)
                Type(TypeInner.Vector(kind.size, scalarHandle))
            }
            is ExpressionKind.As -> module.types[kind.target]
            is ExpressionKind.TypeConstructor -> module.types[kind.type]
            else -> Type(TypeInner.Error)
        }
    }

    protected open fun getBuiltinFunctionName(function: BuiltinFunction): String = function.name.lowercase()

    protected open fun getBinaryOperator(op: BinaryOperator): String = when (op) {
        BinaryOperator.Add -> "+"
        BinaryOperator.Subtract -> "-"
        BinaryOperator.Multiply -> "*"
        BinaryOperator.Divide -> "/"
        BinaryOperator.Modulo -> "%"
        BinaryOperator.Equal -> "=="
        BinaryOperator.NotEqual -> "!="
        BinaryOperator.Less -> "<"
        BinaryOperator.LessOrEqual -> "<="
        BinaryOperator.Greater -> ">"
        BinaryOperator.GreaterOrEqual -> ">="
        BinaryOperator.BitAnd -> "&"
        BinaryOperator.BitOr -> "|"
        BinaryOperator.BitXor -> "^"
        BinaryOperator.LogicalAnd -> "&&"
        BinaryOperator.LogicalOr -> "||"
        BinaryOperator.ShiftLeft -> "<<"
        BinaryOperator.ShiftRight -> ">>"
    }

    protected open fun getUnaryOperator(op: UnaryOperator): String = when (op) {
        UnaryOperator.Negate -> "-"
        UnaryOperator.Not -> "!"
        UnaryOperator.BitNot -> "~"
    }

    protected open fun writeLiteralValue(value: LiteralValue): String {
        return when (value) {
            is LiteralValue.Scalar -> writeScalarValue(value.value)
            is LiteralValue.Vector -> "vec(${value.components.joinToString { writeScalarValue(it) }})"
            is LiteralValue.Matrix -> "mat(...)"
        }
    }

    protected open fun writeScalarValue(value: ScalarValue): String {
        return when (value) {
            is ScalarValue.Bool -> value.value.toString()
            is ScalarValue.I8 -> value.value.toString()
            is ScalarValue.U8 -> "${value.value}u"
            is ScalarValue.I16 -> value.value.toString()
            is ScalarValue.U16 -> "${value.value}u"
            is ScalarValue.I32 -> value.value.toString()
            is ScalarValue.U32 -> "${value.value}u"
            is ScalarValue.I64 -> value.value.toString()
            is ScalarValue.U64 -> "${value.value}u"
            is ScalarValue.F16 -> "${value.value}h"
            is ScalarValue.F32 -> {
                val s = value.value.toString()
                if ("." in s) "${s}f" else "${s}.0f"
            }
            is ScalarValue.F64 -> {
                val s = value.value.toString()
                if ("." in s) s else "$s.0"
            }
            is ScalarValue.AbstractInt -> value.value.toString()
            is ScalarValue.AbstractFloat -> value.value.toString()
        }
    }

    // Utility methods for naming (to be refined)
    protected open fun getTypeName(handle: Handle<Type>): String {
        val type = module.types[handle]
        return when (val inner = type.inner) {
            is TypeInner.Scalar -> getScalarTypeName(inner)
            is TypeInner.Vector -> "vec${inner.size.ordinal + 2}<${getScalarTypeName(module.types[inner.scalar].inner as TypeInner.Scalar)}>"
            is TypeInner.Struct -> "Struct_${handle.index}"
            else -> "Type_${handle.index}"
        }
    }

    protected open fun getScalarTypeName(scalar: TypeInner.Scalar): String {
        return when (scalar.kind) {
            ScalarKind.Bool -> "bool"
            ScalarKind.Sint -> "i${scalar.width * 8}"
            ScalarKind.Uint -> "u${scalar.width * 8}"
            ScalarKind.F32 -> "f32"
            ScalarKind.F16 -> "f16"
            ScalarKind.F64 -> "f64"
            else -> "unknown"
        }
    }

    protected open fun getFunctionArgumentName(index: Int): String {
        return currentFunction?.parameters?.getOrNull(index)?.name ?: "arg_$index"
    }

    protected open fun getConstantName(handle: Handle<Constant>): String = "CONST_${handle.index}"
    protected open fun getGlobalVariableName(handle: Handle<GlobalVariable>): String = "global_${handle.index}"
    protected open fun getFunctionName(handle: Handle<Function>): String = module.functions[handle].name
    protected open fun getLocalVariableName(handle: Handle<LocalVariable>): String = "local_${handle.index}"

    protected fun indent(block: () -> Unit) {
        indentLevel++
        block()
        indentLevel--
    }

    protected fun writeLine(line: String = "") {
        repeat(indentLevel) { output.append(options.indent) }
        output.append(line).append(options.newline)
    }

    protected open fun writeConstantInner(inner: ConstantInner): String {
        return when (inner) {
            is ConstantInner.Scalar -> writeScalarValue(inner.value)
            is ConstantInner.Vector -> {
                val scalarType = when (inner.components.first()) {
                    is ScalarValue.F32 -> "f32"
                    is ScalarValue.U32 -> "u32"
                    is ScalarValue.I32 -> "i32"
                    is ScalarValue.Bool -> "bool"
                    else -> "f32"
                }
                "vec${inner.components.size}<$scalarType>(${inner.components.joinToString { writeScalarValue(it) }})"
            }
            is ConstantInner.Matrix -> "mat${inner.columns.size}x${inner.columns.firstOrNull()?.size ?: 0}(...)"
            is ConstantInner.Zero -> {
                val typeName = getTypeName(inner.type)
                "$typeName()"
            }
            is ConstantInner.Composite -> {
                val typeName = getTypeName(inner.type)
                val elements = inner.components.joinToString { getConstantName(it) }
                "$typeName($elements)"
            }
            is ConstantInner.Expression -> writeExpression(inner.expr)
        }
    }

    protected fun write(text: String) {
        output.append(text)
    }
}
