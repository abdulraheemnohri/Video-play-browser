package com.videoplay.browser.testing

import com.videoplay.browser.tabs.Tab
import com.videoplay.browser.tabs.TabManager
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mozilla.geckoview.GeckoRuntime

/**
 * Example unit test for TabManager.
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
    fun testAddNewTab() {
        val initialTabs = tabManager.getTabs()
        val newTab = tabManager.addNewTab("https://example.com")

        assertEquals(initialTabs.size + 1, tabManager.getTabs().size)
        assertEquals("https://example.com", newTab.url)
    }

    @Test
    fun testCloseTab() {
        val tab = tabManager.addNewTab("https://example.com")
        val initialTabs = tabManager.getTabs()

        tabManager.closeTab(tab.id)

        assertEquals(initialTabs.size - 1, tabManager.getTabs().size)
    }

    @Test
    fun testSwitchToTab() {
        val tab1 = tabManager.addNewTab("https://example1.com")
        val tab2 = tabManager.addNewTab("https://example2.com")

        tabManager.switchToTab(0)
        assertEquals(tab1.id, tabManager.getCurrentTab()?.id)

        tabManager.switchToTab(1)
        assertEquals(tab2.id, tabManager.getCurrentTab()?.id)
    }

    @Test
    fun testGetCurrentTab() {
        val tab = tabManager.addNewTab("https://example.com")
        val currentTab = tabManager.getCurrentTab()

        assertEquals(tab.id, currentTab?.id)
    }

    @Test
    fun testDuplicateCurrentTab() {
        val tab = tabManager.addNewTab("https://example.com")
        val initialTabs = tabManager.getTabs()

        tabManager.duplicateCurrentTab()

        assertEquals(initialTabs.size + 1, tabManager.getTabs().size)
    }

    @Test
    fun testCloseAllTabs() {
        tabManager.addNewTab("https://example1.com")
        tabManager.addNewTab("https://example2.com")

        tabManager.closeAllTabs()

        assertEquals(1, tabManager.getTabs().size) // One default tab remains
    }
}
