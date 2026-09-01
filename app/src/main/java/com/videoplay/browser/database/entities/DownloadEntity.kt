package com.videoplay.browser.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Entity for storing download information.
 */
@Entity(tableName = "downloads")
data class DownloadEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val url: String,
    val fileName: String,
    val filePath: String? = null,
    val status: DownloadStatus = DownloadStatus.PENDING,
    val progress: Int = 0, // Percentage (0-100)
    val totalBytes: Long = 0,
    val downloadedBytes: Long = 0,
    val timestamp: Date = Date()
)

/**
 * Status of a download.
 */
enum class DownloadStatus {
    PENDING,
    DOWNLOADING,
    PAUSED,
    COMPLETED,
    FAILED,
    CANCELLED
}
