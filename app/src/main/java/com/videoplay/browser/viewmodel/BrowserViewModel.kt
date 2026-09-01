package com.videoplay.browser.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.videoplay.browser.browser.navigation.NavigationController
import com.videoplay.browser.gecko.runtime.GeckoRuntimeManager
import com.videoplay.browser.tabs.Tab
import com.videoplay.browser.tabs.TabManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for managing the browser state, tabs, and navigation.
 */
class BrowserViewModel : ViewModel() {

    private val runtime = GeckoRuntimeManager.getRuntime()!!
    private val tabManager = TabManager(runtime)

    // Current tab state
    private val _currentTab = MutableStateFlow<Tab?>(null)
    val currentTab: StateFlow<Tab?> = _currentTab.asStateFlow()

    // Navigation controller for the current tab
    private val _navigationController = MutableStateFlow<NavigationController?>(null)
    val navigationController: StateFlow<NavigationController?> = _navigationController.asStateFlow()

    // List of all tabs
    private val _tabs = MutableStateFlow<List<Tab>>(emptyList())
    val tabs: StateFlow<List<Tab>> = _tabs.asStateFlow()

    init {
        viewModelScope.launch {
            _currentTab.value = tabManager.getCurrentTab()
            _navigationController.value = _currentTab.value?.let { NavigationController(it.session) }
            _tabs.value = tabManager.getTabs()
        }
    }

    /**
     * Loads a URL in the current tab.
     * @param url The URL to load.
     */
    fun loadUrl(url: String) {
        val tab = tabManager.getCurrentTab() ?: return
        tab.session.loadUri(url)
        _currentTab.value = tab.copy(url = url)
        _navigationController.value = NavigationController(tab.session)
    }

    /**
     * Navigates back in the current tab.
     */
    fun goBack() {
        tabManager.getCurrentTab()?.let { tab ->
            NavigationController(tab.session).goBack()
            _currentTab.value = tab
        }
    }

    /**
     * Navigates forward in the current tab.
     */
    fun goForward() {
        tabManager.getCurrentTab()?.let { tab ->
            NavigationController(tab.session).goForward()
            _currentTab.value = tab
        }
    }

    /**
     * Reloads the current tab.
     */
    fun reload() {
        tabManager.getCurrentTab()?.let { tab ->
            NavigationController(tab.session).reload()
            _currentTab.value = tab
        }
    }

    /**
     * Adds a new tab with the specified URL.
     * @param url The URL to load in the new tab.
     */
    fun addNewTab(url: String = "about:blank") {
        val tab = tabManager.addNewTab(url)
        _currentTab.value = tab
        _navigationController.value = NavigationController(tab.session)
        _tabs.value = tabManager.getTabs()
    }

    /**
     * Closes the tab with the specified ID.
     * @param tabId The ID of the tab to close.
     */
    fun closeTab(tabId: String) {
        tabManager.closeTab(tabId)
        _currentTab.value = tabManager.getCurrentTab()
        _navigationController.value = _currentTab.value?.let { NavigationController(it.session) }
        _tabs.value = tabManager.getTabs()
    }

    /**
     * Switches to the tab at the specified index.
     * @param index The index of the tab to switch to.
     */
    fun switchToTab(index: Int) {
        tabManager.switchToTab(index)
        _currentTab.value = tabManager.getCurrentTab()
        _navigationController.value = _currentTab.value?.let { NavigationController(it.session) }
    }

    /**
     * Duplicates the current tab.
     */
    fun duplicateCurrentTab() {
        tabManager.duplicateCurrentTab()?.let { tab ->
            _currentTab.value = tab
            _navigationController.value = NavigationController(tab.session)
            _tabs.value = tabManager.getTabs()
        }
    }

    /**
     * Closes all tabs.
     */
    fun closeAllTabs() {
        tabManager.closeAllTabs()
        _currentTab.value = tabManager.getCurrentTab()
        _navigationController.value = _currentTab.value?.let { NavigationController(it.session) }
        _tabs.value = tabManager.getTabs()
    }

    /**
     * Updates the URL of the current tab.
     * @param url The new URL.
     */
    fun updateCurrentTabUrl(url: String) {
        _currentTab.value = _currentTab.value?.copy(url = url)
    }

    /**
     * Updates the title of the current tab.
     * @param title The new title.
     */
    fun updateCurrentTabTitle(title: String) {
        _currentTab.value = _currentTab.value?.copy(title = title)
    }
}
