package com.example.clubdeportivo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class ListFee : AppCompatActivity() {

    private lateinit var dbHelper: ClubDeportivoDatabaseHelper
    //private lateinit var dbHelper: FeeDatabaseHandler
    private var feeList = mutableListOf<Fee>()

    //private lateinit var dbHelper1: ClientDatabaseHelper
    private var clientList = mutableListOf<Client>()

    //private lateinit var dbHelper2: ClubActivitiesDatabaseHelper
    private var clubActivityList = mutableListOf<ClubActivity>()

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_fee)

        val btn_search = findViewById<Button>(R.id.btn_search)
        btn_search.setOnClickListener {

            dbHelper = ClubDeportivoDatabaseHelper(this)
            //dbHelper = FeeDatabaseHandler(this)
            //dbHelper1 = ClientDatabaseHelper(this)
            //dbHelper2 = ClubActivitiesDatabaseHelper(this)

            var inputFrom = findViewById<TextInputEditText>(R.id.input_from).toString()
            var inputTo = findViewById<TextInputEditText>(R.id.input_from).toString()

            if(inputFrom == "" && inputTo != ""){
                inputFrom = "01/01/1900"
                inputTo = "31/12/2030"
            }

            feeList = dbHelper.listFeeData(inputFrom, inputTo).toMutableList()

            feeList.forEach { fee ->

                if(fee.idClientFee != null){
                    var amount = 0
                    var idClientAnt = 0
                    var lblNum = 0
                    clientList = dbHelper.clientDataById(fee.idClientFee).toMutableList()

                    clientList.forEach { Client ->
                        if(Client.essocioClient == 1){
                            if(idClientAnt == Client.idClient){

                                clubActivityList = dbHelper.clubActivutyDataById(fee.idClientFee).toMutableList()
                                clubActivityList.forEach { clubActivity ->
                                    if(Client.essocioClient == 1){
                                        amount += clubActivity.costClubActivity ?: 0
                                    }
                                }
                            }
                            else{
                                mustraDatos(lblNum, fee.limitDateFee.toString(), Client.dniClient.toString(), Client.nameClient.toString(), Client.surnameClient.toString(), amount.toString())
                                lblNum++
                                amount = 0
                                idClientAnt = Client.idClient
                            }
                        }
                        else{
                            val builder = AlertDialog.Builder(this)
                            builder.setTitle("Lista vacia")
                            builder.setMessage("No se encontraron socio con cuotas pendiente de pago.")
                            builder.setPositiveButton("Aceptar") { dialog, _ ->
                                dialog.dismiss()
                            }
                            builder.create().show()
                        }
                    }
                }

            }

        }

        val btn_return = findViewById<Button>(R.id.btn_return)
        btn_return.setOnClickListener {
            val principalScren = Intent(this, PrincipalScreen::class.java)
            startActivity(principalScren)
        }
    }

    fun mustraDatos(lbl:Int, fecVec:String, dni:String, nombre:String, apellido:String, amount:String){

        when (lbl) {
            1 -> {
                val lblFecVec1 = findViewById<TextView>(R.id.lbl_fecVec1)
                lblFecVec1.text = fecVec

                val lblDni1 = findViewById<TextView>(R.id.lbl_dni1)
                lblDni1.text = dni

                val lblNombre1 = findViewById<TextView>(R.id.lbl_nombre1)
                lblNombre1.text = nombre

                val lblApellido1 = findViewById<TextView>(R.id.lbl_apellido1)
                lblApellido1.text = apellido

                val lblMonto1 = findViewById<TextView>(R.id.lbl_Monto1)
                lblMonto1.text = amount
            }
            2 -> {
                val lblFecVec2 = findViewById<TextView>(R.id.lbl_fecVec2)
                lblFecVec2.text = fecVec

                val lblDni2 = findViewById<TextView>(R.id.lbl_dni2)
                lblDni2.text = dni

                val lblNombre2 = findViewById<TextView>(R.id.lbl_nombre2)
                lblNombre2.text = nombre

                val lblApellido2 = findViewById<TextView>(R.id.lbl_apellido2)
                lblApellido2.text = apellido

                val lblMonto2 = findViewById<TextView>(R.id.lbl_Monto2)
                lblMonto2.text = amount
            }
            3 -> {
                val lblFecVec3 = findViewById<TextView>(R.id.lbl_fecVec3)
                lblFecVec3.text = fecVec

                val lblDni3 = findViewById<TextView>(R.id.lbl_dni3)
                lblDni3.text = dni

                val lblNombre3 = findViewById<TextView>(R.id.lbl_nombre3)
                lblNombre3.text = nombre

                val lblApellido3 = findViewById<TextView>(R.id.lbl_apellido3)
                lblApellido3.text = apellido

                val lblMonto3 = findViewById<TextView>(R.id.lbl_Monto3)
                lblMonto3.text = amount
            }
            4 -> {
                val lblFecVec4 = findViewById<TextView>(R.id.lbl_fecVec4)
                lblFecVec4.text = fecVec

                val lblDni4 = findViewById<TextView>(R.id.lbl_dni4)
                lblDni4.text = dni

                val lblNombre4 = findViewById<TextView>(R.id.lbl_nombre4)
                lblNombre4.text = nombre

                val lblApellido4 = findViewById<TextView>(R.id.lbl_apellido4)
                lblApellido4.text = apellido

                val lblMonto4 = findViewById<TextView>(R.id.lbl_Monto4)
                lblMonto4.text = amount
            }
        }
    }
}