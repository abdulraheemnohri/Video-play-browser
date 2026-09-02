package com.videoplay.browser.privacy

import org.mozilla.geckoview.GeckoSession
import org.mozilla.geckoview.GeckoSessionSettings

/**
 * Manages HTTPS-only mode for GeckoView.
 * Forces all connections to use HTTPS for a more secure browsing experience.
 */
class HttpsOnlyManager {

    /**
     * Enables HTTPS-only mode for a GeckoSession.
     * @param session The GeckoSession to update.
     */
    fun enableHttpsOnly(session: GeckoSession) {
        val settings = session.settings
        settings.setHttpsOnlyMode(true)
    }

    /**
     * Disables HTTPS-only mode for a GeckoSession.
     * @param session The GeckoSession to update.
     */
    fun disableHttpsOnly(session: GeckoSession) {
        val settings = session.settings
        settings.setHttpsOnlyMode(false)
    }

    /**
     * Checks if HTTPS-only mode is enabled for a session.
     * @param session The GeckoSession to check.
     * @return True if HTTPS-only mode is enabled, false otherwise.
     */
    fun isHttpsOnlyEnabled(session: GeckoSession): Boolean {
        return session.settings.isHttpsOnlyModeEnabled
    }

    /**
     * Creates a new GeckoSession with HTTPS-only mode enabled.
     * @return A new GeckoSession with HTTPS-only mode enabled.
     */
    fun createHttpsOnlySession(): GeckoSession {
        val settings = GeckoSessionSettings.Builder()
            .setHttpsOnlyMode(true)
            .build()
        
        return GeckoSession(settings)
    }

    /**
     * Updates HTTPS-only mode for an existing session.
     * @param session The GeckoSession to update.
     * @param enabled Whether HTTPS-only mode is enabled.
     */
    fun setHttpsOnly(session: GeckoSession, enabled: Boolean) {
        session.settings.setHttpsOnlyMode(enabled)
    }

    /**
     * Gets a description of HTTPS-only mode.
     * @return A human-readable description.
     */
    fun getHttpsOnlyDescription(): String {
        return "Forces all connections to use HTTPS for a more secure browsing experience. " +
               "Websites that don't support HTTPS will not load."
    }

    /**
     * Handles HTTPS-only mode errors (e.g., when a site doesn't support HTTPS).
     * @param session The GeckoSession where the error occurred.
     * @param url The URL that failed to load.
     */
    fun handleHttpsOnlyError(session: GeckoSession, url: String) {
        // For now, just log the error
        // In a real implementation, you might want to show a user-friendly error page
        println("HTTPS-Only Error: Could not load $url because it doesn't support HTTPS")
    }

    /**
     * Temporarily disables HTTPS-only mode for a specific URL.
     * This is useful for sites that don't support HTTPS but the user wants to visit.
     * @param session The GeckoSession to update.
     * @param url The URL to allow HTTP for.
     */
    fun allowHttpForUrl(session: GeckoSession, url: String) {
        // Note: GeckoView doesn't have a direct API for this
        // This would require custom handling or user confirmation
        println("Allowing HTTP for $url (temporary)")
    }
}
