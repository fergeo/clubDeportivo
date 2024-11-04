package com.example.clubdeportivo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.app.AlertDialog


class UserAdmDatabaseHelper (context:Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)
{
    companion object{
        private val DATABASE_NAME = "CLUBDEPORTIVO.db"
        private val DATABASE_VERSION = 2

        private val TABLE_USER = "User"
        private val COLUMN_USER_ID = "idUser"
        private val COLUMN_USER_EMAIL = "emailUser"
        private val COLUMN_USER_PASSWORD = "passwordUser"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableUser = ("CREATE TABLE " + TABLE_USER + " ("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USER_EMAIL + " TEXT, "
                + COLUMN_USER_PASSWORD + " TEXT)")

        db.execSQL(createTableUser)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER)
        onCreate(db)
    }

    fun addUser(nameUser:String, passwordUser:String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USER_EMAIL, nameUser)
            put(COLUMN_USER_PASSWORD, passwordUser)
        }
        val success = db.insert(TABLE_USER, null, values)
        return success
    }

    fun checkUser(userName: String, passwordUser: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT 1 FROM user WHERE emailUser = ? AND passwordUser = ?"
        val exists: Boolean

        val cursor = db.rawQuery(query, arrayOf(userName, passwordUser))

        try {
            exists = cursor.moveToFirst()
        } finally {
            cursor.close()
            db.close()
        }

        return exists
    }
}