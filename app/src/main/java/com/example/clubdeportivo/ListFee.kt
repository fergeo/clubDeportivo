package com.example.clubdeportivo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.Nullable
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

private operator fun Int.plusAssign(costClubActivity: Int?) {

}

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
                        if(Client.essocioClient){
                            if(idClientAnt == Client.idClient){

                                clubActivityList = dbHelper.clubActivutyDataById(fee.idClientFee).toMutableList()
                                clubActivityList.forEach { clubActivity ->
                                    if(Client.essocioClient){
                                        amount += clubActivity.costClubActivity
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
                val lbl_fecVec1 = findViewById<TextView>(R.id.lbl_fecVec1)
                lbl_fecVec1.text = fecVec

                val lbl_dni1 = findViewById<TextView>(R.id.lbl_dni1)
                lbl_dni1.text = dni

                val lbl_nombre1 = findViewById<TextView>(R.id.lbl_nombre1)
                lbl_nombre1.text = nombre

                val lbl_apellido1 = findViewById<TextView>(R.id.lbl_apellido1)
                lbl_apellido1.text = apellido

                val lbl_Monto1 = findViewById<TextView>(R.id.lbl_Monto1)
                lbl_Monto1.text = amount
            }
            2 -> {
                val lbl_fecVec2 = findViewById<TextView>(R.id.lbl_fecVec2)
                lbl_fecVec2.text = fecVec

                val lbl_dni2 = findViewById<TextView>(R.id.lbl_dni2)
                lbl_dni2.text = dni

                val lbl_nombre2 = findViewById<TextView>(R.id.lbl_nombre2)
                lbl_nombre2.text = nombre

                val lbl_apellido2 = findViewById<TextView>(R.id.lbl_apellido2)
                lbl_apellido2.text = apellido

                val lbl_Monto2 = findViewById<TextView>(R.id.lbl_Monto2)
                lbl_Monto2.text = amount
            }
            3 -> {
                val lbl_fecVec3 = findViewById<TextView>(R.id.lbl_fecVec3)
                lbl_fecVec3.text = fecVec

                val lbl_dni3 = findViewById<TextView>(R.id.lbl_dni3)
                lbl_dni3.text = dni

                val lbl_nombre3 = findViewById<TextView>(R.id.lbl_nombre3)
                lbl_nombre3.text = nombre

                val lbl_apellido3 = findViewById<TextView>(R.id.lbl_apellido3)
                lbl_apellido3.text = apellido

                val lbl_Monto3 = findViewById<TextView>(R.id.lbl_Monto3)
                lbl_Monto3.text = amount
            }
            4 -> {
                val lbl_fecVec4 = findViewById<TextView>(R.id.lbl_fecVec4)
                lbl_fecVec4.text = fecVec

                val lbl_dni4 = findViewById<TextView>(R.id.lbl_dni4)
                lbl_dni4.text = dni

                val lbl_nombre4 = findViewById<TextView>(R.id.lbl_nombre4)
                lbl_nombre4.text = nombre

                val lbl_apellido4 = findViewById<TextView>(R.id.lbl_apellido4)
                lbl_apellido4.text = apellido

                val lbl_Monto4 = findViewById<TextView>(R.id.lbl_Monto4)
                lbl_Monto4.text = amount
            }
        }


    }
}