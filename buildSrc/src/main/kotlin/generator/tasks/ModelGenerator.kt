package generator.tasks

import com.charleskorn.kaml.Yaml
import generator.domain.MapperContext
import generator.files.RemoteFileManager
import generator.mapper.injectDocumentation
import generator.mapper.loadDescriptors
import generator.mapper.loadDictionaries
import generator.mapper.loadEnums
import generator.mapper.loadInterfaces
import generator.mapper.loadTypeDef
import generator.mapper.loadWebGPUYaml
import generator.mapper.loadWebInterfaces
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import java.io.SequenceInputStream
import java.nio.file.Files

class ModelGenerator(
    val remoteFileManager: RemoteFileManager,
) {

    private val idlExtraTyps = """
        interface mixin NavigatorGPU {
        };
    
        interface Navigator {
        };
    
        interface WorkerNavigator {
        };
    """.byteInputStream()

    val context: MapperContext by lazy {
        val ildPath = remoteFileManager.findFilePath(RemoteFileManager.Files.webgpuIdl) ?: error("fail to get cached file")

        val idlModel = de.fabmax.webidl.parser.WebIdlParser.Companion.parseFromInputStream(
            SequenceInputStream(idlExtraTyps, Files.newInputStream(ildPath))
        )
        val yamlModel = loadWebGPUYaml()

        MapperContext(idlModel, yamlModel).apply {
            loadTypeDef()
            loadInterfaces()
            loadDictionaries()
            loadEnums()
            loadDescriptors()
            loadWebInterfaces()

            adaptToGuidelines()
        }
    }

    fun injectDocumentation() {
        val yamlFile = remoteFileManager.specificationsSourcePath.resolve(RemoteFileManager.Files.documentationYaml).toFile()
        val yamlContent = yamlFile.readText()
        val yamlMap = Yaml.default.decodeFromString(MapSerializer(String.serializer(), String.serializer()), yamlContent)
        context.injectDocumentation(yamlMap)
    }

}

