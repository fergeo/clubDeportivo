package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

private operator fun Int.plusAssign(costClubActivity: Int?) {

}

class ListFee : AppCompatActivity() {

    private lateinit var dbHelper: FeeDatabaseHandler
    private var feeList = mutableListOf<Fee>()

    private lateinit var dbHelper1: ClientDatabaseHelper
    private var clientList = mutableListOf<Client>()

    private lateinit var dbHelper2: ClubActivitiesDatabaseHelper
    private var clubActivityList = mutableListOf<ClubActivity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_fee)

        val btn_search = findViewById<Button>(R.id.btn_search)
        btn_search.setOnClickListener {

            dbHelper = FeeDatabaseHandler(this)
            dbHelper1 = ClientDatabaseHelper(this)
            dbHelper2 = ClubActivitiesDatabaseHelper(this)

            var inputFrom = findViewById<TextInputEditText>(R.id.input_from).toString()
            var inputTo = findViewById<TextInputEditText>(R.id.input_from).toString()

            if(inputFrom == "" && inputTo != ""){
                inputFrom = "01/01/1900"
                inputTo = "31/12/2030"
            }

            feeList = dbHelper.listFeeData(inputFrom, inputTo).toMutableList()

            feeList.forEach { fee ->

                var amount = 0
                val idClientAnt = 0
                clientList = dbHelper1.clientDataById(fee.idClientFee).toMutableList()

                clientList.forEach { Client ->
                    if(Client.essocioClient){
                        if(idClientAnt == Client.idClient){

                            clubActivityList = dbHelper2.clubActivutyDataById(fee.idClientFee).toMutableList()
                            clubActivityList.forEach { clubActivity ->
                                if(Client.essocioClient){
                                    if(idClientAnt == Client.idClient){
                                        amount += clubActivity.costClubActivity
                                    }
                                }
                            }

                            amount = amount + fee.
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
}