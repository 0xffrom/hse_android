package com.goga133.hw16

import android.app.AlertDialog
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.firebase.iid.FirebaseInstanceId

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<MaterialButton>(R.id.button_token).apply {
            setOnClickListener{
                val tkn = FirebaseInstanceId.getInstance().token
                Toast.makeText(context, "Current token [$tkn]",Toast.LENGTH_LONG).show();
                Log.d("App", "Token [$tkn]");
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