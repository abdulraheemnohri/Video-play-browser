package com.videoplay.browser.privacy

import org.mozilla.geckoview.GeckoSession
import org.mozilla.geckoview.GeckoSessionSettings

/**
 * Manages tracking protection settings for GeckoView.
 * Provides different levels of tracking protection (Standard, Strict, Custom).
 */
class TrackingProtectionManager {

    /**
     * Tracking protection levels.
     */
    enum class TrackingProtectionLevel {
        /**
         * Standard protection: Blocks known trackers.
         */
        STANDARD,

        /**
         * Strict protection: Blocks all trackers, may break some websites.
         */
        STRICT,

        /**
         * Custom protection: User-defined settings.
         */
        CUSTOM
    }

    /**
     * Applies tracking protection settings to a GeckoSession.
     * @param session The GeckoSession to apply settings to.
     * @param level The tracking protection level to apply.
     */
    fun applyTrackingProtection(session: GeckoSession, level: TrackingProtectionLevel) {
        val settings = session.settings
        
        when (level) {
            TrackingProtectionLevel.STANDARD -> {
                // Standard protection: Block known trackers
                settings.setTrackingProtectionEnabled(true)
                settings.setStrictTrackingProtectionEnabled(false)
            }
            TrackingProtectionLevel.STRICT -> {
                // Strict protection: Block all trackers
                settings.setTrackingProtectionEnabled(true)
                settings.setStrictTrackingProtectionEnabled(true)
            }
            TrackingProtectionLevel.CUSTOM -> {
                // Custom protection: User-defined (for now, use standard)
                settings.setTrackingProtectionEnabled(true)
                settings.setStrictTrackingProtectionEnabled(false)
            }
        }
    }

    /**
     * Creates a new GeckoSession with tracking protection enabled.
     * @param level The tracking protection level to apply.
     * @return A new GeckoSession with tracking protection settings.
     */
    fun createProtectedSession(level: TrackingProtectionLevel): GeckoSession {
        val settings = GeckoSessionSettings.Builder()
            .setTrackingProtectionEnabled(true)
            .setStrictTrackingProtectionEnabled(level == TrackingProtectionLevel.STRICT)
            .build()
        
        return GeckoSession(settings)
    }

    /**
     * Updates tracking protection settings for an existing session.
     * @param session The GeckoSession to update.
     * @param enabled Whether tracking protection is enabled.
     * @param strict Whether to use strict tracking protection.
     */
    fun updateTrackingProtection(
        session: GeckoSession,
        enabled: Boolean,
        strict: Boolean = false
    ) {
        session.settings.setTrackingProtectionEnabled(enabled)
        session.settings.setStrictTrackingProtectionEnabled(strict && enabled)
    }

    /**
     * Gets the current tracking protection level for a session.
     * @param session The GeckoSession to check.
     * @return The current tracking protection level.
     */
    fun getTrackingProtectionLevel(session: GeckoSession): TrackingProtectionLevel {
        return if (session.settings.isTrackingProtectionEnabled) {
            if (session.settings.isStrictTrackingProtectionEnabled) {
                TrackingProtectionLevel.STRICT
            } else {
                TrackingProtectionLevel.STANDARD
            }
        } else {
            TrackingProtectionLevel.CUSTOM
        }
    }

    /**
     * Enables tracking protection for a session.
     * @param session The GeckoSession to update.
     * @param strict Whether to use strict tracking protection.
     */
    fun enableTrackingProtection(session: GeckoSession, strict: Boolean = false) {
        session.settings.setTrackingProtectionEnabled(true)
        session.settings.setStrictTrackingProtectionEnabled(strict)
    }

    /**
     * Disables tracking protection for a session.
     * @param session The GeckoSession to update.
     */
    fun disableTrackingProtection(session: GeckoSession) {
        session.settings.setTrackingProtectionEnabled(false)
        session.settings.setStrictTrackingProtectionEnabled(false)
    }

    /**
     * Gets a description of the tracking protection level.
     * @param level The tracking protection level.
     * @return A human-readable description.
     */
    fun getTrackingProtectionDescription(level: TrackingProtectionLevel): String {
        return when (level) {
            TrackingProtectionLevel.STANDARD -> "Blocks known trackers that collect data about your browsing activity."
            TrackingProtectionLevel.STRICT -> "Blocks all trackers, may break some websites that rely on tracking."
            TrackingProtectionLevel.CUSTOM -> "Custom tracking protection settings."
        }
    }

    /**
     * Gets the list of all tracking protection levels.
     */
    fun getAllTrackingProtectionLevels(): List<TrackingProtectionLevel> {
        return listOf(
            TrackingProtectionLevel.STANDARD,
            TrackingProtectionLevel.STRICT,
            TrackingProtectionLevel.CUSTOM
        )
    }
}
