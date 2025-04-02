package generator.domain

class DescriptorClass(val name: String, val parameter: List<Parameter>) {

    class Parameter(val name: String, var type: String, var defaultValue: String? = null) {

        override fun toString(): String = StringBuilder().apply {
            append("override val $name: $type")
            if (defaultValue != null) append(" = $defaultValue")
        }.toString()
    }

    override fun toString(): String = StringBuilder().apply {
        append("data class ${name.removePrefix("GPU")}(\n")
        (parameter.filter { it.defaultValue == null } +
        parameter.filter { it.defaultValue != null }).let {
            append(it.joinToString(",\n") { "\t$it" })
        }
        append("\n): $name\n")
    }.toString()
}