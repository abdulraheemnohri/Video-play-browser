package com.videoplay.browser.video.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.ClosedCaption
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.PictureInPicture
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Video Settings Screen for VIDEOPlay Browser.
 * Allows users to configure video playback options with modern UI.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoSettingsScreen(
    onBack: () -> Unit
) {
    val autoplayEnabled = remember { mutableStateOf(false) }
    val rememberPlaybackSpeed = remember { mutableStateOf(true) }
    val rememberPlaybackPosition = remember { mutableStateOf(true) }
    val enablePiP = remember { mutableStateOf(true) }
    val enableMiniPlayer = remember { mutableStateOf(true) }
    val playbackSpeed = remember { mutableStateOf(1.0f) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Video Settings") },
                navigationIcon = {
                    androidx.compose.material3.IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
            // Playback Settings
            SettingsCategory(title = "Playback")

            SettingsItem(
                title = "Autoplay",
                icon = Icons.Default.PlayArrow,
                onClick = { autoplayEnabled.value = !autoplayEnabled.value }
            ) {
                Switch(
                    checked = autoplayEnabled.value,
                    onCheckedChange = { autoplayEnabled.value = it }
                )
            }

            SettingsItem(
                title = "Default Playback Speed",
                icon = Icons.Default.PlayArrow,
                onClick = {}
            ) {
                Text("${"%.2f".format(playbackSpeed.value)}x")
            }

            Slider(
                value = playbackSpeed.value,
                onValueChange = { playbackSpeed.value = it },
                valueRange = 0.25f..2f,
                steps = 7,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            SettingsItem(
                title = "Remember Playback Speed",
                icon = Icons.Default.PlayArrow,
                onClick = { rememberPlaybackSpeed.value = !rememberPlaybackSpeed.value }
            ) {
                Switch(
                    checked = rememberPlaybackSpeed.value,
                    onCheckedChange = { rememberPlaybackSpeed.value = it }
                )
            }

            SettingsItem(
                title = "Remember Playback Position",
                icon = Icons.Default.PlayArrow,
                onClick = { rememberPlaybackPosition.value = !rememberPlaybackPosition.value }
            ) {
                Switch(
                    checked = rememberPlaybackPosition.value,
                    onCheckedChange = { rememberPlaybackPosition.value = it }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Display Settings
            SettingsCategory(title = "Display")

            SettingsItem(
                title = "Fullscreen",
                icon = Icons.Default.Fullscreen,
                onClick = { /* TODO: Navigate to Fullscreen Settings */ }
            ) {
                Icon(Icons.Default.ArrowForward, contentDescription = "Open")
            }

            SettingsItem(
                title = "Picture-in-Picture",
                icon = Icons.Default.PictureInPicture,
                onClick = { enablePiP.value = !enablePiP.value }
            ) {
                Switch(
                    checked = enablePiP.value,
                    onCheckedChange = { enablePiP.value = it }
                )
            }

            SettingsItem(
                title = "Mini Player",
                icon = Icons.Default.PictureInPicture,
                onClick = { enableMiniPlayer.value = !enableMiniPlayer.value }
            ) {
                Switch(
                    checked = enableMiniPlayer.value,
                    onCheckedChange = { enableMiniPlayer.value = it }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Audio Settings
            SettingsCategory(title = "Audio")

            SettingsItem(
                title = "Audio Track",
                icon = Icons.Default.Audiotrack,
                onClick = { /* TODO: Navigate to Audio Settings */ }
            ) {
                Icon(Icons.Default.ArrowForward, contentDescription = "Open")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Subtitles Settings
            SettingsCategory(title = "Subtitles")

            SettingsItem(
                title = "Subtitles",
                icon = Icons.Default.ClosedCaption,
                onClick = { /* TODO: Navigate to Subtitles Settings */ }
            ) {
                Icon(Icons.Default.ArrowForward, contentDescription = "Open")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "More video settings coming soon...",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

/**
 * Composable for a settings category header.
 */
@Composable
fun SettingsCategory(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium
    )
    Spacer(modifier = Modifier.height(8.dp))
}

/**
 * Composable for a settings item.
 */
@Composable
fun SettingsItem(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    trailingContent: @Composable () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = title)
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            modifier = Modifier.weight(1f)
        )
        trailingContent()
    }
}
