package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.text.TextPaint
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream
import java.io.IOError
import java.lang.Exception

class Invoice : AppCompatActivity() {

    private lateinit var dbHelper: ClubDeportivoDatabaseHelper
    private var clientList = mutableListOf<Client>()
    private var feeList = mutableListOf<Fee>()
    private var clubActivityList = mutableListOf<ClubActivity>()
    private var amount = 0
    private var detailFee = ""
    private var idFee = 0
    private var lblNroSocioPDF: String? = null
    private var lblDNIPDF: String? = null
    private var lblNameoPDF: String? = null
    private var lblSurnamePDF: String? = null
    private var lblDetail1PDF: String? = null
    private var lblDetail2PDF: String? = null
    private var lblDetail3PDF: String? = null
    private var lblTotalPDF: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invoice)

        dbHelper = ClubDeportivoDatabaseHelper(this)

        val dniSearch = intent.getStringExtra  ("KEY_DNICLIENT")

        if (dniSearch != null) {
            clientList = dbHelper.clientData(dniSearch).toMutableList()
            var paramClientId = 0
            clientList.forEach { cliente ->

                paramClientId = cliente.idClient

                val lblNroSocio = findViewById<TextView>(R.id.lbl_numberF)
                lblNroSocio?.text = "Nro Ident.: " + (cliente.nroClient ?: "")
                lblNroSocioPDF = "Nro Ident.: " + (cliente.nroClient ?: "")

                val lblDni = findViewById<TextView>(R.id.lbl_dniF)
                lblDni?.text = "D.N.I.: " + (cliente.dniClient ?: "")

                val lblName = findViewById<TextView>(R.id.lbl_nameF)
                lblName?.text = "Nombre: " + (cliente.nameClient ?: "")

                val lblSurname = findViewById<TextView>(R.id.lbl_surnameF)
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

                        clubActivityList = dbHelper.clubActivutyDataById(fee.idClientFee).toMutableList()
                        clubActivityList.forEach { clubActivity ->

                            amount += clubActivity.costClubActivity
                            mustraDatos(lbl,clubActivity.nameClibActivity.toString(),clubActivity.costClubActivity.toString())
                            lbl++
                        }

                        val lblTotal = findViewById<TextView>(R.id.lbl_total)
                        lblTotal.text = amount.toString()
                    }
                }
            }
        }

        //if(checkPermission()) {
        //    Toast.makeText(this, "Permiso Aceptado", Toast.LENGTH_LONG)
        //} else {
        //    requestPermissions()
        //}

        val btn_print = findViewById<Button>(R.id.btn_print)
        btn_print.setOnClickListener {

            generarPdf()

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
    fun generarPdf() {

        var pdfDocument = PdfDocument()
        var paint = Paint()
        var titulo = TextPaint()
        var descripcion = TextPaint()

        var paginaInfo = PdfDocument.PageInfo.Builder(816, 1054, 1).create()
        var pagina1 = pdfDocument.startPage(paginaInfo)

        var canvas = pagina1.canvas

        //var bitmap = BitmapFactory.decodeResource(resources, R.drawable.logo)
        //var bitmapEscala = Bitmap.createScaledBitmap(bitmap, 80,80, false)
        //canvas.drawBitmap(bitmapEscala, 368f, 20f, paint)

        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        titulo.textSize = 20f
        canvas.drawText("Factura", 10f, 150f, titulo)

        descripcion.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
        descripcion.textSize = 14f

        lblNroSocioPDF?.let { nonNullLblNroSocioPDF ->
            val arrDescripcion = nonNullLblNroSocioPDF.split("\n")
            var y = 200f
            for (item in arrDescripcion) {
                canvas.drawText(item, 10f, y, descripcion)
                y += 15
            }
        }

        pdfDocument.finishPage(pagina1)

        val file = File(Environment.getExternalStorageDirectory(), "Archivo.pdf")
        try {
            pdfDocument.writeTo(FileOutputStream(file))
            Toast.makeText(this, "Se creo el PDF correctamente", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        pdfDocument.close()

    }

    private fun checkPermission(): Boolean {
        val permission1 = ContextCompat.checkSelfPermission(applicationContext, WRITE_EXTERNAL_STORAGE)
        val permission2 = ContextCompat.checkSelfPermission(applicationContext, READ_EXTERNAL_STORAGE)
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE),
            200
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 200) {
            if(grantResults.size > 0) {
                val writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED
                val readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED

                if(writeStorage && readStorage) {
                    Toast.makeText(this, "Permisos concedidos", Toast.LENGTH_LONG)
                } else {
                    Toast.makeText(this, "Permisos rechazados", Toast.LENGTH_LONG)
                    finish()
                }
            }
        }
    }
}