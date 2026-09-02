package com.videoplay.browser.settings

import android.os.Environment

/**
 * Manages download-related settings for the browser.
 */
class DownloadSettings {

    private var downloadLocation: String = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path + "/VideoPlay"
    private var wifiOnlyDownloads: Boolean = false
    private var askBeforeDownload: Boolean = true
    private var autoOpenDownloadedFiles: Boolean = false

    /**
     * Gets the download location.
     */
    fun getDownloadLocation(): String {
        return downloadLocation
    }

    /**
     * Sets the download location.
     * @param path The download location path.
     */
    fun setDownloadLocation(path: String) {
        downloadLocation = path
    }

    /**
     * Gets whether to download only on Wi-Fi.
     */
    fun isWifiOnlyDownloads(): Boolean {
        return wifiOnlyDownloads
    }

    /**
     * Sets whether to download only on Wi-Fi.
     * @param wifiOnly Whether to download only on Wi-Fi.
     */
    fun setWifiOnlyDownloads(wifiOnly: Boolean) {
        wifiOnlyDownloads = wifiOnly
    }

    /**
     * Gets whether to ask before downloading.
     */
    fun shouldAskBeforeDownload(): Boolean {
        return askBeforeDownload
    }

    /**
     * Sets whether to ask before downloading.
     * @param ask Whether to ask before downloading.
     */
    fun setAskBeforeDownload(ask: Boolean) {
        askBeforeDownload = ask
    }

    /**
     * Gets whether to auto-open downloaded files.
     */
    fun shouldAutoOpenDownloadedFiles(): Boolean {
        return autoOpenDownloadedFiles
    }

    /**
     * Sets whether to auto-open downloaded files.
     * @param autoOpen Whether to auto-open downloaded files.
     */
    fun setAutoOpenDownloadedFiles(autoOpen: Boolean) {
        autoOpenDownloadedFiles = autoOpen
    }

    /**
     * Resets download settings to default.
     */
    fun resetToDefaults() {
        downloadLocation = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path + "/VideoPlay"
        wifiOnlyDownloads = false
        askBeforeDownload = true
        autoOpenDownloadedFiles = false
    }

    /**
     * Gets all download settings for display.
     */
    fun getAllSettings(): List<Pair<String, String>> {
        return listOf(
            Pair("Download Location", downloadLocation),
            Pair("Wi-Fi Only Downloads", if (wifiOnlyDownloads) "Yes" else "No"),
            Pair("Ask Before Download", if (askBeforeDownload) "Yes" else "No"),
            Pair("Auto-Open Downloaded Files", if (autoOpenDownloadedFiles) "Yes" else "No")
        )
    }
}
