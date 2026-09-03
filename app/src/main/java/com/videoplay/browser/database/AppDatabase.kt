package com.videoplay.browser.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.videoplay.browser.database.dao.BookmarkDao
import com.videoplay.browser.database.dao.DownloadDao
import com.videoplay.browser.database.dao.HistoryDao
import com.videoplay.browser.database.dao.VideoHistoryDao
import com.videoplay.browser.database.entities.BookmarkEntity
import com.videoplay.browser.database.entities.BookmarkFolderEntity
import com.videoplay.browser.database.entities.DownloadEntity
import com.videoplay.browser.database.entities.HistoryEntity
import com.videoplay.browser.database.entities.VideoHistoryEntity

/**
 * Room Database for VIDEOPlay Browser.
 * Stores History, Bookmarks, Video History, and Downloads.
 */
@Database(
    entities = [
        HistoryEntity::class,
        BookmarkEntity::class,
        BookmarkFolderEntity::class,
        VideoHistoryEntity::class,
        DownloadEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao
    abstract fun bookmarkDao(): BookmarkDao
    abstract fun videoHistoryDao(): VideoHistoryDao
    abstract fun downloadDao(): DownloadDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Gets the singleton instance of the database.
         */
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "videoplay_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }

        /**
         * Destroys the database instance.
         */
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
