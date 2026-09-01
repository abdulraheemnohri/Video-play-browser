package com.videoplay.browser.privacy

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.videoplay.browser.core.preferences.SettingsRepository
import com.videoplay.browser.ui.screens.Row

/**
 * Privacy Settings Screen for VIDEOPlay Browser.
 * Allows users to configure privacy-related options.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacySettingsScreen(
    onBack: () -> Unit,
    settingsRepository: SettingsRepository = viewModel()
) {
    val trackingProtection by settingsRepository.trackingProtection.collectAsState()
    val httpsOnly by settingsRepository.httpsOnly.collectAsState()
    val clearDataOnExit by settingsRepository.clearDataOnExit.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Privacy Settings") },
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
            // Tracking Protection
            Text(
                text = "Tracking Protection",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("Protects against trackers that follow your activity across websites.")
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                Text("Enabled")
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = trackingProtection == "standard" || trackingProtection == "strict",
                    onCheckedChange = { enabled ->
                        // TODO: Update tracking protection mode
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // HTTPS-Only Mode
            Text(
                text = "HTTPS-Only Mode",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("Forces all connections to use HTTPS for a more secure browsing experience.")
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                Text("Enabled")
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = httpsOnly,
                    onCheckedChange = { enabled ->
                        // TODO: Update HTTPS-only mode
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Clear Data on Exit
            Text(
                text = "Clear Data on Exit",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("Clears browsing data (history, cookies, cache) when you exit the app.")
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                Text("Enabled")
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = clearDataOnExit,
                    onCheckedChange = { enabled ->
                        // TODO: Update clear data on exit
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "More privacy settings coming soon...",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
