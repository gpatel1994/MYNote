package com.timesproject.mynotes.util

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

class NoteUtil {
    companion object Factory {
        @JvmStatic
        fun generateNoteId() : String? {
            return SimpleDateFormat("yyyyMMDDHHmmss", Locale.US).format(Date())
        }

        @JvmStatic
        fun getFirstNthCharactersFromText(text: String?, numCharacter: Int = 10): String? {
            return if (text.isNullOrEmpty()) {
                null
            } else {
                if (text.length > numCharacter) text.substring(
                    0,
                    numCharacter
                ) + "..." else text
            }
        }
    }
}