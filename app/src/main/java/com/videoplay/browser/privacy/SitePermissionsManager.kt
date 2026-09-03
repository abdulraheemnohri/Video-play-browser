package com.videoplay.browser.privacy

import org.mozilla.geckoview.GeckoSession

/**
 * Manages site-specific permissions for the browser.
 * Handles permissions for camera, microphone, location, notifications, etc.
 */
class SitePermissionsManager {

    enum class PermissionType {
        CAMERA,
        MICROPHONE,
        LOCATION,
        NOTIFICATIONS,
        AUTO_PLAY,
        FULLSCREEN,
        DOWNLOADS
    }

    enum class PermissionState {
        ALLOW,
        ASK,
        BLOCK
    }

    private val sitePermissions: MutableMap<String, MutableMap<PermissionType, PermissionState>> = mutableMapOf()

    fun setPermission(siteUrl: String, permissionType: PermissionType, state: PermissionState) {
        val permissions = sitePermissions.getOrPut(siteUrl) { mutableMapOf() }
        permissions[permissionType] = state
    }

    fun getPermission(siteUrl: String, permissionType: PermissionType): PermissionState {
        return sitePermissions[siteUrl]?.get(permissionType) ?: PermissionState.ASK
    }

    fun getSitePermissions(siteUrl: String): Map<PermissionType, PermissionState> {
        return sitePermissions[siteUrl] ?: emptyMap()
    }

    fun clearSitePermissions(siteUrl: String) {
        sitePermissions.remove(siteUrl)
    }

    fun clearAllPermissions() {
        sitePermissions.clear()
    }

    fun hasCustomPermissions(siteUrl: String): Boolean {
        return sitePermissions.containsKey(siteUrl)
    }

    fun applyPermissionsToSession(session: GeckoSession, siteUrl: String) {
        // Site permissions are managed dynamically via Session Delegate
    }

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

    fun resetToDefaults() {
        sitePermissions.clear()
    }

    fun getSitesWithCustomPermissions(): List<String> {
        return sitePermissions.keys.toList()
    }

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

    fun getPermissionStatesForDisplay(): List<Pair<PermissionState, String>> {
        return listOf(
            Pair(PermissionState.ALLOW, "Allow"),
            Pair(PermissionState.ASK, "Ask"),
            Pair(PermissionState.BLOCK, "Block")
        )
    }
}
