package io.ygdrasil.wgsl.generator.hlsl

import io.ygdrasil.wgsl.back.BackendOptions
import io.ygdrasil.wgsl.back.BackendRegistry
import io.ygdrasil.wgsl.back.BackendWriter
import io.ygdrasil.wgsl.back.HlslOptions
import io.ygdrasil.wgsl.ir.Module
import io.ygdrasil.wgsl.proc.Layouter
import io.ygdrasil.wgsl.proc.Namer
import io.ygdrasil.wgsl.valid.ModuleInfo

class HlslWriterFactory : BackendRegistry.BackendFactory {
    override fun create(): BackendWriter<*> {
        return createWithOptions(HlslOptions())
    }

    override fun createWithOptions(options: BackendOptions): BackendWriter<*> {
        val hlslOptions = options as? HlslOptions ?: HlslOptions()
        return HlslWriter(
            StringBuilder(),
            Module(),
            ModuleInfo(),
            hlslOptions,
            Namer(),
            Layouter()
        )
    }
}

fun registerHlslBackend() {
    BackendRegistry.DEFAULT.register("hlsl", HlslWriterFactory())
}
