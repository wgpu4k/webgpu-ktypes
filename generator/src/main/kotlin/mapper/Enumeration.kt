package mapper

import MapperContext
import de.fabmax.webidl.model.IdlModel
import domain.Enumeration
import domain.YamlModel
import fixName
import webUnwantedTypes
import kotlin.collections.plus

fun MapperContext.loadEnums(idlModel: IdlModel, yamlModel: YamlModel) {

    yamlModel.enums.forEach { yamlEnum ->
        val name = "GPU${yamlEnum.name.convertToKotlinClassName()}"
        val idlEnum = idlModel.enums.find { it.name == name }
        if (idlEnum == null) {
            println("Skipping native enumeration = $name")
            return@forEach
        }
        commonEnumerations += Enumeration(
            name,
            yamlEnum.values
                .filter { it.name != "undefined" }
                .map { value ->
                    value.name.convertToKotlinClassName()
                        .fixNameStartingWithNumeric()
                },
            isExpect = true
        ).also { enumeration ->
            commonWebEnumerations += enumeration.copy(
                isExpect = false, isActual = true,
                parameters = listOf("val value: String"),
                values = enumeration.values.map { enumerationValue ->
                    val webValue = idlEnum.values.firstOrNull {
                        enumerationValue.lowercase() == it.replace("-", "").lowercase()
                    } ?: "unsupported"
                    "$enumerationValue(\"$webValue\")"
                }
            )
            commonNativeEnumerations += enumeration.copy(
                isExpect = false, isActual = true
            )
        }

    }

    idlModel.enums.filter { it.name.fixName() !in webUnwantedTypes }
        .forEach { idlEnum ->
            if (commonEnumerations.none { it.name == idlEnum.name }) println("Enum from Idl not found: ${idlEnum.name}")
        }
}


private fun String.convertToKotlinClassName() = split("_")
        .joinToString("") { component -> component.replaceFirstChar { it.uppercase() } }

private fun String.fixNameStartingWithNumeric(): String {
    return if (first().isDigit()) {
        when (first()) {
            '1' -> "One${substring(1)}"
            '2' -> "Two${substring(1)}"
            '3' -> "Three${substring(1)}"
            '4' -> "Four${substring(1)}"
            '5' -> "Five${substring(1)}"
            '6' -> "Six${substring(1)}"
            '7' -> "Seven${substring(1)}"
            '8' -> "Eight${substring(1)}"
            '9' -> "Nine${substring(1)}"
            '0' -> "Zero${substring(1)}"
            else -> error("Invalid name starting with numeric: $this")
        }
    } else this
}
