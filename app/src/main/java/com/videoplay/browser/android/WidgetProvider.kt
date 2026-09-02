package com.videoplay.browser.android

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.videoplay.browser.R
import com.videoplay.browser.ui.MainActivity

/**
 * Widget provider for VIDEOPlay Browser.
 * Provides a search widget and quick actions widget.
 */
class WidgetProvider : AppWidgetProvider() {

    companion object {
        const val ACTION_SEARCH = "com.videoplay.browser.ACTION_SEARCH"
        const val ACTION_NEW_TAB = "com.videoplay.browser.ACTION_NEW_TAB"
        const val ACTION_PRIVATE_TAB = "com.videoplay.browser.ACTION_PRIVATE_TAB"
        const val ACTION_DOWNLOADS = "com.videoplay.browser.ACTION_DOWNLOADS"

        /**
         * Updates all widget instances.
         */
        fun updateWidgets(context: Context) {
            val widgetManager = AppWidgetManager.getInstance(context)
            val widgetIds = widgetManager.getAppWidgetIds(
                ComponentName(context, WidgetProvider::class.java)
            )
            val remoteViews = createRemoteViews(context)
            widgetManager.updateAppWidget(widgetIds, remoteViews)
        }

        /**
         * Creates the RemoteViews for the widget.
         */
        private fun createRemoteViews(context: Context): RemoteViews {
            val remoteViews = RemoteViews(
                context.packageName,
                R.layout.widget_search
            )

            // Set up search button click
            val searchIntent = Intent(context, MainActivity::class.java).apply {
                action = ACTION_SEARCH
            }
            val searchPendingIntent = PendingIntent.getActivity(
                context,
                0,
                searchIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            remoteViews.setOnClickPendingIntent(R.id.widget_search_button, searchPendingIntent)

            // Set up new tab button click
            val newTabIntent = Intent(context, MainActivity::class.java).apply {
                action = ACTION_NEW_TAB
            }
            val newTabPendingIntent = PendingIntent.getActivity(
                context,
                1,
                newTabIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            remoteViews.setOnClickPendingIntent(R.id.widget_new_tab_button, newTabPendingIntent)

            // Set up private tab button click
            val privateTabIntent = Intent(context, MainActivity::class.java).apply {
                action = ACTION_PRIVATE_TAB
            }
            val privateTabPendingIntent = PendingIntent.getActivity(
                context,
                2,
                privateTabIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            remoteViews.setOnClickPendingIntent(R.id.widget_private_tab_button, privateTabPendingIntent)

            // Set up downloads button click
            val downloadsIntent = Intent(context, MainActivity::class.java).apply {
                action = ACTION_DOWNLOADS
            }
            val downloadsPendingIntent = PendingIntent.getActivity(
                context,
                3,
                downloadsIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            remoteViews.setOnClickPendingIntent(R.id.widget_downloads_button, downloadsPendingIntent)

            return remoteViews
        }
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // Update all widget instances
        updateWidgets(context)
    }

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            ACTION_SEARCH,
            ACTION_NEW_TAB,
            ACTION_PRIVATE_TAB,
            ACTION_DOWNLOADS -> {
                // Handle widget button clicks
                // The actual handling would be in MainActivity
            }
        }
        super.onReceive(context, intent)
    }

    override fun onEnabled(context: Context) {
        // Called when the first widget instance is created
        super.onEnabled(context)
    }

    override fun onDisabled(context: Context) {
        // Called when the last widget instance is removed
        super.onDisabled(context)
    }
}
