import de.fabmax.webidl.model.IdlDictionary
import de.fabmax.webidl.model.IdlSimpleType
import de.fabmax.webidl.model.IdlUnionType
import domain.Interface

internal fun MapperContext.loadDictionaries() {
    idlModel.dictionaries
        .filter { it.name.fixName() !in unwantedTypesOnCommon }
        .forEach { idlDictionary ->
            val name = idlDictionary.name.fixName()
            loadDictionary(name, idlDictionary).also { kinterface ->
                idlDictionary.name.takeIf { it.contains(":") }
                    ?.let {
                        kinterface.extends += it.substringAfter(":")
                            .split(",")
                            .map { it.trim() }
                            .filter { it !in unwantedTypesOnCommon }
                    }
            }
        }
}

internal fun MapperContext.loadDictionary(name: String, idlDictionary: IdlDictionary): Interface {
    return (interfaces.find { it.name == name } ?: Interface(name).also { interfaces.add(it) })
        .also { kinterface ->
            kinterface.extends += idlDictionary.superDictionaries

            idlDictionary.members
                .filter { it.type is IdlSimpleType && (it.type as IdlSimpleType).typeName !in unwantedTypesOnCommon || it.name == "layout" }
                .forEach {
                    var type = if((it.type is IdlSimpleType)) it.type.toKotlinType() else {
                        "${(it.type as IdlUnionType).types.first().toKotlinType()}?"
                    }
                    if (it.defaultValue == null && it.isRequired.not()) { type += "?" }
                    kinterface.attributes += Interface.Attribute(it.name, type, true)
                }
        }
}