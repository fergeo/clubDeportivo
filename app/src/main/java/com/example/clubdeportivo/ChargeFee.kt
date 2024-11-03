package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class ChargeFee : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_charge_fee)

        val arrayAdapter1:ArrayAdapter<*>
        val formaPago = mutableListOf("Efectivo","Tarjeta de Credito")
        val lvDatos1 = findViewById<ListView>(R.id.listView_pay_way)

        val arrayAdapter2:ArrayAdapter<*>
        val Cuotas = mutableListOf("1","3","6")
        val lvDatos2 = findViewById<ListView>(R.id.listView_fee)

        val btn_pay = findViewById<Button>(R.id.btn_pay)
        btn_pay.setOnClickListener {
            val invoice = Intent(this, Invoice::class.java)
            startActivity(invoice)
        }

        val btn_cancel = findViewById<Button>(R.id.btn_cancel)
        btn_cancel.setOnClickListener {
            // Logica para generar un archovo pdf o txt

            val payfee = Intent(this, PayFee::class.java)
            startActivity(payfee)
        }

    }
}