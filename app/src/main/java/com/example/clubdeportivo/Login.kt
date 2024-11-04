package com.example.clubdeportivo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText
import androidx.appcompat.app.AlertDialog

class Login : AppCompatActivity() {

    private lateinit var dbHelper: UserAdmDatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        dbHelper = UserAdmDatabaseHelper(this)

        val email = findViewById<TextInputEditText>(R.id.input_email)
        val password = findViewById<TextInputEditText>(R.id.input_password)

        // Verificar si el usuario administrador ya existe en la base de datos
        if (!dbHelper.checkUser("user@correo.com", "123456")) {
            dbHelper.addUser("user@correo.com", "123456")
        }

        // Configurar el listener del botón de entrada
        val btnEnter = findViewById<Button>(R.id.btn_enter)
        btnEnter.setOnClickListener {
            val enteredEmail = email.text.toString()
            val enteredPassword = password.text.toString()

            if (dbHelper.checkUser(enteredEmail, enteredPassword)) {
                val principalScreen = Intent(this, PrincipalScreen::class.java)
                startActivity(principalScreen)
            } else {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Error de inicio de sesión")
                builder.setMessage("Usuario o contraseña incorrectos.")
                builder.setPositiveButton("Aceptar") { dialog, _ ->
                    dialog.dismiss()
                }
                builder.create().show()
            }
        }
    }
}