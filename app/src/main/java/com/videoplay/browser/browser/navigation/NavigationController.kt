package com.videoplay.browser.browser.navigation

import org.mozilla.geckoview.GeckoSession

/**
 * Controls navigation for a GeckoSession (e.g., back, forward, reload).
 */
class NavigationController(private val session: GeckoSession) {

    var canGoBack: Boolean = false
        private set
    var canGoForward: Boolean = false
        private set
    var currentUrl: String? = null
        private set
    var currentTitle: String? = null
        private set

    fun loadUrl(url: String) {
        currentUrl = url
        session.loadUri(url)
    }

    fun goBack() {
        session.goBack()
    }

    fun goForward() {
        session.goForward()
    }

    fun reload() {
        session.reload()
    }

    fun stop() {
        session.stop()
    }
}
