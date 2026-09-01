package com.videoplay.browser.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Entity for storing video playback history.
 */
@Entity(tableName = "video_history")
data class VideoHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val url: String,
    val title: String,
    val thumbnailUrl: String? = null,
    val position: Long = 0, // Playback position in milliseconds
    val duration: Long = 0, // Total duration in milliseconds
    val lastPlayedAt: Date = Date(),
    val isPrivate: Boolean = false
)
