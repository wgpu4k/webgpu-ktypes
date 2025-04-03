package generator.lm

import generator.MapperContext
import org.jsoup.Jsoup
import java.nio.file.Path

class DocumentGeneratorManager(
    private val context: MapperContext,
    htmlDocumentation: Path
) {
    private val body = Jsoup.parse(htmlDocumentation.toFile(), "UTF-8")
        ?: error("fail to parse html")

    private val llmClient = LLMClient()
    private val documentationExplorerAgent = DocumentationExplorerAgent(llmClient)
    private val documentationWriterAgent = DocumentationWriterAgent(llmClient)

    fun inferDocumentation() {
       val documentation = mutableMapOf<String, String>()
        context.interfaces.forEach { kInterface ->
            val name = kInterface.name.lowercase()
            //println("will infer $name")
            val htmlNode = (body.select("dfn[id=dictdef-$name]").first()
                ?: body.select("dfn[id=typedefdef-$name]").first()
                ?: body.select("a[href=#$name]").first()
                    )?.parent()
            if (htmlNode == null) {
                println("fail to find $name")
            }
            //println("$name htmlDocumentation: $htmlNode")
        }
    }
}