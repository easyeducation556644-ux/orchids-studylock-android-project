package com.studylock

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import android.app.AppOpsManager
import android.content.Context
import android.net.Uri
import android.os.Process
import android.provider.Settings
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.studylock.ui.screens.*
import com.studylock.ui.theme.StudyLockTheme
import com.studylock.services.AppBlockerService

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        checkPermissions()

        // Start the blocker service
        val serviceIntent = Intent(this, AppBlockerService::class.java)
        startForegroundService(serviceIntent)

        val startScreen = intent.getStringExtra("SCREEN") ?: "MAIN"

        setContent {
            StudyLockTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    StudyLockApp(startScreen)
                }
            }
        }
    }

    private fun checkPermissions() {
        if (!hasUsageStatsPermission()) {
            val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        if (!Settings.canDrawOverlays(this)) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

    private fun hasUsageStatsPermission(): Boolean {
        val appOps = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = appOps.checkOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            Process.myUid(),
            packageName
        )
        return mode == AppOpsManager.MODE_ALLOWED
    }
}

@Composable
fun StudyLockApp(startScreen: String) {
    val navController = rememberNavController()
    
    val initialRoute = when (startScreen) {
        "BLOCK_OVERLAY" -> "block"
        else -> "main"
    }

    NavHost(navController = navController, startDestination = initialRoute) {
        composable("main") {
            MainScreen(
                onNavigateToRoutine = { navController.navigate("routine") },
                onNavigateToStats = { navController.navigate("stats") },
                onNavigateToDonate = { navController.navigate("donate") },
                onNavigateToAppSelection = { navController.navigate("app_selection") }
            )
        }
        composable("routine") {
            RoutineScreen(onBack = { navController.popBackStack() })
        }
        composable("stats") {
            StatsScreen(onBack = { navController.popBackStack() })
        }
        composable("donate") {
            DonateScreen(onBack = { navController.popBackStack() })
        }
        composable("app_selection") {
            AppSelectionScreen(onBack = { navController.popBackStack() })
        }
        composable("block") {
            BlockScreen(onDismiss = { navController.navigate("main") })
        }
    }
}
