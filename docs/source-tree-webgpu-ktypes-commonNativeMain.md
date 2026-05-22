# webgpu-ktypes - commonNativeMain Source Tree Analysis

**Date:** 2026-05-19
**Batch:** webgpu-ktypes/src/commonNativeMain/kotlin/
**Scan Level:** Exhaustive
**Files:** 1
**Total Lines:** 2,401

## Overview

This directory contains **platform-specific native implementations** for WebGPU enumeration classes. These are the `actual` declarations that provide concrete implementations for the `expect` declarations in `commonMain`.

## Directory Structure

```
webgpu-ktypes/src/commonNativeMain/kotlin/
└── enumerations.kt    (2,401 lines) - Native enum implementations
```

## Critical Files

### `enumerations.kt`

**Purpose:** Native platform implementations of WebGPU enumeration classes.

**Contains:**
- `actual enum class GPUAddressMode(val value: UInt)` - Texture addressing modes with numeric values
- `actual enum class GPUBlendFactor(val value: UInt)` - Blend factors with numeric values
- And all other WebGPU enums in their native form...

**Pattern:** Each enum value has an explicit `UInt` value assigned

**Example:**
```kotlin
actual enum class GPUAddressMode(val value: UInt) {
    ClampToEdge(1u),
    Repeat(2u),
    MirrorRepeat(3u);
    
    companion object {
        fun of(value: UInt): GPUAddressMode? {
            return entries.find { it.value == value }
        }
    }
}
```

**Note:** File is generated (DO NO EDIT)

## Code Characteristics

- **Package:** `io.ygdrasil.webgpu`
- **Language:** Kotlin (Native)
- **Generation:** 100% auto-generated from WebGPU specification
- **Platform:** Native-specific (actual declarations)
- **Pattern:** Mirrors `commonMain/enumerations.kt` but with `actual` keyword and numeric values

## Relationship to commonMain

This file provides the **native implementation** for the `expect` enum declarations in:
- `webgpu-ktypes/src/commonMain/kotlin/enumerations.kt`

The Kotlin Multiplatform compiler uses these `actual` declarations when compiling for native targets.

## Notes for Development

1. **Generated File:** Do not edit - regenerated from WebGPU specification
2. **Multiplatform:** Provides actual implementations for native platforms
3. **Numeric Values:** Each enum has explicit UInt values for native interop
4. **Companion Objects:** Include lookup functions for value-to-enum conversion

---

_Generated using BMAD Method `document-project` workflow - Batch 2: webgpu-ktypes/src/commonNativeMain/kotlin/_
