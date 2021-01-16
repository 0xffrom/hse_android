package com.goga133.hw15

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText = findViewById<TextView>(R.id.editText).apply {
            val save = getSharedPreferences("SAVE", 0)
            this.text = save.getString("text", "")
        }


        val arrayList = arrayListOf<String>()

        with(openOrCreateDatabase("DBname", MODE_PRIVATE, null)) {
            this.execSQL("CREATE TABLE IF NOT EXISTS MyTable (Name VARCHAR);")
            // Заполняем список

            val cursor = this.rawQuery("SELECT * FROM MyTable", null)
            cursor.moveToFirst();

            do {
                arrayList.add(cursor.getString(cursor.getColumnIndex("Name")))
            } while (cursor.moveToNext())


            this.close()
        }

        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList)

        findViewById<ListView>(R.id.list_view).apply {
            this.adapter = adapter
            onItemClickListener = (AdapterView.OnItemClickListener { parent, view, position, id ->
                val content = adapter.getItem(position)
                with(openOrCreateDatabase("DBname", MODE_PRIVATE, null)) {
                    this.execSQL("DELETE FROM MyTable WHERE Name='${content}';");
                    this.close()
                }
                adapter.remove(content)
            })
        }

        findViewById<Button>(R.id.button).apply {
            setOnClickListener {
                with(openOrCreateDatabase("DBname", MODE_PRIVATE, null)) {
                    this.execSQL("INSERT INTO MyTable VALUES ('${editText.text}');");
                    this.close()
                }

                adapter.add(editText.text.toString())
                editText.text = ""
            }
        }
    }

    override fun onStop() {
        super.onStop()

        val save = getSharedPreferences("SAVE", 0)
        val editor = save.edit()
        editor.putString("text", findViewById<TextView>(R.id.editText).text.toString());
        editor.apply();
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.settings) {
            val intent = Intent(applicationContext, SettingsActivity::class.java)
            startActivity(intent)
        } else if (item.itemId == R.id.action_author) {
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