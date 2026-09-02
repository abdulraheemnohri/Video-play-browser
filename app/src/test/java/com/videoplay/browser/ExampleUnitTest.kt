package com.videoplay.browser

import com.videoplay.browser.tabs.Tab
import com.videoplay.browser.tabs.TabManager
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mozilla.geckoview.GeckoRuntime

/**
 * Example local unit test for TabManager.
 * This test verifies basic tab management functionality.
 */
class ExampleUnitTest {

    private lateinit var tabManager: TabManager
    private lateinit var mockRuntime: GeckoRuntime

    @Before
    fun setup() {
        mockRuntime = mock(GeckoRuntime::class.java)
        tabManager = TabManager(mockRuntime)
    }

    @Test
    fun testInitialTabCreation() {
        // Verify that one tab is created by default
        assertEquals(1, tabManager.getTabs().size)
    }

    @Test
    fun testAddNewTab() {
        val initialTabs = tabManager.getTabs()
        val newTab = tabManager.addNewTab("https://example.com")

        // Verify that a new tab was added
        assertEquals(initialTabs.size + 1, tabManager.getTabs().size)
        assertEquals("https://example.com", newTab.url)
    }

    @Test
    fun testCloseTab() {
        val tab = tabManager.addNewTab("https://example.com")
        val initialTabs = tabManager.getTabs()

        tabManager.closeTab(tab.id)

        // Verify that the tab was closed
        assertEquals(initialTabs.size - 1, tabManager.getTabs().size)
    }

    @Test
    fun testSwitchToTab() {
        val tab1 = tabManager.addNewTab("https://example1.com")
        val tab2 = tabManager.addNewTab("https://example2.com")

        // Switch to tab 1
        tabManager.switchToTab(0)
        assertEquals(tab1.id, tabManager.getCurrentTab()?.id)

        // Switch to tab 2
        tabManager.switchToTab(1)
        assertEquals(tab2.id, tabManager.getCurrentTab()?.id)
    }

    @Test
    fun testGetCurrentTab() {
        val tab = tabManager.addNewTab("https://example.com")
        val currentTab = tabManager.getCurrentTab()

        // Verify that we can get the current tab
        assertEquals(tab.id, currentTab?.id)
    }

    @Test
    fun testDuplicateCurrentTab() {
        val tab = tabManager.addNewTab("https://example.com")
        val initialTabs = tabManager.getTabs()

        tabManager.duplicateCurrentTab()

        // Verify that the tab was duplicated
        assertEquals(initialTabs.size + 1, tabManager.getTabs().size)
    }

    @Test
    fun testCloseAllTabs() {
        tabManager.addNewTab("https://example1.com")
        tabManager.addNewTab("https://example2.com")

        tabManager.closeAllTabs()

        // Verify that all tabs were closed and one new tab was created
        assertEquals(1, tabManager.getTabs().size)
    }

    @Test
    fun testSessionRestoration() {
        tabManager.addNewTab("https://example1.com")
        tabManager.addNewTab("https://example2.com")

        // Save tabs
        val urls = tabManager.saveTabsForRestoration()
        assertEquals(3, urls.size) // Initial tab + 2 new tabs

        // Close all tabs
        tabManager.closeAllTabs()

        // Restore tabs
        tabManager.restoreTabs(urls)
        assertEquals(3, tabManager.getTabs().size)
    }
}
