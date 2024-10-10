package com.example.clubdeportivo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Button

class Login : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_enter = findViewById<Button>(R.id.btn_enter)
        btn_enter.setOnClickListener {
            val prinpalScreem = Intent(this, PrincipalScren::class.java)
            startActivity(prinpalScreem)
        }

    }
}