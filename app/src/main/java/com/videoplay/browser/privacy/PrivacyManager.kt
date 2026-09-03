package com.videoplay.browser.privacy

import android.content.Context
import android.webkit.CookieManager
import com.videoplay.browser.core.preferences.SettingsRepository
import com.videoplay.browser.database.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
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

    enum class TrackingProtectionMode {
        STANDARD,
        STRICT,
        CUSTOM
    }

    suspend fun setTrackingProtection(mode: TrackingProtectionMode) {
        val modeString = when (mode) {
            TrackingProtectionMode.STANDARD -> "standard"
            TrackingProtectionMode.STRICT -> "strict"
            TrackingProtectionMode.CUSTOM -> "custom"
        }
        settingsRepository.setTrackingProtection(modeString)
    }

    suspend fun getTrackingProtectionMode(): TrackingProtectionMode {
        val modeString = settingsRepository.trackingProtection.first()
        return when (modeString) {
            "strict" -> TrackingProtectionMode.STRICT
            "custom" -> TrackingProtectionMode.CUSTOM
            else -> TrackingProtectionMode.STANDARD
        }
    }

    suspend fun setHttpsOnlyMode(enabled: Boolean) {
        settingsRepository.setHttpsOnly(enabled)
    }

    suspend fun isHttpsOnlyModeEnabled(): Boolean {
        return settingsRepository.httpsOnly.first()
    }

    suspend fun setClearDataOnExit(enabled: Boolean) {
        settingsRepository.setClearDataOnExit(enabled)
    }

    suspend fun isClearDataOnExitEnabled(): Boolean {
        return settingsRepository.clearDataOnExit.first()
    }

    fun clearBrowsingData() {
        CoroutineScope(Dispatchers.IO).launch {
            database.historyDao().deleteAll()
            CookieManager.getInstance().removeAllCookies(null)
        }
    }

    fun clearSiteData(url: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val entries = database.historyDao().search("%$url%").first()
            entries.forEach { history ->
                database.historyDao().delete(history)
            }
        }
    }

    suspend fun getPrivacySettingsSummary(): Map<String, Any> {
        return mapOf(
            "trackingProtection" to getTrackingProtectionMode().name,
            "httpsOnly" to isHttpsOnlyModeEnabled(),
            "clearDataOnExit" to isClearDataOnExitEnabled()
        )
    }
}
