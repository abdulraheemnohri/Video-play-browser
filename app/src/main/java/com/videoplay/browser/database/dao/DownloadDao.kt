package com.videoplay.browser.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.videoplay.browser.database.entities.DownloadEntity
import com.videoplay.browser.database.entities.DownloadStatus
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for downloads.
 */
@Dao
interface DownloadDao {

    /**
     * Gets all download entries.
     */
    @Query("SELECT * FROM downloads ORDER BY timestamp DESC")
    fun getAll(): Flow<List<DownloadEntity>>

    /**
     * Gets a download entry by ID.
     */
    @Query("SELECT * FROM downloads WHERE id = :id")
    suspend fun getById(id: Long): DownloadEntity?

    /**
     * Inserts a new download entry.
     */
    @Insert
    suspend fun insert(download: DownloadEntity)

    /**
     * Updates an existing download entry.
     */
    @Update
    suspend fun update(download: DownloadEntity)

    /**
     * Deletes a download entry.
     */
    @Delete
    suspend fun delete(download: DownloadEntity)

    /**
     * Deletes all download entries.
     */
    @Query("DELETE FROM downloads")
    suspend fun deleteAll()

    /**
     * Gets all active downloads (not completed or failed).
     */
    @Query("SELECT * FROM downloads WHERE status IN (:statuses) ORDER BY timestamp DESC")
    fun getActiveDownloads(statuses: List<DownloadStatus> = listOf(
        DownloadStatus.PENDING,
        DownloadStatus.DOWNLOADING,
        DownloadStatus.PAUSED
    )): Flow<List<DownloadEntity>>

    /**
     * Gets all completed downloads.
     */
    @Query("SELECT * FROM downloads WHERE status = :status ORDER BY timestamp DESC")
    fun getCompletedDownloads(status: DownloadStatus = DownloadStatus.COMPLETED): Flow<List<DownloadEntity>>

    /**
     * Updates the status of a download.
     */
    @Query("UPDATE downloads SET status = :status WHERE id = :id")
    suspend fun updateStatus(id: Long, status: DownloadStatus)

    /**
     * Updates the progress of a download.
     */
    @Query("UPDATE downloads SET progress = :progress, downloadedBytes = :downloadedBytes WHERE id = :id")
    suspend fun updateProgress(id: Long, progress: Int, downloadedBytes: Long)
}
