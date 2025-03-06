package domain

class Enumeration(val name: String, val values: List<String>) {
    override fun toString(): String {
        return "enum class $name"
    }
}