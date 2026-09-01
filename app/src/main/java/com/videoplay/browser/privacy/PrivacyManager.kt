package com.videoplay.browser.privacy

import android.content.Context
import android.webkit.CookieManager
import com.videoplay.browser.core.preferences.SettingsRepository
import com.videoplay.browser.database.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Manages privacy-related settings and actions.
 * Provides functions to control tracking protection, HTTPS-only mode, and clear browsing data.
 */
class PrivacyManager(
    private val context: Context,
    private val settingsRepository: SettingsRepository,
    private val database: AppDatabase
) {

    /**
     * Tracking protection modes.
     */
    enum class TrackingProtectionMode {
        STANDARD,
        STRICT,
        CUSTOM
    }

    /**
     * Enables or disables tracking protection.
     * @param mode The tracking protection mode to enable.
     */
    suspend fun setTrackingProtection(mode: TrackingProtectionMode) {
        val modeString = when (mode) {
            TrackingProtectionMode.STANDARD -> "standard"
            TrackingProtectionMode.STRICT -> "strict"
            TrackingProtectionMode.CUSTOM -> "custom"
        }
        settingsRepository.setTrackingProtection(modeString)
        // Note: Actual tracking protection is handled by GeckoView
    }

    /**
     * Gets the current tracking protection mode.
     */
    suspend fun getTrackingProtectionMode(): TrackingProtectionMode {
        val modeString = settingsRepository.trackingProtection.value
        return when (modeString) {
            "strict" -> TrackingProtectionMode.STRICT
            "custom" -> TrackingProtectionMode.CUSTOM
            else -> TrackingProtectionMode.STANDARD
        }
    }

    /**
     * Enables or disables HTTPS-only mode.
     * @param enabled Whether to enable HTTPS-only mode.
     */
    suspend fun setHttpsOnlyMode(enabled: Boolean) {
        settingsRepository.setHttpsOnly(enabled)
        // Note: Actual HTTPS-only enforcement is handled by GeckoView
    }

    /**
     * Gets the current HTTPS-only mode status.
     */
    suspend fun isHttpsOnlyModeEnabled(): Boolean {
        return settingsRepository.httpsOnly.value
    }

    /**
     * Enables or disables clearing browsing data on exit.
     * @param enabled Whether to clear browsing data on exit.
     */
    suspend fun setClearDataOnExit(enabled: Boolean) {
        settingsRepository.setClearDataOnExit(enabled)
    }

    /**
     * Gets the current clear data on exit status.
     */
    suspend fun isClearDataOnExitEnabled(): Boolean {
        return settingsRepository.clearDataOnExit.value
    }

    /**
     * Clears all browsing data (history, cookies, cache).
     */
    fun clearBrowsingData() {
        CoroutineScope(Dispatchers.IO).launch {
            // Clear history
            database.historyDao().deleteAll()
            
            // Clear cookies
            CookieManager.getInstance().removeAllCookies(null)
            
            // Clear cache (GeckoView specific)
            // Note: This would require access to GeckoRuntime
        }
    }

    /**
     * Clears data for a specific site.
     * @param url The URL of the site to clear data for.
     */
    fun clearSiteData(url: String) {
        CoroutineScope(Dispatchers.IO).launch {
            // Clear history for this site
            database.historyDao().search("%$url%").value.forEach { history ->
                database.historyDao().delete(history)
            }
            
            // Clear cookies for this site
            // Note: This would require GeckoView-specific cookie management
        }
    }

    /**
     * Gets the privacy settings summary.
     */
    suspend fun getPrivacySettingsSummary(): Map<String, Any> {
        return mapOf(
            "trackingProtection" to getTrackingProtectionMode().name,
            "httpsOnly" to isHttpsOnlyModeEnabled(),
            "clearDataOnExit" to isClearDataOnExitEnabled()
        )
    }
}
