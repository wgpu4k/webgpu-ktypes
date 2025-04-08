package generator.domain

class KDoc(val description: String, val indentation: Int = 0)  {

    override fun toString(): String = StringBuilder().apply {
        val indentation = getIndentation()
        append("$indentation/**\n")
        append("$indentation * ${description.split("\n").joinToString("\n$indentation * ")}\n")
        append("$indentation */\n")
    }.toString()

    fun getIndentation() = "\t".repeat(indentation)
}