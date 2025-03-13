//Author: Boden Kahn
//Course: CSCI 380
//Due: 11/15/24
package com.example.speedpark

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ParkingAreaDatabaseHelper(applicationContext: Context) : SQLiteOpenHelper(applicationContext, DATABASE_NAME, null, DATABASE_VERSION){
    companion object {
        private const val DATABASE_NAME = "parkingAreas.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "parkingAreas"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_IS_CHECKED = "is_checked"

        @Volatile
        private var INSTANCE: ParkingAreaDatabaseHelper? = null

        // Singleton instance
        fun getInstance(context: Context): ParkingAreaDatabaseHelper {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ParkingAreaDatabaseHelper(context.applicationContext).also {
                    INSTANCE = it
                }
            }
        }
    }

    override fun onCreate(db: SQLiteDatabase){
        val createTableStatement = ("CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_NAME TEXT, $COLUMN_IS_CHECKED INTEGER DEFAULT 0)")
        db.execSQL(createTableStatement)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int){
        // This method is called when the database needs to be upgraded.
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    //Add a parking area to the database
    fun addParkingArea(name: String){
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COLUMN_NAME, name)
        cv.put(COLUMN_IS_CHECKED, false)
        db.insert(TABLE_NAME, null, cv)
    }

    //Get all parking areas as a list of parking areas from the database
    fun getAllParkingAreas(): List<ParkingArea> {
        val itemList = mutableListOf<ParkingArea>()
        val db = this.readableDatabase
        //Create the cursor for the database
        val cursor: Cursor = db.query(
            TABLE_NAME, arrayOf(COLUMN_ID, COLUMN_NAME, COLUMN_IS_CHECKED),
            null, null, null, null, null
        )
        if (cursor.moveToFirst()) {
            do {
                val parkingAreaIdIndex = cursor.getColumnIndex(COLUMN_ID)
                val parkingAreaNameIndex = cursor.getColumnIndex(COLUMN_NAME)
                val isCheckedIndex = cursor.getColumnIndex(COLUMN_IS_CHECKED)
                // Check if the indexes are valid
                if (parkingAreaIdIndex != -1 && parkingAreaNameIndex != -1 && isCheckedIndex != -1) {
                    val id = cursor.getInt(parkingAreaIdIndex)
                    val parkingAreaName = cursor.getString(parkingAreaNameIndex)
                    val isChecked = cursor.getInt(isCheckedIndex)==1
                    val parkingArea = ParkingArea(id, parkingAreaName, isChecked)
                    //Add the item to the list
                    itemList.add(parkingArea)
                }
            } while (cursor.moveToNext())
        }
        cursor.close()
        return itemList
    }

    // Update parking area's checked state in the database
    fun updateParkingArea(id: Int, isChecked: Boolean) {
        val db = writableDatabase
        val values = ContentValues()
        values.put(COLUMN_IS_CHECKED, if (isChecked) 1 else 0)
        db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }

    //Delete a parking area from the database
    fun deleteParkingArea(id: Int){
        val db = writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }
}