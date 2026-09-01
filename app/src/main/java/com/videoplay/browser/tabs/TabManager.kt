package com.videoplay.browser.tabs

import org.mozilla.geckoview.GeckoRuntime

/**
 * Manages browser tabs and their associated GeckoSessions.
 * Provides functionality to add, remove, and switch between tabs.
 */
class TabManager(private val runtime: GeckoRuntime) {

    private val tabs = mutableListOf<Tab>()
    private var currentTabIndex = 0

    init {
        // Create the first tab by default
        addNewTab()
    }

    /**
     * Adds a new tab with the specified URL.
     * @param url The URL to load in the new tab.
     * @return The newly created Tab.
     */
    fun addNewTab(url: String = "about:blank"): Tab {
        val tab = Tab(
            url = url,
            session = GeckoSession().apply {
                open(runtime)
                loadUri(url)
            }
        )
        tabs.add(tab)
        currentTabIndex = tabs.size - 1
        return tab
    }

    /**
     * Closes the tab with the specified ID.
     * @param tabId The ID of the tab to close.
     */
    fun closeTab(tabId: String) {
        val tabIndex = tabs.indexOfFirst { it.id == tabId }
        if (tabIndex != -1) {
            tabs[tabIndex].session.close()
            tabs.removeAt(tabIndex)
            if (currentTabIndex >= tabs.size) {
                currentTabIndex = tabs.size - 1
            }
        }
    }

    /**
     * Closes all tabs.
     */
    fun closeAllTabs() {
        tabs.forEach { it.session.close() }
        tabs.clear()
        currentTabIndex = 0
        addNewTab() // Create a new tab after closing all
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
        // TODO: Implement reopening closed tabs (requires history tracking)
    }
}
