package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PrincipalScrreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_principal_scrreen)

        val btnLogout = findViewById<Button>(R.id.btn_Logout)
        btnLogout.setOnClickListener {
            val principalScreen = Intent(this, MainActivity::class.java)
            startActivity(principalScreen)
        }
    }
}