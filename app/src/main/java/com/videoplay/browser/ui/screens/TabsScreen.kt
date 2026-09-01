package com.videoplay.browser.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.videoplay.browser.tabs.Tab
import com.videoplay.browser.viewmodel.BrowserViewModel

/**
 * Tabs Screen for VIDEOPlay Browser.
 * Displays the list of open tabs with search and management options.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabsScreen(
    onBack: () -> Unit,
    onNavigateToBrowser: () -> Unit,
    viewModel: BrowserViewModel = viewModel()
) {
    val tabs by viewModel.tabs.collectAsState()
    val searchQuery = remember { mutableStateOf("") }

    val filteredTabs = remember(tabs, searchQuery.value) {
        tabs.filter {
            it.title.contains(searchQuery.value, ignoreCase = true) ||
                    it.url.contains(searchQuery.value, ignoreCase = true)
        }
    }

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
                .padding(16.dp)
        ) {
            // Search Bar
            OutlinedTextField(
                value = searchQuery.value,
                onValueChange = { searchQuery.value = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Search Tabs") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Tabs Count
            Text(
                text = "${filteredTabs.size} tabs open",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Tabs List
            if (filteredTabs.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("No tabs found.")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(filteredTabs) { tab ->
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

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { viewModel.addNewTab() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("New Tab")
                }
                Button(
                    onClick = { viewModel.closeAllTabs() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Close All")
                }
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
