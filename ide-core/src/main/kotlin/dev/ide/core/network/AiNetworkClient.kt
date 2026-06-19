package dev.ide.core.network

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class AiNetworkClient {
    private val client = HttpClient()

    suspend fun sendCodeToAi(
        apiUrl: String,
        apiKey: String,
        provider: String,
        prompt: String,
        fileContent: String
    ): String {
        if (apiUrl.isBlank() || apiKey.isBlank()) {
            return "Ошибка: Не задан URL или API-ключ в настройках ИИ."
        }

        return try {
            val escapedContent = fileContent
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "")

            val response: HttpResponse = client.post(apiUrl) {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $apiKey")
                    append(HttpHeaders.ContentType, "application/json")
                }
                
                val jsonBody = """
                    {
                        "model": "gpt-4o-mini",
                        "messages": [
                            {"role": "system", "content": "You are a helpful coding assistant inside an IDE."},
                            {"role": "user", "content": "$prompt\n\nCode:\n$escapedContent"}
                        ]
                    }
                """.trimIndent()
                
                setBody(jsonBody)
            }

            if (response.status.value in 200..299) {
                response.bodyAsText()
            } else {
                "Ошибка сервера: ${response.status.value} - ${response.bodyAsText()}"
            }
        } catch (e: Exception) {
            "Ошибка сети: ${e.message}"
        }
    }
}
