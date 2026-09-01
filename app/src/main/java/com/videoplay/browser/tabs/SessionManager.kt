package com.videoplay.browser.tabs

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.videoplay.browser.BrowserApplication
import com.videoplay.browser.gecko.runtime.GeckoRuntimeManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Manages browser session restoration.
 * Saves and restores tabs when the app is closed or restarted.
 */
class SessionManager(private val application: BrowserApplication) {

    private val sharedPrefs: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(application)
    }

    private val tabManager: TabManager by lazy {
        TabManager(GeckoRuntimeManager.getRuntime()!!)
    }

    companion object {
        private const val PREFS_KEY_TABS = "saved_tabs"
        private const val PREFS_KEY_CURRENT_TAB_INDEX = "current_tab_index"
    }

    /**
     * Saves the current tabs for session restoration.
     */
    fun saveSession() {
        val tabs = tabManager.getTabs()
        val urls = tabs.map { it.url }
        val currentIndex = tabManager.getCurrentTabIndex()

        sharedPrefs.edit()
            .putStringSet(PREFS_KEY_TABS, urls.toSet())
            .putInt(PREFS_KEY_CURRENT_TAB_INDEX, currentIndex)
            .apply()
    }

    /**
     * Restores tabs from a saved session.
     */
    fun restoreSession() {
        val urls = sharedPrefs.getStringSet(PREFS_KEY_TABS, emptySet())?.toList() ?: emptyList()
        val currentIndex = sharedPrefs.getInt(PREFS_KEY_CURRENT_TAB_INDEX, 0)

        if (urls.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                tabManager.restoreTabs(urls)
                if (currentIndex < tabManager.getTabs().size) {
                    tabManager.switchToTab(currentIndex)
                }
            }
        }
    }

    /**
     * Clears the saved session.
     */
    fun clearSession() {
        sharedPrefs.edit()
            .remove(PREFS_KEY_TABS)
            .remove(PREFS_KEY_CURRENT_TAB_INDEX)
            .apply()
    }

    /**
     * Returns the TabManager instance.
     */
    fun getTabManager(): TabManager {
        return tabManager
    }
}
