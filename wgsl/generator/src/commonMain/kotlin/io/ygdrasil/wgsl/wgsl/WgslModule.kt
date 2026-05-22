package io.ygdrasil.wgsl.wgsl

import io.ygdrasil.wgsl.back.WgslOptions
import io.ygdrasil.wgsl.ir.Module
import io.ygdrasil.wgsl.proc.Layouter
import io.ygdrasil.wgsl.proc.Namer
import io.ygdrasil.wgsl.valid.ModuleInfo

/**
 * API publique pour le backend WGSL.
 */
object WgslModule {

    /**
     * Génère le code source WGSL pour un module.
     */
    fun writeString(
        module: Module,
        moduleInfo: ModuleInfo = ModuleInfo.empty(),
        options: WgslOptions = WgslOptions()
    ): String {
        val namer = Namer().apply { reset(emptySet()) }
        val layouter = Layouter().apply { update(module) }
        val output = StringBuilder()
        val writer = WgslWriter(output, module, moduleInfo, options, namer, layouter)
        return writer.write()
    }
}
