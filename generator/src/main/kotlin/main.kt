import de.fabmax.webidl.model.IdlModel
import de.fabmax.webidl.parser.WebIdlParser
import mapper.loadEnums
import mapper.loadDescriptors
import java.io.File
import java.io.SequenceInputStream
import java.net.URI
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.createDirectories

val idlExtraTyps = """
    interface mixin NavigatorGPU {
    };

    interface Navigator {
    };

    interface WorkerNavigator {
    };
""".byteInputStream()

val descriptorCommonSourcePath = Paths.get("webgpu-ktypes-descriptors").resolve("src").resolve("commonMain").resolve("kotlin")
val commonSourcePath = Paths.get("webgpu-ktypes").resolve("src").resolve("commonMain").resolve("kotlin")
val commonWebSourcePath = Paths.get("webgpu-ktypes").resolve("src").resolve("commonWebMain").resolve("kotlin")
val commonNativeSourcePath = Paths.get("webgpu-ktypes").resolve("src").resolve("commonNativeMain").resolve("kotlin")

val webgpuHtmlPath = Paths.get("webgpu.html")
val webgpuHtmlUrl = URI("https://www.w3.org/TR/webgpu/").toURL()
val webgpuIdlPath = Paths.get("webgpu.idl")
val webgpuIdlUrl = URI("https://gpuweb.github.io/gpuweb/webgpu.idl").toURL()

fun main() {
    webgpuHtmlUrl.downloadToPath(webgpuHtmlPath)
    webgpuIdlUrl.downloadToPath(webgpuIdlPath)

    val idlModel = WebIdlParser.parseFromInputStream(
        SequenceInputStream(idlExtraTyps, Files.newInputStream(webgpuIdlPath))
    )
    val yamlModel = loadWebGPUYaml()
    val context = MapperContext(idlModel, yamlModel)

    context.loadTypeDef()
    context.loadInterfaces()
    context.loadDictionaries()
    context.loadEnums()
    context.loadDescriptors()

    //model.listTypes().joinToString(",").let { println(it) }
    context.adaptToGuidelines()

    commonSourcePath.createSourceFile("bitflags.kt") {
        appendText(context.bitflagEnumerations.joinToString("\n"))
    }

    commonSourcePath.createSourceFile("enumerations.kt") {
        appendText(context.commonEnumerations.joinToString("\n"))
    }

    commonWebSourcePath.createSourceFile("enumerations.kt") {
        appendText(context.commonWebEnumerations.joinToString("\n"))
    }

    commonNativeSourcePath.createSourceFile("enumerations.kt") {
        appendText(context.commonNativeEnumerations.joinToString("\n"))
    }

    commonSourcePath.createSourceFile("typealiases.kt") {
        appendText(context.typeAliases.joinToString("\n"))
    }

    commonSourcePath.createSourceFile("interfaces.kt") {
        appendText(context.interfaces.joinToString("\n"))
    }

    descriptorCommonSourcePath.createSourceFile("descriptor.kt") {
        appendText(context.descriptors.joinToString("\n"))
    }

    /*context.commonEnumerations.forEach { enumeration ->
        println("@Deprecated(\"Use ${enumeration.name} instead\")")
        println("typealias ${enumeration.name.removePrefix("GPU")} = ${enumeration.name}")
    }*/

}

private fun Path.createSourceFile(fileName: String, block: File.() -> Unit) {
    createDirectories()
    resolve(fileName).toFile().apply {
        delete()
        createNewFile()

        appendText("@file:Suppress(\"unused\")\n")
        appendText("// This file has been generated DO NO EDIT\n")
        appendText("package io.ygdrasil.webgpu\n\n")
        block()
    }
}

private fun URL.downloadToPath(paths: Path) {
    if (Files.exists(paths)) return
    runCatching {
        openStream().use { inputStream ->
            Files.newOutputStream(paths).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
    }.onFailure { error(it)}
}

private fun IdlModel.listTypes(): Set<String> = (enums.map { it.name } +
        interfaces.map { it.name } +
        typeDefs.map { it.name } +
        dictionaries.map { it.name })
    .map { it.fixName() }
    .filter { it !in webUnwantedTypes }
    .toSet()

