package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class PayFee : AppCompatActivity() {

    private lateinit var dbHelper: ClientDatabaseHelper
    private var clientList = mutableListOf<Client>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_fee)

        val btnSearch = findViewById<Button>(R.id.btn_search)
        btnSearch.setOnClickListener {

            dbHelper = ClientDatabaseHelper(this)

            val dni = findViewById<TextInputEditText>(R.id.input_dni_search)

            if (dni != null) {
                clientList = dbHelper.clientData(dni.toString()).toMutableList()

                clientList.forEach { cliente ->
                    val chargeFee = Intent(this, WillPrint::class.java).apply {
                        putExtra("KEY_IDCLIENT", cliente.idClient)
                    }
                    startActivity(chargeFee)
                }
            }
        }
    }
}