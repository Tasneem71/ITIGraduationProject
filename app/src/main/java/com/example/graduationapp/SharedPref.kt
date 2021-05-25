package com.example.graduationapp

import android.content.Context
import android.content.SharedPreferences

object SharedPref{

    private var pref: SharedPreferences? = null
    private fun SharedPref() {}

fun createPrefObject(context: Context): SharedPreferences? {
    if (pref == null) {
        pref = context.getSharedPreferences("shopyify", Context.MODE_PRIVATE)
    }
    return pref
}
    fun setUserEmail(email: String?) {
        val editor: SharedPreferences.Editor = pref!!.edit()
        editor.putString("user-email", email)
        editor.apply()
    }

    fun getUserEmail(): String? {
        return pref!!.getString("user-email", " ")
    }

}