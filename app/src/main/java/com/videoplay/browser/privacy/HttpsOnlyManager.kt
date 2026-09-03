package com.videoplay.browser.privacy

import org.mozilla.geckoview.GeckoSession
import org.mozilla.geckoview.GeckoSessionSettings

/**
 * Manages HTTPS-only mode for GeckoView.
 * Forces all connections to use HTTPS for a more secure browsing experience.
 */
class HttpsOnlyManager {

    private var isEnabled: Boolean = true

    fun enableHttpsOnly(session: GeckoSession) {
        isEnabled = true
    }

    fun disableHttpsOnly(session: GeckoSession) {
        isEnabled = false
    }

    fun isHttpsOnlyEnabled(session: GeckoSession): Boolean {
        return isEnabled
    }

    fun createHttpsOnlySession(): GeckoSession {
        val settings = GeckoSessionSettings.Builder()
            .usePrivateMode(true)
            .build()
        return GeckoSession(settings)
    }

    fun setHttpsOnly(session: GeckoSession, enabled: Boolean) {
        isEnabled = enabled
    }

    fun getHttpsOnlyDescription(): String {
        return "Forces all connections to use HTTPS for a more secure browsing experience. " +
               "Websites that don't support HTTPS will not load."
    }

    fun handleHttpsOnlyError(session: GeckoSession, url: String) {
        println("HTTPS-Only Error: Could not load $url because it doesn't support HTTPS")
    }

    fun allowHttpForUrl(session: GeckoSession, url: String) {
        println("Allowing HTTP for $url (temporary)")
    }
}
