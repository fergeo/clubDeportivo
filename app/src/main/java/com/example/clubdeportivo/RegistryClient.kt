package com.example.clubdeportivo

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class RegistryClient : AppCompatActivity() {

    private lateinit var dbHelper: ClubDeportivoDatabaseHelper
    private var clientList = mutableListOf<Client>()
    //private lateinit var dbHelper: ClientDatabaseHelper
    //private lateinit var dbHelper1: ClubActivitiesDatabaseHelper
    //private lateinit var dbHelper2: FeeDatabaseHandler

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registry_client)

        dbHelper = ClubDeportivoDatabaseHelper(this)
        //dbHelper = ClientDatabaseHelper(this)
        //dbHelper1 = ClubActivitiesDatabaseHelper(this)
        //dbHelper2 = FeeDatabaseHandler(this)

        //Cargi kas actuvudades que habra en el club
        if(dbHelper.isTableEmpty()){
            var clumActivity = dbHelper.addClubActivity("Musculación",150000)
            clumActivity = dbHelper.addClubActivity("Salsa",55000)
            clumActivity = dbHelper.addClubActivity("Basquetball",75000)
        }

        val inputDni = findViewById<TextInputEditText>(R.id.input_dni)
        val inputName = findViewById<TextInputEditText>(R.id.input_name)
        val inputSurname = findViewById<TextInputEditText>(R.id.input_surname)
        val inputEmail = findViewById<TextInputEditText>(R.id.input_email)
        val physicallyFitCheckBox = findViewById<CheckBox>(R.id.chk_apto_fisico)
        val essocioCheckBox = findViewById<CheckBox>(R.id.chk_socio)

        val btnClean = findViewById<Button>(R.id.btn_clean)
        btnClean.setOnClickListener {
            inputDni.text?.clear()
            inputName.text?.clear()
            inputSurname.text?.clear()
            inputEmail.text?.clear()
            physicallyFitCheckBox.isChecked = false
            essocioCheckBox.isChecked = false
        }

        val btnReturn = findViewById<Button>(R.id.btn_return)
        btnReturn.setOnClickListener {
            val principalScreen = Intent(this, PrincipalScreen::class.java)
            startActivity(principalScreen)
        }

        val btnAccept = findViewById<Button>(R.id.btn_acept)
        btnAccept.setOnClickListener {
            val physicallyFit = physicallyFitCheckBox.isChecked

            if (physicallyFit) {
                val dni = inputDni.text.toString()
                val name = inputName.text.toString()
                val surname = inputSurname.text.toString()
                val email = inputEmail.text.toString()
                val essocio = essocioCheckBox.isChecked

                val nroClient = if (essocio) dni else "NOSCIO$dni"

                if (!dbHelper.searchClient(dni)) {
                    val success = dbHelper.addClient(dni, name, surname, email, physicallyFit, essocio, nroClient)

                    if (success != -1L) { // Verifica si la inserción fue exitosa
                        clientList = dbHelper.clientData(dni).toMutableList()

                        var idClient = 0
                        clientList.forEach { cliente ->
                            idClient = cliente.idClient
                        }

                        val fechaActual = LocalDate.now()
                        val formato = DateTimeFormatter.ofPattern("dd/MM/yyyy")

                        if (essocio) {

                            var fee = dbHelper.addFee(idClient,3,fechaActual.format(formato))
                            fee = dbHelper.addFee(idClient,1,fechaActual.format(formato))
                            fee = dbHelper.addFee(idClient,2,fechaActual.format(formato))

                            val willPrint = Intent(this, WillPrint::class.java).apply {
                                putExtra("KEY_DNI", dni)
                                putExtra("KEY_NROCLIENT", nroClient)
                            }
                            startActivity(willPrint)
                        } else {
                            val fechaActual = LocalDate.now()
                            val formato = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                            val fee = dbHelper.addFee(idClient, 2, fechaActual.format(formato))
                            showAlertDialog("Usuario Registrado", "Se registró satisfactoriamente a $name $surname")
                            val registryClient = Intent(this, RegistryClient::class.java)
                            startActivity(registryClient)
                        }
                    } else {
                        showAlertDialog("Error", "No se pudo registrar al usuario.")
                    }
                } else {
                    showAlertDialog("Cliente Existente", "El Cliente con DNI: $dni ya se encuentra registrado")
                }
            } else {
                showAlertDialog("Falta Apto", "Faltó marcar si presenta Apto Físico.")
            }
        }
    }

    private fun showAlertDialog(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Aceptar") { dialog, _ ->
                Toast.makeText(this, "Aceptado", Toast.LENGTH_SHORT).show()
            }
            .create()
            .show()
    }
}