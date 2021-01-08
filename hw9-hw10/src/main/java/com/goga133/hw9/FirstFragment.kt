package com.goga133.hw9

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar


class FirstFragment : Fragment() {
    companion object {
        const val INFO_TEXT = "Романюк Андрей Сергеевич БПИ-194"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_first, container, false)

        registerForContextMenu(root.findViewById<TextView>(R.id.textView))

        root.findViewById<MaterialButton>(R.id.button_exit).setOnClickListener {
            AlertDialog.Builder(requireContext()).apply {
                setMessage("Вы хотите выйти из приложения?")
                setCancelable(false)
                setPositiveButton("Да, выйти") { _, _ ->
                    activity?.finish()
                }
                setNegativeButton("Нет") { dialog, _ ->
                    dialog?.cancel()
                }
                create()
            }.show()
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val textView = requireView().findViewById<TextView>(R.id.textView)
        return when (item.itemId) {
            R.id.color_red -> {
                textView.setTextColor(Color.RED)
                true
            }
            R.id.color_black -> {
                textView.setTextColor(Color.BLACK)
                true
            }
            R.id.color_blue -> {
                textView.setTextColor(Color.BLUE)
                true
            }
            else -> {
                super.onContextItemSelected(item)
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_hide -> {
                val textView = requireView().findViewById<TextView>(R.id.textView)
                if (item.isChecked) {
                    textView.visibility = View.VISIBLE
                } else {
                    textView.visibility = View.GONE
                }

                item.isChecked = !item.isChecked
                true
            }
            R.id.action_authorSnackBar -> {
                Snackbar.make(requireView(), INFO_TEXT, Snackbar.LENGTH_SHORT).show()
                true
            }
            R.id.action_authorAlertDialog -> {
                AlertDialog.Builder(requireContext()).apply {
                    try {
                        setMessage("${requireActivity().title} версия ${requireActivity().packageManager.getPackageInfo(requireActivity().packageName,0).versionName} \r\n\nПрограмма с примером выполнения диалогового окна \r\n\nАвтор - Романюк Андрей Сергеевич, БПИ-194")
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

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}