package com.videoplay.browser.settings

import androidx.compose.ui.graphics.toArgb
import com.videoplay.browser.ui.theme.Color

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
    private var primaryColor: Int = Color.Primary.toArgb()

    /**
     * Gets the current theme.
     */
    fun getCurrentTheme(): Theme {
        return currentTheme
    }

    /**
     * Sets the current theme.
     * @param theme The theme to set.
     */
    fun setCurrentTheme(theme: Theme) {
        currentTheme = theme
    }

    /**
     * Gets whether dynamic colors are enabled.
     */
    fun isDynamicColorEnabled(): Boolean {
        return dynamicColorEnabled
    }

    /**
     * Sets whether dynamic colors are enabled.
     * @param enabled Whether to enable dynamic colors.
     */
    fun setDynamicColorEnabled(enabled: Boolean) {
        dynamicColorEnabled = enabled
    }

    /**
     * Gets the primary color.
     */
    fun getPrimaryColor(): Int {
        return primaryColor
    }

    /**
     * Sets the primary color.
     * @param color The primary color to set.
     */
    fun setPrimaryColor(color: Int) {
        primaryColor = color
    }

    /**
     * Gets all available themes.
     */
    fun getAllThemes(): List<Pair<Theme, String>> {
        return listOf(
            Pair(Theme.SYSTEM, "System"),
            Pair(Theme.LIGHT, "Light"),
            Pair(Theme.DARK, "Dark"),
            Pair(Theme.AMOLED, "AMOLED")
        )
    }

    /**
     * Gets the display name of the current theme.
     */
    fun getCurrentThemeName(): String {
        return when (currentTheme) {
            Theme.SYSTEM -> "System"
            Theme.LIGHT -> "Light"
            Theme.DARK -> "Dark"
            Theme.AMOLED -> "AMOLED"
        }
    }

    /**
     * Resets appearance settings to default.
     */
    fun resetToDefaults() {
        currentTheme = Theme.SYSTEM
        dynamicColorEnabled = true
        primaryColor = Color.Primary.toArgb()
    }
}
