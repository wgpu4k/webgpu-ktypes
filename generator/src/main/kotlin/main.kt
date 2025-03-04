import de.fabmax.webidl.model.IdlModel
import de.fabmax.webidl.parser.WebIdlParser
import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.text.contains

val webUnwantedTypes = setOf(
    // Types de navigateur
    "NavigatorGPU",
    "Navigator",
    "WorkerNavigator",

    // Types spécifiques au canvas web
    "GPUCanvasContext",
    "GPUCanvasConfiguration",
    "GPUCanvasAlphaMode",
    "GPUCanvasToneMappingMode",
    "GPUCanvasToneMapping",

    // Types dictionnaires redondants
    "GPUColorDict",
    "GPUOrigin2DDict",
    "GPUOrigin3DDict",
    "GPUExtent3DDict",

    // Types d'événements web
    "GPUUncapturedErrorEvent",
    "GPUUncapturedErrorEventInit",

    // Types liés aux workers web
    "GPUExternalTexture",
    "GPUExternalTextureDescriptor",
    "GPUExternalTextureBindingLayout",
    "GPUCopyExternalImageSource",
    "GPUCopyExternalImageDestInfo",
    "GPUCopyExternalImageSourceInfo"
)

val baseSourcePath = Paths.get("webgpu-ktypes").resolve("src").resolve("commonMain").resolve("kotlin")
val typesPath = baseSourcePath.resolve("types.kt")

fun main() {
    // Charger le fichier à partir des ressources
    val resource = {}.javaClass.getResource("/webgpu.idl")
        ?:error("Failed to load webgpu.idl from resources")

    // Parse le contenu du fichier avec `WebIdlParser`
    val model = WebIdlParser.parseFromInputStream(resource.openStream())

    //model..listTypes().joinToString(",").let { println(it) }

    //downloadWebgpuDoc()

    typesPath.toFile().apply {
        createNewFile()
    }
}

private fun IdlModel.listTypes(): Set<String> = (enums.map { it.name } +
        interfaces.map { it.name } +
        typeDefs.map { it.name } +
        dictionaries.map { it.name })
    .map { if (it.contains(':')) it.substringBefore(':') else it }
    .map { it.replace("\n", "").trim() }
    .filter { it !in webUnwantedTypes }
    .toSet()

fun downloadWebgpuDoc() {
    // Télécharger le fichier depuis l'URL
    val url = "https://www.w3.org/TR/webgpu/"
    val filePath = Paths.get("webgpu.html")

    runCatching {
        URL(url).openStream().use { inputStream ->
            Files.newOutputStream(filePath).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }

        println("Downloaded at : ${filePath.toAbsolutePath()}")
    }

    try {
        URL(url).openStream().use { inputStream ->
            Files.newOutputStream(filePath).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        println("Fichier téléchargé et enregistré sous : ${filePath.toAbsolutePath()}")
    } catch (e: Exception) {
        println("Erreur lors du téléchargement ou de l'enregistrement du fichier : ${e.message}")
    }
}

