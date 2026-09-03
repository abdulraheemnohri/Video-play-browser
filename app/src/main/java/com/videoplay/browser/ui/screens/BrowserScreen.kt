package com.videoplay.browser.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.videoplay.browser.viewmodel.BrowserViewModel
import org.mozilla.geckoview.GeckoSession
import org.mozilla.geckoview.GeckoView

/**
 * Browser Screen for VIDEOPlay Browser.
 * Displays the GeckoView and provides navigation controls with modern UI.
 * Fixes progress bar accuracy and ensures proper session management.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrowserScreen(
    onBack: () -> Unit,
    viewModel: BrowserViewModel = viewModel()
) {
    val currentTab by viewModel.currentTab.collectAsState()
    val navigationController by viewModel.navigationController.collectAsState()
    var url by remember { mutableStateOf("about:blank") }
    var isLoading by remember { mutableStateOf(false) }
    var progress by remember { mutableStateOf(0) }
    var showAddressBar by remember { mutableStateOf(true) }

    // Update URL when tab changes
    LaunchedEffect(currentTab) {
        currentTab?.let { tab ->
            url = tab.url
            isLoading = false
            progress = 0
        }
    }

    Scaffold(
        topBar = {
            if (showAddressBar) {
                TopAppBar(
                    title = {
                        OutlinedTextField(
                            value = url,
                            onValueChange = { url = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Search or enter URL") },
                            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Uri
                            ),
                            keyboardActions = KeyboardActions(
                                onSearch = {
                                    viewModel.loadUrl(url)
                                }
                            )
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = { viewModel.goBack() },
                            enabled = navigationController?.canGoBack == true
                        ) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                        IconButton(
                            onClick = { viewModel.goForward() },
                            enabled = navigationController?.canGoForward == true
                        ) {
                            Icon(Icons.Default.ArrowForward, contentDescription = "Forward")
                        }
                        IconButton(onClick = { viewModel.reload() }) {
                            Icon(Icons.Default.Refresh, contentDescription = "Reload")
                        }
                        IconButton(onClick = { /* Share */ }) {
                            Icon(Icons.Default.Share, contentDescription = "Share")
                        }
                        IconButton(onClick = { /* Bookmark */ }) {
                            Icon(Icons.Default.Star, contentDescription = "Bookmark")
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // GeckoView
            currentTab?.let { tab ->
                GeckoWebView(
                    session = tab.session,
                    onProgressChange = { newProgress ->
                        progress = newProgress
                        isLoading = newProgress < 100
                    },
                    onPageStart = { newUrl ->
                        url = newUrl
                        isLoading = true
                        progress = 0
                    },
                    onPageStop = { success ->
                        isLoading = false
                        progress = if (success) 100 else 0
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Progress Bar (Only show if loading and progress < 100)
            if (isLoading && progress < 100) {
                LinearProgressIndicator(
                    progress = { progress / 100f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                )
            }

            // Loading Indicator (Only show if loading and progress is 0)
            if (isLoading && progress == 0) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

/**
 * Composable for embedding GeckoView in Jetpack Compose.
 * Ensures proper progress tracking and error handling.
 */
@Composable
fun GeckoWebView(
    session: GeckoSession,
    onProgressChange: (Int) -> Unit,
    onPageStart: (String) -> Unit,
    onPageStop: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val geckoView = remember { GeckoView(context) }

    LaunchedEffect(session) {
        geckoView.setSession(session)
    }

    // Set up progress listener
    LaunchedEffect(session) {
        session.progressDelegate = object : GeckoSession.ProgressDelegate {
            override fun onPageStart(session: GeckoSession, url: String) {
                onPageStart(url)
                onProgressChange(0)
            }

            override fun onPageStop(session: GeckoSession, success: Boolean) {
                onPageStop(success)
                onProgressChange(if (success) 100 else 0)
            }

            override fun onProgressChange(session: GeckoSession, progress: Int) {
                onProgressChange(progress)
            }
        }
    }

    AndroidView(
        factory = {
            geckoView
        },
        modifier = modifier
    )
}
