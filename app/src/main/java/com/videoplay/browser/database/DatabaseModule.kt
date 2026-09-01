package com.videoplay.browser.database

import com.videoplay.browser.BrowserApplication
import com.videoplay.browser.database.dao.BookmarkDao
import com.videoplay.browser.database.dao.DownloadDao
import com.videoplay.browser.database.dao.HistoryDao
import com.videoplay.browser.database.dao.VideoHistoryDao

/**
 * Provides access to the database and its DAOs.
 * Use this to get instances of the database and its components.
 */
object DatabaseModule {

    // Lazy initialization of the database
    private val database: AppDatabase by lazy {
        BrowserApplication().database
    }

    /**
     * Gets the AppDatabase instance.
     */
    fun getDatabase(): AppDatabase {
        return database
    }

    /**
     * Gets the HistoryDao instance.
     */
    fun getHistoryDao(): HistoryDao {
        return database.historyDao()
    }

    /**
     * Gets the BookmarkDao instance.
     */
    fun getBookmarkDao(): BookmarkDao {
        return database.bookmarkDao()
    }

    /**
     * Gets the VideoHistoryDao instance.
     */
    fun getVideoHistoryDao(): VideoHistoryDao {
        return database.videoHistoryDao()
    }

    /**
     * Gets the DownloadDao instance.
     */
    fun getDownloadDao(): DownloadDao {
        return database.downloadDao()
    }
}
