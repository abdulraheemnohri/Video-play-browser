package com.videoplay.browser.bookmarks

import com.videoplay.browser.database.dao.BookmarkDao
import com.videoplay.browser.database.entities.BookmarkEntity
import com.videoplay.browser.database.entities.BookmarkFolderEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.util.Date

/**
 * Repository for managing bookmarks and bookmark folders.
 * Provides functions to add, delete, update, and query bookmarks.
 */
class BookmarkRepository(private val bookmarkDao: BookmarkDao) {

    fun getAllBookmarks(): Flow<List<BookmarkEntity>> {
        return bookmarkDao.getAll()
    }

    fun getBookmarksInFolder(folderId: Long): Flow<List<BookmarkEntity>> {
        return bookmarkDao.getByFolder(folderId)
    }

    suspend fun addBookmark(url: String, title: String, folderId: Long? = null) {
        val bookmark = BookmarkEntity(
            url = url,
            title = title,
            folderId = folderId,
            timestamp = Date()
        )
        bookmarkDao.insert(bookmark)
    }

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

    suspend fun deleteBookmark(bookmark: BookmarkEntity) {
        bookmarkDao.delete(bookmark)
    }

    suspend fun deleteAllBookmarks() {
        bookmarkDao.deleteAll()
    }

    fun searchBookmarks(query: String): Flow<List<BookmarkEntity>> {
        return bookmarkDao.search("%$query%")
    }

    fun getAllFolders(): Flow<List<BookmarkFolderEntity>> {
        return bookmarkDao.getAllFolders()
    }

    suspend fun addFolder(name: String) {
        val folder = BookmarkFolderEntity(name = name)
        bookmarkDao.insertFolder(folder)
    }

    suspend fun updateFolder(id: Long, name: String) {
        val folder = BookmarkFolderEntity(
            id = id,
            name = name
        )
        bookmarkDao.updateFolder(folder)
    }

    suspend fun deleteFolder(folder: BookmarkFolderEntity) {
        val bookmarksInFolder = bookmarkDao.getByFolder(folder.id).first()
        bookmarksInFolder.forEach { bookmark ->
            updateBookmark(bookmark.id, bookmark.url, bookmark.title, null)
        }
        bookmarkDao.deleteFolder(folder)
    }

    suspend fun exportBookmarks(): List<Pair<String, String>> {
        return bookmarkDao.getAll().first().map { Pair(it.title, it.url) }
    }

    suspend fun importBookmarks(bookmarks: List<Pair<String, String>>) {
        bookmarks.forEach { (title, url) ->
            addBookmark(url, title)
        }
    }
}
