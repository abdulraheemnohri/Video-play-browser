package com.videoplay.browser.downloads

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.videoplay.browser.R

/**
 * Service for managing downloads in the background.
 * Shows notifications for download progress.
 */
class DownloadService : Service() {

    private lateinit var notificationManager: NotificationManager
    private lateinit var downloadManager: DownloadManager

    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        downloadManager = DownloadManager(this)

        // Create notification channel for downloads
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Download Notifications",
                NotificationManager.IMPORTANCE_LOW
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START_DOWNLOAD -> {
                val url = intent.getStringExtra(EXTRA_URL) ?: return START_NOT_STICKY
                val fileName = intent.getStringExtra(EXTRA_FILE_NAME) ?: return START_NOT_STICKY
                startDownload(url, fileName)
            }
            ACTION_CANCEL_DOWNLOAD -> {
                val downloadId = intent.getLongExtra(EXTRA_DOWNLOAD_ID, -1L)
                if (downloadId != -1L) {
                    downloadManager.cancelDownload(downloadId)
                }
            }
        }
        return START_STICKY
    }

    /**
     * Starts a new download and shows a notification.
     */
    private fun startDownload(url: String, fileName: String) {
        val downloadId = downloadManager.startDownload(url, fileName)

        // Show notification
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(fileName)
            .setContentText("Downloading...")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setProgress(100, 0, false)
            .setOngoing(true)
            .setAutoCancel(false)
            .build()

        startForeground(NOTIFICATION_ID, notification)

        // TODO: Update notification progress in a background thread
    }

    /**
     * Updates the download progress notification.
     */
    private fun updateDownloadProgress(downloadId: Long, progress: Int) {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Download in Progress")
            .setContentText("$progress% complete")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setProgress(100, progress, false)
            .setOngoing(true)
            .setAutoCancel(false)
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    /**
     * Shows a notification for a completed download.
     */
    private fun showDownloadCompleteNotification(fileName: String) {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Download Complete")
            .setContentText("$fileName has been downloaded.")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setProgress(0, 0, false)
            .setOngoing(false)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)
        stopForeground(true)
    }

    companion object {
        const val CHANNEL_ID = "download_channel"
        const val NOTIFICATION_ID = 1

        const val ACTION_START_DOWNLOAD = "start_download"
        const val ACTION_CANCEL_DOWNLOAD = "cancel_download"

        const val EXTRA_URL = "extra_url"
        const val EXTRA_FILE_NAME = "extra_file_name"
        const val EXTRA_DOWNLOAD_ID = "extra_download_id"
    }
}
