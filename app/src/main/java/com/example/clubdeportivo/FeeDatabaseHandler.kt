package com.example.clubdeportivo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class Fee {
    var idFee: Int = 0
    var idClient: Int = 0
    var amountFeetFee: String? = null
    var detailFee: String? = null
    var limitDateFee: String? = null
    var stateFee: Int = 0
}

class FeeDatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "CLUBDEPORTIVO.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_FEE = "Fee"
        private const val COLUMN_ID_FEE = "idFee"
        private const val COLUMN_FEE_ID_CLIENT = "idClientFee"
        private const val COLUMN_FEE_AMOUNT = "amountFee"
        private const val COLUMN_FEE_DETAIL = "detailFee"
        private const val COLUMN_FEE_LIMIT_DATE = "limitDateFee"
        private const val COLUMN_FEE_STATE = "stateFee"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableFee = ("CREATE TABLE $TABLE_FEE ("
                + "$COLUMN_ID_FEE INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_FEE_ID_CLIENT INTEGER, "
                + "$COLUMN_FEE_AMOUNT INTEGER, "
                + "$COLUMN_FEE_DETAIL TEXT, "
                + "$COLUMN_FEE_LIMIT_DATE TEXT, "
                + "$COLUMN_FEE_STATE INTEGER)")

        db.execSQL(createTableFee)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_FEE")
        onCreate(db)
    }

    fun addFee(
        idClientFee: Int,
        amountFee: Int,
        detailFee: String,
        limitDateFee: String,
        stateFee: Int
    ): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_FEE_ID_CLIENT, idClientFee)
            put(COLUMN_FEE_AMOUNT, amountFee)
            put(COLUMN_FEE_DETAIL, detailFee)
            put(COLUMN_FEE_LIMIT_DATE, limitDateFee)
            put(COLUMN_FEE_STATE, stateFee)
        }
        return db.insert(TABLE_FEE, null, values)
    }

    fun clientData(idClient: Int): List<Fee> { // Corrección del tipo a Int
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_FEE WHERE $COLUMN_FEE_ID_CLIENT = ?" // Corrección a `TABLE_FEE`
        val cursor = db.rawQuery(query, arrayOf(idClient.toString()))
        val fees = mutableListOf<Fee>()

        try {
            if (cursor.moveToFirst()) {
                do {
                    val fee = Fee().apply {
                        idFee = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID_FEE))
                        this.idClient = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FEE_ID_CLIENT))
                        amountFeetFee = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FEE_AMOUNT))
                        detailFee = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FEE_DETAIL))
                        limitDateFee = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FEE_LIMIT_DATE))
                        stateFee = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FEE_STATE))
                    }
                    fees.add(fee)
                } while (cursor.moveToNext())
            }
        } finally {
            cursor.close()
            db.close()
        }

        return fees
    }
}