package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class WillPrint : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_will_print)

        var lbl_nroSocio = findViewById<TextView>(R.id.lbl_nroSocio)
        var lbl_dni = findViewById<TextView>(R.id.lbl_dni)

        val willPrint = Intent(this, WillPrint::class.java)

        val dni = intent.getStringExtra("KEY_DNI")
        val nroClient = intent.getStringExtra("KEY_NROCLIENT")

        lbl_dni.text = "DNI: " + dni
        lbl_nroSocio.text = "Nro. Pase: " + nroClient

        val btn_yes = findViewById<Button>(R.id.btn_yes)
        btn_yes.setOnClickListener {
            val license = Intent(this, License::class.java)
            startActivity(license)
        }

        val btn_no = findViewById<Button>(R.id.btn_no)
        btn_no.setOnClickListener {
            val registryClient = Intent(this, RegistryClient::class.java)
            startActivity(registryClient)
        }

    }
}