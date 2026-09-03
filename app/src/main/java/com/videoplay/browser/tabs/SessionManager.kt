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

    private val mTabManager: TabManager by lazy {
        TabManager(GeckoRuntimeManager.getRuntime()!!)
    }

    companion object {
        private const val PREFS_KEY_TABS = "saved_tabs"
        private const val PREFS_KEY_CURRENT_TAB_INDEX = "current_tab_index"
    }

    fun saveSession() {
        val tabs = mTabManager.getTabs()
        val urls = tabs.map { it.url }
        val currentIndex = mTabManager.getCurrentTabIndex()

        sharedPrefs.edit()
            .putStringSet(PREFS_KEY_TABS, urls.toSet())
            .putInt(PREFS_KEY_CURRENT_TAB_INDEX, currentIndex)
            .apply()
    }

    fun restoreSession() {
        val urls = sharedPrefs.getStringSet(PREFS_KEY_TABS, emptySet())?.toList() ?: emptyList()
        val currentIndex = sharedPrefs.getInt(PREFS_KEY_CURRENT_TAB_INDEX, 0)

        if (urls.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                mTabManager.restoreTabs(urls)
                if (currentIndex < mTabManager.getTabs().size) {
                    mTabManager.switchToTab(currentIndex)
                }
            }
        }
    }

    fun clearSession() {
        sharedPrefs.edit()
            .remove(PREFS_KEY_TABS)
            .remove(PREFS_KEY_CURRENT_TAB_INDEX)
            .apply()
    }

    fun getTabManager(): TabManager {
        return mTabManager
    }
}
