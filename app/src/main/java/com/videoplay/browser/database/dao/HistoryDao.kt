package com.videoplay.browser.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.videoplay.browser.database.entities.HistoryEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for browsing history.
 */
@Dao
interface HistoryDao {

    /**
     * Gets all history entries.
     */
    @Query("SELECT * FROM history ORDER BY timestamp DESC")
    fun getAll(): Flow<List<HistoryEntity>>

    /**
     * Gets a history entry by ID.
     */
    @Query("SELECT * FROM history WHERE id = :id")
    suspend fun getById(id: Long): HistoryEntity?

    /**
     * Inserts a new history entry.
     */
    @Insert
    suspend fun insert(history: HistoryEntity)

    /**
     * Updates an existing history entry.
     */
    @Update
    suspend fun update(history: HistoryEntity)

    /**
     * Deletes a history entry.
     */
    @Delete
    suspend fun delete(history: HistoryEntity)

    /**
     * Deletes all history entries.
     */
    @Query("DELETE FROM history")
    suspend fun deleteAll()

    /**
     * Deletes history entries older than a specific timestamp.
     */
    @Query("DELETE FROM history WHERE timestamp < :timestamp")
    suspend fun deleteOlderThan(timestamp: Long)

    /**
     * Searches history entries by URL or title.
     */
    @Query("SELECT * FROM history WHERE url LIKE :query OR title LIKE :query ORDER BY timestamp DESC")
    fun search(query: String): Flow<List<HistoryEntity>>
}
