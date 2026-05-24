package io.ygdrasil.wgsl.back

import io.ygdrasil.wgsl.ir.Module
import io.ygdrasil.wgsl.valid.ModuleInfo
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Writer qui sérialise le module IR en JSON.
 */
class IrWriter(private val options: IrOptions = IrOptions()) : BackendWriter<IrOptions> {

    private val json = Json {
        prettyPrint = true
        allowStructuredMapKeys = true
        allowSpecialFloatingPointValues = true
        classDiscriminator = "__type"
    }

    override fun write(module: Module, moduleInfo: ModuleInfo): String {
        return json.encodeToString(module)
    }

    override fun withOptions(options: IrOptions): BackendWriter<IrOptions> {
        return IrWriter(options)
    }

    override fun canHandle(module: Module, moduleInfo: ModuleInfo): Boolean {
        return true
    }
}

fun registerIrBackend() {
    BackendRegistry.DEFAULT.register("ir", object : BackendRegistry.BackendFactory {
        override fun create(): BackendWriter<*> = IrWriter()
        override fun createWithOptions(options: BackendOptions): BackendWriter<*> = IrWriter(options as IrOptions)
    })
}
