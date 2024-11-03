package com.example.clubdeportivo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: UserAdmDatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = UserAdmDatabaseHelper(this)

        val email = findViewById<TextInputEditText>(R.id.editTextTextEmailAddress)
        val password = findViewById<TextInputEditText>(R.id.input_paswword)

        //Verifico que exista el usuario del administrador en la base de datos.
        //if(dbHelper.checkUser(email.text.toString(), password.text.toString())){
        val btn_enter = findViewById<Button>(R.id.btn_enter)
        btn_enter.setOnClickListener {
            val prinpalScreem = Intent(this, PrincipalScren::class.java)
            startActivity(prinpalScreem)
        }
        //}

    }
}