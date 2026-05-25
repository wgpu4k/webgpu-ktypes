# Golden Outputs Currently Missing

This file documents WGSL golden inputs that do not yet have expected outputs in any backend directory.
The list is intentionally consumed by `GoldenCompletenessTest` so missing outputs stay explicit until they
are either generated and reviewed or moved to a narrower unsupported-feature manifest.

Current status: 12 inputs are missing expected outputs for `wgsl`, `glsl`, `hlsl`, `msl`, and `ir`.

- `aliased-ray-query.wgsl` - pending parser/resolver/lowering support.
- `binding-arrays.wgsl` - pending parser/resolver/lowering support.
- `binding-buffer-arrays.wgsl` - pending parser/resolver/lowering support.
- `cooperative-matrix.wgsl` - pending parser/resolver/lowering support.
- `debug-symbol-large-source.wgsl` - pending parser/resolver/lowering support.
- `debug-symbol-terrain.wgsl` - pending parser/resolver/lowering support.
- `mesh-shader.wgsl` - pending parser/resolver/lowering support.
- `mesh-shader-empty.wgsl` - pending parser/resolver/lowering support.
- `mesh-shader-lines.wgsl` - pending parser/resolver/lowering support.
- `mesh-shader-points.wgsl` - pending parser/resolver/lowering support.
- `ray-query.wgsl` - pending parser/resolver/lowering support.
- `ray-query-no-init-tracking.wgsl` - pending parser/resolver/lowering support.
