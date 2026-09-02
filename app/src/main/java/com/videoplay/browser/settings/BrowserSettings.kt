package com.videoplay.browser.settings

/**
 * Manages general browser settings.
 */
class BrowserSettings {

    private var homepage: String = "https://www.google.com"
    private var isDefaultBrowser: Boolean = false
    private var userAgent: String = "Mozilla/5.0 (Android 10; Mobile) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4896.127 Mobile Safari/537.36"
    private var openLinksInNewTab: Boolean = true
    private var showHomeButton: Boolean = true

    /**
     * Gets the homepage URL.
     */
    fun getHomepage(): String {
        return homepage
    }

    /**
     * Sets the homepage URL.
     * @param url The homepage URL to set.
     */
    fun setHomepage(url: String) {
        homepage = if (url.startsWith("http://") || url.startsWith("https://")) {
            url
        } else {
            "https://$url"
        }
    }

    /**
     * Gets whether this browser is set as the default browser.
     */
    fun isDefaultBrowser(): Boolean {
        return isDefaultBrowser
    }

    /**
     * Sets whether this browser is the default browser.
     * Note: This would typically be handled by the system, not the app.
     * @param isDefault Whether this browser is the default.
     */
    fun setDefaultBrowser(isDefault: Boolean) {
        isDefaultBrowser = isDefault
    }

    /**
     * Gets the user agent string.
     */
    fun getUserAgent(): String {
        return userAgent
    }

    /**
     * Sets the user agent string.
     * @param userAgent The user agent string to set.
     */
    fun setUserAgent(userAgent: String) {
        this.userAgent = userAgent
    }

    /**
     * Gets whether to open links in new tabs.
     */
    fun shouldOpenLinksInNewTab(): Boolean {
        return openLinksInNewTab
    }

    /**
     * Sets whether to open links in new tabs.
     * @param openInNewTab Whether to open links in new tabs.
     */
    fun setOpenLinksInNewTab(openInNewTab: Boolean) {
        openLinksInNewTab = openInNewTab
    }

    /**
     * Gets whether to show the home button.
     */
    fun shouldShowHomeButton(): Boolean {
        return showHomeButton
    }

    /**
     * Sets whether to show the home button.
     * @param show Whether to show the home button.
     */
    fun setShowHomeButton(show: Boolean) {
        showHomeButton = show
    }

    /**
     * Resets browser settings to default.
     */
    fun resetToDefaults() {
        homepage = "https://www.google.com"
        isDefaultBrowser = false
        userAgent = "Mozilla/5.0 (Android 10; Mobile) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4896.127 Mobile Safari/537.36"
        openLinksInNewTab = true
        showHomeButton = true
    }

    /**
     * Gets all browser settings for display.
     */
    fun getAllSettings(): List<Pair<String, String>> {
        return listOf(
            Pair("Homepage", homepage),
            Pair("Default Browser", if (isDefaultBrowser) "Yes" else "No"),
            Pair("Open Links in New Tab", if (openLinksInNewTab) "Yes" else "No"),
            Pair("Show Home Button", if (showHomeButton) "Yes" else "No")
        )
    }
}
