import de.fabmax.webidl.model.IdlFunction
import de.fabmax.webidl.model.IdlSimpleType
import de.fabmax.webidl.model.IdlType
import domain.Interface

fun MapperContext.loadInterfaces() {
    idlModel.interfaces
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

                    idlInterface.functions
                        .filter { it.returnType is IdlSimpleType && (it.returnType as IdlSimpleType).typeName !in webUnwantedTypes  }
                        .filter { it.parameters.all { p -> p.type is IdlSimpleType && (p.type as IdlSimpleType).typeName !in webUnwantedTypes } }
                        .forEach { idlFunction ->
                            removeOverloadedMethodWithFewerParams(kinterface, idlFunction)

                            kinterface.methods += Interface.Method(
                                idlFunction.name,
                                idlFunction.returnType.toKotlinType(),
                                idlFunction.parameters.map {
                                    var value = it.defaultValue
                                    var type = it.type.toKotlinType()

                                    if (value == "{}") {
                                        value = "null"
                                        if (type.endsWith("?").not()) {
                                            type = "$type?"
                                        }
                                    } else if (value != null && type.lowercase().contains("signed").not()) {
                                        value = "${value}u"
                                    }

                                    Interface.Method.Parameter(
                                        it.name,
                                        type,
                                        value
                                    )
                                },
                                idlFunction.returnType.isPromise()
                            )
                        }
                }
        }
}

private fun removeOverloadedMethodWithFewerParams(
    kinterface: Interface,
    targetFunction: IdlFunction
) {
    fun isOverloadedWithFewerParams(method: Interface.Method): Boolean =
        method.name == targetFunction.name &&
                method.parameters.size < targetFunction.parameters.size

    kinterface.methods = kinterface.methods.filterNot(::isOverloadedWithFewerParams)
}

private fun IdlType.isPromise(): Boolean {
    return (this as? IdlSimpleType)?.typeName == "Promise"
}