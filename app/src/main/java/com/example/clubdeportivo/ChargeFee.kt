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
    var feeSeleccionado: Int? = 1
    private var clientList = mutableListOf<Client>()
    private var feeList = mutableListOf<Fee>()
    private var formaPagoList = mutableListOf<PaymentMethod>()
    private var formaPagoListSpinner = mutableListOf<String>()
    private var clubActivityList = mutableListOf<ClubActivity>()
    private var amount = 0
    private var detailFee = ""
    private var idFee = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_charge_fee)

        dbHelper = ClubDeportivoDatabaseHelper(this)

        // Configuración del Spinner de formas de pago
        val spinnerfP: Spinner = findViewById(R.id.listView_pay_way)
        val formaPago = listOf("Efectivo", "Tarjeta de Credito")
        formaPagoList = dbHelper.listPaymentMethod().toMutableList()
        formaPagoList.forEach { paymentMehthod ->
            formaPagoListSpinner.add(paymentMehthod.namePaymentMethod.toString())
        }
        val adapterfP = ArrayAdapter(this, android.R.layout.simple_spinner_item, formaPago)
        adapterfP.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerfP.adapter = adapterfP

        // Configuración del Spinner de tarifas
        val spinnerC: Spinner = findViewById(R.id.listView_fee)
        val listFee = listOf(1, 2, 3)
        val adapterC = ArrayAdapter(this, android.R.layout.simple_spinner_item, listFee)
        adapterC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerC.adapter = adapterC

        val dniSearch = intent.getStringExtra  ("KEY_DNICLIENT")
        if (dniSearch != null) {
            clientList = dbHelper.clientData(dniSearch).toMutableList()
            var paramClientId = 0
            clientList.forEach { cliente ->

                paramClientId = cliente.idClient

                val lblNroSocio = findViewById<TextView>(R.id.lbl_numberCC)
                lblNroSocio?.text = "Nro Ident.: " + (cliente.nroClient ?: "")

                val lblDni = findViewById<TextView>(R.id.lbl_dniCC)
                lblDni?.text = "D.N.I.: " + (cliente.dniClient ?: "")

                val lblName = findViewById<TextView>(R.id.lbl_nameCC)
                lblName?.text = "Nombre: " + (cliente.nameClient ?: "")

                val lblSurname = findViewById<TextView>(R.id.lbl_surnameCC)
                lblSurname?.text = "Apellido: " + (cliente.surnameClient ?: "")
            }

            feeList = dbHelper.listFeeDataById(paramClientId).toMutableList()

            feeList.forEach { fee ->

                if(fee.idClientFee != null){
                    idFee = fee.idFee

                    var lbl = 1
                    clientList = dbHelper.clientDataById(fee.idClientFee).toMutableList()

                    clientList.forEach { client ->
                        if(client.essocioClient == 1 ){
                            detailFee = "Pago Mensual"
                        }
                        else{
                            detailFee = "Pago Diario"
                        }

                        clubActivityList = dbHelper.clubActivutyDataById(fee.clubAcivityIdFee).toMutableList()
                        clubActivityList.forEach { clubActivity ->

                            amount += clubActivity.costClubActivity
                            mustraDatos(lbl,clubActivity.nameClibActivity.toString(),clubActivity.costClubActivity.toString())
                        }
                    }
                    lbl++
                }
                val lblTotal = findViewById<TextView>(R.id.lbl_total)
                lblTotal.text = amount.toString()
            }
        }

        // Listener para el Spinner de formas de pago
        spinnerfP.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                formaPagoSeleccionada = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                formaPagoSeleccionada = null // O un valor por defecto si es necesario
            }
        }

        // Listener para el Spinner de Cuotas
        spinnerC.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                feeSeleccionado = parent.getItemAtPosition(position) as? Int ?: 0
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                feeSeleccionado = 1 // Valor predeterminado si no hay selección
            }
        }

        // Manejo del botón de pago
        val btnPay = findViewById<Button>(R.id.btn_pay)
        btnPay.setOnClickListener {

            if (formaPagoSeleccionada != null && feeSeleccionado != null) {

                dbHelper.updateFeeState(idFee)
                dbHelper.addPayFee(amount, detailFee, dbHelper.idPaymentMethod(formaPagoSeleccionada!!), feeSeleccionado ?: 0)

                val invoice = Intent(this, Invoice::class.java).apply {
                    putExtra("KEY_DNICLIENT", dniSearch)
                }
                startActivity(invoice)
            } else {
                // Aquí puedes agregar un mensaje para indicar que se deben seleccionar ambas opciones
            }

        }

        // Manejo del botón de cancelación
        val btnCancel = findViewById<Button>(R.id.btn_cancel)
        btnCancel.setOnClickListener {
            // Redirigir a la actividad PayFee
            val payfee = Intent(this, PayFee::class.java)
            startActivity(payfee)
        }


    }

    private fun mustraDatos(lbl:Int, nameActivity:String, costActivity:String){

        when (lbl) {
            1 -> {
                val lblActivity1 = findViewById<TextView>(R.id.lbl_activity1)
                lblActivity1.text = nameActivity

                val lblMount1 = findViewById<TextView>(R.id.lbl_mount1)
                lblMount1.text = costActivity
            }
            2 -> {
                val lblActivity2 = findViewById<TextView>(R.id.lbl_activity2)
                lblActivity2.text = nameActivity

                val lblMount2 = findViewById<TextView>(R.id.lbl_mount2)
                lblMount2.text = costActivity
            }
            3 -> {
                val lblActivity3 = findViewById<TextView>(R.id.lbl_activity3)
                lblActivity3.text = nameActivity

                val lblMount2 = findViewById<TextView>(R.id.lbl_mount2)
                lblMount2.text = costActivity
            }
        }
    }
}