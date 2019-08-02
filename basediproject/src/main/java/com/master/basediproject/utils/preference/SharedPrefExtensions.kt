// IntelliJ API Decompiler stub source generated from a class file
// Implementation of methods is not available

package com.master.basediproject.utils.preference

import android.content.SharedPreferences

fun SharedPreferences.Editor.execute(synchronous: Boolean) {
    if (synchronous) commit() else apply()
}