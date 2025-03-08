import de.fabmax.webidl.model.IdlModel
import de.fabmax.webidl.parser.WebIdlParser
import mapper.loadEnums
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
    val context = MapperContext()

    context.loadTypeDef(idlModel)
    context.loadInterfaces(idlModel.interfaces)
    context.loadDictionaries(idlModel.dictionaries)
    context.loadEnums(idlModel, yamlModel)

    //model.listTypes().joinToString(",").let { println(it) }
    context.adaptToGuidelines()


    commonSourcePath.createSourceFile("enumerations.kt") {
        appendText(context.commonEnumerations.joinToString("\n"))
    }

    commonWebSourcePath.createSourceFile("enumerations.kt") {
        appendText(context.commonWebEnumerations.joinToString("\n"))
    }

    commonNativeSourcePath.createSourceFile("enumerations.kt") {
        appendText(context.commonNativeEnumerations.joinToString("\n"))
    }

    commonSourcePath.createSourceFile("types.kt") {
        appendText(context.typeAliases.joinToString("\n"))
        appendText("\n\n")
        appendText(context.interfaces.joinToString("\n"))
    }
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

