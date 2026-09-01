package com.videoplay.browser.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity for storing bookmark folders.
 */
@Entity(tableName = "bookmark_folders")
data class BookmarkFolderEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String
)
