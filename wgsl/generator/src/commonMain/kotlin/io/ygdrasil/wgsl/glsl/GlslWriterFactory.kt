package io.ygdrasil.wgsl.generator.glsl

import io.ygdrasil.wgsl.back.BackendOptions
import io.ygdrasil.wgsl.back.BackendRegistry
import io.ygdrasil.wgsl.back.BackendWriter
import io.ygdrasil.wgsl.back.GlslOptions
import io.ygdrasil.wgsl.ir.Module
import io.ygdrasil.wgsl.proc.Layouter
import io.ygdrasil.wgsl.proc.Namer
import io.ygdrasil.wgsl.valid.ModuleInfo

class GlslWriterFactory : BackendRegistry.BackendFactory {
    override fun create(): BackendWriter<*> {
        return createWithOptions(GlslOptions())
    }

    override fun createWithOptions(options: BackendOptions): BackendWriter<*> {
        val glslOptions = options as? GlslOptions ?: GlslOptions()
        return GlslWriter(
            StringBuilder(),
            Module(),
            ModuleInfo(),
            glslOptions,
            Namer(),
            Layouter()
        )
    }
}

fun registerGlslBackend() {
    BackendRegistry.DEFAULT.register("glsl", GlslWriterFactory())
}
