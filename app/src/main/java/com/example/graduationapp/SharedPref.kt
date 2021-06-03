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

    fun getUserID(): String? {
        return pref!!.getString("user-id", " ")
    }
    fun getUserFname(): String? {
        return pref!!.getString("user-info", " ")
    }

    fun setAddressIp(addressIp: String?) {
        val editor: SharedPreferences.Editor = pref!!.edit()
        editor.putString("address_ip", addressIp)
        editor.apply()
    }
    fun getAddressIp(): String? {
        return pref!!.getString("address_ip", " ")
    }
    fun haveOneAddress(addressIp: Boolean) {
        val editor: SharedPreferences.Editor = pref!!.edit()
        editor.putBoolean("have_address", addressIp)
        editor.apply()
    }
    fun isHaveOneAddress(): Boolean {
        return pref!!.getBoolean("have_address", false)
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