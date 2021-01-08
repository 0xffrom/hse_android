package com.goga133.hw13

import android.app.AlertDialog
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.MediaController
import android.widget.VideoView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<VideoView>(R.id.video_view).apply {
            setMediaController(MediaController(context))
            setVideoURI(Uri.parse("android.resource://" + packageName +"/" + R.raw.video))

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