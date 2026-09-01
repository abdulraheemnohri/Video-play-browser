package com.videoplay.browser.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Settings Screen for VIDEOPlay Browser.
 * Provides app settings and preferences with modern UI.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit
) {
    val darkModeEnabled = remember { mutableStateOf(false) }
    val trackingProtectionEnabled = remember { mutableStateOf(true) }
    val autoplayEnabled = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
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
            // General Settings
            SettingsCategory(title = "General")
            
            SettingsItem(
                title = "Dark Mode",
                icon = Icons.Default.Palette,
                onClick = { darkModeEnabled.value = !darkModeEnabled.value }
            ) {
                Switch(
                    checked = darkModeEnabled.value,
                    onCheckedChange = { darkModeEnabled.value = it }
                )
            }

            SettingsItem(
                title = "Language",
                icon = Icons.Default.Language,
                onClick = { /* TODO: Navigate to Language Settings */ }
            ) {
                Text("English")
                Icon(Icons.Default.ArrowForward, contentDescription = "Change")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Video Settings
            SettingsCategory(title = "Video")
            
            SettingsItem(
                title = "Autoplay Videos",
                icon = Icons.Default.VideoLibrary,
                onClick = { autoplayEnabled.value = !autoplayEnabled.value }
            ) {
                Switch(
                    checked = autoplayEnabled.value,
                    onCheckedChange = { autoplayEnabled.value = it }
                )
            }

            SettingsItem(
                title = "Video Settings",
                icon = Icons.Default.VideoLibrary,
                onClick = { /* TODO: Navigate to Video Settings */ }
            ) {
                Icon(Icons.Default.ArrowForward, contentDescription = "Open")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Privacy Settings
            SettingsCategory(title = "Privacy")
            
            SettingsItem(
                title = "Tracking Protection",
                icon = Icons.Default.Security,
                onClick = { trackingProtectionEnabled.value = !trackingProtectionEnabled.value }
            ) {
                Switch(
                    checked = trackingProtectionEnabled.value,
                    onCheckedChange = { trackingProtectionEnabled.value = it }
                )
            }

            SettingsItem(
                title = "Privacy Settings",
                icon = Icons.Default.Security,
                onClick = { /* TODO: Navigate to Privacy Settings */ }
            ) {
                Icon(Icons.Default.ArrowForward, contentDescription = "Open")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "More settings coming soon...",
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
