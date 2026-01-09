package com.studylock.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.studylock.data.StudyLockDatabase
import com.studylock.ui.components.StudyCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onNavigateToRoutine: () -> Unit,
    onNavigateToStats: () -> Unit,
    onNavigateToDonate: () -> Unit,
    onNavigateToAppSelection: () -> Unit
) {
    val context = LocalContext.current
    val db = remember { StudyLockDatabase.getDatabase(context) }
    val routines by db.routineDao().getAllRoutines().collectAsState(initial = emptyList())
    
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("StudyLock", fontWeight = FontWeight.Bold) })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToRoutine) {
                Icon(Icons.Default.Add, contentDescription = "Add Routine")
            }
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
                    Text("Focus Level", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = 0.7f,
                        modifier = Modifier.fillMaxWidth(),
                        strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
                    )
                    Text("Focusing on Study", modifier = Modifier.align(Alignment.End))
                }
            }
            
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = onNavigateToStats, modifier = Modifier.weight(1f)) {
                        Text("Stats")
                    }
                    Button(onClick = onNavigateToAppSelection, modifier = Modifier.weight(1f)) {
                        Icon(Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Block List")
                    }
                }
            }

            item {
                Button(
                    onClick = onNavigateToDonate,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Text("Support Development")
                }
            }

            item {
                Text("Upcoming Sessions", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }

            if (routines.isEmpty()) {
                item {
                    Text("No routines set. Tap + to add one!", style = MaterialTheme.typography.bodyMedium)
                }
            } else {
                items(routines) { routine ->
                    StudyCard {
                        Text(routine.subject, fontWeight = FontWeight.Bold)
                        Text("${routine.startTime} - ${routine.endTime}")
                    }
                }
            }
        }
    }
}
