package com.videoplay.browser

import android.app.Application
import com.videoplay.browser.gecko.runtime.GeckoRuntimeManager

/**
 * Custom Application class for VIDEOPlay Browser.
 * Initializes GeckoRuntime and other app-wide dependencies.
 */
class BrowserApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Initialize GeckoRuntime
        GeckoRuntimeManager.initialize(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        // Release GeckoRuntime
        GeckoRuntimeManager.release()
    }
}
