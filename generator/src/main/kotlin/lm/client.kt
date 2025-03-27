package lm

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Message(val role: String, val content: String)

@Serializable
data class ChatCompletionRequest(
    val model: String,
    val messages: List<Message>,
    val max_tokens: Int = 150
)

@Serializable
data class Choice(val message: Message, val index: Int)

@Serializable
data class ChatCompletionResponse(val id: String, val choices: List<Choice>)

class OpenAIKtorClient() {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun generateCompletion(prompt: String, model: String = "mistral-small-3.1-24b-instruct-2503"): String {
        val response = client.post("http://127.0.0.1:1234/v1/chat/completions") {
            contentType(ContentType.Application.Json)
            setBody(ChatCompletionRequest(
                model = model,
                messages = listOf(Message("user", prompt))
            ))
        }.body<ChatCompletionResponse>()

        return response.choices.firstOrNull()?.message?.content ?: "Pas de réponse"
    }
}

fun main() = runBlocking {
    val openAIClient = OpenAIKtorClient()

    try {
        val response = openAIClient.generateCompletion("Bonjour, comment vas-tu?")
        println("Réponse: $response")
    } catch (e: Exception) {
        println("Erreur: ${e.message}")
    }
}
