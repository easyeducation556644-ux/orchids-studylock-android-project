package com.studylock.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.studylock.MainActivity
import com.studylock.R

class NotificationService(private val context: Context) {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        createNotificationChannel(context)
    }

    companion object {
        const val CHANNEL_ID = "study_lock_channel"
        const val NOTIFICATION_ID = 101

        fun createNotificationChannel(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                val channel = NotificationChannel(
                    CHANNEL_ID,
                    "StudyLock Notifications",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "Reminders for study sessions"
                }
                notificationManager.createNotificationChannel(channel)
            }
        }
    }
}
