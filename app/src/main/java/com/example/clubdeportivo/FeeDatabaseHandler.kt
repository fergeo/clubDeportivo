package com.example.clubdeportivo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.hardware.camera2.CameraExtensionSession.StillCaptureLatency

class FeeDatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private val DATABASE_NAME = "CLUBDEPORTIVO.db"
        private val DATABASE_VERSION = 1
        private val TABLE_FEE = "Fee"
        private val COLUMN_ID_FEE = "idFee"
        private val COLUMN_FEE_ID_CLIENT = "idClientFee"
        private val COLUMN_FEE_CLUB_ACTIVITY_ID = "clubActicityIdFee"
        private val COLUMN_FEE_LIMIT_DATE = "limitDateFee"
        private val COLUMN_FEE_STATE = "stateFee"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableFee = ("CREATE TABLE $TABLE_FEE ("
                + "$COLUMN_ID_FEE INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_FEE_ID_CLIENT INTEGER, "
                + "$COLUMN_FEE_CLUB_ACTIVITY_ID INTEGER, "
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
        clubActivityId: Int,
        limitDateFee: String
    ): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_FEE_ID_CLIENT, idClientFee)
            put(COLUMN_FEE_CLUB_ACTIVITY_ID, clubActivityId)
            put(COLUMN_FEE_LIMIT_DATE, limitDateFee)
            put(COLUMN_FEE_STATE, 0)
        }
        return db.insert(TABLE_FEE, null, values)
    }

    fun feeData(idClient: Int): List<Fee> { // Corrección del tipo a Int
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_FEE WHERE $COLUMN_FEE_ID_CLIENT = ? AND $COLUMN_FEE_STATE = 0"
        val cursor = db.rawQuery(query, arrayOf(idClient.toString()))
        val fees = mutableListOf<Fee>()

        try {
            if (cursor.moveToFirst()) {
                do {
                    val fee = Fee().apply {
                        idFee = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID_FEE))
                        this.idClientFee = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FEE_ID_CLIENT))
                        clubAcivityIdFee = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FEE_CLUB_ACTIVITY_ID))
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

    fun listFeeData(dateFrom: String,dateTo:String): List<Fee> { // Corrección del tipo a Int
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_FEE WHERE $COLUMN_FEE_ID_CLIENT BETWEEN ? AND ?"
        val cursor = db.rawQuery(query, arrayOf(dateFrom, dateTo))
        val fees = mutableListOf<Fee>()

        try {
            if (cursor.moveToFirst()) {
                do {
                    val fee = Fee().apply {
                        idFee = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID_FEE))
                        this.idClientFee = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FEE_ID_CLIENT))
                        clubAcivityIdFee = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FEE_CLUB_ACTIVITY_ID))
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

    fun changeStateFee(idClientFee: Int): Int {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_FEE_ID_CLIENT, idClientFee)
        }
        return db.update(TABLE_FEE, contentValues, "$COLUMN_FEE_ID_CLIENT = ?", arrayOf(idClientFee.toString()))
    }
}