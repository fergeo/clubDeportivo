package com.example.clubdeportivo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText

class Login : AppCompatActivity() {

    private lateinit var dbHelper: UserAdmDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        dbHelper = UserAdmDatabaseHelper(this)

        val email = findViewById<TextInputEditText>(R.id.editTextTextEmailAddress)
        val password = findViewById<TextInputEditText>(R.id.input_paswword)


        // Verificar si el usuario administrador ya existe en la base de datos
        if (!dbHelper.checkUser("user@correo.com", "123456")) {
            dbHelper.addUser("user@correo.com", "123456")
        }

        // Configurar el listener del botón de entrada

        //if (dbHelper.checkUser(email.text.toString(), password.text.toString())) {

        val btnEnter = findViewById<Button>(R.id.btn_enter)
        btnEnter.setOnClickListener {
            val principalScreen = Intent(this, PrincipalScrreen::class.java)
            startActivity(principalScreen)
        /*    } else {
                val builder = AlertDialog.Builder(this)  // Cambiado `Context` a `this`
                builder.setTitle("Título de la alerta")
                builder.setMessage("Usuario o contraseña incorrectos.")

                // Botón de Aceptar
                builder.setPositiveButton("Aceptar") { dialog, _ ->
                    dialog.dismiss()
                }

                // Mostrar el diálogo
                val alertDialog = builder.create()
                alertDialog.show()
            }
        */
        }


    }
}