class MapperContext {
    fun adaptToGuidelines() {

        interfaces.find { it.name == "GPUDevice" }!!.apply {
            // TODO move them to device descriptor
            attributes = attributes.filter { it.name !in listOf("lost", "onuncapturederror") }
        }

        interfaces.find { it.name == "GPUDeviceDescriptor" }!!.apply {
            attributes.find { it.name == "requiredLimits" }!!.apply {
                this.type = "GPUSupportedLimits"
            }
        }

    }

    val interfaces = mutableListOf<Interface>()
    val typeAliases = mutableListOf<TypeAlias>()
    var enumerations = emptyList<Enumeration>()
}