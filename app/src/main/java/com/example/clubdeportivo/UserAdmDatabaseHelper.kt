package com.example.clubdeportivo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class UserAdmDatabaseHelper (context:Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)
{
    companion object{
        private val DATABASE_NAME = "CLUBDEPORTIVO.db"
        private val DATABASE_VERSION = 1

        private val TABLE_USER = "User"
        private val COLUMN_USER_ID = "id"
        private val COLUMN_USER_NAME = "name"
        private val COLUMN_USER_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableUser = ("CREATE TABLE " + TABLE_USER + " ("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USER_NAME + " TEXT, "
                + COLUMN_USER_PASSWORD + " TEXT)")

        db.execSQL(createTableUser)

        val resultado = addUser();

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("CREATE TABLE IF EXISTS " + TABLE_USER)
        onCreate(db)
    }

    fun addUser(): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USER_NAME, "usuer@correo.com")
            put(COLUMN_USER_PASSWORD, "123456")
        }
        val success = db.insert(TABLE_USER, null, values)
        return success
    }

    fun checkUser(userName: String, passwordUser: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT 1 FROM user WHERE name = "+ userName +" AND password = " + passwordUser
        var exists = false

        val cursor = db.rawQuery("SELECT * FROM Productos", null)

        try {
            exists = cursor.count > 0
        } finally {
            cursor.close()
            db.close()
        }

        return exists
    }
}