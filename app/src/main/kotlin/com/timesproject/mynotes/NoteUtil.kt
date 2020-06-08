package com.timesproject.mynotes

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

class NoteUtil {
    companion object Factory {
        @JvmStatic
        fun generateNoteId() : String? {
            return SimpleDateFormat("yyyyMMDDHHmmss", Locale.US).format(Date())
        }

    }

}