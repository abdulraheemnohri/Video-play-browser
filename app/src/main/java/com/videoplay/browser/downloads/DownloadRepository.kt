package com.videoplay.browser.downloads

import com.videoplay.browser.database.dao.DownloadDao
import com.videoplay.browser.database.entities.DownloadEntity
import com.videoplay.browser.database.entities.DownloadStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.util.Date

/**
 * Repository for managing downloads.
 * Provides functions to add, update, delete, and query downloads.
 */
class DownloadRepository(private val downloadDao: DownloadDao) {

    fun getAllDownloads(): Flow<List<DownloadEntity>> {
        return downloadDao.getAll()
    }

    fun getActiveDownloads(): Flow<List<DownloadEntity>> {
        return downloadDao.getActiveDownloads()
    }

    fun getCompletedDownloads(): Flow<List<DownloadEntity>> {
        return downloadDao.getCompletedDownloads()
    }

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

    suspend fun updateDownloadStatus(id: Long, status: DownloadStatus) {
        downloadDao.updateStatus(id, status)
    }

    suspend fun updateDownloadProgress(id: Long, progress: Int, downloadedBytes: Long) {
        downloadDao.updateProgress(id, progress, downloadedBytes)
    }

    suspend fun deleteDownload(download: DownloadEntity) {
        downloadDao.delete(download)
    }

    suspend fun deleteAllDownloads() {
        downloadDao.deleteAll()
    }

    suspend fun deleteAllCompletedDownloads() {
        val completedDownloads = downloadDao.getCompletedDownloads().first()
        completedDownloads.forEach { download ->
            deleteDownload(download)
        }
    }

    suspend fun getDownloadById(id: Long): DownloadEntity? {
        return downloadDao.getById(id)
    }

    suspend fun pauseDownload(id: Long) {
        downloadDao.updateStatus(id, DownloadStatus.PAUSED)
    }

    suspend fun resumeDownload(id: Long) {
        downloadDao.updateStatus(id, DownloadStatus.DOWNLOADING)
    }

    suspend fun cancelDownload(id: Long) {
        downloadDao.updateStatus(id, DownloadStatus.CANCELLED)
    }

    suspend fun markDownloadAsFailed(id: Long) {
        downloadDao.updateStatus(id, DownloadStatus.FAILED)
    }

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
