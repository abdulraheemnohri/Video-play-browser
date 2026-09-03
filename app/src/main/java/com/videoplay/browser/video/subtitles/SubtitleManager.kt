package com.videoplay.browser.video.subtitles

import android.graphics.Color
import java.util.Locale

/**
 * Manages subtitles for the video player.
 * Handles loading, displaying, and customizing subtitles.
 */
class SubtitleManager {

    var isEnabled: Boolean = true
    var language: String = Locale.getDefault().language
    var size: SubtitleSize = SubtitleSize.MEDIUM
    var style: SubtitleStyle = SubtitleStyle.DEFAULT
    var background: SubtitleBackground = SubtitleBackground.SEMI_TRANSPARENT
    var textColor: Int = Color.WHITE
    var backgroundColor: Int = Color.BLACK
    var textPosition: SubtitlePosition = SubtitlePosition.BOTTOM

    private var subtitles: List<Subtitle> = emptyList()
    private var currentSubtitleIndex: Int = -1
    private var currentTime: Long = 0L

    enum class SubtitleSize {
        SMALL,
        MEDIUM,
        LARGE,
        EXTRA_LARGE
    }

    enum class SubtitleStyle {
        DEFAULT,
        HIGH_CONTRAST,
        CUSTOM
    }

    enum class SubtitleBackground {
        NONE,
        SEMI_TRANSPARENT,
        SOLID
    }

    enum class SubtitlePosition {
        TOP,
        BOTTOM,
        CUSTOM
    }

    data class Subtitle(
        val startTime: Long,
        val endTime: Long,
        val text: String
    )

    fun loadSubtitles(subtitleList: List<Subtitle>) {
        subtitles = subtitleList.sortedBy { it.startTime }
        currentSubtitleIndex = -1
    }

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

            if (i + 1 < lines.size && lines[i + 1].contains("-->")) {
                val timeLine = lines[i + 1].trim()
                val times = timeLine.split("-->")
                if (times.size == 2) {
                    val startTime = parseTime(times[0].trim())
                    val endTime = parseTime(times[1].trim())

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

    fun updateTime(time: Long): String? {
        currentTime = time

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

    fun getCurrentSubtitle(): String? {
        return if (currentSubtitleIndex >= 0 && currentSubtitleIndex < subtitles.size) {
            subtitles[currentSubtitleIndex].text
        } else {
            null
        }
    }

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

    data class SubtitleDisplay(
        val text: String,
        val size: SubtitleSize,
        val style: SubtitleStyle,
        val background: SubtitleBackground,
        val textColor: Int,
        val backgroundColor: Int,
        val position: SubtitlePosition
    )

    fun setPosition(position: SubtitlePosition) {
        textPosition = position
    }

    fun clearSubtitles() {
        subtitles = emptyList()
        currentSubtitleIndex = -1
    }

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
