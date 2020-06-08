package com.timesproject.mynotes.database

import android.provider.BaseColumns

object TextNoteDataBaseContract {
    class TextNoteEntry : BaseColumns {
        companion object {
            const val TABLE_NAME = "textNote"
            const val NOTE_ID = "noteId"
            const val NOTE_TEXT = "noteText"
        }
    }
}