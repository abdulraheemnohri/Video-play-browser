package com.videoplay.browser.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Tab
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.videoplay.browser.R

data class ContinueWatchingVideo(
    val title: String,
    val url: String,
    val remainingTime: String,
    val progress: Float
)

/**
 * Home Screen Dashboard for VIDEOPlay Browser.
 * Conforms to spec section 10 and crash recovery section 80.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToBrowser: () -> Unit,
    onNavigateToTabs: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var showCrashRecoveryBanner by remember { mutableStateOf(true) }

    val quickAccessSites = listOf(
        QuickAccessSite("YouTube", "https://www.youtube.com", R.drawable.ic_launcher_foreground),
        QuickAccessSite("Vimeo", "https://www.vimeo.com", R.drawable.ic_launcher_foreground),
        QuickAccessSite("Twitch", "https://www.twitch.tv", R.drawable.ic_launcher_foreground),
        QuickAccessSite("Google", "https://www.google.com", R.drawable.ic_launcher_foreground)
    )

    val continueWatchingList = listOf(
        ContinueWatchingVideo("Android GeckoView Tutorial", "https://www.youtube.com/watch?v=1", "08:42 remaining", 0.65f),
        ContinueWatchingVideo("Kotlin Jetpack Compose Course", "https://www.youtube.com/watch?v=2", "15:21 remaining", 0.42f)
    )

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
            // Crash Recovery Banner (Section 80)
            if (showCrashRecoveryBanner) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "VideoPlay recovered",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "Your browser session can be restored.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(onClick = {
                                showCrashRecoveryBanner = false
                                onNavigateToBrowser()
                            }) {
                                Icon(Icons.Default.Refresh, contentDescription = null)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Restore Tabs")
                            }
                            OutlinedButton(onClick = { showCrashRecoveryBanner = false }) {
                                Text("Start Fresh")
                            }
                        }
                    }
                }
            }

            // Search / URL bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Search or enter URL") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Uri
                ),
                keyboardActions = KeyboardActions(
                    onSearch = { onNavigateToBrowser() }
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Quick Access
            Text(
                text = "Quick Access",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.Start)
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

            // Continue Watching Section
            Text(
                text = "Continue Watching",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                continueWatchingList.forEach { video ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable { onNavigateToBrowser() }
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.PlayCircle, contentDescription = null, modifier = Modifier.size(36.dp))
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(video.title, style = MaterialTheme.typography.titleSmall, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                Text(video.remainingTime, style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Recently Visited
            Text(
                text = "Recently Visited",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                recentlyVisited.forEach { entry ->
                    HistoryItem(
                        entry = entry,
                        onClick = { onNavigateToBrowser() },
                        onDelete = { }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Quick Actions
            Text(
                text = "Quick Actions",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                QuickActionButton(
                    icon = Icons.Default.History,
                    label = "History",
                    onClick = { }
                )
                QuickActionButton(
                    icon = Icons.Default.Bookmark,
                    label = "Bookmarks",
                    onClick = { }
                )
                QuickActionButton(
                    icon = Icons.Default.Download,
                    label = "Downloads",
                    onClick = { }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onNavigateToBrowser,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Open New Tab")
            }
        }
    }
}

data class QuickAccessSite(
    val name: String,
    val url: String,
    val iconRes: Int
)

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

@Composable
fun RowScope.QuickActionButton(
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
