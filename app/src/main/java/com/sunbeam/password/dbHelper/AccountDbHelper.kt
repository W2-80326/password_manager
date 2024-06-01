package com.sunbeam.password.dbHelper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.sunbeam.password.entity.Account

class AccountDbHelper(context: Context): SQLiteOpenHelper(context,"accounts_db",null,1)
{
    companion object{
        private const val table_name = "pass_manager"
        private const val col_id = "id"
        private const val col_acc_type = "acc_type"
        private const val col_acc = "acc"
        private const val col_pass = "pass"
        private const val col_iv = "iv"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE $table_name ("
                            +"$col_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "$col_acc_type TEXT, " +
                            "$col_acc TEXT, " +
                            "$col_pass TEXT, " +
                            "$col_iv TEXT)")
        if (db != null) {
            db.execSQL(createTable)
        }
    }

    fun insertAcc(account : Account) : Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(col_acc_type,account.acc_type)
        contentValues.put(col_acc,account.acc)
        contentValues.put(col_pass,account.pass)
        contentValues.put(col_iv,account.iv)
        val success = db.insert(table_name,null,contentValues)
        db.close()
        return success
    }

    fun getDatas() : List<Account>{
        val accountList = mutableListOf<Account>()
//        val query =
        val db = this.readableDatabase
        val cursor = db.query(table_name,null,null,null,null,null,null)
        while (cursor.moveToNext()){
            val account = Account()
            account.id = cursor.getInt(0)
            account.acc_type = cursor.getString(1)
            account.acc = cursor.getString(2)
            account.pass = cursor.getString(3)
            account.iv = cursor.getString(4)
            accountList.add(account)
        }
        cursor.close()
        return accountList
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        return
    }

    fun onUpdate(account: Account): Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(col_acc,account.acc)
        contentValues.put(col_pass,account.pass)
        val success = db.update(table_name,contentValues,"$col_id=?", arrayOf(account.id.toString()))
        db.close()
        return success
    }

    fun onDelete(id: Int): Int{
        val db = this.writableDatabase
        val success = db.delete(table_name,"$col_id=?", arrayOf(id.toString()))
        db.close()
        return success
    }
}