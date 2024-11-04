package com.example.PaymentMethodDatabaseHelper

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class UserAdmDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "CLUBDEPORTIVO.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_USER = "PaymentMethod"
        private const val COLUMN_PAYMENT_METHOD_ID = "idPaymentMethod"
        private const val COLUMN_PAYMENT_METHOD_NAME = "namePaymentMethod"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableUser = ("CREATE TABLE $TABLE_USER ("
                + "$COLUMN_PAYMENT_METHOD_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_PAYMENT_METHOD_NAME TEXT)")

        db.execSQL(createTableUser)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USER")
        onCreate(db)
    }

    fun addUser(namePaymentMethod: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_PAYMENT_METHOD_NAME, namePaymentMethod)
        }
        return db.insert(TABLE_USER, null, values)
    }

    @SuppressLint("Range")
    fun allPaymentMethods(): List<String> {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_USER"
        val cursor = db.rawQuery(query, null)

        val paymentMethods = mutableListOf<String>() // Lista para almacenar los métodos de pago
        try {
            if (cursor.moveToFirst()) {
                do {
                    val methodName = cursor.getString(cursor.getColumnIndex(COLUMN_PAYMENT_METHOD_NAME))
                    paymentMethods.add(methodName)
                } while (cursor.moveToNext())
            }
        } finally {
            cursor.close()
            db.close()
        }
        return paymentMethods
    }
}