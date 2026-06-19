package dev.ide.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AiAgentDialog(
    onDismiss: () -> Unit,
    onSaveSettings: (provider: String, apiKey: String) -> Unit
) {
    var apiKey by remember { mutableStateOf("") }
    var selectedProvider by remember { mutableStateOf("Gemini") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("AI Assistant Settings") },
        text = {
            Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                Text("Select Provider:")
                OutlinedTextField(
                    value = apiKey,
                    onValueChange = { apiKey = it },
                    label = { Text("Enter API Key") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = { onSaveSettings(selectedProvider, apiKey) }) {
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
