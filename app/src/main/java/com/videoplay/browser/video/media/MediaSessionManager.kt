package com.videoplay.browser.video.media

import android.content.Context
import android.media.MediaMetadata
import android.media.session.MediaSession
import android.media.session.PlaybackState
import android.os.Build

/**
 * Manages Android MediaSession for video playback.
 * Provides integration with lock screen, notifications, and Bluetooth media controls.
 */
class MediaSessionManager(private val context: Context) {

    private var mediaSession: MediaSession? = null

    init {
        createMediaSession()
    }

    private fun createMediaSession() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mediaSession = MediaSession(context, "VIDEOPlayBrowser").apply {
                isActive = true
                setPlaybackState(
                    PlaybackState.Builder()
                        .setState(PlaybackState.STATE_NONE, 0, 0f)
                        .setActions(
                            PlaybackState.ACTION_PLAY or
                                    PlaybackState.ACTION_PAUSE or
                                    PlaybackState.ACTION_PLAY_PAUSE or
                                    PlaybackState.ACTION_SKIP_TO_NEXT or
                                    PlaybackState.ACTION_SKIP_TO_PREVIOUS or
                                    PlaybackState.ACTION_SEEK_TO
                        )
                        .build()
                )
            }
        }
    }

    fun updatePlaybackState(state: Int, position: Long, speed: Float) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val playbackState = PlaybackState.Builder()
                .setState(state, position, speed)
                .setActions(
                    PlaybackState.ACTION_PLAY or
                            PlaybackState.ACTION_PAUSE or
                            PlaybackState.ACTION_PLAY_PAUSE or
                            PlaybackState.ACTION_SKIP_TO_NEXT or
                            PlaybackState.ACTION_SKIP_TO_PREVIOUS or
                            PlaybackState.ACTION_SEEK_TO
                )
                .build()
            mediaSession?.setPlaybackState(playbackState)
        }
    }

    fun setMediaMetadata(title: String, duration: Long) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val metadata = MediaMetadata.Builder()
                .putString(MediaMetadata.METADATA_KEY_TITLE, title)
                .putLong(MediaMetadata.METADATA_KEY_DURATION, duration)
                .build()
            mediaSession?.setMetadata(metadata)
        }
    }

    fun setCallback(callback: MediaSession.Callback) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mediaSession?.setCallback(callback)
        }
    }

    fun release() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mediaSession?.isActive = false
            mediaSession?.release()
        }
        mediaSession = null
    }

    fun getMediaSession(): MediaSession? {
        return mediaSession
    }
}
