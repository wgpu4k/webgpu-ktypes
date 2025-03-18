package mapper

import MapperContext
import de.fabmax.webidl.model.IdlDictionary
import de.fabmax.webidl.model.IdlInterface
import de.fabmax.webidl.model.IdlSimpleType
import de.fabmax.webidl.model.IdlUnionType
import domain.Interface
import loadDictionary
import fixName
import toKotlinType
import toWebKotlinType
import unwantedTypesOnCommon

internal fun MapperContext.loadWebInterfaces() {
    // Load web-specific interfaces from the IDL model
    idlModel.interfaces.forEach { idlInterface ->
            loadWebInterface(idlInterface)
        }

    // Load web-specific dictionaries from the IDL model
    idlModel.dictionaries
        .forEach { idlDictionary ->
            loadWebDictionary(idlDictionary)
        }
}

private fun MapperContext.loadWebInterface(idlInterface: IdlInterface) {
    val name = idlInterface.name.fixName()
    (webInterfaces.find { it.name == "W$name" } ?: Interface("W$name", external = true).also { webInterfaces.add(it) })
        .also { kinterface ->
            // Add extends
            kinterface.extends += idlInterface.superInterfaces
            idlInterface.name.takeIf { it.contains(":") }
                ?.let {
                    kinterface.extends += it.substringAfter(":")
                        .split(",")
                        .map { it.trim() }
                }

            // Add attributes
            idlInterface.attributes
                .forEach {
                    kinterface.attributes += Interface.Attribute(it.name, it.type.toWebKotlinType(), it.isReadonly)
                }

            // Add methods
            idlInterface.functions
                .forEach { idlFunction ->
                    kinterface.methods += Interface.Method(
                        idlFunction.name,
                        idlFunction.returnType.toWebKotlinType(),
                        idlFunction.parameters.map {
                            Interface.Method.Parameter(
                                it.name,
                                it.type.toWebKotlinType(),
                                it.defaultValue
                            )
                        },
                        // No isSuspend on Web
                        isSuspend = false
                    )
                }
        }
}

private fun MapperContext.loadWebDictionary(idlDictionary: IdlDictionary) {
    val name = idlDictionary.name.fixName()
    val kinterface = loadDictionary("W$name", idlDictionary)

    // Add to webInterfaces if not already present
    if (webInterfaces.none { it.name == name }) {
        webInterfaces.add(kinterface)
    }
}

private fun MapperContext.loadWebDictionary(name: String, idlDictionary: IdlDictionary): Interface {
    return (webInterfaces.find { it.name == name } ?: Interface(name).also { webInterfaces.add(it) })
        .also { kinterface ->
            kinterface.extends += idlDictionary.superDictionaries

            idlDictionary.members
                .filter { it.type is IdlSimpleType && (it.type as IdlSimpleType).typeName !in unwantedTypesOnCommon || it.name == "layout" }
                .forEach {
                    var type = if((it.type is IdlSimpleType)) it.type.toWebKotlinType() else {
                        "${(it.type as IdlUnionType).types.first().toWebKotlinType()}?"
                    }
                    if (it.defaultValue == null && it.isRequired.not()) { type += "?" }
                    kinterface.attributes += Interface.Attribute(it.name, type, true)
                }
        }
}