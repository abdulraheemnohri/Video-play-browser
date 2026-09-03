package com.videoplay.browser.video.quality

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.videoplay.browser.core.preferences.SettingsRepository
import kotlinx.coroutines.flow.first

/**
 * Manages video quality settings and automatic quality selection.
 */
class VideoQualityManager(
    private val context: Context,
    private val settingsRepository: SettingsRepository
) {

    /**
     * Video quality options.
     */
    enum class VideoQuality {
        AUTO,
        Q2160P, // 4K
        Q1440P, // 2K
        Q1080P, // Full HD
        Q720P,  // HD
        Q480P,  // SD
        Q360P   // Low
    }

    /**
     * Cellular quality options.
     */
    enum class CellularQuality {
        AUTO,
        DATA_SAVER,
        BEST_AVAILABLE
    }

    /**
     * Wi-Fi quality options.
     */
    enum class WiFiQuality {
        AUTO,
        BEST_AVAILABLE
    }

    /**
     * Gets the preferred video quality.
     */
    suspend fun getPreferredQuality(): VideoQuality {
        val qualityString = settingsRepository.autoPlay.first()
        return when (qualityString) {
            "2160p" -> VideoQuality.Q2160P
            "1440p" -> VideoQuality.Q1440P
            "1080p" -> VideoQuality.Q1080P
            "720p" -> VideoQuality.Q720P
            "480p" -> VideoQuality.Q480P
            "360p" -> VideoQuality.Q360P
            else -> VideoQuality.AUTO
        }
    }

    /**
     * Sets the preferred video quality.
     * @param quality The preferred video quality.
     */
    suspend fun setPreferredQuality(quality: VideoQuality) {
        val qualityString = when (quality) {
            VideoQuality.Q2160P -> "2160p"
            VideoQuality.Q1440P -> "1440p"
            VideoQuality.Q1080P -> "1080p"
            VideoQuality.Q720P -> "720p"
            VideoQuality.Q480P -> "480p"
            VideoQuality.Q360P -> "360p"
            VideoQuality.AUTO -> "auto"
        }
        settingsRepository.setAutoPlay(qualityString)
    }

    /**
     * Gets the cellular quality setting.
     */
    suspend fun getCellularQuality(): CellularQuality {
        return CellularQuality.AUTO
    }

    /**
     * Sets the cellular quality setting.
     * @param quality The cellular quality setting.
     */
    suspend fun setCellularQuality(quality: CellularQuality) {
    }

    /**
     * Gets the Wi-Fi quality setting.
     */
    suspend fun getWiFiQuality(): WiFiQuality {
        return WiFiQuality.BEST_AVAILABLE
    }

    /**
     * Sets the Wi-Fi quality setting.
     * @param quality The Wi-Fi quality setting.
     */
    suspend fun setWiFiQuality(quality: WiFiQuality) {
    }

    /**
     * Gets the recommended quality based on current network conditions.
     */
    suspend fun getRecommendedQuality(): VideoQuality {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return VideoQuality.AUTO
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return VideoQuality.AUTO

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                when (getWiFiQuality()) {
                    WiFiQuality.BEST_AVAILABLE -> VideoQuality.Q1080P
                    WiFiQuality.AUTO -> VideoQuality.AUTO
                }
            }
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                when (getCellularQuality()) {
                    CellularQuality.BEST_AVAILABLE -> VideoQuality.Q720P
                    CellularQuality.DATA_SAVER -> VideoQuality.Q360P
                    CellularQuality.AUTO -> VideoQuality.AUTO
                }
            }
            else -> VideoQuality.AUTO
        }
    }

    /**
     * Gets the quality options for display.
     */
    fun getQualityOptions(): List<Pair<VideoQuality, String>> {
        return listOf(
            Pair(VideoQuality.AUTO, "Auto"),
            Pair(VideoQuality.Q2160P, "2160p (4K)"),
            Pair(VideoQuality.Q1440P, "1440p (2K)"),
            Pair(VideoQuality.Q1080P, "1080p (Full HD)"),
            Pair(VideoQuality.Q720P, "720p (HD)"),
            Pair(VideoQuality.Q480P, "480p (SD)"),
            Pair(VideoQuality.Q360P, "360p (Low)")
        )
    }

    /**
     * Gets the cellular quality options for display.
     */
    fun getCellularQualityOptions(): List<Pair<CellularQuality, String>> {
        return listOf(
            Pair(CellularQuality.AUTO, "Auto"),
            Pair(CellularQuality.DATA_SAVER, "Data Saver"),
            Pair(CellularQuality.BEST_AVAILABLE, "Best Available")
        )
    }

    /**
     * Gets the Wi-Fi quality options for display.
     */
    fun getWiFiQualityOptions(): List<Pair<WiFiQuality, String>> {
        return listOf(
            Pair(WiFiQuality.AUTO, "Auto"),
            Pair(WiFiQuality.BEST_AVAILABLE, "Best Available")
        )
    }

    /**
     * Resets all quality settings to default.
     */
    suspend fun resetToDefaults() {
        setPreferredQuality(VideoQuality.AUTO)
        setCellularQuality(CellularQuality.AUTO)
        setWiFiQuality(WiFiQuality.BEST_AVAILABLE)
    }
}
