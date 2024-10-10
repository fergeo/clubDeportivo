package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class PayFee : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pay_fee)

        val btn_search = findViewById<Button>(R.id.btn_search)
        btn_search.setOnClickListener {
            val chargeFee = Intent(this, ChargeFee::class.java)
            startActivity(chargeFee)
        }

        val btn_return = findViewById<Button>(R.id.btn_return)
        btn_return.setOnClickListener {
            val principalScren = Intent(this, PrincipalScren::class.java)
            startActivity(principalScren)
        }

    }
}