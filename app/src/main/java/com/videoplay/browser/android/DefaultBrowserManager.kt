package com.videoplay.browser.android

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi

/**
 * Manages default browser settings.
 * Allows users to set VIDEOPlay Browser as the default browser.
 */
class DefaultBrowserManager(private val context: Context) {

    /**
     * Checks if VIDEOPlay Browser is set as the default browser.
     * @return True if VIDEOPlay is the default browser, false otherwise.
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    fun isDefaultBrowser(): Boolean {
        val roleManager = context.getSystemService(Context.ROLE_SERVICE) as? android.app.role.RoleManager
        return roleManager?.isRoleHeld(android.app.role.RoleManager.ROLE_BROWSER) ?: false
    }

    /**
     * Requests to set VIDEOPlay Browser as the default browser.
     * This will show a system dialog asking the user to confirm.
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    fun requestDefaultBrowser() {
        val roleManager = context.getSystemService(Context.ROLE_SERVICE) as? android.app.role.RoleManager
        val intent = roleManager?.createRequestRoleIntent(android.app.role.RoleManager.ROLE_BROWSER)
        intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    /**
     * Opens the system settings for default apps.
     */
    fun openDefaultAppsSettings() {
        val intent = Intent(android.provider.Settings.ACTION_MANAGE_DEFAULT_APPS_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    /**
     * Checks if the device supports setting default browsers.
     * @return True if the device supports default browser settings, false otherwise.
     */
    fun isSupported(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
    }

    /**
     * Gets the intent to open a URL in the default browser.
     * @param url The URL to open.
     * @return The intent to open the URL.
     */
    fun getOpenUrlIntent(url: String): Intent {
        return Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    }

    /**
     * Gets the intent to open a URL in VIDEOPlay Browser specifically.
     * @param url The URL to open.
     * @return The intent to open the URL in VIDEOPlay Browser.
     */
    fun getOpenInVideoPlayIntent(url: String): Intent {
        return Intent(context, com.videoplay.browser.ui.MainActivity::class.java).apply {
            action = Intent.ACTION_VIEW
            data = Uri.parse(url)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    }

    /**
     * Gets the package name of the current default browser.
     * @return The package name of the default browser, or null if not set.
     */
    fun getDefaultBrowserPackageName(): String? {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://"))
        val resolveInfo = context.packageManager.resolveActivity(browserIntent, 0)
        return resolveInfo?.activityInfo?.packageName
    }

    /**
     * Gets a description of the default browser status.
     * @return A human-readable description.
     */
    fun getDefaultBrowserStatus(): String {
        return if (isSupported()) {
            if (isDefaultBrowser()) {
                "VIDEOPlay Browser is your default browser"
            } else {
                val defaultBrowser = getDefaultBrowserPackageName()
                if (defaultBrowser != null) {
                    "Default browser: $defaultBrowser"
                } else {
                    "No default browser set"
                }
            }
        } else {
            "Default browser settings not supported on this device"
        }
    }
}
