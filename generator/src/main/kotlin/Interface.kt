class Interface(
    val name: String,
    val sealed: Boolean = false
) {
    var extends: Set<String> = emptySet()
    var attributes: List<Attribute> = emptyList()

    class Attribute(
        val name: String,
        var type: String,
        val isConstant: Boolean
    ) {
        override fun toString(): String = StringBuilder().apply {
            append("\t")
            if (isConstant) append("val") else append("var")
            append(" $name: $type")
        }.toString()
    }

    override fun toString(): String {
        val builder = StringBuilder().apply {
            if (sealed) append("sealed ")
            append("interface $name")
            if (extends.isNotEmpty()) {
                append(" : ")
                append(extends.joinToString(", "))
            }
            append(" {\n")
            append(attributes.joinToString("\n"))
            if (attributes.isNotEmpty()) append("\n")
            append("}\n")
        }
        return builder.toString()
    }
}