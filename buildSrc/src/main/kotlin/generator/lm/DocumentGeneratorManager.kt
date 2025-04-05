package generator.lm

import generator.domain.Interface
import generator.domain.MapperContext
import generator.files.RemoteFileManager
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.nio.file.Path

private val prettyJson = Json {
    prettyPrint = true
}

class DocumentGeneratorManager(
    private val context: MapperContext,
    private val remoteFileManager: RemoteFileManager,
    htmlDocumentation: Path
) {
    private val body = Jsoup.parse(htmlDocumentation.toFile(), "UTF-8")
        ?: error("fail to parse html")

    private val currentDocumentation = mutableMapOf<String, String>()

    private val llmClient = LLMClient()
    private val documentationExplorerAgent = DocumentationExplorerAgent(llmClient)
    private val documentationWriterAgent = DocumentationWriterAgent(llmClient)
    private val jSonRefinerAgent = JSonRefinerAgent(llmClient)

    val documentationFile = remoteFileManager.specificationsSourcePath.resolve(RemoteFileManager.Files.documentation)

    fun inferHtmlDocumentation() = runBlocking {
       val documentation = mutableMapOf<String, String>()
        context.interfaces.forEach { kInterface ->
            runCatching {
                val name = kInterface.name.lowercase()
                val htmlNode = findRootNode(name) ?: error("fail to find root node for declaration $name")
                val htmlDocumentation = inferHtmlDocumentation(htmlNode, name)
                val kdocDocumentation = inferKdocDocumentation(kInterface, htmlDocumentation)
                currentDocumentation += kdocDocumentation
                val jsonString = prettyJson.encodeToString(currentDocumentation)
                java.nio.file.Files.write(documentationFile, jsonString.toByteArray())
            }
        }

        println(currentDocumentation)
    }

    private suspend fun inferKdocDocumentation(
        kInterface: Interface,
        htmlDocumentation: MutableList<Element>
    ): Map<String, String> {
        val userPrompt = """
            Provide only the documentation for the kotlin code and skip the rest.
            This is the kotlin code :
            
            ```kotlin
            $kInterface
            ```
                    
            This is the HTML specification
            $htmlDocumentation
                       
            """.trimIndent()

        println("infer: $kInterface")


        var responseAsJson = documentationWriterAgent.generateDocumentation(userPrompt)
            .getOrThrow()

        var remainingTry = 5
        while (remainingTry > 0) {
            remainingTry--
            runCatching {
                Json.Default.decodeFromString<Map<String, String>>(responseAsJson)
                    .let { return it }
            }
            responseAsJson = jSonRefinerAgent.refine(responseAsJson)
                .getOrThrow()
            println("refined json: $responseAsJson")
        }


        error("fail to get response")
    }

    private suspend fun inferHtmlDocumentation(htmlNode: Element, subject: String): MutableList<Element> {
        val selectedDocumentation = mutableListOf(htmlNode)

        // Parse before
        var shouldContinue = true
        var currentElement = htmlNode.previousElementSibling()
        /*println("Parse before $currentElement")
        while (shouldContinue && currentElement != null) {
            shouldContinue = documentationExplorerAgent.isRelevant(
                selectedDocumentation.toString(),
                currentElement.toString(),
                subject
            ).map { it.lowercase() == "yes" }.getOrElse { false }

            if (shouldContinue) {
                selectedDocumentation.addFirst(currentElement)
                currentElement = currentElement.previousElementSibling()
            }
            println("shouldContinue: $shouldContinue and currentElement is null ?: ${currentElement == null}")
        }*/
        // Parse after
        shouldContinue = true
        currentElement = htmlNode.nextElementSibling()
        while (shouldContinue && currentElement != null) {
            shouldContinue = documentationExplorerAgent.isRelevant(
                selectedDocumentation.toString(),
                currentElement.toString(),
                subject
            ).map { it.lowercase() == "yes" }.getOrElse { false }

            if (shouldContinue) {
                selectedDocumentation.add(currentElement)
                currentElement = currentElement.nextElementSibling()
            }
            println("shouldContinue: $shouldContinue")
        }

        return selectedDocumentation
    }

    private fun findRootNode(name: String): Element? = (body.select("dfn[id=dictdef-$name]").first()
        ?: body.select("dfn[id=typedefdef-$name]").first()
        ?: body.select("a[href=#$name]").first()
            )?.findRootNode()
}

private fun Element.findRootNode(): Element? = parent().let {
    when(it?.tagName()) {
        null, "main" -> this
        else -> it.findRootNode()
    }
}
