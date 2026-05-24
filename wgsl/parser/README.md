# WGSL Parser Module

> **WebGPU Shading Language (WGSL) Parser** - A comprehensive Kotlin implementation for parsing WGSL shader code into an Abstract Syntax Tree (AST) and lowering it to Intermediate Representation (IR).

---

## 📋 Table of Contents

1. [Overview](#-overview)
2. [Architecture](#-architecture)
3. [Parsing Pipeline](#-parsing-pipeline)
4. [Module Components](#-module-components)
5. [Data Flow](#-data-flow)
6. [Error Handling](#-error-handling)
7. [Integration](#-integration)
8. [Examples](#-examples)
9. [Module Dependencies](#-module-dependencies)
10. [Coverage](docs/WGSL_PARSER_COVERAGE.md)

---

## 📋 Overview

The **WGSL Parser Module** is responsible for reading, analyzing, and transforming WGSL (WebGPU Shading Language) source code into a structured Intermediate Representation (IR) that can be consumed by code generators (GLSL, MSL, HLSL, etc.).

### Key Responsibilities

- ✅ **Lexical Analysis**: Tokenizing WGSL source code
- ✅ **Syntax Parsing**: Building Abstract Syntax Tree (AST) from tokens
- ✅ **Type Resolution**: Resolving type references and validating type compatibility
- ✅ **Semantic Analysis**: Validating semantic rules (forward references, cycles, etc.)
- ✅ **IR Lowering**: Converting AST to Intermediate Representation
- ✅ **Error Recovery**: Graceful handling of syntax errors

### Supported WGSL Features

| Category | Features | Status |
|----------|----------|--------|
| **Control Flow** | `if`, `else`, `switch`, `case`, `default`, `loop`, `while`, `for`, `break`, `continue`, `return`, `discard` | ✅ |
| **Declarations** | `fn`, `let`, `const`, `var`, `type`, `struct`, `const_assert` | ✅ |
| **Types** | Scalars (`i32`, `u32`, `f32`, etc.), Vectors, Matrices, Arrays, Pointers, Structs, **Abstract Types (`abstract int`, `abstract float`)** | ✅ |
| **Expressions** | Literals, Identifiers, Binary/Unary ops, Function calls, Member access, Indexing | ✅ |
| **Attributes** | `@location`, `@builtin`, `@binding`, `@group`, `@invariant`, `@must_use`, `@override` | ✅ |
| **Storage Classes** | `uniform`, `storage`, `workgroup`, `private`, `function` | ✅ |
| **Access Modes** | `read`, `write`, `read_write` | ✅ |
| **Built-ins** | All WGSL built-in functions and values | ✅ |
| **Enumerations** | User-defined enums (`enum`) and **Predeclared Enumerants** (AddressMode, FilterMode, etc.) | ✅ |

---

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────────────────────────┐
│                        WGSL Parser Module                               │
├─────────────────────────────────────────────────────────────────────┤
│                                                                       │
│  ┌──────────────┐    ┌──────────────┐    ┌──────────────┐         │
│  │    Lexer     │    │   Parser    │    │ TypeResolver │         │
│  │              │───▶│              │───▶│              │         │
│  └──────────────┘    └──────────────┘    └──────────────┘         │
│           │                    │                    │                │
│           ▼                    ▼                    ▼                │
│  ┌──────────────┐    ┌──────────────┐    ┌──────────────┐         │
│  │   Tokens     │    │    AST       │    │  Resolved    │         │
│  │  (Stream)    │    │ (Translation │    │   AST       │         │
│  └──────────────┘    │   Unit)      │    └──────────────┘         │
│                      └──────────────┘                  │                │
│                             │                            │                │
│                             ▼                            ▼                │
│                      ┌──────────────┐    ┌──────────────┐         │
│                      │ ModuleIndexer │    │   Lowerer    │         │
│                      │  (Topological │───▶│              │         │
│                      │   Sorting)   │    │ (AST → IR)   │         │
│                      └──────────────┘    └──────────────┘         │
│                                             │                          │
│                                             ▼                          │
│                                      ┌──────────────┐                  │
│                                      │     IR       │                  │
│                                      │  (Module)    │                  │
│                                      └──────────────┘                  │
│                                                                       │
└─────────────────────────────────────────────────────────────────────┘
```

---

## 🔄 Parsing Pipeline

### Step 1: Lexical Analysis (`Lexer.kt`)

**Input**: WGSL source code string
**Output**: Stream of `Token` objects

```kotlin
val source = """
    @vertex
    fn main() -> @builtin(position) vec4<f32> {
        return vec4<f32>(0.0);
    }
"""

val lexer = Lexer(source)
val tokens = lexer.tokenizeSignificant()
// Produces: [AT, VERTEX, FN, IDENTIFIER, ..., RETURN, VEC, ...]
```

#### Token Categories

```
┌─────────────────────────────────────────────────────────────┐
│ TokenKind Enum (108+ values)                                    │
├─────────────────────────────────────────────────────────────┤
│                                                                    │
│  EOF & Whitespace:                                                │
│    - EOF, WHITESPACE, SINGLE_LINE_COMMENT, MULTI_LINE_COMMENT    │
│                                                                    │
│  Literals:                                                      │
│    - IDENTIFIER, INT_LITERAL, UINT_LITERAL, FLOAT_LITERAL       │
│    - BOOL_LITERAL, STRING_LITERAL                              │
│                                                                    │
│  Keywords (108 total):                                          │
│    - Control Flow: if, else, switch, case, default, loop, while,│
│      for, break, continue, return, discard, continuing           │
│    - Declarations: fn, let, const, var, type, struct, alias    │
│    - Type Constructors: array, mat, vec, ptr                     │
│    - Storage Classes: uniform, storage, workgroup, private,    │
│      function                                                    │
│    - Attributes: @location, @builtin, @binding, @group,         │
│      @enable, @requires, @interpolate, @invariant, @must_use,   │
│      @override, @compute, @fragment, @vertex                  │
│    - Built-in Types: bool, i8, u8, i16, u16, i32, u32, i64,   │
│      u64, f16, f32, f64                                          │
│    - Texture Types: texture_1d, texture_2d, texture_2d_array,   │
│      texture_3d, sampler, etc.                                   │
│    - Access Modes: read, write, read_write                       │
│    - Layout: packed, aligned                                      │
│    - Template: where                                             │
│                                                                    │
│  Operators: +, -, *, /, %, &, |, ^, ~, <<, >>, &&, ||, !       │
│  Comparison: ==, !=, <, >, <=, >=                              │
│  Assignment: =, +=, -=, *=, /=, %=, &=, |=, ^=, <<=, >>=       │
│  Increment: ++, --                                               │
│                                                                    │
│  Punctuation: (, ), {, }, [, ], ,, ., :, ;, ::, .*, ->, =>,    │
│               <, >, <>, ?, _                                     │
└─────────────────────────────────────────────────────────────┘
```

### Step 2: Syntax Parsing (`Parser.kt`)

**Input**: Token stream
**Output**: `TranslationUnit` (AST root)

```kotlin
val parser = Parser(lexer)
val translationUnit = parser.parse()
// translationUnit contains:
// - declarations: List<GlobalDecl> (functions, structs, variables, etc.)
// - diagnostics: List<Diagnostic> (errors, warnings)
```

#### AST Node Types

```
TranslationUnit (root)
├── GlobalDecl
│   ├── FunctionDecl (fn main() {...})
│   │   ├── name: String
│   │   ├── parameters: List<Param>
│   │   ├── returnType: Type?
│   │   ├── body: BlockStatement
│   │   └── attributes: List<Attribute>
│   │
│   ├── StructDecl (struct MyStruct {...})
│   │   ├── name: String
│   │   ├── members: List<StructMember>
│   │   └── attributes: List<Attribute>
│   │
│   ├── VariableDeclGlobal (var x: i32 = 0;)
│   │   ├── name: String
│   │   ├── type: Type
│   │   ├── init: Expression?
│   │   └── storageClass: StorageClass?
│   │
│   ├── TypeDecl (type MyType = vec4<f32>;)
│   │   ├── name: String
│   │   └── type: Type
│   │
│   └── ConstAssertDecl (const_assert 1 + 1 == 2;)
│       └── expression: Expression
│
├── Statement
│   ├── BlockStatement ({ ... })
│   ├── IfStatement (if (cond) {...})
│   ├── SwitchStatement (switch (x) {case 1: ...})
│   ├── LoopStatement (loop {...})
│   ├── WhileStatement (while (cond) {...})
│   ├── ForStatement (for (init; cond; update) {...})
│   ├── BreakStatement (break;)
│   ├── ContinueStatement (continue;)
│   ├── ReturnStatement (return x;)
│   ├── DiscardStatement (discard;)
│   ├── LetStatement (let x = 5;)
│   ├── ConstStatement (const x = 5;)
│   ├── VarStatement (var x = 5;)
│   ├── AssignmentStatement (x = 5;)
│   ├── ExpressionStatement (foo();)
│   └── ConstAssertStatement (const_assert x > 0;)
│
└── Expression
    ├── Literal (1, 2.5, true, "hello")
    ├── IdentExpr (x, foo)
    ├── BinaryExpr (a + b, x * y)
    ├── UnaryExpr (-x, !cond)
    ├── CallExpr (foo(a, b))
    ├── MemberAccessExpr (obj.member)
    ├── IndexExpr (arr[0])
    ├── CastExpr (i32(x))
    ├── TernaryExpr (a ? b : c)
    └── ... (26 expression kinds)
```

### Step 3: Module Indexing (`ModuleIndexer.kt`)

**Input**: `TranslationUnit`
**Output**: Topologically sorted declarations

Resolves forward references in WGSL:

```wgsl
// Forward reference: b() is called before it's declared
fn a() {
    b();  // ← Forward reference to b()
}

fn b() {  // ← Declared later
    return;
}
```

The `ModuleIndexer` performs:
1. **Dependency Analysis**: Builds a graph of declarations and their dependencies
2. **Cycle Detection**: Identifies circular dependencies (throws `CycleDetectedException`)
3. **Topological Sort**: Orders declarations so all dependencies are resolved first

```kotlin
val sortedUnit = ModuleIndexer().reorderDeclarations(translationUnit)
// sortedUnit.declarations contains declarations in dependency order
```

### Step 4: Type Resolution (`TypeResolver.kt`)

**Input**: `TranslationUnit` with indexed declarations
**Output**: `ResolutionResult` with resolved types

Validates and resolves all type references:

```kotlin
val resolutionResult = TypeResolver().resolve(translationUnit)

// resolutionResult contains:
// - resolvedUnit: TranslationUnit with all types resolved
// - isSuccess: Boolean indicating if resolution succeeded
// - unresolvedReferences: List of unresolved type references (if any)
```

#### Type Resolution Process

```
1. Collect all declared types (structs, aliases, etc.)
2. Build TypeIndex for fast lookup
3. Resolve each expression's type:
   - Literals: Determine type from value (1 → i32, 1.5 → f32)
   - Identifiers: Look up declared type
   - Binary expressions: Determine result type from operands
   - Function calls: Validate argument types against parameters
   - Member access: Validate member exists on type
4. Validate type constraints:
   - Array indexing: index must be integral type
   - Function arguments: types must match parameter types
   - Assignment: RHS type must be compatible with LHS type
```

### Step 5: IR Lowering (`Lowerer.kt`)

**Input**: Resolved `TranslationUnit`
**Output**: `Module` (IR)

Transforms high-level AST into low-level Intermediate Representation:

```kotlin
val irModule = Lowerer().lower(resolvedUnit)

// irModule contains:
// - types: List<Type>
// - functions: List<Function>
// - globalVariables: List<GlobalVariable>
// - ...
```

#### Lowering Transformations

| AST Concept | IR Representation |
|------------|-------------------|
| `if` statement | `If` IR statement with condition, then, else blocks |
| `for` loop | `Loop` IR statement with init, condition, continuing, body |
| `switch` | `Switch` IR statement with cases |
| Function call | `Call` IR expression |
| Binary expression | `Binary` IR expression |
| Variable declaration | `Let` or `Var` IR statement |
| Struct declaration | `Type` with `TypeInner.Struct` |
| Array type | `Type` with `TypeInner.Array` |
| Pointer type | `Type` with `TypeInner.Pointer` |

---

## 📦 Module Components

### Core Classes

#### `Parser.kt`

The main parser class that implements the complete WGSL grammar.

**Key Methods:**
- `parse(): TranslationUnit` - Entry point, parses entire WGSL source
- `parseTopLevelDecl(): GlobalDecl` - Parses a top-level declaration
- `parseTypeDecl(): Type` - Parses a type declaration
- `parseExpression(): Expression` - Parses an expression
- `parseStatement(): Statement` - Parses a statement
- `parseAttribute(): Attribute` - Parses an attribute

**Grammar Coverage:**
- Full WGSL specification support
- Recursive descent parsing
- Error recovery for malformed input

#### `AstBuilder.kt`

Utility class for programmatically constructing AST nodes.

**Purpose:**
- Simplifies creation of complex AST structures
- Used in tests and code generation
- Provides builder pattern for AST nodes

**Example:**
```kotlin
val builder = AstBuilder()
val expr = builder.literal(42)  // Creates IntLiteral(42)
val stmt = builder.return_(expr) // Creates ReturnStatement(expr)
```

#### `TypeIndex.kt`

Maintains an index of all declared types and values for fast lookup.

**Key Data Structures:**
- `types: Map<String, TypeDecl>` - Named types (structs, aliases)
- `values: Map<String, ValueDecl>` - Global variables and constants
- `functions: Map<String, FunctionDecl>` - Function declarations

**Operations:**
- `lookupType(name): TypeDecl?` - Find type by name
- `lookupValue(name): ValueDecl?` - Find value by name
- `lookupFunction(name): FunctionDecl?` - Find function by name
- `addDeclaration(decl)` - Register a declaration

#### `ModuleIndexer.kt`

Handles forward references and topological sorting of declarations.

**Algorithm:**
```
1. Build dependency graph:
   - For each declaration, find all identifiers it references
   - Create edges: declaration → referenced declaration

2. Detect cycles:
   - Use depth-first search to identify circular dependencies
   - Throw CycleDetectedException if cycle found

3. Topological sort:
   - Order declarations so all dependencies come before dependents
   - Use Kahn's algorithm (in-degree based)
```

**Key Classes:**
- `ModuleIndexer` - Main class
- `CycleDetectedException` - Thrown when circular dependency detected

#### `TypeResolver.kt`

Resolves type references and validates type compatibility.

**Resolution Process:**

```kotlin
class TypeResolver {
    fun resolve(translationUnit: TranslationUnit): ResolutionResult {
        // Phase 1: Build type index
        val typeIndex = buildTypeIndex()
        
        // Phase 2: Resolve types in expressions
        val resolver = ExpressionTypeResolver(typeIndex)
        for (decl in translationUnit.declarations) {
            resolveDeclaration(decl, resolver)
        }
        
        // Phase 3: Validate
        validateAllTypesResolved()
        
        return ResolutionResult(
            resolvedUnit = translationUnit,
            isSuccess = true,
            unresolvedReferences = emptyList()
        )
    }
}
```

**Type Compatibility Rules:**
- Scalar types must match exactly for assignments
- Vector types must have same element type and size
- Matrix types must have same element type and dimensions
- Array types must have compatible element types
- Pointer types must have compatible base types and access modes
- Struct types must be identical (WGSL has no struct inheritance)

#### `Lowerer.kt`

Converts AST to Intermediate Representation (IR).

**Lowering Strategy:**

```kotlin
class Lowerer {
    fun lower(resolvedUnit: TranslationUnit): Module {
        val irModule = Module()
        
        // Lower all global declarations
        for (decl in resolvedUnit.declarations) {
            when (decl) {
                is FunctionDecl -> lowerFunction(decl, irModule)
                is StructDecl -> lowerStruct(decl, irModule)
                is VariableDeclGlobal -> lowerGlobalVariable(decl, irModule)
                is TypeDecl -> lowerTypeAlias(decl, irModule)
                // ...
            }
        }
        
        return irModule
    }
    
    private fun lowerFunction(decl: FunctionDecl, module: Module) {
        val irFunction = Function(
            name = decl.name,
            parameters = lowerParameters(decl.parameters),
            returnType = lowerType(decl.returnType),
            body = lowerBlock(decl.body),
            // ...
        )
        module.functions.add(irFunction)
    }
    
    private fun lowerExpression(expr: Expression): IrExpression {
        return when (expr) {
            is Literal -> lowerLiteral(expr)
            is IdentExpr -> lowerIdentifier(expr)
            is BinaryExpr -> lowerBinary(expr)
            is CallExpr -> lowerCall(expr)
            // ... 26 expression kinds
        }
    }
}
```

**IR Output:**
- `Module` - Root IR node containing all types, functions, globals
- `Function` - IR representation of a function
- `Type` - IR type representation
- `Statement` - IR statement (13 kinds)
- `Expression` - IR expression (26 kinds)

---

## 🔄 Data Flow

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                                    DATA FLOW                                     │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                               │
│  WGSL Source Code                                                               │
│       │                                                                           │
│       ▼                                                                           │
│  ┌─────────────┐                                                               │
│  │   Lexer     │──── Token Stream ────▶                                         │
│  └─────────────┘                                                               │
│       │                                                                           │
│       ▼                                                                           │
│  ┌─────────────┐                                                               │
│  │   Parser    │──── TranslationUnit (AST) ──▶                                │
│  └─────────────┘                                                               │
│       │                                                                           │
│       ▼                                                                           │
│  ┌─────────────────┐                                                           │
│  │ ModuleIndexer   │──── Sorted Declarations ──▶                              │
│  └─────────────────┘                                                           │
│       │                                                                           │
│       ▼                                                                           │
│  ┌─────────────────┐                                                           │
│  │  TypeResolver   │──── Resolved TranslationUnit ──▶                          │
│  └─────────────────┘                                                           │
│       │                                                                           │
│       ▼                                                                           │
│  ┌─────────────────┐                                                           │
│  │     Lowerer     │──── IR Module ──────────────────────────────┐            │
│  └─────────────────┘                                           │            │
│                                                                │            │
│                                                                ▼            │
│  ┌──────────────────────────────────────────────────────────────┐            │
│  │                        IR Module                              │            │
│  │  - types: List<Type>                                         │            │
│  │  - functions: List<Function>                                  │            │
│  │  - globalVariables: List<GlobalVariable>                     │            │
│  │  - ...                                                       │            │
│  └──────────────────────────────────────────────────────────────┘            │
│                                                                               │
│                                    │                                             │
│                                    ▼                                             │
│  ┌──────────────────────────────────────────────────────────────┐            │
│  │                    Generator Modules                          │            │
│  │  - wgsl:generator.glsl.GlslWriter                             │            │
│  │  - wgsl:generator.hlsl.HlslWriter                             │            │
│  │  - wgsl:generator.msl.MslWriter                               │            │
│  │  - back:wgsl.WgslWriter (for IR → WGSL)                       │            │
│  └──────────────────────────────────────────────────────────────┘            │
│                                                                               │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## ⚠️ Error Handling

The parser module implements robust error handling to provide meaningful diagnostics:

### Public Parse Result

Use `parseWgslResult(source)` when callers need diagnostics. It returns the parsed `TranslationUnit`, which can be partial after recovery, plus the collected `ParseError` list and an `isSuccess` convenience flag.

```kotlin
import io.ygdrasil.wgsl.parser.parseWgslResult

val result = parseWgslResult(source)

if (!result.isSuccess) {
    result.errors.forEach { error ->
        println("${error.span}: ${error.message}")
    }
    return
}

val translationUnit = result.translationUnit
```

`parseWgsl(source)` remains available as a compatibility helper for callers that only need the AST. New integrations should prefer `parseWgslResult` so syntax errors are not lost.

### Error Recovery (`ErrorRecovery.kt`)

Allows the parser to continue after encountering errors, enabling reporting of multiple issues in a single pass.

**Strategy:**
- On error, skip tokens until a known synchronization point (e.g., `;`, `}`, end of statement)
- Continue parsing and collect all errors
- Report all errors at the end

**Example:**
```wgsl
fn foo() {
    let x = ;  // Error: expected expression
    let y = 5; // Still parsed despite previous error
}
```

### Diagnostics (`Diagnostic.kt`)

Centralized error and warning collection.

**Key Classes:**

```kotlin
// Represents a single diagnostic message
class Diagnostic(
    val severity: Severity,      // ERROR, WARNING, INFO
    val message: String,        // Human-readable message
    val span: Span,             // Source location
    val code: String? = null    // Optional error code
)

// Collection of diagnostics used by semantic phases
class DiagnosticCollection {
    val diagnostics: MutableList<Diagnostic> = mutableListOf()
    
    fun add(diagnostic: Diagnostic)
    fun hasErrors(): Boolean
    fun getErrors(): List<Diagnostic>
    fun getWarnings(): List<Diagnostic>
}

// Exception thrown when too many errors accumulate
class TooManyErrorsException(
    val maxErrors: Int,
    diagnostics: DiagnosticCollection
) : Exception()
```

### Pretty Print (`PrettyPrintError.kt`)

Formats diagnostic messages for human consumption.

**Formatters:**

```kotlin
interface DiagnosticFormatter {
    fun format(diagnostic: Diagnostic): String
}

class DefaultDiagnosticFormatter : DiagnosticFormatter {
    // Full format with context, suggestions, etc.
}

class CompactDiagnosticFormatter : DiagnosticFormatter {
    // Compact one-line format
}
```

**Example Output:**
```
error[E0001]: expected expression
  ┌─ source.wgsl:10:15
  │
10 │ let x = ;
  │         ^ expected expression
  │
  └─ Did you mean to provide a value?
```

---

## 🔗 Integration

### Using the Parser Module

#### Basic Usage

```kotlin
// Parse WGSL source to AST plus parse diagnostics
import io.ygdrasil.wgsl.parser.parseWgslResult

val source = """
    @vertex
    fn main() -> @builtin(position) vec4<f32> {
        return vec4<f32>(0.0);
    }
"""

val result = parseWgslResult(source)

if (!result.isSuccess) {
    error("WGSL parse failed: ${result.errors.joinToString { it.message }}")
}

val translationUnit = result.translationUnit
```

#### Full Pipeline to IR

```kotlin
import io.ygdrasil.wgsl.parser.TypeResolver
import io.ygdrasil.wgsl.parser.Lowerer
import io.ygdrasil.wgsl.parser.parseWgslResult

fun parseWgslToIr(source: String): Module {
    // Step 1: Parsing
    val parseResult = parseWgslResult(source)
    if (!parseResult.isSuccess) {
        throw IllegalStateException("Parse errors: ${parseResult.errors.joinToString()}")
    }
    val translationUnit = parseResult.translationUnit
    
    // Step 2: Type Resolution
    val resolutionResult = TypeResolver().resolve(translationUnit)
    
    if (!resolutionResult.isSuccess) {
        throw IllegalStateException("Type resolution failed: ${resolutionResult.unresolvedReferences}")
    }
    
    // Step 3: Lowering to IR
    return Lowerer().lower(resolutionResult.resolvedUnit)
}
```

#### Error Handling

```kotlin
fun parseWithErrorHandling(source: String): Result<Module> {
    return try {
        val parseResult = parseWgslResult(source)
        
        // Check for parse errors
        if (!parseResult.isSuccess) {
            return Result.failure(
                ParseException("Parse errors: ${parseResult.errors.joinToString()}")
            )
        }
        val tu = parseResult.translationUnit
        
        val resolutionResult = TypeResolver().resolve(tu)
        
        if (!resolutionResult.isSuccess) {
            return Result.failure(
                TypeResolutionException("Type errors: ${resolutionResult.unresolvedReferences}")
            )
        }
        
        Result.success(Lowerer().lower(resolutionResult.resolvedUnit))
        
    } catch (e: Exception) {
        Result.failure(e)
    }
}
```

### Known Limitations

- The parser has broad WGSL syntax coverage, but overall WGSL conformance is not claimed as 100%. Use the compliance corpus and report in [WGSL_PARSER_COMPLIANCE.md](./docs/WGSL_PARSER_COMPLIANCE.md) to track the measured state.
- `parseWgslResult` reports syntax errors from the parser. Type resolution and lowering have separate result/error paths and should be handled explicitly by pipeline callers.
- Lowering now fails explicitly for unsupported or unknown types instead of silently substituting a default scalar type. Callers should treat `LoweringError` as a real pipeline failure.
- Generator output conformance is outside the parser API contract and must be validated in the generator modules.

### Module Dependencies

```
┌─────────────────────────────────────────────────────────────┐
│                    Module Dependencies                        │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  wgsl:core                                                    │
│    └── Contains IR data structures (Type, Expression, etc.)  │
│                                                             │
│  wgsl:parser ────────── depends on ────────▶ wgsl:core      │
│    ├── Lexer (lexer/)                                      │
│    ├── Parser (parser/)                                      │
│    ├── TypeResolver                                          │
│    ├── Lowerer                                               │
│    └── ModuleIndexer                                         │
│                                                             │
│  wgsl:generator ────── depends on ────────▶ wgsl:core     │
│    │                                                  │
│    └────── also depends on ────────▶ wgsl:parser          │
│        ├── glsl/ (GLSL writer)                             │
│        ├── hlsl/ (HLSL writer)                             │
│        └── msl/ (MSL writer)                               │
│                                                             │
│  wgsl:cli ────────── depends on ────────▶ wgsl:parser     │
│    │                     depends on ────────▶ wgsl:core     │
│    └────────── depends on ────────▶ wgsl:generator        │
│                                                             │
│  wgsl:tests ────────── depends on ────────▶ wgsl:parser   │
│    └────────── depends on ────────▶ wgsl:generator        │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

---

## 📚 Examples

See [EXAMPLES.md](./docs/EXAMPLES.md) for comprehensive usage examples including:
- Basic shader examples
- Abstract numeric types examples
- Predeclared enumerants examples
- User-defined enum examples
- Complete shader examples with all features

### Example 1: Simple Shader

**Input WGSL:**
```wgsl
@vertex
fn vs_main() -> @builtin(position) vec4<f32> {
    return vec4<f32>(0.0, 0.0, 0.0, 1.0);
}

@fragment
fn fs_main() -> @location(0) vec4<f32> {
    return vec4<f32>(1.0, 0.0, 0.0, 1.0);
}
```

**Parsing Result:**
```
TranslationUnit(
  declarations = [
    FunctionDecl(
      name = "vs_main",
      attributes = [@vertex],
      returnType = Type(Scalar(Vec4, F32)),
      returnTypeAttribute = @builtin(position),
      parameters = [],
      body = BlockStatement([
        ReturnStatement(
          expression = CallExpr(
            function = IdentExpr("vec4"),
            args = [FloatLiteral(0.0), FloatLiteral(0.0), FloatLiteral(0.0), FloatLiteral(1.0)]
          )
        )
      ])
    ),
    FunctionDecl(
      name = "fs_main",
      attributes = [@fragment],
      returnType = Type(Scalar(Vec4, F32)),
      returnTypeAttribute = @location(0),
      ...
    )
  ]
)
```

**IR Output:**
```
Module(
  types = [...],
  functions = [
    Function(
      name = "vs_main",
      returnType = Type(Vec4(F32)),
      parameters = [],
      body = Block([
        Return(Call(vec4, [Constant(0.0), Constant(0.0), Constant(0.0), Constant(1.0)]))
      ]),
      stage = Vertex
    ),
    Function(
      name = "fs_main",
      returnType = Type(Vec4(F32)),
      parameters = [],
      body = Block([...]),
      stage = Fragment,
      location = 0
    )
  ]
)
```

### Example 2: Struct with Functions

**Input WGSL:**
```wgsl
struct VertexInput {
    @location(0) position: vec3<f32>,
    @location(1) color: vec3<f32>
};

@vertex
fn vs_main(in: VertexInput) -> @builtin(position) vec4<f32> {
    return vec4<f32>(in.position, 1.0);
}
```

**After Type Resolution:**
- `VertexInput` is resolved to a struct type with 2 members
- `in.position` is resolved to `vec3<f32>`
- `vec4<f32>(in.position, 1.0)` is validated:
  - Constructor `vec4` accepts `vec3<f32> + f32` (valid)
  - All types are compatible

---

## 📊 Module Statistics

| Metric | Value |
|--------|-------|
| Total Classes | 9 |
| Total Lines of Code | ~5,000 |
| Token Types | 108+ |
| AST Node Types | 40+ |
| IR Node Types | 30+ |
| Test Coverage | Unit tests for each component |

---

## 🔧 Configuration

### Build Dependencies

```kotlin
// In your build.gradle.kts
dependencies {
    implementation(project(":wgsl:parser"))
    // Also requires:
    implementation(project(":wgsl:core"))  // For IR types
}
```

### Kotlin Version

- **Kotlin**: 2.0+
- **Multiplatform**: Full support (JVM, JS, Native, Wasm)
- **Gradle**: 9.5+

---

## 📝 Contributing

1. **Report Issues**: Open an issue for bugs or feature requests
2. **Pull Requests**: Welcome for improvements
3. **Testing**: Add tests for new features in `src/commonTest/kotlin/parser/`
4. **Documentation**: Update this README with new features

### Adding a New WGSL Feature

1. Add token to `TokenKind.kt` and `Lexer.kt` (if new keyword/operator)
2. Add AST node in `ast/` package
3. Add parsing rule in `Parser.kt`
4. Add type resolution in `TypeResolver.kt`
5. Add lowering in `Lowerer.kt`
6. Add tests in `src/commonTest/kotlin/parser/`

---

## 📄 Documentation

Detailed documentation is available in the `docs/` directory:

| Document | Description |
|----------|-------------|
| [ABSTRACT_TYPES.md](./docs/ABSTRACT_TYPES.md) | Complete guide to abstract numeric types support |
| [PREDECLARED_ENUMERANTS.md](./docs/PREDECLARED_ENUMERANTS.md) | Complete guide to predeclared enumerants support |
| [ENUM_SUPPORT.md](./docs/ENUM_SUPPORT.md) | User-defined enums and predeclared enumerants |
| [CONFORMANCE.md](./docs/CONFORMANCE.md) | Conformance with WGSL specification |
| [WGSL_PARSER_COMPLIANCE.md](./docs/WGSL_PARSER_COMPLIANCE.md) | Parser compliance corpus, measured stages, and known gaps |
| [BREAKING_CHANGES.md](./docs/BREAKING_CHANGES.md) | Breaking changes tracking |
| [EXAMPLES.md](./docs/EXAMPLES.md) | Comprehensive usage examples |

---

## 📄 License

This module is part of the webgpu-ktypes project and is licensed under the same terms.

---

## 🔗 See Also

- [WGSL Specification](https://gpuweb.github.io/gpuweb/wgsl/) - Official WGSL language specification
- [webgpu-ktypes Core Module](../core/README.md) - IR data structures
- [webgpu-ktypes Generator Module](../generator/README.md) - Code generation backends
- [WebGPU API](https://gpuweb.github.io/gpuweb/) - WebGPU standard
