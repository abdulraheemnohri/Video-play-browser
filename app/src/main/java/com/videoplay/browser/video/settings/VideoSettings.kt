package com.videoplay.browser.video.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.videoplay.browser.core.preferences.SettingsRepository
import com.videoplay.browser.ui.screens.Row

/**
 * Video Settings Screen for VIDEOPlay Browser.
 * Allows users to configure video playback options.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoSettingsScreen(
    onBack: () -> Unit,
    settingsRepository: SettingsRepository = viewModel()
) {
    val autoPlay by settingsRepository.autoPlay.collectAsState()
    val defaultPlaybackSpeed by settingsRepository.defaultPlaybackSpeed.collectAsState()
    val rememberPlaybackSpeed by settingsRepository.rememberPlaybackSpeed.collectAsState()
    val rememberPlaybackPosition by settingsRepository.rememberPlaybackPosition.collectAsState()
    val enablePiP by settingsRepository.enablePiP.collectAsState()
    val enableMiniPlayer by settingsRepository.enableMiniPlayer.collectAsState()

    var selectedAutoPlay by remember { mutableStateOf(autoPlay) }
    var selectedPlaybackSpeed by remember { mutableStateOf(defaultPlaybackSpeed) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Video Settings") },
                navigationIcon = {
                    androidx.compose.material3.IconButton(onClick = onBack) {
                        androidx.compose.material.icons.Icons.Default.ArrowBack
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
            // Autoplay Settings
            Text(
                text = "Autoplay",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Autoplay Options
                listOf("Always", "Wi-Fi Only", "Never").forEach { option ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedAutoPlay = when (option) {
                                    "Always" -> "always"
                                    "Wi-Fi Only" -> "wifi_only"
                                    else -> "never"
                                }
                            },
                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                    ) {
                        androidx.compose.material3.RadioButton(
                            selected = selectedAutoPlay == when (option) {
                                "Always" -> "always"
                                "Wi-Fi Only" -> "wifi_only"
                                else -> "never"
                            },
                            onClick = {
                                selectedAutoPlay = when (option) {
                                    "Always" -> "always"
                                    "Wi-Fi Only" -> "wifi_only"
                                    else -> "never"
                                }
                            }
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(option)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Default Playback Speed
            Text(
                text = "Default Playback Speed",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                Text("${"%.2f".format(selectedPlaybackSpeed)}x")
                Spacer(modifier = Modifier.weight(1f))
                Text("${selectedPlaybackSpeed.toInt()}")
            }

            Slider(
                value = selectedPlaybackSpeed,
                onValueChange = { selectedPlaybackSpeed = it },
                valueRange = 0.25f..2f,
                steps = 7,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Remember Playback Speed
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                Text("Remember Playback Speed")
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = rememberPlaybackSpeed,
                    onCheckedChange = { /* TODO: Update setting */ }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Remember Playback Position
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                Text("Remember Playback Position")
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = rememberPlaybackPosition,
                    onCheckedChange = { /* TODO: Update setting */ }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Picture-in-Picture
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                Text("Picture-in-Picture")
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = enablePiP,
                    onCheckedChange = { /* TODO: Update setting */ }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Mini Player
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                Text("Mini Player")
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = enableMiniPlayer,
                    onCheckedChange = { /* TODO: Update setting */ }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "More video settings coming soon...",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

// Helper Composable for Row
@Composable
fun Row(
    modifier: Modifier = Modifier,
    verticalAlignment: androidx.compose.ui.Alignment.Vertical = androidx.compose.ui.Alignment.Top,
    content: @Composable () -> Unit
) {
    androidx.compose.foundation.layout.Row(
        modifier = modifier,
        verticalAlignment = verticalAlignment,
        content = content
    )
}

// Helper Composable for clickable Row
@Composable
fun Row(
    modifier: Modifier = Modifier,
    verticalAlignment: androidx.compose.ui.Alignment.Vertical = androidx.compose.ui.Alignment.Top,
    clickable: Boolean = false,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    androidx.compose.foundation.layout.Row(
        modifier = modifier.clickable(enabled = clickable, onClick = onClick),
        verticalAlignment = verticalAlignment,
        content = content
    )
}
