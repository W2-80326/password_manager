package com.sunbeam.password.dbHelper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AccountDbHelper(context: Context): SQLiteOpenHelper(context,"accounts_db",null,1)
{
    companion object{
        private const val table_name = "pass_manager"
        private const val col_id = "id"
        private const val col_acc_type = "acc_type"
        private const val col_acc = "acc"
        private const val col_pass = "pass"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE $table_name ("
                            +"$col_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "$col_acc_type TEXT, " +
                            "$col_acc TEXT, " +
                            "$col_pass TEXT)")
        if (db != null) {
            db.execSQL(createTable)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}