package com.timesproject.mynotes.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.timesproject.mynotes.model.TextNote

class TextNoteDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "MyNote.db"

        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TextNoteDataBaseContract.TextNoteEntry.TABLE_NAME + " (" +
                    TextNoteDataBaseContract.TextNoteEntry.NOTE_ID + " TEXT PRIMARY KEY," +
                    TextNoteDataBaseContract.TextNoteEntry.NOTE_TEXT + " TEXT)"

        private val SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TextNoteDataBaseContract.TextNoteEntry.TABLE_NAME
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //right now just removing the whole table. we can update the database version here.
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    @Throws(SQLiteConstraintException::class)
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //just updating the database again, can perform some other operation in case of version downgrade
        onUpgrade(db, oldVersion, newVersion)
    }

    fun saveNoteInDB(textNote: TextNote): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()

        values.put(TextNoteDataBaseContract.TextNoteEntry.NOTE_ID, textNote.noteId)
        values.put(TextNoteDataBaseContract.TextNoteEntry.NOTE_TEXT, textNote.noteText)

        // Insert the new row, returning the primary key value of the new row
        db.insert(TextNoteDataBaseContract.TextNoteEntry.TABLE_NAME, null, values)

        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteTextNote(noteId: String): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase
        // Define 'where' part of query.
        val selection = TextNoteDataBaseContract.TextNoteEntry.NOTE_ID + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(noteId)
        // Issue SQL statement.
        db.delete(TextNoteDataBaseContract.TextNoteEntry.TABLE_NAME, selection, selectionArgs)

        return true
    }

    @SuppressLint("Recycle")
    fun readTextNote(noteId: String?): ArrayList<TextNote>? {
        val noteList = ArrayList<TextNote>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            noteId?.let {
                cursor = db.rawQuery(
                    "select * from " + TextNoteDataBaseContract.TextNoteEntry.TABLE_NAME
                            + " WHERE " + TextNoteDataBaseContract.TextNoteEntry.NOTE_ID
                            + "='" + it + "'", null
                )
            }

        } catch (e: SQLiteException) {
            // if table not yet present, create it
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var noteText: String
        cursor?.let {
            if (it.moveToFirst()) {
                while (!it.isAfterLast) {
                    noteText =
                        it.getString(it.getColumnIndex(TextNoteDataBaseContract.TextNoteEntry.NOTE_TEXT))
                    noteList.add(TextNote(noteId, noteText))
                    it.moveToNext()
                }
            }
            return noteList
        }
        return null
    }

    @SuppressLint("Recycle")
    fun readAllNotes(): ArrayList<TextNote>? {
        val noteList = ArrayList<TextNote>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(
                "select * from " + TextNoteDataBaseContract.TextNoteEntry.TABLE_NAME,
                null
            )
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }
        var noteId: String
        var noteText: String
        cursor?.let {
            if (it.moveToFirst()) {
                while (!it.isAfterLast) {
                    noteId =
                        cursor.getString(cursor.getColumnIndex(TextNoteDataBaseContract.TextNoteEntry.NOTE_ID))
                    noteText =
                        cursor.getString(cursor.getColumnIndex(TextNoteDataBaseContract.TextNoteEntry.NOTE_TEXT))

                    noteList.add(TextNote(noteId, noteText))
                    cursor.moveToNext()
                }

            }
            return noteList
        }
        return null
    }
}