# WGPU4K v29 Snapshot Binding Regeneration Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Resolve `wgpu4k-native-specs-jvm:v29.0.0-SNAPSHOT`, regenerate every WebGPU Kotlin binding from its `webgpu.yml`, verify the generated code, and report the API delta from `v25.0.2`.

**Architecture:** Keep the existing classpath-based generator unchanged. Add a narrowly filtered Sonatype snapshot repository to `buildSrc`, update the shared version catalog, then run the existing `generate-binding` task and validate its output across common, JVM, JS, and Wasm source sets.

**Tech Stack:** Gradle Kotlin DSL, Kotlin Multiplatform, KotlinPoet-generated sources, Maven Central Portal snapshots, Git.

## Global Constraints

- Consume `io.ygdrasil:wgpu4k-native-specs-jvm:v29.0.0-SNAPSHOT`.
- Resolve snapshots from `https://central.sonatype.com/repository/maven-snapshots/` only for the `io.ygdrasil` group.
- The snapshot observed during design is Maven build `v29.0.0-20260716.085936-2`.
- Continue loading `webgpu.yml` from the `wgpu4k-native-specs-jvm` classpath dependency.
- Do not edit generated Kotlin sources manually.
- Preserve generator warnings for YAML/IDL mismatches and include them in the final report.
- Do not publish, push, or open a pull request.
- Prefix every shell command with `rtk`.

---

### Task 1: Resolve the v29 Snapshot Reproducibly

**Files:**
- Modify: `buildSrc/build.gradle.kts:7-11`
- Modify: `gradle/libs.versions.toml:6`
- Test: Gradle `buildSrc` runtime classpath resolution; no new test source file

**Interfaces:**
- Consumes: Maven snapshot coordinate `io.ygdrasil:wgpu4k-native-specs-jvm:v29.0.0-SNAPSHOT`
- Produces: `libs.wgpu.specs` resolved from the Sonatype snapshot repository for the generator runtime classpath

- [ ] **Step 1: Confirm the repository still points at the release artifact**

Run:

```bash
rtk rg -n 'wgpu4k-native =|maven-snapshots' gradle/libs.versions.toml buildSrc/build.gradle.kts
```

Expected: `wgpu4k-native = "v25.0.2"` is present and no `maven-snapshots` repository is present.

- [ ] **Step 2: Confirm the requested snapshot metadata exists**

Run:

```bash
rtk curl -fsSL https://central.sonatype.com/repository/maven-snapshots/io/ygdrasil/wgpu4k-native-specs-jvm/v29.0.0-SNAPSHOT/maven-metadata.xml | rtk rg 'v29.0.0-20260716.085936-2|<lastUpdated>20260716085936</lastUpdated>'
```

Expected: the output contains build `v29.0.0-20260716.085936-2` and its `lastUpdated` value.

- [ ] **Step 3: Add the filtered snapshot repository and update the version**

Apply this repository block inside the existing `repositories` block in `buildSrc/build.gradle.kts`, after `mavenCentral()`:

```kotlin
maven {
    name = "sonatypeSnapshots"
    url = uri("https://central.sonatype.com/repository/maven-snapshots/")
    mavenContent {
        snapshotsOnly()
    }
    content {
        includeGroup("io.ygdrasil")
    }
}
```

Change the version catalog entry in `gradle/libs.versions.toml` to:

```toml
wgpu4k-native = "v29.0.0-SNAPSHOT"
```

- [ ] **Step 4: Verify Gradle resolves the v29 generator dependency**

Run:

```bash
rtk ./gradlew -p buildSrc dependencyInsight --dependency wgpu4k-native-specs-jvm --configuration runtimeClasspath --refresh-dependencies --console=plain
```

Expected: exit code `0`, with `io.ygdrasil:wgpu4k-native-specs-jvm:v29.0.0-SNAPSHOT` selected in `runtimeClasspath`.

- [ ] **Step 5: Validate and commit the dependency configuration**

Run:

```bash
rtk git diff --check
rtk git diff -- buildSrc/build.gradle.kts gradle/libs.versions.toml
rtk git add buildSrc/build.gradle.kts gradle/libs.versions.toml
rtk git commit -m "build: use wgpu4k v29 snapshot specs"
```

Expected: the diff contains only the filtered repository and version update, and the commit succeeds.

### Task 2: Regenerate and Compile the Bindings

**Files:**
- Modify generated: `webgpu-ktypes/src/commonMain/kotlin/bitflags.kt`
- Modify generated: `webgpu-ktypes/src/commonMain/kotlin/enumerations.kt`
- Modify generated: `webgpu-ktypes/src/commonMain/kotlin/typealiases.kt`
- Modify generated: `webgpu-ktypes/src/commonMain/kotlin/interfaces.kt`
- Modify generated: `webgpu-ktypes/src/webMain/kotlin/enumerations.kt`
- Modify generated: `webgpu-ktypes/src/commonNativeMain/kotlin/enumerations.kt`
- Modify generated: `webgpu-ktypes-descriptors/src/commonMain/kotlin/descriptor.kt`
- Modify generated: `webgpu-ktypes-web/src/commonMain/kotlin/types.kt`
- Test: Gradle generation and Kotlin compilation tasks; no new test source file

**Interfaces:**
- Consumes: `libs.wgpu.specs` resolved by Task 1 and the existing `generate-binding` task
- Produces: regenerated Kotlin declarations for common, Web, Native, descriptor, and Web interop source sets

- [ ] **Step 1: Run the existing generator with refreshed dependencies**

Run:

```bash
rtk ./gradlew --refresh-dependencies generate-binding --console=plain
```

Expected: exit code `0` and `BUILD SUCCESSFUL`. Preserve the complete list of `Skipping native enumeration` and `Enum from Idl not found` messages for the final report.

- [ ] **Step 2: Confirm that changes are limited to generated outputs**

Run:

```bash
rtk git status --short
rtk git diff --stat
rtk git diff --check
```

Expected: only the eight generated files listed for this task are changed, with no whitespace errors.

- [ ] **Step 3: Compile every source-set family affected by generation**

Run:

```bash
rtk ./gradlew :webgpu-ktypes:compileKotlinMetadata :webgpu-ktypes:compileKotlinJvm :webgpu-ktypes:compileKotlinJs :webgpu-ktypes:compileKotlinWasmJs :webgpu-ktypes-descriptors:compileKotlinMetadata :webgpu-ktypes-descriptors:compileKotlinJvm :webgpu-ktypes-descriptors:compileKotlinJs :webgpu-ktypes-descriptors:compileKotlinWasmJs :webgpu-ktypes-web:compileKotlinMetadata :webgpu-ktypes-web:compileKotlinJs :webgpu-ktypes-web:compileKotlinWasmJs --console=plain
```

Expected: exit code `0` and `BUILD SUCCESSFUL`, with warnings treated according to each module's existing compiler configuration.

- [ ] **Step 4: Review and commit the generated sources**

Run:

```bash
rtk git diff --check
rtk git diff --stat
rtk git add webgpu-ktypes/src/commonMain/kotlin/bitflags.kt webgpu-ktypes/src/commonMain/kotlin/enumerations.kt webgpu-ktypes/src/commonMain/kotlin/typealiases.kt webgpu-ktypes/src/commonMain/kotlin/interfaces.kt webgpu-ktypes/src/webMain/kotlin/enumerations.kt webgpu-ktypes/src/commonNativeMain/kotlin/enumerations.kt webgpu-ktypes-descriptors/src/commonMain/kotlin/descriptor.kt webgpu-ktypes-web/src/commonMain/kotlin/types.kt
rtk git commit -m "chore: regenerate bindings from wgpu4k v29 snapshot"
```

Expected: the commit contains generated files only and succeeds after the compilation evidence from Step 3.

### Task 3: Verify Idempotence, Run Checks, and Classify the API Delta

**Files:**
- Inspect: all files committed by Tasks 1 and 2
- Test: second generation, full Gradle build, Git diff and status

**Interfaces:**
- Consumes: committed v29 dependency configuration and generated declarations
- Produces: a clean, repeatable working tree plus a user-facing categorized change report

- [ ] **Step 1: Prove generation is idempotent**

Run:

```bash
rtk ./gradlew generate-binding --console=plain
rtk git diff --exit-code
```

Expected: `BUILD SUCCESSFUL`, followed by exit code `0` from `git diff`, proving the second generation made no change.

- [ ] **Step 2: Run the full repository build**

Run:

```bash
rtk ./gradlew build --console=plain
```

Expected: exit code `0` and `BUILD SUCCESSFUL`, with all configured module checks and tests completed.

- [ ] **Step 3: Extract the complete change surface since the approved design**

Run:

```bash
rtk git diff --stat e2385dd..HEAD
rtk git diff --unified=0 e2385dd..HEAD -- buildSrc/build.gradle.kts gradle/libs.versions.toml webgpu-ktypes/src/commonMain/kotlin/bitflags.kt webgpu-ktypes/src/commonMain/kotlin/enumerations.kt webgpu-ktypes/src/commonMain/kotlin/typealiases.kt webgpu-ktypes/src/commonMain/kotlin/interfaces.kt webgpu-ktypes/src/webMain/kotlin/enumerations.kt webgpu-ktypes/src/commonNativeMain/kotlin/enumerations.kt webgpu-ktypes-descriptors/src/commonMain/kotlin/descriptor.kt webgpu-ktypes-web/src/commonMain/kotlin/types.kt
```

Expected: a complete diff containing the dependency update and all generated API changes relative to the design commit.

- [ ] **Step 4: Classify the changes for the final response**

From the Step 3 diff, enumerate exact declarations under these headings:

```text
Source-breaking changes
Additive API changes
Enum and bitflag value changes
Descriptor default and type changes
Web and Native representation changes
Documentation-only changes
YAML/IDL mismatch warnings
```

For every source-breaking change, name the affected Kotlin declaration and show its old and new signature or value. Do not count ordering or KDoc-only churn as source-breaking.

- [ ] **Step 5: Confirm final repository state**

Run:

```bash
rtk git status --short
rtk git log -4 --oneline
```

Expected: an empty status and the plan, design, dependency configuration, and generated-binding commits visible in the recent history.
