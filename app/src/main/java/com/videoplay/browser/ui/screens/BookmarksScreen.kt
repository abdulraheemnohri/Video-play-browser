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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.unit.dp

/**
 * Bookmarks Screen for VIDEOPlay Browser.
 * Displays bookmarks in folders with search and delete options.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarksScreen(
    onBack: () -> Unit,
    onNavigateToBrowser: (String) -> Unit
) {
    val searchQuery = remember { mutableStateOf("") }
    
    // Mock data for bookmark folders and entries
    val bookmarkFolders = remember {
        listOf(
            BookmarkFolder("1", "General", listOf(
                BookmarkEntry("1", "https://www.google.com", "Google"),
                BookmarkEntry("2", "https://www.youtube.com", "YouTube")
            )),
            BookmarkFolder("2", "Social", listOf(
                BookmarkEntry("3", "https://www.facebook.com", "Facebook"),
                BookmarkEntry("4", "https://www.twitter.com", "Twitter")
            ))
        )
    }

    val filteredFolders = remember(bookmarkFolders, searchQuery.value) {
        bookmarkFolders.map { folder ->
            folder.copy(
                bookmarks = folder.bookmarks.filter {
                    it.title.contains(searchQuery.value, ignoreCase = true) ||
                            it.url.contains(searchQuery.value, ignoreCase = true)
                }
            )
        }.filter { it.bookmarks.isNotEmpty() }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bookmarks") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* TODO: Add new bookmark */ }) {
                Icon(Icons.Default.Add, contentDescription = "Add Bookmark")
            }
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
                label = { Text("Search Bookmarks") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Bookmark Folders
            if (filteredFolders.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Text("No bookmarks found.")
                    Spacer(modifier = Modifier.weight(1f))
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    filteredFolders.forEach { folder ->
                        stickyHeader {
                            FolderHeader(folder = folder)
                        }
                        items(folder.bookmarks) { bookmark ->
                            BookmarkItem(
                                bookmark = bookmark,
                                onClick = { onNavigateToBrowser(bookmark.url) },
                                onDelete = { /* TODO: Delete bookmark */ }
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Data classes for bookmark folders and entries.
 */
data class BookmarkFolder(
    val id: String,
    val name: String,
    val bookmarks: List<BookmarkEntry>
)

data class BookmarkEntry(
    val id: String,
    val url: String,
    val title: String
)

/**
 * Composable for a folder header.
 */
@Composable
fun FolderHeader(folder: BookmarkFolder) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.Folder, contentDescription = "Folder")
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = folder.name,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

/**
 * Composable for a single bookmark item.
 */
@Composable
fun BookmarkItem(
    bookmark: BookmarkEntry,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp)
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
                    text = bookmark.title,
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = bookmark.url,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}
