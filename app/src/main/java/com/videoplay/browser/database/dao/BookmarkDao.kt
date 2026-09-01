package com.videoplay.browser.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.videoplay.browser.database.entities.BookmarkEntity
import com.videoplay.browser.database.entities.BookmarkFolderEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for bookmarks.
 */
@Dao
interface BookmarkDao {

    // Bookmarks

    /**
     * Gets all bookmarks.
     */
    @Query("SELECT * FROM bookmarks ORDER BY timestamp DESC")
    fun getAll(): Flow<List<BookmarkEntity>>

    /**
     * Gets bookmarks in a specific folder.
     */
    @Query("SELECT * FROM bookmarks WHERE folderId = :folderId ORDER BY timestamp DESC")
    fun getByFolder(folderId: Long): Flow<List<BookmarkEntity>>

    /**
     * Gets a bookmark by ID.
     */
    @Query("SELECT * FROM bookmarks WHERE id = :id")
    suspend fun getById(id: Long): BookmarkEntity?

    /**
     * Inserts a new bookmark.
     */
    @Insert
    suspend fun insert(bookmark: BookmarkEntity)

    /**
     * Updates an existing bookmark.
     */
    @Update
    suspend fun update(bookmark: BookmarkEntity)

    /**
     * Deletes a bookmark.
     */
    @Delete
    suspend fun delete(bookmark: BookmarkEntity)

    /**
     * Deletes all bookmarks.
     */
    @Query("DELETE FROM bookmarks")
    suspend fun deleteAll()

    /**
     * Searches bookmarks by URL or title.
     */
    @Query("SELECT * FROM bookmarks WHERE url LIKE :query OR title LIKE :query ORDER BY timestamp DESC")
    fun search(query: String): Flow<List<BookmarkEntity>>

    // Bookmark Folders

    /**
     * Gets all bookmark folders.
     */
    @Query("SELECT * FROM bookmark_folders ORDER BY name ASC")
    fun getAllFolders(): Flow<List<BookmarkFolderEntity>>

    /**
     * Gets a folder by ID.
     */
    @Query("SELECT * FROM bookmark_folders WHERE id = :id")
    suspend fun getFolderById(id: Long): BookmarkFolderEntity?

    /**
     * Inserts a new folder.
     */
    @Insert
    suspend fun insertFolder(folder: BookmarkFolderEntity)

    /**
     * Updates an existing folder.
     */
    @Update
    suspend fun updateFolder(folder: BookmarkFolderEntity)

    /**
     * Deletes a folder.
     */
    @Delete
    suspend fun deleteFolder(folder: BookmarkFolderEntity)
}
