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
    private var feeList = mutableListOf<Fee>()
    var resultado = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_fee)

        val btnSearch = findViewById<Button>(R.id.btn_search)
        btnSearch.setOnClickListener {

            dbHelper = ClubDeportivoDatabaseHelper(this)
            //dbHelper = ClientDatabaseHelper(this)

            val inputDni = findViewById<TextInputEditText>(R.id.input_dni_searchP)
            val inputDniVal = inputDni.text.toString()
            if (dbHelper.searchClient(inputDniVal)) {
                clientList = dbHelper.clientData(inputDniVal).toMutableList()

                if(!clientList.isEmpty()){

                    clientList.forEach { cliente ->

                        feeList = dbHelper.listFeeDataById(cliente.idClient).toMutableList()
                        if(!feeList.isEmpty()){
                            feeList.forEach { fee ->
                                if(fee.idClientFee != null){
                                    val chargeFee = Intent(this, ChargeFee::class.java).apply {
                                        putExtra("KEY_DNICLIENT", inputDniVal)
                                    }
                                    startActivity(chargeFee)
                                }
                            }
                        }
                        else{
                            mensaje("El dni " + inputDniVal + " no tiene Cuota que pagar.")
                        }


                    }
                }
            }
            else{
                mensaje("El dni " + inputDniVal + " no existe en el sistema.")
            }
        }

        val btnReturn = findViewById<Button>(R.id.btn_return)
        btnReturn.setOnClickListener {
            val principalScreen = Intent(this, PrincipalScreen::class.java)
            startActivity(principalScreen)
        }
    }

    fun mensaje(mensaje:String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Cliente Inexistente")
        builder.setMessage(mensaje)
        builder.setPositiveButton("Aceptar") { dialog, _ ->
            dialog.dismiss()
        }
        builder.create().show()
    }
}