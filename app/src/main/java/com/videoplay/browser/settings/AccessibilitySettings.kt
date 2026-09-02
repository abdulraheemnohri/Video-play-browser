package com.videoplay.browser.settings

/**
 * Manages accessibility settings for the browser.
 */
class AccessibilitySettings {

    private var textSize: TextSize = TextSize.NORMAL
    private var reducedMotion: Boolean = false
    private var highContrast: Boolean = false
    private var colorInversion: Boolean = false
    private var screenReaderSupport: Boolean = false

    /**
     * Text size options.
     */
    enum class TextSize {
        SMALL,
        NORMAL,
        LARGE,
        EXTRA_LARGE
    }

    /**
     * Gets the current text size.
     */
    fun getTextSize(): TextSize {
        return textSize
    }

    /**
     * Sets the text size.
     * @param size The text size to set.
     */
    fun setTextSize(size: TextSize) {
        textSize = size
    }

    /**
     * Gets whether reduced motion is enabled.
     */
    fun isReducedMotionEnabled(): Boolean {
        return reducedMotion
    }

    /**
     * Sets whether reduced motion is enabled.
     * @param enabled Whether to enable reduced motion.
     */
    fun setReducedMotionEnabled(enabled: Boolean) {
        reducedMotion = enabled
    }

    /**
     * Gets whether high contrast mode is enabled.
     */
    fun isHighContrastEnabled(): Boolean {
        return highContrast
    }

    /**
     * Sets whether high contrast mode is enabled.
     * @param enabled Whether to enable high contrast mode.
     */
    fun setHighContrastEnabled(enabled: Boolean) {
        highContrast = enabled
    }

    /**
     * Gets whether color inversion is enabled.
     */
    fun isColorInversionEnabled(): Boolean {
        return colorInversion
    }

    /**
     * Sets whether color inversion is enabled.
     * @param enabled Whether to enable color inversion.
     */
    fun setColorInversionEnabled(enabled: Boolean) {
        colorInversion = enabled
    }

    /**
     * Gets whether screen reader support is enabled.
     */
    fun isScreenReaderSupportEnabled(): Boolean {
        return screenReaderSupport
    }

    /**
     * Sets whether screen reader support is enabled.
     * @param enabled Whether to enable screen reader support.
     */
    fun setScreenReaderSupportEnabled(enabled: Boolean) {
        screenReaderSupport = enabled
    }

    /**
     * Gets all text size options.
     */
    fun getTextSizeOptions(): List<Pair<TextSize, String>> {
        return listOf(
            Pair(TextSize.SMALL, "Small"),
            Pair(TextSize.NORMAL, "Normal"),
            Pair(TextSize.LARGE, "Large"),
            Pair(TextSize.EXTRA_LARGE, "Extra Large")
        )
    }

    /**
     * Gets the current text size as a scale factor.
     */
    fun getTextSizeScale(): Float {
        return when (textSize) {
            TextSize.SMALL -> 0.8f
            TextSize.NORMAL -> 1.0f
            TextSize.LARGE -> 1.2f
            TextSize.EXTRA_LARGE -> 1.5f
        }
    }

    /**
     * Resets accessibility settings to default.
     */
    fun resetToDefaults() {
        textSize = TextSize.NORMAL
        reducedMotion = false
        highContrast = false
        colorInversion = false
        screenReaderSupport = false
    }
}
