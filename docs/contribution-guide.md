# WebGPU Kotlin Toolkit - Contribution Guide

**Date:** 2026-05-19  
**Document Type:** Contribution Guide  
**Language:** English  
**Target Audience:** Contributors, Developers, Maintainers  

---

## Welcome!

Thank you for your interest in contributing to the **WebGPU Kotlin Toolkit**! This guide provides comprehensive information on how to contribute to the project, including code style conventions, PR process, testing requirements, and more.

### Why Contribute?

The WebGPU Kotlin Toolkit is a critical infrastructure project for the Kotlin ecosystem, providing:
- **Type-safe bindings** to the WebGPU API
- **Multiplatform support** across 19+ targets
- **Performance-optimized** implementations for each platform
- **Foundation for graphics programming** in Kotlin

Your contributions help:
- ✅ Improve WebGPU support for Kotlin developers
- ✅ Add new platform targets
- ✅ Enhance performance and correctness
- ✅ Expand the Kotlin graphics ecosystem

---

## Table of Contents

1. [Code Style and Conventions](#code-style-and-conventions)
2. [Pull Request Process](#pull-request-process)
3. [Testing Requirements](#testing-requirements)
4. [Documentation Standards](#documentation-standards)
5. [Commit Conventions](#commit-conventions)
6. [Review Process](#review-process)
7. [Getting Help](#getting-help)

---

## Code Style and Conventions

### Kotlin Style Guide

This project follows the **[Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)** with the following project-specific guidelines:

#### Formatting

1. **Indentation**: 4 spaces (no tabs)
2. **Line Length**: Maximum 120 characters (soft limit)
3. **Braces**: Always use braces for control structures
4. **Imports**: Group and sort imports (IDE can do this automatically)

```kotlin
// ✅ GOOD
if (condition) {
    doSomething()
}

// ❌ BAD - No braces for single statements in multi-line context
if (condition)
    doSomething()

// ✅ GOOD - Single-line is acceptable
if (condition) doSomething()
```

#### Naming Conventions

| Type | Convention | Example |
|------|------------|---------|
| Classes/Interfaces | PascalCase | `ArrayBuffer`, `JvmArrayBuffer` |
| Functions | camelCase | `getByte()`, `allocate()` |
| Variables | camelCase | `bufferSize`, `memorySegment` |
| Constants | UPPER_SNAKE_CASE | `MAX_SIZE`, `DEFAULT_CAPACITY` |
| Type Parameters | Single uppercase letter | `T`, `K`, `V` |
| Properties | camelCase (backing field: `_name`) | `size`, `_buffer` |

**Platform-Specific Naming:**
- Use platform suffix for platform-specific files: `.jvm.kt`, `.js.kt`, `.native.kt`, `.android.kt`, `.wasmJs.kt`
- Use platform prefix for platform-specific classes: `JvmArrayBuffer`, `AndroidArrayBuffer`

#### File Organization

1. **File Structure**: One primary class/interface per file
2. **File Naming**: Match the primary class/interface name
3. **Extensions**: Use `*Extensions.kt` for extension functions
4. **Utilities**: Use `*Utils.kt` or `*Helpers.kt` for utility classes

```
src/commonMain/kotlin/
├── ArrayBuffer.kt          # Primary class
├── ArrayBufferExtensions.kt  # Extension functions
└── ArrayBufferUtils.kt     # Utility functions
```

#### Type Safety

1. **Prefer Unsigned Types**: Use `UInt`, `UShort`, `UByte`, `ULong` where appropriate for WebGPU
2. **Nullable Types**: Use `?` for nullable types, avoid platform types (`null` checks)
3. **Collections**: Use Kotlin collections (`List`, `Set`, `Map`) over Java collections
4. **Arrays**: Prefer `ByteArray`, `IntArray`, etc. for primitive collections

#### Code Generation

**Important:** ~67% of the code in `commonMain` is **generated from the W3C WebGPU specification**.

1. **Generated Files**: Marked with `// This file has been generated DO NO EDIT`
2. **Do Not Modify**: Never edit generated files directly
3. **Regeneration**: Use the code generation toolchain to update
4. **Manual Changes**: Make changes to generation templates or source specifications

### Java Interop

When working with Java interop (JVM, Android):

1. **Use Kotlin-first APIs**: Wrap Java APIs with Kotlin-friendly interfaces
2. **Null Safety**: Handle Java nulls appropriately with `?` or `!!` (prefer `?`)
3. **SAM Conversions**: Use lambda syntax for single-abstract-method interfaces
4. **Checked Exceptions**: Handle or wrap Java checked exceptions

### Platform-Specific Code

1. **Expect/Actual Pattern**: Always use for platform-specific implementations
2. **Source Sets**: Place platform-specific code in appropriate source sets
3. **Type Aliases**: Use type aliases for platform-specific types when necessary

```kotlin
// ✅ GOOD - Proper expect/actual usage
// commonMain
expect sealed interface ArrayBuffer

// jvmMain
actual sealed interface ArrayBuffer
actual class JvmArrayBuffer : ArrayBuffer

// ❌ BAD - Platform-specific logic in common code
// commonMain
actual fun getPlatformName(): String {
    return when {
        Platform.osFamily == OsFamily.LINUX -> "Linux"
        else -> "Other"
    }
}
```

### Build Configuration

1. **Gradle KTS**: All build scripts use Kotlin DSL (`.kts` files)
2. **Convention Plugins**: Use convention plugins in `buildSrc` for shared configuration
3. **Property Access**: Use `project.findProperty()` or `System.getenv()` for configuration
4. **Dependency Management**: Use version catalogs (`libs.versions.toml`)

---

## Pull Request Process

### Before Submitting

1. **Read Existing Code**: Understand the patterns and conventions used
2. **Check for Duplicates**: Ensure your PR doesn't duplicate existing work
3. **Verify Build**: Run `./gradlew check` to ensure all tests pass
4. **Update Documentation**: Update relevant documentation (see [Documentation Standards](#documentation-standards))

### PR Requirements

Every PR must:
- ✅ Follow [commit conventions](#commit-conventions)
- ✅ Include a clear title and description
- ✅ Pass all CI checks
- ✅ Include relevant tests (see [Testing Requirements](#testing-requirements))
- ✅ Be properly formatted (run `./gradlew spotlessApply` if available)

### PR Title Format

Use the **Conventional Commits** format for PR titles:

```
feat: add support for new WebGPU feature
fix: resolve memory leak in JVM implementation
/docs: update README with installation instructions
/refactor: simplify ArrayBuffer implementation
/test: add comprehensive tests for Native platform
/chore: update Gradle wrapper to 9.5.0
```

### PR Description Template

```markdown
## Description

[Clear description of what this PR does]

## Motivation

[Why is this change needed? What problem does it solve?]

## Related Issues

- Fixes #[issue-number]
- Related to #[issue-number]

## Changes Made

- [ ] Added new feature
- [ ] Fixed bug
- [ ] Improved performance
- [ ] Updated documentation
- [ ] Refactored code
- [ ] Other (describe)

## Testing

- [ ] All existing tests pass
- [ ] New tests added for new functionality
- [ ] Manual testing performed

## Checklist

- [ ] Code follows project conventions
- [ ] Commit messages are clear and descriptive
- [ ] Documentation is updated
- [ ] All tests pass locally
- [ ] CI checks pass
```

### Branch Naming

Use descriptive branch names following this convention:

| Type | Format | Example |
|------|--------|---------|
| Feature | `feature/[description]` | `feature/add-wasm-support` |
| Bug Fix | `fix/[description]` | `fix/memory-leak-jvm` |
| Documentation | `docs/[description]` | `docs/update-readme` |
| Refactor | `refactor/[description]` | `refactor/arraybuffer-api` |
| Release | `release/[version]` | `release/v0.0.10` |
| Hotfix | `hotfix/[description]` | `hotfix/critical-security-fix` |
| Test | `test/[description]` | `test/add-native-tests` |
| Chore | `chore/[description]` | `chore/update-gradle` |

**Examples:**
```bash
# Good
git checkout -b feature/add-webgpu-descriptor-types
git checkout -b fix/arraybuffer-out-of-bounds
git checkout -b docs/contribution-guide

# Bad (too vague)
git checkout -b feature/new-stuff
git checkout -b fix/bug
```

### Creating a PR

1. **Fork the Repository** (if you don't have write access)
2. **Create a Branch** from `master`
3. **Make Your Changes**
4. **Commit** with clear messages (see [Commit Conventions](#commit-conventions))
5. **Push** your branch
6. **Open a Pull Request** on GitHub
7. **Request Review** from maintainers

### PR Review Process

See [Review Process](#review-process) section below.

---

## Testing Requirements

### Test Coverage

All new functionality must have **appropriate test coverage**:

| Component Type | Test Requirement |
|----------------|-----------------|
| Core Types | 100% API coverage |
| Platform Implementations | Platform-specific tests |
| Utilities | Unit tests for all public APIs |
| Bug Fixes | Tests that reproduce the bug and verify the fix |

### Test Structure

Follow the existing test structure:

```
src/
├── commonTest/kotlin/          # Platform-independent tests
│   └── ArrayBufferTest.kt    # Tests for common API
├── jvmTest/kotlin/            # JVM-specific tests
│   └── JvmArrayBufferTest.kt
├── nativeTest/kotlin/         # Native-specific tests
│   └── OpaquePointerArrayBufferTest.kt
└── jsTest/kotlin/             # JS-specific tests
    └── ...
```

### Test Framework: Kotest

The project uses **[Kotest](https://kotest.io/)** for testing:

- **Style**: FunSpec (Behavior-Driven Development)
- **Assertions**: Matcher-based (`shouldBe`, `shouldThrow`, etc.)
- **Concurrency**: Tests run in parallel by default

**Example Test:**

```kotlin
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class ArrayBufferTest : FunSpec({
    test("ArrayBuffer.allocate should create buffer of correct size") {
        val buffer = ArrayBuffer.allocate(100)
        buffer.toByteArray().size shouldBe 100
    }
    
    test("ArrayBuffer.getByte should return correct value") {
        val buffer = ArrayBuffer.allocate(10)
        buffer.setByte(0, 42)
        buffer.getByte(0) shouldBe 42
    }
    
    context("ArrayBuffer factory methods") {
        test("ofByteArray should create buffer from byte array") {
            val bytes = byteArrayOf(1, 2, 3, 4, 5)
            val buffer = ArrayBuffer.of(bytes)
            buffer.toByteArray() shouldBe bytes
        }
    }
})
```

### Running Tests

```bash
# Run all tests
./gradlew test

# Run tests for specific module
./gradlew :webgpu-ktypes:test

# Run tests with checks (build + tests + lint)
./gradlew check

# Run specific test class
./gradlew :webgpu-ktypes:test --tests "io.ygdrasil.webgpu.ArrayBufferTest"

# Run platform-specific tests
./gradlew jvmTest
./gradlew nativeTest
./gradlew jsTest
```

### Test Quality Guidelines

1. **Test Isolation**: Each test should be independent
2. **Clear Names**: Test names should describe the behavior being tested
3. **Single Assertion**: Each test should have one primary assertion
4. **Setup/Teardown**: Use `beforeTest`/`afterTest` for setup and cleanup
5. **Error Handling**: Test both success and error cases
6. **Edge Cases**: Test boundary conditions and edge cases

---

## Documentation Standards

### When to Update Documentation

Update documentation when:
- ✅ Adding new public APIs
- ✅ Changing existing behavior
- ✅ Fixing bugs that affect usage
- ✅ Adding new modules or major features
- ✅ Updating build or deployment processes

### Documentation Files

| File | Purpose | Update When |
|------|---------|-------------|
| `README.md` | Project overview | New features, major changes |
| `docs/project-overview.md` | Project overview | Architecture changes |
| `docs/development-guide.md` | Setup instructions | Build process changes |
| `docs/deployment-guide.md` | Deployment info | CI/CD changes |
| `docs/contribution-guide.md` | This file | Contribution process changes |
| KDoc Comments | API documentation | API changes |

### Documentation Format

All documentation is written in **Markdown** with the following standards:

1. **Headings**: Use ATX-style headings (`#`, `##`, `###`)
2. **Code Blocks**: Use triple backticks with language specification
3. **Tables**: Use GitHub Flavored Markdown tables
4. **Links**: Use relative paths for internal links
5. **Lists**: Use `-` for unordered lists, `1.` for ordered lists

### KDoc Comments

All public APIs must have **KDoc comments**:

```kotlin
/**
 * Allocates a new ArrayBuffer with the specified size in bytes.
 *
 * @param size The size of the buffer in bytes.
 * @return A new ArrayBuffer instance.
 * @throws IllegalArgumentException if size is negative.
 */
fun allocate(size: Int): ArrayBuffer

/**
 * Represents a binary data buffer for WebGPU operations.
 *
 * This sealed interface provides platform-independent access to binary data,
 * with platform-specific implementations provided via the expect/actual pattern.
 *
 * @see JvmArrayBuffer JVM-specific implementation
 * @see AndroidArrayBuffer Android-specific implementation
 */
expect sealed interface ArrayBuffer
```

**KDoc Guidelines:**
- First line: Brief description (ends with period)
- `@param` for each parameter
- `@return` for return value
- `@throws` for exceptions
- `@see` for related classes/methods
- `@since` for version information

### Examples in Documentation

Include **runnable code examples** in documentation:

```markdown
### Creating an ArrayBuffer

```kotlin
import io.ygdrasil.webgpu.ArrayBuffer

// Allocate a new buffer
val buffer = ArrayBuffer.allocate(1024)

// Write data
buffer.setInt(0, 42)
buffer.setFloat(4, 3.14f)

// Read data
val value: Int = buffer.getInt(0)
```
```

---

## Commit Conventions

This project follows **[Conventional Commits](https://www.conventionalcommits.org/)** specification.

### Commit Message Format

```
type(scope): subject

body

footer
```

### Commit Types

| Type | Description | When to Use |
|------|-------------|-------------|
| `feat` | A new feature | Adding new functionality |
| `fix` | A bug fix | Fixing a bug |
| `docs` | Documentation only changes | Updating documentation |
| `style` | Changes that do not affect the meaning of the code | Formatting, missing semicolons |
| `refactor` | A code change that neither fixes a bug nor adds a feature | Code restructuring |
| `perf` | A code change that improves performance | Performance optimizations |
| `test` | Adding missing tests | New tests, test improvements |
| `build` | Changes to the build system or external dependencies | Gradle config, dependencies |
| `ci` | Changes to CI configuration files and scripts | GitHub Actions, CI config |
| `chore` | Other changes that don't modify src or test files | Tooling, configuration |
| `revert` | Reverts a previous commit | Rolling back changes |

### Scope

The scope indicates the **module or component** affected by the change:

| Scope | Description |
|-------|-------------|
| `webgpu-ktypes` | Core module |
| `webgpu-ktypes-descriptors` | Descriptors module |
| `webgpu-ktypes-web` | Web module |
| `wgsl` | WGSL module |
| `wgsl:parser` | WGSL parser submodule |
| `wgsl:core` | WGSL core submodule |
| `build` | Build system |
| `docs` | Documentation |
| `ci` | CI/CD |

**Examples:**
```bash
# With scope
feat(webgpu-ktypes): add ArrayBuffer.allocate method
fix(wgsl:parser): resolve parsing error with shader functions

# Without scope (affects multiple modules)
refactor!: update expect/actual pattern across all modules
build: upgrade Gradle to 9.5.0
```

### Subject

The subject should:
- Be **concise** (50-72 characters is ideal)
- Use **lowercase** (except for proper nouns)
- **Not end with a period**
- Use **imperative mood** ("add" not "added", "fix" not "fixed")
- Start with a **verb**

**Examples:**
```bash
# ✅ GOOD
feat: add support for WebGPU 2.0 features
fix: resolve out of bounds access in ArrayBuffer

# ❌ BAD
feat: Added support for WebGPU 2.0 features  # (past tense, ends with period)
fix: Fixed out of bounds access in ArrayBuffer  # (past tense, ends with period)
```

### Body

The body should:
- Explain **what** was changed and **why**
- Include **motivation** and **context**
- Reference **issues** or **discussions**
- Use **bullet points** or **paragraphs**

**Example:**
```
feat(webgpu-ktypes): add WebGPU descriptor types

- Add RenderPipelineDescriptor type
- Add BindGroupLayoutDescriptor type
- Add ShaderModuleDescriptor type
- Generated from W3C WebGPU specification v2.0

Fixes #123
```

### Footer

The footer should:
- Reference **related issues** (`Closes #123`, `Fixes #456`)
- Note **breaking changes** (`BREAKING CHANGE:`)
- Include **co-authored-by** for multiple contributors

**Example:**
```
feat: implement WebGPU buffer mapping

Implement the GPUBuffer.mapAsync and GPUBuffer.unmap methods
for all platform targets.

Closes #789
BREAKING CHANGE: GPUBuffer.map is now async
```

### Examples of Good Commits

```bash
# Feature with scope and body
feat(webgpu-ktypes): add ArrayBuffer.slice method

Add a method to create a slice of an ArrayBuffer without copying data.
This is useful for WebGPU operations that work on buffer regions.

Closes #456

# Bug fix
fix: resolve memory leak in JvmArrayBuffer

The JvmArrayBuffer was not properly closing MemorySegment arenas,
causing memory leaks in long-running applications.

Fixes #789

# Documentation update
docs: update README with installation instructions

Add clear installation and setup instructions for new users.

# Refactor with breaking change
refactor!: update ArrayBuffer API to use ULong for size

Change all size parameters from Int to ULong to support larger buffers
and match the WebGPU specification.

BREAKING CHANGE: ArrayBuffer.allocate now takes ULong instead of Int

# Multiple co-authors
feat(webgpu-ktypes): add native platform support

Implement ArrayBuffer for Kotlin/Native using C interop.

Co-authored-by: Alice <alice@example.com>
Co-authored-by: Bob <bob@example.com>
```

### Commit Validation

Before pushing, validate your commits:

```bash
# Check commit message format
npx commitlint --edit

# Or use Git hooks (recommended)
# Install commitlint and husky
npm install --save-dev @commitlint/cli @commitlint/config-conventional husky
npx husky install
```

---

## Review Process

### Review Assignment

1. **Automatic**: GitHub will request reviews from project maintainers
2. **Manual**: You can request specific reviewers using `@username`
3. **Self-Review**: Always review your own changes before requesting others

### Review Criteria

Reviewers will check:

#### Code Quality
- [ ] **Style**: Code follows project conventions
- [ ] **Clarity**: Code is clear and readable
- [ ] **Simplicity**: Solution is simple and maintainable
- [ ] **Efficiency**: Code is performant and efficient
- [ ] **Safety**: Proper error handling and null safety

#### Functionality
- [ ] **Correctness**: Code works as intended
- [ ] **Testing**: Adequate test coverage
- [ ] **Edge Cases**: Handles edge cases properly
- [ ] **Backward Compatibility**: No breaking changes (unless intentional)

#### Documentation
- [ ] **KDoc**: All public APIs have KDoc comments
- [ ] **README**: Updated if necessary
- [ ] **Examples**: Code examples are clear and correct
- [ ] **Changelog**: Updated if necessary

#### Build & CI
- [ ] **Build**: Project builds successfully
- [ ] **Tests**: All tests pass
- [ ] **Checks**: All CI checks pass
- [ ] **Dependencies**: No unnecessary dependencies added

### Review Workflow

1. **Initial Review**: First reviewer provides feedback
2. **Address Comments**: Author addresses all comments
3. **Request Changes**: Reviewer requests changes if needed
4. **Approve**: Reviewer approves when all issues are resolved
5. **Second Review**: Optional second review for complex changes
6. **Merge**: Author or maintainer merges the PR

### Review Comments

**For Reviewers:**
- Be **specific** and **constructive**
- Reference **lines of code** in comments
- Suggest **improvements** not just problems
- Use **GitHub suggestions** for small fixes

**Example Comments:**
```markdown
# Good
This function should handle null values. Could you add a null check at line 42?

# Better (with suggestion)
This function should handle null values. Could you update it to:

```kotlin
fun process(data: String?) {
    requireNotNull(data) { "Data cannot be null" }
    // ...
}
```

# Best (with explanation)
This function should handle null values. The WebGPU spec requires that buffer data is never null. Could you add validation at line 42?

Also, consider using `checkNotNull` instead of `requireNotNull` since this is an internal method.
```

### Addressing Review Comments

1. **Respond to All Comments**: Reply to every comment, even if just to acknowledge
2. **Push Fixes**: Commit fixes as new commits (not amending unless requested)
3. **Request Re-Review**: Use "Request changes" → "Approve" or comment "Ready for re-review"
4. **Resolve Conversations**: Mark conversations as resolved when addressed

**Example Response:**
```markdown
Thanks for the feedback! I've addressed all your comments:

- Added null check in commit abc123
- Updated KDoc in commit def456
- Fixed formatting in commit ghi789

Ready for re-review!
```

### Merge Requirements

A PR can be merged when:
- ✅ All CI checks pass
- ✅ All review comments are addressed
- ✅ At least one maintainer has approved
- ✅ No unresolved conversations
- ✅ Branch is up to date with `master`

### Merge Strategies

This project uses **Squash Merge** by default:
- All commits are squashed into a single commit
- Commit message follows Conventional Commits format
- Preserves a clean Git history

**When to Use Rebase Merge:**
- For PRs with well-structured commits that should be preserved
- Requested by the author
- Approved by maintainers

**When to Use Merge Commit:**
- Rarely - only for very large PRs where preserving history is important

---

## Getting Help

### Communication Channels

| Channel | Purpose | Response Time |
|---------|---------|---------------|
| GitHub Issues | Bug reports, feature requests | 1-3 days |
| GitHub Discussions | General questions, ideas | 1-3 days |
| Pull Requests | Code review, contributions | 1-7 days |

### Asking Questions

Before asking a question:
1. **Search existing issues and discussions**
2. **Check the documentation**
3. **Review the code**
4. **Prepare a minimal reproduction** (for bugs)

**Good Issue:**
```markdown
## Description

When calling `ArrayBuffer.allocate` with a size larger than 2GB, the JVM implementation throws an OutOfMemoryError.

## Steps to Reproduce

```kotlin
val buffer = ArrayBuffer.allocate(3L * 1024 * 1024 * 1024) // 3GB
```

## Expected Behavior

Should allocate a buffer of the requested size, or throw an IllegalArgumentException with a clear message.

## Actual Behavior

```
java.lang.OutOfMemoryError: Requested array size exceeds VM limit
```

## Environment

- Java: 25.0.0-tem
- Kotlin: 2.0.0
- OS: macOS 14.4
```

**Bad Issue:**
```markdown
It doesn't work
```

### Reporting Bugs

Include:
- Clear description of the problem
- Steps to reproduce
- Expected vs actual behavior
- Environment information (Java version, Kotlin version, OS, platform)
- Minimal reproduction code

### Suggesting Features

Include:
- Clear description of the feature
- Use case and motivation
- Proposed API design
- Potential implementation approach

### Requesting Reviews

When requesting a review:
1. **Ensure all checks pass**
2. **Address self-review comments**
3. **Provide context** in the PR description
4. **Tag specific reviewers** if needed
5. **Be patient** - reviews take time

---

## Maintainer Guidelines

### For Project Maintainers

1. **Be Responsive**: Acknowledge PRs and issues within 24-48 hours
2. **Be Constructive**: Provide clear, actionable feedback
3. **Be Consistent**: Apply the same standards to all contributions
4. **Be Respectful**: Treat all contributors with respect
5. **Document Decisions**: Document architecture decisions and rationale

### Maintainer Responsibilities

- **Code Review**: Review PRs thoroughly and promptly
- **Issue Triage**: Triage new issues and label appropriately
- **Release Management**: Create releases and publish artifacts
- **Documentation**: Keep documentation up to date
- **Community**: Foster a welcoming and inclusive community

### Decision Making

1. **Consensus**: Strive for consensus among maintainers
2. **BDFL**: For contentious decisions, the project lead has final say
3. **Documentation**: Document significant decisions in ADR (Architecture Decision Records)

---

## Resources

### Development Resources

- [Development Guide](development-guide.md) - Setup and build instructions
- [Architecture Documentation](architecture-webgpu-ktypes.md) - Module architecture
- [WebGPU Specification](https://gpuweb.gpuinfo.org/) - Official W3C spec
- [Kotlin Documentation](https://kotlinlang.org/docs/home.html)
- [Gradle Documentation](https://docs.gradle.org/current/userguide/userguide.html)

### Contribution Resources

- [Conventional Commits](https://www.conventionalcommits.org/) - Commit message convention
- [Kotest Documentation](https://kotest.io/docs/intro.html) - Test framework docs
- [GitHub Flow](https://docs.github.com/en/get-started/quickstart/github-flow) - GitHub workflow

### Community Resources

- [Kotlin Slack](https://surveys.jetbrains.com/s3/kotlinlang-slack-sign-up) - Kotlin community
- [WebGPU Discuss](https://github.com/gpuweb/gpuweb/discussions) - WebGPU discussions

---

## Code of Conduct

This project adheres to a **Code of Conduct** (if available, typically `CODE_OF_CONDUCT.md`). All contributors are expected to:

- Be **welcoming** and **inclusive**
- Be **respectful** of differing viewpoints
- Accept **constructive criticism**
- Focus on **what is best for the project**
- Show **empathy** towards other community members

**Unacceptable behavior includes:**
- Harassment or discrimination
- Personal attacks
- Trolling or insults
- Public or private harassment
- Publishing others' private information

**Reporting Issues:**
If you experience or witness unacceptable behavior, please report it to the project maintainers.

---

## Document Information

| Attribute | Value |
|-----------|-------|
| **Generated By** | BMAD document-project workflow - Step 9 |
| **Date** | 2026-05-19 |
| **Language** | English |
| **Version** | 1.0 |
| **Related Documents** | [Development Guide](development-guide.md), [Project Overview](project-overview.md) |

---

*Thank you for contributing to the WebGPU Kotlin Toolkit! Your contributions help make WebGPU more accessible to Kotlin developers worldwide.*

*For questions or support, please open an issue or discussion on GitHub.*
