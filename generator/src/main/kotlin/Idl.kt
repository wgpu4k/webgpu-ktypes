import de.fabmax.webidl.model.IdlEnum
import de.fabmax.webidl.model.IdlInterface
import de.fabmax.webidl.model.IdlSimpleType
import de.fabmax.webidl.model.IdlType

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

fun MapperContext.loadEnums(idlEnums: List<IdlEnum>) {
    idlEnums.filter { it.name.fixName() !in webUnwantedTypes }
        .forEach { idlEnum ->
            enumerations += Enumeration(idlEnum.name)
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
                        .filter { it.type is IdlSimpleType && (it.type as IdlSimpleType).typeName !in webUnwantedTypes }
                        .forEach {
                            kinterface.attributes += Interface.Attribute(it.name, it.type.toKotlinType(), it.isReadonly)
                        }
                }
        }
}


internal fun IdlType.toKotlinType(): String = (this as IdlSimpleType).let {
    when (typeName) {
        "sequence", "FrozenArray" -> "List<${this.parameterTypes!!.first().toKotlinType()}>"
        "record" -> "Map<${this.parameterTypes!!.first().toKotlinType()}, ${this.parameterTypes!![1].toKotlinType()}>"
        else -> typeName.toKotlinType()
    }
}

internal fun String.toKotlinType(): String = when (this) {
    "unsigned long" -> "UInt"
    "unsigned long long" -> "ULong"
    "short" -> "Short"
    "unsigned short" -> "UShort"
    "long" -> "Int"
    "long long" -> "Long"
    "float" -> "Float"
    "double" -> "Double"
    "DOMString", "USVString" -> "String"
    "boolean" -> "Boolean"
    else -> this
}


internal fun String.fixName(): String = (if (contains(':')) substringBefore(':') else this)
    .replace("\n", "")
    .trim()
