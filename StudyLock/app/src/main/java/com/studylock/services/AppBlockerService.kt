package com.studylock.services

import android.app.Service
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.os.Handler
import android.os.Looper
import androidx.core.app.NotificationCompat
import com.studylock.data.StudyLockDatabase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest

class AppBlockerService : Service() {
    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.IO + serviceJob)
    private var blockedPackages: Set<String> = emptySet()
    private val handler = Handler(Looper.getMainLooper())
    private val checkInterval = 1000L // Check every second for better responsiveness

    override fun onCreate() {
        super.onCreate()
        NotificationService.createNotificationChannel(this)
        startForeground(NOTIFICATION_ID, createNotification())
        observeBlockedApps()
        startMonitoring()
    }

    private fun observeBlockedApps() {
        serviceScope.launch {
            val db = StudyLockDatabase.getDatabase(applicationContext)
            db.blockedAppDao().getAllBlockedApps().collectLatest { apps ->
                blockedPackages = apps.filter { it.isBlocked }.map { it.packageName }.toSet()
            }
        }
    }

    private fun createNotification() = NotificationCompat.Builder(this, NotificationService.CHANNEL_ID)
        .setContentTitle("StudyLock is Running")
        .setContentText("Monitoring distracting apps to keep you focused.")
        .setSmallIcon(android.R.drawable.ic_lock_lock)
        .setOngoing(true)
        .build()

    private fun startMonitoring() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                checkTopActivity()
                handler.postDelayed(this, checkInterval)
            }
        }, checkInterval)
    }

    private fun checkTopActivity() {
        val usageStatsManager = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val time = System.currentTimeMillis()
        val stats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 60, time)
        
        if (stats != null && stats.isNotEmpty()) {
            val sortedStats = stats.sortedByDescending { it.lastTimeUsed }
            val topPackageName = sortedStats[0].packageName
            
            if (blockedPackages.contains(topPackageName)) {
                launchBlockOverlay(topPackageName)
            }
        }
    }

    private fun launchBlockOverlay(packageName: String) {
        // Bring user back to StudyLock or show a block screen
        val intent = Intent(this, com.studylock.MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            putExtra("SCREEN", "BLOCK_OVERLAY")
            putExtra("BLOCKED_PACKAGE", packageName)
        }
        startActivity(intent)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        serviceJob.cancel()
        handler.removeCallbacksAndMessages(null)
    }

    companion object {
        const val NOTIFICATION_ID = 102
    }
}
