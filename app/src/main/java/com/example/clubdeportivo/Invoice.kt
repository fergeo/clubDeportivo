package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Invoice : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invoice)

        val btn_print = findViewById<Button>(R.id.btn_print)
        btn_print.setOnClickListener {
            // Logica para generar un archovo pdf o txt

            val payfee = Intent(this, PayFee::class.java)
            startActivity(payfee)
        }

    }
}