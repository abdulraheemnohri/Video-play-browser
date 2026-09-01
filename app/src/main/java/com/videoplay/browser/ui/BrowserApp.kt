package com.videoplay.browser.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.videoplay.browser.ui.screens.BookmarksScreen
import com.videoplay.browser.ui.screens.BrowserScreen
import com.videoplay.browser.ui.screens.DownloadsScreen
import com.videoplay.browser.ui.screens.HistoryScreen
import com.videoplay.browser.ui.screens.HomeScreen
import com.videoplay.browser.ui.screens.SettingsScreen
import com.videoplay.browser.ui.screens.TabsScreen
import com.videoplay.browser.ui.screens.VideoSettingsScreen

/**
 * Root Composable for the VIDEOPlay Browser app.
 * Handles navigation between screens with modern Android navigation.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrowserApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination?.route

    // Navigation items for bottom navigation
    val navigationItems = listOf(
        NavigationItem("home", "Home", androidx.compose.material.icons.Icons.Default.Home),
        NavigationItem("browser", "Browser", androidx.compose.material.icons.Icons.Default.Public),
        NavigationItem("tabs", "Tabs", androidx.compose.material.icons.Icons.Default.Tab),
        NavigationItem("history", "History", androidx.compose.material.icons.Icons.Default.History),
        NavigationItem("settings", "Settings", androidx.compose.material.icons.Icons.Default.Settings)
    )

    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar(
                modifier = Modifier.padding(WindowInsets.safeDrawing.asPaddingValues()),
                windowInsets = WindowInsets(0, 0, 0, 0)
            ) {
                navigationItems.forEach { item ->
                    NavigationBarItem(
                        selected = currentDestination == item.route,
                        onClick = { navController.navigate(item.route) },
                        icon = { Icon(item.icon, contentDescription = item.title) },
                        label = { Text(item.title) }
                    )
                }
            }
        }
    ) { paddingValues ->
        androidx.compose.foundation.layout.Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            androidx.navigation.compose.NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.fillMaxSize()
            ) {
                composable("home") {
                    HomeScreen(
                        onNavigateToBrowser = { navController.navigate("browser") },
                        onNavigateToTabs = { navController.navigate("tabs") },
                        onNavigateToSettings = { navController.navigate("settings") }
                    )
                }

                composable("browser") {
                    BrowserScreen(
                        onBack = { navController.popBackStack() }
                    )
                }

                composable("tabs") {
                    TabsScreen(
                        onBack = { navController.popBackStack() },
                        onNavigateToBrowser = { navController.navigate("browser") }
                    )
                }

                composable("history") {
                    HistoryScreen(
                        onBack = { navController.popBackStack() },
                        onNavigateToBrowser = { url -> navController.navigate("browser") }
                    )
                }

                composable("settings") {
                    SettingsScreen(
                        onBack = { navController.popBackStack() }
                    )
                }

                composable("bookmarks") {
                    BookmarksScreen(
                        onBack = { navController.popBackStack() },
                        onNavigateToBrowser = { url -> navController.navigate("browser") }
                    )
                }

                composable("downloads") {
                    DownloadsScreen(
                        onBack = { navController.popBackStack() },
                        onOpenDownload = { url -> navController.navigate("browser") }
                    )
                }

                composable("video_settings") {
                    VideoSettingsScreen(
                        onBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}

/**
 * Data class for navigation items.
 */
data class NavigationItem(
    val route: String,
    val title: String,
    val icon: ImageVector
)
