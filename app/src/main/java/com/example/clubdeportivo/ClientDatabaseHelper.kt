package com.example.clubdeportivo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class Client {
    var idClient: Int = 0
    var dniClient: String? = null
    var nameClient: String? = null
    var surnameClient: String? = null
    var emailClient: String? = null
    var essocioClient: Boolean = false
    var nroClient: String? = null
}

class ClientDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "CLUBDEPORTIVO.db"
        const val DATABASE_VERSION = 1
        const val TABLE_CLIENT = "Client"
        const val COLUMN_CLIENT_ID = "idClient"
        const val COLUMN_CLIENT_DNI = "dniClient"
        const val COLUMN_CLIENT_NAME = "nameClient"
        const val COLUMN_CLIENT_SURNAME = "surnameClient"
        const val COLUMN_CLIENT_EMAIL = "emailClient"
        const val COLUMN_CLIENT_PHYSICALLYFIT = "physicallyfitClient"
        const val COLUMN_CLIENT_ESSOCIO = "essocioClient"
        const val COLUMN_CLIENT_NROCLIENT = "nroClient"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableUser = ("CREATE TABLE " + TABLE_CLIENT + " ("
                + COLUMN_CLIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_CLIENT_DNI + " TEXT, "
                + COLUMN_CLIENT_NAME + " TEXT, "
                + COLUMN_CLIENT_SURNAME + " TEXT, "
                + COLUMN_CLIENT_EMAIL + " TEXT, "
                + COLUMN_CLIENT_PHYSICALLYFIT + " INTEGER, "
                + COLUMN_CLIENT_ESSOCIO + " INTEGER, "
                + COLUMN_CLIENT_NROCLIENT + " TEXT)")

        db.execSQL(createTableUser)
        Log.d("ClientDatabaseHelper", "Tabla Client creada.")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CLIENT")
        onCreate(db)
    }

    fun addClient(
        dni: String,
        name: String,
        surname: String,
        email: String,
        physicallyfit: Boolean,
        essocio: Boolean,
        nroclient: String
    ): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_CLIENT_ID, dni)
            put(COLUMN_CLIENT_DNI, name)
            put(COLUMN_CLIENT_NAME, surname)
            put(COLUMN_CLIENT_SURNAME, email)
            put(COLUMN_CLIENT_EMAIL, if (physicallyfit) 1 else 0)
            put(COLUMN_CLIENT_ESSOCIO, if (essocio) 1 else 0)
            put(COLUMN_CLIENT_NROCLIENT, nroclient)
        }
        val success = db.insert(TABLE_CLIENT, null, values)
        db.close()
        return success
    }

    fun searchClient(dni: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_CLIENT WHERE $COLUMN_CLIENT_DNI = ?"
        val cursor = db.rawQuery(query, arrayOf(dni))
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }

    fun clientData(dni: String): List<Client> {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_CLIENT WHERE $COLUMN_CLIENT_DNI = ?"
        val cursor = db.rawQuery(query, arrayOf(dni))
        val clients = mutableListOf<Client>()

        try {
            if (cursor.moveToFirst()) {
                do {
                    val client = Client().apply {
                        idClient = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CLIENT_ID))
                        dniClient = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLIENT_DNI))
                        nameClient = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLIENT_NAME))
                        surnameClient = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLIENT_SURNAME))
                        emailClient = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLIENT_EMAIL))
                        essocioClient = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CLIENT_ESSOCIO)) == 1
                        nroClient = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLIENT_NROCLIENT))
                    }
                    clients.add(client)
                } while (cursor.moveToNext())
            }
        } finally {
            cursor.close()
            db.close()
        }

        return clients
    }
}