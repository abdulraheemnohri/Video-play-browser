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
import androidx.compose.material.icons.filled.CleanHands
import androidx.compose.material.icons.filled.Https
import androidx.compose.material.icons.filled.Lock
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.videoplay.browser.privacy.ClearBrowsingDataManager
import com.videoplay.browser.privacy.HttpsOnlyManager
import com.videoplay.browser.privacy.TrackingProtectionManager

/**
 * Privacy Settings Screen for VIDEOPlay Browser.
 * Allows users to configure privacy-related options.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacySettingsScreen(
    onBack: () -> Unit,
    onNavigateToClearData: () -> Unit,
    onNavigateToSitePermissions: () -> Unit
) {
    val context = LocalContext.current
    
    // State for tracking protection
    val trackingProtectionEnabled = remember { mutableStateOf(true) }
    val trackingProtectionLevel = remember { 
        mutableStateOf(TrackingProtectionManager.TrackingProtectionLevel.STANDARD) 
    }
    
    // State for HTTPS-only mode
    val httpsOnlyEnabled = remember { mutableStateOf(true) }

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
            // Tracking Protection Section
            SettingsCategory(title = "Tracking Protection")
            
            Text(
                text = "Protects against trackers that follow your activity across websites.",
                style = MaterialTheme.typography.bodySmall
            )
            
            Spacer(modifier = Modifier.height(8.dp))

            // Tracking Protection Toggle
            SettingsItem(
                title = "Tracking Protection",
                icon = Icons.Default.Shield,
                onClick = { trackingProtectionEnabled.value = !trackingProtectionEnabled.value }
            ) {
                Switch(
                    checked = trackingProtectionEnabled.value,
                    onCheckedChange = { trackingProtectionEnabled.value = it }
                )
            }

            // Tracking Protection Level
            if (trackingProtectionEnabled.value) {
                Spacer(modifier = Modifier.height(8.dp))
                
                TrackingProtectionManager().getAllTrackingProtectionLevels().forEach { level ->
                    val displayName = when (level) {
                        TrackingProtectionManager.TrackingProtectionLevel.STANDARD -> "Standard"
                        TrackingProtectionManager.TrackingProtectionLevel.STRICT -> "Strict"
                        TrackingProtectionManager.TrackingProtectionLevel.CUSTOM -> "Custom"
                    }
                    val description = TrackingProtectionManager().getTrackingProtectionDescription(level)
                    
                    SettingsItem(
                        title = displayName,
                        icon = Icons.Default.Security,
                        onClick = { trackingProtectionLevel.value = level }
                    ) {
                        if (trackingProtectionLevel.value == level) {
                            Icon(Icons.Default.Lock, contentDescription = "Selected")
                        }
                    }
                    
                    if (trackingProtectionLevel.value == level) {
                        Text(
                            text = description,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 32.dp, top = 4.dp, bottom = 8.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // HTTPS-Only Mode Section
            SettingsCategory(title = "HTTPS-Only Mode")
            
            Text(
                text = HttpsOnlyManager().getHttpsOnlyDescription(),
                style = MaterialTheme.typography.bodySmall
            )
            
            Spacer(modifier = Modifier.height(8.dp))

            SettingsItem(
                title = "HTTPS-Only Mode",
                icon = Icons.Default.Https,
                onClick = { httpsOnlyEnabled.value = !httpsOnlyEnabled.value }
            ) {
                Switch(
                    checked = httpsOnlyEnabled.value,
                    onCheckedChange = { httpsOnlyEnabled.value = it }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Clear Browsing Data Section
            SettingsCategory(title = "Clear Browsing Data")
            
            Text(
                text = "Clear your browsing data to protect your privacy.",
                style = MaterialTheme.typography.bodySmall
            )
            
            Spacer(modifier = Modifier.height(8.dp))

            SettingsItem(
                title = "Clear Browsing Data",
                icon = Icons.Default.CleanHands,
                onClick = onNavigateToClearData
            ) {
                Icon(Icons.Default.ArrowForward, contentDescription = "Open")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Site Permissions Section
            SettingsCategory(title = "Site Permissions")
            
            Text(
                text = "Manage permissions for specific websites.",
                style = MaterialTheme.typography.bodySmall
            )
            
            Spacer(modifier = Modifier.height(8.dp))

            SettingsItem(
                title = "Site Permissions",
                icon = Icons.Default.Security,
                onClick = onNavigateToSitePermissions
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
