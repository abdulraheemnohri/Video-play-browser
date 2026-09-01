package com.videoplay.browser.gecko.runtime

import android.content.Context
import org.mozilla.geckoview.GeckoRuntime
import org.mozilla.geckoview.GeckoRuntimeSettings

/**
 * Manages the GeckoRuntime instance for the browser.
 * Ensures that GeckoRuntime is initialized only once and provides access to it.
 */
object GeckoRuntimeManager {

    private var geckoRuntime: GeckoRuntime? = null

    /**
     * Initializes GeckoRuntime with the given context.
     * @param context The Android context.
     */
    fun initialize(context: Context) {
        if (geckoRuntime == null) {
            val settings = GeckoRuntimeSettings.Builder()
                .build()
            geckoRuntime = GeckoRuntime.create(context, settings)
        }
    }

    /**
     * Returns the GeckoRuntime instance.
     * @return The GeckoRuntime instance or null if not initialized.
     */
    fun getRuntime(): GeckoRuntime? {
        return geckoRuntime
    }

    /**
     * Releases the GeckoRuntime instance.
     */
    fun release() {
        geckoRuntime?.shutdown()
        geckoRuntime = null
    }
}
