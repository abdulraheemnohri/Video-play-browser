package com.videoplay.browser.browser.navigation

import org.mozilla.geckoview.GeckoSession

/**
 * Controls navigation for a GeckoSession (e.g., back, forward, reload).
 */
class NavigationController(private val session: GeckoSession) {

    /**
     * Loads the specified URL in the session.
     * @param url The URL to load.
     */
    fun loadUrl(url: String) {
        session.loadUri(url)
    }

    /**
     * Navigates back in the session's history.
     */
    fun goBack() {
        if (session.canGoBack) {
            session.goBack()
        }
    }

    /**
     * Navigates forward in the session's history.
     */
    fun goForward() {
        if (session.canGoForward) {
            session.goForward()
        }
    }

    /**
     * Reloads the current page.
     */
    fun reload() {
        session.reload()
    }

    /**
     * Stops loading the current page.
     */
    fun stop() {
        session.stop()
    }

    /**
     * Checks if the session can go back.
     */
    val canGoBack: Boolean
        get() = session.canGoBack

    /**
     * Checks if the session can go forward.
     */
    val canGoForward: Boolean
        get() = session.canGoForward

    /**
     * Gets the current URL of the session.
     */
    val currentUrl: String?
        get() = session.uri

    /**
     * Gets the title of the current page.
     */
    val currentTitle: String?
        get() = session.title
}
