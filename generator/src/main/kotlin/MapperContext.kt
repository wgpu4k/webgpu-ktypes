import domain.Enumeration
import domain.Interface
import domain.TypeAlias

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

        // Flag are Long type to keep native compatibility
        typeAliases
            .filter { it.name.endsWith("Flags") }
            .forEach { typeAlias -> typeAlias.type = "ULong" }

    }

    val interfaces = mutableListOf<Interface>()
    val typeAliases = mutableListOf<TypeAlias>()
    var commonEnumerations = emptyList<Enumeration>()
    var commonWebEnumerations = emptyList<Enumeration>()
    var commonNativeEnumerations = emptyList<Enumeration>()
}

