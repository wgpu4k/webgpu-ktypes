package io.ygdrasil.wgsl.generator.msl

import io.ygdrasil.wgsl.back.BackendOptions
import io.ygdrasil.wgsl.back.BackendRegistry
import io.ygdrasil.wgsl.back.BackendWriter
import io.ygdrasil.wgsl.back.MslOptions
import io.ygdrasil.wgsl.ir.Module
import io.ygdrasil.wgsl.proc.Layouter
import io.ygdrasil.wgsl.proc.Namer
import io.ygdrasil.wgsl.valid.ModuleInfo

class MslWriterFactory : BackendRegistry.BackendFactory {
    override fun create(): BackendWriter<*> {
        return createWithOptions(MslOptions())
    }

    override fun createWithOptions(options: BackendOptions): BackendWriter<*> {
        val mslOptions = options as? MslOptions ?: MslOptions()
        // These will be initialized later in write() or we provide defaults
        return MslWriter(
            StringBuilder(),
            Module(),
            ModuleInfo(),
            mslOptions,
            Namer(),
            Layouter()
        )
    }
}

fun registerMslBackend() {
    BackendRegistry.DEFAULT.register("msl", MslWriterFactory())
}
