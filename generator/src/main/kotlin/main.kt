import de.fabmax.webidl.model.IdlModel
import de.fabmax.webidl.parser.WebIdlParser
import java.io.SequenceInputStream
import java.net.URI
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

val idlExtraTyps = """
    interface mixin NavigatorGPU {
    };

    interface Navigator {
    };

    interface WorkerNavigator {
    };
""".byteInputStream()



val baseSourcePath = Paths.get("webgpu-ktypes").resolve("src").resolve("commonMain").resolve("kotlin")
val typesPath = baseSourcePath.resolve("types.kt")


val webgpuHtmlPath = Paths.get("webgpu.html")
val webgpuHtmlUrl = URI("https://www.w3.org/TR/webgpu/").toURL()
val webgpuIdlPath = Paths.get("webgpu.idl")
val webgpuIdlUrl = URI("https://gpuweb.github.io/gpuweb/webgpu.idl").toURL()

fun main() {
    // Charger le fichier à partir des ressources
    val resource = webgpuIdlUrl
    webgpuHtmlUrl.downloadToPath(webgpuHtmlPath)
    webgpuIdlUrl.downloadToPath(webgpuIdlPath)

    // Parse le contenu du fichier avec `WebIdlParser`
    val model = WebIdlParser.parseFromInputStream(
        SequenceInputStream(idlExtraTyps, resource.openStream())
    )
    val context = MapperContext()
    context.loadTypeDef(model)
    context.loadInterfaces(model.interfaces)
    context.loadDictionaries(model.dictionaries)
    context.loadEnums(model.enums)

    //model.listTypes().joinToString(",").let { println(it) }
    context.adaptToGuidelines()

    typesPath.toFile().apply {
        delete()
        createNewFile()

        appendText("@file:Suppress(\"unused\")\n")
        appendText("// This file has been generated DO NO EDIT\n")
        appendText("package io.ygdrasil.webgpu\n\n")

        appendText(context.typeAliases.joinToString("\n"))
        appendText("\n\n")
        appendText(context.enumerations.joinToString("\n"))
        appendText("\n\n")
        appendText(context.interfaces.joinToString("\n"))

    }
}

private fun URL.downloadToPath(paths: Path) {
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

