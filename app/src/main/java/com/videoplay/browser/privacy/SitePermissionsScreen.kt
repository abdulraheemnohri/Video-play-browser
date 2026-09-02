package com.videoplay.browser.privacy

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Screen for managing site-specific permissions.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SitePermissionsScreen(
    onBack: () -> Unit
) {
    val sitePermissionsManager = remember { SitePermissionsManager() }
    val searchQuery = remember { mutableStateOf("") }
    val expandedSite = remember { mutableStateOf<String?>(null) }

    // Mock data for sites with custom permissions
    val sitesWithPermissions = remember {
        listOf(
            "example.com",
            "google.com",
            "youtube.com"
        )
    }

    val filteredSites = remember(sitesWithPermissions, searchQuery.value) {
        sitesWithPermissions.filter {
            it.contains(searchQuery.value, ignoreCase = true)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Site Permissions") },
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
            // Search bar
            OutlinedTextField(
                value = searchQuery.value,
                onValueChange = { searchQuery.value = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Search sites") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Sites with custom permissions",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (filteredSites.isEmpty()) {
                Text("No sites with custom permissions found.")
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(filteredSites) { site ->
                        SitePermissionItem(
                            site = site,
                            permissions = sitePermissionsManager.getSitePermissions(site),
                            onPermissionChange = { permissionType, state ->
                                sitePermissionsManager.setPermission(site, permissionType, state)
                            },
                            onClearPermissions = {
                                sitePermissionsManager.clearSitePermissions(site)
                            },
                            onExpand = { expandedSite.value = it }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Default permissions",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            sitePermissionsManager.getPermissionTypesForDisplay().forEach { (permissionType, displayName) ->
                val defaultState = sitePermissionsManager.getDefaultPermission(permissionType)
                PermissionItem(
                    permissionType = permissionType,
                    displayName = displayName,
                    currentState = defaultState,
                    onStateChange = { state ->
                        // In a real implementation, this would update the default permission
                        // For now, we'll just show the current default
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

/**
 * Composable for a site with its permissions.
 */
@Composable
fun SitePermissionItem(
    site: String,
    permissions: Map<SitePermissionsManager.PermissionType, SitePermissionsManager.PermissionState>,
    onPermissionChange: (SitePermissionsManager.PermissionType, SitePermissionsManager.PermissionState) -> Unit,
    onClearPermissions: () -> Unit,
    onExpand: (String?) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // Site header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onExpand(if (expandedSite.value == site) null else site) },
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Text(
                text = site,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.weight(1f)
            )
            Icon(
                Icons.Default.ArrowDropDown,
                contentDescription = "Expand"
            )
        }

        // Permissions list (shown when expanded)
        if (expandedSite.value == site) {
            Spacer(modifier = Modifier.height(8.dp))
            permissions.forEach { (permissionType, state) ->
                PermissionItem(
                    permissionType = permissionType,
                    displayName = sitePermissionsManager.getPermissionTypesForDisplay()
                        .find { it.first == permissionType }?.second ?: "Unknown",
                    currentState = state,
                    onStateChange = { newState ->
                        onPermissionChange(permissionType, newState)
                    }
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.End
            ) {
                IconButton(onClick = onClearPermissions) {
                    Icon(Icons.Default.Delete, contentDescription = "Clear permissions")
                }
            }
        }
    }
}

/**
 * Composable for a single permission item.
 */
@Composable
fun PermissionItem(
    permissionType: SitePermissionsManager.PermissionType,
    displayName: String,
    currentState: SitePermissionsManager.PermissionState,
    onStateChange: (SitePermissionsManager.PermissionState) -> Unit
) {
    val showMenu = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { showMenu.value = true },
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        Text(
            text = displayName,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = currentState.name,
            style = MaterialTheme.typography.bodySmall
        )
        IconButton(onClick = { showMenu.value = true }) {
            Icon(Icons.Default.ArrowDropDown, contentDescription = "Change")
        }

        DropdownMenu(
            expanded = showMenu.value,
            onDismissRequest = { showMenu.value = false }
        ) {
            sitePermissionsManager.getPermissionStatesForDisplay().forEach { (state, displayState) ->
                DropdownMenuItem(
                    text = { Text(displayState) },
                    onClick = {
                        onStateChange(state)
                        showMenu.value = false
                    }
                )
            }
        }
    }
}
