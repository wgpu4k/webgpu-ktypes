import jdk.javadoc.internal.doclets.formats.html.markup.HtmlStyle.returnType

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
        val parameters: List<Parameter>,
        val isSuspend: Boolean
    ) {

        class Parameter(val name: String, val type: String, val defaultValue: String? = null) {
            override fun toString(): String = StringBuilder().apply {
                append("$name: $type")
                if (defaultValue != null) append(" = $defaultValue")
            }.toString()
        }

        override fun toString(): String = StringBuilder().apply {
            append("\t")
            if (isSuspend) append("suspend ")
            append("fun $name(")
            append(parameters.joinToString(", "))
            append(")")
            if (returnType != "Unit") append(": $returnType")
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
            if (attributes.isNotEmpty() || methods.isNotEmpty()) append(" {\n")
            append(attributes.joinToString("\n"))
            if (attributes.isNotEmpty()) append("\n")
            append(methods.joinToString("\n"))
            if (methods.isNotEmpty()) append("\n")
            if (attributes.isNotEmpty() || methods.isNotEmpty()) append("}\n")
        }
        return builder.toString()
    }
}