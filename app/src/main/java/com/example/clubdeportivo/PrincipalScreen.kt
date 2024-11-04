package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class PrincipalScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_principal_scrreen)

        val btm_register = findViewById<Button>(R.id.btm_register)
        btm_register.setOnClickListener {
            val registryClient = Intent(this, RegistryClient::class.java)
            startActivity(registryClient)
        }

        val btmLicense = findViewById<Button>(R.id.btn_license)
        btmLicense.setOnClickListener {
            val pintLicense = Intent(this, PrintLicense::class.java)
            startActivity(pintLicense)
        }


        val btnLogout = findViewById<Button>(R.id.btn_Logout)
        btnLogout.setOnClickListener {
            val principalScreen = Intent(this, Login::class.java)
            startActivity(principalScreen)
        }
    }
}