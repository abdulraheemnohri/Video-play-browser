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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Https
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
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
 * Privacy Settings Screen for VIDEOPlay Browser.
 * Allows users to configure privacy-related options with modern UI.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacySettingsScreen(
    onBack: () -> Unit
) {
    val trackingProtectionEnabled = remember { mutableStateOf(true) }
    val httpsOnlyEnabled = remember { mutableStateOf(true) }
    val clearDataOnExitEnabled = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Privacy Settings") },
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
            // Tracking Protection
            SettingsCategory(title = "Tracking Protection")

            Text(
                text = "Protects against trackers that follow your activity across websites.",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(8.dp))

            SettingsItem(
                title = "Enabled",
                icon = Icons.Default.Shield,
                onClick = { trackingProtectionEnabled.value = !trackingProtectionEnabled.value }
            ) {
                Switch(
                    checked = trackingProtectionEnabled.value,
                    onCheckedChange = { trackingProtectionEnabled.value = it }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // HTTPS-Only Mode
            SettingsCategory(title = "HTTPS-Only Mode")

            Text(
                text = "Forces all connections to use HTTPS for a more secure browsing experience.",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(8.dp))

            SettingsItem(
                title = "Enabled",
                icon = Icons.Default.Https,
                onClick = { httpsOnlyEnabled.value = !httpsOnlyEnabled.value }
            ) {
                Switch(
                    checked = httpsOnlyEnabled.value,
                    onCheckedChange = { httpsOnlyEnabled.value = it }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Clear Data on Exit
            SettingsCategory(title = "Clear Data on Exit")

            Text(
                text = "Clears browsing data (history, cookies, cache) when you exit the app.",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(8.dp))

            SettingsItem(
                title = "Enabled",
                icon = Icons.Default.Delete,
                onClick = { clearDataOnExitEnabled.value = !clearDataOnExitEnabled.value }
            ) {
                Switch(
                    checked = clearDataOnExitEnabled.value,
                    onCheckedChange = { clearDataOnExitEnabled.value = it }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Site Permissions
            SettingsCategory(title = "Site Permissions")

            SettingsItem(
                title = "Manage Permissions",
                icon = Icons.Default.Security,
                onClick = { /* TODO: Navigate to Site Permissions */ }
            ) {
                Icon(Icons.Default.ArrowForward, contentDescription = "Open")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "More privacy settings coming soon...",
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
