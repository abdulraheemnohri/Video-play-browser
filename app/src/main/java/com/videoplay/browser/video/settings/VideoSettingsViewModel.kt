package com.videoplay.browser.video.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.videoplay.browser.core.preferences.SettingsRepository
import kotlinx.coroutines.launch

/**
 * ViewModel for managing video settings.
 */
class VideoSettingsViewModel(private val settingsRepository: SettingsRepository) : ViewModel() {

    /**
     * Updates the autoplay setting.
     * @param mode The autoplay mode ("always", "wifi_only", "never").
     */
    fun setAutoPlay(mode: String) {
        viewModelScope.launch {
            settingsRepository.setAutoPlay(mode)
        }
    }

    /**
     * Updates the default playback speed.
     * @param speed The playback speed (e.g., 1.0 for normal speed).
     */
    fun setDefaultPlaybackSpeed(speed: Float) {
        viewModelScope.launch {
            settingsRepository.setDefaultPlaybackSpeed(speed)
        }
    }

    /**
     * Updates the remember playback speed setting.
     * @param enabled Whether to remember the playback speed.
     */
    fun setRememberPlaybackSpeed(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.setRememberPlaybackSpeed(enabled)
        }
    }

    /**
     * Updates the remember playback position setting.
     * @param enabled Whether to remember the playback position.
     */
    fun setRememberPlaybackPosition(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.setRememberPlaybackPosition(enabled)
        }
    }

    /**
     * Updates the Picture-in-Picture setting.
     * @param enabled Whether to enable PiP.
     */
    fun setEnablePiP(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.setEnablePiP(enabled)
        }
    }

    /**
     * Updates the mini player setting.
     * @param enabled Whether to enable the mini player.
     */
    fun setEnableMiniPlayer(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.setEnableMiniPlayer(enabled)
        }
    }
}
