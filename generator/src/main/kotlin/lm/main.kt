package lm

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val client = OpenAIClient(
        baseUrl = "http://127.0.0.1:1234/v1",
        apiKey = null // Pas nécessaire pour LM Studio local
    )

    // Exemple 1: Chat completion standard
    try {
        val messages = listOf(
            Message("system", "Tu es un assistant français utile et concis."),
            Message("user", "Explique-moi comment fonctionne l'API OpenAI en trois points.")
        )

        val response = client.chatCompletion(messages = messages)
        println("Réponse: ${response.choices.firstOrNull()?.message?.content}")
        println("Tokens utilisés: ${response.usage.total_tokens}")
    } catch (e: Exception) {
        println("Erreur: ${e.message}")
    }

    // Exemple 2: Streaming pour des réponses progressives
    try {
        val messages = listOf(
            Message("system", "Tu es un assistant français utile et concis."),
            Message("user", "Raconter une histoire courte à propos d'un robot.")
        )

        println("Réponse en streaming:")
        launch {
            client.streamChatCompletion(messages).collect { chunk ->
                print(chunk)
            }
            println("\n--- Fin du streaming ---")
        }
    } catch (e: Exception) {
        println("Erreur de streaming: ${e.message}")
    }

    // Exemple 3: Liste des modèles disponibles
    try {
        val models = client.listModels()
        println("\nModèles disponibles:")
        models.data.forEach { model ->
            println("- ${model.id}")
        }
    } catch (e: Exception) {
        println("Erreur lors de la récupération des modèles: ${e.message}")
    }

    // Attendre la fin des coroutines
    kotlinx.coroutines.delay(10000)
}
