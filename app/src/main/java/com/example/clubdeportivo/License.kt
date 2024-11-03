package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class License : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_license)

        val btn_print = findViewById<Button>(R.id.btn_print)
        btn_print.setOnClickListener {
            // Logica para generar un archovo pdf o txt

            val printLicense = Intent(this, PrintLicense::class.java)
            startActivity(printLicense)
        }

        val btn_cancel = findViewById<Button>(R.id.btn_cancel)
        btn_cancel.setOnClickListener {
            val printLicense = Intent(this, PrintLicense::class.java)
            startActivity(printLicense)
        }

    }
}