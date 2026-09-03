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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

/**
 * Screen for clearing browsing data with options to select what to clear.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClearBrowsingDataScreen(
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val clearBrowsingDataManager = remember { ClearBrowsingDataManager(context) }
    
    var selectedDataTypes by remember { mutableStateOf(setOf<ClearBrowsingDataManager.DataType>()) }
    var showConfirmationDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Clear Browsing Data") },
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
            Text(
                text = "Choose what to clear",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "This will clear the selected data from your browser.",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            clearBrowsingDataManager.getDataTypesForDisplay().forEach { (dataType, displayName) ->
                if (dataType != ClearBrowsingDataManager.DataType.ALL) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedDataTypes = if (dataType in selectedDataTypes) {
                                    selectedDataTypes - dataType
                                } else {
                                    selectedDataTypes + dataType
                                }
                            }
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = dataType in selectedDataTypes,
                            onCheckedChange = { isChecked ->
                                selectedDataTypes = if (isChecked) {
                                    selectedDataTypes + dataType
                                } else {
                                    selectedDataTypes - dataType
                                }
                            }
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = displayName,
                                style = MaterialTheme.typography.titleSmall
                            )
                            Text(
                                text = clearBrowsingDataManager.getDataTypeDescription(dataType),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = { selectedDataTypes = emptySet() }
                ) {
                    Text("Deselect All")
                }

                OutlinedButton(
                    onClick = { 
                        selectedDataTypes = clearBrowsingDataManager.getDefaultDataTypes()
                    }
                ) {
                    Text("Select Default")
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { showConfirmationDialog = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                )
            ) {
                Icon(Icons.Default.Delete, contentDescription = "Clear")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Clear Selected Data")
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        if (showConfirmationDialog) {
            AlertDialog(
                onDismissRequest = { showConfirmationDialog = false },
                title = { Text("Clear Browsing Data") },
                text = {
                    Column {
                        Text("Are you sure you want to clear the selected data?")
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Info, contentDescription = "Info")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("This action cannot be undone.")
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showConfirmationDialog = false
                            clearBrowsingDataManager.clearBrowsingData(
                                selectedDataTypes,
                                onComplete = { }
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error,
                            contentColor = MaterialTheme.colorScheme.onError
                        )
                    ) {
                        Text("Clear")
                    }
                },
                dismissButton = {
                    OutlinedButton(
                        onClick = { showConfirmationDialog = false }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}
