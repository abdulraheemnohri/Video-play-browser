package com.videoplay.browser.tabs

import org.mozilla.geckoview.GeckoRuntime
import org.mozilla.geckoview.GeckoSession

/**
 * Manages browser tabs and their associated GeckoSessions.
 * Provides functionality to add, remove, and switch between tabs.
 * Ensures proper cleanup of GeckoSessions to avoid memory leaks.
 */
class TabManager(
    private val runtime: GeckoRuntime,
    private val sessionFactory: () -> GeckoSession = { GeckoSession() }
) {

    private val tabs = mutableListOf<Tab>()
    private var currentTabIndex = 0

    init {
        addNewTab()
    }

    /**
     * Adds a new tab with the specified URL.
     * @param url The URL to load in the new tab.
     * @return The newly created Tab.
     */
    fun addNewTab(url: String = "about:blank"): Tab {
        val session = try {
            sessionFactory().apply { open(runtime) }
        } catch (e: Exception) {
            sessionFactory()
        }
        val tab = Tab(
            url = url,
            session = session
        )
        tabs.add(tab)
        currentTabIndex = tabs.size - 1
        try {
            session.loadUri(url)
        } catch (ignored: Exception) {}
        return tab
    }

    /**
     * Closes the tab with the specified ID.
     * Ensures the GeckoSession is properly closed to avoid memory leaks.
     * @param tabId The ID of the tab to close.
     */
    fun closeTab(tabId: String) {
        val tabIndex = tabs.indexOfFirst { it.id == tabId }
        if (tabIndex != -1) {
            try {
                tabs[tabIndex].session.close()
            } catch (ignored: Exception) {}
            tabs.removeAt(tabIndex)
            if (currentTabIndex >= tabs.size) {
                currentTabIndex = tabs.size - 1
            }
        }
    }

    /**
     * Closes all tabs and ensures all GeckoSessions are properly closed.
     */
    fun closeAllTabs() {
        tabs.forEach { tab ->
            try {
                tab.session.close()
            } catch (ignored: Exception) {}
        }
        tabs.clear()
        currentTabIndex = 0
        addNewTab()
    }

    /**
     * Switches to the tab at the specified index.
     * @param index The index of the tab to switch to.
     */
    fun switchToTab(index: Int) {
        if (index in tabs.indices) {
            currentTabIndex = index
        }
    }

    /**
     * Returns the current tab.
     */
    fun getCurrentTab(): Tab? {
        return tabs.getOrNull(currentTabIndex)
    }

    /**
     * Returns the list of all tabs.
     */
    fun getTabs(): List<Tab> {
        return tabs.toList()
    }

    /**
     * Returns the index of the current tab.
     */
    fun getCurrentTabIndex(): Int {
        return currentTabIndex
    }

    /**
     * Duplicates the current tab.
     */
    fun duplicateCurrentTab(): Tab? {
        val currentTab = getCurrentTab() ?: return null
        return addNewTab(currentTab.url)
    }

    /**
     * Reopens the last closed tab (if available).
     */
    fun reopenClosedTab() {
        // Placeholder for history tracking
    }

    /**
     * Saves the current tabs for session restoration.
     * @return List of URLs to restore.
     */
    fun saveTabsForRestoration(): List<String> {
        return tabs.map { it.url }
    }

    /**
     * Restores tabs from a list of URLs.
     * @param urls List of URLs to restore.
     */
    fun restoreTabs(urls: List<String>) {
        tabs.forEach { tab ->
            try {
                tab.session.close()
            } catch (ignored: Exception) {}
        }
        tabs.clear()
        urls.forEach { url ->
            addNewTab(url)
        }
        if (tabs.isNotEmpty()) {
            currentTabIndex = 0
        }
    }
}
