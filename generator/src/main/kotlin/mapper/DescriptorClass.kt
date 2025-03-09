package mapper

import toKotlinType
import MapperContext
import fixName
import webUnwantedTypes
import de.fabmax.webidl.model.IdlDictionary
import de.fabmax.webidl.model.IdlSimpleType

fun MapperContext.loadDescriptors() {
    idlModel.dictionaries
        .filter { it.name.fixName() !in webUnwantedTypes }
        .forEach { loadDescriptor(it.name.fixName(), it) }
}

internal fun MapperContext.loadDescriptor(name: String, idlDictionary: IdlDictionary) {
    val parameters = idlDictionary.members.map {
        var value = it.defaultValue
        println("$name ${it.type}")
        var type = if(it.type is IdlSimpleType) it.type.toKotlinType() else {
            println("not supported: ${it.type}")
            "FakeType"
        }

        when {
            value == null -> {}
            value == "{}" -> {
                value = "${type.removePrefix("GPU")}()"
            }
            value.isUnsignedNumericType(type) -> {
                value = "${value}u"
            }
            value == "[]" -> {
                value = "emptyList()"
            }
            isEnumeration(type) -> {
                value = "$type.${getEnumerationValueNameOnKotlin(type, value)}"
            }
        }
        domain.DescriptorClass.Parameter(it.name, type, value)
    }
    descriptors += domain.DescriptorClass(name, parameters)
}

private fun String?.isUnsignedNumericType(type: String): Boolean = this?.let {
    runCatching {
        Integer.parseInt(it)
    }
        .mapCatching { isHexadecimal() }
        .map { type.lowercase().contains("signed").not() }
        .getOrElse { false }
} ?: false

private fun String.isHexadecimal(): Boolean {
    val hexRegex = Regex("^0[xX][0-9a-fA-F]+\$")
    return hexRegex.matches(this)
}
