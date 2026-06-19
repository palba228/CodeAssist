package dev.ide.ui.network

import java.net.HttpURLConnection
import java.net.URL
import java.io.InputStreamReader
import java.io.BufferedReader
import java.io.OutputStreamWriter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

actual class AiNetworkClient actual constructor() {
    actual suspend fun sendCodeToAi(
        apiUrl: String, apiKey: String, provider: String, prompt: String, fileContent: String
    ): String = withContext(Dispatchers.IO) {
        if (apiUrl.isBlank() || apiKey.isBlank()) return@withContext "Ошибка: Не задан URL или API-ключ."
        try {
            val cleanContent = fileContent.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "")
            val json = """{"model": "gpt-4o-mini", "messages": [{"role": "system", "content": "You are a coding assistant."}, {"role": "user", "content": "$prompt\n\n$cleanContent"}]}"""
            
            val url = URL(apiUrl)
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "POST"
            conn.setRequestProperty("Authorization", "Bearer $apiKey")
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8")
            conn.doOutput = true
            
            OutputStreamWriter(conn.outputStream, "UTF-8").use { it.write(json); it.flush() }
            
            val status = conn.responseCode
            val stream = if (status in 200..299) conn.inputStream else conn.errorStream
            val response = BufferedReader(InputStreamReader(stream, "UTF-8")).use { it.readText() }
            
            if (status in 200..299) response else "Ошибка сервера: $status\n$response"
        } catch (e: Exception) {
            "Ошибка сети: ${e.message}"
        }
    }
}
