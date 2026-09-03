package com.videoplay.browser.ui.screens

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
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.videoplay.browser.ui.components.SettingsCategory
import com.videoplay.browser.ui.components.SettingsItem

data class SearchableSetting(
    val title: String,
    val category: String,
    val onSelect: () -> Unit
)

/**
 * Settings Screen for VIDEOPlay Browser with Global Settings Search and Reset Settings.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    onNavigateToPrivacy: () -> Unit,
    onNavigateToVideoSettings: () -> Unit
) {
    var darkModeEnabled by remember { mutableStateOf(false) }
    var autoplayEnabled by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var confirmResetTarget by remember { mutableStateOf<String?>(null) }

    val allSettings = listOf(
        SearchableSetting("PiP (Picture-in-picture)", "Video -> Picture-in-picture", onNavigateToVideoSettings),
        SearchableSetting("Autoplay", "Video -> Playback", onNavigateToVideoSettings),
        SearchableSetting("Subtitles", "Video -> Subtitles", onNavigateToVideoSettings),
        SearchableSetting("Playback Speed", "Video -> Playback", onNavigateToVideoSettings),
        SearchableSetting("Downloads", "Downloads", {}),
        SearchableSetting("Dark Mode", "Appearance", {}),
        SearchableSetting("Tracking Protection", "Privacy", onNavigateToPrivacy),
        SearchableSetting("HTTPS-Only Mode", "Privacy", onNavigateToPrivacy),
        SearchableSetting("Site Permissions", "Privacy", onNavigateToPrivacy)
    )

    val searchResults = remember(searchQuery) {
        if (searchQuery.isBlank()) emptyList()
        else allSettings.filter {
            it.title.contains(searchQuery, ignoreCase = true) || it.category.contains(searchQuery, ignoreCase = true)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
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
            // Global Settings Search Bar (Section 70)
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Search settings...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (searchQuery.isNotBlank()) {
                Text("Search Results", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                if (searchResults.isEmpty()) {
                    Text("No matching settings found.", style = MaterialTheme.typography.bodySmall)
                } else {
                    searchResults.forEach { item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable { item.onSelect() }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(item.title, style = MaterialTheme.typography.titleSmall)
                                    Text("→ " + item.category, style = MaterialTheme.typography.bodySmall)
                                }
                                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            // General Settings
            SettingsCategory(title = "General")
            
            SettingsItem(
                title = "Dark Mode",
                icon = Icons.Default.Palette,
                onClick = { darkModeEnabled = !darkModeEnabled }
            ) {
                Switch(
                    checked = darkModeEnabled,
                    onCheckedChange = { darkModeEnabled = it }
                )
            }

            SettingsItem(
                title = "Language",
                icon = Icons.Default.Language,
                onClick = { }
            ) {
                Text("English")
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Change")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Video Settings
            SettingsCategory(title = "Video")
            
            SettingsItem(
                title = "Autoplay Videos",
                icon = Icons.Default.VideoLibrary,
                onClick = { autoplayEnabled = !autoplayEnabled }
            ) {
                Switch(
                    checked = autoplayEnabled,
                    onCheckedChange = { autoplayEnabled = it }
                )
            }

            SettingsItem(
                title = "Video Settings Master",
                subtitle = "Playback, Controls, Display, Quality, Audio...",
                icon = Icons.Default.VideoLibrary,
                onClick = onNavigateToVideoSettings
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Open")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Privacy Settings
            SettingsCategory(title = "Privacy")
            
            SettingsItem(
                title = "Privacy Settings",
                icon = Icons.Default.Security,
                onClick = onNavigateToPrivacy
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Open")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Reset Settings Section (Section 71)
            SettingsCategory(title = "Reset Options")

            SettingsItem(
                title = "Reset video settings",
                icon = Icons.Default.RestartAlt,
                onClick = { confirmResetTarget = "Reset Video Settings" }
            )

            SettingsItem(
                title = "Reset site permissions",
                icon = Icons.Default.RestartAlt,
                onClick = { confirmResetTarget = "Reset Site Permissions" }
            )

            SettingsItem(
                title = "Reset browser settings",
                icon = Icons.Default.RestartAlt,
                onClick = { confirmResetTarget = "Reset Browser Settings" }
            )

            SettingsItem(
                title = "Reset all application settings",
                icon = Icons.Default.DeleteForever,
                onClick = { confirmResetTarget = "Reset All Application Settings" }
            )
        }

        // Confirmation Dialog for Reset Actions
        confirmResetTarget?.let { target ->
            AlertDialog(
                onDismissRequest = { confirmResetTarget = null },
                title = { Text(target) },
                text = { Text("Are you sure you want to perform $target? This action requires confirmation.") },
                confirmButton = {
                    Button(
                        onClick = {
                            confirmResetTarget = null
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Text("Reset")
                    }
                },
                dismissButton = {
                    OutlinedButton(onClick = { confirmResetTarget = null }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}
