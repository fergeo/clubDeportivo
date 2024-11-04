package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class RegistryClient : AppCompatActivity() {

    private lateinit var dbHelper: ClientDatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registry_client)

        dbHelper = ClientDatabaseHelper(this)

        var inputDni = findViewById<TextInputEditText>(R.id.input_dni)
        var inputName = findViewById<TextInputEditText>(R.id.input_name)
        var inputSurname = findViewById<TextInputEditText>(R.id.input_surname)
        var inputEmail = findViewById<TextInputEditText>(R.id.input_email)
        val physicallyFitcheckBox = findViewById<CheckBox>(R.id.chk_apto_fisico)
        val essociocheckBox = findViewById<CheckBox>(R.id.chk_socio)

        val btn_clean = findViewById<Button>(R.id.btn_clean)
        btn_clean.setOnClickListener {

            inputDni.setText("")
            inputName.setText("")
            inputSurname.setText("")
            inputEmail.setText("")
            physicallyFitcheckBox.isChecked = false
            essociocheckBox.isChecked = false

        }

        val btn_return = findViewById<Button>(R.id.btn_return)
        btn_return.setOnClickListener {
            val principalScren = Intent(this, PrincipalScrreen::class.java)
            startActivity(principalScren)
        }

        val btn_acept = findViewById<Button>(R.id.btn_acept)
        btn_acept.setOnClickListener {

            val physicallyFit = physicallyFitcheckBox.isChecked

            if(physicallyFit){

                val dni = inputDni.text.toString()
                val name = inputName.text.toString()
                val surname = inputSurname.text.toString()
                val email = inputEmail.text.toString()
                val essocio = essociocheckBox.isChecked

                var nroClient = ""
                if(essocio)
                    nroClient = dni
                else
                    nroClient = "NOSCIO"+dni

                val success = dbHelper.addClient(dni,name,surname,email,physicallyFit,essocio,nroClient)

                if(essocio){
                    val willPrint = Intent(this, WillPrint::class.java)
                    willPrint.putExtra("KEY_DNI", dni)
                    willPrint.putExtra("KEY_NROCLIENT", nroClient)
                    startActivity(willPrint)
                }
                else{
                    val alertDialog = AlertDialog.Builder(this)
                        .setTitle("Usuario Registrado")
                        .setMessage("Se registro safisfactoriamente a " + name + " " + surname)
                        .setPositiveButton("Aceptar") { dialog, which ->
                            Toast.makeText(this, "Aceptado", Toast.LENGTH_SHORT).show()
                        }
                        .create()

                    // Mostrar el AlertDialog
                    alertDialog.show()

                    val registryClient = Intent(this, RegistryClient::class.java)
                    startActivity(registryClient)
                }
            }
            else{
                val alertDialog = AlertDialog.Builder(this)
                    .setTitle("Falta Apto")
                    .setMessage("Falto marca si presenta Apto Fisico.")
                    .setPositiveButton("Aceptar") { dialog, which ->
                        Toast.makeText(this, "Aceptado", Toast.LENGTH_SHORT).show()
                    }
                    .create()

                // Mostrar el AlertDialog
                alertDialog.show()
            }
        }
    }

}