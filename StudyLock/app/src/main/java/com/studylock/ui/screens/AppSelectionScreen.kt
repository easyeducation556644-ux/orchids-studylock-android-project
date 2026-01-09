package com.studylock.ui.screens

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import com.studylock.data.StudyLockDatabase
import com.studylock.data.entities.BlockedApp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSelectionScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val db = remember { StudyLockDatabase.getDatabase(context) }
    val blockedAppDao = db.blockedAppDao()
    
    val packageManager = context.packageManager
    val installedApps = remember {
        packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
            .filter { it.flags and ApplicationInfo.FLAG_SYSTEM == 0 } // Filter out system apps
            .sortedBy { packageManager.getApplicationLabel(it).toString() }
    }
    
    val blockedApps by blockedAppDao.getAllBlockedApps().collectAsState(initial = emptyList())
    val blockedPackageNames = blockedApps.map { it.packageName }.toSet()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Block Distractions") },
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
        ) {
            items(installedApps) { app ->
                val isBlocked = blockedPackageNames.contains(app.packageName)
                val label = packageManager.getApplicationLabel(app).toString()
                
                ListItem(
                    headlineContent = { Text(label) },
                    supportingContent = { Text(app.packageName) },
                    leadingContent = {
                        val icon = packageManager.getApplicationIcon(app).toBitmap().asImageBitmap()
                        Image(bitmap = icon, contentDescription = null, modifier = Modifier.size(40.dp))
                    },
                    trailingContent = {
                        Switch(
                            checked = isBlocked,
                            onCheckedChange = { checked ->
                                scope.launch {
                                    if (checked) {
                                        blockedAppDao.insertBlockedApp(BlockedApp(app.packageName, label))
                                    } else {
                                        blockedAppDao.updateBlockedApp(BlockedApp(app.packageName, label, false))
                                        // Alternatively, delete if you prefer
                                    }
                                }
                            }
                        )
                    }
                )
            }
        }
    }
}
