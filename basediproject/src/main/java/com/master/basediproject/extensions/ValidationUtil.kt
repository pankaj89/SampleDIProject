package com.master.basediproject.extensions

import android.util.Patterns

/**
 * email validation
 */
fun String.isValidEmail(): Boolean {
    val pattern = Patterns.EMAIL_ADDRESS
    val matcher = pattern.matcher(this)
    return matcher.matches()
}

/**
 * phone number validation
 */
fun String.isValidPhoneNumber(): Boolean {
    return this.matches("[0-9]*".toRegex()) && this.length in 8..12
}

/**
 * password validation
 */
fun String.isValidPassword(): Boolean {
    return this.length in 6..50
}

fun String.isValidName(): Boolean {
    return this.length >= 2
}



