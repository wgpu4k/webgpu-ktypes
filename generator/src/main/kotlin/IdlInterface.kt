import de.fabmax.webidl.model.IdlInterface
import de.fabmax.webidl.model.IdlSimpleType
import de.fabmax.webidl.model.IdlType

fun MapperContext.loadInterfaces(idlInterfaces: List<IdlInterface>) {
    idlInterfaces
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
                            kinterface.methods += Interface.Method(
                                idlFunction.name,
                                idlFunction.returnType.toKotlinType(),
                                idlFunction.parameters.map { it.name to it.type.toKotlinType() },
                                idlFunction.returnType.isPromise()
                            )
                        }
                }
        }
}


private fun IdlType.isPromise(): Boolean {
    return (this as? IdlSimpleType)?.typeName == "Promise"
}