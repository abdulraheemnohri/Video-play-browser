package com.videoplay.browser.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.videoplay.browser.database.entities.VideoHistoryEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for video history.
 */
@Dao
interface VideoHistoryDao {

    /**
     * Gets all video history entries.
     */
    @Query("SELECT * FROM video_history ORDER BY lastPlayedAt DESC")
    fun getAll(): Flow<List<VideoHistoryEntity>>

    /**
     * Gets a video history entry by ID.
     */
    @Query("SELECT * FROM video_history WHERE id = :id")
    suspend fun getById(id: Long): VideoHistoryEntity?

    /**
     * Inserts a new video history entry.
     */
    @Insert
    suspend fun insert(videoHistory: VideoHistoryEntity)

    /**
     * Updates an existing video history entry.
     */
    @Update
    suspend fun update(videoHistory: VideoHistoryEntity)

    /**
     * Deletes a video history entry.
     */
    @Delete
    suspend fun delete(videoHistory: VideoHistoryEntity)

    /**
     * Deletes all video history entries.
     */
    @Query("DELETE FROM video_history")
    suspend fun deleteAll()

    /**
     * Deletes video history entries older than a specific timestamp.
     */
    @Query("DELETE FROM video_history WHERE lastPlayedAt < :timestamp")
    suspend fun deleteOlderThan(timestamp: Long)

    /**
     * Searches video history entries by URL or title.
     */
    @Query("SELECT * FROM video_history WHERE url LIKE :query OR title LIKE :query ORDER BY lastPlayedAt DESC")
    fun search(query: String): Flow<List<VideoHistoryEntity>>

    /**
     * Gets the most recently watched videos.
     */
    @Query("SELECT * FROM video_history ORDER BY lastPlayedAt DESC LIMIT :limit")
    fun getRecentlyWatched(limit: Int = 10): Flow<List<VideoHistoryEntity>>

    /**
     * Gets videos to continue watching (partially watched).
     */
    @Query("SELECT * FROM video_history WHERE position > 0 AND position < duration ORDER BY lastPlayedAt DESC LIMIT :limit")
    fun getContinueWatching(limit: Int = 10): Flow<List<VideoHistoryEntity>>
}
