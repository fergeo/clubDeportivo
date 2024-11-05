package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class ChargeFee : AppCompatActivity() {

    private lateinit var dbHelper: ClubDeportivoDatabaseHelper
    // Variables para almacenar las selecciones
    var formaPagoSeleccionada: String? = null
    var feeSeleccionado: Int? = null
    private var clientList = mutableListOf<Client>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_charge_fee)

        dbHelper = ClubDeportivoDatabaseHelper(this)

        // Configuración del Spinner de formas de pago
        val spinnerfP: Spinner = findViewById(R.id.listView_pay_way)
        val formaPago = listOf("Efectivo", "Tarjeta de Credito")
        val adapterfP = ArrayAdapter(this, android.R.layout.simple_spinner_item, formaPago)
        adapterfP.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerfP.adapter = adapterfP

        // Configuración del Spinner de tarifas
        val spinnerC: Spinner = findViewById(R.id.listView_fee)
        val feeList = listOf(1, 2, 3)
        val adapterC = ArrayAdapter(this, android.R.layout.simple_spinner_item, feeList)
        adapterC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerC.adapter = adapterC

        val dniSearch = intent.getStringExtra  ("KEY_DNICLIENT")

        if (dniSearch != null) {
            clientList = dbHelper.clientData(dniSearch).toMutableList()

            clientList.forEach { cliente ->
                val lblNroSocio = findViewById<TextView>(R.id.lbl_numberCC)
                lblNroSocio?.text = "Nro Ident.: " + (cliente.nroClient ?: "")

                val lblDni = findViewById<TextView>(R.id.lbl_dniCC)
                lblDni?.text = "D.N.I.: " + (cliente.dniClient ?: "")

                val lblName = findViewById<TextView>(R.id.lbl_nameCC)
                lblName?.text = "Nombre: " + (cliente.nameClient ?: "")

                val lblSurname = findViewById<TextView>(R.id.lbl_surnameCC)
                lblSurname?.text = "Apellido: " + (cliente.surnameClient ?: "")
            }
        }
        else{
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Lista vacia de Cliente")
            builder.setMessage("Lista vacia de Cliente.")
            builder.setPositiveButton("Aceptar") { dialog, _ ->
                dialog.dismiss()
            }
            builder.create().show()
        }

        // Manejo del botón de pago
        val btn_pay = findViewById<Button>(R.id.btn_pay)
        btn_pay.setOnClickListener {
            if (formaPagoSeleccionada != null && feeSeleccionado != null) {
                val invoice = Intent(this, Invoice::class.java)
                startActivity(invoice)
            } else {
                // Aquí puedes agregar un mensaje para indicar que se deben seleccionar ambas opciones
            }
        }

        // Manejo del botón de cancelación
        val btn_cancel = findViewById<Button>(R.id.btn_cancel)
        btn_cancel.setOnClickListener {
            // Listener para el Spinner de formas de pago
            spinnerfP.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    formaPagoSeleccionada = parent.getItemAtPosition(position).toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    formaPagoSeleccionada = null // O un valor por defecto si es necesario
                }
            }

            // Listener para el Spinner de tarifas
            spinnerC.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    feeSeleccionado = parent.getItemAtPosition(position) as Int
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    feeSeleccionado = null // O un valor por defecto si es necesario
                }
            }

            // Redirigir a la actividad PayFee
            val payfee = Intent(this, PayFee::class.java)
            startActivity(payfee)
        }
    }
}