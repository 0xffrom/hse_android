package com.goga133.hw8

import android.app.Activity
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RemoteViews

/**
 * The configuration screen for the [hw8_widget] AppWidget.
 */
class hw8_widgetConfigureActivity : Activity() {
    private var appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID
    private lateinit var appWidgetText: EditText

    public override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        val context = this
        setResult(RESULT_CANCELED)

        setContentView(R.layout.hw8_widget_configure)

        val intent = intent
        val extras = intent.extras
        if (extras != null) {
            appWidgetId = extras.getInt(
                AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID
            )
        }
        val views = RemoteViews(
            context.packageName,
            R.layout.hw8_widget
        )


        appWidgetText = findViewById<View>(R.id.appwidget_text) as EditText

        findViewById<View>(R.id.add_button).setOnClickListener {
            val widgetText = appWidgetText.text.toString()
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(widgetText))
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
            views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);

            val appWidgetManager = AppWidgetManager.getInstance(context)
            updateAppWidget(appWidgetId, views, appWidgetManager)

            val resultValue = Intent()
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            setResult(RESULT_OK, resultValue)
            finish()
        }



        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish()
            return
        }
    }
}
