# Golden Expected Failures

This manifest records the currently known failing golden cases while the WGSL test suite is being stabilized.
Each listed case is still executed. The test fails if the case starts passing unexpectedly or fails at a different phase.

| Suite | Input | Phase | Reason | Issue |
|-------|-------|-------|--------|-------|
| `glsl` | `6438-conflicting-idents.wgsl` | `native-validation` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `glsl` | `6772-unpack-expr-accesses.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `7048-multiple-dynamic-1.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `7995-unicode-idents.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `8820-multiple-local-invocation-index-id.wgsl` | `native-validation` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `glsl` | `9105-primitive-index-ordering.wgsl` | `native-validation` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `glsl` | `abstract-types-atomic.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `abstract-types-builtins.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `abstract-types-const.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `abstract-types-function-calls.wgsl` | `native-validation` | Golden output is reviewed, but GLSL native validation rejects current abstract composite argument materialization. | `#16` |
| `glsl` | `abstract-types-let.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `abstract-types-texture.wgsl` | `type-resolution` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `glsl` | `abstract-types-var.wgsl` | `native-validation` | Golden output is reviewed, but GLSL native validation rejects current abstract composite initializer conversions. | `#16` |
| `glsl` | `access.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `aliased-ray-query.wgsl` | `native-validation` | Golden output is reviewed after ray-query builtin registration, but GLSL native validation does not accept the generated ray-query output. | `#16` |
| `glsl` | `array-in-ctor.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `array-in-function-return-type.wgsl` | `native-validation` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `glsl` | `atomicCompareExchange-int64.wgsl` | `native-validation` | Golden output is reviewed after int64 suffix and scalar-width support, but GLSL native validation still rejects the generated int64 output shape. | `#16` |
| `glsl` | `atomicCompareExchange.wgsl` | `native-validation` | Golden output is reviewed, but GLSL native validation rejects the current storage buffer output shape. | `#16` |
| `glsl` | `atomicOps-float32.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `atomicOps-int64-min-max.wgsl` | `native-validation` | Golden output is reviewed after int64 suffix and scalar-width support, but GLSL native validation still rejects the generated int64 output shape. | `#16` |
| `glsl` | `atomicOps-int64.wgsl` | `native-validation` | Golden output is reviewed after int64 suffix and scalar-width support, but GLSL native validation still rejects the generated int64 output shape. | `#16` |
| `glsl` | `atomicOps.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `atomicTexture-int64.wgsl` | `native-validation` | Golden output is reviewed after texture atomic builtin registration, but GLSL native validation still rejects the generated storage texture output shape. | `#16` |
| `glsl` | `atomicTexture.wgsl` | `native-validation` | Golden output is reviewed after texture atomic builtin registration, but GLSL native validation still rejects the generated storage texture output shape. | `#16` |
| `glsl` | `barycentrics.wgsl` | `native-validation` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `glsl` | `binding-arrays.wgsl` | `native-validation` | Golden output is reviewed after binding-array lowering support, but GLSL native validation still rejects the generated binding-array output. | `#16` |
| `glsl` | `binding-buffer-arrays.wgsl` | `native-validation` | Golden output is reviewed after binding-array lowering support, but GLSL native validation still rejects the generated storage binding-array output. | `#16` |
| `glsl` | `bits-optimized-msl.wgsl` | `native-validation` | Golden output is reviewed after firstLeadingBit builtin registration, but GLSL native validation rejects generated pack4x8snorm output. | `#16` |
| `glsl` | `bits.wgsl` | `native-validation` | Golden output is reviewed after firstLeadingBit builtin registration, but GLSL native validation rejects generated bit-packing output. | `#16` |
| `glsl` | `bits_downlevel.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `bits_downlevel_webgl.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `boids.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `bounds-check-dynamic-buffer.wgsl` | `native-validation` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `glsl` | `bounds-check-image-restrict-depth.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `bounds-check-image-restrict.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `bounds-check-image-rzsw-depth.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `bounds-check-image-rzsw.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `bounds-check-restrict.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `bounds-check-zero-atomic.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `bounds-check-zero.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `clip-distances.wgsl` | `native-validation` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `glsl` | `collatz.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `const-exprs.wgsl` | `native-validation` | Golden output is reviewed after packed dot-product builtin and const switch selector support, but GLSL native validation rejects a generated bool-to-vec2<bool> assignment. | `#16` |
| `glsl` | `const_assert.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `constructors.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `control-flow.wgsl` | `native-validation` | Golden output is reviewed after switch trailing-comma parsing and scalar const selector lowering, but GLSL native validation rejects generated storageBarrier output. | `#16` |
| `glsl` | `conversion-float-to-int-no-f64.wgsl` | `native-validation` | Golden output is reviewed, but GLSL native validation rejects generated non-finite float constants as Infinity. | `#16` |
| `glsl` | `conversion-float-to-int.wgsl` | `native-validation` | Golden output is reviewed, but GLSL native validation rejects generated non-finite float constants as Infinity. | `#16` |
| `glsl` | `cooperative-matrix.wgsl` | `type-resolution` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `glsl` | `cross.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `cubeArrayShadow.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `debug-symbol-large-source.wgsl` | `native-validation` | Golden output is reviewed after stage keyword identifier resolution, but GLSL native validation rejects the generated array declaration. | `#16` |
| `glsl` | `debug-symbol-simple.wgsl` | `native-validation` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `glsl` | `debug-symbol-terrain.wgsl` | `native-validation` | Golden output is reviewed after stage keyword identifier resolution, but GLSL native validation rejects the generated array declaration. | `#16` |
| `glsl` | `draw-index.wgsl` | `native-validation` | Golden output is reviewed, but GLSL native validation rejects the current struct builtin entry-point lowering for draw_index. | `#16` |
| `glsl` | `dualsource.wgsl` | `native-validation` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `glsl` | `early-depth-test-conservative.wgsl` | `native-validation` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `glsl` | `empty-if.wgsl` | `native-validation` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `glsl` | `extra.wgsl` | `native-validation` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `glsl` | `f16-native.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `f16-polyfill.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `f16.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `f64.wgsl` | `type-resolution` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `glsl` | `force_point_size_vertex_shader_webgl.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `fragment-output.wgsl` | `native-validation` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `glsl` | `functions-optimized-by-capability.wgsl` | `native-validation` | Golden output is reviewed after packed dot-product builtin support, but GLSL native validation rejects the generated packed dot-product output. | `#16` |
| `glsl` | `functions-optimized-by-version.wgsl` | `native-validation` | Golden output is reviewed after packed dot-product builtin support, but GLSL native validation rejects the generated packed dot-product output. | `#16` |
| `glsl` | `functions-unoptimized.wgsl` | `native-validation` | Golden output is reviewed after packed dot-product builtin support, but GLSL native validation rejects the generated packed dot-product output. | `#16` |
| `glsl` | `functions-webgl.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `functions.wgsl` | `native-validation` | Golden output is reviewed after packed dot-product builtin support, but GLSL native validation rejects the generated packed dot-product output. | `#16` |
| `glsl` | `globals.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `image.wgsl` | `native-validation` | Golden output is reviewed after texture query builtin support, but GLSL native validation still rejects the generated image output. | `#16` |
| `glsl` | `index-by-value.wgsl` | `native-validation` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `glsl` | `int16.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `int64.wgsl` | `native-validation` | Golden output is reviewed after int64 suffix and scalar-width support, but GLSL native validation still rejects the generated int64 output shape. | `#16` |
| `glsl` | `interface.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `interpolate.wgsl` | `native-validation` | Golden output is reviewed after interpolation enumerant resolution, but GLSL native validation rejects the generated vertex output declaration. | `#16` |
| `glsl` | `interpolate_compat.wgsl` | `native-validation` | Golden output is reviewed after interpolation enumerant resolution, but GLSL native validation rejects the generated vertex output declaration. | `#16` |
| `glsl` | `invariant.wgsl` | `native-validation` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `glsl` | `mat2-uniform-alignment.wgsl` | `native-validation` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `glsl` | `mat_cx2.wgsl` | `native-validation` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `glsl` | `mat_cx2_f16.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `mat_cx3.wgsl` | `native-validation` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `glsl` | `math-functions.wgsl` | `native-validation` | Golden output is reviewed after bit-count builtin registration and structured `modf`/`frexp` result lowering, but GLSL native validation rejects the generated math builtin output. | `#16` |
| `glsl` | `memory-decorations-coherent.wgsl` | `native-validation` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `glsl` | `memory-decorations.wgsl` | `native-validation` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `glsl` | `mesh-shader-empty.wgsl` | `native-validation` | Golden output is reviewed after mesh builtin enumerant registration, but GLSL native validation does not accept the generated mesh/task stage output. | `#16` |
| `glsl` | `mesh-shader-lines.wgsl` | `native-validation` | Golden output is reviewed after mesh builtin enumerant registration, but GLSL native validation does not accept the generated mesh/task stage output. | `#16` |
| `glsl` | `mesh-shader-points.wgsl` | `native-validation` | Golden output is reviewed after mesh builtin enumerant registration, but GLSL native validation does not accept the generated mesh/task stage output. | `#16` |
| `glsl` | `mesh-shader.wgsl` | `native-validation` | Golden output is reviewed after mesh builtin enumerant registration, but GLSL native validation does not accept the generated mesh/task stage output. | `#16` |
| `glsl` | `module-scope.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `msl-varyings.wgsl` | `native-validation` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `glsl` | `msl-vpt-formats-x1.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `msl-vpt-formats-x2.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `msl-vpt-formats-x3.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `msl-vpt-formats-x4.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `msl-vpt.wgsl` | `native-validation` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `glsl` | `multiview.wgsl` | `native-validation` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `glsl` | `multiview_webgl.wgsl` | `native-validation` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `glsl` | `operators.wgsl` | `native-validation` | Golden output is reviewed after comparison type lowering fix, but GLSL native validation still rejects current logical builtin output. | `#16` |
| `glsl` | `overrides-atomicCompareExchangeWeak.wgsl` | `native-validation` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `glsl` | `overrides-ray-query.wgsl` | `native-validation` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `glsl` | `packed-vec3-bitcast.wgsl` | `native-validation` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `glsl` | `padding.wgsl` | `native-validation` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `glsl` | `phony_assignment.wgsl` | `native-validation` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `glsl` | `pointers.wgsl` | `native-validation` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `glsl` | `policy-mix.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `primitive-index-mesh.wgsl` | `native-validation` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `glsl` | `primitive-index.wgsl` | `native-validation` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `glsl` | `push-constants.wgsl` | `native-validation` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `glsl` | `quad.wgsl` | `native-validation` | Golden output is reviewed after texture sampling lowering fix, but GLSL native validation still rejects the current multi-entry-point output due overlapping locations. | `#16` |
| `glsl` | `ray-query-no-init-tracking.wgsl` | `native-validation` | Golden output is reviewed after ray-query builtin registration, but GLSL native validation does not accept the generated ray-query output. | `#16` |
| `glsl` | `ray-query.wgsl` | `native-validation` | Golden output is reviewed after ray-query builtin registration, but GLSL native validation does not accept the generated ray-query output. | `#16` |
| `glsl` | `ray-tracing-pipeline.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `resource-binding-map.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `sample-cube-array-depth-lod.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `select.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `separate-entry-points.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `shadow.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `skybox.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `sprite.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `standard.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `storage-textures.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `struct-layout.wgsl` | `native-validation` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `glsl` | `subgroup-barrier.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `subgroup-operations.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `template-list-ge.wgsl` | `native-validation` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `glsl` | `template-list-trailing-comma.wgsl` | `native-validation` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `glsl` | `texture-arg.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `texture-external.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `types_with_comments.wgsl` | `native-validation` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `glsl` | `unconsumed_vertex_outputs_frag.wgsl` | `native-validation` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `glsl` | `use-gl-ext-over-grad-workaround-if-instructed.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `workgroup-uniform-load-atomic.wgsl` | `native-validation` | Golden output is reviewed, but GLSL native validation rejects the current atomic storage output. | `#16` |
| `glsl` | `workgroup-uniform-load.wgsl` | `native-validation` | Generated output now matches the golden; native validation still rejects the backend output after Emit preservation. | `#16` |
| `glsl` | `workgroup-var-init.wgsl` | `native-validation` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `hlsl` | `abstract-types-return.wgsl` | `native-validation` | HLSL does not support returning arrays with the current writer shape; golden is reviewed but native validation still fails. | `#16` |
| `hlsl` | `abstract-types-texture.wgsl` | `type-resolution` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `hlsl` | `cooperative-matrix.wgsl` | `type-resolution` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `hlsl` | `f64.wgsl` | `type-resolution` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `ir` | `abstract-types-texture.wgsl` | `type-resolution` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `ir` | `cooperative-matrix.wgsl` | `type-resolution` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `ir` | `f64.wgsl` | `type-resolution` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `msl` | `abstract-types-texture.wgsl` | `type-resolution` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `msl` | `cooperative-matrix.wgsl` | `type-resolution` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `msl` | `f64.wgsl` | `type-resolution` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `roundtrip` | `6438-conflicting-idents.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `roundtrip` | `abstract-types-atomic.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `roundtrip` | `abstract-types-texture.wgsl` | `type-resolution` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `roundtrip` | `access.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `roundtrip` | `atomicCompareExchange-int64.wgsl` | `roundtrip-semantic-isomorphism` | Golden output is reviewed, but roundtrip currently drops `li`/`lu` literal suffix width information. | `#16` |
| `roundtrip` | `atomicCompareExchange.wgsl` | `roundtrip-semantic-isomorphism` | Golden output is reviewed, but roundtrip still differs on pointer/value-pointer representation. | `#16` |
| `roundtrip` | `atomicOps-float32.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `roundtrip` | `atomicOps-int64-min-max.wgsl` | `roundtrip-semantic-isomorphism` | Golden output is reviewed, but roundtrip currently rewrites an untyped integer scalar to `u64`. | `#16` |
| `roundtrip` | `atomicOps-int64.wgsl` | `roundtrip-semantic-isomorphism` | Golden output is reviewed, but roundtrip still changes atomic pointer expressions from `Pointer` to `ValuePointer`. | `#16` |
| `roundtrip` | `atomicOps.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `roundtrip` | `atomicTexture-int64.wgsl` | `roundtrip-semantic-isomorphism` | Golden output is reviewed, but roundtrip currently rewrites an untyped integer scalar to `u64`. | `#16` |
| `roundtrip` | `binding-arrays.wgsl` | `roundtrip-semantic-isomorphism` | Golden output is reviewed after binding-array lowering support, but roundtrip currently changes location binding shape. | `#16` |
| `roundtrip` | `binding-buffer-arrays.wgsl` | `roundtrip-semantic-isomorphism` | Golden output is reviewed after binding-array lowering support, but roundtrip currently changes location binding shape. | `#16` |
| `roundtrip` | `bounds-check-zero-atomic.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `roundtrip` | `clip-distances.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `roundtrip` | `const-exprs.wgsl` | `roundtrip-semantic-isomorphism` | Golden output is reviewed after packed dot-product builtin and const switch selector support, but roundtrip currently changes the semantic shape of the lowered module. | `#16` |
| `roundtrip` | `control-flow.wgsl` | `roundtrip-semantic-isomorphism` | Golden output is reviewed after switch trailing-comma parsing and scalar const selector lowering, but roundtrip currently changes the semantic shape of lowered switch cases. | `#16` |
| `roundtrip` | `conversion-float-to-int-no-f64.wgsl` | `roundtrip-semantic-isomorphism` | Generated WGSL contains non-finite float constants as Infinity, which the roundtrip parser resolves as an unknown identifier. | `#16` |
| `roundtrip` | `conversion-float-to-int.wgsl` | `roundtrip-semantic-isomorphism` | Generated WGSL contains non-finite float constants as Infinity, which the roundtrip parser resolves as an unknown identifier. | `#16` |
| `roundtrip` | `cooperative-matrix.wgsl` | `type-resolution` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `roundtrip` | `debug-symbol-large-source.wgsl` | `roundtrip-semantic-isomorphism` | Golden output is reviewed after stage keyword identifier resolution, but roundtrip currently changes location binding shape. | `#16` |
| `roundtrip` | `debug-symbol-simple.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `roundtrip` | `debug-symbol-terrain.wgsl` | `roundtrip-semantic-isomorphism` | Golden output is reviewed after stage keyword identifier resolution, but roundtrip currently changes location binding shape. | `#16` |
| `roundtrip` | `draw-index.wgsl` | `roundtrip-semantic-isomorphism` | Golden output is reviewed, but WGSL writer currently drops the draw_index builtin binding on struct-member input. | `#16` |
| `roundtrip` | `dualsource.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `roundtrip` | `extra.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `roundtrip` | `f16-native.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `roundtrip` | `f16-polyfill.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `roundtrip` | `f64.wgsl` | `type-resolution` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `roundtrip` | `fragment-output.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `roundtrip` | `globals.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `roundtrip` | `int64.wgsl` | `roundtrip-semantic-isomorphism` | Golden output is reviewed, but roundtrip currently rewrites an untyped integer scalar to `i64`. | `#16` |
| `roundtrip` | `interface.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `roundtrip` | `interpolate.wgsl` | `roundtrip-semantic-isomorphism` | Golden output is reviewed after interpolation enumerant resolution, but roundtrip currently changes builtin binding shape. | `#16` |
| `roundtrip` | `interpolate_compat.wgsl` | `roundtrip-semantic-isomorphism` | Golden output is reviewed after interpolation enumerant resolution, but roundtrip currently changes builtin binding shape. | `#16` |
| `roundtrip` | `lexical-scopes.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `roundtrip` | `mesh-shader.wgsl` | `roundtrip-semantic-isomorphism` | Golden output is reviewed after mesh builtin enumerant registration, but roundtrip currently changes builtin binding shape. | `#16` |
| `roundtrip` | `msl-varyings.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `roundtrip` | `msl-vpt-formats-x1.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `roundtrip` | `msl-vpt-formats-x2.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `roundtrip` | `msl-vpt-formats-x3.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `roundtrip` | `msl-vpt-formats-x4.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `roundtrip` | `msl-vpt.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `roundtrip` | `overrides-atomicCompareExchangeWeak.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `roundtrip` | `operators.wgsl` | `roundtrip-semantic-isomorphism` | Reviewed output compares after comparison type lowering fix, but WGSL roundtrip currently rewrites a unary expression as a literal. | `#16` |
| `roundtrip` | `overrides-ray-query.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `roundtrip` | `aliased-ray-query.wgsl` | `roundtrip-semantic-isomorphism` | Golden output is reviewed after ray-query builtin registration, but roundtrip currently changes pointer binding shape. | `#16` |
| `roundtrip` | `ray-query-no-init-tracking.wgsl` | `roundtrip-semantic-isomorphism` | Golden output is reviewed after ray-query builtin registration, but roundtrip currently changes pointer binding shape. | `#16` |
| `roundtrip` | `ray-query.wgsl` | `roundtrip-semantic-isomorphism` | Golden output is reviewed after ray-query builtin registration, but roundtrip currently changes pointer binding shape. | `#16` |
| `roundtrip` | `packed-vec3-bitcast.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `roundtrip` | `pointer-function-arg-restrict.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `roundtrip` | `pointer-function-arg-rzsw.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `roundtrip` | `pointer-function-arg.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `roundtrip` | `pointers.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `roundtrip` | `push-constants.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `roundtrip` | `quad.wgsl` | `roundtrip-semantic-isomorphism` | Golden output is reviewed after texture sampling lowering fix, but roundtrip comparison still differs on return location binding placement. | `#16` |
| `roundtrip` | `ray-tracing-pipeline.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `roundtrip` | `shadow.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `roundtrip` | `skybox.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `roundtrip` | `struct-layout.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `roundtrip` | `unconsumed_vertex_outputs_frag.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `roundtrip` | `unconsumed_vertex_outputs_vert.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `roundtrip` | `workgroup-uniform-load-atomic.wgsl` | `roundtrip-semantic-isomorphism` | Reviewed output compares, but WGSL roundtrip currently changes atomic pointer expressions from `Pointer` to `ValuePointer`. | `#16` |
| `roundtrip` | `workgroup-uniform-load.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `wgsl` | `6438-conflicting-idents.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `wgsl` | `abstract-types-atomic.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `wgsl` | `abstract-types-texture.wgsl` | `type-resolution` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `wgsl` | `access.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `wgsl` | `atomicCompareExchange-int64.wgsl` | `roundtrip-semantic-isomorphism` | Golden output is reviewed, but roundtrip currently drops `li`/`lu` literal suffix width information. | `#16` |
| `wgsl` | `atomicCompareExchange.wgsl` | `roundtrip-semantic-isomorphism` | Golden output is reviewed, but roundtrip still differs on pointer/value-pointer representation. | `#16` |
| `wgsl` | `atomicOps-float32.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `wgsl` | `atomicOps-int64-min-max.wgsl` | `roundtrip-semantic-isomorphism` | Golden output is reviewed, but roundtrip currently rewrites an untyped integer scalar to `u64`. | `#16` |
| `wgsl` | `atomicOps-int64.wgsl` | `roundtrip-semantic-isomorphism` | Golden output is reviewed, but roundtrip still changes atomic pointer expressions from `Pointer` to `ValuePointer`. | `#16` |
| `wgsl` | `atomicOps.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `wgsl` | `atomicTexture-int64.wgsl` | `roundtrip-semantic-isomorphism` | Golden output is reviewed, but roundtrip currently rewrites an untyped integer scalar to `u64`. | `#16` |
| `wgsl` | `binding-arrays.wgsl` | `roundtrip-semantic-isomorphism` | Golden output is reviewed after binding-array lowering support, but roundtrip currently changes location binding shape. | `#16` |
| `wgsl` | `binding-buffer-arrays.wgsl` | `roundtrip-semantic-isomorphism` | Golden output is reviewed after binding-array lowering support, but roundtrip currently changes location binding shape. | `#16` |
| `wgsl` | `bounds-check-zero-atomic.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `wgsl` | `clip-distances.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `wgsl` | `const-exprs.wgsl` | `roundtrip-semantic-isomorphism` | Golden output is reviewed after packed dot-product builtin and const switch selector support, but roundtrip currently changes the semantic shape of the lowered module. | `#16` |
| `wgsl` | `control-flow.wgsl` | `roundtrip-semantic-isomorphism` | Golden output is reviewed after switch trailing-comma parsing and scalar const selector lowering, but roundtrip currently changes the semantic shape of lowered switch cases. | `#16` |
| `wgsl` | `conversion-float-to-int-no-f64.wgsl` | `roundtrip-semantic-isomorphism` | Generated WGSL contains non-finite float constants as Infinity, which the roundtrip parser resolves as an unknown identifier. | `#16` |
| `wgsl` | `conversion-float-to-int.wgsl` | `roundtrip-semantic-isomorphism` | Generated WGSL contains non-finite float constants as Infinity, which the roundtrip parser resolves as an unknown identifier. | `#16` |
| `wgsl` | `cooperative-matrix.wgsl` | `type-resolution` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `wgsl` | `debug-symbol-large-source.wgsl` | `roundtrip-semantic-isomorphism` | Golden output is reviewed after stage keyword identifier resolution, but roundtrip currently changes location binding shape. | `#16` |
| `wgsl` | `debug-symbol-simple.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `wgsl` | `debug-symbol-terrain.wgsl` | `roundtrip-semantic-isomorphism` | Golden output is reviewed after stage keyword identifier resolution, but roundtrip currently changes location binding shape. | `#16` |
| `wgsl` | `draw-index.wgsl` | `roundtrip-semantic-isomorphism` | Golden output is reviewed, but WGSL writer currently drops the draw_index builtin binding on struct-member input. | `#16` |
| `wgsl` | `dualsource.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `wgsl` | `extra.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `wgsl` | `f16-native.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `wgsl` | `f16-polyfill.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `wgsl` | `f64.wgsl` | `type-resolution` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `wgsl` | `fragment-output.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `wgsl` | `globals.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `wgsl` | `int64.wgsl` | `roundtrip-semantic-isomorphism` | Golden output is reviewed, but roundtrip currently rewrites an untyped integer scalar to `i64`. | `#16` |
| `wgsl` | `interface.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `wgsl` | `interpolate.wgsl` | `roundtrip-semantic-isomorphism` | Golden output is reviewed after interpolation enumerant resolution, but roundtrip currently changes builtin binding shape. | `#16` |
| `wgsl` | `interpolate_compat.wgsl` | `roundtrip-semantic-isomorphism` | Golden output is reviewed after interpolation enumerant resolution, but roundtrip currently changes builtin binding shape. | `#16` |
| `wgsl` | `lexical-scopes.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `wgsl` | `mesh-shader.wgsl` | `roundtrip-semantic-isomorphism` | Golden output is reviewed after mesh builtin enumerant registration, but roundtrip currently changes builtin binding shape. | `#16` |
| `wgsl` | `msl-varyings.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `wgsl` | `msl-vpt-formats-x1.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `wgsl` | `msl-vpt-formats-x2.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `wgsl` | `msl-vpt-formats-x3.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `wgsl` | `msl-vpt-formats-x4.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `wgsl` | `msl-vpt.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `wgsl` | `overrides-atomicCompareExchangeWeak.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `wgsl` | `operators.wgsl` | `roundtrip-semantic-isomorphism` | Reviewed output compares after comparison type lowering fix, but WGSL roundtrip currently rewrites a unary expression as a literal. | `#16` |
| `wgsl` | `overrides-ray-query.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `wgsl` | `aliased-ray-query.wgsl` | `roundtrip-semantic-isomorphism` | Golden output is reviewed after ray-query builtin registration, but WGSL comparison currently changes pointer binding shape. | `#16` |
| `wgsl` | `ray-query-no-init-tracking.wgsl` | `roundtrip-semantic-isomorphism` | Golden output is reviewed after ray-query builtin registration, but WGSL comparison currently changes pointer binding shape. | `#16` |
| `wgsl` | `ray-query.wgsl` | `roundtrip-semantic-isomorphism` | Golden output is reviewed after ray-query builtin registration, but WGSL comparison currently changes pointer binding shape. | `#16` |
| `wgsl` | `packed-vec3-bitcast.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `wgsl` | `pointer-function-arg-restrict.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `wgsl` | `pointer-function-arg-rzsw.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `wgsl` | `pointer-function-arg.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `wgsl` | `pointers.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `wgsl` | `push-constants.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `wgsl` | `quad.wgsl` | `roundtrip-semantic-isomorphism` | Golden output is reviewed after texture sampling lowering fix, but WGSL comparison still differs on return location binding placement. | `#16` |
| `wgsl` | `ray-tracing-pipeline.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `wgsl` | `shadow.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `wgsl` | `skybox.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `wgsl` | `struct-layout.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `wgsl` | `unconsumed_vertex_outputs_frag.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `wgsl` | `unconsumed_vertex_outputs_vert.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
| `wgsl` | `workgroup-uniform-load-atomic.wgsl` | `roundtrip-semantic-isomorphism` | Reviewed output compares, but WGSL roundtrip currently changes atomic pointer expressions from `Pointer` to `ValuePointer`. | `#16` |
| `wgsl` | `workgroup-uniform-load.wgsl` | `roundtrip-semantic-isomorphism` | Baseline debt from issue 16 golden stabilization. | `#16` |
