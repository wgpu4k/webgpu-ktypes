package generator.mapper

import generator.MapperContext
import generator.unwantedTypesOnCommon
import de.fabmax.webidl.model.IdlDictionary
import de.fabmax.webidl.model.IdlMember
import de.fabmax.webidl.model.IdlSimpleType
import de.fabmax.webidl.model.IdlUnionType
import generator.domain.DescriptorClass
import generator.fixName
import generator.toKotlinType
import kotlin.collections.plusAssign

fun MapperContext.loadDescriptors() {
    idlModel.dictionaries
        .filter { it.name.fixName() !in unwantedTypesOnCommon }
        .forEach { loadDescriptor(it.name.fixName(), it) }
}

internal fun MapperContext.loadDescriptor(name: String, idlDictionary: IdlDictionary) {
    val parameters = getMembers(idlDictionary)
        // Layout is a special case
        .filter { (it.type as? IdlSimpleType)?.toKotlinType() !in unwantedTypesOnCommon || it.name == "layout"}
        .map {
            var value = it.defaultValue
            var type = if (it.type is IdlSimpleType) it.type.toKotlinType() else {
                value = "null"
                "${(it.type as IdlUnionType).types.first().toKotlinType()}?"
            }

            when {
                value == null -> if (it.isRequired.not()) {
                    value = "null"
                    type += "?"
                }
                value == "{}" && type.startsWith("Map<") -> value = "emptyMap()"
                value == "{}" -> value = "${type.removePrefix("GPU")}()"
                isUnsignedNumericType(type) -> value = "${value}u"
                isFloatType(type) -> value = "${value}f"
                value == "[]" -> value = "emptyList()"
                isEnumeration(type) -> value = "$type.${getEnumerationValueNameOnKotlin(type, value)}"
            }
            DescriptorClass.Parameter(it.name, type, value)
        }
    descriptors += DescriptorClass(name, parameters)
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
                .filter { it !in unwantedTypesOnCommon }
                .flatMap {
                    getMembers(idlModel.dictionaries.find { dictionary -> dictionary.name.fixName() == it }
                        ?: error("Ghost member not found: $it"))
                }
        } ?: emptyList()
}