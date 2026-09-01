package com.videoplay.browser.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.videoplay.browser.ui.screens.BrowserScreen
import com.videoplay.browser.ui.screens.HomeScreen
import com.videoplay.browser.ui.screens.SettingsScreen
import com.videoplay.browser.ui.screens.TabsScreen

/**
 * Root Composable for the VIDEOPlay Browser app.
 * Handles navigation between screens.
 */
@Composable
fun BrowserApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
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

        composable("settings") {
            SettingsScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
