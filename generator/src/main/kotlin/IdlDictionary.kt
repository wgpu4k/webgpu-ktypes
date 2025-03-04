import de.fabmax.webidl.model.IdlDictionary
import de.fabmax.webidl.model.IdlSimpleType

internal fun MapperContext.loadDictionaries(idlDictionaries: List<IdlDictionary>) {
    idlDictionaries
        .filter { it.name.fixName() !in webUnwantedTypes }
        .forEach { idlDictionary ->
            val name = idlDictionary.name.fixName()
            loadDictionary(name, idlDictionary)
        }
}

internal fun MapperContext.loadDictionary(name: String, idlDictionary: IdlDictionary) {
    (interfaces.find { it.name == name } ?: Interface(name).also { interfaces.add(it) })
        .also { kinterface ->
            kinterface.extends += idlDictionary.superDictionaries

            idlDictionary.members
                .filter { it.type is IdlSimpleType && (it.type as IdlSimpleType).typeName !in webUnwantedTypes }
                .forEach {
                    kinterface.attributes += Interface.Attribute(it.name, it.type.toKotlinType(), true)
                }
        }
}