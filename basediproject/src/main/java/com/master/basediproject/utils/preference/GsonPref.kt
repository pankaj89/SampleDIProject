package com.master.basediproject.utils.preference

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.google.gson.Gson
import java.lang.reflect.Type
import kotlin.reflect.KProperty


class GsonPref<T : Any>(
    private val targetType: Type,
    private val default: T,
    override val key: String?,
    private val commitByDefault: Boolean,
    private val gson: Gson
) : AbstractPref<T>() {

    override fun getFromPreference(property: KProperty<*>, preference: SharedPreferences): T {
        return preference.getString(key ?: property.name, null)?.let { json ->
            deserializeFromJson(json) ?: default
        } ?: default
    }

    @SuppressLint("CommitPrefEdits")
    override fun setToPreference(property: KProperty<*>, value: T, preference: SharedPreferences) {
        serializeToJson(value).let { json ->
            preference.edit().putString(key ?: property.name, json).execute(commitByDefault)
        }
    }

    override fun setToEditor(property: KProperty<*>, value: T, editor: SharedPreferences.Editor) {
        serializeToJson(value).let { json ->
            editor.putString(key ?: property.name, json)
        }
    }

    private fun serializeToJson(value: T?): String? {
        return gson.let {
            if (it == null) throw IllegalStateException("Gson has not been set to Kotpref")

            it.toJson(value)
        }
    }

    private fun deserializeFromJson(json: String): T? {
        return gson.let {
            if (it == null) throw IllegalStateException("Gson has not been set to Kotpref")

            it.fromJson(json, targetType)
        }
    }
}
