package io.ygdrasil.wgsl.proc

import io.ygdrasil.wgsl.arena.Handle
import io.ygdrasil.wgsl.arena.Arena
import io.ygdrasil.wgsl.ir.*
import io.ygdrasil.wgsl.ir.Function

/**
 * Evaluates constant expressions in the IR.
 */
class ConstantEvaluator(
    private val module: Module,
    private val function: Function? = null
) {

    /**
     * Result of a constant evaluation.
     */
    data class Result(
        val value: ConstValue,
    )

    /**
     * Error during constant evaluation.
     */
    data class Error(
        val message: String,
    )

    /**
     * List of accumulated errors.
     */
    val errors: MutableList<Error> = mutableListOf()

    /**
     * Tries to evaluate an expression as constant.
     * Returns the constant value or null if the expression cannot be evaluated.
     */
    fun tryEvaluate(expression: Handle<io.ygdrasil.wgsl.ir.Expression>): Result? {
        return try {
            val context = EvaluationContext(module, function)
            val result = evaluateExpression(expression, context)
            if (result.isConst()) {
                Result(result)
            } else {
                null
            }
        } catch (e: EvaluationError) {
            errors.add(Error(e.message ?: "Unknown evaluation error"))
            null
        }
    }

    /**
     * Evaluates a constant expression.
     * Throws an exception if evaluation fails.
     */
    fun evaluate(expression: Handle<io.ygdrasil.wgsl.ir.Expression>): Result {
        val result = tryEvaluate(expression)
        return result ?: throw EvaluationError(
            "Expression is not constant"
        )
    }

    /**
     * Evaluates all constant expressions in a module.
     * Replaces constant expressions with ExpressionKind.Literal where possible.
     */
    fun evaluateModule() {
        val context = EvaluationContext(module, null)

        // Evaluate global expressions
        module.globalExpressions.forEachWithHandle { handle, _ ->
            tryEvaluateAndReplace(handle, module.globalExpressions, context)
        }

        // Evaluate global variables initializers
        for (global in module.globalVariables) {
            if (global.init != null) {
                tryEvaluateAndReplace(global.init, module.globalExpressions, context)
            }
        }

        // Evaluate functions
        for (func in module.functions) {
            evaluateFunction(func)
        }
    }

    /**
     * Evaluates all constant expressions in a function.
     */
    private fun evaluateFunction(func: Function) {
        val context = EvaluationContext(module, func)

        // Evaluate local variables initializers
        for (local in func.localVariables) {
            if (local.init != null) {
                tryEvaluateAndReplace(local.init, func.expressions, context)
            }
        }

        // Evaluate all expressions in the function arena
        func.expressions.forEachWithHandle { handle, _ ->
            tryEvaluateAndReplace(handle, func.expressions, context)
        }
    }

    private fun tryEvaluateAndReplace(
        handle: Handle<io.ygdrasil.wgsl.ir.Expression>,
        arena: Arena<io.ygdrasil.wgsl.ir.Expression>,
        context: EvaluationContext
    ) {
        val expr = arena[handle]
        if (expr.kind is ExpressionKind.Literal) return // Already a literal

        try {
            val result = evaluateExpression(handle, context)
            if (result.isConst()) {
                val literal = when (result) {
                    is ConstValue.Scalar -> LiteralValue.Scalar(result.value)
                    is ConstValue.Vector -> LiteralValue.Vector(result.components)
                    is ConstValue.Matrix -> LiteralValue.Matrix(result.columns)
                    else -> return
                }
                // Update the expression kind to Literal
                arena[handle] = Expression(ExpressionKind.Literal(literal))
            }
        } catch (e: EvaluationError) {
            errors.add(Error(e.message ?: "Evaluation error"))
        }
    }

    /**
     * Evaluates an expression.
     */
    private fun evaluateExpression(
        exprHandle: Handle<io.ygdrasil.wgsl.ir.Expression>,
        context: EvaluationContext
    ): ConstValue {
        val expr = if (context.function != null && exprHandle.index >= 0 && exprHandle.index < context.function.expressions.size) {
            context.function.expressions[exprHandle]
        } else {
            module.globalExpressions.getOrNull(exprHandle) ?: return ConstValue.NotConst
        }

        return when (val kind = expr.kind) {
            is ExpressionKind.Literal -> {
                convertLiteralToConstValue(kind.value)
            }
            is ExpressionKind.ConstantExpr -> {
                val constant = module.constants[kind.handle]
                evaluateConstant(constant, context)
            }
            is ExpressionKind.Unary -> {
                evaluateUnaryExpression(kind, context)
            }
            is ExpressionKind.Binary -> {
                evaluateBinaryExpression(kind, context)
            }
            is ExpressionKind.Select -> {
                evaluateSelectExpression(kind, context)
            }
            is ExpressionKind.ArrayLength -> {
                evaluateArrayLengthExpression(kind, context)
            }
            is ExpressionKind.As -> {
                evaluateAsExpression(kind, context)
            }
            is ExpressionKind.GlobalVar -> {
                evaluateGlobalVariableExpression(kind, context)
            }
            is ExpressionKind.LocalVar -> {
                evaluateLocalVariableExpression(kind, context)
            }
            is ExpressionKind.FunctionArgument -> {
                evaluateFunctionArgumentExpression(kind, context)
            }
            is ExpressionKind.Access -> {
                evaluateAccessExpression(kind, context)
            }
            is ExpressionKind.AccessIndex -> {
                evaluateAccessIndexExpression(kind, context)
            }
            is ExpressionKind.Splat -> {
                evaluateSplatExpression(kind, context)
            }
            is ExpressionKind.Swizzle -> {
                evaluateSwizzleExpression(kind, context)
            }
            is ExpressionKind.TypeConstructor -> {
                evaluateTypeConstructorExpression(kind, context)
            }
            else -> {
                ConstValue.NotConst
            }
        }
    }

    /**
     * Converts a literal to ConstValue.
     */
    private fun convertLiteralToConstValue(literal: LiteralValue): ConstValue {
        return when (literal) {
            is LiteralValue.Scalar -> {
                ConstValue.Scalar(literal.value, Handle.create<Type>(-1))
            }
            is LiteralValue.Vector -> {
                ConstValue.Vector(literal.components, Handle.create<Type>(-1))
            }
            is LiteralValue.Matrix -> {
                ConstValue.Matrix(literal.columns, Handle.create<Type>(-1))
            }
        }
    }

    /**
     * Evaluates a constant.
     */
    private fun evaluateConstant(constant: Constant, context: EvaluationContext): ConstValue {
        return when (val inner = constant.inner) {
            is ConstantInner.Scalar -> ConstValue.Scalar(inner.value, constant.type)
            is ConstantInner.Vector -> ConstValue.Vector(inner.components, constant.type)
            is ConstantInner.Matrix -> ConstValue.Matrix(inner.columns, constant.type)
            is ConstantInner.Zero -> evaluateZeroConstant(inner.type)
            is ConstantInner.Composite -> evaluateCompositeConstant(inner, context)
            is ConstantInner.Expression -> evaluateExpression(inner.expr, context)
        }
    }

    private fun evaluateZeroConstant(typeHandle: Handle<Type>): ConstValue {
        val type = module.types[typeHandle]
        return when (val inner = type.inner) {
            is TypeInner.Scalar -> ConstValue.Scalar(zeroScalar(inner.kind), typeHandle)
            is TypeInner.Vector -> {
                val scalarType = module.types[inner.scalar]
                val scalar = if (scalarType.inner is TypeInner.Scalar) zeroScalar(scalarType.inner.kind) else zeroScalar(ScalarKind.F32)
                ConstValue.Vector(List(inner.size.value) { scalar }, typeHandle)
            }
            is TypeInner.Matrix -> {
                val scalarType = module.types[inner.scalar]
                val scalar = if (scalarType.inner is TypeInner.Scalar) zeroScalar(scalarType.inner.kind) else zeroScalar(ScalarKind.F32)
                val rows = inner.rows.value
                val columns = inner.columns.value
                ConstValue.Matrix(List(columns) { List(rows) { scalar } }, typeHandle)
            }
            is TypeInner.Array -> {
                ConstValue.Array(emptyList(), typeHandle)
            }
            else -> ConstValue.NotConst
        }
    }

    private fun zeroScalar(kind: ScalarKind): ScalarValue = when (kind) {
        ScalarKind.Bool -> ScalarValue.Bool(false)
        ScalarKind.Sint, ScalarKind.S32 -> ScalarValue.I32(0)
        ScalarKind.Uint, ScalarKind.U32 -> ScalarValue.U32(0L)
        ScalarKind.F32 -> ScalarValue.F32(0f)
        ScalarKind.F64 -> ScalarValue.F64(0.0)
        ScalarKind.AbstractInt -> ScalarValue.AbstractInt(0L)
        ScalarKind.AbstractFloat -> ScalarValue.AbstractFloat(0.0)
        ScalarKind.S16 -> ScalarValue.I16(0)
        ScalarKind.U16 -> ScalarValue.U16(0)
        ScalarKind.S64 -> ScalarValue.I64(0L)
        ScalarKind.U64 -> ScalarValue.U64(0uL)
        ScalarKind.F16 -> ScalarValue.F16(0f)
    }

    private fun evaluateCompositeConstant(composite: ConstantInner.Composite, context: EvaluationContext): ConstValue {
        val elements = composite.components.map { evaluateConstant(module.constants[it], context) }
        return ConstValue.Array(elements, composite.type)
    }

    /**
     * Evaluates a unary expression.
     */
    private fun evaluateUnaryExpression(
        kind: ExpressionKind.Unary,
        context: EvaluationContext
    ): ConstValue {
        val operand = evaluateExpression(kind.expr, context)

        return if (operand is ConstValue.Scalar) {
            evaluateUnaryOp(kind.operator, operand)
        } else {
            ConstValue.NotConst
        }
    }

    private fun evaluateUnaryOp(
        op: UnaryOperator,
        operand: ConstValue.Scalar
    ): ConstValue {
        return when (op) {
            UnaryOperator.Negate -> {
                when (val v = operand.value) {
                    is ScalarValue.I32 -> ConstValue.Scalar(ScalarValue.I32(-v.value), operand.type)
                    is ScalarValue.F32 -> ConstValue.Scalar(ScalarValue.F32(-v.value), operand.type)
                    is ScalarValue.F64 -> ConstValue.Scalar(ScalarValue.F64(-v.value), operand.type)
                    is ScalarValue.AbstractInt -> ConstValue.Scalar(ScalarValue.AbstractInt(-v.value), operand.type)
                    is ScalarValue.AbstractFloat -> ConstValue.Scalar(ScalarValue.AbstractFloat(-v.value), operand.type)
                    else -> throw EvaluationError("Cannot negate ${v::class.simpleName}")
                }
            }
            UnaryOperator.Not -> {
                ConstValue.Scalar(ScalarValue.Bool(!operand.value.toBool()), operand.type)
            }
            UnaryOperator.BitNot -> {
                when (val v = operand.value) {
                    is ScalarValue.I32 -> ConstValue.Scalar(ScalarValue.I32(v.value.inv()), operand.type)
                    is ScalarValue.U32 -> ConstValue.Scalar(ScalarValue.U32(v.value.inv() and 0xFFFFFFFFL), operand.type)
                    is ScalarValue.AbstractInt -> ConstValue.Scalar(ScalarValue.AbstractInt(v.value.inv()), operand.type)
                    else -> throw EvaluationError("Cannot bitwise not ${v::class.simpleName}")
                }
            }
        }
    }

    /**
     * Evaluates a binary expression.
     */
    private fun evaluateBinaryExpression(
        kind: ExpressionKind.Binary,
        context: EvaluationContext
    ): ConstValue {
        val left = evaluateExpression(kind.left, context)
        val right = evaluateExpression(kind.right, context)

        return when {
            left is ConstValue.Scalar && right is ConstValue.Scalar -> {
                evaluateBinaryOp(kind.operator, left, right)
            }
            else -> ConstValue.NotConst
        }
    }

    private fun evaluateBinaryOp(
        op: BinaryOperator,
        left: ConstValue.Scalar,
        right: ConstValue.Scalar
    ): ConstValue {
        val l = left.value
        val r = right.value

        // Handle logical operators first (only for booleans)
        if (l is ScalarValue.Bool && r is ScalarValue.Bool) {
            val res = when (op) {
                BinaryOperator.LogicalAnd -> l.value && r.value
                BinaryOperator.LogicalOr -> l.value || r.value
                BinaryOperator.Equal -> l.value == r.value
                BinaryOperator.NotEqual -> l.value != r.value
                else -> throw EvaluationError("Invalid operator $op for boolean")
            }
            return ConstValue.Scalar(ScalarValue.Bool(res), left.type)
        }

        // Handle numeric operators
        return when {
            l is ScalarValue.I32 && r is ScalarValue.I32 -> {
                val lv = l.value
                val rv = r.value
                when (op) {
                    BinaryOperator.Add -> ConstValue.Scalar(ScalarValue.I32(lv + rv), left.type)
                    BinaryOperator.Subtract -> ConstValue.Scalar(ScalarValue.I32(lv - rv), left.type)
                    BinaryOperator.Multiply -> ConstValue.Scalar(ScalarValue.I32(lv * rv), left.type)
                    BinaryOperator.Divide -> if (rv != 0) ConstValue.Scalar(ScalarValue.I32(lv / rv), left.type) else throw EvaluationError("Division by zero")
                    BinaryOperator.Modulo -> if (rv != 0) ConstValue.Scalar(ScalarValue.I32(lv % rv), left.type) else throw EvaluationError("Modulo by zero")
                    BinaryOperator.Equal -> ConstValue.Scalar(ScalarValue.Bool(lv == rv), Handle.create<Type>(-1))
                    BinaryOperator.NotEqual -> ConstValue.Scalar(ScalarValue.Bool(lv != rv), Handle.create<Type>(-1))
                    BinaryOperator.Less -> ConstValue.Scalar(ScalarValue.Bool(lv < rv), Handle.create<Type>(-1))
                    BinaryOperator.LessOrEqual -> ConstValue.Scalar(ScalarValue.Bool(lv <= rv), Handle.create<Type>(-1))
                    BinaryOperator.Greater -> ConstValue.Scalar(ScalarValue.Bool(lv > rv), Handle.create<Type>(-1))
                    BinaryOperator.GreaterOrEqual -> ConstValue.Scalar(ScalarValue.Bool(lv >= rv), Handle.create<Type>(-1))
                    BinaryOperator.BitAnd -> ConstValue.Scalar(ScalarValue.I32(lv and rv), left.type)
                    BinaryOperator.BitOr -> ConstValue.Scalar(ScalarValue.I32(lv or rv), left.type)
                    BinaryOperator.BitXor -> ConstValue.Scalar(ScalarValue.I32(lv xor rv), left.type)
                    BinaryOperator.ShiftLeft -> ConstValue.Scalar(ScalarValue.I32(lv shl rv), left.type)
                    BinaryOperator.ShiftRight -> ConstValue.Scalar(ScalarValue.I32(lv shr rv), left.type)
                    else -> ConstValue.NotConst
                }
            }
            l is ScalarValue.U32 && r is ScalarValue.U32 -> {
                val lv = l.value
                val rv = r.value
                when (op) {
                    BinaryOperator.Add -> ConstValue.Scalar(ScalarValue.U32(lv + rv), left.type)
                    BinaryOperator.Subtract -> ConstValue.Scalar(ScalarValue.U32(lv - rv), left.type)
                    BinaryOperator.Multiply -> ConstValue.Scalar(ScalarValue.U32(lv * rv), left.type)
                    BinaryOperator.Divide -> if (rv != 0L) ConstValue.Scalar(ScalarValue.U32(lv / rv), left.type) else throw EvaluationError("Division by zero")
                    BinaryOperator.Modulo -> if (rv != 0L) ConstValue.Scalar(ScalarValue.U32(lv % rv), left.type) else throw EvaluationError("Modulo by zero")
                    BinaryOperator.Equal -> ConstValue.Scalar(ScalarValue.Bool(lv == rv), Handle.create<Type>(-1))
                    BinaryOperator.NotEqual -> ConstValue.Scalar(ScalarValue.Bool(lv != rv), Handle.create<Type>(-1))
                    BinaryOperator.Less -> ConstValue.Scalar(ScalarValue.Bool(lv < rv), Handle.create<Type>(-1))
                    BinaryOperator.LessOrEqual -> ConstValue.Scalar(ScalarValue.Bool(lv <= rv), Handle.create<Type>(-1))
                    BinaryOperator.Greater -> ConstValue.Scalar(ScalarValue.Bool(lv > rv), Handle.create<Type>(-1))
                    BinaryOperator.GreaterOrEqual -> ConstValue.Scalar(ScalarValue.Bool(lv >= rv), Handle.create<Type>(-1))
                    BinaryOperator.BitAnd -> ConstValue.Scalar(ScalarValue.U32(lv and rv), left.type)
                    BinaryOperator.BitOr -> ConstValue.Scalar(ScalarValue.U32(lv or rv), left.type)
                    BinaryOperator.BitXor -> ConstValue.Scalar(ScalarValue.U32(lv xor rv), left.type)
                    BinaryOperator.ShiftLeft -> ConstValue.Scalar(ScalarValue.U32(lv shl rv.toInt()), left.type)
                    BinaryOperator.ShiftRight -> ConstValue.Scalar(ScalarValue.U32(lv shr rv.toInt()), left.type)
                    else -> ConstValue.NotConst
                }
            }
            l is ScalarValue.F32 && r is ScalarValue.F32 -> {
                val lv = l.value
                val rv = r.value
                when (op) {
                    BinaryOperator.Add -> ConstValue.Scalar(ScalarValue.F32(lv + rv), left.type)
                    BinaryOperator.Subtract -> ConstValue.Scalar(ScalarValue.F32(lv - rv), left.type)
                    BinaryOperator.Multiply -> ConstValue.Scalar(ScalarValue.F32(lv * rv), left.type)
                    BinaryOperator.Divide -> if (rv != 0f) ConstValue.Scalar(ScalarValue.F32(lv / rv), left.type) else throw EvaluationError("Division by zero")
                    BinaryOperator.Modulo -> if (rv != 0f) ConstValue.Scalar(ScalarValue.F32(lv % rv), left.type) else throw EvaluationError("Modulo by zero")
                    BinaryOperator.Equal -> ConstValue.Scalar(ScalarValue.Bool(lv == rv), Handle.create<Type>(-1))
                    BinaryOperator.NotEqual -> ConstValue.Scalar(ScalarValue.Bool(lv != rv), Handle.create<Type>(-1))
                    BinaryOperator.Less -> ConstValue.Scalar(ScalarValue.Bool(lv < rv), Handle.create<Type>(-1))
                    BinaryOperator.LessOrEqual -> ConstValue.Scalar(ScalarValue.Bool(lv <= rv), Handle.create<Type>(-1))
                    BinaryOperator.Greater -> ConstValue.Scalar(ScalarValue.Bool(lv > rv), Handle.create<Type>(-1))
                    BinaryOperator.GreaterOrEqual -> ConstValue.Scalar(ScalarValue.Bool(lv >= rv), Handle.create<Type>(-1))
                    else -> ConstValue.NotConst
                }
            }
            l is ScalarValue.AbstractInt && r is ScalarValue.AbstractInt -> {
                val lv = l.value
                val rv = r.value
                when (op) {
                    BinaryOperator.Add -> ConstValue.Scalar(ScalarValue.AbstractInt(lv + rv), left.type)
                    BinaryOperator.Subtract -> ConstValue.Scalar(ScalarValue.AbstractInt(lv - rv), left.type)
                    BinaryOperator.Multiply -> ConstValue.Scalar(ScalarValue.AbstractInt(lv * rv), left.type)
                    BinaryOperator.Divide -> if (rv != 0L) ConstValue.Scalar(ScalarValue.AbstractInt(lv / rv), left.type) else throw EvaluationError("Division by zero")
                    BinaryOperator.Modulo -> if (rv != 0L) ConstValue.Scalar(ScalarValue.AbstractInt(lv % rv), left.type) else throw EvaluationError("Modulo by zero")
                    BinaryOperator.Equal -> ConstValue.Scalar(ScalarValue.Bool(lv == rv), Handle.create<Type>(-1))
                    BinaryOperator.NotEqual -> ConstValue.Scalar(ScalarValue.Bool(lv != rv), Handle.create<Type>(-1))
                    BinaryOperator.Less -> ConstValue.Scalar(ScalarValue.Bool(lv < rv), Handle.create<Type>(-1))
                    BinaryOperator.LessOrEqual -> ConstValue.Scalar(ScalarValue.Bool(lv <= rv), Handle.create<Type>(-1))
                    BinaryOperator.Greater -> ConstValue.Scalar(ScalarValue.Bool(lv > rv), Handle.create<Type>(-1))
                    BinaryOperator.GreaterOrEqual -> ConstValue.Scalar(ScalarValue.Bool(lv >= rv), Handle.create<Type>(-1))
                    else -> ConstValue.NotConst
                }
            }
            l is ScalarValue.AbstractFloat && r is ScalarValue.AbstractFloat -> {
                val lv = l.value
                val rv = r.value
                when (op) {
                    BinaryOperator.Add -> ConstValue.Scalar(ScalarValue.AbstractFloat(lv + rv), left.type)
                    BinaryOperator.Subtract -> ConstValue.Scalar(ScalarValue.AbstractFloat(lv - rv), left.type)
                    BinaryOperator.Multiply -> ConstValue.Scalar(ScalarValue.AbstractFloat(lv * rv), left.type)
                    BinaryOperator.Divide -> if (rv != 0.0) ConstValue.Scalar(ScalarValue.AbstractFloat(lv / rv), left.type) else throw EvaluationError("Division by zero")
                    BinaryOperator.Modulo -> if (rv != 0.0) ConstValue.Scalar(ScalarValue.AbstractFloat(lv % rv), left.type) else throw EvaluationError("Modulo by zero")
                    BinaryOperator.Equal -> ConstValue.Scalar(ScalarValue.Bool(lv == rv), Handle.create<Type>(-1))
                    BinaryOperator.NotEqual -> ConstValue.Scalar(ScalarValue.Bool(lv != rv), Handle.create<Type>(-1))
                    BinaryOperator.Less -> ConstValue.Scalar(ScalarValue.Bool(lv < rv), Handle.create<Type>(-1))
                    BinaryOperator.LessOrEqual -> ConstValue.Scalar(ScalarValue.Bool(lv <= rv), Handle.create<Type>(-1))
                    BinaryOperator.Greater -> ConstValue.Scalar(ScalarValue.Bool(lv > rv), Handle.create<Type>(-1))
                    BinaryOperator.GreaterOrEqual -> ConstValue.Scalar(ScalarValue.Bool(lv >= rv), Handle.create<Type>(-1))
                    else -> ConstValue.NotConst
                }
            }
            else -> ConstValue.NotConst
        }
    }

    private fun evaluateSelectExpression(kind: ExpressionKind.Select, context: EvaluationContext): ConstValue {
        val condition = evaluateExpression(kind.condition, context)
        val accept = evaluateExpression(kind.accept, context)
        val reject = evaluateExpression(kind.reject, context)

        return if (condition is ConstValue.Scalar && condition.value.toBool()) {
            accept
        } else {
            reject
        }
    }

    private fun evaluateArrayLengthExpression(kind: ExpressionKind.ArrayLength, context: EvaluationContext): ConstValue {
        val array = evaluateExpression(kind.expr, context)
        return if (array is ConstValue.Array) {
            ConstValue.Scalar(ScalarValue.U32(array.elements.size.toLong()), Handle.create<Type>(-1))
        } else {
            ConstValue.NotConst
        }
    }

    private fun evaluateAsExpression(kind: ExpressionKind.As, context: EvaluationContext): ConstValue {
        val value = evaluateExpression(kind.expr, context)
        val targetType = module.types[kind.target]
        return if (value is ConstValue.Scalar && targetType.inner is TypeInner.Scalar) {
            ConstValue.Scalar(castScalar(value.value, targetType.inner.kind), kind.target)
        } else {
            value
        }
    }

    private fun evaluateGlobalVariableExpression(kind: ExpressionKind.GlobalVar, context: EvaluationContext): ConstValue {
        val global = module.globalVariables[kind.handle]
        return if (global.init != null) {
            evaluateExpression(global.init, context)
        } else {
            ConstValue.NotConst
        }
    }

    private fun evaluateLocalVariableExpression(kind: ExpressionKind.LocalVar, context: EvaluationContext): ConstValue {
        val local = function?.localVariables?.get(kind.handle)
        return if (local?.init != null) {
            evaluateExpression(local.init, context)
        } else {
            ConstValue.NotConst
        }
    }

    private fun evaluateFunctionArgumentExpression(kind: ExpressionKind.FunctionArgument, context: EvaluationContext): ConstValue {
        return ConstValue.NotConst
    }

    private fun evaluateAccessExpression(kind: ExpressionKind.Access, context: EvaluationContext): ConstValue {
        val base = evaluateExpression(kind.expr, context)
        val indexVal = evaluateExpression(kind.index, context)
        
        if (indexVal !is ConstValue.Scalar || (indexVal.value !is ScalarValue.I32 && indexVal.value !is ScalarValue.U32)) {
            return ConstValue.NotConst
        }
        
        val index = when (val v = indexVal.value) {
            is ScalarValue.I32 -> v.value
            is ScalarValue.U32 -> v.value.toInt()
        }
        
        return when (base) {
            is ConstValue.Vector -> {
                if (index >= 0 && index < base.components.size) {
                    ConstValue.Scalar(base.components[index], base.type)
                } else {
                    throw EvaluationError("Vector index out of bounds")
                }
            }
            is ConstValue.Matrix -> {
                 if (index >= 0 && index < base.columns.size) {
                    ConstValue.Vector(base.columns[index], base.type)
                } else {
                    throw EvaluationError("Matrix index out of bounds")
                }
            }
            is ConstValue.Array -> {
                if (index >= 0 && index < base.elements.size) {
                    base.elements[index]
                } else {
                    throw EvaluationError("Array index out of bounds")
                }
            }
            is ConstValue.Scalar, is ConstValue.Struct, is ConstValue.NotConst -> ConstValue.NotConst
        }
    }

    private fun evaluateAccessIndexExpression(kind: ExpressionKind.AccessIndex, context: EvaluationContext): ConstValue {
        val base = evaluateExpression(kind.expr, context)
        val index = kind.index.toInt()
        return when (base) {
            is ConstValue.Vector -> {
                if (index >= 0 && index < base.components.size) {
                    ConstValue.Scalar(base.components[index], base.type)
                } else {
                    throw EvaluationError("Vector index out of bounds")
                }
            }
            is ConstValue.Matrix -> {
                 if (index >= 0 && index < base.columns.size) {
                    ConstValue.Vector(base.columns[index], base.type)
                } else {
                    throw EvaluationError("Matrix index out of bounds")
                }
            }
            is ConstValue.Array -> {
                if (index >= 0 && index < base.elements.size) {
                    base.elements[index]
                } else {
                    throw EvaluationError("Array index out of bounds")
                }
            }
            is ConstValue.Scalar, is ConstValue.Struct, is ConstValue.NotConst -> ConstValue.NotConst
        }
    }

    private fun evaluateSplatExpression(kind: ExpressionKind.Splat, context: EvaluationContext): ConstValue {
        val value = evaluateExpression(kind.value, context)
        return if (value is ConstValue.Scalar) {
            val components = List(kind.size.value) { value.value }
            ConstValue.Vector(components, Handle.create(-1))
        } else {
            ConstValue.NotConst
        }
    }

    private fun evaluateSwizzleExpression(kind: ExpressionKind.Swizzle, context: EvaluationContext): ConstValue {
        val vector = evaluateExpression(kind.vector, context)
        return if (vector is ConstValue.Vector) {
            val components = kind.pattern.map { index ->
                if (index >= 0 && index < vector.components.size) {
                    vector.components[index]
                } else {
                    throw EvaluationError("Swizzle index $index out of bounds for vector of size ${vector.components.size}")
                }
            }
            ConstValue.Vector(components, Handle.create(-1))
        } else {
            ConstValue.NotConst
        }
    }

    private fun evaluateTypeConstructorExpression(kind: ExpressionKind.TypeConstructor, context: EvaluationContext): ConstValue {
        val type = module.types[kind.type]
        val args = kind.arguments.map { evaluateExpression(it, context) }

        if (args.any { !it.isConst() }) return ConstValue.NotConst

        return when (val inner = type.inner) {
            is TypeInner.Vector -> {
                val components = mutableListOf<ScalarValue>()
                for (arg in args) {
                    when (arg) {
                        is ConstValue.Scalar -> components.add(arg.value)
                        is ConstValue.Vector -> components.addAll(arg.components)
                        else -> return ConstValue.NotConst
                    }
                }
                ConstValue.Vector(components, kind.type)
            }
            is TypeInner.Matrix -> {
                val columns = mutableListOf<List<ScalarValue>>()
                for (arg in args) {
                    when (arg) {
                        is ConstValue.Vector -> columns.add(arg.components)
                        else -> return ConstValue.NotConst
                    }
                }
                ConstValue.Matrix(columns, kind.type)
            }
            is TypeInner.Array -> {
                ConstValue.Array(args, kind.type)
            }
            is TypeInner.Struct -> {
                ConstValue.Struct(args, kind.type)
            }
            is TypeInner.Scalar -> {
                if (args.size == 1 && args[0] is ConstValue.Scalar) {
                    val valToCast = (args[0] as ConstValue.Scalar)
                    ConstValue.Scalar(castScalar(valToCast.value, inner.kind), kind.type)
                } else {
                    ConstValue.NotConst
                }
            }
            else -> ConstValue.NotConst
        }
    }

    private fun castScalar(value: ScalarValue, targetKind: ScalarKind): ScalarValue {
        return when (targetKind) {
            ScalarKind.S32 -> ScalarValue.I32(value.toI32())
            ScalarKind.U32 -> ScalarValue.U32(value.toI32().toLong())
            ScalarKind.F32 -> ScalarValue.F32(value.toF32())
            ScalarKind.F64 -> ScalarValue.F64(value.toF64())
            ScalarKind.Bool -> ScalarValue.Bool(value.toBool())
            ScalarKind.AbstractInt -> ScalarValue.AbstractInt(value.toI32().toLong())
            ScalarKind.AbstractFloat -> ScalarValue.AbstractFloat(value.toF64())
            else -> value // Placeholder for other types
        }
    }
}

/**
 * Context for evaluation.
 */
private data class EvaluationContext(
    val module: Module,
    val function: io.ygdrasil.wgsl.ir.Function?
)

/**
 * Evaluation error.
 */
class EvaluationError(message: String) : RuntimeException(message)

/**
 * Extensions for ScalarValue.
 */
fun ScalarValue.toBool(): Boolean = when (this) {
    is ScalarValue.Bool -> value
    is ScalarValue.I32 -> value != 0
    is ScalarValue.U32 -> value != 0L
    is ScalarValue.F32 -> value != 0f
    is ScalarValue.F64 -> value != 0.0
    is ScalarValue.AbstractInt -> value != 0L
    is ScalarValue.AbstractFloat -> value != 0.0
    else -> false
}

fun ScalarValue.toF32(): Float = when (this) {
    is ScalarValue.F32 -> value
    is ScalarValue.F64 -> value.toFloat()
    is ScalarValue.I32 -> value.toFloat()
    is ScalarValue.U32 -> value.toFloat()
    is ScalarValue.AbstractInt -> value.toFloat()
    is ScalarValue.AbstractFloat -> value.toFloat()
    else -> 0f
}

fun ScalarValue.toF64(): Double = when (this) {
    is ScalarValue.F32 -> value.toDouble()
    is ScalarValue.F64 -> value
    is ScalarValue.I32 -> value.toDouble()
    is ScalarValue.U32 -> value.toDouble()
    is ScalarValue.AbstractInt -> value.toDouble()
    is ScalarValue.AbstractFloat -> value
    else -> 0.0
}

fun ScalarValue.toI32(): Int = when (this) {
    is ScalarValue.I32 -> value
    is ScalarValue.U32 -> value.toInt()
    is ScalarValue.Bool -> if (value) 1 else 0
    is ScalarValue.AbstractInt -> value.toInt()
    else -> 0
}
