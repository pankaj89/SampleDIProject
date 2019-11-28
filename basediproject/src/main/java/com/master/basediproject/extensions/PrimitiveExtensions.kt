package com.master.basediproject.extensions

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import androidx.annotation.ColorInt
import com.google.gson.JsonObject
import org.json.JSONObject
import java.lang.Exception
import java.math.BigDecimal
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


fun String.showToast(context: Context) {
    android.widget.Toast.makeText(context, this, android.widget.Toast.LENGTH_LONG).show()
}

fun Long.toServerTimeFormat(): String {
    val seconds = this / 1000
    val m = seconds / 60 % 60
    val h = seconds / (60 * 60) % 24
    return String.format("%02d:%02d", h, m)
}

fun Int.secondToMinSec(): String {
    val seconds = this
    val s = seconds % 60
    val m = seconds / 60 % 60
    val h = seconds / (60 * 60) % 24
    return String.format("%02d:%02d", m, s)
}

fun String.toTimeStamp(inputFormat: String): Long {
    try {
        val inputDateFormat = SimpleDateFormat(inputFormat)
        val date = inputDateFormat.parse(this)
        return date.time
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return System.currentTimeMillis()
}

fun String?.stringIfBlank(value: String): String {
    return if (this?.isNotBlank() == true) this else value
}


/*fun String.formatDate(inputFormat: String, outputFormat: String): String {
    try {
        val inputDateFormat = SimpleDateFormat(inputFormat)
        val date = inputDateFormat.parse(this)
        val simpleDateFormat = SimpleDateFormat(outputFormat)
        return simpleDateFormat.format(date)
    } catch (e: ParseException) {
//        e.printStackTrace()
    }
    return this
}*/

fun Long.toServerDateFormat(): String {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    return simpleDateFormat.format(this)
}

fun Long.toServerDateTimeFormat(): String {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
    return simpleDateFormat.format(this)
}

fun Bundle.putStringMe(key: String, value: String): Bundle {
    putString(key, value)
    return this
}

fun parseInteger(value: String?): Int {
    val pattern = Pattern.compile("^\\d{1,}\$")
    if (pattern.matcher(value).matches() == true) {
        return value!!.toInt()
    } else {
        return 0
    }
}

fun parseFloat(value: String?): Float {
    val pattern = Pattern.compile("^\\d{1,}(.{0,1}\\d{1,}){0,1}\$")
    if (pattern.matcher(value).matches() == true) {
        return value!!.toFloat()
    } else {
        return 0f
    }
}

fun String?.parseBoolean(): Boolean {
    return if ("1".equals(this)) {
        true
    } else if ("yes".equals(this, ignoreCase = true)) {
        true
    } else if ("true".equals(this, ignoreCase = true)) {
        true
    } else if ("0".equals(this)) {
        false
    } else if (this.isNullOrBlank()) {
        false
    } else {
        this.toBoolean()
    }
}

fun Boolean.parseString(): String {
    return if (this) {
        "Yes"
    } else
        "No"
}

fun String?.parseColor(@ColorInt defaultColor: Int = 0): Int {

    try{
        return Color.parseColor(this)
    }
    catch (e:Exception){
        return defaultColor
    }
}

fun String?.parseInt(defaultVal: Int = 0): Int {

    return if (this.isNullOrBlank()) {
        defaultVal
    } else if (this?.isInt() == true) {
        this.toInt() ?: defaultVal
    } else {
        defaultVal
    }
}

fun String?.parseFloat(defaultVal: Float = 0f): Float {

    return if (this.isNullOrBlank()) {
        defaultVal
    } else if (this?.isFloat() == true) {
        this?.toFloat() ?: defaultVal
    } else {
        defaultVal
    }
}

fun String.isInt(): Boolean {
    return this.matches(regex = "\\d*".toRegex())
}

fun String.isFloat(): Boolean {
    return this.matches(regex = "[+-]?([0-9]*[.])?[0-9]+".toRegex())
}

fun parseDouble(value: String?): Double {
    val pattern = Pattern.compile("^[-]{0,1}\\d{1,}(.{0,1}\\d{1,}){0,1}$")
    if (pattern.matcher(value).matches() == true) {
        return value!!.toDouble()
    } else {
        return 0.00
    }
}

fun String?.versionToNumber(): Int {
    return this?.replace(".", "").parseInt(0)
}

fun JsonObject.stringToBoolean(key: String) {
    addProperty(key, get(key)?.asString.parseBoolean())
}

fun String.toCapSentence(): String {
    if (!TextUtils.isEmpty(this)) {
        return substring(0, 1).toUpperCase() + substring(1)

    }
    return ""
}

fun <E : Any, T : Collection<E>> T?.isNotNullNorEmpty(func: (nullOrEmpty: Boolean) -> Unit) {
    if (this != null && this.isNotEmpty()) {
        func(true)
    } else
        func(false)
}

fun <E : Any, T : Collection<E>> T?.iterateIfNotEmptyNull(func: E.() -> Unit) {
    if (this != null && this.isNotEmpty()) {
        forEach { func(it) }
    }
}


fun String.toDoubleMe(): Double {
    if (isEmpty()) {
        return 0.0
    }
    return toDouble()
}

/**
 * Returns the sum of all values produced by [selector] function applied to each element in the collection.
 */
public inline fun <T> Iterable<T>.sumByBigDecimal(selector: (T) -> BigDecimal): BigDecimal {
    var sum: BigDecimal = BigDecimal(0.0)
    for (element in this) {
        sum += selector(element)
    }
    return sum
}

fun String?.toDefaultIfNull(): String {
    if (this == null)
        return ""
    return this
}

fun String?.toDefaultDoubleIfNull(defaultVal: Double = 0.0): Double {
    if (this == null || TextUtils.isEmpty(this.trim()))
        return defaultVal
    return try {
        trim().toDouble()
    } catch (e: NumberFormatException) {
        e.printStackTrace()
        0.0
    }

}

fun Double?.toInteger(): Int {
    if (this != null)
        return toInt()
    return 0
}

fun String?.toBoolean(): Boolean {
    if (this == null || TextUtils.isEmpty(trim())) {
        return false
    }
    if (equals("1"))
        return true
    return false
}


fun Double?.toDigitPrice(): String {
    return String.format("$%.2f", this)
}

fun String?.toDigitPrice(): String {
    return String.format("$%.2f", toDefaultDoubleIfNull())
}

operator fun ArrayList<Any>.minus(o: Any) {
    this.remove(o)
}

operator fun ArrayList<Any>.plus(o: Any) {
    this.add(o)
}


fun <T> ArrayList<T>.applyChanges(func: T.() -> Unit) {
    forEach {
        with(this) { func(it) }
    }
}

fun <T> ArrayList<T>.optAddAll(list: ArrayList<T>?) {
    if (list != null) {
        addAll(list)
    }
}


fun <T> T?.ifNotNull(func: (T) -> Unit) {
    if (this != null) {
        func(this)
    }
}

fun String?.ifNotBlank(func: (String) -> Unit) {
    if (this != null && this.trim().isNotEmpty()) {
        func(this)
    }
}

fun JSONObject.getAlternateString(key: String, vararg alternates: String): String {
    if (has(key)) {
        return getString(key)
    }
    alternates.forEach {
        if (has(it)) {
            return getString(it)
        }
    }
    return ""
}

fun <T> Iterable<T>?.getCSVString(func: (T) -> String?): String {
    return TextUtils.join(",", this?.map(func))
}