import de.fabmax.webidl.model.IdlSimpleType
import de.fabmax.webidl.model.IdlUnionType
import domain.Interface
import domain.TypeAlias
import mapper.loadDescriptor

internal fun MapperContext.loadTypeDef() {
    idlModel.typeDefs
        .filter { it.name.fixName() !in webUnwantedTypes }
        .filter { it.type is IdlSimpleType }
        .forEach { idlTypeDef ->
            typeAliases += TypeAlias(idlTypeDef.name, idlTypeDef.type.toKotlinType())
        }
    idlModel.typeDefs
        .filter { it.name.fixName() !in webUnwantedTypes }
        .filter { it.type is IdlUnionType }
        .forEach { idlTypeDef ->
            val type = (idlTypeDef.type as IdlUnionType)
            // Special cases
            if (type.types.size == 2 && type.types.first().typeName == "sequence" && type.types[1].typeName.startsWith("GPU")) {
                val typeToInline = type.types[1]
                idlModel.dictionaries.find { it.name == typeToInline.typeName }?.let { dictionary ->
                    loadDictionary(idlTypeDef.name, dictionary)
                    loadDescriptor(idlTypeDef.name, dictionary)
                }
            } else if(type.types.all { it.typeName.startsWith("GPU") }){
                interfaces += Interface(idlTypeDef.name, sealed = true)
                type.types.forEach { subType ->
                    (interfaces.find { it.name == subType.typeName } ?: Interface(subType.typeName).also { interfaces.add(it) })
                        .extends += idlTypeDef.name
                }
            } else {
                error("Unhandled union type: ${idlTypeDef.name}: ${type.types.joinToString { it.typeName }}")
            }

        }
}