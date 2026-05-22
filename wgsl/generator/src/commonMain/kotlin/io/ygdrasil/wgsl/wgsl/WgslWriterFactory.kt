package io.ygdrasil.wgsl.wgsl

import io.ygdrasil.wgsl.back.BackendOptions
import io.ygdrasil.wgsl.back.BackendRegistry
import io.ygdrasil.wgsl.back.BackendWriter
import io.ygdrasil.wgsl.back.WgslOptions
import io.ygdrasil.wgsl.ir.Module
import io.ygdrasil.wgsl.proc.Layouter
import io.ygdrasil.wgsl.proc.Namer
import io.ygdrasil.wgsl.valid.ModuleInfo

class WgslWriterFactory : BackendRegistry.BackendFactory {
    override fun create(): BackendWriter<*> {
        return createWithOptions(WgslOptions())
    }

    override fun createWithOptions(options: BackendOptions): BackendWriter<*> {
        val wgslOptions = options as? WgslOptions ?: WgslOptions()
        return WgslWriter(
            StringBuilder(),
            Module(),
            ModuleInfo(),
            wgslOptions,
            Namer(),
            Layouter()
        )
    }
}

fun registerWgslBackend() {
    BackendRegistry.DEFAULT.register("wgsl", WgslWriterFactory())
}
