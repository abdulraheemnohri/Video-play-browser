package com.videoplay.browser.android

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build

/**
 * Manages Android App Links for the browser.
 * Allows VIDEOPlay Browser to handle web links directly.
 */
class AppLinksManager(private val context: Context) {

    /**
     * Verifies that VIDEOPlay Browser can handle web links.
     * @return True if VIDEOPlay can handle web links, false otherwise.
     */
    fun verifyAppLinks(): Boolean {
        val packageManager = context.packageManager
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.example.com"))
        val resolveInfo = packageManager.resolveActivity(intent, 0)
        return resolveInfo?.activityInfo?.packageName == context.packageName
    }

    /**
     * Gets the intent filter for web links.
     * This should be added to the AndroidManifest.xml.
     */
    fun getWebIntentFilter(): String {
        return """
            <intent-filter>
                <action android:name="android.intent.action.VIEW" />
                <category android:name="android.intent.category.DEFAULT" />
                <category android:name="android.intent.category.BROWSABLE" />
                <data android:scheme="http" />
                <data android:scheme="https" />
            </intent-filter>
        """.trimIndent()
    }

    /**
     * Gets the digital asset links configuration.
     * This should be added to the AndroidManifest.xml.
     */
    fun getAssetLinksConfig(): String {
        return """
            <meta-data
                android:name="asset_statements"
                android:resource="@string/asset_statements" />
        """.trimIndent()
    }

    /**
     * Gets the asset statements for digital asset links.
     * This should be added to res/values/strings.xml.
     */
    fun getAssetStatements(): String {
        return """
            <?xml version="1.0" encoding="utf-8"?>
            <resources>
                <string name="asset_statements">[{
                    \"relation\": [\"delegate_permission/common.handle_all_urls\"],
                    \"target\": {\"namespace\": \"android_app\", \"package_name\": \"com.videoplay.browser\"}
                }]
                </string>
            </resources>
        """.trimIndent()
    }

    /**
     * Checks if the app is the default handler for a specific URL.
     * @param url The URL to check.
     * @return True if this app is the default handler, false otherwise.
     */
    fun isDefaultHandler(url: String): Boolean {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        val resolveInfo = context.packageManager.resolveActivity(intent, 0)
        return resolveInfo?.activityInfo?.packageName == context.packageName
    }

    /**
     * Opens a URL in VIDEOPlay Browser.
     * @param url The URL to open.
     */
    fun openInVideoPlay(url: String) {
        val intent = Intent(context, com.videoplay.browser.ui.MainActivity::class.java).apply {
            action = Intent.ACTION_VIEW
            data = Uri.parse(url)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }

    /**
     * Checks if the device supports digital asset links (Android 12+).
     * @return True if the device supports digital asset links, false otherwise.
     */
    fun supportsDigitalAssetLinks(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    }

    /**
     * Gets the package visibility configuration for Android 11+.
     * This should be added to the AndroidManifest.xml.
     */
    fun getPackageVisibilityConfig(): String {
        return """
            <queries>
                <package android:name="com.android.chrome" />
                <package android:name="org.mozilla.firefox" />
                <package android:name="com.opera.browser" />
                <package android:name="com.microsoft.emmx" />
            </queries>
        """.trimIndent()
    }

    /**
     * Gets a description of the current app links status.
     * @return A human-readable description.
     */
    fun getAppLinksStatus(): String {
        return if (verifyAppLinks()) {
            "VIDEOPlay Browser is set to handle web links"
        } else {
            "VIDEOPlay Browser is not set to handle web links"
        }
    }

    /**
     * Requests to set VIDEOPlay Browser as the default handler for web links.
     * On Android 11+, this will show a system dialog.
     */
    fun requestDefaultHandler() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val intent = Intent(
                "android.settings.MANAGE_DEFAULT_APPS_SETTINGS"
            ).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } else {
            // For older versions, just open the default apps settings
            val intent = Intent(
                android.provider.Settings.ACTION_MANAGE_DEFAULT_APPS_SETTINGS
            ).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        }
    }
}
