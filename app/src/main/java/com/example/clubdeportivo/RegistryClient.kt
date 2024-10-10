package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class RegistryClient : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registry_client)


/*
            val btn_clean = findViewById<Button>(R.id.btn_clean)
            btn_clean.setOnClickListener {

            var input_dni = (TextView)findViewById(R.id.input_dni)
            input_dni.setText("")

            var input_name = (TextView)findViewById(R.id.input_name)
            input_dni.setText("")

            var input_surname = (TextView)findViewById(R.id.input_surname)
            input_dni.setText("")

            var input_email = (TextView)findViewById(R.id.input_email)
            input_dni.setText("")

        }
*/

        val btn_return = findViewById<Button>(R.id.btn_return)
        btn_return.setOnClickListener {
            val principalScren = Intent(this, PrincipalScren::class.java)
            startActivity(principalScren)
        }

        val btn_acept = findViewById<Button>(R.id.btn_acept)
        btn_acept.setOnClickListener {
            val willPrint = Intent(this, WillPrint::class.java)
            startActivity(willPrint)
        }

    }

}