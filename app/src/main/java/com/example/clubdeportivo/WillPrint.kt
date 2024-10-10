package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class WillPrint : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_will_print)

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