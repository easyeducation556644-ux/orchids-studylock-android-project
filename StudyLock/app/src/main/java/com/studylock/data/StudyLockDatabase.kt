package com.studylock.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.studylock.data.dao.BlockedAppDao
import com.studylock.data.dao.RoutineDao
import com.studylock.data.dao.StatsDao
import com.studylock.data.entities.BlockedApp
import com.studylock.data.entities.Routine
import com.studylock.data.entities.StudyStats

@Database(entities = [Routine::class, BlockedApp::class, StudyStats::class], version = 1, exportSchema = false)
abstract class StudyLockDatabase : RoomDatabase() {
    abstract fun routineDao(): RoutineDao
    abstract fun blockedAppDao(): BlockedAppDao
    abstract fun statsDao(): StatsDao

    companion object {
        @Volatile
        private var INSTANCE: StudyLockDatabase? = null

        fun getDatabase(context: Context): StudyLockDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StudyLockDatabase::class.java,
                    "studylock_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
