# Golden Outputs Currently Missing

This file documents WGSL golden inputs that do not yet have expected outputs in any backend directory.
The list is intentionally consumed by `GoldenCompletenessTest` so missing outputs stay explicit until they
are either generated and reviewed or moved to a narrower unsupported-feature manifest.

Current status: 43 inputs are missing expected outputs for `wgsl`, `glsl`, `hlsl`, `msl`, and `ir`.

- `abstract-types-function-calls.wgsl` - pending parser/resolver/lowering support.
- `abstract-types-return.wgsl` - pending parser/resolver/lowering support.
- `abstract-types-var.wgsl` - pending parser/resolver/lowering support.
- `aliased-ray-query.wgsl` - pending parser/resolver/lowering support.
- `atomicCompareExchange.wgsl` - pending parser/resolver/lowering support.
- `atomicCompareExchange-int64.wgsl` - pending parser/resolver/lowering support.
- `atomicOps-int64.wgsl` - pending parser/resolver/lowering support.
- `atomicOps-int64-min-max.wgsl` - pending parser/resolver/lowering support.
- `atomicTexture.wgsl` - pending parser/resolver/lowering support.
- `atomicTexture-int64.wgsl` - pending parser/resolver/lowering support.
- `binding-arrays.wgsl` - pending parser/resolver/lowering support.
- `binding-buffer-arrays.wgsl` - pending parser/resolver/lowering support.
- `bits.wgsl` - pending parser/resolver/lowering support.
- `bits-optimized-msl.wgsl` - pending parser/resolver/lowering support.
- `break-if.wgsl` - pending parser/resolver/lowering support.
- `const-exprs.wgsl` - pending parser/resolver/lowering support.
- `control-flow.wgsl` - pending parser/resolver/lowering support.
- `conversion-float-to-int.wgsl` - pending parser/resolver/lowering support.
- `conversion-float-to-int-no-f64.wgsl` - pending parser/resolver/lowering support.
- `cooperative-matrix.wgsl` - pending parser/resolver/lowering support.
- `debug-symbol-large-source.wgsl` - pending parser/resolver/lowering support.
- `debug-symbol-terrain.wgsl` - pending parser/resolver/lowering support.
- `draw-index.wgsl` - pending parser/resolver/lowering support.
- `functions.wgsl` - pending parser/resolver/lowering support.
- `functions-optimized-by-capability.wgsl` - pending parser/resolver/lowering support.
- `functions-optimized-by-version.wgsl` - pending parser/resolver/lowering support.
- `functions-unoptimized.wgsl` - pending parser/resolver/lowering support.
- `image.wgsl` - pending parser/resolver/lowering support.
- `int64.wgsl` - pending parser/resolver/lowering support.
- `interpolate.wgsl` - pending parser/resolver/lowering support.
- `interpolate_compat.wgsl` - pending parser/resolver/lowering support.
- `local-const.wgsl` - pending parser/resolver/lowering support.
- `math-functions.wgsl` - pending parser/resolver/lowering support.
- `mesh-shader.wgsl` - pending parser/resolver/lowering support.
- `mesh-shader-empty.wgsl` - pending parser/resolver/lowering support.
- `mesh-shader-lines.wgsl` - pending parser/resolver/lowering support.
- `mesh-shader-points.wgsl` - pending parser/resolver/lowering support.
- `operators.wgsl` - pending parser/resolver/lowering support.
- `quad.wgsl` - pending parser/resolver/lowering support.
- `ray-query.wgsl` - pending parser/resolver/lowering support.
- `ray-query-no-init-tracking.wgsl` - pending parser/resolver/lowering support.
- `type-inference.wgsl` - pending parser/resolver/lowering support.
- `workgroup-uniform-load-atomic.wgsl` - parser/resolver/lowering support now passes; golden output needs review and generation.
