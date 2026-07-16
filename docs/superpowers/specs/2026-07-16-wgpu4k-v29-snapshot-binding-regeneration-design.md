# WGPU4K v29 Snapshot Binding Regeneration Design

## Objective

Regenerate the WebGPU Kotlin bindings from the `webgpu.yml` embedded in
`io.ygdrasil:wgpu4k-native-specs-jvm:v29.0.0-SNAPSHOT`, published by the
`wgpu4k/wgpu4k-native` snapshot workflow run `29485438379` from commit
`d3da8969f7968e5f6ab5d7aebd38cf10c0f0e136`.

The repository must retain the snapshot dependency and repository configuration
so another checkout can run the same generation workflow without relying on a
developer's Maven local repository.

## Dependency Resolution

Update the `wgpu4k-native` version in `gradle/libs.versions.toml` from
`v25.0.2` to `v29.0.0-SNAPSHOT`.

Add `https://central.sonatype.com/repository/maven-snapshots/` to the
repositories used by `buildSrc`, where `wgpu4k-native-specs-jvm` is consumed.
Restrict this repository to snapshots from the `io.ygdrasil` group. Existing
release dependencies continue to resolve from the existing repositories.

Use Gradle dependency refresh during the first generation so the locally cached
`v25.0.2` artifact cannot mask the newly published snapshot. The snapshot
observed during design is Maven build `v29.0.0-20260716.085936-2`.

## Generation Flow

Run the existing `generate-binding` Gradle task. The generator continues to load
`webgpu.yml` from the `wgpu4k-native-specs-jvm` classpath dependency and combines
it with the repository's cached WebGPU IDL and documentation. Generated Kotlin
sources remain owned exclusively by the existing generator; no generated source
is edited manually.

Expected generated areas are:

- `webgpu-ktypes/src/commonMain/kotlin`
- `webgpu-ktypes/src/webMain/kotlin`
- `webgpu-ktypes/src/commonNativeMain/kotlin`
- `webgpu-ktypes-descriptors/src/commonMain/kotlin`
- `webgpu-ktypes-web/src/commonMain/kotlin`

## Diff Analysis

Compare the regenerated tree with the current `v25.0.2` output and report API
changes by category:

- added, removed, reordered, or renumbered enums and bitflags;
- changed descriptor properties and defaults;
- changed interfaces, methods, parameters, and return types;
- typealias and Web interop changes;
- documentation-only changes;
- generator warnings caused by YAML/IDL mismatches.

The report must distinguish source-breaking API changes from generated text or
documentation churn.

## Failure Handling

If Gradle cannot resolve the snapshot, verify the Sonatype snapshot metadata and
repository content filter before changing generator code. If generation fails
because the v29 YAML schema is incompatible with `YamlModel`, make the smallest
schema-mapping change required and include it explicitly in the diff report. If
the YAML and cached IDL disagree, preserve the generator warning or failure and
report the mismatch instead of inventing platform values.

## Verification

Verification consists of:

1. resolving the v29 snapshot with refreshed dependencies;
2. completing `generate-binding` successfully;
3. running generation a second time and confirming it produces no additional
   diff;
4. compiling and testing the affected Gradle modules;
5. reviewing `git diff`, generated API declarations, and generator warnings;
6. confirming that only the approved dependency configuration, any strictly
   necessary generator compatibility change, and regenerated outputs changed.

No publication, push, or pull request is part of this work.
