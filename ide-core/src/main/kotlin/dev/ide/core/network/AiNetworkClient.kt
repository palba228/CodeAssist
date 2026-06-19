package dev.ide.ui.network

expect class AiNetworkClient() {
    suspend fun sendCodeToAi(apiUrl: String, apiKey: String, provider: String, prompt: String, fileContent: String): String
}

