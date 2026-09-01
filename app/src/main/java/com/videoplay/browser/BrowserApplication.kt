package com.videoplay.browser

import android.app.Application
import androidx.room.Room
import com.videoplay.browser.database.AppDatabase
import com.videoplay.browser.gecko.runtime.GeckoRuntimeManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Custom Application class for VIDEOPlay Browser.
 * Initializes GeckoRuntime, Room Database, and other app-wide dependencies.
 */
class BrowserApplication : Application() {

    // Room Database instance
    val database: AppDatabase by lazy {
        Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "videoplay_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        // Initialize GeckoRuntime
        GeckoRuntimeManager.initialize(this)

        // Initialize Room Database in a background thread
        CoroutineScope(Dispatchers.IO).launch {
            database // This will trigger the database creation
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        // Release GeckoRuntime
        GeckoRuntimeManager.release()
    }
}
