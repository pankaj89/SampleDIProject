package com.master.basediproject.extensions

import java.text.SimpleDateFormat
import java.util.*


const val DATE_FORMAT_INPUT = "dd/MM/yyyy"
const val DATE_FORMAT_OUTPUT = "dd-MM-yyyy"
const val DATE_FORMAT_FILE = "dd/MM/yyyy HH:mm"

const val TIME_FORMAT_INPUT = "HH:mm"
const val TIME_FORMAT_OUTPUT = "hh:mm a"

fun String?.getFormatedDate(
    inputFormat: String = DATE_FORMAT_INPUT,
    outputFormat: String = DATE_FORMAT_OUTPUT
): String {
    return try {
        var outFormat = outputFormat
        if (outFormat.contains("'st'")) {
            val inputDate = SimpleDateFormat(inputFormat, Locale.getDefault()).parse(this)
            val date = SimpleDateFormat("dd", Locale.getDefault()).format(inputDate)
            val suffix = getSuffixForDates(date.parseInt(0))
            outFormat = outFormat.replace("st", suffix)
        }
        val inputDate = SimpleDateFormat(inputFormat, Locale.getDefault()).parse(this)
        SimpleDateFormat(outFormat, Locale.getDefault()).format(inputDate)
    } catch (e: Exception) {
        this ?: ""
    }
}

fun String?.getTimestamp(
    inputFormat: String = DATE_FORMAT_INPUT
): Long {
    return try {
        val date = SimpleDateFormat(inputFormat, Locale.getDefault()).parse(this)
        date.time
    } catch (e: Exception) {
        System.currentTimeMillis()
    }
}

fun Long.getFormatedDate(
    outputFormat: String = DATE_FORMAT_OUTPUT
): String {
    return SimpleDateFormat(outputFormat, Locale.getDefault()).format(Date(this))
}


fun String.getDate(inputFormat: String = DATE_FORMAT_INPUT): Date {
    return SimpleDateFormat(inputFormat, Locale.getDefault()).parse(this)
}

private fun getSuffixForDates(day: Int): String {
    val suffix = arrayOf("th", "st", "nd", "rd")
    var last = day % 10
    if (last >= 4 || day in 11..13) {
        last = 0
    }
    return suffix[last]
}