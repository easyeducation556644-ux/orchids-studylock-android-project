package com.studylock.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.studylock.data.StudyLockDatabase
import com.studylock.ui.components.StudyCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val db = remember { StudyLockDatabase.getDatabase(context) }
    val stats by db.statsDao().getAllStats().collectAsState(initial = emptyList())
    
    val totalFocusMinutes = stats.sumOf { it.focusMinutes }
    val currentStreak = stats.firstOrNull()?.streakCount ?: 0
    val avgCompliance = if (stats.isNotEmpty()) stats.map { it.complianceScore }.average().toInt() else 0

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Performance Stats") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                StudyCard {
                    Text("Total Focus Time", fontWeight = FontWeight.SemiBold)
                    Text("${totalFocusMinutes / 60} Hours ${totalFocusMinutes % 60} Mins", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }
            }
            item {
                StudyCard {
                    Text("Current Streak", fontWeight = FontWeight.SemiBold)
                    Text("$currentStreak Days", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }
            }
            item {
                StudyCard {
                    Text("Compliance Rate", fontWeight = FontWeight.SemiBold)
                    Text("$avgCompliance%", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }
            }
            
            item {
                Text(
                    "AI Insights: Based on your sessions, you are most productive during the morning hours. Keep it up!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
