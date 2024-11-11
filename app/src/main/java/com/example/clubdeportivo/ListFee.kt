package com.example.clubdeportivo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ListFee : AppCompatActivity() {

    private lateinit var dbHelper: ClubDeportivoDatabaseHelper
    //private lateinit var dbHelper: FeeDatabaseHandler
    private var feeList = mutableListOf<Fee>()
    private var listaSocios = mutableListOf<Client>()

    //private lateinit var dbHelper1: ClientDatabaseHelper
    private var clientList = mutableListOf<Client>()

    //private lateinit var dbHelper2: ClubActivitiesDatabaseHelper
    private var clubActivityList = mutableListOf<ClubActivity>()

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_fee)

        var inputFrom = findViewById<TextInputEditText>(R.id.input_from).toString()
        var inputTo = findViewById<TextInputEditText>(R.id.input_from).toString()

        if(inputFrom == "" && inputTo != ""){
            val fechaActual = LocalDate.now()
            val formato = DateTimeFormatter.ofPattern("dd/MM/yyyy")

            inputFrom = fechaActual.format(formato)
            inputTo = fechaActual.format(formato)
        }
        listarCuotas(inputFrom,inputTo)

        val btn_search = findViewById<Button>(R.id.btn_search)
        btn_search.setOnClickListener {
            listarCuotas(inputFrom,inputTo)
        }

        val btn_return = findViewById<Button>(R.id.btn_return)
        btn_return.setOnClickListener {
            val principalScren = Intent(this, PrincipalScreen::class.java)
            startActivity(principalScren)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun listarCuotas(deste: String, hasta: String){

        var cantCuotas = 0
        dbHelper = ClubDeportivoDatabaseHelper(this)

        listaSocios = dbHelper.listarSocios().toMutableList()
        var amount = 0
        var idClientAnt = 0
        var i = 0

        if (!listaSocios.isEmpty()) {

            val data: MutableList<MutableList<String>> = mutableListOf()
            var dateFee = ""
            clientList.forEach { client ->

                if(i == 0)
                {
                    idClientAnt = client.idClient
                }
                else{
                    i++
                }

                var builder = AlertDialog.Builder(this)
                builder.setTitle("Socios111")
                builder.setMessage(idClientAnt)
                builder.setPositiveButton("Aceptar") { dialog, _ ->
                    dialog.dismiss()
                }
                builder.create().show()

                if(idClientAnt == client.idClient){

                    feeList = dbHelper.listFeeData(deste, hasta).toMutableList()
                    feeList.forEach { fee ->
                        if(fee.idClientFee != null){

                            clientList = dbHelper.clientDataById(fee.idClientFee).toMutableList()
                            clubActivityList = dbHelper.clubActivutyDataById(fee.idClientFee).toMutableList()
                            clubActivityList.forEach { clubActivity ->
                                amount += clubActivity.costClubActivity ?: 0
                            }
                            dateFee = fee.limitDateFee.toString()
                        }
                    }
                }
                else{
                    data.add(mutableListOf(dateFee,
                        client.dniClient.toString(),
                        client.nameClient.toString(),
                        client.surnameClient.toString(),
                        amount.toString()))
                    amount = 0
                    cantCuotas++
                    idClientAnt = client.idClient
                }
            }

            val tableLayout = findViewById<TableLayout>(R.id.tableLayoutFee)

            // Crear filas y columnas programáticamente
            for (rowData in data) {
                val tableRow = TableRow(this)
                for (cellData in rowData) {
                    val textView = TextView(this)
                    textView.text = cellData
                    textView.setPadding(8, 8, 8, 8) // Ajusta el padding según necesites
                    tableRow.addView(textView)
                }
                tableLayout.addView(tableRow)
            }
        }

        if( cantCuotas == 0 ){
            var builder = AlertDialog.Builder(this)
            builder.setTitle("Sin Datos")
            builder.setMessage("No hay Socios que presenten cuotas a vencer.")
            builder.setPositiveButton("Aceptar") { dialog, _ ->
                dialog.dismiss()
            }
            builder.create().show()
        }
    }
}