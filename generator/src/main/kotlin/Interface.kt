class Interface(
    val name: String,
    val sealed: Boolean = false
) {
    var extends: Set<String> = emptySet()
    var attributes: List<Attribute> = emptyList()

    class Attribute(
        val name: String,
        val type: String
    )
}