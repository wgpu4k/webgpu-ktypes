package generator.domain

class KDoc(val description: String)  {

    override fun toString(): String {
        return """
/**
 * ${description.split("\n").joinToString("\n * ")}
 */
"""
    }
}