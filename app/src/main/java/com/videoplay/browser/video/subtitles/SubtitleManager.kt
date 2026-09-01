package com.videoplay.browser.video.subtitles

import android.graphics.Color
import android.text.TextUtils
import java.util.Locale

/**
 * Manages subtitles for the video player.
 * Handles loading, displaying, and customizing subtitles.
 */
class SubtitleManager {

    // Subtitle settings
    var isEnabled: Boolean = true
    var language: String = Locale.getDefault().language
    var size: SubtitleSize = SubtitleSize.MEDIUM
    var style: SubtitleStyle = SubtitleStyle.DEFAULT
    var background: SubtitleBackground = SubtitleBackground.SEMI_TRANSPARENT
    var textColor: Int = Color.WHITE
    var backgroundColor: Int = Color.BLACK
    var textPosition: SubtitlePosition = SubtitlePosition.BOTTOM

    // Subtitle data
    private var subtitles: List<Subtitle> = emptyList()
    private var currentSubtitleIndex: Int = -1
    private var currentTime: Long = 0L

    /**
     * Subtitle size options.
     */
    enum class SubtitleSize {
        SMALL,
        MEDIUM,
        LARGE,
        EXTRA_LARGE
    }

    /**
     * Subtitle style options.
     */
    enum class SubtitleStyle {
        DEFAULT,
        HIGH_CONTRAST,
        CUSTOM
    }

    /**
     * Subtitle background options.
     */
    enum class SubtitleBackground {
        NONE,
        SEMI_TRANSPARENT,
        SOLID
    }

    /**
     * Subtitle position options.
     */
    enum class SubtitlePosition {
        TOP,
        BOTTOM,
        CUSTOM
    }

    /**
     * Represents a single subtitle.
     */
    data class Subtitle(
        val startTime: Long, // in milliseconds
        val endTime: Long,   // in milliseconds
        val text: String
    )

    /**
     * Loads subtitles from a list of subtitle entries.
     * @param subtitleList List of subtitles to load.
     */
    fun loadSubtitles(subtitleList: List<Subtitle>) {
        subtitles = subtitleList.sortedBy { it.startTime }
        currentSubtitleIndex = -1
    }

    /**
     * Loads subtitles from a WebVTT file content.
     * @param webVttContent The content of the WebVTT file.
     */
    fun loadFromWebVTT(webVttContent: String) {
        val subtitleList = mutableListOf<Subtitle>()
        val lines = webVttContent.split("\n")
        var i = 0

        while (i < lines.size) {
            val line = lines[i].trim()
            if (line.isEmpty() || line.startsWith("WEBVTT") || line.startsWith("-->")) {
                i++
                continue
            }

            // Parse time range
            if (i + 1 < lines.size && lines[i + 1].contains("-->")) {
                val timeLine = lines[i + 1].trim()
                val times = timeLine.split("-->")
                if (times.size == 2) {
                    val startTime = parseTime(times[0].trim())
                    val endTime = parseTime(times[1].trim())

                    // Parse subtitle text
                    val textBuilder = StringBuilder()
                    i += 2
                    while (i < lines.size && !lines[i].trim().isEmpty()) {
                        if (textBuilder.isNotEmpty()) {
                            textBuilder.append("\n")
                        }
                        textBuilder.append(lines[i].trim())
                        i++
                    }

                    subtitleList.add(Subtitle(startTime, endTime, textBuilder.toString()))
                }
            }
            i++
        }

        loadSubtitles(subtitleList)
    }

    /**
     * Parses a time string in the format "HH:MM:SS.mmm" to milliseconds.
     */
    private fun parseTime(timeStr: String): Long {
        val parts = timeStr.split(":")
        if (parts.size != 3) return 0L

        val hours = parts[0].toLongOrNull() ?: 0L
        val minutes = parts[1].toLongOrNull() ?: 0L
        val secondsParts = parts[2].split(".")
        val seconds = secondsParts[0].toLongOrNull() ?: 0L
        val milliseconds = if (secondsParts.size > 1) {
            secondsParts[1].toLongOrNull() ?: 0L
        } else {
            0L
        }

        return hours * 3600000 + minutes * 60000 + seconds * 1000 + milliseconds
    }

    /**
     * Updates the current playback time and returns the current subtitle (if any).
     * @param time The current playback time in milliseconds.
     * @return The current subtitle text, or null if no subtitle is active.
     */
    fun updateTime(time: Long): String? {
        currentTime = time

        // Find the current subtitle
        for (i in subtitles.indices) {
            val subtitle = subtitles[i]
            if (time >= subtitle.startTime && time < subtitle.endTime) {
                if (i != currentSubtitleIndex) {
                    currentSubtitleIndex = i
                }
                return subtitle.text
            }
        }

        currentSubtitleIndex = -1
        return null
    }

    /**
     * Gets the current subtitle text.
     */
    fun getCurrentSubtitle(): String? {
        return if (currentSubtitleIndex >= 0 && currentSubtitleIndex < subtitles.size) {
            subtitles[currentSubtitleIndex].text
        } else {
            null
        }
    }

    /**
     * Gets the formatted current subtitle with styling.
     */
    fun getFormattedSubtitle(): SubtitleDisplay? {
        if (!isEnabled || currentSubtitleIndex < 0) return null

        val subtitle = subtitles[currentSubtitleIndex]
        return SubtitleDisplay(
            text = subtitle.text,
            size = size,
            style = style,
            background = background,
            textColor = textColor,
            backgroundColor = backgroundColor,
            position = textPosition
        )
    }

    /**
     * Represents a subtitle with display properties.
     */
    data class SubtitleDisplay(
        val text: String,
        val size: SubtitleSize,
        val style: SubtitleStyle,
        val background: SubtitleBackground,
        val textColor: Int,
        val backgroundColor: Int,
        val position: SubtitlePosition
    )

    /**
     * Sets the subtitle language.
     * @param languageCode The language code (e.g., "en", "ur", "es").
     */
    fun setLanguage(languageCode: String) {
        language = languageCode
    }

    /**
     * Sets the subtitle size.
     * @param size The subtitle size.
     */
    fun setSize(size: SubtitleSize) {
        this.size = size
    }

    /**
     * Sets the subtitle style.
     * @param style The subtitle style.
     */
    fun setStyle(style: SubtitleStyle) {
        this.style = style
    }

    /**
     * Sets the subtitle background.
     * @param background The subtitle background.
     */
    fun setBackground(background: SubtitleBackground) {
        this.background = background
    }

    /**
     * Sets the subtitle text color.
     * @param color The text color.
     */
    fun setTextColor(color: Int) {
        textColor = color
    }

    /**
     * Sets the subtitle background color.
     * @param color The background color.
     */
    fun setBackgroundColor(color: Int) {
        backgroundColor = color
    }

    /**
     * Sets the subtitle position.
     * @param position The subtitle position.
     */
    fun setPosition(position: SubtitlePosition) {
        textPosition = position
    }

    /**
     * Clears all loaded subtitles.
     */
    fun clearSubtitles() {
        subtitles = emptyList()
        currentSubtitleIndex = -1
    }

    /**
     * Resets all subtitle settings to default.
     */
    fun resetToDefaults() {
        isEnabled = true
        language = Locale.getDefault().language
        size = SubtitleSize.MEDIUM
        style = SubtitleStyle.DEFAULT
        background = SubtitleBackground.SEMI_TRANSPARENT
        textColor = Color.WHITE
        backgroundColor = Color.BLACK
        textPosition = SubtitlePosition.BOTTOM
    }
}
