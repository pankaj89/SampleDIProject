package com.master.basediproject.extensions

import java.text.SimpleDateFormat
import java.util.*


const val DATE_FORMAT_INPUT = "dd/MM/yyyy"
const val DATE_FORMAT_OUTPUT = "dd-MM-yyyy"

fun String.getFormatedDate(
    inputFormat: String = DATE_FORMAT_INPUT,
    outputFormat: String = DATE_FORMAT_OUTPUT
): String {
    val inputDate = SimpleDateFormat(inputFormat, Locale.getDefault()).parse(this)
    return SimpleDateFormat(outputFormat, Locale.getDefault()).format(inputDate)
}

fun Long.getFormatedDate(
    outputFormat: String = DATE_FORMAT_OUTPUT
): String {
    return SimpleDateFormat(outputFormat, Locale.getDefault()).format(Date(this))
}


fun String.getDate(inputFormat: String = DATE_FORMAT_INPUT): Date {
    return SimpleDateFormat(inputFormat, Locale.getDefault()).parse(this)
}