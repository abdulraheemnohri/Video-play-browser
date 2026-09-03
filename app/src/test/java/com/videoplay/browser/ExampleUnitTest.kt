package com.videoplay.browser

import com.videoplay.browser.tabs.TabManager
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mozilla.geckoview.GeckoRuntime
import org.mozilla.geckoview.GeckoSession

/**
 * Unit test for TabManager.
 */
class ExampleUnitTest {

    private lateinit var tabManager: TabManager
    private lateinit var mockRuntime: GeckoRuntime

    @Before
    fun setup() {
        mockRuntime = mock(GeckoRuntime::class.java)
        val mockSessionFactory = { mock(GeckoSession::class.java) }
        tabManager = TabManager(mockRuntime, mockSessionFactory)
    }

    @Test
    fun testInitialTabCreation() {
        assertEquals(1, tabManager.getTabs().size)
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

        tabManager.switchToTab(1)
        assertEquals(tab1.id, tabManager.getTabs()[1].id)

        tabManager.switchToTab(2)
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
        tabManager.addNewTab("https://example.com")
        val initialTabs = tabManager.getTabs()

        tabManager.duplicateCurrentTab()

        assertEquals(initialTabs.size + 1, tabManager.getTabs().size)
    }

    @Test
    fun testCloseAllTabs() {
        tabManager.addNewTab("https://example1.com")
        tabManager.addNewTab("https://example2.com")

        tabManager.closeAllTabs()

        assertEquals(1, tabManager.getTabs().size)
    }

    @Test
    fun testSessionRestoration() {
        tabManager.addNewTab("https://example1.com")
        tabManager.addNewTab("https://example2.com")

        val urls = tabManager.saveTabsForRestoration()
        assertEquals(3, urls.size)

        tabManager.closeAllTabs()

        tabManager.restoreTabs(urls)
        assertEquals(3, tabManager.getTabs().size)
    }
}
