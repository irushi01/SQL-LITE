package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
class StudentDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null,
    DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "StudentDB"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "students"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_MARKS = "marks"
    }
    override fun onCreate(db: SQLiteDatabase) {

        val createTable = ("CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY,$COLUMN_NAME TEXT, $COLUMN_MARKS INTEGER)")
        db.execSQL(createTable)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addStudent(id: Int, name: String, marks: Int): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_ID, id)
            put(COLUMN_NAME, name)
            put(COLUMN_MARKS, marks)
        }
        val result = db.insert(TABLE_NAME, null, contentValues)
        db.close()
        return result != -1L // Returns true if the data is inserted successfully
    }

    fun getStudentById(id: Int): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = ?",
            arrayOf(id.toString()))
    }
}