package com.videoplay.browser.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.videoplay.browser.tabs.Tab
import com.videoplay.browser.viewmodel.BrowserViewModel

/**
 * Tabs Screen for VIDEOPlay Browser.
 * Displays the list of open tabs and provides tab management options.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabsScreen(
    onBack: () -> Unit,
    onNavigateToBrowser: () -> Unit,
    viewModel: BrowserViewModel = viewModel()
) {
    val tabs by viewModel.tabs.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tabs") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.addNewTab() }
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "New Tab")
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
            // New Tab Button
            Button(
                onClick = { viewModel.addNewTab() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("New Tab")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tabs List
            if (tabs.isEmpty()) {
                Text("No tabs open.")
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(tabs) { tab ->
                        TabItem(
                            tab = tab,
                            onClick = {
                                viewModel.switchToTab(tabs.indexOf(tab))
                                onNavigateToBrowser()
                            },
                            onClose = { viewModel.closeTab(tab.id) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Close All Tabs Button
            Button(
                onClick = { viewModel.closeAllTabs() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Close All Tabs")
            }
        }
    }
}

/**
 * Composable for a single tab item in the tabs list.
 */
@Composable
fun TabItem(
    tab: Tab,
    onClick: () -> Unit,
    onClose: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = tab.title.ifEmpty { "Untitled" },
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = tab.url,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            IconButton(onClick = onClose) {
                Icon(Icons.Default.Close, contentDescription = "Close Tab")
            }
        }
    }
}
