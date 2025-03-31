import de.fabmax.webidl.parser.WebIdlParser
import files.RemoteFileManager
import generator.MapperContext
import generator.loadDictionaries
import generator.loadInterfaces
import generator.loadTypeDef
import generator.loadWebGPUYaml
import generator.mapper.loadDescriptors
import generator.mapper.loadEnums
import generator.mapper.loadWebInterfaces
import java.io.SequenceInputStream
import java.nio.file.Files

object ModelGenerator {

    private val idlExtraTyps = """
        interface mixin NavigatorGPU {
        };
    
        interface Navigator {
        };
    
        interface WorkerNavigator {
        };
    """.byteInputStream()

    val context: MapperContext by lazy {
        RemoteFileManager.checkCache()
        val ildPath = RemoteFileManager.findFilePath(RemoteFileManager.Files.webgpuIdl) ?: error("fail to get cached file")

        val idlModel = WebIdlParser.parseFromInputStream(
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


}

