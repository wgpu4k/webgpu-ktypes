import de.fabmax.webidl.model.IdlModel
import domain.Enumeration
import domain.Interface
import domain.TypeAlias
import domain.YamlModel
import mapper.convertToKotlinClassName
import org.gradle.internal.impldep.kotlinx.metadata.KmClassifier

class MapperContext(
    val idlModel: IdlModel,
    val yamlModel: YamlModel
) {

    val interfaces = mutableListOf<Interface>()
    val typeAliases = mutableListOf<TypeAlias>()
    var commonEnumerations = emptyList<Enumeration>()
    var commonWebEnumerations = emptyList<Enumeration>()
    var commonNativeEnumerations = emptyList<Enumeration>()
    var bitflagEnumerations = emptyList<Enumeration>()
    val descriptors = mutableListOf<domain.DescriptorClass>()

    fun adaptToGuidelines() {

        interfaces.find { it.name == "GPUDevice" }!!.apply {
            // TODO move them to device descriptor
            attributes = attributes.filter { it.name !in listOf("lost", "onuncapturederror") }
        }

        // If interface contains destroy, we set it as AutoCloseable
        interfaces.forEach { kinterface ->
            if (kinterface.methods.any { it.name == "destroy" }) {
                kinterface.methods = kinterface.methods.filter { it.name != "destroy" }
                kinterface.extends += "AutoCloseable"
            }
        }

        // Add AutoCloseable trait to specific classes as they require to be release on native
        interfaces.filter { it.name in interfaceToAddAutocloseableTrait }
            .forEach { it.extends += "AutoCloseable" }

        // Change GPUDeviceDescriptor#requiredLimits type to GPUSupportedLimits?
        descriptors.first { it.name == "GPUDeviceDescriptor" }
            .also { descriptor ->
                descriptor.parameter.first { it.name == "requiredLimits" }.apply {
                    type = "GPUSupportedLimits?"
                    defaultValue = "null"
                }
            }

        interfaces.find { it.name == "GPUDeviceDescriptor" }!!.apply {
            attributes.find { it.name == "requiredLimits" }!!.apply {
                this.type = "GPUSupportedLimits?"
            }
        }

        // Convert setlike to typealias
        val setLikes = idlModel.interfaces
            .filter { it.setLike != null }
            .map { it.name }
        interfaces.toList()
            .forEach {
            if ( it.name in setLikes) {
                interfaces -= it
            }
        }

        typeAliases += TypeAlias("GPUSupportedFeatures", "Set<GPUFeatureName>")
        typeAliases.filter { it.name.endsWith("Flags") }
            .forEach { it.type = "Set<${it.name.removeSuffix("Flags")}>" }

        interfaces.first { it.name == "GPUBuffer" }.apply {
            attributes.find { it.name == "usage" }!!.apply {
                type = "GPUBufferUsageFlags"
            }
        }
        interfaces.first { it.name == "GPUTexture" }.apply {
            attributes.find { it.name == "usage" }!!.apply {
                type = "GPUTextureUsageFlags"
            }
        }

        bitflagEnumerations.first { it.name == "GPUColorWriteMask" }.apply {
            name = "GPUColorWrite"
        }

        descriptors.first { it.name == "GPUColorTargetState" }.apply {
            parameter.first { it.name == "writeMask" }.apply { defaultValue = "setOf(GPUColorWrite.All)"}
        }
        descriptors.first { it.name == "GPUTextureViewDescriptor" }.apply {
            parameter.first { it.name == "usage" }.apply { defaultValue = "emptySet()"}
        }

    }

    fun isEnumeration(typeName: String) = idlModel.enums.any { it.name == typeName }

    fun getEnumerationValueNameOnKotlin(typeName: String, value: String): String {
        val fixedValue = value
            .replace("\"", "")
            .replace("-", "")
            .fixNameStartingWithNumeric()
            .lowercase()
        return commonEnumerations.find { it.name == typeName }
            ?.let { enum -> enum.values.find { it.lowercase() == fixedValue }}
            ?: error("enumeration not found with type $typeName and value $fixedValue")
    }

    fun MapperContext.isUnsignedNumericType(type: String): Boolean {
        return (typeAliases.find { it.name == type }
            ?.let { isUnsignedNumericType(it.type) })
            ?: (type in listOf("UInt", "ULong", "UShort"))
    }

    fun MapperContext.isFloatType(type: String): Boolean {
        return (typeAliases.find { it.name == type }
            ?.let { isUnsignedNumericType(it.type) })
            ?: (type in listOf("Float"))
    }
}


internal fun String.fixNameStartingWithNumeric(): String {
    return if (first().isDigit()) {
        when (first()) {
            '1' -> "One${substring(1)}"
            '2' -> "Two${substring(1)}"
            '3' -> "Three${substring(1)}"
            '4' -> "Four${substring(1)}"
            '5' -> "Five${substring(1)}"
            '6' -> "Six${substring(1)}"
            '7' -> "Seven${substring(1)}"
            '8' -> "Eight${substring(1)}"
            '9' -> "Nine${substring(1)}"
            '0' -> "Zero${substring(1)}"
            else -> error("Invalid name starting with numeric: $this")
        }
    } else this
}

private val interfaceToAddAutocloseableTrait = listOf(
    "GPUAdapter", "GPUBindGroup", "GPUBindGroupLayout", "GPUCommandBuffer", "GPUComputePipeline", "GPUPipelineLayout",
    "GPURenderPipeline", "GPUSampler", "GPUShaderModule", "GPUTextureView", "GPURenderBundleEncoder", "GPUCommandEncoder"
)