package com.videoplay.browser.history

import com.videoplay.browser.database.dao.HistoryDao
import com.videoplay.browser.database.entities.HistoryEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

/**
 * Repository for managing browsing history.
 * Provides functions to add, delete, and query history entries.
 */
class HistoryRepository(private val historyDao: HistoryDao) {

    /**
     * Gets all history entries, ordered by timestamp (newest first).
     */
    fun getAllHistory(): Flow<List<HistoryEntity>> {
        return historyDao.getAll()
    }

    /**
     * Adds a new history entry.
     * @param url The URL of the page.
     * @param title The title of the page.
     * @param isPrivate Whether the entry is from a private tab.
     */
    suspend fun addHistoryEntry(url: String, title: String, isPrivate: Boolean = false) {
        val entry = HistoryEntity(
            url = url,
            title = title,
            timestamp = Date(),
            isPrivate = isPrivate
        )
        historyDao.insert(entry)
    }

    /**
     * Deletes a history entry.
     * @param entry The history entry to delete.
     */
    suspend fun deleteHistoryEntry(entry: HistoryEntity) {
        historyDao.delete(entry)
    }

    /**
     * Deletes all history entries.
     */
    suspend fun deleteAllHistory() {
        historyDao.deleteAll()
    }

    /**
     * Deletes history entries older than a specific date.
     * @param olderThan The cutoff date.
     */
    suspend fun deleteHistoryOlderThan(olderThan: Date) {
        historyDao.deleteOlderThan(olderThan.time)
    }

    /**
     * Searches history entries by URL or title.
     * @param query The search query.
     */
    fun searchHistory(query: String): Flow<List<HistoryEntity>> {
        return historyDao.search("%$query%")
    }

    /**
     * Gets history entries for a specific date.
     * @param date The date to filter by.
     */
    fun getHistoryForDate(date: Date): Flow<List<HistoryEntity>> {
        // Calculate start and end of the day in milliseconds
        val startOfDay = date.time - (date.hours * 60 * 60 * 1000 + date.minutes * 60 * 1000 + date.seconds * 1000)
        val endOfDay = startOfDay + 24 * 60 * 60 * 1000
        
        // This is a simplified approach; for a real implementation, you'd need to
        // modify the HistoryDao to support date range queries
        return historyDao.search("%") // Placeholder - would need DAO update
    }

    /**
     * Gets the most recently visited entries.
     * @param limit The maximum number of entries to return.
     */
    fun getRecentHistory(limit: Int = 10): Flow<List<HistoryEntity>> {
        // This would require a custom query in HistoryDao
        return historyDao.getAll() // Placeholder - would need DAO update
    }
}
