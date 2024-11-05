package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class PayFee : AppCompatActivity() {

    private lateinit var dbHelper: ClubDeportivoDatabaseHelper
    //private lateinit var dbHelper: ClientDatabaseHelper
    private var clientList = mutableListOf<Client>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_fee)

        val btnSearch = findViewById<Button>(R.id.btn_search)
        btnSearch.setOnClickListener {

            dbHelper = ClubDeportivoDatabaseHelper(this)
            //dbHelper = ClientDatabaseHelper(this)

            val dni = findViewById<TextInputEditText>(R.id.input_dni_searchP)
            var dniSearch = dni.text.toString()

            if (dniSearch != null) {
                clientList = dbHelper.clientData(dniSearch).toMutableList()

                if (!clientList.isEmpty()) {
                    clientList.forEach { cliente ->
                        val chargeFee = Intent(this, ChargeFee::class.java).apply {
                            putExtra("KEY_IDCLIENT", cliente.idClient)
                        }
                        startActivity(chargeFee)
                    }
                } else {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Cliente Inexistente")
                    builder.setMessage("El dni " + dni + " no corresponde a un cliente")
                    builder.setPositiveButton("Aceptar") { dialog, _ ->
                        dialog.dismiss()
                    }
                    builder.create().show()
                }


            }
        }

        val btnReturn = findViewById<Button>(R.id.btn_return)
        btnReturn.setOnClickListener {
            val principalScreen = Intent(this, PrincipalScreen::class.java)
            startActivity(principalScreen)
        }
    }
}