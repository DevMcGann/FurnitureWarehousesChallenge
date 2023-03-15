package com.example.furnitureshopchallenge.data.datasource.local

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPref (private val context: Context) {

    private val dict = "challenge"
    val sharedPref: SharedPreferences = context.getSharedPreferences(dict, Context.MODE_PRIVATE)

    fun savePref(key:String, value:String){
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getFromPrefs(key: String): String? {
        return sharedPref.getString(key, null)
    }

    fun clearSharedPreference() {
        sharedPref.edit().clear().apply()
    }

    fun removeValue(KEY_NAME: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.remove(KEY_NAME)
        editor.apply()
    }


}


