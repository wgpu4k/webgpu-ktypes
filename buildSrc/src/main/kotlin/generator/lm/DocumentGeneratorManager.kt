package generator.lm

import generator.MapperContext
import org.jsoup.Jsoup
import java.nio.file.Path

class DocumentGeneratorManager(
    private val context: MapperContext,
    htmlDocumentation: Path
) {
    private val body = Jsoup.parse(htmlDocumentation.toFile(), "UTF-8")
        .select("main")
        .first()
        ?.also { it.select("script").remove() }
        ?: error("fail to parse html")

    private val llmClient = LLMClient()
    private val documentationExplorerAgent = DocumentationExplorerAgent(llmClient)
    private val documentationWriterAgent = DocumentationWriterAgent(llmClient)

    fun inferDocumentation() {
        val documentation = mutableMapOf<String, String>()
        context.interfaces.forEach { kInterface ->
            val name = kInterface.name.lowercase()
            val htmlDocumentation = body.select("dfn[id=dictdef-$name]")
                .first()!!
                .parent()
            println("$name htmlDocumentation: $htmlDocumentation")
        }
    }
}