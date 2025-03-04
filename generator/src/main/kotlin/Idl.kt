import de.fabmax.webidl.model.IdlEnum
import de.fabmax.webidl.model.IdlInterface
import de.fabmax.webidl.model.IdlSimpleType
import de.fabmax.webidl.model.IdlType
import toKotlinType

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




internal fun IdlType.toKotlinType(): String = (this as IdlSimpleType).let {
    when (typeName) {
        "sequence", "FrozenArray" -> "List<${this.parameterTypes!!.first().toKotlinType()}>"
        "record" -> "Map<${this.parameterTypes!!.first().toKotlinType()}, ${this.parameterTypes!![1].toKotlinType()}>"
        "Promise" -> this.parameterTypes!!.first().toKotlinType()
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
    "undefined" -> "Unit"
    "AllowSharedBufferSource" -> "GPUBufferSource"
    "Uint32Array" -> "List<UInt>"
    else -> this
}


internal fun String.fixName(): String = (if (contains(':')) substringBefore(':') else this)
    .replace("\n", "")
    .trim()
