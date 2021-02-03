package com.example.homework1

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val DATABASE_NAME = "HW1DB"
val TABLE_NAME = "users"
//val COL_ID = "id"
val COL_USERNAME = "username"
val COL_PASSWORD = "password"

class DataBaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLE_NAME ($COL_USERNAME$COL_PASSWORD )"

        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    // Used to insert data into the database
    fun insertData(user : User){
        val db = this.writableDatabase
        var cv = ContentValues()
        //cv.put(COL_ID, user.id)
        cv.put(COL_USERNAME, user.userName)
        cv.put(COL_PASSWORD, user.passWord)
        var result = db.insert(TABLE_NAME, null, cv)
        if(result == (-1).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
            //user.id += 1
        }
    }

    // Read data from the database, currently used for testing purposes
    fun readData() : MutableList<User> {
        var list: MutableList<User> = ArrayList()

        val db = this.readableDatabase
        val query = "Select * from $TABLE_NAME"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                var user = User()
                //user.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                user.userName = result.getString(result.getColumnIndex(COL_USERNAME))
                user.passWord = result.getString(result.getColumnIndex(COL_PASSWORD))
                list.add(user)
            }while (result.moveToNext())
        }

        result.close()
        db.close()
        return list
    }
}