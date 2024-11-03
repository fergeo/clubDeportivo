package com.example.clubdeportivo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ClientDatabaseHelper (context:Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)
{
    companion object{
        private val DATABASE_NAME = "CLUBDEPORTIVO.db"
        private val DATABASE_VERSION = 1

        private val TABLE_CLIENT = "Client"
        private val TABLE_CLIENT_ID = "idClient"
        private val TABLE_CLIENT_DNI = "dniClient"
        private val TABLE_CLIENT_NAME = "nameClient"
        private val TABLE_CLIENT_SURNAME = "surnameClient"
        private val TABLE_CLIENT_EMAIL = "emailClient"
        private val TABLE_CLIENT_PHYSICALLYFIT = "physicallyfitClient"
        private val TABLE_CLIENT_ESSOCIO = "essocioClient"
        private val TABLE_CLIENT_NROCLIENT = "nroClient"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableUser = ("CREATE TABLE " + TABLE_CLIENT + " ("
                + TABLE_CLIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TABLE_CLIENT_DNI + " TEXT, "
                + TABLE_CLIENT_NAME + " TEXT, "
                + TABLE_CLIENT_SURNAME + " TEXT, "
                + TABLE_CLIENT_EMAIL + " TEXT, "
                + TABLE_CLIENT_PHYSICALLYFIT + " BOOLEAN, "
                + TABLE_CLIENT_ESSOCIO + " BOOLEAN, "
                + TABLE_CLIENT_NROCLIENT + " TEXT)")

        db.execSQL(createTableUser)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("CREATE TABLE IF EXISTS " + TABLE_CLIENT)
        onCreate(db)
    }

    fun addClient(
        dni:String,
        name:String,
        surname:String,
        email:String,
        physicallyfit:Boolean,
        essocio:Boolean,
        nroclient:String
    ): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(TABLE_CLIENT_DNI, dni)
            put(TABLE_CLIENT_NAME, name)
            put(TABLE_CLIENT_SURNAME, surname)
            put(TABLE_CLIENT_EMAIL, email)
            put(TABLE_CLIENT_PHYSICALLYFIT, physicallyfit)
            put(TABLE_CLIENT_ESSOCIO, essocio)
            put(TABLE_CLIENT_NROCLIENT, nroclient)
        }
        val success = db.insert(TABLE_CLIENT, null, values)
        return success
    }

    fun searchClient(dni: String, email : String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM client WHERE name = ? AND password = ?"
        var exists = false

        val cursor = db.rawQuery(query, arrayOf(dni, email))
        try {
            exists = cursor.count > 0
        } finally {
            cursor.close()
            db.close()
        }

        return exists
    }
}