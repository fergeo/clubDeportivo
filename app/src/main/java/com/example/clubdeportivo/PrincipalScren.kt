package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class PrincipalScren : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal_screen)

        val btm_register = findViewById<Button>(R.id.btm_register)
        btm_register.setOnClickListener {
            val registryClient = Intent(this, RegistryClient::class.java)
            startActivity(registryClient)
        }

        val btn_license = findViewById<Button>(R.id.btn_license)
        btn_license.setOnClickListener {
            val printLicense = Intent(this, PrintLicense::class.java)
            startActivity(printLicense)
        }

        val btn_list = findViewById<Button>(R.id.btn_list)
        btn_list.setOnClickListener {
            val listFee = Intent(this, ListFee::class.java)
            startActivity(listFee)
        }

        val btn_payment = findViewById<Button>(R.id.btn_payment)
        btn_payment.setOnClickListener {
            val payFee = Intent(this, PayFee::class.java)
            startActivity(payFee)
        }

        val btn_Logout = findViewById<Button>(R.id.btn_Logout)
        btn_Logout.setOnClickListener {
            val mainScreen = Intent(this, MainActivity::class.java)
            startActivity(mainScreen)
        }

    }
}