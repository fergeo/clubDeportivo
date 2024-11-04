package com.example.clubdeportivo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ClientDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "CLUBDEPORTIVO.db"
        const val DATABASE_VERSION = 5
        const val TABLE_CLIENT = "Client"
        const val TABLE_CLIENT_ID = "idClient"
        const val TABLE_CLIENT_DNI = "dniClient"
        const val TABLE_CLIENT_NAME = "nameClient"
        const val TABLE_CLIENT_SURNAME = "surnameClient"
        const val TABLE_CLIENT_EMAIL = "emailClient"
        const val TABLE_CLIENT_PHYSICALLYFIT = "physicallyfitClient"
        const val TABLE_CLIENT_ESSOCIO = "essocioClient"
        const val TABLE_CLIENT_NROCLIENT = "nroClient"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableUser = ("CREATE TABLE " + TABLE_CLIENT + " ("
                + TABLE_CLIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TABLE_CLIENT_DNI + " TEXT, "
                + TABLE_CLIENT_NAME + " TEXT, "
                + TABLE_CLIENT_SURNAME + " TEXT, "
                + TABLE_CLIENT_EMAIL + " TEXT, "
                + TABLE_CLIENT_PHYSICALLYFIT + " INTEGER, "
                + TABLE_CLIENT_ESSOCIO + " INTEGER, "
                + TABLE_CLIENT_NROCLIENT + " TEXT)")

        db.execSQL(createTableUser)
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
            put(TABLE_CLIENT_DNI, dni)
            put(TABLE_CLIENT_NAME, name)
            put(TABLE_CLIENT_SURNAME, surname)
            put(TABLE_CLIENT_EMAIL, email)
            put(TABLE_CLIENT_PHYSICALLYFIT, if (physicallyfit) 1 else 0)
            put(TABLE_CLIENT_ESSOCIO, if (essocio) 1 else 0)
            put(TABLE_CLIENT_NROCLIENT, nroclient)
        }
        val success = db.insert(TABLE_CLIENT, null, values)
        db.close()
        return success
    }

    fun searchClient(dni: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_CLIENT WHERE $TABLE_CLIENT_DNI = ?"
        var exists = false

        val cursor = db.rawQuery(query, arrayOf(dni))
        try {
            exists = cursor.count > 0
        } finally {
            cursor.close()
            db.close()
        }

        return exists
    }
}