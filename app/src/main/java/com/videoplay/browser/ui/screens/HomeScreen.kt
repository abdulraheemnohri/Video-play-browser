package com.videoplay.browser.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Tab
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.videoplay.browser.R

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
    val context = LocalContext.current

    // Mock data for quick access
    val quickAccessSites = listOf(
        QuickAccessSite("Google", "https://www.google.com", R.drawable.ic_launcher_foreground),
        QuickAccessSite("YouTube", "https://www.youtube.com", R.drawable.ic_launcher_foreground),
        QuickAccessSite("GitHub", "https://www.github.com", R.drawable.ic_launcher_foreground),
        QuickAccessSite("Twitter", "https://www.twitter.com", R.drawable.ic_launcher_foreground)
    )

    // Mock data for recently visited
    val recentlyVisited = listOf(
        HistoryEntry("https://www.google.com", "Google", "2 hours ago"),
        HistoryEntry("https://www.youtube.com", "YouTube", "1 day ago")
    )

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
                .verticalScroll(rememberScrollState())
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
                keyboardOptions = androidx.compose.ui.text.input.KeyboardOptions(
                    keyboardType = androidx.compose.ui.text.input.KeyboardType.Uri
                ),
                keyboardActions = androidx.compose.ui.text.input.KeyboardActions(
                    onSearch = { onNavigateToBrowser() }
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Quick Access
            Text(
                text = "Quick Access",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(quickAccessSites) { site ->
                    QuickAccessItem(
                        site = site,
                        onClick = { onNavigateToBrowser() }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Recently Visited
            Text(
                text = "Recently Visited",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))

            if (recentlyVisited.isEmpty()) {
                Text("No recent visits yet.")
            } else {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    recentlyVisited.forEach { entry ->
                        HistoryItem(
                            entry = entry,
                            onClick = { onNavigateToBrowser() },
                            onDelete = { /* TODO: Delete history entry */ }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Quick Actions
            Text(
                text = "Quick Actions",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                QuickActionButton(
                    icon = Icons.Default.History,
                    label = "History",
                    onClick = { /* TODO: Navigate to History */ }
                )
                QuickActionButton(
                    icon = Icons.Default.Bookmark,
                    label = "Bookmarks",
                    onClick = { /* TODO: Navigate to Bookmarks */ }
                )
                QuickActionButton(
                    icon = Icons.Default.Download,
                    label = "Downloads",
                    onClick = { /* TODO: Navigate to Downloads */ }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // New Tab Button
            Button(
                onClick = onNavigateToBrowser,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Open New Tab")
            }
        }
    }
}

/**
 * Data class for quick access sites.
 */
data class QuickAccessSite(
    val name: String,
    val url: String,
    val iconRes: Int
)

/**
 * Composable for a quick access item.
 */
@Composable
fun QuickAccessItem(
    site: QuickAccessSite,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .size(80.dp)
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = site.iconRes,
                contentDescription = site.name,
                modifier = Modifier.size(32.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = site.name,
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

/**
 * Composable for a quick action button.
 */
@Composable
fun QuickActionButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .weight(1f)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.size(64.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Icon(
                icon,
                contentDescription = label,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall
        )
    }
}
