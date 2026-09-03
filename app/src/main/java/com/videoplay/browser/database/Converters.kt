package com.videoplay.browser.database

import androidx.room.TypeConverter
import com.videoplay.browser.database.entities.DownloadStatus
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromDownloadStatus(status: DownloadStatus?): String? {
        return status?.name
    }

    @TypeConverter
    fun toDownloadStatus(value: String?): DownloadStatus? {
        return value?.let {
            try {
                DownloadStatus.valueOf(it)
            } catch (e: Exception) {
                DownloadStatus.PENDING
            }
        }
    }
}
