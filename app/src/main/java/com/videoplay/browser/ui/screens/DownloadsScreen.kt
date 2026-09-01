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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.unit.dp

/**
 * Downloads Screen for VIDEOPlay Browser.
 * Displays download progress with pause/resume/cancel options.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DownloadsScreen(
    onBack: () -> Unit,
    onOpenDownload: (String) -> Unit
) {
    val searchQuery = remember { mutableStateOf("") }
    
    // Mock data for downloads
    val downloads = remember {
        listOf(
            DownloadEntry(
                id = "1",
                fileName = "video.mp4",
                url = "https://example.com/video.mp4",
                progress = 75,
                status = "Downloading",
                fileSize = "100 MB"
            ),
            DownloadEntry(
                id = "2",
                fileName = "document.pdf",
                url = "https://example.com/document.pdf",
                progress = 100,
                status = "Completed",
                fileSize = "10 MB"
            ),
            DownloadEntry(
                id = "3",
                fileName = "image.jpg",
                url = "https://example.com/image.jpg",
                progress = 0,
                status = "Paused",
                fileSize = "5 MB"
            )
        )
    }

    val filteredDownloads = remember(downloads, searchQuery.value) {
        downloads.filter {
            it.fileName.contains(searchQuery.value, ignoreCase = true) ||
                    it.url.contains(searchQuery.value, ignoreCase = true)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Downloads") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
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
            // Search Bar
            OutlinedTextField(
                value = searchQuery.value,
                onValueChange = { searchQuery.value = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Search Downloads") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Downloads List
            if (filteredDownloads.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Text("No downloads found.")
                    Spacer(modifier = Modifier.weight(1f))
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(filteredDownloads) { download ->
                        DownloadItem(
                            download = download,
                            onOpen = { onOpenDownload(download.url) },
                            onPauseResume = { /* TODO: Pause/Resume download */ },
                            onCancel = { /* TODO: Cancel download */ },
                            onDelete = { /* TODO: Delete download */ }
                        )
                    }
                }
            }
        }
    }
}

/**
 * Data class for download entries.
 */
data class DownloadEntry(
    val id: String,
    val fileName: String,
    val url: String,
    val progress: Int,
    val status: String,
    val fileSize: String
)

/**
 * Composable for a single download item.
 */
@Composable
fun DownloadItem(
    download: DownloadEntry,
    onOpen: () -> Unit,
    onPauseResume: () -> Unit,
    onCancel: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onOpen),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = download.fileName,
                        style = MaterialTheme.typography.titleSmall
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = download.fileSize,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Text(
                    text = download.status,
                    style = MaterialTheme.typography.labelSmall
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Progress Bar
            LinearProgressIndicator(
                progress = { download.progress / 100f },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.End
            ) {
                if (download.status == "Downloading") {
                    IconButton(onClick = onPauseResume) {
                        Icon(Icons.Default.Pause, contentDescription = "Pause")
                    }
                } else if (download.status == "Paused") {
                    IconButton(onClick = onPauseResume) {
                        Icon(Icons.Default.PlayArrow, contentDescription = "Resume")
                    }
                }
                IconButton(onClick = onCancel) {
                    Icon(Icons.Default.Cancel, contentDescription = "Cancel")
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
    }
}
