package mapper

import toKotlinType
import MapperContext
import fixName
import webUnwantedTypes
import de.fabmax.webidl.model.IdlDictionary
import de.fabmax.webidl.model.IdlModel

fun MapperContext.loadDescriptors(idlModel: IdlModel) {
    idlModel.dictionaries
        .filter { it.name.fixName() !in webUnwantedTypes }
        .forEach { loadDescriptor(it.name, it) }
}

internal fun MapperContext.loadDescriptor(name: String, idlDictionary: IdlDictionary) {
    val parameters = idlDictionary.members.map {
        var value = it.defaultValue
        println("$name ${it.type}")
        var type = it.type.toKotlinType()

        if (value == "{}") {
            value = "$type()"
        } else if (value != null && type.lowercase().contains("signed").not()) {
            value = "${value}u"
        }
        domain.DescriptorClass.Parameter(it.name, type, value)
    }
    descriptors += domain.DescriptorClass(name, parameters)
}