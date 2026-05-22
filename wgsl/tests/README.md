# WGSL Tests

This module contains the test infrastructure for WGSL (WebGPU Shading Language) parsing, validation, and code generation.

## Structure

```
wgsl/tests/
├── build.gradle.kts          # Gradle test configuration
├── README.md                # This file
└── src/
    └── jvmTest/
        └── kotlin/
            └── io/ygdrasil/wgsl/tests/
                ├── GoldenTestBase.kt     # Base class for all golden tests
                ├── GoldenDebug.kt        # Standalone debug tool
                ├── Registration.kt       # Backend registration
                ├── RoundTripTest.kt      # WGSL round-trip tests
                ├── WgslGoldenTest.kt     # WGSL golden tests
                ├── MslGoldenTest.kt      # MSL golden tests
                ├── HlslGoldenTest.kt     # HLSL golden tests
                ├── GlslGoldenTest.kt     # GLSL golden tests
                ├── IrGoldenTest.kt       # IR golden tests
                └── validator/            # Native validation utilities
```

## Golden Tests

Golden tests compare the output of the WGSL compiler against pre-generated "golden" files. This ensures that:
1. The parser correctly handles all WGSL syntax
2. Type resolution works as expected
3. Code generation produces consistent output
4. Native validation passes (when available)

### Test Data Location

```
tests/golden/
├── inputs/   # WGSL source files (160+ files)
└── outputs/  # Generated output files by backend
    ├── wgsl/
    ├── msl/
    ├── hlsl/
    ├── glsl/
    └── ir/
```

### Supported Backends

- **wgsl** - WebGPU Shading Language (normalized comparison)
- **msl** - Metal Shading Language
- **hlsl** - High-Level Shading Language
- **glsl** - OpenGL Shading Language
- **ir** - Intermediate Representation (JSON format)

## Running Tests

### Basic Usage

```bash
# Run all golden tests for all backends
./gradlew :wgsl:tests:jvmTest

# Run tests for a specific backend only
./gradlew :wgsl:tests:jvmTest --tests "*WgslGoldenTest*"
./gradlew :wgsl:tests:jvmTest --tests "*MslGoldenTest*"
```

### Filtering Tests

Use `GOLDEN_FILTER` to run tests for specific files only:

```bash
# Run tests only for files containing "empty" in their name
GOLDEN_FILTER="empty" ./gradlew :wgsl:tests:jvmTest

# Run tests for a specific file
GOLDEN_FILTER="access" ./gradlew :wgsl:tests:jvmTest

# Run tests for multiple patterns
GOLDEN_FILTER="abstract-types" ./gradlew :wgsl:tests:jvmTest
```

This is particularly useful during development to focus on a single file without running all 160+ tests.

### Controlling Error Output

By default, error messages are clean and stack traces are hidden to keep output readable:

```
[wgsl] type-resolution failed for access.wgsl: Unresolved references: [...]
```

To see full stack traces in stdout:

```bash
GOLDEN_DEBUG=true ./gradlew :wgsl:tests:jvmTest
```

### Updating Golden Files

When you make changes that affect code generation, update the golden output files:

```bash
GOLDEN_UPDATE=true ./gradlew :wgsl:tests:jvmTest
```

This will create or update all output files in `tests/golden/outputs/`.

**Warning:** Review the changes carefully before committing updated golden files.

## Standalone Debug Tool

For debugging individual files, use the `GoldenDebug` tool:

### Usage

```bash
# Debug a single file with WGSL backend (default)
./gradlew :wgsl:tests:goldenDebug -Pargs="empty.wgsl"

# Debug with a specific backend
./gradlew :wgsl:tests:goldenDebug -Pargs="empty.wgsl msl"
./gradlew :wgsl:tests:goldenDebug -Pargs="empty.wgsl hlsl"
./gradlew :wgsl:tests:goldenDebug -Pargs="empty.wgsl glsl"
./gradlew :wgsl:tests:goldenDebug -Pargs="empty.wgsl ir"
```

### With Full Stack Trace

```bash
DEBUG=true ./gradlew :wgsl:tests:goldenDebug -Pargs="access.wgsl"
```

This will show the complete stack trace if an error occurs.

### Output

The debug tool shows progress through each compilation phase:

```
Debugging: access.wgsl (backend: wgsl)
============================================================
Phase 1: Parsing...
  ✓ Parse successful
Phase 2: Resolving types...
  ✓ Type resolution successful
Phase 3: Lowering to IR...
  ✓ Lowering successful
Phase 4: Generating wgsl code...
  ✓ Code generation successful

✓ All phases completed successfully!
```

Or, on failure:

```
Debugging: access.wgsl (backend: wgsl)
============================================================
Phase 1: Parsing...
  ✓ Parse successful
Phase 2: Resolving types...

✗ FAILED: [wgsl] type-resolution failed for access.wgsl
   Message: Unresolved references: [UnresolvedReferenceError(name=...)]
```

## Test Phases

Each golden test executes the following phases:

1. **Parse** - Lex and parse WGSL source into AST
2. **Type Resolution** - Resolve all type references
3. **Lowering** - Convert AST to intermediate representation (IR)
4. **Code Generation** - Generate backend-specific code
5. **Comparison/Update** - Compare with golden file or update it
6. **Native Validation** - Validate with native compiler (if available)

If any phase fails, a `GoldenTestException` is thrown with:
- File name
- Backend name
- Phase name
- Error message

## Error Handling

The test framework provides clean error messages by default:

### GoldenTestException

Custom exception type that provides structured error information:

```kotlin
class GoldenTestException(
    val fileName: String,      // e.g., "access.wgsl"
    val backend: String,       // e.g., "wgsl", "msl"
    val phase: String,         // e.g., "parse", "type-resolution"
    message: String,           // Error description
    cause: Throwable?         // Original exception (logged separately)
) : RuntimeException("[$backend] $phase failed for $fileName: $message", cause)
```

### Error Logging

Full stack traces are logged to the logger (DEBUG level) but not printed to stdout by default. This keeps test output clean while preserving debugging information.

## Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `GOLDEN_UPDATE` | Update golden output files | `false` |
| `GOLDEN_FILTER` | Filter test files by name | `null` (all files) |
| `GOLDEN_DEBUG` | Show full stack traces in stdout | `false` |
| `DEBUG` | (For GoldenDebug tool) Show stack traces | `false` |

## Examples

### Develop a New Feature

```bash
# Test a specific file repeatedly during development
GOLDEN_FILTER="my-feature" ./gradlew :wgsl:tests:jvmTest

# Debug issues with full output
GOLDEN_FILTER="my-feature" GOLDEN_DEBUG=true ./gradlew :wgsl:tests:jvmTest

# Use the debug tool for interactive testing
./gradlew :wgsl:tests:goldenDebug -Pargs="my-feature.wgsl"
```

### Update All Golden Files

```bash
# Update all backends
GOLDEN_UPDATE=true ./gradlew :wgsl:tests:jvmTest

# Update only WGSL golden files
GOLDEN_UPDATE=true ./gradlew :wgsl:tests:jvmTest --tests "*WgslGoldenTest*"
```

### Debug a Failing Test

```bash
# Find which test is failing
./gradlew :wgsl:tests:jvmTest

# Run only that specific test with debug output
GOLDEN_FILTER="failing-file" GOLDEN_DEBUG=true ./gradlew :wgsl:tests:jvmTest

# Or use the standalone debugger
DEBUG=true ./gradlew :wgsl:tests:goldenDebug -Pargs="failing-file.wgsl"
```

## Log Files

When configured, error details are written to:

- `wgsl/tests/build/test-logs.log` - Main test log
- `wgsl/tests/build/reports/golden-exceptions.log` - Exception details

## Native Validation

Native validation is performed when available:

- **MSL**: Validated with Metal compiler (macOS only)
- **GLSL**: Validated with GLSL validator
- **HLSL**: Validated with HLSL compiler
- **SPIRV**: Validated with SPIR-V tools

Validation requires the appropriate tools to be installed and available in PATH.

## Adding New Tests

1. Add your WGSL source file to `tests/golden/inputs/`
2. Run with `GOLDEN_UPDATE=true` to generate initial output files
3. Verify the generated output is correct
4. Commit both the input and output files

## Troubleshooting

### "Backend not found" Error

Ensure all backends are registered. Check `Registration.kt`:

```kotlin
fun registerAllBackends() {
    registerIrBackend()
    registerMslBackend()
    registerHlslBackend()
    registerGlslBackend()
    registerWgslBackend()
}
```

### "File not found" Error

Verify the file exists in `tests/golden/inputs/` and has the `.wgsl` extension.

### Validation Failures

If native validation fails, check:
1. The native compiler is installed
2. The shader is valid for the target backend
3. The shader stage (vertex, fragment, compute) is correctly specified

### Test Output Too Verbose

Use the default settings (without `GOLDEN_DEBUG`) for clean output. Stack traces are logged separately and can be examined when needed.

## Performance Tips

- Use `GOLDEN_FILTER` to limit tests during development
- Run specific backend tests: `--tests "*WgslGoldenTest*"`
- The `GoldenDebug` tool is fastest for single-file iteration
- Consider using `--no-daemon` for one-off test runs
