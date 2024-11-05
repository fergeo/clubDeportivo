package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import androidx.appcompat.app.AlertDialog

class PrintLicense : AppCompatActivity() {

    private lateinit var dbHelper: ClubDeportivoDatabaseHelper
    //private lateinit var dbHelper: ClientDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_print_license)

        dbHelper = ClubDeportivoDatabaseHelper(this)
        //dbHelper = ClientDatabaseHelper(this)

        val inputDni = findViewById<TextInputEditText>(R.id.input_dni)
        val btnSearch = findViewById<Button>(R.id.btn_search)

        btnSearch.setOnClickListener {
            val inputDniVal = inputDni.text.toString()

            if (dbHelper.searchClient(inputDniVal)) {
                val license = Intent(this, License::class.java)
                license.putExtra("KEY_DNI", inputDniVal)
                startActivity(license)
            } else {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Socio Inexistente")
                builder.setMessage("No existe el Socio con DNI: $inputDniVal")
                builder.setPositiveButton("Aceptar") { dialog, _ ->
                    dialog.dismiss()
                }
                builder.create().show()
            }
        }

        // Configuración opcional de márgenes de ventana
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { view, insets ->
            view.setPadding(
                view.paddingLeft,
                insets.getInsets(WindowInsetsCompat.Type.systemBars()).top,
                view.paddingRight,
                insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
            )
            insets
        }

        val btnReturn = findViewById<Button>(R.id.btn_return)
        btnReturn.setOnClickListener {
            val principalScreen = Intent(this, PrincipalScreen::class.java)
            startActivity(principalScreen)
        }
    }
}