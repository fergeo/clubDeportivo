package com.example.clubdeportivo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class FeeDatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "CLUBDEPORTIVO.db"
        private const val DATABASE_VERSION = 5
        private const val TABLE_USER = "Fee"
        private const val COLUMN_ID_FEE = "idFee"
        private const val COLUMN_FEE_ID_CLIENT = "idClientFee"
        private const val COLUMN_FEE_AMOUNT = "amountFee"
        private const val COLUMN_FEE_DETAIL = "detailFee"
        private const val COLUMN_FEE_LIMIT_DATE = "limitDateFee"
        private const val COLUMN_FEE_STATE = "stateFee"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableFee = ("CREATE TABLE $TABLE_USER ("
                + "$COLUMN_ID_FEE INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_FEE_ID_CLIENT INTEGER, "
                + "$COLUMN_FEE_AMOUNT INTEGER, "
                + "$COLUMN_FEE_DETAIL TEXT, "
                + "$COLUMN_FEE_LIMIT_DATE TEXT, "
                + "$COLUMN_FEE_STATE INTEGER)")

        db.execSQL(createTableFee)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USER")
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
        return db.insert(TABLE_USER, null, values)
    }
}