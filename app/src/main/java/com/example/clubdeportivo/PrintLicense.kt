package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class PrintLicense : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_print_license)

        val btn_search = findViewById<Button>(R.id.btn_search)
        btn_search.setOnClickListener {
            val license = Intent(this, License::class.java)
            startActivity(license)
        }

        val btn_return = findViewById<Button>(R.id.btn_return)
        btn_return.setOnClickListener {
            val principalScreen = Intent(this, PrincipalScren::class.java)
            startActivity(principalScreen)
        }

    }
}