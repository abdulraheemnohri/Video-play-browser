package com.videoplay.browser.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Tab
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.videoplay.browser.privacy.ClearBrowsingDataScreen
import com.videoplay.browser.privacy.SitePermissionsScreen
import com.videoplay.browser.ui.screens.BookmarksScreen
import com.videoplay.browser.ui.screens.BrowserScreen
import com.videoplay.browser.ui.screens.DownloadsScreen
import com.videoplay.browser.ui.screens.HistoryScreen
import com.videoplay.browser.ui.screens.HomeScreen
import com.videoplay.browser.ui.screens.PrivacySettingsScreen
import com.videoplay.browser.ui.screens.SettingsScreen
import com.videoplay.browser.ui.screens.TabsScreen
import com.videoplay.browser.video.settings.VideoSettingsScreen

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

    val navigationItems = listOf(
        NavigationItem("home", "Home", Icons.Default.Home),
        NavigationItem("browser", "Browser", Icons.Default.Public),
        NavigationItem("tabs", "Tabs", Icons.Default.Tab),
        NavigationItem("history", "History", Icons.Default.History),
        NavigationItem("settings", "Settings", Icons.Default.Settings)
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
                        onClick = { 
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(item.icon, contentDescription = item.title) },
                        label = { Text(item.title) }
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            NavHost(
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
                        onNavigateToBrowser = { url -> 
                            navController.navigate("browser") {
                                launchSingleTop = true
                            }
                        }
                    )
                }

                composable("settings") {
                    SettingsScreen(
                        onBack = { navController.popBackStack() },
                        onNavigateToPrivacy = { navController.navigate("privacy_settings") },
                        onNavigateToVideoSettings = { navController.navigate("video_settings") }
                    )
                }

                composable("bookmarks") {
                    BookmarksScreen(
                        onBack = { navController.popBackStack() },
                        onNavigateToBrowser = { url -> 
                            navController.navigate("browser") {
                                launchSingleTop = true
                            }
                        }
                    )
                }

                composable("downloads") {
                    DownloadsScreen(
                        onBack = { navController.popBackStack() },
                        onOpenDownload = { url -> 
                            navController.navigate("browser") {
                                launchSingleTop = true
                            }
                        }
                    )
                }

                composable("video_settings") {
                    VideoSettingsScreen(
                        onBack = { navController.popBackStack() }
                    )
                }

                composable("privacy_settings") {
                    PrivacySettingsScreen(
                        onBack = { navController.popBackStack() },
                        onNavigateToClearData = { navController.navigate("clear_data") },
                        onNavigateToSitePermissions = { navController.navigate("site_permissions") }
                    )
                }

                composable("clear_data") {
                    ClearBrowsingDataScreen(
                        onBack = { navController.popBackStack() }
                    )
                }

                composable("site_permissions") {
                    SitePermissionsScreen(
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
