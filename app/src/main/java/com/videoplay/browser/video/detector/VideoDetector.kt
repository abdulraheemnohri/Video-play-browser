package com.videoplay.browser.video.detector

import org.mozilla.geckoview.GeckoSession

/**
 * Detects video elements in a GeckoSession and notifies about video state changes.
 */
class VideoDetector(private val session: GeckoSession) {

    private var onVideoDetected: ((Boolean, String?) -> Unit)? = null
    private var onFullScreenChange: ((Boolean) -> Unit)? = null

    init {
        setupContentDelegate()
    }

    /**
     * Sets up the ContentDelegate to detect video and fullscreen changes.
     */
    private fun setupContentDelegate() {
        session.addContentDelegate(object : GeckoSession.ContentDelegate {
            override fun onFullScreen(
                session: GeckoSession,
                enabled: Boolean,
                request: GeckoSession.FullScreenRequest?
            ) {
                onFullScreenChange?.invoke(enabled)
            }

            override fun onContextMenu(
                session: GeckoSession,
                screenX: Int,
                screenY: Int,
                targetUri: String?,
                elementSrc: String?,
                elementType: GeckoSession.ContentDelegate.ContextElement?
            ): Boolean {
                // Detect if the context menu is for a video element
                if (elementType == GeckoSession.ContentDelegate.ContextElement.VIDEO) {
                    onVideoDetected?.invoke(true, targetUri)
                }
                return false
            }
        })
    }

    /**
     * Sets a callback for when a video is detected.
     * @param callback The callback to invoke when a video is detected.
     */
    fun setOnVideoDetected(callback: (Boolean, String?) -> Unit) {
        onVideoDetected = callback
    }

    /**
     * Sets a callback for when fullscreen mode changes.
     * @param callback The callback to invoke when fullscreen mode changes.
     */
    fun setOnFullScreenChange(callback: (Boolean) -> Unit) {
        onFullScreenChange = callback
    }

    /**
     * Checks if the current page has a video element.
     * @return True if a video element is detected, false otherwise.
     */
    fun hasVideo(): Boolean {
        // This is a placeholder for actual video detection logic.
        // In a real implementation, you would use JavaScript injection or other methods.
        return false
    }
}
