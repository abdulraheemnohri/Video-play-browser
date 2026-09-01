package com.videoplay.browser.downloads

import com.videoplay.browser.database.dao.DownloadDao
import com.videoplay.browser.database.entities.DownloadEntity
import com.videoplay.browser.database.entities.DownloadStatus
import kotlinx.coroutines.flow.Flow
import java.util.Date

/**
 * Repository for managing downloads.
 * Provides functions to add, update, delete, and query downloads.
 */
class DownloadRepository(private val downloadDao: DownloadDao) {

    /**
     * Gets all download entries, ordered by timestamp (newest first).
     */
    fun getAllDownloads(): Flow<List<DownloadEntity>> {
        return downloadDao.getAll()
    }

    /**
     * Gets all active downloads (not completed or failed).
     */
    fun getActiveDownloads(): Flow<List<DownloadEntity>> {
        return downloadDao.getActiveDownloads()
    }

    /**
     * Gets all completed downloads.
     */
    fun getCompletedDownloads(): Flow<List<DownloadEntity>> {
        return downloadDao.getCompletedDownloads()
    }

    /**
     * Adds a new download entry.
     * @param url The URL of the download.
     * @param fileName The name of the file.
     * @param filePath The path where the file is saved (null if not yet saved).
     * @param status The current status of the download.
     * @param progress The current progress (0-100).
     * @param totalBytes The total size of the download in bytes.
     * @param downloadedBytes The number of bytes downloaded so far.
     */
    suspend fun addDownload(
        url: String,
        fileName: String,
        filePath: String? = null,
        status: DownloadStatus = DownloadStatus.PENDING,
        progress: Int = 0,
        totalBytes: Long = 0,
        downloadedBytes: Long = 0
    ) {
        val download = DownloadEntity(
            url = url,
            fileName = fileName,
            filePath = filePath,
            status = status,
            progress = progress,
            totalBytes = totalBytes,
            downloadedBytes = downloadedBytes,
            timestamp = Date()
        )
        downloadDao.insert(download)
    }

    /**
     * Updates a download entry.
     * @param id The ID of the download to update.
     * @param filePath The new file path (null if unchanged).
     * @param status The new status (null if unchanged).
     * @param progress The new progress (null if unchanged).
     * @param totalBytes The new total bytes (null if unchanged).
     * @param downloadedBytes The new downloaded bytes (null if unchanged).
     */
    suspend fun updateDownload(
        id: Long,
        filePath: String? = null,
        status: DownloadStatus? = null,
        progress: Int? = null,
        totalBytes: Long? = null,
        downloadedBytes: Long? = null
    ) {
        val existingDownload = downloadDao.getById(id) ?: return
        
        val updatedDownload = existingDownload.copy(
            filePath = filePath ?: existingDownload.filePath,
            status = status ?: existingDownload.status,
            progress = progress ?: existingDownload.progress,
            totalBytes = totalBytes ?: existingDownload.totalBytes,
            downloadedBytes = downloadedBytes ?: existingDownload.downloadedBytes
        )
        
        downloadDao.update(updatedDownload)
    }

    /**
     * Updates the status of a download.
     * @param id The ID of the download.
     * @param status The new status.
     */
    suspend fun updateDownloadStatus(id: Long, status: DownloadStatus) {
        downloadDao.updateStatus(id, status)
    }

    /**
     * Updates the progress of a download.
     * @param id The ID of the download.
     * @param progress The new progress (0-100).
     * @param downloadedBytes The new number of downloaded bytes.
     */
    suspend fun updateDownloadProgress(id: Long, progress: Int, downloadedBytes: Long) {
        downloadDao.updateProgress(id, progress, downloadedBytes)
    }

    /**
     * Deletes a download entry.
     * @param download The download to delete.
     */
    suspend fun deleteDownload(download: DownloadEntity) {
        downloadDao.delete(download)
    }

    /**
     * Deletes all download entries.
     */
    suspend fun deleteAllDownloads() {
        downloadDao.deleteAll()
    }

    /**
     * Deletes all completed downloads.
     */
    suspend fun deleteAllCompletedDownloads() {
        val completedDownloads = downloadDao.getCompletedDownloads().value
        completedDownloads.forEach { download ->
            deleteDownload(download)
        }
    }

    /**
     * Gets a download by its ID.
     * @param id The ID of the download.
     */
    suspend fun getDownloadById(id: Long): DownloadEntity? {
        return downloadDao.getById(id)
    }

    /**
     * Pauses a download.
     * @param id The ID of the download to pause.
     */
    suspend fun pauseDownload(id: Long) {
        downloadDao.updateStatus(id, DownloadStatus.PAUSED)
    }

    /**
     * Resumes a download.
     * @param id The ID of the download to resume.
     */
    suspend fun resumeDownload(id: Long) {
        downloadDao.updateStatus(id, DownloadStatus.DOWNLOADING)
    }

    /**
     * Cancels a download.
     * @param id The ID of the download to cancel.
     */
    suspend fun cancelDownload(id: Long) {
        downloadDao.updateStatus(id, DownloadStatus.CANCELLED)
    }

    /**
     * Marks a download as failed.
     * @param id The ID of the download.
     */
    suspend fun markDownloadAsFailed(id: Long) {
        downloadDao.updateStatus(id, DownloadStatus.FAILED)
    }

    /**
     * Marks a download as completed.
     * @param id The ID of the download.
     * @param filePath The path where the file was saved.
     */
    suspend fun markDownloadAsCompleted(id: Long, filePath: String) {
        val download = downloadDao.getById(id) ?: return
        downloadDao.update(
            download.copy(
                status = DownloadStatus.COMPLETED,
                filePath = filePath,
                progress = 100
            )
        )
    }
}
