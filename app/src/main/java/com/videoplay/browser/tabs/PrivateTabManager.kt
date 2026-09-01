package com.videoplay.browser.tabs

import org.mozilla.geckoview.GeckoRuntime
import org.mozilla.geckoview.GeckoSession
import org.mozilla.geckoview.GeckoSessionSettings

/**
 * Manages private browsing tabs with separate sessions and no history.
 */
class PrivateTabManager(private val runtime: GeckoRuntime) {

    private val privateTabs = mutableListOf<Tab>()
    private var currentPrivateTabIndex = 0

    /**
     * Creates a new private tab.
     * @return The newly created private Tab.
     */
    fun createPrivateTab(url: String = "about:blank"): Tab {
        val settings = GeckoSessionSettings.Builder()
            .usePrivateMode(true) // Enable private mode
            .build()

        val session = GeckoSession(settings).apply {
            open(runtime)
        }

        val tab = Tab(
            url = url,
            isPrivate = true,
            session = session
        )

        privateTabs.add(tab)
        currentPrivateTabIndex = privateTabs.size - 1
        session.loadUri(url)
        return tab
    }

    /**
     * Closes a private tab and ensures the session is properly closed.
     * @param tabId The ID of the private tab to close.
     */
    fun closePrivateTab(tabId: String) {
        val tabIndex = privateTabs.indexOfFirst { it.id == tabId }
        if (tabIndex != -1) {
            privateTabs[tabIndex].session.close()
            privateTabs.removeAt(tabIndex)
            if (currentPrivateTabIndex >= privateTabs.size) {
                currentPrivateTabIndex = privateTabs.size - 1
            }
        }
    }

    /**
     * Closes all private tabs.
     */
    fun closeAllPrivateTabs() {
        privateTabs.forEach { tab ->
            tab.session.close()
        }
        privateTabs.clear()
        currentPrivateTabIndex = 0
    }

    /**
     * Switches to a private tab at the specified index.
     * @param index The index of the private tab to switch to.
     */
    fun switchToPrivateTab(index: Int) {
        if (index in privateTabs.indices) {
            currentPrivateTabIndex = index
        }
    }

    /**
     * Returns the current private tab.
     */
    fun getCurrentPrivateTab(): Tab? {
        return privateTabs.getOrNull(currentPrivateTabIndex)
    }

    /**
     * Returns the list of all private tabs.
     */
    fun getPrivateTabs(): List<Tab> {
        return privateTabs.toList()
    }

    /**
     * Returns the index of the current private tab.
     */
    fun getCurrentPrivateTabIndex(): Int {
        return currentPrivateTabIndex
    }

    /**
     * Checks if there are any private tabs open.
     */
    fun hasPrivateTabs(): Boolean {
        return privateTabs.isNotEmpty()
    }

    /**
     * Gets the number of private tabs.
     */
    fun getPrivateTabCount(): Int {
        return privateTabs.size
    }
}
