package com.goga133.hw16

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FireMsgService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d("Msg", "Message received [$remoteMessage]")

        val intent =  Intent (this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }

        val pendingIntent = PendingIntent.getActivity (this, 1410, intent, PendingIntent.FLAG_ONE_SHOT)
        val notificationBuilder = NotificationCompat.Builder (this)
            .setSmallIcon(R.drawable.common_full_open_on_phone).setContentTitle("Message")
            .setContentText(remoteMessage.notification?.body)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent);

        (getSystemService (Context.NOTIFICATION_SERVICE) as (NotificationManager)).apply {
            notify(1410, notificationBuilder.build())
        }
    }
}