package com.example.clubdeportivo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class PayFeeDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "CLUBDEPORTIVO.db"
        private const val DATABASE_VERSION = 5
        private const val TABLE_USER = "PayFee"
        private const val COLUMN_PAY_FEE_ID = "idPayFee"
        private const val COLUMN_PAY_FEE_ID_FEE = "idFeePayFee"
        private const val COLUMN_PAY_FEE_AMOUMT = "amountPayFee"
        private const val COLUMN__PAY_FEE_DETAIL = "detailPayFee"
        private const val COLUMN__PAY_FEE_ID_PAYMENT_METHOD = "idPaymentMethodPayFee"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableFee = ("CREATE TABLE $TABLE_USER ("
                + "$COLUMN_PAY_FEE_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_PAY_FEE_ID_FEE INTEGER, "
                + "$COLUMN_PAY_FEE_AMOUMT INTEGER, "
                + "$COLUMN__PAY_FEE_DETAIL TEXT, "
                + "$COLUMN__PAY_FEE_ID_PAYMENT_METHOD INTEGER, ")

        db.execSQL(createTableFee)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USER")
        onCreate(db)
    }

    fun addPayFee(
        idFee: Int,
        amountFee: Int,
        detailFee: String,
        idPaymentMethod: Int
    ): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_PAY_FEE_ID_FEE, idFee)
            put(COLUMN_PAY_FEE_AMOUMT, amountFee)
            put(COLUMN__PAY_FEE_DETAIL, detailFee)
            put(COLUMN__PAY_FEE_ID_PAYMENT_METHOD, idPaymentMethod)
        }
        return db.insert(TABLE_USER, null, values)
    }
}