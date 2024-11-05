package com.example.clubdeportivo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class License : AppCompatActivity() {

    private lateinit var dbHelper: ClubDeportivoDatabaseHelper
    //private lateinit var dbHelper: ClientDatabaseHelper
    private var clientList = mutableListOf<Client>()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_license)

        dbHelper = ClubDeportivoDatabaseHelper(this)
        //dbHelper = ClientDatabaseHelper(this)

        val dni = intent.getStringExtra("KEY_DNI")

        if (dni != null) {
            clientList = dbHelper.clientData(dni).toMutableList()

            clientList.forEach { cliente ->
                val lblNroSocio = findViewById<TextView>(R.id.lbl_nroSocio)
                lblNroSocio?.text = "Nro Ident.: " + (cliente.nroClient ?: "")

                val lblDni = findViewById<TextView>(R.id.lbl_dni)
                lblDni?.text = "D.N.I.: " + (cliente.dniClient ?: "")

                val lblName = findViewById<TextView>(R.id.lbl_name)
                lblName?.text = "Nombre: " + (cliente.nameClient ?: "")

                val lblSurname = findViewById<TextView>(R.id.lbl_surname)
                lblSurname?.text = "Apellido: " + (cliente.surnameClient ?: "")

                val lbl_surname = findViewById<TextView>(R.id.lbl_email)
                lbl_surname?.text = "E-mail: " + (cliente.emailClient ?: "")
            }
        }

        val btn_cancel = findViewById<Button>(R.id.btn_cancel)
        btn_cancel.setOnClickListener {
            val printLicense = Intent(this, PrintLicense::class.java)
            startActivity(printLicense)
        }
    }
}