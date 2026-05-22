# WebGPU Kotlin Toolkit - Project Overview

**Date:** 2026-05-19  
**Document Type:** Project Overview  
**Repository Type:** Monorepo  
**Language:** English  

---

## Executive Summary

The **WebGPU Kotlin Toolkit** is a comprehensive, type-safe Kotlin implementation of the WebGPU API specification, providing multiplatform bindings and utilities for WebGPU development across 19+ target platforms. This monorepo contains five interdependent modules that together form a complete ecosystem for WebGPU and WGSL (WebGPU Shading Language) development in Kotlin.

### Project Purpose

The toolkit addresses the need for **type-safe, idiomatic Kotlin bindings** to the WebGPU API, which is the modern standard for graphics programming on the web and native platforms. By leveraging Kotlin Multiplatform, it provides a **single codebase** that compiles to:

- **JVM** (Java 25) - Desktop and server applications
- **JS/Node.js** - Browser and Node.js applications
- **Native** - iOS, macOS, Linux, Windows, Android Native, watchOS
- **WebAssembly (WasmJs)** - Web-based applications with near-native performance

### Key Achievements

| Metric | Value |
|--------|-------|
| **Platform Targets** | 19+ |
| **Generated Code** | ~67% of core interfaces (from W3C spec) |
| **Test Coverage** | 100% for ArrayBuffer (27 test cases) |
| **Total Lines of Code** | 20,000+ (estimated) |
| **Modules** | 5 core + 5 WGSL submodules |
| **Primary Language** | Kotlin 2.0+ |

---

## Project Name and Classification

### Project Identity

| Attribute | Value |
|-----------|-------|
| **Name** | WebGPU Kotlin Toolkit |
| **Repository** | webgpu-ktypes |
| **Organization** | ygdrasil-oss |
| **Primary Language** | Kotlin |
| **Build System** | Gradle (KTS DSL) |
| **Repository Type** | Monorepo |

### Classification

- **Repository Structure:** Monorepo with hierarchical dependencies
- **Architecture Pattern:** Modular Multiplatform Library
- **Project Type:** Library (all 5 parts classified as libraries)
- **Domain:** Graphics Programming / WebGPU Bindings

---

## Technology Stack Summary

### Core Technologies

| Technology | Version | Purpose | Coverage |
|------------|---------|---------|----------|
| **Kotlin** | 2.0+ | Primary programming language | All modules |
| **Kotlin Multiplatform** | Latest | Multiplatform compilation | All modules |
| **Gradle** | 9.5.0 | Build system (KTS DSL) | All modules |
| **Java Toolchain** | 25 | JVM compilation | JVM targets |
| **Java Toolchain (Android)** | 17 | Android compilation | Android targets |
| **Kotest** | Latest | Test framework | All modules with tests |

### Platform-Specific Technologies

| Platform | Technology | Implementation | Lines |
|----------|------------|----------------|-------|
| **JVM** | Java 21+ Foreign Function & Memory API | MemorySegment-based | 490 |
| **JS/Node.js** | Kotlin/JS Typed Arrays | Int8Array, Int16Array, etc. | 284 |
| **WasmJs** | Kotlin/Wasm | JavaScript typed arrays | 332 |
| **Native** | Kotlin/Native C Interop | COpaquePointer | 631+ |
| **Android** | Java NIO ByteBuffer | Direct buffers with views | 437 |

### Build Plugins

```kotlin
// Shared across all modules
plugins {
    kotlin("multiplatform")       // Multiplatform support
    `android.library`             // Android library
    `kotest`                      // Testing framework
    `ksp`                         // Kotlin Symbol Processing
    `publish`                     // Artifact publishing
    `generator`                   // Code generation
    `kotlinx-serialization`       // Data serialization
    `kotlin-parcelize`            // Parcelize support
}
```

---

## Architecture Type Classification

### Monorepo Structure

The WebGPU Kotlin Toolkit follows a **Modular Monorepo** architecture with **hierarchical dependencies**:

```
┌─────────────────────────────────────────────────────────────────────────┐
│                         MONOREPO ARCHITECTURE                               │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  ┌─────────────────────────────────────────────────────────────────────┐ │
│  │                        ROOT PROJECT                                  │ │
│  │                  (webgpu-ktypes-root)                                │ │
│  └───────────────────────────┬────────────────────────────────────────┘ │
│                                  │                                        │
│          ┌───────────────────────▼───────────────────────┐              │
│          │                                               │              │
│  ┌───────▼───────┐                      ┌────────▼────────┐          │
│  │  webgpu-ktypes │                      │       wgsl       │          │
│  │   (CORE)       │                      │  (INDEPENDENT)   │          │
│  └───────┬───────┘                      └────────┬────────┘          │
│          │                                       │                      │
│  ┌───────▼───────┐              ┌─────────────────▼─────────────────┐  │
│  │webgpu-ktypes- │              │  wgsl/core  │ wgsl/parser │    │  │
│  │ descriptors   │              │             │            │    │  │
│  └───────┬───────┘              └─────────────────┬─────────────────┘  │
│          │                                       │                      │
│  ┌───────▼───────┐              ┌─────────────────▼─────────────────┐  │
│  │webgpu-ktypes- │              │  wgsl/generator  │ wgsl/cli │        │  │
│  │ specifications│              │                 │         │        │  │
│  └───────┬───────┘              └─────────────────┬─────────────────┘  │
│          │                                       │                      │
│          ▼                                       ▼                      │
│  ┌─────────────────────────────────────────────────────────────────┐  │
│  │                     webgpu-ktypes-web                            │  │
│  │                  (JS/WasmJs specific interop)                    │  │
│  └─────────────────────────────────────────────────────────────────┘  │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

### Dependency Hierarchy

```
wgsl/core → wgsl/parser → webgpu-ktypes → webgpu-ktypes-descriptors
                                  → webgpu-ktypes-web
                                  → webgpu-ktypes-specifications
```

### Key Architectural Characteristics

1. **Platform-Agnostic Core**: `webgpu-ktypes` contains platform-independent WebGPU API bindings
2. **Platform-Specific Implementations**: Each platform provides `actual` implementations via Kotlin Multiplatform
3. **Sealed Interface Hierarchy**: Type-safe abstractions with sealed interfaces (e.g., `ArrayBuffer`)
4. **Expect/Actual Pattern**: Kotlin Multiplatform's mechanism for platform-specific code
5. **Modular Design**: Clear separation of concerns with 5 distinct modules
6. **Code Generation**: ~67% of core interfaces generated from W3C WebGPU specification

---

## Repository Structure Summary

### Monorepo Organization

```
.
├── _bmad/                          # BMAD workflow configuration
├── .agents/                       # Agent skills and configurations
├── .github/                       # GitHub CI/CD workflows
│   └── workflows/
│       ├── test.yml               # Test pipeline
│       ├── publish.yml            # Publish pipeline
│       ├── snapshot.yml           # Snapshot pipeline
│       └── ...
├── .gradle/                       # Gradle metadata
├── .idea/                         # IntelliJ IDE configuration
├── gradle/                        # Gradle wrapper
│   └── wrapper/
├── docs/                          # Documentation (THIS LOCATION)
│   ├── project-scan-report.json
│   ├── source-tree-analysis.md
│   ├── development-guide.md
│   ├── deployment-guide.md
│   ├── integration-architecture.md
│   ├── architecture-webgpu-ktypes.md
│   ├── architecture-webgpu-ktypes-descriptors.md
│   ├── architecture-webgpu-ktypes-specifications.md
│   ├── architecture-webgpu-ktypes-web.md
│   ├── architecture-wgsl.md
│   ├── project-overview.md        # ← THIS FILE
│   ├── component-inventory-webgpu-ktypes.md
│   ├── contribution-guide.md
│   └── project-parts.json
├── webgpu-ktypes/                 # Core WebGPU bindings
├── webgpu-ktypes-descriptors/     # Descriptor type definitions
├── webgpu-ktypes-specifications/  # Specification types
├── webgpu-ktypes-web/             # Web/JS specific types
├── wgsl/                          # WGSL processor
│   ├── core/
│   ├── parser/
│   ├── generator/
│   ├── cli/
│   └── tests/
├── README.md
├── IR_EXPLORATION_REPORT.md
├── PLAN_TESTS_LOWERING.md
└── TYPE_MAPPING.md
```

### Module Breakdown

| Module | Path | Dependencies | Platforms | Purpose |
|--------|------|--------------|-----------|---------|
| **webgpu-ktypes** | `/webgpu-ktypes` | None | All 19 | Core WebGPU bindings |
| **webgpu-ktypes-descriptors** | `/webgpu-ktypes-descriptors` | webgpu-ktypes | All 19 | Descriptor type definitions |
| **webgpu-ktypes-specifications** | `/webgpu-ktypes-specifications` | webgpu-ktypes | All 19 | Specification types & docs |
| **webgpu-ktypes-web** | `/webgpu-ktypes-web` | webgpu-ktypes | JS, WasmJs | Web/JS specific interop |
| **wgsl** | `/wgsl` | None | All 19 | WGSL parser & processor |

### Platform Target Matrix

| Module | JVM | JS | Native | Android | WasmJs | iOS | macOS | Linux | Windows | watchOS |
|--------|-----|----|--------|---------|-------|-----|-------|-------|---------|--------|
| webgpu-ktypes | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| webgpu-ktypes-descriptors | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| webgpu-ktypes-specifications | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| webgpu-ktypes-web | ❌ | ✅ | ❌ | ❌ | ✅ | ❌ | ❌ | ❌ | ❌ | ❌ |
| wgsl | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |

---

## Links to All Generated Documentation

### Overview & Setup
- [📋 Project Overview](project-overview.md) - *This document*
- [🚀 Getting Started](#getting-started) - *Below in this document*
- [📚 Development Guide](development-guide.md) - Environment setup, build commands, testing
- [📦 Deployment Guide](deployment-guide.md) - CI/CD, artifact publishing

### Architecture
- [🏗️ Integration Architecture](integration-architecture.md) - Module dependencies and communication patterns
- [🏛️ webgpu-ktypes Architecture](architecture-webgpu-ktypes.md) - Core module architecture
- [🏛️ webgpu-ktypes-descriptors Architecture](architecture-webgpu-ktypes-descriptors.md) - Descriptor module
- [🏛️ webgpu-ktypes-specifications Architecture](architecture-webgpu-ktypes-specifications.md) - Specifications module
- [🏛️ webgpu-ktypes-web Architecture](architecture-webgpu-ktypes-web.md) - Web-specific module
- [🏛️ wgsl Architecture](architecture-wgsl.md) - WGSL processor architecture

### Source Analysis
- [🌳 Complete Source Tree Analysis](source-tree-analysis.md) - Master tree with all modules
- [🌳 webgpu-ktypes/commonMain](source-tree-webgpu-ktypes-commonMain.md) - Common platform-agnostic code
- [🌳 webgpu-ktypes/commonNativeMain](source-tree-webgpu-ktypes-commonNativeMain.md) - Native common code
- [🌳 webgpu-ktypes/commonTest](source-tree-webgpu-ktypes-commonTest.md) - Common tests
- [🌳 webgpu-ktypes/jvmMain](source-tree-webgpu-ktypes-jvmMain.md) - JVM platform implementation
- [🌳 webgpu-ktypes/jvmTest](source-tree-webgpu-ktypes-jvmTest.md) - JVM tests
- [🌳 webgpu-ktypes/jsMain](source-tree-webgpu-ktypes-jsMain.md) - JS platform implementation
- [🌳 webgpu-ktypes/webMain](source-tree-webgpu-ktypes-webMain.md) - Web platform implementation
- [🌳 webgpu-ktypes/nativeMain](source-tree-webgpu-ktypes-nativeMain.md) - Native platform implementation
- [🌳 webgpu-ktypes/nativeTest](source-tree-webgpu-ktypes-nativeTest.md) - Native tests
- [🌳 webgpu-ktypes/wasmJsMain](source-tree-webgpu-ktypes-wasmJsMain.md) - WasmJs platform implementation
- [🌳 webgpu-ktypes/androidMain](source-tree-webgpu-ktypes-androidMain.md) - Android platform implementation

### Supporting Documentation (This Step)
- [📦 Component Inventory - webgpu-ktypes](component-inventory-webgpu-ktypes.md) - Detailed component catalog
- [🤝 Contribution Guide](contribution-guide.md) - How to contribute to the project
- [📊 Project Parts Metadata](project-parts.json) - Machine-readable module information

### Additional Resources
- [📄 Root README](/README.md) - Project readme
- [🔍 IR Exploration Report](/IR_EXPLORATION_REPORT.md) - IR analysis findings
- [📝 Test Planning](/PLAN_TESTS_LOWERING.md) - Test strategy
- [🎯 Type Mapping](/TYPE_MAPPING.md) - WebGPU type mappings
- [📋 Project Scan Report](project-scan-report.json) - Workflow state

---

## Getting Started

### Prerequisites

Before you begin, ensure you have the following installed:

#### Required Tools

| Tool | Version | Purpose | Installation |
|------|---------|---------|-------------|
| Java JDK | 25+ | JVM compilation, Gradle | [Adoptium Temurin](https://adoptium.net/) or [Oracle JDK](https://www.oracle.com/java/technologies/javase-downloads.html) |
| Gradle | 9.5.0 | Build tool | Automatic via `./gradlew` wrapper |
| Git | 2.x | Version control | [git-scm.com](https://git-scm.com/) |

#### Optional Tools

| Tool | Version | Purpose | When Needed |
|------|---------|---------|-------------|
| IntelliJ IDEA | 2024.3+ | IDE | Recommended for development |
| Android Studio | Latest | Android development | Android targets |
| Node.js | 20+ | JavaScript compilation | JS/Wasm targets |

#### Platform-Specific Requirements

**macOS:**
```bash
# Install Xcode Command Line Tools (for Native targets)
xcode-select --install
```

**Linux (Ubuntu):**
```bash
# For Native targets
sudo apt-get update
sudo apt-get install -y glslang-tools spirv-tools

# For Android targets
sudo apt-get install -y openjdk-17-jdk
```

**Windows:**
- Visual Studio 2022 (for Native targets)
- Windows Subsystem for Linux (WSL) recommended

### Quick Start

#### 1. Clone the Repository

```bash
# HTTPS
git clone https://github.com/ygdrasil-oss/webgpu-ktypes.git
cd webgpu-ktypes

# Or SSH
git clone git@github.com:ygdrasil-oss/webgpu-ktypes.git
cd webgpu-ktypes
```

#### 2. Set Up Java Version

The project requires **Java 25** for JVM targets:

```bash
# Check current Java version
java -version

# If using SDKMAN!
sdk install java 25.0.0-tem
sdk use java 25.0.0-tem

# Or set JAVA_HOME explicitly
export JAVA_HOME=$(/usr/libexec/java_home -v 25)
```

**Note:** Android targets require Java 17, which is handled automatically by the build system.

#### 3. Build the Project

```bash
# Full build (all modules, all platforms)
./gradlew build
```

**Build Duration:** ~10-30 minutes (depending on machine and cached dependencies)

#### 4. Run Tests

```bash
# Run all tests
./gradlew test

# Or run tests with checks (build + tests + lint)
./gradlew check
```

#### 5. Verify Setup

```bash
# Check Gradle wrapper version
./gradlew --version

# List all projects
./gradlew projects

# List available tasks
./gradlew tasks
```

### Common Build Commands

| Command | Description |
|---------|-------------|
| `./gradlew build` | Build all modules for all platforms |
| `./gradlew test` | Run all tests |
| `./gradlew check` | Build + tests + lint |
| `./gradlew clean` | Remove all build outputs |
| `./gradlew clean build` | Clean build (full rebuild) |
| `./gradlew :webgpu-ktypes:build` | Build only core module |
| `./gradlew :wgsl:build` | Build all WGSL modules |

### Platform-Specific Builds

```bash
# JVM only
./gradlew jvmBinaries

# JS only
./gradlew jsBinaries

# Native (macOS)
./gradlew macosArm64Binaries

# All Native targets
./gradlew nativeBinaries
```

### Using the Library

#### Gradle Dependency (Snapshot)

```kotlin
// build.gradle.kts
dependencies {
    implementation("io.ygdrasil:webgpu-ktypes:0.0.9-SNAPSHOT")
    implementation("io.ygdrasil:webgpu-ktypes-descriptors:0.0.9-SNAPSHOT")
    implementation("io.ygdrasil:wgsl-parser:0.0.9-SNAPSHOT")
}
```

#### Example: Creating an ArrayBuffer

```kotlin
import io.ygdrasil.webgpu.ArrayBuffer

fun main() {
    // Allocate a new buffer
    val buffer = ArrayBuffer.allocate(1024)
    
    // Write data
    buffer.setInt(0, 42)
    buffer.setFloat(4, 3.14f)
    
    // Read data
    val value: Int = buffer.getInt(0)
    val pi: Float = buffer.getFloat(4)
    
    println("Value: $value, Pi: $pi")
}
```

---

## Project Metadata

### Version Information

- **Current Version:** 0.0.9-SNAPSHOT
- **Version Management:** Set via `VERSION` environment variable or GitHub Release tag
- **Semantic Versioning:** Follows SemVer (MAJOR.MINOR.PATCH)

### Licensing

- **License:** Apache-2.0
- **License File:** LICENSE (if exists)

### Contact & Support

- **GitHub Repository:** https://github.com/ygdrasil-oss/webgpu-ktypes
- **Issues:** https://github.com/ygdrasil-oss/webgpu-ktypes/issues
- **Discussions:** https://github.com/ygdrasil-oss/webgpu-ktypes/discussions

### Related Specifications

- **WebGPU Specification:** https://gpuweb.gpuinfo.org/
- **WGSL Specification:** https://gpuweb.gpuinfo.org/wgsl/
- **Kotlin Multiplatform:** https://kotlinlang.org/docs/multiplatform-mobile-convert-project.html

---

## Document Information

| Attribute | Value |
|-----------|-------|
| **Generated By** | BMAD document-project workflow - Step 9 |
| **Date** | 2026-05-19 |
| **Language** | English |
| **Related Documents** | See [Links to All Generated Documentation](#links-to-all-generated-documentation) |

---

*This document provides a comprehensive overview of the WebGPU Kotlin Toolkit monorepo. For detailed information about specific modules, refer to the linked architecture documents. For contribution guidelines, see the [Contribution Guide](contribution-guide.md).*
