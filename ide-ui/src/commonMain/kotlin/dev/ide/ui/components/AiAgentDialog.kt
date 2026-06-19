package dev.ide.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AiAgentDialog(
    onDismiss: () -> Unit,
    onSaveSettings: (provider: String, apiKey: String, url: String) -> Unit
) {
    var apiKey by remember { mutableStateOf("") }
    var selectedProvider by remember { mutableStateOf("Gemini") }
    var apiUrl by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("AI Assistant Settings") },
        text = {
            Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                Text("Select Provider:")
                OutlinedTextField(
                    value = selectedProvider,
                    onValueChange = { selectedProvider = it },
                    label = { Text("Enter Provider") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = apiUrl,
                    onValueChange = { apiUrl = it },
                    label = { Text("Enter API URL") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = apiKey,
                    onValueChange = { apiKey = it },
                    label = { Text("Enter API Key") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = { onSaveSettings(selectedProvider, apiKey, apiUrl) }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
