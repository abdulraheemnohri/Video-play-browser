package com.videoplay.browser.core.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Repository for managing app settings using DataStore.
 */
class SettingsRepository(private val dataStore: DataStore<Preferences>) {

    // General Settings
    private val HOMEPAGE_KEY = stringPreferencesKey("homepage")
    private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
    private val APP_LANGUAGE_KEY = stringPreferencesKey("app_language")

    // Browser Settings
    private val SEARCH_ENGINE_KEY = stringPreferencesKey("search_engine")
    private val BLOCK_POPUPS_KEY = booleanPreferencesKey("block_popups")
    private val ENABLE_JAVASCRIPT_KEY = booleanPreferencesKey("enable_javascript")

    // Video Settings
    private val AUTO_PLAY_KEY = stringPreferencesKey("auto_play") // "always", "wifi_only", "never"
    private val DEFAULT_PLAYBACK_SPEED_KEY = floatPreferencesKey("default_playback_speed")
    private val REMEMBER_PLAYBACK_SPEED_KEY = booleanPreferencesKey("remember_playback_speed")
    private val REMEMBER_PLAYBACK_POSITION_KEY = booleanPreferencesKey("remember_playback_position")
    private val ENABLE_PIP_KEY = booleanPreferencesKey("enable_pip")
    private val ENABLE_MINI_PLAYER_KEY = booleanPreferencesKey("enable_mini_player")

    // Privacy Settings
    private val TRACKING_PROTECTION_KEY = stringPreferencesKey("tracking_protection") // "standard", "strict", "custom"
    private val HTTPS_ONLY_KEY = booleanPreferencesKey("https_only")
    private val CLEAR_DATA_ON_EXIT_KEY = booleanPreferencesKey("clear_data_on_exit")

    // Download Settings
    private val DOWNLOAD_LOCATION_KEY = stringPreferencesKey("download_location")
    private val WI_FI_ONLY_DOWNLOADS_KEY = booleanPreferencesKey("wifi_only_downloads")
    private val ASK_BEFORE_DOWNLOAD_KEY = booleanPreferencesKey("ask_before_download")

    // Accessibility Settings
    private val REDUCED_MOTION_KEY = booleanPreferencesKey("reduced_motion")
    private val LARGE_TEXT_KEY = booleanPreferencesKey("large_text")

    // Getters for General Settings
    val homepage: Flow<String> = dataStore.data.map { preferences ->
        preferences[HOMEPAGE_KEY] ?: "https://www.google.com"
    }

    val darkMode: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[DARK_MODE_KEY] ?: false
    }

    val appLanguage: Flow<String> = dataStore.data.map { preferences ->
        preferences[APP_LANGUAGE_KEY] ?: "en"
    }

    // Getters for Browser Settings
    val searchEngine: Flow<String> = dataStore.data.map { preferences ->
        preferences[SEARCH_ENGINE_KEY] ?: "google"
    }

    val blockPopups: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[BLOCK_POPUPS_KEY] ?: true
    }

    val enableJavaScript: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[ENABLE_JAVASCRIPT_KEY] ?: true
    }

    // Getters for Video Settings
    val autoPlay: Flow<String> = dataStore.data.map { preferences ->
        preferences[AUTO_PLAY_KEY] ?: "wifi_only"
    }

    val defaultPlaybackSpeed: Flow<Float> = dataStore.data.map { preferences ->
        preferences[DEFAULT_PLAYBACK_SPEED_KEY] ?: 1.0f
    }

    val rememberPlaybackSpeed: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[REMEMBER_PLAYBACK_SPEED_KEY] ?: true
    }

    val rememberPlaybackPosition: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[REMEMBER_PLAYBACK_POSITION_KEY] ?: true
    }

    val enablePiP: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[ENABLE_PIP_KEY] ?: true
    }

    val enableMiniPlayer: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[ENABLE_MINI_PLAYER_KEY] ?: true
    }

    // Getters for Privacy Settings
    val trackingProtection: Flow<String> = dataStore.data.map { preferences ->
        preferences[TRACKING_PROTECTION_KEY] ?: "standard"
    }

    val httpsOnly: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[HTTPS_ONLY_KEY] ?: true
    }

    val clearDataOnExit: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[CLEAR_DATA_ON_EXIT_KEY] ?: false
    }

    // Getters for Download Settings
    val downloadLocation: Flow<String> = dataStore.data.map { preferences ->
        preferences[DOWNLOAD_LOCATION_KEY] ?: "Downloads/VideoPlay"
    }

    val wifiOnlyDownloads: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[WI_FI_ONLY_DOWNLOADS_KEY] ?: false
    }

    val askBeforeDownload: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[ASK_BEFORE_DOWNLOAD_KEY] ?: true
    }

    // Getters for Accessibility Settings
    val reducedMotion: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[REDUCED_MOTION_KEY] ?: false
    }

    val largeText: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[LARGE_TEXT_KEY] ?: false
    }

    // Setters for General Settings
    suspend fun setHomepage(url: String) {
        dataStore.edit { preferences ->
            preferences[HOMEPAGE_KEY] = url
        }
    }

    suspend fun setDarkMode(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[DARK_MODE_KEY] = enabled
        }
    }

    suspend fun setAppLanguage(language: String) {
        dataStore.edit { preferences ->
            preferences[APP_LANGUAGE_KEY] = language
        }
    }

    // Setters for Browser Settings
    suspend fun setSearchEngine(engine: String) {
        dataStore.edit { preferences ->
            preferences[SEARCH_ENGINE_KEY] = engine
        }
    }

    suspend fun setBlockPopups(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[BLOCK_POPUPS_KEY] = enabled
        }
    }

    suspend fun setEnableJavaScript(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[ENABLE_JAVASCRIPT_KEY] = enabled
        }
    }

    // Setters for Video Settings
    suspend fun setAutoPlay(mode: String) {
        dataStore.edit { preferences ->
            preferences[AUTO_PLAY_KEY] = mode
        }
    }

    suspend fun setDefaultPlaybackSpeed(speed: Float) {
        dataStore.edit { preferences ->
            preferences[DEFAULT_PLAYBACK_SPEED_KEY] = speed
        }
    }

    suspend fun setRememberPlaybackSpeed(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[REMEMBER_PLAYBACK_SPEED_KEY] = enabled
        }
    }

    suspend fun setRememberPlaybackPosition(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[REMEMBER_PLAYBACK_POSITION_KEY] = enabled
        }
    }

    suspend fun setEnablePiP(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[ENABLE_PIP_KEY] = enabled
        }
    }

    suspend fun setEnableMiniPlayer(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[ENABLE_MINI_PLAYER_KEY] = enabled
        }
    }

    // Setters for Privacy Settings
    suspend fun setTrackingProtection(mode: String) {
        dataStore.edit { preferences ->
            preferences[TRACKING_PROTECTION_KEY] = mode
        }
    }

    suspend fun setHttpsOnly(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[HTTPS_ONLY_KEY] = enabled
        }
    }

    suspend fun setClearDataOnExit(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[CLEAR_DATA_ON_EXIT_KEY] = enabled
        }
    }

    // Setters for Download Settings
    suspend fun setDownloadLocation(path: String) {
        dataStore.edit { preferences ->
            preferences[DOWNLOAD_LOCATION_KEY] = path
        }
    }

    suspend fun setWifiOnlyDownloads(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[WI_FI_ONLY_DOWNLOADS_KEY] = enabled
        }
    }

    suspend fun setAskBeforeDownload(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[ASK_BEFORE_DOWNLOAD_KEY] = enabled
        }
    }

    // Setters for Accessibility Settings
    suspend fun setReducedMotion(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[REDUCED_MOTION_KEY] = enabled
        }
    }

    suspend fun setLargeText(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[LARGE_TEXT_KEY] = enabled
        }
    }
}
