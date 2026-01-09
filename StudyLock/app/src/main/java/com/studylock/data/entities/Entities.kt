package com.studylock.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "routines")
data class Routine(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val subject: String,
    val examDate: Long,
    val startTime: String, // HH:mm
    val endTime: String,   // HH:mm
    val isActive: Boolean = true
)

@Entity(tableName = "blocked_apps")
data class BlockedApp(
    @PrimaryKey val packageName: String,
    val appName: String,
    val isBlocked: Boolean = true
)

@Entity(tableName = "study_stats")
data class StudyStats(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String, // YYYY-MM-DD
    val focusMinutes: Int,
    val complianceScore: Int,
    val streakCount: Int
)
