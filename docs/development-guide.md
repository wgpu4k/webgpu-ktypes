# WebGPU Kotlin Toolkit - Development Guide

**Date:** 2026-05-19  
**Language:** English  
**Target Audience:** Developers, Contributors  

---

## Prerequisites

### Required Tools

| Tool | Version | Purpose | Installation |
|------|---------|---------|-------------|
| Java JDK | 25+ | JVM compilation, Gradle | [Adoptium Temurin](https://adoptium.net/) or [Oracle JDK](https://www.oracle.com/java/technologies/javase-downloads.html) |
| Gradle | 9.5.0 | Build tool (via wrapper) | Automatic via `./gradlew` |
| Kotlin | 2.0+ | Kotlin compiler | Managed by Gradle |
| Git | 2.x | Version control | [git-scm.com](https://git-scm.com/) |

### Optional Tools

| Tool | Version | Purpose | When Needed |
|------|---------|---------|-------------|
| IntelliJ IDEA | 2024.3+ | IDE | Recommended for development |
| Android Studio | Latest | Android development | Android targets |
| Node.js | 20+ | JavaScript compilation | JS/Wasm targets |
| glslang-tools | Latest | GLSL validation | Native targets (Ubuntu) |
| spirv-tools | Latest | SPIR-V validation | Native targets (Ubuntu) |
| Docker | Latest | Containerization | CI/CD, deployment |

### Platform-Specific Requirements

#### macOS
- Xcode Command Line Tools (for Native targets)
- `xcode-select --install`

#### Linux (Ubuntu)
```bash
# For Native targets
sudo apt-get update
sudo apt-get install -y glslang-tools spirv-tools

# For Android targets
sudo apt-get install -y openjdk-17-jdk
```

#### Windows
- Visual Studio 2022 (for Native targets)
- Windows Subsystem for Linux (WSL) recommended

---

## Environment Setup

### 1. Clone the Repository

```bash
# HTTPS
git clone https://github.com/ygdrasil-oss/webgpu-ktypes.git
cd webgpu-ktypes

# Or SSH
git clone git@github.com:ygdrasil-oss/webgpu-ktypes.git
cd webgpu-ktypes
```

### 2. Configure Java Version

The project requires **Java 25** for JVM targets:

```bash
# Check current Java version
java -version

# If using SDKMAN!
sdk install java 25.0.0-tem
sdk use java 25.0.0-tem

# Set JAVA_HOME
export JAVA_HOME=$(/usr/libexec/java_home -v 25)
```

**Note:** Android targets require Java 17. The build system handles this automatically.

### 3. Verify Gradle Wrapper

```bash
# Check Gradle wrapper version
./gradlew --version

# Expected output should show Gradle 9.5.0
```

If the wrapper is missing or outdated, run:
```bash
gradle wrapper --gradle-version 9.5.0
```

---

## Build Commands

### Full Build

Builds all modules for all platforms:

```bash
./gradlew build
```

**Duration:** ~10-30 minutes (depending on machine and cached dependencies)

### Incremental Build

Builds only changed modules:

```bash
./gradlew build -x check
```

### Build for Specific Module

```bash
# Build only core module
./gradlew :webgpu-ktypes:build

# Build only WGSL parser
./gradlew :wgsl:parser:build

# Build all WGSL modules
./gradlew :wgsl:build
```

### Build with Tests

```bash
# Run all tests
./gradlew test

# Run tests for specific module
./gradlew :webgpu-ktypes:test

# Run checks (build + tests + lint)
./gradlew check
```

### Clean Build

Removes all build outputs and rebuilds:

```bash
./gradlew clean build
```

### Specific Platform Targets

```bash
# JVM only
./gradlew jvmBinaries

# JS only
./gradlew jsBinaries

# Native (macOS)
./gradlew macosArm64Binaries

# Native (Linux)
./gradlew linuxX64Binaries

# All Native targets
./gradlew nativeBinaries
```

---

## Run Commands

### Run Tests

```bash
# All tests
./gradlew test

# Specific test class
./gradlew :webgpu-ktypes:test --tests "io.ygdrasil.webgpu.ArrayBufferTest"

# Tests with coverage
./gradlew testWithCoverage
```

### Run Gradle Daemon

For faster subsequent builds:

```bash
# Start daemon (automatic on first run)
./gradlew --daemon build

# Stop daemon
./gradlew --stop
```

### IDE Integration

#### IntelliJ IDEA

1. Open the project directory in IntelliJ
2. Wait for Gradle synchronization to complete
3. Ensure Kotlin and Gradle plugins are installed
4. Select Java 25 SDK in Project Structure settings

#### Android Studio

1. Open the project
2. Ensure Gradle JDK is set to Java 25
3. Android Studio will automatically configure Kotlin Multiplatform

---

## Testing Strategy

### Test Framework: Kotest

The project uses [Kotest](https://kotest.io/) for testing:

- **Style:** FunSpec (Behavior-Driven Development)
- **Assertions:** Matcher-based (`shouldBe`, `shouldThrow`, etc.)
- **Concurrency:** Tests run in parallel by default

### Test Structure

```
┌─────────────────────────────────────────────────────────────┐
│                      Test Hierarchy                             │
├─────────────────────────────────────────────────────────────┤
│  commonTest/                          # Platform-independent tests │
│  ├── ArrayBufferTest.kt               # Core ArrayBuffer tests │
│  └── ...                              # Other common tests      │
│                                                                  │
│  jvmTest/                             # JVM-specific tests       │
│  └── JvmArrayBufferTest.kt           # JVM MemorySegment tests │
│                                                                  │
│  nativeTest/                          # Native-specific tests    │
│  └── OpaquePointerArrayBufferTest.kt # C interop tests         │
│                                                                  │
│  jsTest/ (in webgpu-ktypes-web)       # JS-specific tests       │
│  └── JsArrayInteropTest.js.kt         # JS interop tests        │
│                                                                  │
│  wasmJsTest/ (in webgpu-ktypes-web)  # Wasm-specific tests     │
│  └── JsArrayInteropTest.wasmJs.kt    # Wasm interop tests      │
└─────────────────────────────────────────────────────────────┘
```

### Test Coverage

| Module | Test File | Coverage | Tests |
|--------|-----------|----------|-------|
| webgpu-ktypes (common) | ArrayBufferTest.kt | 100% | 27 test cases |
| webgpu-ktypes (JVM) | JvmArrayBufferTest.kt | High | 13 test cases |
| webgpu-ktypes (Native) | OpaquePointerArrayBufferTest.kt | High | 11 test cases |
| webgpu-ktypes-web (JS) | JsArrayInteropTest.* | Medium | Multiple files |

### Running Tests for Specific Platform

```bash
# JVM tests only
./gradlew jvmTest

# Native tests only (macOS)
./gradlew macosArm64Test

# JS tests only
./gradlew jsTest
```

---

## Development Workflow

### 1. Create a New Feature Branch

```bash
# From master
git checkout master
git pull origin master

# Create feature branch
git checkout -b feature/your-feature-name

# Or for a bug fix
git checkout -b fix/your-bug-description
```

**Branch Naming Convention:**
- `feature/*` - New features
- `fix/*` - Bug fixes
- `release/*` - Release preparation
- `hotfix/*` - Critical production fixes
- `docs/*` - Documentation updates
- `refactor/*` - Code refactoring
- `test/*` - Test-related changes

### 2. Make Changes

- Follow existing code style and patterns
- Add appropriate tests for new functionality
- Update documentation if applicable

### 3. Run Checks

```bash
# Build and run all tests
./gradlew check

# Or individually
./gradlew build
./gradlew test
```

### 4. Commit Changes

```bash
# Stage changes
git add .

# Commit with descriptive message
git commit -m "feat: add new ArrayBuffer functionality"

# Push to remote
git push origin feature/your-feature-name
```

**Commit Message Convention:** [Conventional Commits](https://www.conventionalcommits.org/)
- `feat:` - New feature
- `fix:` - Bug fix
- `docs:` - Documentation changes
- `style:` - Code style changes (formatting, missing semicolons, etc.)
- `refactor:` - Code refactoring (no functional changes)
- `perf:` - Performance improvements
- `test:` - Test-related changes
- `build:` - Build system or dependency changes
- `ci:` - CI/CD configuration changes
- `chore:` - Other changes that don't modify src or test files

### 5. Create Pull Request

1. Go to GitHub repository
2. Create new Pull Request from your branch
3. Fill in PR template (if available)
4. Add reviewers
5. Link to relevant issues

### 6. Address Review Comments

- Respond to all review comments
- Make requested changes
- Push updates to the same branch
- Request re-review when ready

### 7. Merge

- Requires approval from at least one maintainer
- All CI checks must pass
- Squash merge preferred for clean history

---

## Build Configuration

### Version Management

The project version is managed via environment variable:

```kotlin
// In build.gradle.kts
version = System.getenv("VERSION")?.takeIf { it.isNotBlank() } ?: "0.0.9-SNAPSHOT"
```

**Setting Version:**
```bash
# For snapshot builds (default)
./gradlew build

# For release builds
export VERSION=0.0.10
./gradlew build
```

### Dependency Management

Uses Gradle Version Catalogs:

```kotlin
// settings.gradle.kts
dependencyResolutionManagement {
    versionCatalogs {
        create("kotlinWrappers") {
            val wrappersVersion = "2025.12.6"
            from("org.jetbrains.kotlin-wrappers:kotlin-wrappers-catalog:$wrappersVersion")
        }
    }
}
```

**Dependencies:**
- Kotlin Multiplatform
- Kotlin Serialization
- Android Library
- Kotest (testing)
- KSP (Kotlin Symbol Processing)
- Custom generator plugin

---

## Troubleshooting

### Common Issues

#### Java Version Mismatch

**Symptom:** Build fails with "Unsupported class file major version"

**Solution:**
```bash
# Check Java version
java -version

# Install Java 25
sdk install java 25.0.0-tem
sdk use java 25.0.0-tem

# Or set JAVA_HOME explicitly
export JAVA_HOME=/path/to/jdk-25
```

#### Gradle Daemon Issues

**Symptom:** Gradle commands hang or fail

**Solution:**
```bash
# Stop all daemon instances
./gradlew --stop

# Clean Gradle cache
rm -rf ~/.gradle/caches/

# Retry the build
./gradlew build --no-daemon
```

#### Native Target Build Failures (macOS)

**Symptom:** "No toolchain found for macosArm64"

**Solution:**
```bash
# Install Xcode Command Line Tools
xcode-select --install

# Accept license
sudo xcodebuild -license accept
```

#### Native Target Build Failures (Linux)

**Symptom:** Linker errors for native targets

**Solution:**
```bash
# Install required tools
sudo apt-get update
sudo apt-get install -y g++ make cmake glslang-tools spirv-tools
```

#### Memory Issues

**Symptom:** Out of memory errors

**Solution:**
```bash
# Increase Gradle heap size
./gradlew build -Dorg.gradle.jvmargs="-Xmx4g -XX:MaxMetaspaceSize=1g"

# Or set in gradle.properties
echo "org.gradle.jvmargs=-Xmx4g -XX:MaxMetaspaceSize=1g" >> gradle.properties
```

---

## IDE-Specific Tips

### IntelliJ IDEA

1. **Enable Kotlin Multiplatform Support:**
   - Settings > Plugins > Kotlin Multiplatform
   - Settings > Build, Execution, Deployment > Compiler > Kotlin > Target platform: JVM + JS + Native

2. **Configure JDK:**
   - File > Project Structure > SDK: Java 25

3. **Run Configurations:**
   - Use Gradle tasks to run/build
   - Create Kotest run configurations for specific tests

4. **Code Style:**
   - Use project's .editorconfig
   - Enable Kotlin style checks in inspections

### Android Studio

1. **Switch to Project View:**
   - View > Tool Windows > Project

2. **Gradle Sync:**
   - Click "Sync Project with Gradle Files" if prompted

3. **Run on Device:**
   - For Android-specific testing

---

## Performance Tips

### Faster Builds

```bash
# Use build cache
./gradlew build --build-cache

# Use configuration cache
./gradlew build --configuration-cache

# Parallel builds
export GRADLE_OPTS="-Dorg.gradle.parallel=true"

# Incremental builds (default)
./gradlew build
```

### Gradle Properties

Add to `gradle.properties`:

```properties
# Enable parallel execution
org.gradle.parallel=true

# Enable configuration caching
org.gradle.configuration-cache=true

# Enable build caching
org.gradle.caching=true

# Increase heap size
org.gradle.jvmargs=-Xmx4g -XX:MaxMetaspaceSize=1g

# Use more daemon instances
org.gradle.workers.max=4

# Enable daemon
org.gradle.daemon=true
```

---

## Module-Specific Development

### Working on webgpu-ktypes

The core module contains WebGPU bindings:

```bash
# Navigate to module
cd webgpu-ktypes

# Build just this module
../gradlew :webgpu-ktypes:build

# Run tests
../gradlew :webgpu-ktypes:test
```

**Source Layout:**
```
webgpu-ktypes/src/
├── commonMain/      # Shared code
├── jvmMain/        # JVM-specific
├── jsMain/         # JS-specific
├── nativeMain/     # Native-specific
├── wasmJsMain/     # Wasm-specific
├── androidMain/    # Android-specific
└── commonTest/     # Shared tests
```

### Working on wgsl

The WGSL module contains shader language processing:

```bash
# Navigate to module
cd wgsl

# Build all WGSL submodules
../gradlew :wgsl:build

# Build specific submodule
../gradlew :wgsl:parser:build
```

**Submodules:**
- `wgsl/core` - Core WGSL processing
- `wgsl/parser` - WGSL parser
- `wgsl/generator` - Code generation
- `wgsl/cli` - Command-line interface
- `wgsl/tests` - WGSL-specific tests

---

## Contributing

See [CONTRIBUTING.md](CONTRIBUTING.md) for contribution guidelines.

*Currently, this file does not exist. Consider creating it based on this guide.*

---

## Useful Commands Reference

| Task | Command |
|------|---------|
| Full build | `./gradlew build` |
| Run tests | `./gradlew test` |
| Run checks | `./gradlew check` |
| Clean | `./gradlew clean` |
| Clean all | `./gradlew cleanAll` |
| Dependencies | `./gradlew dependencies` |
| Project list | `./gradlew projects` |
| Task list | `./gradlew tasks` |
| Stop daemon | `./gradlew --stop` |
| Version info | `./gradlew --version` |

---

## References

- [Kotlin Multiplatform Documentation](https://kotlinlang.org/docs/multiplatform-mobile-convert-project.html)
- [Gradle User Manual](https://docs.gradle.org/current/userguide/userguide.html)
- [Kotest Documentation](https://kotest.io/docs/intro.html)
- [WebGPU Specification](https://gpuweb.gpuinfo.org/)

---

*Generated by BMAD document-project workflow - Step 6*  
*Date: 2026-05-19T20:44:00Z*
