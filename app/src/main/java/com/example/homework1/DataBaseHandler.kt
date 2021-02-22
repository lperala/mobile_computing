package com.example.homework1

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import java.util.*
import kotlin.collections.ArrayList

val DATABASE_NAME = "HW1DB"
val TABLE_NAME = "users"
val COL_ID = "id"
val COL_USERNAME = "username"
val COL_PASSWORD = "password"

val TABLE_NAME_MESSAGE = "messages"
val COL_ID_MESSAGE = "id_message"
val COL_MESSAGE = "message"
val COL_LOCATION_X = "location_x"
val COL_LOCATION_Y = "location_y"
val COL_REMINDER_TIME = "reminder_time"
val COL_CREATION_TIME = "creation_time"
val COL_CREATOR_ID = "creator_id"
val COL_REMINDER_SEEN = "reminder_seen"

public var loggedAs = ""



//Test
class DataBaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + TABLE_NAME + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_USERNAME + " VARCHAR(256)," + COL_PASSWORD + " INTEGER)";
        val createTableMessage = "CREATE TABLE " + TABLE_NAME_MESSAGE + " (" + COL_ID_MESSAGE + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_MESSAGE + " VARCHAR(256)," + COL_LOCATION_X + " VARCHAR(256)," +
                COL_LOCATION_Y + " VARCHAR(256)," + COL_REMINDER_TIME +  " VARCHAR(256)," + COL_CREATION_TIME + " VARCHAR(256)," + COL_CREATOR_ID + " VARCHAR(256)," + COL_REMINDER_SEEN + " INTEGER)";

        db?.execSQL(createTable)
        db?.execSQL(createTableMessage)
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

    fun insertMessage(messages: Messages){
        val dbMessages = this.writableDatabase
        val db = DataBaseHandler(context)
        val data = db.readDataMessages()
        var cvMessages = ContentValues()

        //cvMessages.put(COL_ID_MESSAGE, final_id)
        cvMessages.put(COL_MESSAGE, messages.message)
        cvMessages.put(COL_LOCATION_X, messages.location_x)
        cvMessages.put(COL_LOCATION_Y, messages.location_y)
        cvMessages.put(COL_REMINDER_TIME, messages.reminder_time)
        cvMessages.put(COL_CREATION_TIME, messages.creation_time)
        cvMessages.put(COL_CREATOR_ID, loggedAs)
        cvMessages.put(COL_REMINDER_SEEN, messages.reminder_seen)

        var resultMessages = dbMessages.insert(TABLE_NAME_MESSAGE, null, cvMessages)
        if(resultMessages == (-1).toLong()) {
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

    fun readDataMessages() : MutableList<Messages>{
        var list: MutableList<Messages> = ArrayList()

        val dbMessage = this.readableDatabase
        val query = "Select * from $TABLE_NAME_MESSAGE"
        val result = dbMessage.rawQuery(query, null)
        if (result.moveToFirst()){
            do {
                var messages = Messages()
                // ADD COLUMNS HERE
                messages.message = result.getString(result.getColumnIndex(COL_MESSAGE))
                messages.reminder_time = result.getString(result.getColumnIndex(COL_REMINDER_TIME))
                messages.creator_id = result.getString(result.getColumnIndex(COL_CREATOR_ID))
                list.add(messages)
            }while (result.moveToNext())
        }
        result.close()
        dbMessage.close()
        return list
    }
    /*
    fun updateDataMessages(){
        val dbMessage = this.readableDatabase
        val query = "Select * from $TABLE_NAME_MESSAGE"
        val result = dbMessage.rawQuery(query, null)
        if (result.moveToFirst()){
            do {
                var cv = ContentValues()
                cv.put(COL_ID_MESSAGE, result.getInt(result.getColumnIndex(COL_ID_MESSAGE)))
                dbMessage.update(TABLE_NAME_MESSAGE, cv, COL_ID_MESSAGE +  "=? AND " + COL_MESSAGE)
            }while (result.moveToNext())
        }
        result.close()
        dbMessage.close()
    }
     */

    fun getDataMessages() : List<ListMessages>{
        var data = this.readDataMessages()
        val list = mutableListOf<ListMessages>()

        for (i in 0 until data.size){
            if (data[i].creator_id == loggedAs){
                list.add(ListMessages(2, data[i].message,"3"))
            }
        }


        return list
    }

    fun deleteDataMessages() {
        val db = this.writableDatabase
        db.delete(TABLE_NAME_MESSAGE, COL_ID_MESSAGE + "=" + "1", null)
        db.close()
    }

}