package mapper

import toKotlinType
import MapperContext
import fixName
import webUnwantedTypes
import de.fabmax.webidl.model.IdlDictionary
import de.fabmax.webidl.model.IdlMember
import de.fabmax.webidl.model.IdlSimpleType

fun MapperContext.loadDescriptors() {
    idlModel.dictionaries
        .filter { it.name.fixName() !in webUnwantedTypes }
        .forEach { loadDescriptor(it.name.fixName(), it) }
}

internal fun MapperContext.loadDescriptor(name: String, idlDictionary: IdlDictionary) {
    val parameters = getMembers(idlDictionary).map {
        var value = it.defaultValue
        println("$name ${it.type}")
        var type = if(it.type is IdlSimpleType) it.type.toKotlinType() else {
            println("not supported: ${it.type}")
            "FakeType"
        }

        when {
            value == null -> if (it.isRequired.not()) {
                value = "null"
                type += "?"
            }
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

fun MapperContext.getMembers(idlDictionary: IdlDictionary): List<IdlMember> {
    return idlDictionary.members +
            idlDictionary.superDictionaries.flatMap { getMembers(idlModel.dictionaries.find { dictionary -> dictionary.name == it }!!) } +
            getGhostMembers(idlDictionary)

}

/**
 * To remove when fixe on parsing library
 * Retrieves a list of ghost members related to the given `IdlDictionary`.
 *
 * Ghost members are those members derived from the `name` property of the provided `IdlDictionary`.
 * If `name` contains a colon (":"), a subset of types is parsed from it, excluding unwanted types,
 * and their corresponding members are fetched recursively.
 *
 * @param idlDictionary The `IdlDictionary` from which ghost members are extracted.
 * @return A list of `IdlMember` objects representing the ghost members. Returns an empty list if `name` does not contain a colon or there are no valid ghost members.
 */
fun MapperContext.getGhostMembers(idlDictionary: IdlDictionary): List<IdlMember> {
    return idlDictionary.name.takeIf { it.contains(":") }
        ?.let {
            it.substringAfter(":")
                .split(",")
                .map { it.trim() }
                .filter { it !in webUnwantedTypes }
                .also { println("Ghost members for ${idlDictionary.name}: $it") }
                .flatMap { getMembers(idlModel.dictionaries.find { dictionary -> dictionary.name.fixName() == it } ?: error("Ghost member not found: $it")) }
        } ?: emptyList()
}