package com.videoplay.browser.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Tab
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

/**
 * Home Screen for VIDEOPlay Browser.
 * Displays quick access, recently visited, and navigation options.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToBrowser: () -> Unit,
    onNavigateToTabs: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    val searchQuery = remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("VIDEOPlay Browser") },
                actions = {
                    IconButton(onClick = onNavigateToTabs) {
                        Icon(Icons.Default.Tab, contentDescription = "Tabs")
                    }
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Search Bar
            OutlinedTextField(
                value = searchQuery.value,
                onValueChange = { searchQuery.value = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Search or enter URL") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                keyboardType = KeyboardType.Uri,
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Quick Access Buttons
            Button(
                onClick = { onNavigateToBrowser() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Open New Tab")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { onNavigateToTabs() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("View Tabs")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { onNavigateToSettings() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Settings")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Recently Visited (Placeholder)
            Text(
                text = "Recently Visited",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text("No recent visits yet.")
        }
    }
}
