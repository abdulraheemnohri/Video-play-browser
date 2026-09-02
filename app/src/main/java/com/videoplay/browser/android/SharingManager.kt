package com.videoplay.browser.android

import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 * Manages sharing functionality for the browser.
 * Allows sharing pages, text, images, and downloads.
 */
class SharingManager(private val context: Context) {

    /**
     * Shares a URL (web page).
     * @param url The URL to share.
     * @param title The title of the page (optional).
     */
    fun shareUrl(url: String, title: String? = null) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, url)
            if (title != null) {
                putExtra(Intent.EXTRA_SUBJECT, title)
            }
        }
        context.startActivity(Intent.createChooser(shareIntent, "Share Page"))
    }

    /**
     * Shares text content.
     * @param text The text to share.
     * @param title The title for the chooser dialog.
     */
    fun shareText(text: String, title: String = "Share Text") {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }
        context.startActivity(Intent.createChooser(shareIntent, title))
    }

    /**
     * Shares an image file.
     * @param imageUri The URI of the image to share.
     */
    fun shareImage(imageUri: Uri) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_STREAM, imageUri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(shareIntent, "Share Image"))
    }

    /**
     * Shares a file (download).
     * @param fileUri The URI of the file to share.
     * @param mimeType The MIME type of the file.
     */
    fun shareFile(fileUri: Uri, mimeType: String? = null) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            if (mimeType != null) {
                type = mimeType
            } else {
                type = "*/*"
            }
            putExtra(Intent.EXTRA_STREAM, fileUri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(shareIntent, "Share File"))
    }

    /**
     * Shares multiple files.
     * @param fileUris List of URIs of the files to share.
     */
    fun shareMultipleFiles(fileUris: List<Uri>) {
        val shareIntent = Intent(Intent.ACTION_SEND_MULTIPLE).apply {
            type = "*/*"
            putParcelableArrayListExtra(Intent.EXTRA_STREAM, ArrayList(fileUris))
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(shareIntent, "Share Files"))
    }

    /**
     * Opens a URL in another app.
     * @param url The URL to open.
     */
    fun openInOtherApp(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(Intent.createChooser(intent, "Open in Another App"))
    }

    /**
     * Copies text to clipboard.
     * @param text The text to copy.
     * @param label The label for the clipboard item.
     */
    fun copyToClipboard(text: String, label: String = "Copied from VIDEOPlay") {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
        val clip = android.content.ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)
    }

    /**
     * Gets text from clipboard.
     * @return The text from clipboard, or null if empty.
     */
    fun getFromClipboard(): String? {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
        return if (clipboard.hasPrimaryClip() && clipboard.primaryClip?.itemCount ?: 0 > 0) {
            clipboard.primaryClip?.getItemAt(0)?.text?.toString()
        } else {
            null
        }
    }

    /**
     * Checks if there is text in the clipboard.
     * @return True if there is text in the clipboard, false otherwise.
     */
    fun hasTextInClipboard(): Boolean {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
        return clipboard.hasPrimaryClip() && 
               clipboard.primaryClip?.itemCount ?: 0 > 0 &&
               clipboard.primaryClip?.getItemAt(0)?.text != null
    }

    /**
     * Shares the current page info.
     * @param url The URL of the page.
     * @param title The title of the page.
     */
    fun sharePage(url: String, title: String) {
        val shareText = "$title\n$url"
        shareText(shareText, "Share Page")
    }

    /**
     * Gets the list of apps that can handle a specific intent.
     * @param intent The intent to check.
     * @return List of package names of apps that can handle the intent.
     */
    fun getAppsForIntent(intent: Intent): List<String> {
        val resolveInfoList = context.packageManager.queryIntentActivities(intent, 0)
        return resolveInfoList.map { it.activityInfo.packageName }
    }

    /**
     * Checks if there are apps available to handle a share intent.
     * @param type The MIME type to check.
     * @return True if there are apps available, false otherwise.
     */
    fun hasAppsForSharing(type: String = "text/plain"): Boolean {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            this.type = type
        }
        val resolveInfoList = context.packageManager.queryIntentActivities(shareIntent, 0)
        return resolveInfoList.isNotEmpty()
    }
}
