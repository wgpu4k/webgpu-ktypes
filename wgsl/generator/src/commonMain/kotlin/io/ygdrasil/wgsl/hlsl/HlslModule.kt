package io.ygdrasil.wgsl.generator.hlsl

import io.ygdrasil.wgsl.back.HlslOptions
import io.ygdrasil.wgsl.ir.Module
import io.ygdrasil.wgsl.proc.Layouter
import io.ygdrasil.wgsl.proc.Namer
import io.ygdrasil.wgsl.valid.ModuleInfo

/**
 * API publique pour le backend HLSL.
 */
object HlslModule {

    /**
     * Génère le code source HLSL pour un module.
     */
    fun writeString(
        module: Module,
        moduleInfo: ModuleInfo = ModuleInfo.empty(),
        options: HlslOptions = HlslOptions()
    ): String {
        val namer = Namer().apply { reset(emptySet()) }
        val layouter = Layouter().apply { update(module) }
        val output = StringBuilder()
        val writer = HlslWriter(output, module, moduleInfo, options, namer, layouter)
        return writer.write()
    }
}
