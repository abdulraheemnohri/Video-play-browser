package com.videoplay.browser.tabs

import org.mozilla.geckoview.GeckoSession
import java.util.UUID

/**
 * Represents a browser tab with its associated GeckoSession.
 * @param id Unique identifier for the tab.
 * @param title Title of the webpage loaded in the tab.
 * @param url URL of the webpage loaded in the tab.
 * @param isPrivate Whether the tab is in private browsing mode.
 * @param session The GeckoSession associated with the tab.
 */
data class Tab(
    val id: String = UUID.randomUUID().toString(),
    var title: String = "",
    var url: String = "about:blank",
    val isPrivate: Boolean = false,
    val session: GeckoSession = GeckoSession()
)
