package com.videoplay.browser.video.playback

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for managing the state of the video player.
 */
class VideoPlayerViewModel : ViewModel() {

    // Video state
    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _currentPosition = MutableStateFlow(0L)
    val currentPosition: StateFlow<Long> = _currentPosition.asStateFlow()

    private val _duration = MutableStateFlow(0L)
    val duration: StateFlow<Long> = _duration.asStateFlow()

    private val _volume = MutableStateFlow(1.0f)
    val volume: StateFlow<Float> = _volume.asStateFlow()

    private val _isMuted = MutableStateFlow(false)
    val isMuted: StateFlow<Boolean> = _isMuted.asStateFlow()

    private val _playbackSpeed = MutableStateFlow(1.0f)
    val playbackSpeed: StateFlow<Float> = _playbackSpeed.asStateFlow()

    private val _isFullscreen = MutableStateFlow(false)
    val isFullscreen: StateFlow<Boolean> = _isFullscreen.asStateFlow()

    private val _isPiP = MutableStateFlow(false)
    val isPiP: StateFlow<Boolean> = _isPiP.asStateFlow()

    private val _videoTitle = MutableStateFlow("")
    val videoTitle: StateFlow<String> = _videoTitle.asStateFlow()

    /**
     * Toggles play/pause state.
     */
    fun togglePlayPause() {
        _isPlaying.value = !_isPlaying.value
    }

    /**
     * Updates the current playback position.
     * @param position The new position in milliseconds.
     */
    fun updatePosition(position: Long) {
        _currentPosition.value = position
    }

    /**
     * Updates the video duration.
     * @param duration The duration of the video in milliseconds.
     */
    fun updateDuration(duration: Long) {
        _duration.value = duration
    }

    /**
     * Updates the volume.
     * @param volume The new volume (0.0 to 1.0).
     */
    fun updateVolume(volume: Float) {
        _volume.value = volume.coerceIn(0f, 1f)
    }

    /**
     * Toggles mute state.
     */
    fun toggleMute() {
        _isMuted.value = !_isMuted.value
    }

    /**
     * Updates the playback speed.
     * @param speed The new playback speed (e.g., 1.0 for normal speed).
     */
    fun updatePlaybackSpeed(speed: Float) {
        _playbackSpeed.value = speed.coerceIn(0.25f, 2f)
    }

    /**
     * Toggles fullscreen mode.
     */
    fun toggleFullscreen() {
        _isFullscreen.value = !_isFullscreen.value
    }

    /**
     * Updates PiP mode.
     * @param isPiP Whether PiP mode is enabled.
     */
    fun updatePiP(isPiP: Boolean) {
        _isPiP.value = isPiP
    }

    /**
     * Updates the video title.
     * @param title The title of the video.
     */
    fun updateVideoTitle(title: String) {
        _videoTitle.value = title
    }

    /**
     * Seeks to a specific position in the video.
     * @param position The position to seek to in milliseconds.
     */
    fun seekTo(position: Long) {
        _currentPosition.value = position.coerceAtMost(_duration.value)
    }

    /**
     * Seeks forward by a specified amount.
     * @param milliseconds The amount to seek forward in milliseconds.
     */
    fun seekForward(milliseconds: Long) {
        _currentPosition.value = (_currentPosition.value + milliseconds).coerceAtMost(_duration.value)
    }

    /**
     * Seeks backward by a specified amount.
     * @param milliseconds The amount to seek backward in milliseconds.
     */
    fun seekBackward(milliseconds: Long) {
        _currentPosition.value = (_currentPosition.value - milliseconds).coerceAtLeast(0L)
    }

    /**
     * Resets the video player state.
     */
    fun reset() {
        viewModelScope.launch {
            _isPlaying.value = false
            _currentPosition.value = 0L
            _duration.value = 0L
            _volume.value = 1.0f
            _isMuted.value = false
            _playbackSpeed.value = 1.0f
            _isFullscreen.value = false
            _isPiP.value = false
            _videoTitle.value = ""
        }
    }
}
