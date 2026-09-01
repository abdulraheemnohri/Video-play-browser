package com.videoplay.browser.bookmarks

import com.videoplay.browser.database.dao.BookmarkDao
import com.videoplay.browser.database.entities.BookmarkEntity
import com.videoplay.browser.database.entities.BookmarkFolderEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

/**
 * Repository for managing bookmarks and bookmark folders.
 * Provides functions to add, delete, update, and query bookmarks.
 */
class BookmarkRepository(private val bookmarkDao: BookmarkDao) {

    // Bookmarks

    /**
     * Gets all bookmarks, ordered by timestamp (newest first).
     */
    fun getAllBookmarks(): Flow<List<BookmarkEntity>> {
        return bookmarkDao.getAll()
    }

    /**
     * Gets bookmarks in a specific folder.
     * @param folderId The ID of the folder.
     */
    fun getBookmarksInFolder(folderId: Long): Flow<List<BookmarkEntity>> {
        return bookmarkDao.getByFolder(folderId)
    }

    /**
     * Adds a new bookmark.
     * @param url The URL of the bookmark.
     * @param title The title of the bookmark.
     * @param folderId The ID of the folder to add the bookmark to (null for no folder).
     */
    suspend fun addBookmark(url: String, title: String, folderId: Long? = null) {
        val bookmark = BookmarkEntity(
            url = url,
            title = title,
            folderId = folderId,
            timestamp = Date()
        )
        bookmarkDao.insert(bookmark)
    }

    /**
     * Updates an existing bookmark.
     * @param id The ID of the bookmark to update.
     * @param url The new URL.
     * @param title The new title.
     * @param folderId The new folder ID (null for no folder).
     */
    suspend fun updateBookmark(id: Long, url: String, title: String, folderId: Long? = null) {
        val bookmark = BookmarkEntity(
            id = id,
            url = url,
            title = title,
            folderId = folderId,
            timestamp = Date()
        )
        bookmarkDao.update(bookmark)
    }

    /**
     * Deletes a bookmark.
     * @param bookmark The bookmark to delete.
     */
    suspend fun deleteBookmark(bookmark: BookmarkEntity) {
        bookmarkDao.delete(bookmark)
    }

    /**
     * Deletes all bookmarks.
     */
    suspend fun deleteAllBookmarks() {
        bookmarkDao.deleteAll()
    }

    /**
     * Searches bookmarks by URL or title.
     * @param query The search query.
     */
    fun searchBookmarks(query: String): Flow<List<BookmarkEntity>> {
        return bookmarkDao.search("%$query%")
    }

    // Bookmark Folders

    /**
     * Gets all bookmark folders, ordered by name.
     */
    fun getAllFolders(): Flow<List<BookmarkFolderEntity>> {
        return bookmarkDao.getAllFolders()
    }

    /**
     * Adds a new bookmark folder.
     * @param name The name of the folder.
     */
    suspend fun addFolder(name: String) {
        val folder = BookmarkFolderEntity(name = name)
        bookmarkDao.insertFolder(folder)
    }

    /**
     * Updates an existing folder.
     * @param id The ID of the folder to update.
     * @param name The new name.
     */
    suspend fun updateFolder(id: Long, name: String) {
        val folder = BookmarkFolderEntity(
            id = id,
            name = name
        )
        bookmarkDao.updateFolder(folder)
    }

    /**
     * Deletes a folder and all its bookmarks.
     * @param folder The folder to delete.
     */
    suspend fun deleteFolder(folder: BookmarkFolderEntity) {
        // First, move all bookmarks in this folder to no folder
        val bookmarksInFolder = bookmarkDao.getByFolder(folder.id).value
        bookmarksInFolder.forEach { bookmark ->
            updateBookmark(bookmark.id, bookmark.url, bookmark.title, null)
        }
        bookmarkDao.deleteFolder(folder)
    }

    /**
     * Exports bookmarks to a list of URLs and titles.
     * @return List of pairs (title, URL).
     */
    suspend fun exportBookmarks(): List<Pair<String, String>> {
        return bookmarkDao.getAll().value.map { Pair(it.title, it.url) }
    }

    /**
     * Imports bookmarks from a list of URLs and titles.
     * @param bookmarks List of pairs (title, URL).
     */
    suspend fun importBookmarks(bookmarks: List<Pair<String, String>>) {
        bookmarks.forEach { (title, url) ->
            addBookmark(url, title)
        }
    }
}
