package com.videoplay.browser.privacy

import android.content.Context
import android.webkit.CookieManager
import com.videoplay.browser.BrowserApplication
import com.videoplay.browser.database.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Manages clearing browsing data (history, cookies, cache, etc.).
 */
class ClearBrowsingDataManager(private val context: Context) {

    private val database: AppDatabase by lazy {
        (context.applicationContext as BrowserApplication).database
    }

    /**
     * Data types that can be cleared.
     */
    enum class DataType {
        HISTORY,
        COOKIES,
        CACHE,
        BOOKMARKS,
        DOWNLOADS,
        VIDEO_HISTORY,
        ALL
    }

    /**
     * Clears browsing data based on the selected data types.
     * @param dataTypes The types of data to clear.
     * @param onComplete Callback to be invoked when clearing is complete.
     */
    fun clearBrowsingData(dataTypes: Set<DataType>, onComplete: () -> Unit = {}) {
        CoroutineScope(Dispatchers.IO).launch {
            if (DataType.HISTORY in dataTypes || DataType.ALL in dataTypes) {
                clearHistory()
            }

            if (DataType.COOKIES in dataTypes || DataType.ALL in dataTypes) {
                clearCookies()
            }

            if (DataType.CACHE in dataTypes || DataType.ALL in dataTypes) {
                clearCache()
            }

            if (DataType.BOOKMARKS in dataTypes || DataType.ALL in dataTypes) {
                clearBookmarks()
            }

            if (DataType.DOWNLOADS in dataTypes || DataType.ALL in dataTypes) {
                clearDownloads()
            }

            if (DataType.VIDEO_HISTORY in dataTypes || DataType.ALL in dataTypes) {
                clearVideoHistory()
            }

            // Notify completion on main thread
            CoroutineScope(Dispatchers.Main).launch {
                onComplete()
            }
        }
    }

    /**
     * Clears browsing history.
     */
    private suspend fun clearHistory() {
        database.historyDao().deleteAll()
    }

    /**
     * Clears cookies.
     */
    private fun clearCookies() {
        CookieManager.getInstance().removeAllCookies(null)
        CookieManager.getInstance().flush()
    }

    /**
     * Clears cache.
     * Note: This is a simplified implementation. In a real app, you would need
     * to clear GeckoView's cache as well.
     */
    private fun clearCache() {
        // Clear GeckoView cache (if available)
        // Note: This requires access to GeckoRuntime
        println("Clearing cache (GeckoView cache would be cleared here)")
    }

    /**
     * Clears bookmarks.
     */
    private suspend fun clearBookmarks() {
        database.bookmarkDao().deleteAll()
    }

    /**
     * Clears downloads.
     */
    private suspend fun clearDownloads() {
        database.downloadDao().deleteAll()
    }

    /**
     * Clears video history.
     */
    private suspend fun clearVideoHistory() {
        database.videoHistoryDao().deleteAll()
    }

    /**
     * Gets the list of all data types for display.
     * @return List of data types with their display names.
     */
    fun getDataTypesForDisplay(): List<Pair<DataType, String>> {
        return listOf(
            Pair(DataType.HISTORY, "Browsing History"),
            Pair(DataType.COOKIES, "Cookies and Site Data"),
            Pair(DataType.CACHE, "Cached Images and Files"),
            Pair(DataType.BOOKMARKS, "Bookmarks"),
            Pair(DataType.DOWNLOADS, "Downloads"),
            Pair(DataType.VIDEO_HISTORY, "Video History"),
            Pair(DataType.ALL, "All Browsing Data")
        )
    }

    /**
     * Gets a description of what will be cleared for each data type.
     * @param dataType The data type to describe.
     * @return A human-readable description.
     */
    fun getDataTypeDescription(dataType: DataType): String {
        return when (dataType) {
            DataType.HISTORY -> "Clears your browsing history, including recently visited pages."
            DataType.COOKIES -> "Clears cookies and site data, which may log you out of websites."
            DataType.CACHE -> "Clears cached images and files, which may make websites load slower on next visit."
            DataType.BOOKMARKS -> "Clears all your saved bookmarks."
            DataType.DOWNLOADS -> "Clears your download history, but not the downloaded files."
            DataType.VIDEO_HISTORY -> "Clears your video watching history."
            DataType.ALL -> "Clears all browsing data including history, cookies, cache, bookmarks, downloads, and video history."
        }
    }

    /**
     * Gets the default data types to clear.
     * @return Set of default data types.
     */
    fun getDefaultDataTypes(): Set<DataType> {
        return setOf(
            DataType.HISTORY,
            DataType.COOKIES,
            DataType.CACHE
        )
    }
}
