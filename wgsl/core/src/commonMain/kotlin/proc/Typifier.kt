package io.ygdrasil.wgsl.proc

import io.ygdrasil.wgsl.arena.Handle
import io.ygdrasil.wgsl.arena.Arena
import io.ygdrasil.wgsl.ir.*
import io.ygdrasil.wgsl.ir.Function

/**
 * Result of type resolution for an expression.
 */
sealed class TypeResolution {
    data class ByHandle(val handle: io.ygdrasil.wgsl.arena.Handle<Type>) : TypeResolution()
    data class ByValue(val inner: TypeInner) : TypeResolution()

    /**
     * Returns the [TypeInner] for this resolution.
     */
    fun getInner(module: Module): TypeInner = when (this) {
        is ByHandle -> module.types[handle].inner
        is ByValue -> inner
    }
}

/**
 * Resolve types for expressions in a module or function.
 * 
 * The Typifier calculates the type of each expression in an arena.
 * This is a port of Naga's typifier.
 */
class Typifier {
    private val resolutions = mutableListOf<TypeResolution>()

    /**
     * Resolves types for all expressions in a function.
     */
    fun fill(
        module: Module,
        function: io.ygdrasil.wgsl.ir.Function?,
        expressions: Arena<Expression>
    ) {
        resolutions.clear()
        // We must iterate in order because expressions can depend on previous ones
        for (i in 0 until expressions.size) {
            val handle = io.ygdrasil.wgsl.arena.Handle.create<Expression>(i)
            val expr = expressions[handle]
            resolutions.add(resolve(expr, module, function, expressions))
        }
    }

    /**
     * Resolves types for a single expression.
     */
    fun resolve(
        expr: Expression,
        module: Module,
        function: Function?,
        expressions: Arena<Expression>
    ): TypeResolution {
        return when (val kind = expr.kind) {
            is ExpressionKind.Literal -> {
                resolveLiteral(kind.value, module)
            }
            is ExpressionKind.ConstantExpr -> {
                TypeResolution.ByHandle(module.constants[kind.handle].type)
            }
            is ExpressionKind.Unary -> {
                // Unary operations (Negate, Not, BitNot) preserve the type
                this[kind.expr]
            }
            is ExpressionKind.Binary -> {
                resolveBinary(kind, expressions, module)
            }
            is ExpressionKind.Splat -> {
                resolveSplat(kind, expressions, module)
            }
            is ExpressionKind.Swizzle -> {
                resolveSwizzle(kind, expressions, module)
            }
            is ExpressionKind.Access -> {
                resolveAccess(kind, expressions, module)
            }
            is ExpressionKind.AccessIndex -> {
                resolveAccessIndex(kind, expressions, module)
            }
            is ExpressionKind.GlobalVar -> {
                TypeResolution.ByHandle(module.globalVariables[kind.handle].type)
            }
            is ExpressionKind.LocalVar -> {
                TypeResolution.ByHandle(function!!.localVariables[kind.handle].type)
            }
            is ExpressionKind.FunctionArgument -> {
                TypeResolution.ByHandle(function!!.parameters[kind.index].type)
            }
            is ExpressionKind.Call -> {
                val func = module.functions[kind.function]
                if (func.returnType != null) {
                    TypeResolution.ByHandle(func.returnType)
                } else {
                    // Placeholder for void/no result
                    TypeResolution.ByValue(TypeInner.Error)
                }
            }
            is ExpressionKind.As -> {
                TypeResolution.ByHandle(kind.target)
            }
            is ExpressionKind.Load -> {
                val pointerRes = this[kind.pointer]
                when (val inner = pointerRes.getInner(module)) {
                    is TypeInner.Pointer -> TypeResolution.ByHandle(inner.base)
                    is TypeInner.ValuePointer -> TypeResolution.ByHandle(inner.base)
                    else -> TypeResolution.ByValue(TypeInner.Error)
                }
            }
            is ExpressionKind.ArrayLength -> {
                TypeResolution.ByValue(TypeInner.Scalar(ScalarKind.U32, 4))
            }
            is ExpressionKind.TypeConstructor -> {
                TypeResolution.ByHandle(kind.type)
            }
            else -> {
                TypeResolution.ByValue(TypeInner.Error)
            }
        }
    }

    operator fun get(handle: Handle<Expression>): TypeResolution {
        return resolutions[handle.index]
    }

    private fun resolveLiteral(literal: LiteralValue, module: Module): TypeResolution {
        return when (literal) {
            is LiteralValue.Scalar -> {
                val kind = getScalarKind(literal.value)
                val handle = module.types.append(Type(TypeInner.Scalar(kind, 4)))
                TypeResolution.ByHandle(handle)
            }
            is LiteralValue.Vector -> {
                val scalarKind = getScalarKind(literal.components.first())
                val scalarHandle = module.types.append(Type(TypeInner.Scalar(scalarKind, 4)))
                val vectorHandle = module.types.append(Type(TypeInner.Vector(VectorSize.fromInt(literal.components.size), scalarHandle)))
                TypeResolution.ByHandle(vectorHandle)
            }
            is LiteralValue.Matrix -> {
                val scalarKind = getScalarKind(literal.columns.first().first())
                val scalarHandle = module.types.append(Type(TypeInner.Scalar(scalarKind, 4)))
                val matrixHandle = module.types.append(Type(TypeInner.Matrix(
                    VectorSize.fromInt(literal.columns.size),
                    VectorSize.fromInt(literal.columns.first().size),
                    scalarHandle
                )))
                TypeResolution.ByHandle(matrixHandle)
            }
        }
    }

    private fun getScalarKind(value: ScalarValue): ScalarKind = when (value) {
        is ScalarValue.Bool -> ScalarKind.Bool
        is ScalarValue.I32 -> ScalarKind.S32
        is ScalarValue.U32 -> ScalarKind.U32
        is ScalarValue.F32 -> ScalarKind.F32
        is ScalarValue.F64 -> ScalarKind.F64
        is ScalarValue.AbstractInt -> ScalarKind.AbstractInt
        is ScalarValue.AbstractFloat -> ScalarKind.AbstractFloat
        is ScalarValue.I8 -> ScalarKind.Sint
        is ScalarValue.U8 -> ScalarKind.Uint
        is ScalarValue.I16 -> ScalarKind.S16
        is ScalarValue.U16 -> ScalarKind.U16
        is ScalarValue.I64 -> ScalarKind.S64
        is ScalarValue.U64 -> ScalarKind.U64
        is ScalarValue.F16 -> ScalarKind.F16
    }

    private fun resolveBinary(kind: ExpressionKind.Binary, expressions: Arena<Expression>, module: Module): TypeResolution {
        val left = this[kind.left]
        val right = this[kind.right]
        
        return when (kind.operator) {
            BinaryOperator.Equal, BinaryOperator.NotEqual, BinaryOperator.Less, BinaryOperator.LessOrEqual,
            BinaryOperator.Greater, BinaryOperator.GreaterOrEqual -> {
                val leftInner = left.getInner(module)
                if (leftInner is TypeInner.Vector) {
                    val boolHandle = module.types.append(Type(TypeInner.Scalar(ScalarKind.Bool, 1)))
                    TypeResolution.ByHandle(module.types.append(Type(TypeInner.Vector(leftInner.size, boolHandle))))
                } else {
                    TypeResolution.ByValue(TypeInner.Scalar(ScalarKind.Bool, 1))
                }
            }
            BinaryOperator.LogicalAnd, BinaryOperator.LogicalOr -> {
                TypeResolution.ByValue(TypeInner.Scalar(ScalarKind.Bool, 1))
            }
            else -> {
                val leftInner = left.getInner(module)
                val rightInner = right.getInner(module)
                if (leftInner is TypeInner.Scalar && rightInner is TypeInner.Vector) {
                    right
                } else {
                    left
                }
            }
        }
    }

    private fun resolveSplat(kind: ExpressionKind.Splat, expressions: Arena<Expression>, module: Module): TypeResolution {
        val scalarRes = this[kind.value]
        val scalarHandle = when (scalarRes) {
            is TypeResolution.ByHandle -> scalarRes.handle
            is TypeResolution.ByValue -> module.types.append(Type(scalarRes.inner))
        }
        return TypeResolution.ByHandle(module.types.append(Type(TypeInner.Vector(kind.size, scalarHandle))))
    }

    private fun resolveSwizzle(kind: ExpressionKind.Swizzle, expressions: Arena<Expression>, module: Module): TypeResolution {
        val vectorRes = this[kind.vector]
        val vectorInner = vectorRes.getInner(module)
        return if (vectorInner is TypeInner.Vector) {
            TypeResolution.ByValue(TypeInner.Vector(kind.size, vectorInner.scalar))
        } else {
            TypeResolution.ByValue(TypeInner.Error)
        }
    }

    private fun resolveAccess(kind: ExpressionKind.Access, expressions: Arena<Expression>, module: Module): TypeResolution {
        val baseRes = this[kind.expr]
        return when (val inner = baseRes.getInner(module)) {
            is TypeInner.Vector -> TypeResolution.ByHandle(inner.scalar)
            is TypeInner.Matrix -> TypeResolution.ByValue(TypeInner.Vector(inner.rows, inner.scalar))
            is TypeInner.Array -> TypeResolution.ByHandle(inner.element)
            else -> TypeResolution.ByValue(TypeInner.Error)
        }
    }

    private fun resolveAccessIndex(kind: ExpressionKind.AccessIndex, expressions: Arena<Expression>, module: Module): TypeResolution {
        val baseRes = this[kind.expr]
        return when (val inner = baseRes.getInner(module)) {
            is TypeInner.Vector -> TypeResolution.ByHandle(inner.scalar)
            is TypeInner.Matrix -> TypeResolution.ByValue(TypeInner.Vector(inner.rows, inner.scalar))
            is TypeInner.Array -> TypeResolution.ByHandle(inner.element)
            is TypeInner.Struct -> TypeResolution.ByHandle(inner.members[kind.index.toInt()].type)
            else -> TypeResolution.ByValue(TypeInner.Error)
        }
    }
}
