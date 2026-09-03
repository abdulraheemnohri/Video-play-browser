package com.videoplay.browser.privacy

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
    var searchQuery by remember { mutableStateOf("") }
    var expandedSite by remember { mutableStateOf<String?>(null) }

    val sitesWithPermissions = remember {
        listOf(
            "example.com",
            "google.com",
            "youtube.com"
        )
    }

    val filteredSites = remember(sitesWithPermissions, searchQuery) {
        sitesWithPermissions.filter {
            it.contains(searchQuery, ignoreCase = true)
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
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
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
                    modifier = Modifier.weight(1f)
                ) {
                    items(filteredSites) { site ->
                        SitePermissionItem(
                            site = site,
                            isExpanded = expandedSite == site,
                            permissions = sitePermissionsManager.getSitePermissions(site),
                            permissionTypes = sitePermissionsManager.getPermissionTypesForDisplay(),
                            permissionStates = sitePermissionsManager.getPermissionStatesForDisplay(),
                            onPermissionChange = { permissionType, state ->
                                sitePermissionsManager.setPermission(site, permissionType, state)
                            },
                            onClearPermissions = {
                                sitePermissionsManager.clearSitePermissions(site)
                            },
                            onToggleExpand = {
                                expandedSite = if (expandedSite == site) null else site
                            }
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
                    displayName = displayName,
                    currentState = defaultState,
                    permissionStates = sitePermissionsManager.getPermissionStatesForDisplay(),
                    onStateChange = { state -> }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun SitePermissionItem(
    site: String,
    isExpanded: Boolean,
    permissions: Map<SitePermissionsManager.PermissionType, SitePermissionsManager.PermissionState>,
    permissionTypes: List<Pair<SitePermissionsManager.PermissionType, String>>,
    permissionStates: List<Pair<SitePermissionsManager.PermissionState, String>>,
    onPermissionChange: (SitePermissionsManager.PermissionType, SitePermissionsManager.PermissionState) -> Unit,
    onClearPermissions: () -> Unit,
    onToggleExpand: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onToggleExpand() },
            verticalAlignment = Alignment.CenterVertically
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

        if (isExpanded) {
            Spacer(modifier = Modifier.height(8.dp))
            permissions.forEach { (permissionType, state) ->
                val displayName = permissionTypes.find { it.first == permissionType }?.second ?: "Unknown"
                PermissionItem(
                    displayName = displayName,
                    currentState = state,
                    permissionStates = permissionStates,
                    onStateChange = { newState ->
                        onPermissionChange(permissionType, newState)
                    }
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = onClearPermissions) {
                    Icon(Icons.Default.Delete, contentDescription = "Clear permissions")
                }
            }
        }
    }
}

@Composable
fun PermissionItem(
    displayName: String,
    currentState: SitePermissionsManager.PermissionState,
    permissionStates: List<Pair<SitePermissionsManager.PermissionState, String>>,
    onStateChange: (SitePermissionsManager.PermissionState) -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { showMenu = true },
        verticalAlignment = Alignment.CenterVertically
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
        IconButton(onClick = { showMenu = true }) {
            Icon(Icons.Default.ArrowDropDown, contentDescription = "Change")
        }

        DropdownMenu(
            expanded = showMenu,
            onDismissRequest = { showMenu = false }
        ) {
            permissionStates.forEach { (state, displayState) ->
                DropdownMenuItem(
                    text = { Text(displayState) },
                    onClick = {
                        onStateChange(state)
                        showMenu = false
                    }
                )
            }
        }
    }
}
