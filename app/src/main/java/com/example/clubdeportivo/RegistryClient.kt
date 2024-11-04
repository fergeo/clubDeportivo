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

        var input_dni = findViewById<TextInputEditText>(R.id.input_dni)
        var input_name = findViewById<TextInputEditText>(R.id.input_name)
        var input_surname = findViewById<TextInputEditText>(R.id.input_surname)
        var input_email = findViewById<TextInputEditText>(R.id.input_email)
        val physicallyFitcheckBox = findViewById<CheckBox>(R.id.chk_apto_fisico)
        val essociocheckBox = findViewById<CheckBox>(R.id.chk_socio)

        val btn_clean = findViewById<Button>(R.id.btn_clean)
        btn_clean.setOnClickListener {

            input_dni.setText("")
            input_name.setText("")
            input_surname.setText("")
            input_email.setText("")
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

                val dni = input_dni.getText().toString();
                val name = input_name.getText().toString();
                val surname = input_surname.getText().toString();
                val email = input_email.getText().toString();
                val essocio = essociocheckBox.isChecked

                var nroClient = ""
                if(essocio)
                    nroClient = dni
                else
                    nroClient = "NOSCIO"+dni

                val success = dbHelper.addClient(dni,name,surname,email,physicallyFit,essocio,nroClient)

                val willPrint = Intent(this, WillPrint::class.java)
                willPrint.putExtra("KEY_DNI", dni)
                willPrint.putExtra("KEY_NROCLIENT", nroClient)
                startActivity(willPrint)

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