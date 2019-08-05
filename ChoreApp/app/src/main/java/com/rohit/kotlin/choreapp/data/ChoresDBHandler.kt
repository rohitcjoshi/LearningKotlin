package com.rohit.kotlin.choreapp.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.rohit.kotlin.choreapp.model.*
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChoresDBHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_CHORE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_CHORE_NAME + " TEXT," +
                KEY_CHORE_ASSIGNED_BY + " TEXT," +
                KEY_CHORE_ASSIGNED_TO + " TEXT," +
                KEY_CHORE_ASSIGNED_TIME + " LONG" + ")"

        db?.execSQL(CREATE_CHORE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)

        // create table again
        onCreate(db)
    }

    /**
     * CRUD - Create, Read, Update, Delete
     *
     * Create chore and put it in DB
     */
    fun createChore(chore: Chore) {
        var db: SQLiteDatabase = writableDatabase
        var values: ContentValues = ContentValues()
        values.put(KEY_CHORE_NAME, chore.choreName)
        values.put(KEY_CHORE_ASSIGNED_BY, chore.assignedBy)
        values.put(KEY_CHORE_ASSIGNED_TO, chore.assignedTo)
        values.put(KEY_CHORE_ASSIGNED_TIME, System.currentTimeMillis())

        db.insert(TABLE_NAME, null, values)

        Log.d("DB_TAG", " Data inserted..!")
        db.close()
    }

    /**
     * Read specified chore from DB of given ID
     */
    fun readAChore(id: Int): Chore? {
        val db: SQLiteDatabase = writableDatabase
        val cursor: Cursor = db.query(
            TABLE_NAME, arrayOf(
                KEY_ID,
                KEY_CHORE_NAME, KEY_CHORE_ASSIGNED_BY, KEY_CHORE_ASSIGNED_TO, KEY_CHORE_ASSIGNED_TIME
            ), KEY_ID + "=?", arrayOf(id.toString()), null, null, null, null
        )

        if (cursor != null)
            cursor.moveToFirst()

        val chore = Chore()
        chore.choreName = cursor.getString(cursor.getColumnIndex(KEY_CHORE_NAME))
        chore.assignedTo = cursor.getString(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TO))
        chore.assignedBy = cursor.getString(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_BY))
        chore.timeAssigned = cursor.getLong(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TIME))

        val dateFormat: DateFormat = DateFormat.getDateInstance()
        // Formatted Date: Feb 24, 2019
        var formattedDate = dateFormat.format(Date(cursor.getLong(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TIME))).time)
        cursor.close()

        return chore

    }

    /**
     * Read all chores from DB
     */
    fun readAllChores(): ArrayList<Chore> {
        val choreList: ArrayList<Chore> = ArrayList()

        val db: SQLiteDatabase = writableDatabase

        val selectAll = "SELECT * FROM " + TABLE_NAME
        val cursor: Cursor = db.rawQuery(selectAll, null)
        if (cursor.moveToFirst()) {
            do {
                val chore = Chore()
                chore.choreName = cursor.getString(cursor.getColumnIndex(KEY_CHORE_NAME))
                chore.assignedTo = cursor.getString(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TO))
                chore.assignedBy = cursor.getString(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_BY))
                chore.timeAssigned = cursor.getLong(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TIME))

                choreList.add(chore)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return choreList
    }

    /**
     * Update chore in DB
     */
    fun updateChore(chore: Chore): Int {
        val db:SQLiteDatabase = writableDatabase

        val values = ContentValues()
        values.put(KEY_CHORE_NAME, chore.choreName)
        values.put(KEY_CHORE_ASSIGNED_BY, chore.assignedBy)
        values.put(KEY_CHORE_ASSIGNED_TO, chore.assignedTo)
        values.put(KEY_CHORE_ASSIGNED_TIME, System.currentTimeMillis())

        // Update a row
        return db.update(TABLE_NAME, values, KEY_ID + "=?", arrayOf(chore.id.toString()))
    }

    /**
     * Delete chore from DB
     */
    fun deleteChore(chore: Chore) {
        val db: SQLiteDatabase = writableDatabase
        db.delete(TABLE_NAME, KEY_ID + "=?", arrayOf(chore.id.toString()))
        db.close()
    }

    /**
     * Get count of chores stored in DB
     */
    fun getChoresCount(): Int {
        var countQuery = ""

        return 0
    }
}