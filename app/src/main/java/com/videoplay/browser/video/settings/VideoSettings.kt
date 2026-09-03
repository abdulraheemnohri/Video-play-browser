package com.videoplay.browser.video.settings

import android.os.Build
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.ClosedCaption
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.PictureInPicture
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SignalCellularAlt
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.videoplay.browser.ui.components.SettingsItem

enum class VideoSection {
    MASTER,
    PLAYBACK,
    CONTROLS,
    DISPLAY,
    QUALITY,
    AUDIO,
    SUBTITLES,
    PIP,
    BACKGROUND,
    DOWNLOADS,
    DATA_SAVER,
    ACCESSIBILITY,
    SITES,
    DIAGNOSTICS
}

/**
 * Master Video Settings Landing Page and Subpages for VIDEOPlay Browser.
 * Conforms strictly to section 69 spec.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoSettingsScreen(
    onBack: () -> Unit
) {
    var activeSection by remember { mutableStateOf(VideoSection.MASTER) }

    when (activeSection) {
        VideoSection.MASTER -> MasterVideoSettingsScreen(
            onBack = onBack,
            onSelectSection = { activeSection = it }
        )
        VideoSection.PLAYBACK -> VideoPlaybackSettingsScreen(onBack = { activeSection = VideoSection.MASTER })
        VideoSection.CONTROLS -> VideoControlsSettingsScreen(onBack = { activeSection = VideoSection.MASTER })
        VideoSection.DISPLAY -> VideoDisplaySettingsScreen(onBack = { activeSection = VideoSection.MASTER })
        VideoSection.QUALITY -> VideoQualitySettingsScreen(onBack = { activeSection = VideoSection.MASTER })
        VideoSection.AUDIO -> VideoAudioSettingsScreen(onBack = { activeSection = VideoSection.MASTER })
        VideoSection.SUBTITLES -> VideoSubtitleSettingsScreen(onBack = { activeSection = VideoSection.MASTER })
        VideoSection.PIP -> VideoPipSettingsScreen(onBack = { activeSection = VideoSection.MASTER })
        VideoSection.BACKGROUND -> VideoBackgroundSettingsScreen(onBack = { activeSection = VideoSection.MASTER })
        VideoSection.DOWNLOADS -> VideoDownloadSettingsScreen(onBack = { activeSection = VideoSection.MASTER })
        VideoSection.DATA_SAVER -> VideoDataSaverSettingsScreen(onBack = { activeSection = VideoSection.MASTER })
        VideoSection.ACCESSIBILITY -> VideoAccessibilitySettingsScreen(onBack = { activeSection = VideoSection.MASTER })
        VideoSection.SITES -> SiteVideoSettingsScreen(onBack = { activeSection = VideoSection.MASTER })
        VideoSection.DIAGNOSTICS -> VideoDiagnosticsSettingsScreen(onBack = { activeSection = VideoSection.MASTER })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MasterVideoSettingsScreen(
    onBack: () -> Unit,
    onSelectSection: (VideoSection) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Video") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            SettingsItem(
                title = "Playback",
                subtitle = "Playback behavior and speed",
                icon = Icons.Default.PlayArrow,
                onClick = { onSelectSection(VideoSection.PLAYBACK) }
            ) { Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null) }

            SettingsItem(
                title = "Controls",
                subtitle = "Seek, gestures and controls",
                icon = Icons.Default.Movie,
                onClick = { onSelectSection(VideoSection.CONTROLS) }
            ) { Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null) }

            SettingsItem(
                title = "Display",
                subtitle = "Fullscreen, zoom and orientation",
                icon = Icons.Default.Fullscreen,
                onClick = { onSelectSection(VideoSection.DISPLAY) }
            ) { Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null) }

            SettingsItem(
                title = "Quality",
                subtitle = "Preferred quality and data usage",
                icon = Icons.Default.Movie,
                onClick = { onSelectSection(VideoSection.QUALITY) }
            ) { Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null) }

            SettingsItem(
                title = "Audio",
                subtitle = "Volume and audio focus",
                icon = Icons.Default.Audiotrack,
                onClick = { onSelectSection(VideoSection.AUDIO) }
            ) { Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null) }

            SettingsItem(
                title = "Subtitles",
                subtitle = "Caption appearance",
                icon = Icons.Default.ClosedCaption,
                onClick = { onSelectSection(VideoSection.SUBTITLES) }
            ) { Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null) }

            SettingsItem(
                title = "Picture-in-picture",
                subtitle = "PiP behavior",
                icon = Icons.Default.PictureInPicture,
                onClick = { onSelectSection(VideoSection.PIP) }
            ) { Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null) }

            SettingsItem(
                title = "Background",
                subtitle = "Background playback",
                icon = Icons.Default.Headphones,
                onClick = { onSelectSection(VideoSection.BACKGROUND) }
            ) { Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null) }

            SettingsItem(
                title = "Downloads",
                subtitle = "Download behavior",
                icon = Icons.Default.Download,
                onClick = { onSelectSection(VideoSection.DOWNLOADS) }
            ) { Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null) }

            SettingsItem(
                title = "Data Saver",
                subtitle = "Video data optimization",
                icon = Icons.Default.SignalCellularAlt,
                onClick = { onSelectSection(VideoSection.DATA_SAVER) }
            ) { Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null) }

            SettingsItem(
                title = "Accessibility",
                subtitle = "Accessible playback",
                icon = Icons.Default.Accessibility,
                onClick = { onSelectSection(VideoSection.ACCESSIBILITY) }
            ) { Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null) }

            SettingsItem(
                title = "Sites",
                subtitle = "Per-site video preferences",
                icon = Icons.Default.Movie,
                onClick = { onSelectSection(VideoSection.SITES) }
            ) { Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null) }

            SettingsItem(
                title = "Diagnostics",
                subtitle = "Playback information",
                icon = Icons.Default.Movie,
                onClick = { onSelectSection(VideoSection.DIAGNOSTICS) }
            ) { Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoPlaybackSettingsScreen(onBack: () -> Unit) {
    var autoplay by remember { mutableStateOf("Wi-Fi only") }
    var speed by remember { mutableFloatStateOf(1.0f) }
    var rememberSpeed by remember { mutableStateOf(true) }
    var resumeVideos by remember { mutableStateOf(true) }
    var rememberPosition by remember { mutableStateOf(true) }
    var skipIntro by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Playback") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text("Autoplay: $autoplay", style = MaterialTheme.typography.titleMedium)
            listOf("Allow", "Wi-Fi only", "Ask", "Block").forEach { option ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { autoplay = option }
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(selected = autoplay == option, onClick = { autoplay = option })
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(option)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Default Playback Speed: ${"%.2f".format(speed)}x", style = MaterialTheme.typography.titleMedium)
            Slider(
                value = speed,
                onValueChange = { speed = it },
                valueRange = 0.25f..2.0f,
                steps = 6
            )

            Spacer(modifier = Modifier.height(16.dp))

            SettingsItem(
                title = "Remember Playback Speed",
                icon = Icons.Default.PlayArrow,
                onClick = { rememberSpeed = !rememberSpeed }
            ) { Switch(checked = rememberSpeed, onCheckedChange = { rememberSpeed = it }) }

            SettingsItem(
                title = "Resume Videos",
                icon = Icons.Default.PlayArrow,
                onClick = { resumeVideos = !resumeVideos }
            ) { Switch(checked = resumeVideos, onCheckedChange = { resumeVideos = it }) }

            SettingsItem(
                title = "Remember Playback Position",
                icon = Icons.Default.PlayArrow,
                onClick = { rememberPosition = !rememberPosition }
            ) { Switch(checked = rememberPosition, onCheckedChange = { rememberPosition = it }) }

            SettingsItem(
                title = "Skip Intro",
                icon = Icons.Default.PlayArrow,
                onClick = { skipIntro = !skipIntro }
            ) { Switch(checked = skipIntro, onCheckedChange = { skipIntro = it }) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoControlsSettingsScreen(onBack: () -> Unit) {
    var seekBack by remember { mutableStateOf("10 sec") }
    var seekForward by remember { mutableStateOf("10 sec") }
    var doubleTapSeek by remember { mutableStateOf(true) }
    var swipeSeek by remember { mutableStateOf(true) }
    var brightnessGesture by remember { mutableStateOf(true) }
    var volumeGesture by remember { mutableStateOf(true) }
    var longPress2x by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Controls") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text("Seek Backward: $seekBack", style = MaterialTheme.typography.titleMedium)
            listOf("5 sec", "10 sec", "15 sec", "30 sec").forEach { opt ->
                Row(
                    modifier = Modifier.fillMaxWidth().clickable { seekBack = opt },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(selected = seekBack == opt, onClick = { seekBack = opt })
                    Text(opt)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Seek Forward: $seekForward", style = MaterialTheme.typography.titleMedium)
            listOf("5 sec", "10 sec", "15 sec", "30 sec").forEach { opt ->
                Row(
                    modifier = Modifier.fillMaxWidth().clickable { seekForward = opt },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(selected = seekForward == opt, onClick = { seekForward = opt })
                    Text(opt)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            SettingsItem(
                title = "Double-tap Seek",
                icon = Icons.Default.Movie,
                onClick = { doubleTapSeek = !doubleTapSeek }
            ) { Switch(checked = doubleTapSeek, onCheckedChange = { doubleTapSeek = it }) }

            SettingsItem(
                title = "Swipe Seek",
                icon = Icons.Default.Movie,
                onClick = { swipeSeek = !swipeSeek }
            ) { Switch(checked = swipeSeek, onCheckedChange = { swipeSeek = it }) }

            SettingsItem(
                title = "Brightness Gesture",
                icon = Icons.Default.Movie,
                onClick = { brightnessGesture = !brightnessGesture }
            ) { Switch(checked = brightnessGesture, onCheckedChange = { brightnessGesture = it }) }

            SettingsItem(
                title = "Volume Gesture",
                icon = Icons.Default.Movie,
                onClick = { volumeGesture = !volumeGesture }
            ) { Switch(checked = volumeGesture, onCheckedChange = { volumeGesture = it }) }

            SettingsItem(
                title = "Long Press 2x",
                icon = Icons.Default.Movie,
                onClick = { longPress2x = !longPress2x }
            ) { Switch(checked = longPress2x, onCheckedChange = { longPress2x = it }) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoDisplaySettingsScreen(onBack: () -> Unit) {
    var fullscreen by remember { mutableStateOf(true) }
    var immersive by remember { mutableStateOf(true) }
    var zoom by remember { mutableStateOf("Fit") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Display") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            SettingsItem(
                title = "Fullscreen",
                icon = Icons.Default.Fullscreen,
                onClick = { fullscreen = !fullscreen }
            ) { Switch(checked = fullscreen, onCheckedChange = { fullscreen = it }) }

            SettingsItem(
                title = "Immersive Fullscreen",
                icon = Icons.Default.Fullscreen,
                onClick = { immersive = !immersive }
            ) { Switch(checked = immersive, onCheckedChange = { immersive = it }) }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Video Zoom: $zoom", style = MaterialTheme.typography.titleMedium)
            listOf("Fit", "Crop", "Fill").forEach { option ->
                Row(
                    modifier = Modifier.fillMaxWidth().clickable { zoom = option },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(selected = zoom == option, onClick = { zoom = option })
                    Text(option)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoQualitySettingsScreen(onBack: () -> Unit) {
    var quality by remember { mutableStateOf("Auto") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Quality") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text("Preferred Quality: $quality", style = MaterialTheme.typography.titleMedium)
            listOf("Auto", "2160p", "1440p", "1080p", "720p", "480p", "360p").forEach { opt ->
                Row(
                    modifier = Modifier.fillMaxWidth().clickable { quality = opt },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(selected = quality == opt, onClick = { quality = opt })
                    Text(opt)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoAudioSettingsScreen(onBack: () -> Unit) {
    var muteAutoplay by remember { mutableStateOf(true) }
    var audioFocus by remember { mutableStateOf("Pause") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Audio") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            SettingsItem(
                title = "Mute Autoplay",
                icon = Icons.Default.Audiotrack,
                onClick = { muteAutoplay = !muteAutoplay }
            ) { Switch(checked = muteAutoplay, onCheckedChange = { muteAutoplay = it }) }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Audio Focus Behavior: $audioFocus", style = MaterialTheme.typography.titleMedium)
            listOf("Pause", "Duck", "Continue").forEach { opt ->
                Row(
                    modifier = Modifier.fillMaxWidth().clickable { audioFocus = opt },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(selected = audioFocus == opt, onClick = { audioFocus = opt })
                    Text(opt)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoSubtitleSettingsScreen(onBack: () -> Unit) {
    var enabled by remember { mutableStateOf(true) }
    var size by remember { mutableStateOf("Medium") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Subtitles") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            SettingsItem(
                title = "Subtitles",
                icon = Icons.Default.ClosedCaption,
                onClick = { enabled = !enabled }
            ) { Switch(checked = enabled, onCheckedChange = { enabled = it }) }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Subtitle Size: $size", style = MaterialTheme.typography.titleMedium)
            listOf("Small", "Medium", "Large", "Extra Large").forEach { opt ->
                Row(
                    modifier = Modifier.fillMaxWidth().clickable { size = opt },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(selected = size == opt, onClick = { size = opt })
                    Text(opt)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoPipSettingsScreen(onBack: () -> Unit) {
    var pipEnabled by remember { mutableStateOf(true) }
    var autoEnter by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Picture-in-picture") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            SettingsItem(
                title = "Picture-in-picture",
                icon = Icons.Default.PictureInPicture,
                onClick = { pipEnabled = !pipEnabled }
            ) { Switch(checked = pipEnabled, onCheckedChange = { pipEnabled = it }) }

            SettingsItem(
                title = "Enter PiP automatically",
                icon = Icons.Default.PictureInPicture,
                onClick = { autoEnter = !autoEnter }
            ) { Switch(checked = autoEnter, onCheckedChange = { autoEnter = it }) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoBackgroundSettingsScreen(onBack: () -> Unit) {
    var backgroundPlayback by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Background") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            SettingsItem(
                title = "Background Playback",
                icon = Icons.Default.Headphones,
                onClick = { backgroundPlayback = !backgroundPlayback }
            ) { Switch(checked = backgroundPlayback, onCheckedChange = { backgroundPlayback = it }) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoDownloadSettingsScreen(onBack: () -> Unit) {
    var wifiOnly by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Downloads") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            SettingsItem(
                title = "Wi-Fi Only Downloads",
                icon = Icons.Default.Download,
                onClick = { wifiOnly = !wifiOnly }
            ) { Switch(checked = wifiOnly, onCheckedChange = { wifiOnly = it }) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoDataSaverSettingsScreen(onBack: () -> Unit) {
    var mode by remember { mutableStateOf("Moderate") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Data Saver") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text("Video Data Saver: $mode", style = MaterialTheme.typography.titleMedium)
            listOf("OFF", "Moderate", "Aggressive").forEach { opt ->
                Row(
                    modifier = Modifier.fillMaxWidth().clickable { mode = opt },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(selected = mode == opt, onClick = { mode = opt })
                    Text(opt)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoAccessibilitySettingsScreen(onBack: () -> Unit) {
    var highContrast by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Accessibility") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            SettingsItem(
                title = "High Contrast Controls",
                icon = Icons.Default.Accessibility,
                onClick = { highContrast = !highContrast }
            ) { Switch(checked = highContrast, onCheckedChange = { highContrast = it }) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SiteVideoSettingsScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Site Preferences") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text("Per-site video overrides (e.g. example.com)", style = MaterialTheme.typography.titleMedium)
            Text("Global settings remain defaults.", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoDiagnosticsSettingsScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Diagnostics") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("GeckoView Version: 120.0")
                    Text("Android Version: ${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})")
                    Text("Hardware Acceleration: Enabled")
                    Text("Media Decoding: Software / Hardware dual fallback")
                }
            }
        }
    }
}
