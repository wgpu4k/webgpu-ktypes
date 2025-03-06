package domain

class Enumeration(val name: String, val values: List<String>) {
    override fun toString(): String = StringBuilder().apply {
        append("enum class $name {\n\t")
        append(values.joinToString(",\n\t"))
        append(";\n")
        append("}\n")
    }.toString()
}