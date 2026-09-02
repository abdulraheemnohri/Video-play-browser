package com.videoplay.browser.android

import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Build
import androidx.annotation.RequiresApi
import com.videoplay.browser.R
import com.videoplay.browser.ui.MainActivity

/**
 * Manages app shortcuts for quick access to common actions.
 * Supports dynamic shortcuts on Android 7.1+ (API 25+).
 */
class AppShortcutsManager(private val context: Context) {

    private val shortcutManager: ShortcutManager? by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            context.getSystemService(ShortcutManager::class.java)
        } else {
            null
        }
    }

    /**
     * Creates and publishes dynamic app shortcuts.
     */
    @RequiresApi(Build.VERSION_CODES.N_MR1)
    fun createDynamicShortcuts() {
        if (shortcutManager == null) return

        val shortcuts = listOf(
            createShortcut(
                "new_tab",
                "New Tab",
                "Open a new tab",
                R.drawable.ic_launcher_foreground,
                Intent(context, MainActivity::class.java).apply {
                    action = Intent.ACTION_VIEW
                    putExtra("shortcut", "new_tab")
                }
            ),
            createShortcut(
                "private_tab",
                "New Private Tab",
                "Open a new private tab",
                R.drawable.ic_launcher_foreground,
                Intent(context, MainActivity::class.java).apply {
                    action = Intent.ACTION_VIEW
                    putExtra("shortcut", "private_tab")
                }
            ),
            createShortcut(
                "search",
                "Search",
                "Search the web",
                R.drawable.ic_launcher_foreground,
                Intent(context, MainActivity::class.java).apply {
                    action = Intent.ACTION_VIEW
                    putExtra("shortcut", "search")
                }
            ),
            createShortcut(
                "downloads",
                "Downloads",
                "View your downloads",
                R.drawable.ic_launcher_foreground,
                Intent(context, MainActivity::class.java).apply {
                    action = Intent.ACTION_VIEW
                    putExtra("shortcut", "downloads")
                }
            )
        )

        shortcutManager?.dynamicShortcuts = shortcuts
    }

    /**
     * Creates a shortcut with the given parameters.
     */
    @RequiresApi(Build.VERSION_CODES.N_MR1)
    private fun createShortcut(
        id: String,
        shortLabel: String,
        longLabel: String,
        iconRes: Int,
        intent: Intent
    ): ShortcutInfo {
        return ShortcutInfo.Builder(context, id)
            .setShortLabel(shortLabel)
            .setLongLabel(longLabel)
            .setIcon(Icon.createWithResource(context, iconRes))
            .setIntent(intent)
            .build()
    }

    /**
     * Updates a dynamic shortcut.
     */
    @RequiresApi(Build.VERSION_CODES.N_MR1)
    fun updateShortcut(shortcut: ShortcutInfo) {
        shortcutManager?.updateShortcuts(listOf(shortcut))
    }

    /**
     * Removes a dynamic shortcut.
     */
    @RequiresApi(Build.VERSION_CODES.N_MR1)
    fun removeShortcut(shortcutId: String) {
        shortcutManager?.removeDynamicShortcuts(listOf(shortcutId))
    }

    /**
     * Removes all dynamic shortcuts.
     */
    @RequiresApi(Build.VERSION_CODES.N_MR1)
    fun removeAllShortcuts() {
        shortcutManager?.removeAllDynamicShortcuts()
    }

    /**
     * Gets the list of all dynamic shortcuts.
     */
    @RequiresApi(Build.VERSION_CODES.N_MR1)
    fun getDynamicShortcuts(): List<ShortcutInfo> {
        return shortcutManager?.dynamicShortcuts ?: emptyList()
    }

    /**
     * Checks if dynamic shortcuts are supported on this device.
     */
    fun isSupported(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1 && shortcutManager != null
    }

    /**
     * Reports that a shortcut was used.
     */
    @RequiresApi(Build.VERSION_CODES.N_MR1)
    fun reportShortcutUsed(shortcutId: String) {
        shortcutManager?.reportShortcutUsed(shortcutId)
    }

    /**
     * Handles a shortcut click from the launcher.
     * @param intent The intent that was received.
     * @return True if the intent was handled, false otherwise.
     */
    fun handleShortcut(intent: Intent): Boolean {
        val shortcut = intent.getStringExtra("shortcut")
        return when (shortcut) {
            "new_tab" -> {
                // Handle new tab shortcut
                true
            }
            "private_tab" -> {
                // Handle private tab shortcut
                true
            }
            "search" -> {
                // Handle search shortcut
                true
            }
            "downloads" -> {
                // Handle downloads shortcut
                true
            }
            else -> false
        }
    }
}
