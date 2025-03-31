package generator.domain

data class Enumeration(
    var name: String,
    val values: List<String>,
    val parameters: List<String> = emptyList(),
    val isActual: Boolean = false, val isExpect: Boolean = false,
    val extra: String? = null,
    val extends: List<String> = emptyList()
) {

    init {
        if (isActual && isExpect) throw IllegalArgumentException("Enumeration cannot be actual and expect at the same time")
    }

    override fun toString(): String = StringBuilder().apply {
        if (isActual) append("actual ")
        if (isExpect) append("expect ")
        append("enum class $name")
        if (parameters.isNotEmpty()) append("(${parameters.joinToString(", ")})")
        if (extends.isNotEmpty()) {
            append(": ")
            append(extends.joinToString(", "))
        }
        append(" {\n\t")
        append(values.joinToString(",\n\t"))
        append(";\n")

        if (extra != null) append(extra)

        append("}\n")
    }.toString()
}