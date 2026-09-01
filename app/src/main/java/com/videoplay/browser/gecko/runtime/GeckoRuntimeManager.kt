package com.videoplay.browser.gecko.runtime

import android.content.Context
import android.util.Log
import org.mozilla.geckoview.GeckoRuntime
import org.mozilla.geckoview.GeckoRuntimeSettings

/**
 * Manages the GeckoRuntime instance for the browser.
 * Ensures that GeckoRuntime is initialized only once and provides access to it.
 * Handles initialization errors gracefully.
 */
object GeckoRuntimeManager {

    private const val TAG = "GeckoRuntimeManager"
    private var geckoRuntime: GeckoRuntime? = null
    private var initializationError: Throwable? = null

    /**
     * Initializes GeckoRuntime with the given context.
     * Handles initialization errors and logs them.
     * @param context The Android context.
     */
    fun initialize(context: Context) {
        if (geckoRuntime == null && initializationError == null) {
            try {
                val settings = GeckoRuntimeSettings.Builder()
                    .build()
                geckoRuntime = GeckoRuntime.create(context, settings)
                Log.d(TAG, "GeckoRuntime initialized successfully")
            } catch (e: Throwable) {
                initializationError = e
                Log.e(TAG, "Failed to initialize GeckoRuntime", e)
            }
        }
    }

    /**
     * Returns the GeckoRuntime instance.
     * @return The GeckoRuntime instance or null if not initialized or initialization failed.
     */
    fun getRuntime(): GeckoRuntime? {
        return geckoRuntime
    }

    /**
     * Checks if GeckoRuntime is initialized and ready to use.
     * @return True if GeckoRuntime is initialized, false otherwise.
     */
    fun isInitialized(): Boolean {
        return geckoRuntime != null
    }

    /**
     * Gets the initialization error, if any.
     * @return The initialization error or null if none.
     */
    fun getInitializationError(): Throwable? {
        return initializationError
    }

    /**
     * Releases the GeckoRuntime instance.
     */
    fun release() {
        try {
            geckoRuntime?.shutdown()
            geckoRuntime = null
            initializationError = null
            Log.d(TAG, "GeckoRuntime released successfully")
        } catch (e: Throwable) {
            Log.e(TAG, "Failed to release GeckoRuntime", e)
        }
    }
}
