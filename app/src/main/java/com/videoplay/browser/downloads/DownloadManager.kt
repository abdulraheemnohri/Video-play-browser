package com.videoplay.browser.downloads

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import com.videoplay.browser.database.entities.DownloadEntity
import com.videoplay.browser.database.entities.DownloadStatus

/**
 * Manages downloads for the browser.
 * Uses Android's DownloadManager to handle file downloads.
 */
class DownloadManager(private val context: Context) {

    private val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

    /**
     * Starts a new download.
     * @param url The URL of the file to download.
     * @param fileName The name of the file.
     * @param destinationDirectory The destination directory (e.g., Environment.DIRECTORY_DOWNLOADS).
     * @return The download ID.
     */
    fun startDownload(
        url: String,
        fileName: String,
        destinationDirectory: String = Environment.DIRECTORY_DOWNLOADS
    ): Long {
        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle(fileName)
            .setDescription("Downloading via VIDEOPlay Browser")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(destinationDirectory, fileName)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(false)

        return downloadManager.enqueue(request)
    }

    /**
     * Pauses a download.
     * @param downloadId The ID of the download to pause.
     */
    fun pauseDownload(downloadId: Long) {
        // Note: Android's DownloadManager does not support pausing directly.
        // This is a placeholder for custom download logic.
    }

    /**
     * Resumes a paused download.
     * @param downloadId The ID of the download to resume.
     */
    fun resumeDownload(downloadId: Long) {
        // Note: Android's DownloadManager does not support resuming directly.
        // This is a placeholder for custom download logic.
    }

    /**
     * Cancels a download.
     * @param downloadId The ID of the download to cancel.
     */
    fun cancelDownload(downloadId: Long) {
        downloadManager.remove(downloadId)
    }

    /**
     * Gets the status of a download.
     * @param downloadId The ID of the download.
     * @return The DownloadStatus.
     */
    fun getDownloadStatus(downloadId: Long): DownloadStatus {
        val query = DownloadManager.Query().setFilterById(downloadId)
        val cursor = downloadManager.query(query)

        return if (cursor.moveToFirst()) {
            when (cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS))) {
                DownloadManager.STATUS_PENDING -> DownloadStatus.PENDING
                DownloadManager.STATUS_RUNNING -> DownloadStatus.DOWNLOADING
                DownloadManager.STATUS_PAUSED -> DownloadStatus.PAUSED
                DownloadManager.STATUS_SUCCESSFUL -> DownloadStatus.COMPLETED
                DownloadManager.STATUS_FAILED -> DownloadStatus.FAILED
                else -> DownloadStatus.CANCELLED
            }
        } else {
            DownloadStatus.CANCELLED
        }.also { cursor.close() }
    }

    /**
     * Gets the progress of a download.
     * @param downloadId The ID of the download.
     * @return The progress percentage (0-100).
     */
    fun getDownloadProgress(downloadId: Long): Int {
        val query = DownloadManager.Query().setFilterById(downloadId)
        val cursor = downloadManager.query(query)

        return if (cursor.moveToFirst()) {
            val bytesDownloaded = cursor.getLong(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
            val bytesTotal = cursor.getLong(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
            if (bytesTotal > 0) {
                ((bytesDownloaded.toDouble() / bytesTotal) * 100).toInt()
            } else {
                0
            }
        } else {
            0
        }.also { cursor.close() }
    }

    /**
     * Gets the file path of a completed download.
     * @param downloadId The ID of the download.
     * @return The file path or null if not found.
     */
    fun getDownloadFilePath(downloadId: Long): String? {
        val query = DownloadManager.Query().setFilterById(downloadId)
        val cursor = downloadManager.query(query)

        return if (cursor.moveToFirst()) {
            cursor.getString(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_LOCAL_URI))
        } else {
            null
        }.also { cursor.close() }
    }

    /**
     * Converts a DownloadManager status to a DownloadEntity.
     */
    fun toDownloadEntity(downloadId: Long, url: String, fileName: String): DownloadEntity {
        val status = getDownloadStatus(downloadId)
        val progress = getDownloadProgress(downloadId)
        val filePath = getDownloadFilePath(downloadId)

        return DownloadEntity(
            id = downloadId,
            url = url,
            fileName = fileName,
            filePath = filePath,
            status = status,
            progress = progress
        )
    }
}
