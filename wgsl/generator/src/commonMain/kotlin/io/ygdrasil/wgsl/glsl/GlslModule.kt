package io.ygdrasil.wgsl.generator.glsl

import io.ygdrasil.wgsl.back.GlslOptions
import io.ygdrasil.wgsl.ir.Module
import io.ygdrasil.wgsl.proc.Layouter
import io.ygdrasil.wgsl.proc.Namer
import io.ygdrasil.wgsl.valid.ModuleInfo

/**
 * API publique pour le backend GLSL.
 */
object GlslModule {

    /**
     * Génère le code source GLSL pour un module.
     */
    fun writeString(
        module: Module,
        moduleInfo: ModuleInfo = ModuleInfo.empty(),
        options: GlslOptions = GlslOptions()
    ): String {
        val namer = Namer().apply { reset(emptySet()) }
        val layouter = Layouter().apply { update(module) }
        val output = StringBuilder()
        val writer = GlslWriter(output, module, moduleInfo, options, namer, layouter)
        return writer.write()
    }
}
