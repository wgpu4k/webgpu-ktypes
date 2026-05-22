# Rapport d'Exploration des Classes IR

> **Package exploré:** `io.ygdrasil.wgsl.ir`  
> **Date:** 2024  
> **Objectif:** Comprendre la structure de l'IR, identifier les patterns, les problèmes de visibilité, et proposer des helpers de vérification pour les tests.

---

## 📋 Table des Matières

1. [Résumé Exécutif](#-résumé-exécutif)
2. [Structure du Package](#-structure-du-package)
3. [Hiérarchie des Classes IR](#-hiérarchie-des-classes-ir)
4. [Système Arena/Handle](#-système-arenahandle)
5. [Visibilité et Accès](#-visibilité-et-accès)
6. [Exemples de Code](#-exemples-de-code)
7. [Problèmes d'Accessibilité](#-problèmes-daccessibilité)
8. [Helpers de Test Recommandés](#-helpers-de-test-recommandés)
9. [Annexes](#-annexes)

---

## 🎯 Résumé Exécutif

L'IR (Intermediate Representation) de ce projet est une **représentation fidèlement inspirée de Naga** (le projet Rust pour WebGPU). Il utilise un système **Arena-based** avec des `Handle<T>` comme références type-safe vers des indices. Toutes les data classes principales sont **publiques** et accessibles depuis les tests. Cependant, certaines fonctions utilitaires de `Handle` sont marquées `internal`, ce qui limite la construction directe d'Handles depuis les tests.

**Points clés:**
- ✅ Toutes les data classes IR sont `public`
- ✅ Les propriétés `inner` (Type) et `kind` (Expression) sont accessibles directement
- ⚠️ `Handle.fromIndex` et `Handle.create` sont `internal`
- ✅ Le pattern recommandé: utiliser `arena.append()` pour créer des éléments et obtenir leurs Handles
- ✅ Des extensions utilitaires existent déjà (ex: `Type.isScalar`, `ScalarKind.isNumeric`)

---

## 📁 Structure du Package

### Localisation
```
wgsl/core/src/commonMain/kotlin/ir/
├── AccessMode.kt          # Enum: Read, Write, ReadWrite
├── AddressSpace.kt       # Enum: Function, Private, Workgroup, Uniform, Storage
├── BinaryOperator.kt     # Enum + extensions (isArithmetic, isBitwise, etc.)
├── ConstValue.kt         # Sealed class pour valeurs constantes évaluées
├── Constant.kt           # Data class Constant avec inner ConstantInner
├── Diagnostic.kt         # Gestion des diagnostics et erreurs
├── Expression.kt         # Data class Expression avec kind ExpressionKind
├── Function.kt           # Data class Function, FunctionParameter, Block, Statement
├── GlobalVariable.kt     # Data class GlobalVariable, Binding
├── LocalVariable.kt      # Data class LocalVariable
├── MatrixSize.kt         # Enum: Mat2x2, Mat3x3, Mat4x4, etc.
├── Module.kt             # Data class Module (racine de l'IR)
├── package.kt            # Package-level declarations
├── ScalarKind.kt         # Enum + extensions (isInteger, isFloat, isNumeric, width)
├── Span.kt               # Source code span pour error reporting
├── StorageClass.kt       # Enum + extension isMutable
├── Type.kt               # Data class Type avec inner TypeInner + extensions
├── UnaryOperator.kt      # Enum: Negate, Not, BitNot
└── VectorSize.kt         # Enum: Bi, Tri, Quad
```

### Package Associé: Arena
```
wgsl/core/src/commonMain/kotlin/arena/
├── Arena.kt              # Mutable collection avec Handles
├── Handle.kt             # @JvmInline value class pour références type-safe
├── Range.kt              # Range d'indices pour Statement.Emit
├── UniqueArena.kt        # Arena garantissant l'unicité (pour Type)
└── package.kt
```

---

## 🏗️ Hiérarchie des Classes IR

### 1. Module (Racine)

```kotlin
@Serializable
data class Module(
    val types: UniqueArena<Type> = UniqueArena(),
    val constants: Arena<Constant> = Arena(),
    val globalExpressions: Arena<Expression> = Arena(),
    val globalVariables: Arena<GlobalVariable> = Arena(),
    val functions: Arena<Function> = Arena(),
    val entryPoints: MutableList<EntryPoint> = mutableListOf(),
    val specialTypes: SpecialTypes = SpecialTypes(),
    val diagnosticFilters: Arena<DiagnosticFilterNode> = Arena(),
    val diagnosticFilterLeaf: Handle<DiagnosticFilterNode>? = null,
    val docComments: DocComments? = null,
)
```

**EntryPoint:**
```kotlin
@Serializable
data class EntryPoint(
    val name: String,
    val function: Handle<Function>,
    val stage: ShaderStage,  // Vertex, Fragment, Compute
    val workgroupSize: List<Int>? = null,
    val earlyDepthTest: EarlyDepthTest? = null,
    val bindings: List<BindingAttribute> = emptyList(),
)
```

### 2. Type et TypeInner (Sealed Class)

```kotlin
@Serializable
data class Type(val inner: TypeInner) : Equatable

@Serializable
sealed class TypeInner : Equatable {
    data class Scalar(val kind: ScalarKind, val width: Int)
    data class Vector(val size: VectorSize, val scalar: Handle<Type>)
    data class Matrix(val columns: VectorSize, val rows: VectorSize, val scalar: Handle<Type>)
    data class Array(val element: Handle<Type>, val size: ArraySize)
    data class Struct(val members: List<StructMember>)
    data class Pointer(val base: Handle<Type>, val addressSpace: AddressSpace, val accessMode: AccessMode? = null)
    data class ValuePointer(val base: Handle<Type>)
    data class Opaque(val name: String)
    object Error
    data class Abstract(val scalar: ScalarKind)
}
```

**Extensions Type:**
```kotlin
val Type.isScalar: Boolean
val Type.isVector: Boolean
val Type.isMatrix: Boolean
val Type.isArray: Boolean
val Type.isStruct: Boolean
val Type.isPointer: Boolean
val Type.isNumeric: Boolean
```

### 3. Expression et ExpressionKind (Sealed Class)

```kotlin
@Serializable
data class Expression(val kind: ExpressionKind)

@Serializable
sealed class ExpressionKind {
    // Littéraux
    data class Literal(val value: LiteralValue)
    
    // Variables
    data class GlobalVar(val handle: Handle<GlobalVariable>)
    data class LocalVar(val handle: Handle<LocalVariable>)
    data class FunctionArgument(val index: Int)
    
    // Constructeurs
    data class TypeConstructor(val type: Handle<Type>, val arguments: List<Handle<Expression>>)
    
    // Opérations
    data class Unary(val operator: UnaryOperator, val expr: Handle<Expression>)
    data class Binary(val operator: BinaryOperator, val left: Handle<Expression>, val right: Handle<Expression>)
    data class Select(val condition: Handle<Expression>, val accept: Handle<Expression>, val reject: Handle<Expression>)
    
    // Appels
    data class Call(val function: Handle<Function>, val arguments: List<Handle<Expression>>)
    data class BuiltinCall(val function: BuiltinFunction, val arguments: List<Handle<Expression>>)
    
    // Constantes
    data class ConstantExpr(val handle: Handle<Constant>)
    
    // Vecteurs
    data class Splat(val size: VectorSize, val value: Handle<Expression>)
    data class Swizzle(val size: VectorSize, val vector: Handle<Expression>, val pattern: List<Int>)
    
    // Pointeurs
    data class Load(val pointer: Handle<Expression>)
    data class Store(val pointer: Handle<Expression>, val value: Handle<Expression>)
    data class ValuePointer(val base: Handle<Expression>)
    
    // Accès
    data class Access(val expr: Handle<Expression>, val index: Handle<Expression>)
    data class AccessIndex(val expr: Handle<Expression>, val index: Int)
    
    // Textures
    data class Sample(val texture: Handle<Expression>, val sampler: Handle<Expression>?, ...)
    data class TextureQuery(val texture: Handle<Expression>, val query: TextureQueryKind)
    
    // Arrays
    data class ArrayLength(val expr: Handle<Expression>)
    
    // Casts
    data class As(val expr: Handle<Expression>, val target: Handle<Type>)
    data class Bitcast(val expr: Handle<Expression>)
    
    // Relationnels
    data class Relational(val fun_: RelationalFunction, val arguments: List<Handle<Expression>>)
    
    // Atomiques
    data class Atomic(val pointer: Handle<Expression>, val fun_: AtomicFunction, ...)
    
    // Ray Query
    data class RayQuery(val query: RayQueryKind, val arguments: List<Handle<Expression>>)
}
```

### 4. Statement (Sealed Class)

```kotlin
@Serializable
sealed class Statement {
    object Nop
    data class Block(val block: Handle<Block>)
    data class Declare(val variable: Handle<LocalVariable>)
    data class Init(val variable: Handle<LocalVariable>)
    data class Assign(val pointer: Handle<Expression>, val value: Handle<Expression>)
    data class Emit(val range: Range<Expression>)
    data class If(val condition: Handle<Expression>, val accept: Handle<Block>, val reject: Handle<Block>? = null)
    data class Switch(val selector: Handle<Expression>, val body: Handle<Block>, ...)
    data class Loop(val body: Handle<Block>, val continuing: Handle<Block>? = null)
    object Break
    object Continue
    data class Return(val value: Handle<Expression>? = null)
    object Discard
    object Kill
}
```

### 5. Function et Composants

```kotlin
@Serializable
data class Function(
    val name: String,
    val parameters: List<FunctionParameter>,
    val returnType: Handle<Type>? = null,
    val localVariables: Arena<LocalVariable>,
    val expressions: Arena<Expression>,
    val blocks: Arena<Block>,
    val body: Handle<Block>,
    val result: Handle<Expression>? = null,
)

@Serializable
data class FunctionParameter(val name: String, val type: Handle<Type>, val binding: BindingAttribute? = null)

@Serializable
data class Block(val statements: List<Statement>)
```

### 6. Variables

**GlobalVariable:**
```kotlin
@Serializable
data class GlobalVariable(
    val name: String,
    val storageClass: StorageClass,
    val accessMode: AccessMode? = null,
    val type: Handle<Type>,
    val init: Handle<Expression>? = null,
    val binding: Binding? = null,
)

@Serializable
data class Binding(val group: Int, val index: Int)
```

**LocalVariable:**
```kotlin
@Serializable
data class LocalVariable(
    val name: String,
    val type: Handle<Type>,
    val init: Handle<Expression>? = null,
)
```

---

## 🎯 Système Arena/Handle

### Handle<T>

```kotlin
@Serializable(HandleSerializer::class)
@JvmInline
value class Handle<T>(val index: Int) {
    companion object {
        val INVALID: Handle<Nothing> = Handle(-1)
        internal fun <T> fromIndex(index: Int): Handle<T> = Handle(index)
        internal fun <U> create(index: Int): Handle<U> = Handle(index)
    }
    
    fun isValid(): Boolean = index >= 0
    fun isInvalid(): Boolean = !isValid()
}
```

### Arena<T>

```kotlin
@Serializable
class Arena<T> : Iterable<T>, Collection<T>, MutableCollection<T> {
    internal val data: MutableList<T> = mutableListOf()
    
    fun append(value: T): Handle<T>
    operator fun get(handle: Handle<T>): T
    fun getOrNull(handle: Handle<T>): T?
    fun findHandle(predicate: (T) -> Boolean): Handle<T>?
    fun forEachWithHandle(action: (Handle<T>, T) -> Unit)
}
```

### UniqueArena<T> where T : Equatable

```kotlin
@Serializable
class UniqueArena<T> where T : Equatable {
    internal val data: MutableList<T> = mutableListOf()
    internal val indexMap: MutableMap<T, Int> = mutableMapOf()
    
    fun append(value: T): Handle<T>  // Retourne Handle existant ou nouveau
    fun contains(value: T): Boolean
    fun findHandle(value: T): Handle<T>?
}
```

---

## 🔓 Visibilité et Accès

### ✅ Accessible depuis les tests

- `Type.inner` - public val
- `Expression.kind` - public val
- `Function.body` - public val
- `Module.types` / `Module.functions` - public val
- `Arena.append()` - public fun
- `Handle.index` / `Handle.isValid()` - public
- Toutes les data classes et enums

### ⚠️ Restrictions

- `Handle.fromIndex()` - INTERNAL - Utiliser `arena.append()`
- `Handle.create()` - INTERNAL - Utiliser `arena.append()`
- `Arena.data` - INTERNAL - Utiliser méthodes publiques

**Conclusion:** Tous les types nécessaires sont accessibles. La seule limitation est la création directe de Handles, mais `arena.append()` fournit une alternative parfaite.

---

## 💻 Exemples de Code

### 1. Construire un Module IR Manuellement

```kotlin
import io.ygdrasil.wgsl.ir.*

fun buildSimpleModule(): Module {
    val module = Module()
    
    // Créer des types scalaires
    val f32 = module.types.append(Type(TypeInner.Scalar(ScalarKind.F32, 4)))
    val i32 = module.types.append(Type(TypeInner.Scalar(ScalarKind.Sint, 4)))
    
    // Créer un type vecteur vec3<f32>
    val vec3 = module.types.append(Type(TypeInner.Vector(VectorSize.Tri, f32)))
    
    // Créer un type struct
    val structType = module.types.append(Type(TypeInner.Struct(listOf(
        StructMember("position", vec3, offset = 0),
        StructMember("color", vec3, offset = 12)
    ))))
    
    // Créer des expressions
    val one = module.globalExpressions.append(
        Expression(ExpressionKind.Literal(LiteralValue.Scalar(ScalarValue.F32(1.0f))))
    )
    val two = module.globalExpressions.append(
        Expression(ExpressionKind.Literal(LiteralValue.Scalar(ScalarValue.F32(2.0f))))
    )
    val addExpr = module.globalExpressions.append(
        Expression(ExpressionKind.Binary(BinaryOperator.Add, one, two))
    )
    
    // Créer une constante
    val myConst = module.constants.append(Constant(
        type = f32,
        inner = ConstantInner.Scalar(ScalarValue.F32(3.14f))
    ))
    
    // Créer une variable globale
    val globalVar = module.globalVariables.append(GlobalVariable(
        name = "myGlobal",
        storageClass = StorageClass.Private,
        type = f32,
        init = one
    ))
    
    return module
}
```

### 2. Accéder aux Members d'un Type

```kotlin
fun inspectType(module: Module, type: Type) {
    when (val inner = type.inner) {
        is TypeInner.Scalar -> println("Scalar: ${inner.kind}, width: ${inner.width}")
        is TypeInner.Vector -> {
            val scalarType = module.types[inner.scalar]
            println("Vector: size=${inner.size}, scalar=$scalarType")
        }
        is TypeInner.Struct -> {
            println("Struct with ${inner.members.size} members:")
            inner.members.forEachIndexed { i, member ->
                println("  [$i] ${member.name}: ${module.types[member.type]}")
            }
        }
        // ... autres cas
    }
}
```

### 3. Vérifier qu'une Expression est un Literal

```kotlin
// Extension property
val Expression.isLiteral: Boolean
    get() = kind is ExpressionKind.Literal

// Avec accès à la valeur
fun Expression.getLiteralValue(): LiteralValue? {
    return (kind as? ExpressionKind.Literal)?.value
}

// Utilisation
val expr: Expression = module.globalExpressions.append(
    Expression(ExpressionKind.Literal(LiteralValue.Scalar(ScalarValue.I32(42))))
)

if (expr.isLiteral) {
    val literalValue = expr.getLiteralValue()
    println("Literal: $literalValue")
}
```

---

## ⚠️ Problèmes d'Accessibilité

### 1. Handle.fromIndex est internal

**Problème:** Impossible de créer un Handle avec un index spécifique depuis les tests.

```kotlin
// ❌ NE FONCTIONNE PAS
val handle = Handle.fromIndex<MyType>(5)

// ✅ FONCTIONNE
val arena = Arena<MyType>()
val handle = arena.append(MyType(...))  // Retourne Handle(0), Handle(1), etc.
```

### 2. ✅ Pas de problème avec Type.inner et Expression.kind

Ces propriétés sont **publiques** et accessibles directement.

---

## 🛠️ Helpers de Test Recommandés

### Module Assertions

```kotlin
fun Module.shouldHaveType(predicate: (Type) -> Boolean): Boolean {
    return types.any { predicate(it) }
}

fun Module.shouldHaveScalarType(kind: ScalarKind, width: Int = 4): Boolean {
    return shouldHaveType { 
        it.inner is TypeInner.Scalar && 
        it.inner.kind == kind && 
        it.inner.width == width 
    }
}

fun Module.shouldHaveFunction(name: String): Boolean {
    return functions.any { it.name == name }
}

// Usage
module.shouldHaveScalarType(ScalarKind.F32) shouldBe true
```

### Type Checkers

```kotlin
fun Type.isScalar(kind: ScalarKind, width: Int = 4): Boolean {
    return inner is TypeInner.Scalar && 
           (inner as TypeInner.Scalar).kind == kind && 
           (inner as TypeInner.Scalar).width == width
}

fun Type.isVector(size: VectorSize): Boolean {
    return inner is TypeInner.Vector && 
           (inner as TypeInner.Vector).size == size
}

fun Type.getStructMember(name: String): StructMember? {
    return (inner as? TypeInner.Struct)?.members?.find { it.name == name }
}
```

### Expression Checkers

```kotlin
fun Expression.isLiteral(): Boolean = kind is ExpressionKind.Literal
fun Expression.isBinary(operator: BinaryOperator): Boolean {
    return kind is ExpressionKind.Binary && 
           (kind as ExpressionKind.Binary).operator == operator
}
fun Expression.isBinaryAdd(): Boolean = isBinary(BinaryOperator.Add)
fun Expression.isGlobalVar(handle: Handle<GlobalVariable>): Boolean {
    return kind is ExpressionKind.GlobalVar && 
           (kind as ExpressionKind.GlobalVar).handle == handle
}

fun Expression.getLiteralScalarValue(): ScalarValue? {
    return (kind as? ExpressionKind.Literal)?.value?.let { literalValue ->
        (literalValue as? LiteralValue.Scalar)?.value
    }
}
```

### Statement Checkers

```kotlin
fun Statement.isReturn(): Boolean = this is Statement.Return
fun Statement.isReturnWithValue(): Boolean = this is Statement.Return && this.value != null
fun Statement.isBreak(): Boolean = this is Statement.Break
fun Statement.isDeclare(handle: Handle<LocalVariable>): Boolean {
    return this is Statement.Declare && this.variable == handle
}
```

### Builders pour construction facile

```kotlin
class IrModuleBuilder {
    val module = Module()
    
    fun scalar(kind: ScalarKind, width: Int = 4): Handle<Type> {
        val inner = TypeInner.Scalar(kind, width)
        return module.types.append(Type(inner))
    }
    
    fun vector(size: VectorSize, scalar: Handle<Type>): Handle<Type> {
        val inner = TypeInner.Vector(size, scalar)
        return module.types.append(Type(inner))
    }
    
    fun literalScalar(value: ScalarValue): Handle<Expression> {
        return module.globalExpressions.append(
            Expression(ExpressionKind.Literal(LiteralValue.Scalar(value)))
        )
    }
    
    fun binary(op: BinaryOperator, left: Handle<Expression>, right: Handle<Expression>): Handle<Expression> {
        return module.globalExpressions.append(
            Expression(ExpressionKind.Binary(op, left, right))
        )
    }
    
    fun function(
        name: String,
        returnType: Handle<Type>? = null,
        vararg params: Pair<String, Handle<Type>>,
        bodyBuilder: IrFunctionBuilder.() -> Unit
    ): Handle<Function> {
        // Implementation avec IrFunctionBuilder
    }
}

// Usage
val builder = IrModuleBuilder()
val f32 = builder.scalar(ScalarKind.F32)
val one = builder.literalScalar(ScalarValue.I32(1))
val two = builder.literalScalar(ScalarValue.I32(2))
val add = builder.binary(BinaryOperator.Add, one, two)

---

## 📚 Annexes

### A. Liste Complète des Types dans le Package IR

| Fichier | Types Définis |
|--------|---------------|
| AccessMode.kt | `AccessMode` (enum: Read, Write, ReadWrite) |
| AddressSpace.kt | `AddressSpace` (enum: Function, Private, Workgroup, Uniform, Storage) |
| BinaryOperator.kt | `BinaryOperator` (enum: Add, Subtract, Multiply, Divide, Modulo, BitAnd, BitOr, BitXor, ShiftLeft, ShiftRight, Equal, NotEqual, Less, LessOrEqual, Greater, GreaterOrEqual, LogicalAnd, LogicalOr) + extensions |
| ConstValue.kt | `ConstValue` (sealed), `notConst()`, `isConst()`, `isConstOrNull()` |
| Constant.kt | `Constant`, `ConstantInner` (sealed: Scalar, Vector, Matrix, Zero, Composite, Expression) |
| Diagnostic.kt | `DiagnosticSeverity` (enum), `Diagnostic`, `ShaderError`, `DiagnosticBuilder`, `diagnostic()` |
| Expression.kt | `Expression`, `ExpressionKind` (sealed), `LiteralValue` (sealed), `ScalarValue` (sealed), `SampleLevel` (sealed), `TextureQueryKind` (enum), `RelationalFunction` (enum), `AtomicFunction` (enum), `RayQueryKind` (enum) |
| Function.kt | `Function`, `FunctionParameter`, `Block`, `BuiltinValue` (enum), `Statement` (sealed), `Case`, `CaseSelector` (sealed), `BuiltinFunction` (enum: 50+ valeurs) |
| GlobalVariable.kt | `GlobalVariable`, `Binding` |
| LocalVariable.kt | `LocalVariable` |
| MatrixSize.kt | `MatrixSize` (enum: Mat2x2, Mat2x3, Mat2x4, Mat3x2, Mat3x3, Mat3x4, Mat4x2, Mat4x3, Mat4x4) + extensions |
| Module.kt | `Module`, `SpecialTypes`, `EntryPoint`, `ShaderStage` (enum), `EarlyDepthTest` (sealed), `BindingAttribute` (sealed), `Sampling` (enum), `InterpolationType` (enum), `DiagnosticFilterNode` (sealed), `DocComments` |
| ScalarKind.kt | `ScalarKind` (enum: Bool, Sint, Uint, S16, U16, S32, U32, S64, U64, F16, F32, F64, AbstractInt, AbstractFloat) + extensions |
| Span.kt | `Span`, `SourceLocation`, `SpanContext` (typealias) |
| StorageClass.kt | `StorageClass` (enum: Function, Private, Workgroup, Uniform, Storage, PushConstant, Handle) + extension isMutable |
| Type.kt | `Type`, `TypeInner` (sealed), `StructMember`, `ArraySize` (sealed: Constant, Dynamic) + extensions |
| UnaryOperator.kt | `UnaryOperator` (enum: Negate, Not, BitNot) |
| VectorSize.kt | `VectorSize` (enum: Bi, Tri, Quad) + extension value |

### B. Résumé des Sealed Classes

#### TypeInner (10 types)
1. `Scalar(kind: ScalarKind, width: Int)`
2. `Vector(size: VectorSize, scalar: Handle<Type>)`
3. `Matrix(columns: VectorSize, rows: VectorSize, scalar: Handle<Type>)`
4. `Array(element: Handle<Type>, size: ArraySize)`
5. `Struct(members: List<StructMember>)`
6. `Pointer(base: Handle<Type>, addressSpace: AddressSpace, accessMode: AccessMode?)`
7. `ValuePointer(base: Handle<Type>)`
8. `Opaque(name: String)`
9. `Error`
10. `Abstract(scalar: ScalarKind)`

#### ExpressionKind (25+ types)
- **Littéraux:** `Literal`
- **Variables:** `GlobalVar`, `LocalVar`, `FunctionArgument`
- **Constructeurs:** `TypeConstructor`
- **Opérations:** `Unary`, `Binary`, `Select`
- **Appels:** `Call`, `BuiltinCall`
- **Constantes:** `ConstantExpr`
- **Vecteurs:** `Splat`, `Swizzle`
- **Pointeurs:** `Load`, `Store`, `ValuePointer`
- **Accès:** `Access`, `AccessIndex`
- **Textures:** `Sample`, `TextureQuery`
- **Arrays:** `ArrayLength`
- **Casts:** `As`, `Bitcast`
- **Relationnels:** `Relational`
- **Atomiques:** `Atomic`
- **Ray Query:** `RayQuery`

#### Statement (14 types)
1. `Nop`
2. `Block`
3. `Declare`
4. `Init`
5. `Assign`
6. `Emit`
7. `If`
8. `Switch`
9. `Loop`
10. `Break`
11. `Continue`
12. `Return`
13. `Discard`
14. `Kill`

#### ScalarValue (14 types)
Bool, I8, U8, I16, U16, I32, U32, I64, U64, F16, F32, F64, AbstractInt, AbstractFloat

### C. Extensions Existantes

#### ScalarKind Extensions
- `width: Int?` - Retourne la taille en bytes (null pour abstract)
- `isInteger: Boolean` - Vrai pour les types entiers
- `isSigned: Boolean` - Vrai pour les entiers signés
- `isFloat: Boolean` - Vrai pour les flottants
- `isNumeric: Boolean` - Vrai pour integer ou float

#### Type Extensions
- `isScalar: Boolean`
- `isVector: Boolean`
- `isMatrix: Boolean`
- `isArray: Boolean`
- `isStruct: Boolean`
- `isPointer: Boolean`
- `isNumeric: Boolean`

#### BinaryOperator Extensions
- `isArithmetic: Boolean`
- `isBitwise: Boolean`
- `isComparison: Boolean`
- `isLogical: Boolean`

#### StorageClass Extensions
- `isMutable: Boolean`

#### VectorSize Extensions
- `value: Int` - Retourne 2, 3, ou 4

#### MatrixSize Extensions
- `columns: Int`
- `rows: Int`

### D. Pattern de Construction Recommandé

```kotlin
// 1. Toujours utiliser arena.append() pour créer des éléments
val module = Module()
val typeHandle = module.types.append(Type(TypeInner.Scalar(ScalarKind.F32, 4)))
val exprHandle = module.globalExpressions.append(Expression(...))

// 2. Utiliser les extensions pour les vérifications
if (module.types[typeHandle].isScalar) { ... }

// 3. Pattern matching avec when pour inspecter les types
when (val inner = type.inner) {
    is TypeInner.Scalar -> { ... }
    is TypeInner.Vector -> { ... }
    // etc.
}

// 4. Accéder aux éléments via les Arenas
val type: Type = module.types[typeHandle]
val expr: Expression = module.globalExpressions[exprHandle]

// 5. Utiliser Handle.INVALID pour les cas sentinelle
val invalid: Handle<MyType> = Handle.INVALID
if (handle.isValid()) { ... }
```

### E. Bonnes Pratiques pour les Tests

```kotlin
// ✅ DO:
// - Utiliser arena.append() pour créer des éléments
// - Utiliser les extensions existantes (isScalar, isNumeric, etc.)
// - Utiliser le pattern matching avec when
// - Stocker les Handles pour référence future

// ❌ DON'T:
// - Essayer de créer des Handles directement avec fromIndex
// - Accéder à Arena.data directement
// - Supposer que les indices sont stables après modification
// - Oublier de vérifier handle.isValid()
```

---

## ✅ Conclusion

L'IR est **bien conçu et accessible depuis les tests**. Les quelques limitations (`Handle.fromIndex` étant internal) sont facilement contournables en utilisant le pattern `arena.append()`. 

**Recommandations:**
1. ✅ Créer un fichier `IrAssertions.kt` ou `IrMatchers.kt` avec tous les helpers de test
2. ✅ Utiliser des builders pour simplifier la construction manuelle de l'IR
3. ✅ Ajouter des extensions pour les vérifications courantes (isLiteral, isBinary, etc.)
4. ✅ Documenter les patterns de construction dans le code

**Toutes les data classes et propriétés nécessaires pour les tests sont publiques et accessibles.**

---

*Rapport généré par exploration du codebase webgpu-ktypes*
*Package: io.ygdrasil.wgsl.ir*
