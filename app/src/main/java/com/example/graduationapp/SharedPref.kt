package com.example.graduationapp

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

object SharedPref{

    private var pref: SharedPreferences? = null

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

    fun setUserId(id: String?) {
        val editor: SharedPreferences.Editor = pref!!.edit()
        editor.putString("user-id", id)
        editor.apply()
    }

    fun setUserInfo(info: String?) {
        val editor: SharedPreferences.Editor = pref!!.edit()
        editor.putString("user-info", info)
        editor.apply()
    }

    fun setUserState(state: Boolean) {
        val editor: SharedPreferences.Editor = pref!!.edit()
        editor.putBoolean("user-state", state)
        editor.apply()
    }

    fun getUserEmail(): String? {
        return pref!!.getString("user-email", " ")
    }

    fun getUserInfo(): String? {
        return pref!!.getString("user-info", " ")
    }

    fun getUserStatus(): Boolean {
        Log.i("tasneem",""+pref!!.getBoolean("user-state", false))
        return pref!!.getBoolean("user-state", false)
    }

    fun checkLoginWithFirebase(): Boolean? {
        return pref!!.getBoolean("login-firebase", false)
    }

    fun setLogin(login: Boolean) {
        val editor = pref!!.edit()
        editor.putBoolean("login", login)
        editor.apply()
    }
}