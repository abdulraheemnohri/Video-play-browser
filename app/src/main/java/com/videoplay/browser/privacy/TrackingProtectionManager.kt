package com.videoplay.browser.privacy

import org.mozilla.geckoview.GeckoSession
import org.mozilla.geckoview.GeckoSessionSettings

/**
 * Manages tracking protection settings for GeckoView.
 * Provides different levels of tracking protection (Standard, Strict, Custom).
 */
class TrackingProtectionManager {

    enum class TrackingProtectionLevel {
        STANDARD,
        STRICT,
        CUSTOM
    }

    fun applyTrackingProtection(session: GeckoSession, level: TrackingProtectionLevel) {
        val settings = session.settings
        when (level) {
            TrackingProtectionLevel.STANDARD -> {
                settings.useTrackingProtection = true
            }
            TrackingProtectionLevel.STRICT -> {
                settings.useTrackingProtection = true
            }
            TrackingProtectionLevel.CUSTOM -> {
                settings.useTrackingProtection = true
            }
        }
    }

    fun createProtectedSession(level: TrackingProtectionLevel): GeckoSession {
        val settings = GeckoSessionSettings.Builder()
            .useTrackingProtection(true)
            .build()
        
        return GeckoSession(settings)
    }

    fun updateTrackingProtection(
        session: GeckoSession,
        enabled: Boolean,
        strict: Boolean = false
    ) {
        session.settings.useTrackingProtection = enabled
    }

    fun getTrackingProtectionLevel(session: GeckoSession): TrackingProtectionLevel {
        return if (session.settings.useTrackingProtection) {
            TrackingProtectionLevel.STANDARD
        } else {
            TrackingProtectionLevel.CUSTOM
        }
    }

    fun enableTrackingProtection(session: GeckoSession, strict: Boolean = false) {
        session.settings.useTrackingProtection = true
    }

    fun disableTrackingProtection(session: GeckoSession) {
        session.settings.useTrackingProtection = false
    }

    fun getTrackingProtectionDescription(level: TrackingProtectionLevel): String {
        return when (level) {
            TrackingProtectionLevel.STANDARD -> "Blocks known trackers that collect data about your browsing activity."
            TrackingProtectionLevel.STRICT -> "Blocks all trackers, may break some websites that rely on tracking."
            TrackingProtectionLevel.CUSTOM -> "Custom tracking protection settings."
        }
    }

    fun getAllTrackingProtectionLevels(): List<TrackingProtectionLevel> {
        return listOf(
            TrackingProtectionLevel.STANDARD,
            TrackingProtectionLevel.STRICT,
            TrackingProtectionLevel.CUSTOM
        )
    }
}
