import com.charleskorn.kaml.Yaml
import domain.YamlModel

fun loadWebGPUYaml() = readFileFromClasspath("webgpu.yml")
    .let { text -> parser.decodeFromString(YamlModel.serializer(), text) }

val parser = Yaml(
    configuration = Yaml.default.configuration.copy(strictMode = false)
)

fun readFileFromClasspath(fileName: String): String {
    val classLoader = Thread.currentThread().contextClassLoader
    return classLoader.getResourceAsStream(fileName)?.use { inputStream ->
        inputStream.bufferedReader().use { it.readText() }
    } ?: error("File not found: $fileName")
}

