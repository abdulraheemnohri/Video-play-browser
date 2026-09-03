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

    private val dbInstance: AppDatabase by lazy {
        BrowserApplication().database
    }

    fun getDatabase(): AppDatabase {
        return dbInstance
    }

    fun getHistoryDao(): HistoryDao {
        return dbInstance.historyDao()
    }

    fun getBookmarkDao(): BookmarkDao {
        return dbInstance.bookmarkDao()
    }

    fun getVideoHistoryDao(): VideoHistoryDao {
        return dbInstance.videoHistoryDao()
    }

    fun getDownloadDao(): DownloadDao {
        return dbInstance.downloadDao()
    }
}
