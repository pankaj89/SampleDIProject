package com.master.basediproject.utils.preference

import android.annotation.SuppressLint
import android.content.SharedPreferences
import kotlin.reflect.KProperty

internal class StringNullablePref(
    val default: String?,
    override val key: String?,
    private val commitByDefault: Boolean
) : AbstractPref<String?>() {

    override fun getFromPreference(property: KProperty<*>, preference: SharedPreferences): String? {
        return preference.getString(key ?: property.name, default)
    }

    @SuppressLint("CommitPrefEdits")
    override fun setToPreference(
        property: KProperty<*>,
        value: String?,
        preference: SharedPreferences
    ) {
        preference.edit().putString(key ?: property.name, value).execute(commitByDefault)
    }

    override fun setToEditor(
        property: KProperty<*>,
        value: String?,
        editor: SharedPreferences.Editor
    ) {
        editor.putString(key ?: property.name, value)
    }
}
