package com.goga133.hw11

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.material.button.MaterialButton


class MainActivity : AppCompatActivity() {
    companion object {
        // Идентификатор уведомления
        private const val NOTIFY_ID = 101
        const val CHANNEL_ID = "HW11"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val resumeButton = findViewById<MaterialButton>(R.id.button_resume)
        val stopButton = findViewById<MaterialButton>(R.id.button_pause)
        val mediaPlayer = MediaPlayer.create(this, R.raw.maksim)

        resumeButton.setOnClickListener{
            mediaPlayer.start()
            resumeButton.visibility = View.GONE
            stopButton.visibility = View.VISIBLE
        }

        stopButton.setOnClickListener{
            mediaPlayer.pause()
            stopButton.visibility = View.GONE
            resumeButton.visibility = View.VISIBLE
        }

        val soundUri : Uri = Uri.parse("${ContentResolver.SCHEME_ANDROID_RESOURCE}://${packageName}/${R.raw.sound}")
        findViewById<MaterialButton>(R.id.button_notification).setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(CHANNEL_ID, CHANNEL_ID, importance)

                val audioAttributes = AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build()

                channel.setSound(soundUri, audioAttributes)
                // Register the channel with the system
                val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }


            val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Печельный заголовок.")
                .setTicker("Новое уведомление!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(NotificationCompat.BigTextStyle()
                    .bigText("Всем привет, и я болею :("))
                .setSound(soundUri)

            with(NotificationManagerCompat.from(this)) {
                notify(NOTIFY_ID, builder.build())
            }

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_author) {
            AlertDialog.Builder(this).apply {
                try {
                    setMessage(
                        "$title версия ${
                            packageManager.getPackageInfo(
                                packageName,
                                0
                            ).versionName
                        } \r\n\nПрограмма с примером выполнения диалогового окна \r\n\nАвтор - Романюк Андрей Сергеевич, БПИ-194"
                    )
                } catch (e: PackageManager.NameNotFoundException) {
                    e.printStackTrace()
                }
                setTitle("О приложении")
                setCancelable(false)
                setNeutralButton("Закрыть") { dialog, _ ->
                    dialog?.dismiss()
                }
                setIcon(R.mipmap.ic_launcher_round)
                create()
            }.show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }
}