package domain

data class Enumeration(
    val name: String,
    val values: List<String>,
    val parameters: List<String> = emptyList(),
    val isActual: Boolean = false, val isExpect: Boolean = false
) {

    init {
        if (isActual && isExpect) throw IllegalArgumentException("Enumeration cannot be actual and expect at the same time")
    }

    override fun toString(): String = StringBuilder().apply {
        if (isActual) append("actual ")
        if (isExpect) append("expect ")
        append("enum class $name")
        if (parameters.isNotEmpty()) append("(${parameters.joinToString(", ")})")
        append(" {\n\t")
        append(values.joinToString(",\n\t"))
        append(";\n")
        append("}\n")
    }.toString()
}