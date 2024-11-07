package com.example.clubdeportivo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Client {
    var idClient: Int = 0
    var dniClient: String? = null
    var nameClient: String? = null
    var surnameClient: String? = null
    var emailClient: String? = null
    var essocioClient: Int = 0
    var nroClient: String? = null
}

class Fee {
    var idFee: Int = 0
    var idClientFee: Int = 0
    var clubAcivityIdFee: Int = 0
    var limitDateFee: String? = null
    var stateFee: Int = 0
}

class ClubActivity {
    var clubActityId: Int = 0
    var nameClibActivity: String? = null
    var costClubActivity: Int? = 0
}

class PaymentMethod{
    //var idPaymentMethod: Int = 0
    var namePaymentMethod: String? = null
}

class ClubDeportivoDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "CLUBDEPORTIVO.db"
        private const val DATABASE_VERSION = 2

        //Tabla Usuarios Administradores
        private const val TABLE_USER = "User"
        private const val COLUMN_USER_ID = "idUser"
        private const val COLUMN_USER_EMAIL = "emailUser"
        private const val COLUMN_USER_PASSWORD = "passwordUser"

        //Tabla de Socios y No Socios
        private const val TABLE_CLIENT = "Client"
        private const val COLUMN_CLIENT_ID = "idClient"
        private const val COLUMN_CLIENT_DNI = "dniClient"
        private const val COLUMN_CLIENT_NAME = "nameClient"
        private const val COLUMN_CLIENT_SURNAME = "surnameClient"
        private const val COLUMN_CLIENT_EMAIL = "emailClient"
        private const val COLUMN_CLIENT_PHYSICALLYFIT = "physicallyfitClient"
        private const val COLUMN_CLIENT_ESSOCIO = "essocioClient"
        private const val COLUMN_CLIENT_NROCLIENT = "nroClient"

        //Tabla de las Actividades del Club Deportivo
        private const val TABLE_CLUBACTIVITY = "ClubActivities"
        private const val COLUMN_CLUBACTIVITY_ID = "idClubActivity"
        private const val COLUMN_CLUBACTIVITY_NAME = "nameClubActivity"
        private const val COLUMN_CLUBACTIVITY_COST = "costClubActivity"

        //Tabla de las Cuotas
        private const val TABLE_FEE = "Fee"
        private const val COLUMN_FEE_ID = "idFee"
        private const val COLUMN_FEE_ID_CLIENT = "idClientFee"
        private const val COLUMN_FEE_CLUB_ACTIVITY_ID = "clubActicityIdFee"
        private const val COLUMN_FEE_LIMIT_DATE = "limitDateFee"
        private const val COLUMN_FEE_STATE = "stateFee"

        //Tabla de Pagos
        private const val TABLE_PAYFEE = "PayFee"
        private const val COLUMN_PAY_FEE_ID = "idPayFee"
        private const val COLUMN_PAY_FEE_ID_FEE = "idFeePayFee"
        private const val COLUMN_PAY_FEE_AMOUMT = "amountPayFee"
        private const val COLUMN__PAY_FEE_DETAIL = "detailPayFee"
        private const val COLUMN__PAY_FEE_ID_PAYMENT_METHOD = "idPaymentMethodPayFee"
        private const val COLUMN__PAY_FEE_FEES = "fessPayFee"

        //Tabla Forma de Pagos
        private const val TABLE_PAYMENT_METHOD = "PaymentMethod"
        private const val COLUMN_PAYMENT_METHOD_ID = "idPaymentMethod"
        private const val COLUMN_PAYMENT_METHOD_NAME = "namePaymentMethod"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableUser = ("CREATE TABLE " + TABLE_USER + " ("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USER_EMAIL + " TEXT, "
                + COLUMN_USER_PASSWORD + " TEXT)")
        db.execSQL(createTableUser)

        //Creacion de la Tabla de Clientes
        val createTableClient = ("CREATE TABLE " + TABLE_CLIENT + " ("
                + COLUMN_CLIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_CLIENT_DNI + " TEXT, "
                + COLUMN_CLIENT_NAME + " TEXT, "
                + COLUMN_CLIENT_SURNAME + " TEXT, "
                + COLUMN_CLIENT_EMAIL + " TEXT, "
                + COLUMN_CLIENT_PHYSICALLYFIT + " INTEGER, "
                + COLUMN_CLIENT_ESSOCIO + " INTEGER, "
                + COLUMN_CLIENT_NROCLIENT + " TEXT)")
        db.execSQL(createTableClient)

        //Creacion Tabla de Actividades del Club Deportivo
        val createTableActivityClub = ("CREATE TABLE " + TABLE_CLUBACTIVITY + " ("
                + COLUMN_CLUBACTIVITY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_CLUBACTIVITY_NAME + " TEXT, "
                + COLUMN_CLUBACTIVITY_COST + " INTEGER)")
        db.execSQL(createTableActivityClub)

        //Creacion Tabla de Cuotas
        val createTableFee = ("CREATE TABLE " + TABLE_FEE + "("
                + COLUMN_FEE_ID +  " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_FEE_ID_CLIENT + " INTEGER, "
                + COLUMN_FEE_CLUB_ACTIVITY_ID + " INTEGER, "
                + COLUMN_FEE_LIMIT_DATE + " TEXT, "
                + COLUMN_FEE_STATE + " INTEGER)")

        db.execSQL(createTableFee)

        //Creacion Tabla Pago de Cuotas
        val createTablePayFee = ("CREATE TABLE " + TABLE_PAYFEE + "("
                + COLUMN_PAY_FEE_ID +  " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_PAY_FEE_ID_FEE + " INTEGER, "
                + COLUMN_PAY_FEE_AMOUMT + " INTEGER, "
                + COLUMN__PAY_FEE_DETAIL + " TEXT, "
                + COLUMN__PAY_FEE_ID_PAYMENT_METHOD+ " INTEGER, "
                + COLUMN__PAY_FEE_FEES + " INTEGER)")

        db.execSQL(createTablePayFee)

        val createTablePaymentMethod = ("CREATE TABLE ${TABLE_PAYMENT_METHOD} ("
                + "${COLUMN_PAYMENT_METHOD_ID} INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "${COLUMN_PAYMENT_METHOD_NAME} TEXT)")

        db.execSQL(createTablePaymentMethod)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENT)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLUBACTIVITY)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FEE)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PAYFEE)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PAYMENT_METHOD)
        onCreate(db)
    }

//Funciones para administracion de Usuarios
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
        val query = "SELECT 1 FROM User WHERE emailUser = ? AND passwordUser = ?"
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

//Funciones para administracion de Clientes
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
            put(COLUMN_CLIENT_DNI, dni)
            put(COLUMN_CLIENT_NAME, name)
            put(COLUMN_CLIENT_SURNAME, surname)
            put(COLUMN_CLIENT_EMAIL, email)
            put(COLUMN_CLIENT_PHYSICALLYFIT, if (physicallyfit) 1 else 0)
            put(COLUMN_CLIENT_ESSOCIO, if (essocio) 1 else 0)
            put(COLUMN_CLIENT_NROCLIENT, nroclient)
        }
        val success = db.insert(TABLE_CLIENT, null, values)
        db.close()
        return success
    }

    fun searchClient(dni: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM ${TABLE_CLIENT} WHERE ${COLUMN_CLIENT_DNI} = ?"
        val cursor = db.rawQuery(query, arrayOf(dni))
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }

    fun searchSocioByDni(dni: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM ${TABLE_CLIENT} WHERE ${COLUMN_CLIENT_DNI} = ? AND ${COLUMN_CLIENT_ESSOCIO} = 1"
        val cursor = db.rawQuery(query, arrayOf(dni))
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }

    fun listarSocios(): List<Client> {
        val db = this.readableDatabase
        val query = "SELECT * FROM ${TABLE_CLIENT} WHERE ${COLUMN_CLIENT_ESSOCIO} = 1"
        val cursor = db.rawQuery(query, null)
        val clients = mutableListOf<Client>()

        try {
            if (cursor.moveToFirst()) {
                do {
                    val client = Client().apply {
                        this.idClient = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CLIENT_ID))
                        this.dniClient = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLIENT_DNI))
                        this.nameClient = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLIENT_NAME))
                        this.surnameClient = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLIENT_SURNAME))
                        this.emailClient = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLIENT_EMAIL))
                        this.essocioClient = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CLIENT_ESSOCIO))
                        this.nroClient = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLIENT_NROCLIENT))
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

    fun clientData(dni: String): List<Client> {
        val db = this.readableDatabase
        val query = "SELECT * FROM Client WHERE dniClient  = ?"
        val cursor = db.rawQuery(query, arrayOf(dni))
        val clients = mutableListOf<Client>()

        try {
            if (cursor.moveToFirst()) {
                do {
                    val client = Client().apply {
                        this.idClient = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CLIENT_ID))
                        this.dniClient = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLIENT_DNI))
                        this.nameClient = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLIENT_NAME))
                        this.surnameClient = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLIENT_SURNAME))
                        this.emailClient = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLIENT_EMAIL))
                        this.essocioClient = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CLIENT_ESSOCIO))
                        this.nroClient = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLIENT_NROCLIENT))
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

    fun clientDataById(paramIdClient: Int): List<Client> {
        val db = this.readableDatabase
        val query = "SELECT * FROM ${TABLE_CLIENT} WHERE ${COLUMN_CLIENT_ID} = ?"
        val cursor = db.rawQuery(query, arrayOf(paramIdClient.toString()))
        val clients = mutableListOf<Client>()

        try {
            if (cursor.moveToFirst()) {
                do {
                    val client = Client().apply {
                        this.idClient = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CLIENT_ID))
                        this.dniClient = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLIENT_DNI))
                        this.nameClient = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLIENT_NAME))
                        this.surnameClient = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLIENT_SURNAME))
                        this.emailClient = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLIENT_EMAIL))
                        this.essocioClient = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CLIENT_ESSOCIO))
                        this.nroClient = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLIENT_NROCLIENT))
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

//Funciones para administracion de las Actividades del Club Deportivo
    fun addClubActivity(nameClubActivity: String, costClubActivity: Int): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_CLUBACTIVITY_NAME, nameClubActivity)
            put(COLUMN_CLUBACTIVITY_COST, costClubActivity)
        }
        return db.insert(TABLE_CLUBACTIVITY, null, values)
    }

    fun isTableEmpty(): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM ${TABLE_CLUBACTIVITY}", null)
        cursor.moveToFirst()
        val count = cursor.getInt(0)
        cursor.close()
        return count == 0
    }

    fun clubActivutyDataById(paramIdClubActivity: Int): List<ClubActivity> {
        val db = this.readableDatabase
        val query = "SELECT * FROM ${TABLE_CLUBACTIVITY} WHERE ${COLUMN_CLUBACTIVITY_ID} = ?"
        val cursor = db.rawQuery(query, arrayOf(paramIdClubActivity.toString()))
        val clubActivityList = mutableListOf<ClubActivity>()

        try {
            if (cursor.moveToFirst()) {
                do {
                    val clubActivity = ClubActivity().apply {
                        clubActityId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CLUBACTIVITY_ID))
                        nameClibActivity = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLUBACTIVITY_NAME))
                        costClubActivity = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CLUBACTIVITY_COST))
                    }
                    clubActivityList.add(clubActivity)
                } while (cursor.moveToNext())
            }
        } finally {
            cursor.close()
            db.close()
        }

        return clubActivityList
    }

//Funciones para la administracion de Cuotas
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

    fun listFeeData(dateFrom: String,dateTo:String): List<Fee> { // Corrección del tipo a Int
        val db = this.readableDatabase
        val query = "SELECT * FROM ${TABLE_FEE} WHERE ${COLUMN_FEE_STATE} = 0 AND ${COLUMN_FEE_ID_CLIENT} BETWEEN ? AND ?"
        val cursor = db.rawQuery(query, arrayOf(dateFrom, dateTo))
        val fees = mutableListOf<Fee>()

        try {
            if (cursor.moveToFirst()) {
                do {
                    val fee = Fee().apply {
                        this.idFee = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FEE_ID))
                        this.idClientFee = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FEE_ID_CLIENT))
                        this.clubAcivityIdFee = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FEE_CLUB_ACTIVITY_ID))
                        this.limitDateFee = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FEE_LIMIT_DATE))
                        this.stateFee = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FEE_STATE))
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

    fun listFeeDataById(idClientFee: Int): List<Fee> { // Corrección del tipo a Int
        val db = this.readableDatabase
        val query = "SELECT * FROM ${TABLE_FEE} WHERE ${COLUMN_FEE_ID_CLIENT} = ? AND ${COLUMN_FEE_STATE} = 0"
        val cursor = db.rawQuery(query, arrayOf(idClientFee.toString()))
        val fees = mutableListOf<Fee>()

        try {
            if (cursor.moveToFirst()) {
                do {
                    val fee = Fee().apply {
                        this.idFee = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FEE_ID))
                        this.idClientFee = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FEE_ID_CLIENT))
                        this.clubAcivityIdFee = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FEE_CLUB_ACTIVITY_ID))
                        this.limitDateFee = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FEE_LIMIT_DATE))
                        this.stateFee = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FEE_STATE))
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

    fun updateFeeState(idFee: Int) {
        val db = this.writableDatabase
        val newState = 1
        val values = ContentValues().apply {
            put(COLUMN_FEE_STATE, newState)
        }

        // Condición para identificar el registro a actualizar
        val selection = "$COLUMN_FEE_ID = ?"
        val selectionArgs = arrayOf(idFee.toString())

        // Realizar la actualización
        val count = db.update(TABLE_FEE, values, selection, selectionArgs)
        db.close()
    }

//Funciones de administracion de Pagos de Cuotas
    fun addPayFee(
        payEee: Int,
        amountFee: Int,
        detailFee: String,
        idPaymentMethod: Int,
        fees: Int
    ): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_PAY_FEE_ID_FEE, payEee)
            put(COLUMN_PAY_FEE_AMOUMT, amountFee)
            put(COLUMN__PAY_FEE_DETAIL, detailFee)
            put(COLUMN__PAY_FEE_ID_PAYMENT_METHOD, idPaymentMethod)
            put(COLUMN__PAY_FEE_FEES, fees)
        }
        return db.insert(TABLE_PAYFEE, null, values)
    }

// Funciones de administracion de Formas de Pago
    fun addPaymentMehod(namePaymentMethod: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_PAYMENT_METHOD_NAME, namePaymentMethod)
        }
        return db.insert(TABLE_PAYMENT_METHOD, null, values)
    }

    fun idPaymentMethod(name:String): Int { // Corrección del tipo a Int
        val db = this.readableDatabase
        val query = "SELECT ${COLUMN_PAYMENT_METHOD_ID} FROM ${TABLE_PAYMENT_METHOD} WHERE ${COLUMN_PAYMENT_METHOD_NAME} = ?"
        val cursor = db.rawQuery(query, arrayOf(name))
        var idPaymentMehtd = 1

        try {
            if (cursor.moveToFirst()) {
                do {
                    idPaymentMehtd = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PAYMENT_METHOD_ID))
                } while (cursor.moveToNext())
            }
        } finally {
            cursor.close()
            db.close()
        }
        return idPaymentMehtd
    }

    fun listPaymentMethod(): List<PaymentMethod> { // Corrección del tipo a Int
        val db = this.readableDatabase
        val query = "SELECT ${COLUMN_PAYMENT_METHOD_NAME} FROM ${TABLE_PAYMENT_METHOD}"
        val cursor = db.rawQuery(query, null)
        val paymentMethods = mutableListOf<PaymentMethod>()

        try {
            if (cursor.moveToFirst()) {
                do {
                    val paymentMethod = PaymentMethod().apply {
                        this.namePaymentMethod = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PAYMENT_METHOD_NAME))
                    }
                    paymentMethods.add(paymentMethod)
                } while (cursor.moveToNext())
            }
        } finally {
            cursor.close()
            db.close()
        }
        return paymentMethods
    }
}