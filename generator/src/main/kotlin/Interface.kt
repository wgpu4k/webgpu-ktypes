class Interface(
    val name: String,
    val sealed: Boolean = false
) {
    var extends: Set<String> = emptySet()
    var attributes: List<Attribute> = emptyList()
    var methods: List<Method> = emptyList()

    class Method(
        val name: String,
        val returnType: String,
        val parameters: List<Pair<String, String>>,
        val isSuspend: Boolean
    ) {
        override fun toString(): String = StringBuilder().apply {
            append("\tfun $name(")
            append(parameters.joinToString(", ") { (name, type) -> "$name: $type" })
            append("): $returnType")
        }.toString()
    }

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
            append(methods.joinToString("\n"))
            if (methods.isNotEmpty()) append("\n")
            append(attributes.joinToString("\n"))
            if (attributes.isNotEmpty()) append("\n")
            append("}\n")
        }
        return builder.toString()
    }
}