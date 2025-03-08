package domain

class DescriptorClass(val name: String, val parameter: List<Parameter>) {

    class Parameter(val name: String, val type: String, val defaultValue: String? = null) {

        override fun toString(): String = StringBuilder().apply {
            append("override val $name: $type")
            if (defaultValue != null) append(" = $defaultValue")
        }.toString()
    }

    override fun toString(): String = StringBuilder().apply {
        append("data class ${name.removePrefix("GPU")}(")
        append(parameter.joinToString(", "))
        append("): $name")
    }.toString()
}