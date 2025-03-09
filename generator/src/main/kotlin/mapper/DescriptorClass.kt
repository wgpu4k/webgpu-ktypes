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

        if (value == "{}") {
            value = "$type()"
        } else if (value.isUnsignedNumericType(type)) {
            value = "${value}u"
        } else if (value == "[]") {
            value = "emptyList()"
        }
        domain.DescriptorClass.Parameter(it.name, type, value)
    }
    descriptors += domain.DescriptorClass(name, parameters)
}

fun String?.isUnsignedNumericType(type: String): Boolean = this?.let {
    runCatching {
        Integer.parseInt(it)
    }.map { type.lowercase().contains("signed").not() }
        .getOrElse { false }
} ?: false