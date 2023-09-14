package com.shashank.platform.saloon.constant

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences


class SharedPreferenceClass(context: Context) {
    private val USER_PREFS = "MySaloon"
    private val appSharedPrefs: SharedPreferences =
        context.getSharedPreferences(USER_PREFS, Activity.MODE_PRIVATE)

    private val prefsEditor: SharedPreferences.Editor = appSharedPrefs.edit()

    fun getValue_int(intKeyValue: String): Int {
        return appSharedPrefs.getInt(intKeyValue, 0)
    }

    fun getString(stringKeyValue: String): String {
        return appSharedPrefs.getString(stringKeyValue, "") ?: ""
    }

    fun getBoolean(stringKeyValue: String): Boolean {
        return appSharedPrefs.getBoolean(stringKeyValue, false)
    }

    fun setValue_int(intKeyValue: String, _intValue: Int) {
        prefsEditor.putInt(intKeyValue, _intValue).commit()
    }

    fun setString(stringKeyValue: String, _stringValue: String) {
        prefsEditor.putString(stringKeyValue, _stringValue).commit()
    }

    fun setBoolean(stringKeyValue: String, _bool: Boolean) {
        prefsEditor.putBoolean(stringKeyValue, _bool).commit()
    }

    fun setValue_int(intKeyValue: String) {
        prefsEditor.putInt(intKeyValue, 0).commit()
    }

    fun clearData() {
        prefsEditor.clear().commit()
    }

    fun getBoolean(stringKeyValue: String, _stringValue: Boolean) {
        prefsEditor.putBoolean(stringKeyValue, _stringValue).commit()
    }
}

