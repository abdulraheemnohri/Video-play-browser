package com.videoplay.browser.privacy

import org.mozilla.geckoview.GeckoSession

/**
 * Manages site-specific permissions for the browser.
 * Handles permissions for camera, microphone, location, notifications, etc.
 */
class SitePermissionsManager {

    /**
     * Permission types that can be managed per site.
     */
    enum class PermissionType {
        CAMERA,
        MICROPHONE,
        LOCATION,
        NOTIFICATIONS,
        AUTO_PLAY,
        FULLSCREEN,
        DOWNLOADS
    }

    /**
     * Permission states.
     */
    enum class PermissionState {
        ALLOW,
        ASK,
        BLOCK
    }

    // Map to store permissions for each site
    // Format: "site_url" -> Map<PermissionType, PermissionState>
    private val sitePermissions: MutableMap<String, MutableMap<PermissionType, PermissionState>> = mutableMapOf()

    /**
     * Sets the permission state for a specific site and permission type.
     * @param siteUrl The URL of the site.
     * @param permissionType The type of permission.
     * @param state The permission state to set.
     */
    fun setPermission(siteUrl: String, permissionType: PermissionType, state: PermissionState) {
        val permissions = sitePermissions.getOrPut(siteUrl) { mutableMapOf() }
        permissions[permissionType] = state
    }

    /**
     * Gets the permission state for a specific site and permission type.
     * @param siteUrl The URL of the site.
     * @param permissionType The type of permission.
     * @return The permission state, or ASK if not set.
     */
    fun getPermission(siteUrl: String, permissionType: PermissionType): PermissionState {
        return sitePermissions[siteUrl]?.get(permissionType) ?: PermissionState.ASK
    }

    /**
     * Gets all permissions for a specific site.
     * @param siteUrl The URL of the site.
     * @return Map of permission types to their states.
     */
    fun getSitePermissions(siteUrl: String): Map<PermissionType, PermissionState> {
        return sitePermissions[siteUrl] ?: emptyMap()
    }

    /**
     * Clears all permissions for a specific site.
     * @param siteUrl The URL of the site.
     */
    fun clearSitePermissions(siteUrl: String) {
        sitePermissions.remove(siteUrl)
    }

    /**
     * Clears all permissions for all sites.
     */
    fun clearAllPermissions() {
        sitePermissions.clear()
    }

    /**
     * Checks if a site has any custom permissions set.
     * @param siteUrl The URL of the site.
     * @return True if the site has custom permissions, false otherwise.
     */
    fun hasCustomPermissions(siteUrl: String): Boolean {
        return sitePermissions.containsKey(siteUrl)
    }

    /**
     * Applies the permission settings to a GeckoSession for a specific site.
     * @param session The GeckoSession to apply permissions to.
     * @param siteUrl The URL of the site.
     */
    fun applyPermissionsToSession(session: GeckoSession, siteUrl: String) {
        // Get the domain from the URL
        val domain = getDomain(siteUrl)

        // Apply camera permission
        when (getPermission(domain, PermissionType.CAMERA)) {
            PermissionState.ALLOW -> session.setCameraPermission(GeckoSession.Permission.ALLOW)
            PermissionState.BLOCK -> session.setCameraPermission(GeckoSession.Permission.DENY)
            PermissionState.ASK -> session.setCameraPermission(GeckoSession.Permission.PROMPT)
        }

        // Apply microphone permission
        when (getPermission(domain, PermissionType.MICROPHONE)) {
            PermissionState.ALLOW -> session.setMicrophonePermission(GeckoSession.Permission.ALLOW)
            PermissionState.BLOCK -> session.setMicrophonePermission(GeckoSession.Permission.DENY)
            PermissionState.ASK -> session.setMicrophonePermission(GeckoSession.Permission.PROMPT)
        }

        // Apply location permission
        when (getPermission(domain, PermissionType.LOCATION)) {
            PermissionState.ALLOW -> session.setLocationPermission(GeckoSession.Permission.ALLOW)
            PermissionState.BLOCK -> session.setLocationPermission(GeckoSession.Permission.DENY)
            PermissionState.ASK -> session.setLocationPermission(GeckoSession.Permission.PROMPT)
        }

        // Apply autoplay permission
        when (getPermission(domain, PermissionType.AUTO_PLAY)) {
            PermissionState.ALLOW -> session.setAutoplayPermission(GeckoSession.Permission.ALLOW)
            PermissionState.BLOCK -> session.setAutoplayPermission(GeckoSession.Permission.DENY)
            PermissionState.ASK -> session.setAutoplayPermission(GeckoSession.Permission.PROMPT)
        }

        // Apply fullscreen permission
        when (getPermission(domain, PermissionType.FULLSCREEN)) {
            PermissionState.ALLOW -> session.setFullscreenPermission(GeckoSession.Permission.ALLOW)
            PermissionState.BLOCK -> session.setFullscreenPermission(GeckoSession.Permission.DENY)
            PermissionState.ASK -> session.setFullscreenPermission(GeckoSession.Permission.PROMPT)
        }
    }

    /**
     * Extracts the domain from a URL.
     * @param url The URL to extract the domain from.
     * @return The domain, or the original URL if extraction fails.
     */
    private fun getDomain(url: String): String {
        return try {
            val uri = android.net.Uri.parse(url)
            uri.host ?: url
        } catch (e: Exception) {
            url
        }
    }

    /**
     * Gets the default permission state for a permission type.
     * @param permissionType The type of permission.
     * @return The default permission state.
     */
    fun getDefaultPermission(permissionType: PermissionType): PermissionState {
        return when (permissionType) {
            PermissionType.CAMERA -> PermissionState.ASK
            PermissionType.MICROPHONE -> PermissionState.ASK
            PermissionType.LOCATION -> PermissionState.ASK
            PermissionType.NOTIFICATIONS -> PermissionState.ASK
            PermissionType.AUTO_PLAY -> PermissionState.BLOCK
            PermissionType.FULLSCREEN -> PermissionState.ALLOW
            PermissionType.DOWNLOADS -> PermissionState.ALLOW
        }
    }

    /**
     * Resets all permissions to their default values.
     */
    fun resetToDefaults() {
        sitePermissions.clear()
    }

    /**
     * Gets all sites that have custom permissions.
     * @return List of site URLs with custom permissions.
     */
    fun getSitesWithCustomPermissions(): List<String> {
        return sitePermissions.keys.toList()
    }

    /**
     * Gets the permission types for display.
     * @return List of permission types with their display names.
     */
    fun getPermissionTypesForDisplay(): List<Pair<PermissionType, String>> {
        return listOf(
            Pair(PermissionType.CAMERA, "Camera"),
            Pair(PermissionType.MICROPHONE, "Microphone"),
            Pair(PermissionType.LOCATION, "Location"),
            Pair(PermissionType.NOTIFICATIONS, "Notifications"),
            Pair(PermissionType.AUTO_PLAY, "Autoplay"),
            Pair(PermissionType.FULLSCREEN, "Fullscreen"),
            Pair(PermissionType.DOWNLOADS, "Downloads")
        )
    }

    /**
     * Gets the permission states for display.
     * @return List of permission states with their display names.
     */
    fun getPermissionStatesForDisplay(): List<Pair<PermissionState, String>> {
        return listOf(
            Pair(PermissionState.ALLOW, "Allow"),
            Pair(PermissionState.ASK, "Ask"),
            Pair(PermissionState.BLOCK, "Block")
        )
    }
}
