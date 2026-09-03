package com.videoplay.browser.video

import com.videoplay.browser.database.dao.VideoHistoryDao
import com.videoplay.browser.database.entities.VideoHistoryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.util.Date

/**
 * Repository for managing video history and playback state.
 * Provides functions to save, update, delete, and query video history entries.
 */
class VideoRepository(private val videoHistoryDao: VideoHistoryDao) {

    /**
     * Gets all video history entries, ordered by last played date (newest first).
     */
    fun getAllVideoHistory(): Flow<List<VideoHistoryEntity>> {
        return videoHistoryDao.getAll()
    }

    /**
     * Gets the most recently watched videos.
     * @param limit The maximum number of videos to return.
     */
    fun getRecentlyWatched(limit: Int = 10): Flow<List<VideoHistoryEntity>> {
        return videoHistoryDao.getRecentlyWatched(limit)
    }

    /**
     * Gets videos to continue watching (partially watched).
     * @param limit The maximum number of videos to return.
     */
    fun getContinueWatching(limit: Int = 10): Flow<List<VideoHistoryEntity>> {
        return videoHistoryDao.getContinueWatching(limit)
    }

    /**
     * Saves or updates a video history entry.
     * @param url The URL of the video.
     * @param title The title of the video.
     * @param thumbnailUrl The URL of the video thumbnail (null if not available).
     * @param position The current playback position in milliseconds.
     * @param duration The total duration of the video in milliseconds.
     * @param isPrivate Whether the entry is from a private tab.
     */
    suspend fun saveVideoHistory(
        url: String,
        title: String,
        thumbnailUrl: String? = null,
        position: Long,
        duration: Long,
        isPrivate: Boolean = false
    ) {
        val existingEntry = videoHistoryDao.getAll().first().find { it.url == url }
        
        if (existingEntry != null) {
            // Update existing entry
            val updatedEntry = existingEntry.copy(
                title = title,
                thumbnailUrl = thumbnailUrl,
                position = position,
                duration = duration,
                lastPlayedAt = Date()
            )
            videoHistoryDao.update(updatedEntry)
        } else {
            // Insert new entry
            val newEntry = VideoHistoryEntity(
                url = url,
                title = title,
                thumbnailUrl = thumbnailUrl,
                position = position,
                duration = duration,
                lastPlayedAt = Date(),
                isPrivate = isPrivate
            )
            videoHistoryDao.insert(newEntry)
        }
    }

    /**
     * Updates the playback position of a video.
     * @param url The URL of the video.
     * @param position The new playback position in milliseconds.
     */
    suspend fun updateVideoPosition(url: String, position: Long) {
        val existingEntry = videoHistoryDao.getAll().first().find { it.url == url }
        
        if (existingEntry != null) {
            val updatedEntry = existingEntry.copy(
                position = position,
                lastPlayedAt = Date()
            )
            videoHistoryDao.update(updatedEntry)
        }
    }

    /**
     * Deletes a video history entry.
     * @param entry The video history entry to delete.
     */
    suspend fun deleteVideoHistory(entry: VideoHistoryEntity) {
        videoHistoryDao.delete(entry)
    }

    /**
     * Deletes all video history entries.
     */
    suspend fun deleteAllVideoHistory() {
        videoHistoryDao.deleteAll()
    }

    /**
     * Deletes video history entries older than a specific date.
     * @param olderThan The cutoff date.
     */
    suspend fun deleteVideoHistoryOlderThan(olderThan: Date) {
        videoHistoryDao.deleteOlderThan(olderThan.time)
    }

    /**
     * Searches video history entries by URL or title.
     * @param query The search query.
     */
    fun searchVideoHistory(query: String): Flow<List<VideoHistoryEntity>> {
        return videoHistoryDao.search("%$query%")
    }

    /**
     * Gets a video history entry by its URL.
     * @param url The URL of the video.
     */
    suspend fun getVideoHistoryByUrl(url: String): VideoHistoryEntity? {
        return videoHistoryDao.getAll().first().find { it.url == url }
    }

    /**
     * Checks if a video is in the history.
     * @param url The URL of the video.
     */
    suspend fun isVideoInHistory(url: String): Boolean {
        return videoHistoryDao.getAll().first().any { it.url == url }
    }

    /**
     * Gets the playback position of a video.
     * @param url The URL of the video.
     * @return The playback position in milliseconds, or 0 if not found.
     */
    suspend fun getVideoPosition(url: String): Long {
        return videoHistoryDao.getAll().first().find { it.url == url }?.position ?: 0L
    }
}
