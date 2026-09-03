package com.videoplay.browser.settings

/**
 * Manages appearance settings for the browser.
 */
class AppearanceSettings {

    /**
     * Theme options.
     */
    enum class Theme {
        SYSTEM,
        LIGHT,
        DARK,
        AMOLED
    }

    /**
     * Dynamic color options.
     */
    enum class DynamicColor {
        ENABLED,
        DISABLED
    }

    private var currentTheme: Theme = Theme.SYSTEM
    private var dynamicColorEnabled: Boolean = true
    private var primaryColor: Int = 0xFF6200EE.toInt()

    fun getCurrentTheme(): Theme = currentTheme

    fun setCurrentTheme(theme: Theme) {
        currentTheme = theme
    }

    fun isDynamicColorEnabled(): Boolean = dynamicColorEnabled

    fun setDynamicColorEnabled(enabled: Boolean) {
        dynamicColorEnabled = enabled
    }

    fun getPrimaryColor(): Int = primaryColor

    fun setPrimaryColor(color: Int) {
        primaryColor = color
    }

    fun getAllThemes(): List<Pair<Theme, String>> {
        return listOf(
            Pair(Theme.SYSTEM, "System"),
            Pair(Theme.LIGHT, "Light"),
            Pair(Theme.DARK, "Dark"),
            Pair(Theme.AMOLED, "AMOLED")
        )
    }

    fun getCurrentThemeName(): String {
        return when (currentTheme) {
            Theme.SYSTEM -> "System"
            Theme.LIGHT -> "Light"
            Theme.DARK -> "Dark"
            Theme.AMOLED -> "AMOLED"
        }
    }

    fun resetToDefaults() {
        currentTheme = Theme.SYSTEM
        dynamicColorEnabled = true
        primaryColor = 0xFF6200EE.toInt()
    }
}
