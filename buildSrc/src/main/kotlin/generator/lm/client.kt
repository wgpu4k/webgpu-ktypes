package generator.lm

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.call.body
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.sse.*
import io.ktor.client.plugins.sse.sse
import io.ktor.client.plugins.timeout
import io.ktor.client.request.*
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.*
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json

class LLMClient(
    private val baseUrl: String = "http://127.0.0.1:1234/v1",
    private val apiKey: String? = null
) {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }
    }

    suspend fun chatCompletion(
        messages: List<Message>,
        model: String = "mistral-small-3.1-24b-instruct-2503",
        maxTokens: Int = 1000,
        temperature: Double = 0.1,
        topP: Double = 1.0,
        n: Int = 1,
        stop: List<String>? = null,
        presencePenalty: Double = 0.0,
        frequencyPenalty: Double = 0.0,
        user: String? = null
    ): ChatCompletionResponse {
        val request = ChatCompletionRequest(
            model = model,
            messages = messages,
            max_tokens = maxTokens,
            temperature = temperature,
            top_p = topP,
            n = n,
            stream = false,
            stop = stop,
            presence_penalty = presencePenalty,
            frequency_penalty = frequencyPenalty,
            user = user
        )

        return client.post("$baseUrl/chat/completions") {
            timeout { HttpTimeoutConfig(requestTimeoutMillis = 60_000) }
            contentType(ContentType.Application.Json)
            apiKey?.let { header("Authorization", "Bearer $it") }
            setBody(request)
        }.body()
    }

    // Méthode pratique pour les cas simples
    suspend fun generateCompletion(
        prompt: String,
        model: String = "mistral-small-3.1-24b-instruct-2503",
        maxTokens: Int = 150
    ): String {
        val messages = listOf(Message("user", prompt))
        val response = chatCompletion(
            messages = messages,
            model = model,
            maxTokens = maxTokens
        )
        return response.choices.firstOrNull()?.message?.content ?: "Pas de réponse"
    }

    // Méthode pour le streaming
    fun streamChatCompletion(
        messages: List<Message>,
        model: String = "mistral-small-3.1-24b-instruct-2503",
        maxTokens: Int = 150,
        temperature: Double = 0.7,
        topP: Double = 1.0
    ): Flow<String> = flow {
        val request = ChatCompletionRequest(
            model = model,
            messages = messages,
            max_tokens = maxTokens,
            temperature = temperature,
            top_p = topP,
            stream = true
        )



        client.sse("$baseUrl/chat/completions", {
            io.ktor.client.request.HttpRequestBuilder().apply {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
        }) {
            while (true) {
                incoming.collect { event ->
                    println("Event from server:")
                    println(event)
                }
            }
        }

        /*response.bodyAsText().lineSequence()
            //.filter { it.startsWith("data:") && it.contains("{") }
            //.map { it.removePrefix("data:").trim() }
            //.filter { it.isNotBlank() && it != "[DONE]" }
            .forEach { line ->
                print(line)
                try {
                    val chunk = Json.decodeFromString<ChatCompletionChunk>(line)
                    chunk.choices.firstOrNull()?.delta?.content?.let { content ->
                        if (content.isNotEmpty()) {
                            emit(content)
                        }
                    }
                } catch (e: Exception) {
                    // Ignorer les lignes malformées
                }
            }*/
    }

    // Support des embeddings
    suspend fun createEmbeddings(
        input: List<String>,
        model: String = "text-embedding-ada-002"
    ): EmbeddingResponse {
        val request = EmbeddingRequest(
            model = model,
            input = input
        )

        return client.post("$baseUrl/embeddings") {
            contentType(ContentType.Application.Json)
            apiKey?.let { header("Authorization", "Bearer $it") }
            setBody(request)
        }.body()
    }

    // Support de la modération de contenu
    suspend fun createModeration(
        input: String,
        model: String = "text-moderation-latest"
    ): ModerationResponse {
        val request = ModerationRequest(
            input = input,
            model = model
        )

        return client.post("$baseUrl/moderations") {
            contentType(ContentType.Application.Json)
            apiKey?.let { header("Authorization", "Bearer $it") }
            setBody(request)
        }.body()
    }

    // Support des modèles disponibles
    suspend fun listModels(): ModelsResponse {
        return client.get("$baseUrl/models") {
            apiKey?.let { header("Authorization", "Bearer $it") }
        }.body()
    }
}
