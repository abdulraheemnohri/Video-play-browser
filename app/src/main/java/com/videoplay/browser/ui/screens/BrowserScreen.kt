package com.videoplay.browser.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.videoplay.browser.viewmodel.BrowserViewModel
import org.mozilla.geckoview.GeckoSession
import org.mozilla.geckoview.GeckoView

/**
 * Browser Screen for VIDEOPlay Browser.
 * Displays the GeckoView and provides navigation controls.
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

    // Update URL when tab changes
    LaunchedEffect(currentTab) {
        currentTab?.let { tab ->
            url = tab.url
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("VIDEOPlay Browser") },
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
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Address Bar
            OutlinedTextField(
                value = url,
                onValueChange = { url = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("URL") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                singleLine = true,
                keyboardOptions = androidx.compose.ui.text.input.KeyboardOptions(
                    keyboardType = androidx.compose.ui.text.input.KeyboardType.Uri
                ),
                keyboardActions = androidx.compose.ui.text.input.KeyboardActions(
                    onSearch = {
                        viewModel.loadUrl(url)
                    }
                )
            )

            // Progress Bar
            if (isLoading) {
                LinearProgressIndicator(
                    progress = { progress / 100f },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // GeckoView
            currentTab?.let { tab ->
                GeckoWebView(
                    session = tab.session,
                    onProgressChange = { newProgress ->
                        progress = newProgress
                        isLoading = newProgress < 100
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

/**
 * Composable for embedding GeckoView in Jetpack Compose.
 */
@Composable
fun GeckoWebView(
    session: GeckoSession,
    onProgressChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val geckoView = remember { GeckoView(androidx.compose.ui.platform.LocalContext.current) }

    LaunchedEffect(session) {
        geckoView.setSession(session)
    }

    // Set up progress listener
    LaunchedEffect(session) {
        session.progressDelegate = object : GeckoSession.ProgressDelegate {
            override fun onPageStart(session: GeckoSession, url: String) {
                onProgressChange(0)
            }

            override fun onPageStop(session: GeckoSession, success: Boolean) {
                onProgressChange(100)
            }

            override fun onProgressChange(session: GeckoSession, progress: Int) {
                onProgressChange(progress)
            }
        }
    }

    AndroidView(
        factory = { context ->
            geckoView
        },
        modifier = modifier
    )
}
