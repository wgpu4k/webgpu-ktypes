package io.ygdrasil.wgsl.tests

import io.ygdrasil.wgsl.back.registerIrBackend
import io.ygdrasil.wgsl.generator.glsl.registerGlslBackend
import io.ygdrasil.wgsl.generator.hlsl.registerHlslBackend
import io.ygdrasil.wgsl.generator.msl.registerMslBackend
import io.ygdrasil.wgsl.wgsl.registerWgslBackend

fun registerAllBackends() {
    registerIrBackend()
    registerMslBackend()
    registerHlslBackend()
    registerGlslBackend()
    registerWgslBackend()
}
