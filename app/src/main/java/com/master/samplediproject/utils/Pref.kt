package com.master.samplediproject.utils

import android.app.Activity
import android.content.Context
import com.google.gson.Gson
import com.master.basediproject.utils.preference.KotprefModel
import javax.inject.Inject

class Pref @Inject constructor(private val _context: Context, private val _gson: Gson) :
    KotprefModel(_context, _gson) {
    var name by stringPref()
    var age by intPref()
    var userDetail by gsonPref(Activity())
}