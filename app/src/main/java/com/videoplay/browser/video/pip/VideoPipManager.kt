package com.videoplay.browser.video.pip

import android.app.PictureInPictureParams
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi

/**
 * Manages Picture-in-Picture (PiP) mode for video playback.
 */
@RequiresApi(Build.VERSION_CODES.O)
class VideoPipManager(private val context: Context) {

    private var isInPipMode = false
    private var pipParams: PictureInPictureParams? = null

    /**
     * Enters Picture-in-Picture mode.
     * @param autoEnter Whether to automatically enter PiP mode.
     */
    fun enterPipMode(autoEnter: Boolean = false) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val params = PictureInPictureParams.Builder()
                .setAutoEnterEnabled(autoEnter)
                .build()
            pipParams = params
            isInPipMode = true
            // Note: Actual PiP mode entry is handled by the Activity
        }
    }

    /**
     * Exits Picture-in-Picture mode.
     */
    fun exitPipMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && isInPipMode) {
            isInPipMode = false
            // Note: Actual PiP mode exit is handled by the Activity
        }
    }

    /**
     * Checks if the app is currently in PiP mode.
     */
    fun isInPip(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            isInPipMode
        } else {
            false
        }
    }

    /**
     * Updates PiP parameters.
     * @param aspectRatio The aspect ratio for PiP (e.g., 16f/9f).
     */
    fun updatePipParams(aspectRatio: Float) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            pipParams = PictureInPictureParams.Builder()
                .setAspectRatio(aspectRatio)
                .build()
        }
    }

    /**
     * Gets the current PiP parameters.
     */
    fun getPipParams(): PictureInPictureParams? {
        return pipParams
    }
}
