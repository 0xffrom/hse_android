package com.goga133.hw8

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import com.goga133.hw8.hw8_widget.Companion.URL_STR


/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in [hw8_widgetConfigureActivity]
 */
class hw8_widget : AppWidgetProvider() {
    companion object {
        const val URL_STR = "https://github.com/goga133/hse_android"
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(
                context = context,
                appWidgetManager = appWidgetManager,
                appWidgetId = appWidgetId
            )
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    appWidgetId: Int,
    context: Context,
    appWidgetManager: AppWidgetManager
) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(URL_STR))
    val pending = PendingIntent.getActivity(context, 0, intent, 0)

    val remoteViews = RemoteViews(context.packageName, R.layout.hw8_widget)
    remoteViews.setOnClickPendingIntent(R.id.appwidget_text, pending)
    appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
}
internal fun updateAppWidget(
    appWidgetId: Int,
    remoteViews: RemoteViews,
    appWidgetManager: AppWidgetManager
) {
    appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
}