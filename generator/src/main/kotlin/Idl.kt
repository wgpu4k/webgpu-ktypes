import de.fabmax.webidl.model.IdlDictionary
import de.fabmax.webidl.model.IdlInterface
import de.fabmax.webidl.model.IdlSimpleType
import de.fabmax.webidl.model.IdlType
import de.fabmax.webidl.model.IdlTypeDef

internal val webUnwantedTypes = setOf(
    // Types de navigateur
    "NavigatorGPU",
    "Navigator",
    "WorkerNavigator",

    // Types spécifiques au canvas web
    "GPUCanvasContext",
    "GPUCanvasConfiguration",
    "GPUCanvasAlphaMode",
    "GPUCanvasToneMappingMode",
    "GPUCanvasToneMapping",

    // Types dictionnaires redondants
    "GPUColorDict",
    "GPUOrigin2DDict",
    "GPUOrigin3DDict",
    "GPUExtent3DDict",

    // Types d'événements web
    "GPUUncapturedErrorEvent",
    "GPUUncapturedErrorEventInit",

    // Types liés aux workers web
    "GPUExternalTexture",
    "GPUExternalTextureDescriptor",
    "GPUExternalTextureBindingLayout",
    "GPUCopyExternalImageSource",
    "GPUCopyExternalImageDestInfo",
    "GPUCopyExternalImageSourceInfo"
)

fun MapperContext.loadTypeDef(idlTypeDefs: List<IdlTypeDef>) {
    idlTypeDefs
        .filter { it.name.fixName() !in webUnwantedTypes }
        .filter { it.type is IdlSimpleType }
        .forEach { idlTypeDef ->
            typeAliases += TypeAlias(idlTypeDef.name, idlTypeDef.type.toKotlinType())
        }
}


fun MapperContext.loadInterfaces(idlInterfaces: List<IdlInterface>) {
    idlInterfaces
        .filter { it.name.fixName() !in webUnwantedTypes }
        .forEach { idlInterface ->
            val name = idlInterface.name.fixName()
            (interfaces.find { it.name == name } ?: Interface(name).also { interfaces.add(it) })
                .also { kinterface ->
                    kinterface.extends += idlInterface.superInterfaces

                    idlInterface.attributes
                        .filter { it.type is IdlSimpleType }
                        .forEach {
                            kinterface.attributes += Interface.Attribute(it.name, it.type.toKotlinType())
                        }
                }
        }
}

fun MapperContext.loadDictionaries(idlDictionaries: List<IdlDictionary>) {
    idlDictionaries
        .filter { it.name.fixName() !in webUnwantedTypes }
        .forEach { idlDictionary ->
            val name = idlDictionary.name.fixName()
            (interfaces.find { it.name == name } ?: Interface(name).also { interfaces.add(it) })
                .also { kinterface ->
                    kinterface.extends += idlDictionary.superDictionaries

                    idlDictionary.members
                        .filter { it.type is IdlSimpleType }
                        .forEach {
                            kinterface.attributes += Interface.Attribute(it.name, it.type.toKotlinType())
                        }
                }
        }
}

internal fun IdlType.toKotlinType(): String = (this as IdlSimpleType).let {
    when (typeName) {
        "unsigned long" -> "UInt"
        "unsigned long long" -> "ULong"
        "long" -> "Int"
        "long long" -> "Long"
        "float" -> "Float"
        "double" -> "Double"
        "DOMString", "USVString" -> "String"
        "boolean" -> "Boolean"
        else -> typeName
    }
}

internal fun String.fixName(): String = (if (contains(':')) substringBefore(':') else this)
    .replace("\n", "")
    .trim()
