package com.videoplay.browser.video.fullscreen

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.videoplay.browser.gecko.runtime.GeckoRuntimeManager
import com.videoplay.browser.gecko.session.GeckoSessionManager
import com.videoplay.browser.ui.theme.VIDEOPlayBrowserTheme
import com.videoplay.browser.video.playback.VideoPlayerScreen
import org.mozilla.geckoview.GeckoSession

/**
 * Activity for fullscreen video playback.
 * Hides system UI and provides an immersive video experience.
 */
class FullscreenVideoActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable fullscreen mode
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        GeckoRuntimeManager.initialize(this)
        val runtime = GeckoRuntimeManager.getRuntime()
        val session = if (runtime != null) {
            GeckoSessionManager(runtime).createSession()
        } else {
            GeckoSession()
        }

        setContent {
            VIDEOPlayBrowserTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    VideoPlayerScreen(
                        session = session,
                        onBack = { finish() }
                    )
                }
            }
        }
    }
}
