package com.techjd.chatapp.utils

import java.lang.Exception
import java.text.SimpleDateFormat

object MyUtils {
    fun convertInto12(time: String): String {
        try {
            val displayFormat = SimpleDateFormat("HH:mm")
            val parseFormat = SimpleDateFormat("hh:mm a")
            val date1 = displayFormat.parse(time)
            return parseFormat.format(date1)
        } catch (e: Exception) {
            return e.toString()
        }
    }
}