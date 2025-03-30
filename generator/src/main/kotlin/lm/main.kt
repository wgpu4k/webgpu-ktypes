package lm

import kotlinx.coroutines.runBlocking
import org.jsoup.Jsoup
import java.io.File

private val client = LLMClient()
private val documentationWriterAgent = DocumentationWriterAgent(client)
private val documentationExplorerAgent = DocumentationExplorerAgent(client)

fun main() = runBlocking {

    val fichierHTML = File("webgpu.html")

    val body = Jsoup.parse(fichierHTML, "UTF-8")
        .select("main")
        .also { it.select("script").remove() }

    val htmlDocumentation = body.select("dfn[id=dictdef-gpucolordict]")
        .first()!!
        .parent()

    val selectedDocumentation = mutableListOf(htmlDocumentation)

    // Parse before
    var shouldContinue = true
    var currentElement = htmlDocumentation.previousElementSibling()
    println("Parse before $currentElement")
    while (shouldContinue && currentElement != null) {
        shouldContinue = documentationExplorerAgent.isRevelant(
            selectedDocumentation.toString(),
            currentElement.toString()
        ).map { it.lowercase() == "yes" }.getOrElse { false}

        if (shouldContinue) {
            selectedDocumentation.addFirst(currentElement)
            currentElement = currentElement.previousElementSibling()
        }
        println("shouldContinue: $shouldContinue and currentElement is null ?: ${currentElement == null}")
    }
    // Parse after
    shouldContinue = true
    currentElement = htmlDocumentation.nextElementSibling()
    println("Parse after $currentElement")
    while (shouldContinue && currentElement != null) {
        shouldContinue = documentationExplorerAgent.isRevelant(
            selectedDocumentation.toString(),
            currentElement.toString()
        ).map { it.lowercase() == "yes" }.getOrElse { false}

        if (shouldContinue) {
            selectedDocumentation.add(currentElement)
            currentElement = currentElement.nextElementSibling()
        }
        println("shouldContinue: $shouldContinue and currentElement is null ?: ${currentElement == null}")
    }



    println("doc: ${selectedDocumentation}")

    val userPrompt = """
        Provide only the documentation.
        This is the kotlin code :
        
        interface GPUColor {
            val r: Double
            val g: Double
            val b: Double
            val a: Double
        }
        
        This is the HTML specification
        $selectedDocumentation
           
    """.trimIndent()


    val responseAsJson = documentationWriterAgent.generateDocumentation(userPrompt)
        .getOrThrow()
    val responseAsMap = kotlinx.serialization.json.Json.decodeFromString<Map<String, String>>(responseAsJson)

    println("as map $responseAsMap")
}


