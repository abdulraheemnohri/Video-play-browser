package com.videoplay.browser.video.media

import android.content.Context
import android.media.session.MediaSession
import android.media.session.PlaybackState
import android.os.Build
import androidx.annotation.RequiresApi

/**
 * Manages Android MediaSession for video playback.
 * Provides integration with lock screen and Bluetooth controls.
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class MediaSessionManager(private val context: Context) {

    private var mediaSession: MediaSession? = null
    private var playbackState: PlaybackState? = null

    init {
        createMediaSession()
    }

    /**
     * Creates a MediaSession instance.
     */
    private fun createMediaSession() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mediaSession = MediaSession(context, "VIDEOPlayBrowser").apply {
                setFlags(MediaSession.FLAG_HANDLES_MEDIA_BUTTONS or MediaSession.FLAG_HANDLES_TRANSPORT_CONTROLS)
                setPlaybackState(
                    PlaybackState.Builder()
                        .setState(PlaybackState.STATE_NONE, 0, 0f)
                        .setActions(
                            PlaybackState.ACTION_PLAY or
                                    PlaybackState.ACTION_PAUSE or
                                    PlaybackState.ACTION_PLAY_PAUSE or
                                    PlaybackState.ACTION_SKIP_TO_NEXT or
                                    PlaybackState.ACTION_SKIP_TO_PREVIOUS
                        )
                        .build()
                )
            }
        }
    }

    /**
     * Updates the playback state.
     * @param state The new playback state (e.g., STATE_PLAYING, STATE_PAUSED).
     * @param position The current playback position in milliseconds.
     * @param speed The playback speed (e.g., 1.0 for normal speed).
     */
    fun updatePlaybackState(state: Int, position: Long, speed: Float) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            playbackState = PlaybackState.Builder()
                .setState(state, position, speed)
                .setActions(
                    PlaybackState.ACTION_PLAY or
                            PlaybackState.ACTION_PAUSE or
                            PlaybackState.ACTION_PLAY_PAUSE or
                            PlaybackState.ACTION_SKIP_TO_NEXT or
                            PlaybackState.ACTION_SKIP_TO_PREVIOUS
                )
                .build()
            mediaSession?.setPlaybackState(playbackState)
        }
    }

    /**
     * Sets the metadata for the current media.
     * @param title The title of the media.
     * @param duration The duration of the media in milliseconds.
     */
    fun setMediaMetadata(title: String, duration: Long) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val metadata = android.media.MediaMetadata.Builder()
                .putString(android.media.MediaMetadata.METADATA_KEY_TITLE, title)
                .putLong(android.media.MediaMetadata.METADATA_KEY_DURATION, duration)
                .build()
            mediaSession?.setMetadata(metadata)
        }
    }

    /**
     * Releases the MediaSession.
     */
    fun release() {
        mediaSession?.release()
        mediaSession = null
    }

    /**
     * Gets the MediaSession instance.
     */
    fun getMediaSession(): MediaSession? {
        return mediaSession
    }
}
