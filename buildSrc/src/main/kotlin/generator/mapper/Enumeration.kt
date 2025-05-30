package generator.mapper

import generator.domain.Enumeration
import generator.domain.MapperContext
import generator.domain.fixNameStartingWithNumeric
import kotlin.collections.forEach

fun MapperContext.loadEnums() {
    loadBitFlagEnums()
    loadConventionalEnums()
}

private fun MapperContext.loadBitFlagEnums() {
    yamlModel.bitflags.forEach { bitflag ->
        val name = bitflag.name.convertToKotlinClassName()
        bitflagEnumerations += Enumeration(
            "GPU$name",
            bitflag.entries
                .mapIndexed { index, entry ->
                    // Calculate first if that a combination
                    val value =
                        entry.value_combination?.sumOf { subPart -> indexToFlagValue(bitflag.entries.indexOfFirst { it.name == subPart }) }
                            ?: indexToFlagValue(index)

                    val name = entry.name.convertToKotlinClassName()
                    Enumeration.Value("$name(${value}uL)")
                },
            parameters = listOf("override val value: ULong"),
            extends = listOf("FlagEnumeration"),

            )
    }
}

private fun indexToFlagValue(base: Int): Int = if (base == 0) 0 else 1 shl (base - 1)

private fun MapperContext.loadConventionalEnums() {
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
                .filter { it.name != "undefined" && it.name != "null" }
                .map { value ->
                    Enumeration.Value(value.name.convertToKotlinClassName()
                        .fixNameStartingWithNumeric())
                },
            isExpect = true
        ).also { enumeration ->
            commonWebEnumerations += enumeration.copy(
                isExpect = false, isActual = true,
                parameters = listOf("val value: String"),
                extra = generateExtra(name, "String"),
                values = enumeration.values.map { enumerationValue ->
                    val webValue = idlEnum.values.firstOrNull {
                        enumerationValue.name.lowercase() == it.replace("-", "").fixNameStartingWithNumeric().lowercase()
                    } ?: "unsupported"
                    Enumeration.Value("${enumerationValue.name}(\"$webValue\")")
                }
            )
            commonNativeEnumerations += enumeration.copy(
                isExpect = false, isActual = true,
                parameters = listOf("val value: UInt"),
                extra = generateExtra(name, "UInt"),
                values = enumeration.values.map { enumerationValue ->
                    val nativeValue = yamlEnum.values.first {
                        enumerationValue.name.lowercase() == it.name.convertToKotlinClassName().fixNameStartingWithNumeric()
                            .lowercase()
                    }.let {
                        it.value
                            ?: yamlEnum.values.indexOf(it)
                    }
                    Enumeration.Value("${enumerationValue.name}(${nativeValue}u)")
                }
            )
        }

    }

    idlModel.enums.filter { it.name.fixName() !in unwantedTypesOnCommon }
        .forEach { idlEnum ->
            if (commonEnumerations.none { it.name == idlEnum.name }) println("Enum from Idl not found: ${idlEnum.name}")
        }
}

private fun generateExtra(name: String, type: String): String = """

	companion object {
		/**
		 * Retrieves the corresponding [$type] for the given value.
		 *
		 * @param value The dependent platform value representing the WebGPU value.
		 * @return The matching [$type] or `null` if no match is found.
		 */
		fun of(value: $type): $name? {
			return entries.find { it.value == value }
		}
    }

"""

internal fun String.convertToKotlinClassName() = split("_")
        .joinToString("") { component -> component.replaceFirstChar { it.uppercase() } }



