package generator.lm

import kotlinx.serialization.Serializable


@kotlinx.serialization.Serializable
data class Message(val role: String, val content: String)

@kotlinx.serialization.Serializable
data class Usage(
    val prompt_tokens: Int,
    val completion_tokens: Int,
    val total_tokens: Int
)

@kotlinx.serialization.Serializable
data class ChatCompletionResponse(
    val id: String,
    val `object`: String,
    val created: Long,
    val model: String,
    val choices: List<Choice>,
    val usage: Usage
)

@kotlinx.serialization.Serializable
data class Choice(
    val message: Message,
    val index: Int,
    val finish_reason: String
)

@kotlinx.serialization.Serializable
data class ChatCompletionRequest(
    val model: String,
    val messages: List<Message>,
    val max_tokens: Int = 150,
    val temperature: Double = 0.7,
    val top_p: Double = 1.0,
    val n: Int = 1,
    val stream: Boolean = false,
    val stop: List<String>? = null,
    val presence_penalty: Double = 0.0,
    val frequency_penalty: Double = 0.0,
    val user: String? = null
)

@kotlinx.serialization.Serializable
data class ChatCompletionChunk(
    val id: String,
    val `object`: String,
    val created: Long,
    val model: String,
    val choices: List<ChunkChoice>
)

@kotlinx.serialization.Serializable
data class ChunkChoice(
    val delta: Delta,
    val index: Int,
    val finish_reason: String? = null
)

@kotlinx.serialization.Serializable
data class Delta(
    val role: String? = null,
    val content: String? = null
)
@kotlinx.serialization.Serializable
data class EmbeddingRequest(
    val model: String,
    val input: List<String>
)

@kotlinx.serialization.Serializable
data class EmbeddingResponse(
    val data: List<Embedding>,
    val model: String,
    val usage: Usage
)

@kotlinx.serialization.Serializable
data class Embedding(
    val embedding: List<Float>,
    val index: Int
)

@kotlinx.serialization.Serializable
data class ModerationRequest(
    val input: String,
    val model: String
)

@kotlinx.serialization.Serializable
data class ModerationResponse(
    val id: String,
    val model: String,
    val results: List<ModerationResult>
)

@kotlinx.serialization.Serializable
data class ModerationResult(
    val flagged: Boolean,
    val categories: Map<String, Boolean>,
    val category_scores: Map<String, Double>
)

@kotlinx.serialization.Serializable
data class ModelsResponse(
    val data: List<ModelInfo>,
    val `object`: String
)

@kotlinx.serialization.Serializable
data class ModelInfo(
    val id: String,
    val created: Long? = null,
    val owned_by: String? = null,
    val `object`: String? = null
)
