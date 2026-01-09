package com.studylock.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.studylock.ui.components.StudyButton

@Composable
fun BlockScreen(onDismiss: () -> Unit) {
    var reason by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.errorContainer
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Focus Mode Active!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onErrorContainer
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "This app is blocked to help you focus. If you really need to open it, please provide a valid reason.",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onErrorContainer
            )
            Spacer(modifier = Modifier.height(32.dp))
            OutlinedTextField(
                value = reason,
                onValueChange = { reason = it },
                label = { Text("Reason for override") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onErrorContainer
                )
            )
            Spacer(modifier = Modifier.height(24.dp))
            StudyButton(
                text = "Submit & Continue",
                onClick = {
                    if (reason.length > 10) {
                        onDismiss()
                    }
                }
            )
            TextButton(onClick = { /* Go back to StudyLock */ }) {
                Text("Nevermind, let's study!")
            }
        }
    }
}
