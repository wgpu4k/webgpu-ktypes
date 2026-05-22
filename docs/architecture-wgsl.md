# WebGPU Kotlin Toolkit - wgsl Architecture Documentation

**Date:** 2026-05-19  
**Module:** wgsl  
**Version:** 1.0  
**Language:** English  
**Document Type:** Architecture Specification  

---

## Table of Contents

1. [Executive Summary](#executive-summary)
2. [Technology Stack](#technology-stack)
3. [Architecture Pattern](#architecture-pattern)
4. [Data Architecture](#data-architecture)
5. [Component Overview](#component-overview)
6. [Source Tree](#source-tree)
7. [Development Workflow](#development-workflow)
8. [Deployment Architecture](#deployment-architecture)
9. [Testing Strategy](#testing-strategy)

---

## Executive Summary

The **wgsl** module is an **independent WGSL (WebGPU Shading Language) parser and processor** for the WebGPU Kotlin Toolkit. This standalone module provides comprehensive parsing, analysis, and code generation capabilities for WGSL shader code, enabling developers to work with WebGPU shaders in Kotlin.

### Key Facts

| Attribute | Value |
|-----------|-------|
| **Module Role** | WGSL Parser and Processor |
| **Technology** | Kotlin Multiplatform |
| **Platform Coverage** | All 19 targets (JVM, JS, Native, Android, WasmJs, iOS, macOS, Linux, Windows, watchOS) |
| **Dependencies** | None (standalone module) |
| **Submodules** | core, parser, generator, cli, tests |
| **Architecture** | Loose coupling, pipeline processing (parse → analyze → generate) |
| **Inter-submodule deps** | core → parser & generator → cli → tests |

### Purpose

This module provides:
- **Complete WGSL parsing** - Lexical analysis, syntax parsing, and semantic analysis
- **Intermediate Representation (IR)** - Lowering AST to structured IR for processing
- **Code generation** - Generating GLSL, HLSL, MSL, and other shading languages from WGSL
- **Command-line interface** - Standalone CLI for WGSL processing
- **Comprehensive testing** - Extensive test suite for parser and generator

### Use Cases

The wgsl module is used for:
- **Shader Analysis** - Analyzing WGSL shader code for correctness and features
- **Shader Translation** - Converting WGSL to other shading languages (GLSL, HLSL, MSL)
- **Shader Optimization** - Analyzing and optimizing WGSL shaders
- **Code Generation** - Generating shaders from higher-level descriptions
- **Validation** - Validating WGSL shader code against specification
- **Tooling** - Building tools for WebGPU shader development

### Relationship to Other Modules

```
┌─────────────────────────────────────────────────────────────┐
│                    DEPENDENCY HIERARCHY                        │
├─────────────────────────────────────────────────────────────┤
│                                                                 │
│  ┌───────────────────────────────────────────────────────────┐ │
│  │                         wgsl                                   │ │
│  │              (INDEPENDENT MODULE)                           │ │
│  │                                                                 │ │
│  │  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐         │ │
│  │  │    core     │  │   parser    │  │  generator   │         │ │
│  │  │             │  │             │  │              │         │ │
│  │  │  (No deps)  │  │  (Depends on │  │  (Depends on │         │ │
│  │  │             │  │   core)     │  │   core)     │         │ │
│  │  └─────────────┘  └─────────────┘  └─────────────┘         │ │
│  │           │            │              │                   │ │
│  │           └────────────┼──────────────┘                   │ │
│  │                          │                                    │ │
│  │                           ▼                                    │ │
│  │                ┌─────────────┐                              │ │
│  │                │     cli     │                              │ │
│  │                │             │                              │ │
│  │                │ (Depends on│                              │ │
│  │                │  parser &   │                              │ │
│  │                │  generator) │                              │ │
│  │                └─────────────┘                              │ │
│  │                          │                                    │ │
│  │                           ▼                                    │ │
│  │                ┌─────────────┐                              │ │
│  │                │    tests    │                              │ │
│  │                │             │                              │ │
│  │                │ (Depends on│                              │ │
│  │                │  parser &   │                              │ │
│  │                │  generator) │                              │ │
│  │                └─────────────┘                              │ │
│  └───────────────────────────────────────────────────────────┘ │
│                                                                 │
│  ┌───────────────────────────────────────────────────────────┐ │
│  │                    webgpu-ktypes modules                     │ │
│  │              (Separate dependency tree)                       │ │
│  │                                                                 │ │
│  │  webgpu-ktypes --> webgpu-ktypes-descriptors --> webgpu-ktypes-web │ │
│  │                       ↓                                        │ │
│  │                webgpu-ktypes-specifications                   │ │
│  └───────────────────────────────────────────────────────────┘ │
│                                                                 │
│  NOTE: wgsl and webgpu-ktypes are INDEPENDENT                     │
│        They can be used separately or together                       │
│                                                                 │
└─────────────────────────────────────────────────────────────┘
```

This module is **independent** of the `webgpu-ktypes` modules, with its own dependency hierarchy.

---

## Technology Stack

### Core Technologies

| Technology | Version | Purpose |
|------------|---------|---------|
| **Kotlin** | 2.0+ | Primary programming language |
| **Kotlin Multiplatform** | Latest | Multiplatform compilation |
| **Gradle** | 9.5.0 | Build system (KTS DSL) |
| **Java Toolchain** | 25 | JVM compilation |
| **Kotest** | Latest | Test framework |

### All Platforms Supported

The wgsl module targets **all 19 platforms** supported by the monorepo:
- JVM, JS, Native (macOS, iOS, watchOS, tvOS, Linux, Windows, Mingw), Android, WasmJs

### Submodule Technologies

| Submodule | Technology | Purpose |
|-----------|------------|---------|
| **core** | Pure Kotlin | IR data structures and types |
| **parser** | Kotlin + ANTLR (implied) | WGSL parsing and lexical analysis |
| **generator** | Pure Kotlin | Code generation backends |
| **cli** | Kotlin | Command-line interface |
| **tests** | Kotlin + Kotest | Comprehensive test suite |

---

## Architecture Pattern

### Primary Pattern: Pipeline Processing

This module implements a **Pipeline Processing** pattern with the following characteristics:

1. **Loose Coupling** - Submodules have minimal dependencies on each other
2. **Pipeline Flow** - Data flows through stages: Parse → Analyze → Generate
3. **Modular Design** - Each stage can be used independently
4. **Hierarchical Dependencies** - core → parser & generator → cli → tests

### Design Patterns Used

| Pattern | Usage | Location |
|---------|-------|----------|
| **Pipeline** | Parse → Analyze → Lower → Generate | All submodules |
| **Visitor** | AST traversal and processing | parser/, generator/ |
| **Builder** | AST and IR construction | parser/, core/ |
| **Factory** | Node creation in AST/IR | parser/, core/ |
| **Composite** | AST node hierarchy | parser/, core/ |

### Architecture Diagram

```
┌─────────────────────────────────────────────────────────────────────────┐
│                        WGSL MODULE ARCHITECTURE                            │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                           │
│  ┌────────────────────────────────────────────────────────────────────┐ │
│  │                         SUBMODULES                                    │ │
│  │                                                                       │ │
│  │  ┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐   │ │
│  │  │      CORE        │    │      PARSER      │    │    GENERATOR    │   │ │
│  │  │  (Foundation)    │    │   (Parsing)      │    │   (Code Gen)    │   │ │
│  │  │                 │    │                 │    │                 │   │ │
│  │  │ - IR Types      │    │ - Lexer          │    │ - GLSL Writer   │   │ │
│  │  │ - Module        │    │ - Parser         │    │ - HLSL Writer   │   │ │
│  │  │ - Type          │    │ - TypeResolver   │    │ - MSL Writer    │   │ │
│  │  │ - Expression    │    │ - ModuleIndexer  │    │ - WGSL Writer   │   │ │
│  │  │ - Statement     │    │ - Lowerer        │    │                 │   │ │
│  │  │ - Function      │    │                 │    │                 │   │ │
│  │  └─────────────────┘    └────────┬────────┘    └─────────────────┘   │ │
│  │                                    │                                  │ │
│  │                                    ▼                                  │ │
│  │  ┌────────────────────────────────────────────────────────────┐ │ │
│  │  │                         CLI                                     │ │ │
│  │  │   (Command-Line Interface)                                    │ │ │
│  │  │                                                               │ │ │
│  │  │  - Argument parsing                                          │ │ │
│  │  │  - Input/output handling                                     │ │ │
│  │  │  - Pipeline orchestration                                    │ │ │
│  │  └────────────────────────────────────────────────────────────┘ │ │
│  │                                                                   │ │
│  │                                    ▼                                  │ │
│  │  ┌────────────────────────────────────────────────────────────┐ │ │
│  │  │                        TESTS                                    │ │ │
│  │  │   (Comprehensive Test Suite)                                  │ │ │
│  │  │                                                               │ │ │
│  │  │  - Parser tests                                               │ │ │
│  │  │  - Type resolver tests                                        │ │ │
│  │  │  - Lowerer tests                                              │ │ │
│  │  │  - Generator tests                                            │ │ │
│  │  │  - Integration tests                                          │ │ │
│  │  └────────────────────────────────────────────────────────────┘ │ │
│  │                                                                   │ │
│  └────────────────────────────────────────────────────────────────────┘ │
│                                                                           │
│  DATA FLOW:                                                               │
│  ┌─────────────┐     ┌─────────────┐     ┌─────────────┐              │ │
│  │   WGSL      │────▶│    AST      │────▶│     IR      │────┐          │ │
│  │  Source     │     │  (Parsed)   │     │  (Lowered)   │    │          │ │
│  └─────────────┘     └─────────────┘     └─────────────┘    │          │ │
│                                                            │          │ │
│                  ┌─────────────────────────────────────────────────┘   │ │
│                  │                                               │
│                  ▼                                               │
│  ┌──────────────────────────────────────────────────────────────┐   │ │
│  │                    CODE GENERATION                           │   │ │
│  │  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐        │   │ │
│  │  │   GLSL      │  │   HLSL      │  │    MSL      │        │   │ │
│  │  │  Generator   │  │  Generator   │  │  Generator   │        │   │ │
│  │  └─────────────┘  └─────────────┘  └─────────────┘        │   │ │
│  └──────────────────────────────────────────────────────────────┘   │ │
│                                                                       │
└─────────────────────────────────────────────────────────────────────────┘
```

### Key Architectural Decisions

1. **Independent Module**: wgsl is completely independent of webgpu-ktypes, allowing separate use and evolution
2. **Pipeline Architecture**: Clear separation of parsing, analysis, and generation stages
3. **IR-Based Processing**: Uses Intermediate Representation (IR) for all processing, enabling multiple backends
4. **Multi-Backend Support**: Can generate GLSL, HLSL, MSL, and WGSL from the same IR
5. **Loose Coupling**: Submodules have minimal dependencies, enabling independent testing and use

---

## Data Architecture

### Core Data Structures

#### 1. Abstract Syntax Tree (AST)

The parser produces an **Abstract Syntax Tree** representing the WGSL source code structure.

**Root Node: TranslationUnit**
```kotlin
data class TranslationUnit(
    val declarations: List<GlobalDecl>,
    val diagnostics: List<Diagnostic>
)
```

**Declaration Types (GlobalDecl):**
- `FunctionDecl` - Function declarations (`fn main() {...}`)
- `StructDecl` - Structure declarations (`struct VertexInput {...}`)
- `VariableDeclGlobal` - Global variable declarations
- `TypeDecl` - Type aliases (`type MyVec4 = vec4<f32>`)
- `ConstAssertDecl` - Compile-time assertions

**Statement Types (40+ kinds):**
- `BlockStatement` - Code blocks `{ ... }`
- `IfStatement` - Conditional statements
- `SwitchStatement` - Switch statements
- `LoopStatement` - Loop statements
- `WhileStatement` - While loops
- `ForStatement` - For loops
- `BreakStatement` - Loop breaks
- `ContinueStatement` - Loop continues
- `ReturnStatement` - Function returns
- `DiscardStatement` - Fragment discard
- `LetStatement` - Variable declarations
- `ConstStatement` - Constant declarations
- `VarStatement` - Mutable variable declarations
- `AssignmentStatement` - Assignments
- `ExpressionStatement` - Expression statements

**Expression Types (26+ kinds):**
- `Literal` - Literal values (1, 2.5, true, "hello")
- `IdentExpr` - Identifiers (x, foo)
- `BinaryExpr` - Binary operations (a + b)
- `UnaryExpr` - Unary operations (-x, !cond)
- `CallExpr` - Function calls
- `MemberAccessExpr` - Member access (obj.member)
- `IndexExpr` - Array indexing (arr[0])
- `CastExpr` - Type casts
- `TernaryExpr` - Ternary operator (a ? b : c)
- And 17+ more...

#### 2. Intermediate Representation (IR)

The lowerer converts AST to **Intermediate Representation** for processing.

**Root: Module**
```kotlin
data class Module(
    val types: List<Type>,
    val functions: List<Function>,
    val globalVariables: List<GlobalVariable>,
    // ... other module-level elements
)
```

**IR Types:**
- `Type` - Type representation (scalars, vectors, matrices, arrays, structs, pointers)
- `Function` - Function representation with IR statements
- `GlobalVariable` - Global variable representation
- `Statement` - IR statement (13 kinds)
- `Expression` - IR expression (26 kinds)

**IR Characteristic:**
- Lower-level than AST
- More suitable for analysis and transformation
- Independent of source language (WGSL)
- Can be serialized and deserialized

#### 3. Token Stream

The lexer produces a **stream of tokens** from WGSL source:

**Token Categories:**
- **EOF & Whitespace**: EOF, WHITESPACE, SINGLE_LINE_COMMENT, MULTI_LINE_COMMENT
- **Literals**: IDENTIFIER, INT_LITERAL, UINT_LITERAL, FLOAT_LITERAL, BOOL_LITERAL, STRING_LITERAL
- **Keywords**: 108+ WGSL keywords (if, else, fn, let, const, var, type, struct, etc.)
- **Operators**: +, -, *, /, %, &, |, ^, ~, <<, >>, &&, ||, !, ==, !=, <, >, <=, >=, =, +=, -=, etc.
- **Punctuation**: (, ), {, }, [, ], ,, ., :, ;, ::, .*, ->, =>, <>, ?, _

#### 4. Diagnostic System

Centralized error and warning collection:

```kotlin
// Diagnostic severity
enum class Severity {
    ERROR, WARNING, INFO
}

// Source location
@JvmInline
value class Span(val start: Position, val end: Position)

// Diagnostic message
data class Diagnostic(
    val severity: Severity,
    val message: String,
    val span: Span,
    val code: String? = null
)

// Diagnostic collection
class DiagnosticCollection {
    val diagnostics: MutableList<Diagnostic>
    fun add(diagnostic: Diagnostic)
    fun hasErrors(): Boolean
    fun getErrors(): List<Diagnostic>
    fun getWarnings(): List<Diagnostic>
}
```

### Data Flow

```
WGSL Source Code
       │
       ▼
┌─────────────┐
│   Lexer     │──── Token Stream ──▶
└─────────────┘
       │
       ▼
┌─────────────┐
│   Parser    │──── TranslationUnit (AST) ──▶
└─────────────┘
       │
       ▼
┌─────────────────┐
│ ModuleIndexer   │──── Sorted Declarations ──▶
└─────────────────┘
       │
       ▼
┌─────────────────┐
│  TypeResolver   │──── Resolved TranslationUnit ──▶
└─────────────────┘
       │
       ▼
┌─────────────────┐
│     Lowerer     │──── IR Module ──────────────────┐
└─────────────────┘                               │
                                               │
                                               ▼
┌──────────────────────────────────────────────────────────────┐
│                        IR Module                               │
│  - types: List<Type>                                         │
│  - functions: List<Function>                                  │
│  - globalVariables: List<GlobalVariable>                     │
└──────────────────────────────────────────────────────────────┘
       │
       ▼
┌─────────────────┐
│   Generators   │──── Generated Code (GLSL/HLSL/MSL/WGSL)
└─────────────────┘
```

---

## Component Overview

### Submodules

#### 1. wgsl/core

**Role:** Foundation module with IR data structures

**Purpose:**
- Defines all IR data structures
- Provides core types for WGSL processing
- No external dependencies

**Key Components:**
| Component | Location | Purpose |
|-----------|----------|---------|
| **Type.kt** | `core/src/commonMain/kotlin/Type.kt` | IR type definitions |
| **Expression.kt** | `core/src/commonMain/kotlin/Expression.kt` | IR expression types |
| **Statement.kt** | `core/src/commonMain/kotlin/Statement.kt` | IR statement types |
| **Function.kt** | `core/src/commonMain/kotlin/Function.kt` | IR function definitions |
| **Module.kt** | `core/src/commonMain/kotlin/Module.kt` | IR module (root) |

**Dependencies:** None (root of wgsl module)

#### 2. wgsl/parser

**Role:** WGSL parsing module

**Purpose:**
- Lexical analysis (tokenization)
- Syntax parsing (AST construction)
- Semantic analysis (type resolution)
- Module indexing (forward reference resolution)
- IR lowering (AST to IR conversion)

**Key Components:**
| Component | Location | Purpose |
|-----------|----------|---------|
| **Lexer.kt** | `parser/src/commonMain/kotlin/Lexer.kt` | Tokenization of WGSL source |
| **Parser.kt** | `parser/src/commonMain/kotlin/Parser.kt` | Syntax parsing to AST |
| **TokenKind.kt** | `parser/src/commonMain/kotlin/TokenKind.kt` | Token type definitions (108+ values) |
| **ModuleIndexer.kt** | `parser/src/commonMain/kotlin/ModuleIndexer.kt` | Forward reference resolution |
| **TypeResolver.kt** | `parser/src/commonMain/kotlin/TypeResolver.kt` | Type resolution and validation |
| **Lowerer.kt** | `parser/src/commonMain/kotlin/Lowerer.kt` | AST to IR lowering |
| **AstBuilder.kt** | `parser/src/commonMain/kotlin/AstBuilder.kt` | Programmatic AST construction |
| **ErrorRecovery.kt** | `parser/src/commonMain/kotlin/ErrorRecovery.kt` | Error recovery for malformed input |

**Dependencies:**
- `wgsl:core` (for IR types)

#### 3. wgsl/generator

**Role:** Code generation module

**Purpose:**
- Generate GLSL, HLSL, MSL from IR
- Generate WGSL from IR (round-trip)
- Code generation backends for different shading languages

**Directory Structure:**
```
generator/
├── build.gradle.kts
├── README.md
└── src/
    └── commonMain/
        └── kotlin/
            ├── glsl/          # GLSL backend
            │   └── GlslWriter.kt
            ├── hlsl/          # HLSL backend
            │   └── HlslWriter.kt
            ├── msl/           # Metal Shading Language backend
            │   └── MslWriter.kt
            └── wgsl/          # WGSL backend (round-trip)
                └── WgslWriter.kt
```

**Key Components:**
| Backend | Location | Target Language | Purpose |
|---------|----------|-----------------|---------|
| **GLSL** | `generator/src/commonMain/kotlin/glsl/GlslWriter.kt` | OpenGL Shading Language | Desktop/mobile graphics |
| **HLSL** | `generator/src/commonMain/kotlin/hlsl/HlslWriter.kt` | High-Level Shading Language | DirectX/Windows |
| **MSL** | `generator/src/commonMain/kotlin/msl/MslWriter.kt` | Metal Shading Language | Apple platforms |
| **WGSL** | `generator/src/commonMain/kotlin/wgsl/WgslWriter.kt` | WebGPU Shading Language | Round-trip generation |

**Dependencies:**
- `wgsl:core` (for IR types)
- `wgsl:parser` (optional, for parsing before generation)

#### 4. wgsl/cli

**Role:** Command-line interface module

**Purpose:**
- Provide a standalone CLI for WGSL processing
- Parse command-line arguments
- Orchestrate the parsing and generation pipeline
- Handle input/output operations

**Key Components:**
| Component | Location | Purpose |
|-----------|----------|---------|
| **Main.kt** | `cli/src/commonMain/kotlin/Main.kt` | CLI entry point |
| **CliOptions.kt** | `cli/src/commonMain/kotlin/CliOptions.kt` | Command-line options parsing |
| **CliExecutor.kt** | `cli/src/commonMain/kotlin/CliExecutor.kt` | Pipeline orchestration |

**Dependencies:**
- `wgsl:parser` (for parsing)
- `wgsl:generator` (for generation)
- `wgsl:core` (transitive)

#### 5. wgsl/tests

**Role:** Comprehensive test suite

**Purpose:**
- Unit tests for all components
- Integration tests for the full pipeline
- Test utilities and fixtures
- Example WGSL shaders for testing

**Directory Structure:**
```
tests/
├── build.gradle.kts
├── README.md
└── src/
    └── commonTest/
        └── kotlin/
            ├── parser/       # Parser tests
            │   ├── LexerTest.kt
            │   ├── ParserTest.kt
            │   ├── TypeResolverTest.kt
            │   └── ...
            ├── generator/    # Generator tests
            │   ├── GlslGeneratorTest.kt
            │   ├── HlslGeneratorTest.kt
            │   ├── MslGeneratorTest.kt
            │   └── WgslGeneratorTest.kt
            ├── integration/   # Integration tests
            │   └── PipelineTest.kt
            └── fixtures/      # Test fixtures
                ├── shaders/    # Example WGSL shaders
                └── expected/   # Expected outputs
```

**Dependencies:**
- `wgsl:parser` (for parsing)
- `wgsl:generator` (for generation)
- `wgsl:core` (transitive)

### Component Relationships

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    COMPONENT RELATIONSHIPS                                   │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                           │
│  ┌─────────────────┐                                             │
│  │     wgsl:core   │                                             │
│  │  (No deps)      │                                             │
│  │                 │                                             │
│  │  - Type        │◄──────────────────────────────────────────┐   │
│  │  - Expression   │                                     │   │
│  │  - Statement    │                                     │   │
│  │  - Function     │                                     │   │
│  │  - Module       │                                     │   │
│  │  - ...          │                                     │   │
│  └────────┬────────┘                                     │   │
│           │                                               │   │
│           ▼                                               │   │
│  ┌─────────────────┐      ┌─────────────────┐              │   │
│  │  wgsl:parser    │      │ wgsl:generator   │              │   │
│  │                 │      │                 │              │   │
│  │  - Lexer       │      │  - GlslWriter   │              │   │
│  │  - Parser      │      │  - HlslWriter   │              │   │
│  │  - TypeResolver│      │  - MslWriter    │              │   │
│  │  - ModuleIndexer│      │  - WgslWriter   │              │   │
│  │  - Lowerer     │      │                 │              │   │
│  │  - ...         │      │                 │              │   │
│  └────────┬────────┘      └────────┬────────┘              │   │
│           │                         │                      │   │
│           └─────────────────────────┼──────────────────┘   │
│                                     │                      │
│                                     ▼                      │
│                           ┌─────────────────┐            │
│                           │   wgsl:cli       │            │
│                           │                 │            │
│                           │  - Main         │            │
│                           │  - CliOptions   │            │
│                           │  - CliExecutor  │            │
│                           └─────────────────┘            │
│                                     │                      │
│                                     ▼                      │
│                           ┌─────────────────┐            │
│                           │   wgsl:tests     │            │
│                           │                 │            │
│                           │  - Parser tests  │            │
│                           │  - Generator tests│            │
│                           │  - Integration    │            │
│                           │    tests         │            │
│                           └─────────────────┘            │
│                                                                   │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## Source Tree

### Directory Structure

```
wgsl/
├── build.gradle.kts            # Root module build configuration
│
├── parser/
│   ├── build.gradle.kts        # Parser module build
│   ├── README.md               # Parser documentation
│   └── src/
│       └── commonMain/
│           └── kotlin/
│               └── parser/      # Parser package
│                   ├── Lexer.kt
│                   ├── Parser.kt
│                   ├── TokenKind.kt
│                   ├── ModuleIndexer.kt
│                   ├── TypeResolver.kt
│                   ├── Lowerer.kt
│                   ├── AstBuilder.kt
│                   └── ErrorRecovery.kt
│
├── core/
│   ├── build.gradle.kts        # Core module build
│   └── src/
│       └── commonMain/
│           └── kotlin/
│               └── ir/          # IR package
│                   ├── Type.kt
│                   ├── Expression.kt
│                   ├── Statement.kt
│                   ├── Function.kt
│                   └── Module.kt
│
├── generator/
│   ├── build.gradle.kts        # Generator module build
│   └── src/
│       └── commonMain/
│           └── kotlin/
│               ├── glsl/
│               │   └── GlslWriter.kt
│               ├── hlsl/
│               │   └── HlslWriter.kt
│               ├── msl/
│               │   └── MslWriter.kt
│               └── wgsl/
│                   └── WgslWriter.kt
│
├── cli/
│   ├── build.gradle.kts        # CLI module build
│   └── src/
│       └── commonMain/
│           └── kotlin/
│               ├── Main.kt
│               ├── CliOptions.kt
│               └── CliExecutor.kt
│
└── tests/
    ├── build.gradle.kts        # Tests module build
    ├── README.md               # Tests documentation
    └── src/
        └── commonTest/
            └── kotlin/
                ├── parser/
                │   ├── LexerTest.kt
                │   ├── ParserTest.kt
                │   ├── TypeResolverTest.kt
                │   ├── ModuleIndexerTest.kt
                │   └── LowererTest.kt
                ├── generator/
                │   ├── GlslGeneratorTest.kt
                │   ├── HlslGeneratorTest.kt
                │   ├── MslGeneratorTest.kt
                │   └── WgslGeneratorTest.kt
                ├── integration/
                │   └── PipelineTest.kt
                └── fixtures/
                    ├── shaders/
                    │   ├── simple.wgsl
                    │   ├── vertex.wgsl
                    │   ├── fragment.wgsl
                    │   └── compute.wgsl
                    └── expected/
                        ├── simple.glsl
                        ├── simple.hlsl
                        └── simple.msl
```

### Submodule Build Configuration

Each submodule has its own `build.gradle.kts`:

```kotlin
// wgsl/core/build.gradle.kts
plugins {
    kotlin("multiplatform")
}

kotlin {
    // Target all 19 platforms
    // ...
}

// No dependencies - this is the root
```

```kotlin
// wgsl/parser/build.gradle.kts
plugins {
    kotlin("multiplatform")
}

dependencies {
    implementation(project(":wgsl:core"))
}
```

```kotlin
// wgsl/generator/build.gradle.kts
plugins {
    kotlin("multiplatform")
}

dependencies {
    implementation(project(":wgsl:core"))
    // Optionally:
    // implementation(project(":wgsl:parser"))
}
```

```kotlin
// wgsl/cli/build.gradle.kts
plugins {
    kotlin("multiplatform")
    application  // For CLI
}

dependencies {
    implementation(project(":wgsl:parser"))
    implementation(project(":wgsl:generator"))
}
```

```kotlin
// wgsl/tests/build.gradle.kts
plugins {
    kotlin("multiplatform")
}

dependencies {
    implementation(project(":wgsl:parser"))
    implementation(project(":wgsl:generator"))
    implementation(project(":wgsl:core"))
}
```

### File Statistics

| Submodule | Files | Lines | Purpose |
|----------|-------|-------|---------|
| core | 5+ | ~1,000 | IR data structures |
| parser | 7+ | ~5,000 | WGSL parsing |
| generator | 4+ | ~3,000 | Code generation |
| cli | 3+ | ~500 | Command-line interface |
| tests | 10+ | ~5,000 | Comprehensive tests |

### Package Structure

| Submodule | Package |
|----------|---------|
| core | `io.ygdrasil.wgsl.core` |
| parser | `io.ygdrasil.wgsl.parser` |
| generator | `io.ygdrasil.wgsl.generator.*` |
| cli | `io.ygdrasil.wgsl.cli` |
| tests | `io.ygdrasil.wgsl.tests` |

---

## Development Workflow

### Prerequisites

Same as `webgpu-ktypes` module:
- **Java JDK**: 25+
- **Gradle**: 9.5.0 (via wrapper)
- **Kotlin**: 2.0+

### Submodule Development

The wgsl module has **5 submodules** with a clear dependency hierarchy:

1. **core** - Develop IR types and structures first
2. **parser** - Develop parser using core types
3. **generator** - Develop generators using core types
4. **cli** - Develop CLI using parser and generator
5. **tests** - Develop tests for all submodules

### Development Process

#### Adding a New Feature

1. **Update Core**: Add new IR types if needed (in `core/`)
2. **Update Parser**: Add parsing support (in `parser/`)
   - Add token types if new keywords/operators
   - Add AST node types
   - Add parsing rules
   - Add type resolution
   - Add lowering
3. **Update Generator**: Add generation support (in `generator/`)
   - Add generation logic for each backend
4. **Update CLI**: Add CLI options if needed (in `cli/`)
5. **Add Tests**: Add comprehensive tests (in `tests/`)
6. **Verify**: Run full build and tests

#### Example: Adding a New WGSL Feature

```kotlin
// Step 1: Add IR type in core/
data class NewFeatureType(
    val property: String
) : Type()

// Step 2: Add AST node in parser/
data class NewFeatureExpression(
    val operand: Expression
) : Expression()

// Step 3: Add token in parser/
enum class TokenKind {
    // ... existing tokens
    NEW_FEATURE_KEYWORD,
}

// Step 4: Add parsing rule in parser/
fun Parser.parseNewFeatureExpression(): NewFeatureExpression {
    // Parsing logic
}

// Step 5: Add lowering in parser/
fun Lowerer.lowerNewFeatureExpression(expr: NewFeatureExpression): IrExpression {
    // Lowering logic
}

// Step 6: Add generation in generator/
fun GlslWriter.writeNewFeatureExpression(expr: IrNewFeatureExpression): String {
    // Generation logic
}

// Step 7: Add tests in tests/
class NewFeatureTest {
    @Test
    fun testNewFeatureParsing() {
        // Test parsing
    }
    
    @Test
    fun testNewFeatureGeneration() {
        // Test generation
    }
}
```

### Build Commands

```bash
# Build entire wgsl module
./gradlew :wgsl:build

# Build specific submodule
./gradlew :wgsl:parser:build
./gradlew :wgsl:generator:build

# Run all tests
./gradlew :wgsl:tests:allTests

# Run CLI
./gradlew :wgsl:cli:run --args="input.wgsl output.glsl"
```

### Code Quality

- **Comprehensive Documentation**: All public APIs have KDoc
- **Extensive Testing**: High test coverage for all components
- **Error Handling**: Robust error recovery and diagnostics
- **Performance**: Efficient parsing and generation

---

## Deployment Architecture

### Artifact Publishing

The wgsl module and its submodules are published as **separate artifacts**:

| Submodule | Artifact ID | Purpose |
|-----------|-------------|---------|
| core | `io.ygdrasil:wgsl-core` | IR data structures |
| parser | `io.ygdrasil:wgsl-parser` | WGSL parsing |
| generator | `io.ygdrasil:wgsl-generator` | Code generation |
| cli | `io.ygdrasil:wgsl-cli` | Command-line interface |
| tests | `io.ygdrasil:wgsl-tests` | Test utilities |

**Note:** The root `wgsl` module is a **composite build** that includes all submodules.

### Build Outputs

Each submodule produces artifacts for **all 19 platform targets**:

| Platform Category | Targets | Artifact Type |
|-------------------|---------|---------------|
| **JVM** | JVM | JAR with JVM classes |
| **JS** | JS (IR), JS (Legacy) | JS modules |
| **Wasm** | WasmJs | Wasm modules |
| **Android** | Android | AAR/Android library |
| **Native** | macOS, iOS, watchOS, tvOS, Linux, Windows, Mingw | Native binaries |

### Dependency Management

Applications can depend on individual submodules or the entire module:

```kotlin
// Individual submodule dependencies
dependencies {
    implementation("io.ygdrasil:wgsl-parser:VERSION")
    implementation("io.ygdrasil:wgsl-generator:VERSION")
}

// Or the entire module (includes all submodules)
dependencies {
    implementation("io.ygdrasil:wgsl:VERSION")
}

// For local development
dependencies {
    implementation(project(":wgsl:parser"))
    implementation(project(":wgsl:generator"))
}
```

### Versioning

All wgsl submodules share the **same version number** and are released together:
- **MAJOR**: Breaking changes in core IR or parser
- **MINOR**: New features, backward-compatible
- **PATCH**: Bug fixes, backward-compatible

### CLI Distribution

The CLI submodule produces:
- **JAR with main class**: For JVM execution
- **Standalone executable**: For easy distribution
- **Docker image**: For containerized deployment

---

## Testing Strategy

### Test Coverage

| Submodule | Test Location | Coverage | Focus |
|-----------|---------------|----------|-------|
| **core** | N/A | N/A | No tests (data structures only) |
| **parser** | `tests/src/commonTest/kotlin/parser/` | High | Parsing, type resolution, lowering |
| **generator** | `tests/src/commonTest/kotlin/generator/` | High | Code generation for all backends |
| **cli** | Integration tests | Medium | CLI functionality |
| **tests** | All submodule tests | High | Full pipeline testing |

### Test Types

#### Parser Tests

```kotlin
// LexerTest.kt
class LexerTest {
    @Test
    fun testSimpleIdentifier() {
        val source = "foo"
        val lexer = Lexer(source)
        val tokens = lexer.tokenizeSignificant()
        
        assertEquals(1, tokens.size)
        assertEquals(TokenKind.IDENTIFIER, tokens[0].kind)
        assertEquals("foo", tokens[0].text)
    }
    
    @Test
    fun testNumberLiterals() {
        val source = "42 3.14 0xFF"
        val lexer = Lexer(source)
        val tokens = lexer.tokenizeSignificant()
        
        assertEquals(3, tokens.size)
        assertEquals(TokenKind.INT_LITERAL, tokens[0].kind)
        assertEquals(TokenKind.FLOAT_LITERAL, tokens[1].kind)
        assertEquals(TokenKind.INT_LITERAL, tokens[2].kind)
    }
}

// ParserTest.kt
class ParserTest {
    @Test
    fun testSimpleFunction() {
        val source = """
            fn main() {
                return;
            }
        """
        val lexer = Lexer(source)
        val parser = Parser(lexer)
        val translationUnit = parser.parse()
        
        assertEquals(1, translationUnit.declarations.size)
        assertTrue(translationUnit.declarations[0] is FunctionDecl)
    }
    
    @Test
    fun testStructDeclaration() {
        val source = """
            struct VertexInput {
                @location(0) position: vec3<f32>,
                @location(1) color: vec3<f32>
            }
        """
        val translationUnit = parseWgsl(source)
        
        assertEquals(1, translationUnit.declarations.size)
        assertTrue(translationUnit.declarations[0] is StructDecl)
    }
}

// TypeResolverTest.kt
class TypeResolverTest {
    @Test
    fun testSimpleTypeResolution() {
        val source = """
            fn main() -> i32 {
                return 42;
            }
        """
        val translationUnit = parseWgsl(source)
        val resolver = TypeResolver(translationUnit)
        val result = resolver.resolve()
        
        assertTrue(result.isSuccess)
    }
}

// LowererTest.kt
class LowererTest {
    @Test
    fun testFunctionLowering() {
        val source = """
            fn add(a: i32, b: i32) -> i32 {
                return a + b;
            }
        """
        val translationUnit = parseWgsl(source)
        val resolver = TypeResolver(translationUnit)
        val resolved = resolver.resolve()
        val lowerer = Lowerer(resolved.resolvedUnit)
        val irModule = lowerer.lower()
        
        assertEquals(1, irModule.functions.size)
        assertEquals("add", irModule.functions[0].name)
    }
}
```

#### Generator Tests

```kotlin
// GlslGeneratorTest.kt
class GlslGeneratorTest {
    @Test
    fun testSimpleFunctionGeneration() {
        val source = """
            fn main() -> vec4<f32> {
                return vec4<f32>(1.0, 0.0, 0.0, 1.0);
            }
        """
        val irModule = parseWgslToIr(source)
        val glsl = GlslWriter.writeModule(irModule)
        
        assertTrue(glsl.contains("vec4"))
        assertTrue(glsl.contains("1.0"))
    }
    
    @Test
    fun testStructGeneration() {
        val source = """
            struct VertexInput {
                position: vec3<f32>
            }
            
            fn main(in: VertexInput) -> vec4<f32> {
                return vec4<f32>(in.position, 1.0);
            }
        """
        val irModule = parseWgslToIr(source)
        val glsl = GlslWriter.writeModule(irModule)
        
        assertTrue(glsl.contains("struct VertexInput"))
        assertTrue(glsl.contains("position"))
    }
}

// IntegrationTest.kt
class PipelineTest {
    @Test
    fun testFullPipelineWgslToGlsl() {
        val wgslSource = """
            @vertex
            fn vs_main() -> @builtin(position) vec4<f32> {
                return vec4<f32>(0.0, 0.0, 0.0, 1.0);
            }
            
            @fragment
            fn fs_main() -> @location(0) vec4<f32> {
                return vec4<f32>(1.0, 0.0, 0.0, 1.0);
            }
        """
        
        val glsl = parseWgslToGlsl(wgslSource)
        
        assertNotNull(glsl)
        assertTrue(glsl.contains("vs_main"))
        assertTrue(glsl.contains("fs_main"))
    }
    
    @Test
    fun testRoundTripWgslToWgsl() {
        val original = """
            fn add(a: i32, b: i32) -> i32 {
                return a + b;
            }
        """
        
        val irModule = parseWgslToIr(original)
        val generatedWgsl = WgslWriter.writeModule(irModule)
        
        // Parse the generated WGSL
        val reparsed = parseWgslToIr(generatedWgsl)
        
        // Should be semantically equivalent
        assertEquals(irModule.functions.size, reparsed.functions.size)
    }
}
```

### Test Framework

- **Test Framework**: Kotest
- **Assertion Library**: Kotest assertions
- **Test Runner**: JUnit Platform (JVM), Karma (JS), Native test runner (Native)

### Test Execution

```bash
# Run all wgsl tests
./gradlew :wgsl:tests:allTests

# Run specific submodule tests
./gradlew :wgsl:tests:parserTests
./gradlew :wgsl:tests:generatorTests

# Run tests for specific platform
./gradlew :wgsl:tests:jvmTest
./gradlew :wgsl:tests:jsTest
./gradlew :wgsl:tests:nativeTest
```

### CI/CD Integration

All tests are executed as part of the **monorepo CI/CD pipeline**:
- Runs on all 19 platform targets
- Executed in GitHub Actions workflows
- Runs on push/PR to relevant branches
- Matrix testing across all platforms

---

## Cross-References

### Dependencies

- **Depends on**: None (independent module)
- **Used by**: Application code, tools, webgpu-ktypes modules (optional)

### Relationship to webgpu-ktypes

The wgsl module is **independent** of webgpu-ktypes, but they can be used together:

- **wgsl** provides WGSL parsing and generation
- **webgpu-ktypes** provides WebGPU API bindings
- Together, they provide a **complete WebGPU solution**

### Related Documentation

- [WGSL Parser README](../../wgsl/parser/README.md) - Detailed parser documentation
- [Integration Architecture](../integration-architecture.md)
- [Development Guide](../development-guide.md)
- [WebGPU Specification](https://www.w3.org/TR/webgpu/) - Official W3C specification
- [WGSL Specification](https://gpuweb.github.io/gpuweb/wgsl/) - WGSL language specification

### See Also

- [Naga](https://github.com/gfx-rs/naga) - Similar project in Rust (inspiration for this module)
- [GLSL](https://www.khronos.org/opengl/wiki/Core_Language_(GLSL)) - OpenGL Shading Language
- [HLSL](https://docs.microsoft.com/en-us/windows/win32/direct3d/hlsl-reference) - High-Level Shading Language
- [MSL](https://developer.apple.com/metal/Metal-Shading-Language-Specification.pdf) - Metal Shading Language

---

*Document generated using BMAD Method `document-project` workflow - Step 8: Architecture Documentation*
