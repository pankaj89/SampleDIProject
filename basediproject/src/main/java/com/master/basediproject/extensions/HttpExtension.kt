package com.master.basediproject.extensions

import okhttp3.Response

fun Response.getHeaderByName(name: String): String {
    val keyList = this.headers().names()
    keyList.forEachIndexed { index, key ->
        if (key == name) {
            val valueList = this.headers().values(name)
            return valueList[0]
        }
    }
    return ""
}