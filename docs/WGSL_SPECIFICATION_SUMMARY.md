# WGSL Specification - Summary
*WebGPU Shading Language - Version CRD (Candidate Recommendation Draft)*
*Source: https://www.w3.org/TR/WGSL/*
*Generated: 2025-05-19*

---

## 📋 TABLE OF CONTENTS

### 1. [Introduction](#1-introduction)
- 1.1. [Overview](#11-overview)
- 1.2. [Syntax Notation](#12-syntax-notation)
- 1.3. [Mathematical Terms and Notation](#13-mathematical-terms-and-notation)

### 2. [WGSL Module](#2-wgsl-module)
- 2.1. [Shader Lifecycle](#21-shader-lifecycle)
- 2.2. [Errors](#22-errors)
- 2.3. [Diagnostics](#23-diagnostics)
  - 2.3.1. Diagnostic Processing
  - 2.3.2. Filterable Triggering Rules
  - 2.3.3. Diagnostic Filtering
- 2.4. [Limits](#24-limits)

### 3. [Textual Structure](#3-textual-structure)
- 3.1. [Parsing](#31-parsing)
- 3.2. [Blankspace and Line Breaks](#32-blankspace-and-line-breaks)
- 3.3. [Comments](#33-comments)
- 3.4. [Tokens](#34-tokens)
- 3.5. [Literals](#35-literals)
  - 3.5.1. Boolean Literals
  - 3.5.2. Numeric Literals
    - Integer Literals
    - Floating-Point Literals
- 3.6. [Keywords](#36-keywords)
- 3.7. [Identifiers](#37-identifiers)
  - 3.7.1. Identifier Comparison
- 3.8. [Context-Dependent Names](#38-context-dependent-names)
  - 3.8.1. Attribute Names
  - 3.8.2. Built-in Value Names
  - 3.8.3. Diagnostic Rule Names
  - 3.8.4. Diagnostic Severity Control Names
  - 3.8.5. Extension Names
  - 3.8.6. Interpolation Type Names
  - 3.8.7. Interpolation Sampling Names
  - 3.8.8. Swizzle Names
- 3.9. [Template Lists](#39-template-lists)

### 4. [Directives](#4-directives)
- 4.1. [Extensions](#41-extensions)
  - 4.1.1. Enable Extensions
  - 4.1.2. Language Extensions
- 4.2. [Global Diagnostic Filter](#42-global-diagnostic-filter)

### 5. [Declaration and Scope](#5-declaration-and-scope)

### 6. [Types](#6-types)
- 6.1. [Type Checking](#61-type-checking)
  - 6.1.1. Type Rule Tables
  - 6.1.2. Conversion Rank
  - 6.1.3. Overload Resolution
- 6.2. [Plain Types](#62-plain-types)
  - 6.2.1. Abstract Numeric Types
  - 6.2.2. Boolean Type
  - 6.2.3. Integer Types
  - 6.2.4. Floating Point Types
  - 6.2.5. Scalar Types
  - 6.2.6. Vector Types
  - 6.2.7. Matrix Types
  - 6.2.8. Atomic Types
  - 6.2.9. Array Types
  - 6.2.10. Structure Types
  - 6.2.11. Composite Types
  - 6.2.12. Constructible Types
  - 6.2.13. Fixed-Footprint Types
- 6.3. [Enumeration Types](#63-enumeration-types)
  - 6.3.1. Predeclared Enumerants

### 7. [Type Declarations](#7-type-declarations)
- 7.1. Structure Type Declaration
- 7.2. Alias Declaration

### 8. [Constants, Variables, and Values](#8-constants-variables-and-values)
- 8.1. Constant and Variable Declarations
- 8.2. Constant Values
- 8.3. Value Defining Expressions
- 8.4. Type Inference

### 9. [Expressions](#9-expressions)
- 9.1. General
- 9.2. Operand Expressions
- 9.3. Unary Operators
- 9.4. Binary Operators
- 9.5. Builtin Functions
- 9.6. Type Casts
- 9.7. Type Conversions
- 9.8. Promotion
- 9.9. Vector and Matrix Swizzling
- 9.10. Component Access
- 9.11. Array Access
- 9.12. Pointer Access
- 9.13. Struct and Array Length
- 9.14. Function Calls
- 9.15. Equality Testing
- 9.16. Boolean Contexts
- 9.17. Short Circuiting
- 9.18. Ternary Selection
- 9.19. Expression Evaluation Order

### 10. [Statements](#10-statements)
- 10.1. General
- 10.2. Empty Statement
- 10.3. Block Statements
- 10.4. Assignment Statements
- 10.5. Compound Assignment Statements
- 10.6. Increment and Decrement Statements
- 10.7. If Statements
- 10.8. Switch Statements
- 10.9. Loops
  - 10.9.1. while Loops
  - 10.9.2. for Loops
  - 10.9.3. Loop Control Statements (break, continue)
  - 10.9.4. break if Statement
- 10.10. Return Statements
- 10.11. Discard Statement
- 10.12. Function Call Statements

### 11. [Functions](#11-functions)
- 11.1. General
- 11.2. Function Declarations
- 11.3. Function Bodies
- 11.4. Entry Points
- 11.5. Builtin Functions
- 11.6. Function Overloading
- 11.7. Functions in a Shader Stage

### 12. [Builtin Functions](#12-builtin-functions)
- 12.1. General
- 12.2. Normalization Rules
- 12.3. Argument Compatibility
- 12.4. Relaxed Precisions
- 12.5. Builtin Function Categories
  - 12.5.1. Platform Mapping
  - 12.5.2. WGSL Optimization Hints
  - 12.5.3. FLoat Memory Access
  - 12.5.4. Vector Constructors
  - 12.5.5. Matrix Constructors
  - 12.5.6. Length Calculations
  - 12.5.7. Dot Products
  - 12.5.8. Outer Products
  - 12.5.9. Cross Product
  - 12.5.10. Determinants
  - 12.5.11. Inverses
  - 12.5.12. Transposes
  - 12.5.13. Normalization
  - 12.5.14. Vector and Matrix Math
  - 12.5.15. Integer Math
  - 12.5.16. Trigonometric Functions
  - 12.5.17. Exponential and Logarithmic Functions
  - 12.5.18. Floating-point Packing Functions
  - 12.5.19. Floating-point Min/Max
  - 12.5.20. Bit Operations
  - 12.5.21. Quantization Functions
  - 12.5.22. Texture Sampling Functions
  - 12.5.23. Texture Query Functions
  - 12.5.24. Texture Gathering Functions
  - 12.5.25. Comparison Functions
  - 12.5.26. Type Cast
  - 12.5.27. Conversion
  - 12.5.28. Reinterpretation
  - 12.5.29. Array Length
  - 12.5.30. Atomic Functions
  - 12.5.31. Barrier Functions
  - 12.5.32. Workgroup Functions
  - 12.5.33. Subgroup Functions
  - 12.5.34. Ray Tracing Functions
  - 12.5.35. Mesh Functions
  - 12.5.36. Ray Query Functions
  - 12.5.37. Ray Query General
  - 12.5.38. Ray Query Candidate
  - 12.5.39. Ray Query Committed
  - 12.5.40. Ray Query Committed Triangle
  - 12.5.41. Ray Query Any
  - 12.5.42. Ray Query All

### 13. [Attributes](#13-attributes)
- 13.1. General
- 13.2. Parameter Attributes
- 13.3. Variable and Struct Member Attributes
- 13.4. Struct Attributes
- 13.5. Function Attributes

### 14. [Shader Interface and Resource Binding](#14-shader-interface-and-resource-binding)
- 14.1. General
- 14.2. Inputs and Outputs
- 14.3. Builtin Variables
- 14.4. Resource Binding
- 14.5. Binding Attributes
- 14.6. Resource Binding Validation

### 15. [Builtin Values](#15-builtin-values)
- 15.1. General
- 15.2. Function Builtins
- 15.3. Type Builtins

---

## 1️⃣ INTRODUCTION

**WebGPU Shading Language (WGSL)** is the shader language for [WebGPU]. An application using the WebGPU API uses WGSL to express the programs, known as *shaders*, that run on the GPU.

### 1.1 Overview

WebGPU issues a unit of work to the GPU in the form of a **GPU command**. WGSL is concerned with two kinds of GPU commands:

1. **Draw Command**: Executes a **render pipeline** in the context of inputs, outputs, and attached resources.
2. **Dispatch Command**: Executes a **compute pipeline** in the context of inputs and attached resources.

Both kinds of pipelines use shaders written in WGSL.

A **shader** is the portion of a WGSL program that executes a **shader stage** in a pipeline. A shader comprises:

- An **entry point** function.
- The transitive closure of all called functions, starting with the entry point. This set includes both **user-defined** and **built-in** functions.
- The set of variables and constants **statically accessed** by all those functions.
- The set of types used to define or analyze all those functions, variables, and constants.

> **Note**: A WGSL program does not require an entry point; however, such a program cannot be executed by the API because an entry point is required to create a `GPUProgrammableStage`.

When executing a shader stage, the implementation:

1. Computes the values of constants declared at **module-scope**.
2. Binds **resources** to variables in the shader's **resource interface**, making the contents of those resources available to the shader during execution.
3. Allocates memory for other **module-scope** variables, and populates that memory with the specified initial values.
4. Populates the formal parameters of the entry point, if they exist, with the shader stage's inputs.
5. Connects the entry point **return value**, if one exists, to the shader stage's outputs.
6. Then it invokes the entry point.

A WGSL program is organized into:

- **Directives**, which specify module-level behavior controls.
- **Functions**, which specify execution behavior.
- **Statements**, which are declarations or units of executable behavior.
- **Literals**, which are text representations for pure mathematical values.
- **Constants**, each providing a name for a value computed at a specific time.
- **Variables**, each providing a name for memory holding a value.
- **Expressions**, each of which combines a set of values to produce a result value.
- **Types**, each of which describes:
  - A set of values.
  - Constraints on supported expressions.
  - The semantics of those expressions.
- **Attributes**, which modify an object to specify extra information such as:
  - Specifying the interfaces to entry points.
  - Specifying diagnostic filters.

> **Note**: A WGSL program is currently composed of a single WGSL module.

WGSL is an **imperative language**: behavior is specified as a sequence of statements to execute. Statements can:

- Declare constants or variables.
- Modify the contents of variables.
- Modify execution order using structured programming constructs:
  - **Selective execution**: `if` (with optional `else if` and `else` clauses), `switch`.
  - **Repetition**: `loop`, `while`, `for`.
  - **Escaping a nested execution construct**: `continue`, `break`, `break if`.
  - **Refactoring**: function call and `return`.
- Evaluate expressions to compute values as part of the above behaviors.
- Check assumptions at shader creation time on constant expressions.

WGSL is **statically typed**: each value computed by a particular expression is in a specific type, determined only by examining the program source.

WGSL has types describing:

- **Booleans** and **numbers** (integers and floating point).
- **Vectors** and **matrices** of numeric types.
- **Arrays** of any type.
- **Structures** which contain a sequence of named members, each of a specified type.

---

## 🎯 EXAMPLE: FRAGMENT SHADER

```wgsl
// A fragment shader which lights textured geometry with point lights.

// Lights from a storage buffer binding.
struct PointLight {
  position: vec3<f32>,
  color: vec3<f32>,
}

struct LightStorage {
  pointCount: u32,
  point: array<PointLight>,
}

@group(0) @binding(0) var<storage> lights: LightStorage;

// Texture and sampler.
@group(1) @binding(0) var baseColorSampler: sampler;
@group(1) @binding(1) var baseColorTexture: texture_2d<f32>;

// Function arguments are values from the vertex shader.
@fragment
fn fragmentMain(
    @location(0) worldPos: vec3<f32>,
    @location(1) normal: vec3<f32>,
    @location(2) uv: vec2<f32>
) -> @location(0) vec4<f32> {
  // Sample the base color of the surface from a texture.
  let baseColor = textureSample(baseColorTexture, baseColorSampler, uv);

  let N = normalize(normal);
  var surfaceColor = vec3<f32>(0);

  // Loop over the scene point lights.
  for (var i = 0u; i < lights.pointCount; i++) {
    let worldToLight = lights.point[i].position - worldPos;
    let dist = length(worldToLight);
    let dir = normalize(worldToLight);

    // Determine the contribution of this light to the surface color.
    let radiance = lights.point[i].color * (1 / pow(dist, 2));
    let nDotL = max(dot(N, dir), 0);

    // Accumulate light contribution to the surface color.
    surfaceColor += baseColor.rgb * radiance * nDotL;
  }

  // Return the accumulated surface color.
  return vec4<f32>(surfaceColor, baseColor.a);
}
```

---

## 2️⃣ WGSL MODULE

A **WGSL module** is a translation unit for the WGSL language. It consists of a sequence of **top-level** constructs: directives, type declarations, global constants, global variables, and functions.

### 2.1 Shader Lifecycle

See [Introduction](#11-overview) for shader lifecycle details.

### 2.2 Errors

If a WGSL module contains **any error**, it is invalid, and must not be accepted by the API. An implementation may detect errors at any time, and report them via the diagnostic interface.

Error conditions include:

- **Syntax errors**: The source text violates the lexical or grammatical rules of WGSL.
- **Validation errors**: The source text is syntactically correct but violates semantic rules of WGSL.
- **Device errors**: The module is syntactically and semantically correct, but exceeds limits of the device or implementation.

### 2.3 Diagnostics

WGSL provides mechanisms for:

- **Diagnostic rules**: Identifying specific error conditions.
- **Diagnostic filters**: Suppressing or modifying diagnostic messages.
- **Diagnostic severity**: `error`, `warning`, `info`.

#### 2.3.1 Diagnostic Processing

Diagnostics are processed during module compilation. Each diagnostic has:

- A **rule**: The specific error condition (e.g., `must-use`, `out-of-bounds`).
- A **severity**: One of `error`, `warning`, or `info`.
- A **message**: Human-readable description.
- A **location**: Source location (file, line, column).

#### 2.3.2 Filterable Triggering Rules

Some diagnostic rules can be filtered:

| Rule | Description | Default Severity |
|------|-------------|------------------|
| `assignment-to-const` | Assignment to constant variable | error |
| `comparison-to-const` | Comparison with constant | warning |
| `dead-code` | Unreachable code | info |
| `deprecated` | Use of deprecated feature | warning |
| `out-of-bounds` | Array/list access out of bounds | error |
| `overflow` | Arithmetic overflow | warning |
| `shadowing` | Variable name shadowing | warning |
| `type-mismatch` | Type mismatch in assignment/expression | error |
| `uninitialized` | Use of uninitialized variable | error |
| `unused-result` | Result of expression not used | info |

#### 2.3.3 Diagnostic Filtering

Diagnostics can be filtered using:

```wgsl
// Suppress a specific rule
@diagnostic(off, "out-of-bounds")
fn myFunction() { ... }

// Change severity
@diagnostic(warning, "unused-result")
fn myFunction() { ... }
```

### 2.4 Limits

Implementations may impose **implementation-defined limits** on:

- Number of entry points per module
- Number of functions per module
- Number of global variables per module
- Number of types per module
- Nesting depth of blocks
- Number of struct members
- Size of arrays
- etc.

If a module exceeds any limit, it is invalid and must generate a **device error**.

---

## 3️⃣ TEXTUAL STRUCTURE

### 3.1 Parsing

WGSL source text is parsed according to the grammar defined in this specification. The parsing process:

1. **Lexical analysis**: Breaks the source into tokens.
2. **Syntax analysis**: Groups tokens into grammatical structures (AST).
3. **Semantic analysis**: Validates the AST and resolves references.

### 3.2 Blankspace and Line Breaks

- **Blankspace**: Space, tab, newline, carriage return, form feed.
- **Line breaks**: Newline, carriage return, form feed.
- Blankspace is generally ignored except where it separates tokens.
- Line breaks terminate single-line comments and separate tokens.

### 3.3 Comments

WGSL supports two styles of comments:

**Single-line comments**:
```wgsl
// This is a single-line comment
let x = 42; // Inline comment
```

**Multi-line comments**:
```wgsl
/* This is a
   multi-line comment */
let y = 100;
```

Comments do not nest. The sequence `/* /* */ */` is invalid.

### 3.4 Tokens

Tokens are the smallest individual units of WGSL source text. They include:

- **Keywords**: Reserved words (e.g., `fn`, `let`, `if`, `for`)
- **Identifiers**: User-defined names
- **Literals**: Numeric, boolean, string values
- **Operators**: `+`, `-`, `*`, `/`, `==`, `!=`, `<`, `>`, etc.
- **Punctuation**: `(`, `)`, `{`, `}`, `[`, `]`, `,`, `;`, `:`, etc.

### 3.5 Literals

#### 3.5.1 Boolean Literals

```wgsl
true
false
```

#### 3.5.2 Numeric Literals

**Integer literals**:
```wgsl
42          // decimal
0x2A        // hexadecimal (42)
052         // octal (42) - NOT SUPPORTED in WGSL
2147483648u // unsigned decimal
42i        // signed decimal (explicit)
42u        // unsigned decimal (explicit)
```

**Floating-point literals**:
```wgsl
3.14
.5         // 0.5
5.         // 5.0
1e3        // 1000.0
1.5e-2     // 0.015
0x1.8p1    // hex float: 1.5 * 2^1 = 3.0
```

### 3.6 Keywords

WGSL has the following **reserved keywords**:

| Category | Keywords |
|----------|----------|
| **Program structure** | `fn`, `let`, `var`, `const`, `type`, `struct`, `alias` |
| **Control flow** | `if`, `else`, `switch`, `case`, `default`, `loop`, `while`, `for`, `break`, `continue`, `return`, `discard` |
| **Type declarations** | `array`, `matrix`, `vec2`, `vec3`, `vec4`, `mat2x2`, `mat2x3`, `mat2x4`, `mat3x2`, `mat3x3`, `mat3x4`, `mat4x2`, `mat4x3`, `mat4x4` |
| **Type qualifiers** | `ptr`, `atomic` |
| **Storage classes** | `function`, `private`, `workgroup`, `uniform`, `storage`, `push_constant` |
| **Access modes** | `read`, `write`, `read_write` |
| **Function attributes** | `@compute`, `@fragment`, `@vertex` |
| **Variable attributes** | `@builtin`, `@location`, `@binding`, `@group`, `@interpolate`, `@invariant` |
| **Directives** | `@enable`, `@diagnostic`, `@must_use` |
| **Boolean** | `true`, `false` |
| **Built-in types** | `bool`, `i32`, `u32`, `f32`, `f16`, `sampler`, `texture_*` |

> **Note**: The full list contains 108+ keywords.

### 3.7 Identifiers

Identifiers are user-defined names for:

- Variables
- Constants
- Functions
- Types (structs, aliases)
- Parameters
- Struct members

**Syntax**:
```
identifier: [a-zA-Z_] [a-zA-Z0-9_]*
```

**Examples**:
```wgsl
myVariable
MyFunction
_type123
```

Identifiers are **case-sensitive**: `foo` ≠ `Foo` ≠ `FOO`.

#### 3.7.1 Identifier Comparison

Identifiers are compared using **exact matching** (case-sensitive).

### 3.8 Context-Dependent Names

Some names are only valid in specific contexts:

#### 3.8.1 Attribute Names

Used in attribute annotations:
```wgsl
@group(0) @binding(0)
@location(0)
@interpolate(perspective, linear, sample)
@must_use
```

#### 3.8.2 Built-in Value Names

Predefined names for built-in values:
- `position`: Built-in input/output
- `SV_Position`: Alternative name (DirectX-style)
- `instance_index`: Instance index
- `vertex_index`: Vertex index
- etc.

#### 3.8.3 Diagnostic Rule Names

Names of diagnostic rules that can be filtered:
- `assignment-to-const`
- `out-of-bounds`
- `type-mismatch`
- etc.

#### 3.8.4 Diagnostic Severity Control Names

Severity levels for diagnostics:
- `off`: Suppress the diagnostic
- `error`: Treat as error
- `warning`: Treat as warning
- `info`: Treat as info

#### 3.8.5 Extension Names

Names of WGSL extensions:
- `WGSL_EXT_annotation_conformance`
- `WGSL_EXT_annotation_tool`
- etc.

#### 3.8.6 Interpolation Type Names

Interpolation types for `@interpolate`:
- `perspective`
- `linear`
- `flat`
- `sample`

#### 3.8.7 Interpolation Sampling Names

Interpolation sampling for `@interpolate`:
- `center`
- `centroid`
- `sample`

#### 3.8.8 Swizzle Names

Component names for vector swizzling:
- `x`, `y`, `z`, `w` (for vectors)
- `r`, `g`, `b`, `a` (aliases for x, y, z, w)
- `s`, `t`, `p`, `q` (for textures)

### 3.9 Template Lists

Template lists are used in generic type and function declarations:

```wgsl
// Generic struct
struct Generic<T> {
  value: T,
}

// Generic function
fn genericFunc<T>(x: T) -> T {
  return x;
}
```

---

## 4️⃣ DIRECTIVES

Directives are module-level declarations that affect the entire module.

### 4.1 Extensions

Extensions allow enabling optional features:

#### 4.1.1 Enable Extensions

```wgsl
@enable extension_name;
```

#### 4.1.2 Language Extensions

WGSL defines the following extensions:

| Extension | Description | Status |
|-----------|-------------|--------|
| `WGSL_EXT_annotation_conformance` | Annotation conformance | Optional |
| `WGSL_EXT_annotation_tool` | Annotation tool support | Optional |
| `WGSL_EXT_debug_command` | Debug command support | Optional |

### 4.2 Global Diagnostic Filter

```wgsl
@diagnostic(severity, "rule-name");
```

---

## 6️⃣ TYPES

WGSL has a rich type system with various categories of types.

### 6.1 Type Checking

WGSL is **statically typed**: all type checking is performed at compile time.

#### 6.1.1 Type Rule Tables

Type compatibility is determined by **type rules**:

- **Exact match**: Types must be identical
- **Subtype**: A type is a subtype of another
- **Convertible**: A type can be converted to another
- **Promotable**: A type can be promoted to another in expressions

#### 6.1.2 Conversion Rank

When multiple conversions are possible, the **conversion rank** determines which is preferred:

1. **Exact match** (rank 0)
2. **Promotion** (rank 1)
3. **Conversion** (rank 2)
4. **User-defined conversion** (rank 3)

#### 6.1.3 Overload Resolution

When resolving function overloads, the compiler:

1. Collects all candidate functions
2. Filters by number of parameters
3. Performs type checking on arguments
4. Selects the best match using conversion ranks
5. Reports ambiguity if multiple candidates have the same rank

### 6.2 Plain Types

#### 6.2.1 Abstract Numeric Types

Abstract numeric types represent numeric values without specifying the underlying representation:

- `abstract int`
- `abstract float`

#### 6.2.2 Boolean Type

The **boolean** type (`bool`) has two values: `true` and `false`.

#### 6.2.3 Integer Types

**Signed integers**:
- `i8`: 8-bit signed
- `i16`: 16-bit signed
- `i32`: 32-bit signed
- `i64`: 64-bit signed

**Unsigned integers**:
- `u8`: 8-bit unsigned
- `u16`: 16-bit unsigned
- `u32`: 32-bit unsigned
- `u64`: 64-bit unsigned

#### 6.2.4 Floating Point Types

- `f16`: 16-bit floating point (half precision)
- `f32`: 32-bit floating point (single precision)
- `f64`: 64-bit floating point (double precision) - **OPTIONAL**

#### 6.2.5 Scalar Types

Scalar types are the basic building blocks:

- `bool`
- All integer types (`i8`, `i16`, `i32`, `i64`, `u8`, `u16`, `u32`, `u64`)
- All floating point types (`f16`, `f32`)

#### 6.2.6 Vector Types

Vector types represent tuples of scalar values:

```wgsl
vec2<T>  // 2-component vector
dvec3<T> // 3-component vector (deprecated, use vec3<T>)
vec4<T>  // 4-component vector
```

Where `T` is any scalar type.

**Examples**:
```wgsl
vec2<f32>  // 2D float vector
vec3<i32>  // 3D int vector
vec4<u32>  // 4D unsigned int vector
```

**Component access**:
```wgsl
let v = vec4<f32>(1, 2, 3, 4);
let x = v.x;  // 1
let y = v.y;  // 2
let z = v.z;  // 3
let w = v.w;  // 4

// Swizzling
let xy = v.xy;    // vec2<f32>(1, 2)
let yx = v.yx;    // vec2<f32>(2, 1)
let xzzy = v.xzzy; // vec4<f32>(1, 3, 3, 2)
```

#### 6.2.7 Matrix Types

Matrix types represent 2D arrays of scalar values:

```wgsl
matCxR<T>  // C columns, R rows
```

**Examples**:
```wgsl
mat2x2<f32>  // 2x2 float matrix
mat3x4<f32>  // 3x4 float matrix
mat4x3<i32>  // 4x3 int matrix
```

**Matrix construction**:
```wgsl
let m = mat2x2<f32>(
  vec2<f32>(1, 2),
  vec2<f32>(3, 4)
);
```

#### 6.2.8 Atomic Types

Atomic types for atomic operations:

```wgsl
atomic<T>
```

Where `T` is an integer type.

#### 6.2.9 Array Types

Array types represent sequences of elements of the same type:

```wgsl
array<T, N>  // Array of N elements of type T
array<T>     // Dynamic array of elements of type T
```

**Examples**:
```wgsl
array<f32, 10>     // Array of 10 f32 values
array<vec3<f32>>   // Dynamic array of vec3<f32>
array<array<i32, 5>, 3>  // 3x5 array of i32
```

#### 6.2.10 Structure Types

Structure types (structs) contain a sequence of named members:

```wgsl
struct MyStruct {
  member1: f32,
  member2: vec3<f32>,
  member3: array<u32, 10>,
}
```

**Member access**:
```wgsl
let s = MyStruct(...);
let x = s.member1;
let y = s.member2;
```

#### 6.2.11 Composite Types

Types that are composed of other types:

- Arrays
- Structs
- Vectors
- Matrices

#### 6.2.12 Constructible Types

Types that can be constructed using constructors:

- All scalar types
- Vector types
- Matrix types
- Array types (with length)
- Struct types

#### 6.2.13 Fixed-Footprint Types

Types with a fixed memory footprint:

- Scalar types
- Vector types
- Matrix types
- Struct types (with fixed-size members)
- Array types (with fixed length)

### 6.3 Enumeration Types

Enumeration types define a set of named constant values:

```wgsl
enum MyEnum {
  Value1,
  Value2,
  Value3 = 10,
}
```

#### 6.3.1 Predeclared Enumerants

WGSL does not have built-in enums, but some types have predefined constants:

- `AddressMode`: `clamp_to_edge`, `repeat`, `mirror_repeat`
- `FilterMode`: `nearest`, `linear`
- `MipmapFilterMode`: `nearest`, `linear`
- etc.

---

## 9️⃣ EXPRESSIONS

### 9.1 General

Expressions compute values. Each expression has:

- A **type**: The type of the computed value
- A **value**: The computed result
- **Side effects**: May modify variables or other state

### 9.2 Operand Expressions

**Primary expressions**:
```wgsl
42              // literal
x               // variable
myFunction()    // function call
myStruct.value // member access
```

### 9.3 Unary Operators

| Operator | Name | Example |
|----------|------|---------|
| `+` | Identity | `+x` |
| `-` | Negation | `-x` |
| `~` | Bitwise NOT | `~x` |
| `!` | Logical NOT | `!x` |

### 9.4 Binary Operators

**Arithmetic**:
| Operator | Name | Example |
|----------|------|---------|
| `+` | Addition | `x + y` |
| `-` | Subtraction | `x - y` |
| `*` | Multiplication | `x * y` |
| `/` | Division | `x / y` |
| `%` | Modulo | `x % y` |

**Bitwise**:
| Operator | Name | Example |
|----------|------|---------|
| `&` | Bitwise AND | `x & y` |
| `|` | Bitwise OR | `x | y` |
| `^` | Bitwise XOR | `x ^ y` |
| `<<` | Left shift | `x << y` |
| `>>` | Right shift | `x >> y` |

**Comparison**:
| Operator | Name | Example | Returns |
|----------|------|---------|---------|
| `==` | Equal | `x == y` | `bool` |
| `!=` | Not equal | `x != y` | `bool` |
| `<` | Less than | `x < y` | `bool` |
| `>` | Greater than | `x > y` | `bool` |
| `<=` | Less or equal | `x <= y` | `bool` |
| `>=` | Greater or equal | `x >= y` | `bool` |

**Logical**:
| Operator | Name | Example | Returns |
|----------|------|---------|---------|
| `&&` | Logical AND | `x && y` | `bool` |
| `||` | Logical OR | `x || y` | `bool` |

### 9.5 Builtin Functions

WGSL provides a rich set of builtin functions. See [Section 12](#12-builtin-functions) for details.

### 9.6 Type Casts

Explicit type conversions:

```wgsl
let x: f32 = 42;        // implicit conversion
let y: f32 = f32(42);    // explicit cast
let z: i32 = i32(3.14);  // explicit cast (truncation)
```

### 9.7 Type Conversions

Implicit conversions follow type compatibility rules.

### 9.8 Promotion

Types are promoted in mixed-type expressions:

- `i32` and `u32` → `i32` or `u32` (depending on context)
- `f32` and `i32` → `f32`
- `vec2<f32>` and `f32` → `vec2<f32>`

### 9.9 Vector and Matrix Swizzling

```wgsl
let v = vec4<f32>(1, 2, 3, 4);
let xy = v.xy;      // vec2<f32>(1, 2)
let yx = v.yx;      // vec2<f32>(2, 1)
let zwwx = v.zwwx;  // vec4<f32>(3, 4, 4, 1)

// Write swizzle
v.xy = vec2<f32>(5, 6);  // v = vec4<f32>(5, 6, 3, 4)
```

### 9.10 Component Access

```wgsl
let v = vec3<f32>(1, 2, 3);
let x = v.x;  // f32
let y = v[1]; // f32 (index 1)
```

### 9.11 Array Access

```wgsl
let arr = array<f32, 5>(1, 2, 3, 4, 5);
let x = arr[0];  // f32
let y = arr[2];  // f32
```

### 9.12 Pointer Access

```wgsl
let ptr: ptr<function, i32> = ...;
let x = ptr^;  // dereference
ptr^ = 42;      // assign through pointer
```

### 9.13 Struct and Array Length

```wgsl
let arr = array<f32, 10>(...);
let len = arrayLength(&arr);  // 10

struct MyStruct {
  @size(16) data: array<u8>,
}
let s = MyStruct(...);
let len = arrayLength(&s.data);
```

### 9.14 Function Calls

```wgsl
let x = myFunction(a, b, c);
let y = builtinFunction(z);
```

### 9.15 Equality Testing

```wgsl
let equal = (x == y);
let notEqual = (x != y);
```

### 9.16 Boolean Contexts

Expressions used in boolean contexts (`if`, `while`, etc.) are converted to `bool`.

### 9.17 Short Circuiting

Logical operators short-circuit:

```wgsl
if (x || expensive()) { ... }  // expensive() not called if x is true
if (y && expensive()) { ... }  // expensive() not called if y is false
```

### 9.18 Ternary Selection

```wgsl
let result = condition ? trueValue : falseValue;
```

### 9.19 Expression Evaluation Order

Evaluation order is **unspecified** except where noted (e.g., short-circuiting, sequence operator).

---

## 🔟 STATEMENTS

### 10.1 General

Statements are the building blocks of executable code.

### 10.2 Empty Statement

```wgsl
;  // empty statement
```

### 10.3 Block Statements

```wgsl
{
  let x = 42;
  let y = 100;
}
```

### 10.4 Assignment Statements

```wgsl
x = 42;
y = x + 10;
```

### 10.5 Compound Assignment Statements

```wgsl
x += 10;  // x = x + 10
x -= 5;   // x = x - 5
x *= 2;   // x = x * 2
x /= 2;   // x = x / 2
x %= 3;   // x = x % 3
x &= mask; // x = x & mask
x |= mask; // x = x | mask
x ^= mask; // x = x ^ mask
x <<= 1;  // x = x << 1
x >>= 1;  // x = x >> 1
```

### 10.6 Increment and Decrement Statements

```wgsl
x++;  // post-increment
y--;  // post-decrement
++x;  // pre-increment
--y;  // pre-decrement
```

### 10.7 If Statements

```wgsl
if (condition) {
  // statements
}

if (condition) {
  // statements
} else {
  // else statements
}

if (condition1) {
  // statements
} else if (condition2) {
  // else-if statements
} else {
  // else statements
}
```

### 10.8 Switch Statements

```wgsl
switch (value) {
  case 1: {
    // statements
    break;
  }
  case 2: {
    // statements
    break;
  }
  default: {
    // default statements
  }
}
```

**Important**: `break` is **required** in switch cases unless the case ends with `return`, `discard`, or another control flow statement that exits the function.

### 10.9 Loops

#### 10.9.1 while Loops

```wgsl
while (condition) {
  // statements
}

// With continuing and breaking blocks
while (condition) : label {
  // statements
  continue label;  // optional label
  break label;     // optional label
}
```

#### 10.9.2 for Loops

```wgsl
for (init; condition; update) {
  // statements
}

// Example
for (var i = 0; i < 10; i++) {
  print(i);
}

// For-each style (WGSL 2.0+)
for (var x : array) {
  print(x);
}
```

#### 10.9.3 Loop Control Statements

```wgsl
continue;       // skip to next iteration
break;         // exit loop
break if (cond); // exit loop if condition is true
```

#### 10.9.4 break if Statement

```wgsl
for (var i = 0; i < 100; i++) {
  break if (i == 10);  // exit after 10 iterations
  // process i
}
```

### 10.10 Return Statements

```wgsl
fn myFunction() -> i32 {
  return 42;
}

fn voidFunction() {
  return;  // optional in void functions
}
```

### 10.11 Discard Statement

```wgsl
@fragment
fn main() -> @location(0) vec4<f32> {
  if (shouldDiscard) {
    discard;  // discard this fragment
  }
  return color;
}
```

> **Note**: `discard` is only valid in fragment shaders.

### 10.12 Function Call Statements

```wgsl
myFunction(a, b, c);  // call as statement (result ignored)
```

---

## 1️⃣1️⃣ FUNCTIONS

### 11.1 General

Functions are the primary mechanism for organizing and reusing code.

### 11.2 Function Declarations

```wgsl
fn functionName(param1: type1, param2: type2) -> returnType {
  // body
}

// With template parameters
fn generic<T>(x: T) -> T {
  return x;
}

// With attributes
@compute @workgroup_size(1)
fn main() {
  // compute shader
}
```

### 11.3 Function Bodies

Function bodies consist of:

- **Statements**: Executable code
- **Return value**: The value returned by `return`
- **Implicit return**: For functions with no body (declarations only)

### 11.4 Entry Points

Entry points are special functions that:

- Are marked with a **stage attribute** (`@vertex`, `@fragment`, `@compute`)
- Define the entry point for a shader stage
- Have specific requirements on parameters and return values

**Vertex entry point**:
```wgsl
@vertex
fn main(
  @location(0) position: vec4<f32>,
  @location(1) uv: vec2<f32>
) -> @builtin(position) vec4<f32> {
  return position;
}
```

**Fragment entry point**:
```wgsl
@fragment
fn main(
  @location(0) color: vec4<f32>
) -> @location(0) vec4<f32> {
  return color;
}
```

**Compute entry point**:
```wgsl
@compute @workgroup_size(10, 10, 1)
fn main(@builtin(global_invocation_id) id: vec3<u32>) {
  // compute work
}
```

### 11.5 Builtin Functions

WGSL provides hundreds of builtin functions. See [Section 12](#12-builtin-functions) for the complete list.

### 11.6 Function Overloading

Functions can be overloaded based on:

- Number of parameters
- Types of parameters
- Template parameters

```wgsl
fn add(x: i32, y: i32) -> i32 { return x + y; }
fn add(x: f32, y: f32) -> f32 { return x + y; }
fn add(x: vec2<f32>, y: vec2<f32>) -> vec2<f32> { return x + y; }
```

### 11.7 Functions in a Shader Stage

A **shader** consists of:

- An **entry point** function
- All functions **transitively called** by the entry point
- All types, constants, and variables **statically accessed** by those functions

---

## 1️⃣2️⃣ BUILTIN FUNCTIONS

WGSL provides a comprehensive set of builtin functions organized into categories.

### 12.1 General

All builtin functions:

- Are **implicitly declared** (no need to import)
- Have **fixed signatures** (cannot be redefined)
- Are **type-generic** where applicable
- May have **overloads** for different types

### 12.2 Normalization Rules

Builtin function names follow the naming convention:

```
[prefix][operation][type_suffix]
```

Examples:
- `sin` → scalar sine
- `sinVec2` → vector sine (if exists)
- `f32` → type suffix for f32
- `i32` → type suffix for i32

### 12.3 Argument Compatibility

Builtin functions accept arguments that are:

- Exactly the declared type
- Convertible to the declared type
- Promotable to the declared type

### 12.4 Relaxed Precisions

Some functions have **relaxed precision** versions:

- Standard: Full precision
- `Relaxed`: Reduced precision (faster on some hardware)

### 12.5 Builtin Function Categories

#### 12.5.1 Platform Mapping

Functions for mapping between platforms:

- `abs(x)`: Absolute value
- `sign(x)`: Sign of x (-1, 0, +1)
- `min(x, y)`, `max(x, y)`: Minimum/maximum
- `clamp(x, low, high)`: Clamp x between low and high
- `mix(x, y, a)`: Linear interpolation: x + a * (y - x)
- `step(edge, x)`: 0 if x < edge, 1 otherwise
- `smoothstep(low, high, x)`: Smooth Hermite interpolation

#### 12.5.2 WGSL Optimization Hints

Functions that provide optimization hints:

- `assume(condition)`: Hint that condition is true
- `unreachable()`: Hint that code is unreachable

#### 12.5.3 Float Memory Access

Functions for type-punning and memory access:

- `bitcast<T>(x)`: Reinterpret bits as type T
- `as<T>(x)`: Convert x to type T

#### 12.5.4 Vector Constructors

```wgsl
vec2<f32>(x)           // vec2(x, x)
vec2<f32>(x, y)        // vec2(x, y)
vec3<f32>(x)           // vec3(x, x, x)
vec3<f32>(x, y)        // vec3(x, y, 0)
vec3<f32>(x, y, z)     // vec3(x, y, z)
vec4<f32>(x)           // vec4(x, x, x, x)
vec4<f32>(x, y)        // vec4(x, y, 0, 1)
vec4<f32>(x, y, z)     // vec4(x, y, z, 1)
vec4<f32>(x, y, z, w)  // vec4(x, y, z, w)
```

#### 12.5.5 Matrix Constructors

```wgsl
mat2x2<f32>(c0, c1)        // 2x2 matrix from 2 vec2
mat3x3<f32>(c0, c1, c2)   // 3x3 matrix from 3 vec3
mat4x4<f32>(c0, c1, c2, c3) // 4x4 matrix from 4 vec4
```

#### 12.5.6 Length Calculations

```wgsl
length(x)       // vector length (Euclidean norm)
```

#### 12.5.7 Dot Products

```wgsl
dot(x, y)       // dot product of vectors
```

#### 12.5.8 Outer Products

```wgsl
outerProduct(x, y)  // outer product (matrix from 2 vectors)
```

#### 12.5.9 Cross Product

```wgsl
cross(x, y)    // cross product (vec3 only)
```

#### 12.5.10 Determinants

```wgsl
determinant(m)  // determinant of matrix
```

#### 12.5.11 Inverses

```wgsl
inverse(m)      // matrix inverse
```

#### 12.5.12 Transposes

```wgsl
transpose(m)    // matrix transpose
```

#### 12.5.13 Normalization

```wgsl
normalize(x)    // normalize vector (unit length)
```

#### 12.5.14 Vector and Matrix Math

**Trigonometric**:
- `sin(x)`, `cos(x)`, `tan(x)`
- `asin(x)`, `acos(x)`, `atan(x)`
- `sinh(x)`, `cosh(x)`, `tanh(x)`
- `atan2(y, x)`

**Exponential**:
- `exp(x)`, `exp2(x)`
- `log(x)`, `log2(x)`
- `pow(x, y)`
- `sqrt(x)`, `inverseSqrt(x)`

**Common**:
- `abs(x)`
- `sign(x)`
- `floor(x)`, `ceil(x)`, `round(x)`, `trunc(x)`
- `fract(x)` (fractional part)
- `mod(x, y)` (modulo)
- `fma(a, b, c)` (fused multiply-add: a * b + c)

#### 12.5.15 Integer Math

- `abs(x)`
- `min(x, y)`, `max(x, y)`
- `clamp(x, low, high)`
- `clz(x)`, `ctz(x)` (count leading/trailing zeros)
- `popcount(x)` (population count)
- `reverseBits(x)`

#### 12.5.16 Trigonometric Functions

All trigonometric functions work on:
- Scalar numeric types
- Vector numeric types (element-wise)

#### 12.5.17 Exponential and Logarithmic Functions

Work on floating-point types only.

#### 12.5.18 Floating-point Packing Functions

- `pack4x8snorm(x)`, `unpack4x8snorm(x)`
- `pack4x8unorm(x)`, `unpack4x8unorm(x)`
- `pack2x16snorm(x)`, `unpack2x16snorm(x)`
- `pack2x16unorm(x)`, `unpack2x16unorm(x)`
- `pack2x16float(x)`, `unpack2x16float(x)`

#### 12.5.19 Floating-point Min/Max

- `fmin(x, y)`, `fmax(x, y)`

#### 12.5.20 Bit Operations

- `bitCount(x)`
- `bitFieldExtract(x, offset, count)`
- `bitFieldInsert(x, y, offset, count)`
- `bitFieldReverse(x)`

#### 12.5.21 Quantization Functions

- `quantizeToF16(x)`

#### 12.5.22 Texture Sampling Functions

**Sampling**:
- `textureSample(texture, sampler, coord)`
- `textureSampleBias(texture, sampler, coord, bias)`
- `textureSampleGrad(texture, sampler, coord, ddx, ddy)`
- `textureSampleLevel(texture, sampler, coord, level)`

**With offsets**:
- `textureSampleOffset(texture, sampler, coord, offset)`
- `textureSampleOffsetBias(texture, sampler, coord, offset, bias)`

**Comparison**:
- `textureSampleCompare(texture, sampler, coord, ref)`
- `textureSampleCompareLevel(texture, coord, ref, level)`

#### 12.5.23 Texture Query Functions

- `textureDimensions(texture)`
- `textureNumLayers(texture)`
- `textureNumLevels(texture)`
- `textureNumSamples(texture)`
- `textureSampleCount(texture)`

#### 12.5.24 Texture Gathering Functions

- `textureGather(texture, sampler, coord, component)`
- `textureGatherOffset(texture, sampler, coord, offset, component)`
- `textureGatherOffsets(texture, sampler, coord, offsets, component)`

#### 12.5.25 Comparison Functions

- `equal(x, y)`
- `notEqual(x, y)`
- `lessThan(x, y)`
- `lessThanEqual(x, y)`
- `greaterThan(x, y)`
- `greaterThanEqual(x, y)`

> **Note**: These return vector<bool> for vector inputs.

#### 12.5.26 Type Cast

- `i32(x)`, `u32(x)`, `f32(x)`, etc.

#### 12.5.27 Conversion

- `f32(x)`: Convert to f32
- `i32(x)`: Convert to i32
- `u32(x)`: Convert to u32

#### 12.5.28 Reinterpretation

- `bitcast<T>(x)`: Reinterpret bits

#### 12.5.29 Array Length

- `arrayLength(arr)`: Get array length

#### 12.5.30 Atomic Functions

- `atomicAdd(ptr, value)`
- `atomicSub(ptr, value)`
- `atomicMax(ptr, value)`
- `atomicMin(ptr, value)`
- `atomicAnd(ptr, value)`
- `atomicOr(ptr, value)`
- `atomicXor(ptr, value)`
- `atomicExchange(ptr, value)`
- `atomicCompareExchangeWeak(ptr, expected, replacement)`
- `atomicCompareExchangeStrong(ptr, expected, replacement)`
- `atomicLoad(ptr)`
- `atomicStore(ptr, value)`

#### 12.5.31 Barrier Functions

- `storageBarrier()`
- `workgroupBarrier()`

#### 12.5.32 Workgroup Functions

- `workgroupUniformLoad(ptr)`

#### 12.5.33 Subgroup Functions

- `subgroupElect()`
- `subgroupAll(x)`
- `subgroupAny(x)`
- `subgroupAllEqual(x)`
- `subgroupBroadcast(x, id)`
- etc.

#### 12.5.34-42 Ray Tracing Functions

See the WGSL specification for the complete list of ray tracing functions.

---

## 1️⃣3️⃣ ATTRIBUTES

Attributes provide additional information about declarations.

### 13.1 General

Attributes are specified using the `@` syntax:

```wgsl
@attribute_name(arguments...)
```

### 13.2 Parameter Attributes

- `@builtin(name)`: Built-in parameter (e.g., `@builtin(position)`)

### 13.3 Variable and Struct Member Attributes

- `@align(n)`: Alignment
- `@size(n)`: Size in bytes
- `@offset(n)`: Offset in bytes
- `@stride(n)`: Stride in bytes

### 13.4 Struct Attributes

- `@packed`: Packed struct (no padding)

### 13.5 Function Attributes

**Shader stage**:
- `@vertex`
- `@fragment`
- `@compute`

**Workgroup size** (for compute shaders):
- `@workgroup_size(x)`
- `@workgroup_size(x, y)`
- `@workgroup_size(x, y, z)`

**Entry point control**:
- `@early_depth_test(level)`: Controls early depth testing
- `@unroll_loops`: Hint to unroll loops

---

## 1️⃣4️⃣ SHADER INTERFACE AND RESOURCE BINDING

### 14.1 General

The **shader interface** defines how a shader interacts with the outside world through:

- **Inputs and outputs**: Data passed to/from shader stages
- **Builtin variables**: Predefined variables with special meaning
- **Resource binding**: Access to external resources (textures, buffers, etc.)

### 14.2 Inputs and Outputs

Inputs and outputs are declared using `@location`:

```wgsl
@vertex
fn main(
  @location(0) position: vec4<f32>,
  @location(1) uv: vec2<f32>
) -> @builtin(position) vec4<f32> {
  return position;
}

@fragment
fn main(
  @location(0) color: vec4<f32>
) -> @location(0) vec4<f32> {
  return color;
}
```

### 14.3 Builtin Variables

Builtin variables provide access to special values:

**Vertex shader builtins**:
- `@builtin(vertex_index)`: Vertex index (u32)
- `@builtin(instance_index)`: Instance index (u32)

**Fragment shader builtins**:
- `@builtin(position)`: Fragment position (vec4<f32>)
- `@builtin(frag_depth)`: Fragment depth (f32)
- `@builtin(sample_index)`: Sample index (u32)
- `@builtin(sample_mask)`: Sample mask (u32)

**Compute shader builtins**:
- `@builtin(global_invocation_id)`: Global invocation ID (vec3<u32>)
- `@builtin(local_invocation_id)`: Local invocation ID (vec3<u32>)
- `@builtin(local_invocation_index)`: Local invocation index (u32)
- `@builtin(workgroup_id)`: Workgroup ID (vec3<u32>)
- `@builtin(num_workgroups)`: Number of workgroups (vec3<u32>)

**Common builtins**:
- `@builtin(subgroup_invocation_id)`: Subgroup invocation ID (u32)
- `@builtin(subgroup_size)`: Subgroup size (u32)

### 14.4 Resource Binding

Resources (textures, samplers, buffers) are bound to variables using `@group` and `@binding`:

```wgsl
@group(0) @binding(0) var<uniform> camera: CameraUniforms;
@group(1) @binding(0) var<storage> vertices: array<Vertex>;
@group(1) @binding(1) var texture: texture_2d<f32>;
@group(1) @binding(2) var sampler: sampler;
```

### 14.5 Binding Attributes

**Storage class**:
- `uniform`: Read-only, uniform across all invocations
- `storage`: Read-write, storage buffers
- `read`: Read-only
- `write`: Write-only
- `read_write`: Read and write

**Access mode** (for storage class):
- `@group(n) @binding(m) var<storage, read> data: T;
- `@group(n) @binding(m) var<storage, write> data: T;
- `@group(n) @binding(m) var<storage, read_write> data: T;

### 14.6 Resource Binding Validation

The implementation validates that:

- All resources are valid for their binding point
- No two variables share the same binding
- Resource types match their usage

---

## 1️⃣5️⃣ BUILTIN VALUES

### 15.1 General

Builtin values are predefined constants and variables.

### 15.2 Function Builtins

See [Section 12](#12-builtin-functions) for builtin functions.

### 15.3 Type Builtins

Builtin types include:

- Scalar types: `bool`, `i8`, `i16`, `i32`, `i64`, `u8`, `u16`, `u32`, `u64`, `f16`, `f32`
- Vector types: `vec2<T>`, `vec3<T>`, `vec4<T>`
- Matrix types: `matCxR<T>`
- Array types: `array<T, N>`, `array<T>`
- Struct types
- Atomic types: `atomic<T>`
- Sampler types: `sampler`, `sampler_comparison`
- Texture types: `texture_1d`, `texture_2d`, `texture_2d_array`, `texture_3d`, `texture_cube`, `texture_multisampled_2d`
- Depth texture types: `texture_depth_2d`, `texture_depth_2d_array`, `texture_depth_cube`, `texture_depth_multisampled_2d`
- External texture type: `texture_external`

---

## 📚 GRAMMAR SUMMARY

### Lexical Grammar

```
input           : (whitespace / comment / token)*

whitespace      : SPACE / TAB / NEWLINE / CR / FF

comment         : single_line_comment / multi_line_comment
single_line_comment: "//" (CHAR - NEWLINE)*
multi_line_comment  : "/*" (CHAR - "*/")* "*/"

token           : keyword / identifier / literal / operator / punctuator

keyword         : "align" / "alias" / "atomic" / "binding" / ... (108+ keywords)

identifier      : (LETTER / "_") (LETTER / DIGIT / "_")*

literal         : integer_literal / float_literal / bool_literal
integer_literal : DECINT / HEXINT / DECINT "i" / DECINT "u"
float_literal   : DECFLT / HEXFLT
bool_literal    : "true" / "false"

operator        : "+" / "-" / "*" / "/" / "%" / "&" / "|" / "^" / "~" / "!" /
                  "==" / "!=" / "<" / ">" / "<=" / ">=" / "&&" / "||" /
                  "<<" / ">>" / "..." / "." / "->"

punctuator      : "(" / ")" / "{" / "}" / "[" / "]" / "," / ";" / ":" / "::" / "?"
```

### Syntactic Grammar (Selected Rules)

```
translation_unit: (directive / type_decl / global_const_decl / global_var_decl / function_decl)*

directive       : enable_directive / diagnostic_directive

enable_directive: "@enable" "extension" identifier ";"

function_decl   : (attribute*)? function_header compound_statement?
function_header : "fn" identifier "<" template_param_list? ">" "(" param_list? ")" ("->" type_decl)?

compound_statement: "{" statement* "}"

statement       : empty_statement / compound_statement / assignment_statement / ...

if_statement    : "if" expression compound_statement ("else if" expression compound_statement)* ("else" compound_statement)?

for_statement   : "for" "(" for_init? ";" expression? ";" expression? ")" compound_statement
while_statement  : "while" expression ":" identifier? compound_statement

return_statement: "return" expression? ";"

discard_statement: "discard" ";"

break_statement : "break" identifier? ";"
continue_statement: "continue" identifier? ";"

switch_statement: "switch" "(" expression ")" "{" switch_clause* "}"
switch_clause   : ("case" expression ":" / "default" ":") compound_statement

expression      : logical_or_expression
logical_or_expression: logical_and_expression ("||" logical_and_expression)*
logical_and_expression: equality_expression ("&&" equality_expression)*
... (continues for all operator precedences)

primary_expression: literal / identifier / "(" expression ")" / function_call
function_call   : identifier "<" template_arg_list? ">" "(" argument_expression_list? ")"

member_access   : primary_expression "." identifier
index_expression: primary_expression "[" expression "]"

array_length    : "arrayLength" "(" expression ")"

type_decl       : identifier "<" template_arg_list? ">" / scalar_type / vector_type / matrix_type / array_type / struct_type / atomic_type
scalar_type     : "bool" / "i8" / "i16" / "i32" / "i64" / "u8" / "u16" / "u32" / "u64" / "f16" / "f32"
vector_type     : "vec" DECINT "<" type_decl ">"
matrix_type     : "mat" DECINT "x" DECINT "<" type_decl ">"
array_type      : "array" "<" type_decl ("," expression)? ">"
struct_type     : "struct" identifier "{" struct_member* "}"
struct_member   : (attribute*)? identifier ":" type_decl ("=" expression)? ","

attribute       : "@" identifier "(" attribute_arg_list? ")"
```

---

## 🎯 QUICK REFERENCE

### Type Categories

| Category | Types | Size (bytes) |
|----------|-------|--------------|
| **Boolean** | `bool` | 1 |
| **Signed Integer** | `i8`, `i16`, `i32`, `i64` | 1, 2, 4, 8 |
| **Unsigned Integer** | `u8`, `u16`, `u32`, `u64` | 1, 2, 4, 8 |
| **Floating Point** | `f16`, `f32` | 2, 4 |
| **Vector** | `vec2<T>`, `vec3<T>`, `vec4<T>` | 2×N, 3×N, 4×N |
| **Matrix** | `matCxR<T>` | C×R×N |
| **Array** | `array<T, N>`, `array<T>` | Dynamic |
| **Struct** | User-defined | Implementation-defined |
| **Atomic** | `atomic<T>` | Same as T |

### Common Builtin Functions

| Category | Functions |
|----------|-----------|
| **Math** | `abs`, `min`, `max`, `clamp`, `mix`, `step`, `smoothstep` |
| **Trigonometry** | `sin`, `cos`, `tan`, `asin`, `acos`, `atan`, `atan2` |
| **Exponential** | `exp`, `exp2`, `log`, `log2`, `pow`, `sqrt` |
| **Vector** | `length`, `normalize`, `dot`, `cross`, `distance` |
| **Matrix** | `determinant`, `inverse`, `transpose` |
| **Texture** | `textureSample`, `textureLoad`, `textureDimensions` |
| **Atomic** | `atomicAdd`, `atomicExchange`, `atomicCompareExchange` |

### Shader Stages

| Stage | Attribute | Typical Inputs | Typical Outputs |
|-------|-----------|----------------|-----------------|
| **Vertex** | `@vertex` | Vertex data | Clip-space position, varying outputs |
| **Fragment** | `@fragment` | Interpolated varyings | Framebuffer color/depth |
| **Compute** | `@compute` | Workgroup ID, invocation ID | Storage buffer writes |

### Storage Classes

| Class | Description | Typical Usage |
|-------|-------------|---------------|
| `function` | Function-local | Local variables |
| `private` | Private to shader | Temporary storage |
| `workgroup` | Workgroup-shared | Shared memory |
| `uniform` | Read-only, uniform | Uniform buffers |
| `storage` | Read-write | Storage buffers |
| `push_constant` | Push constants | Small uniform data |

### Access Modes

| Mode | Description |
|------|-------------|
| `read` | Read-only access |
| `write` | Write-only access |
| `read_write` | Read and write access |

---

## 📖 REFERENCES

- [WebGPU Specification](https://gpuweb.github.io/gpuweb/)
- [WGSL Specification (HTML)](https://www.w3.org/TR/WGSL/)
- [Naga IR (Rust)](https://github.com/gfx-rs/naga)
- [webgpu-ktypes Project](https://github.com/ygdrasil-team/webgpu-ktypes)

---

*Document generated from W3C WGSL Specification (CRD version)*
*For the most accurate and up-to-date information, always refer to the official specification.*
