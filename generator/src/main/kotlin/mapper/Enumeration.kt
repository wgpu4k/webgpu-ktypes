package mapper

import MapperContext
import de.fabmax.webidl.model.IdlModel
import domain.Enumeration
import domain.YamlModel
import fixName
import webUnwantedTypes
import kotlin.collections.plus

fun MapperContext.loadEnums(model: IdlModel, yamlModel: YamlModel) {
    model.enums.filter { it.name.fixName() !in webUnwantedTypes }
        .forEach { idlEnum ->
            enumerations += Enumeration(idlEnum.name, emptyList())
        }
}