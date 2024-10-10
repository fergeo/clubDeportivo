package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ListFee : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_fee)

        val btn_search = findViewById<Button>(R.id.btn_search)
        btn_search.setOnClickListener {
            //Logica para recuperar el listado de cuotas a vencer o vencidas.
        }

        val btn_return = findViewById<Button>(R.id.btn_return)
        btn_return.setOnClickListener {
            val principalScren = Intent(this, PrincipalScren::class.java)
            startActivity(principalScren)
        }

    }
}