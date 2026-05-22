package io.ygdrasil.wgsl.generator.msl

import io.ygdrasil.wgsl.back.MslOptions
import io.ygdrasil.wgsl.ir.Module
import io.ygdrasil.wgsl.proc.Layouter
import io.ygdrasil.wgsl.proc.Namer
import io.ygdrasil.wgsl.valid.ModuleInfo

/**
 * API publique pour le backend MSL.
 */
object MslModule {

    /**
     * Génère le code source MSL pour un module.
     */
    fun writeString(
        module: Module,
        moduleInfo: ModuleInfo = ModuleInfo.empty(),
        options: MslOptions = MslOptions()
    ): String {
        val namer = Namer().apply { reset(Keywords.MSL_RESERVED) }
        val layouter = Layouter().apply { update(module) }
        val output = StringBuilder()
        val writer = MslWriter(output, module, moduleInfo, options, namer, layouter)
        return writer.write()
    }
}
