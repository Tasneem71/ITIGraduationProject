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

    fun setUserDiscount(id: Long) {
        val editor: SharedPreferences.Editor = pref!!.edit()
        editor.putLong("user-discount", id)
        editor.apply()
    }

    fun setAddressID(addressIp: String?) {
        val editor: SharedPreferences.Editor = pref!!.edit()
        editor.putString("address_ip", addressIp)
        editor.apply()
    }

    fun checkLoginWithFirebase(): Boolean? {
        return pref!!.getBoolean("login-firebase", false)
    }

    fun setLogin(login: Boolean) {
        val editor = pref!!.edit()
        editor.putBoolean("login", login)
        editor.apply()
    }

    fun setEver(login: Boolean) {
        val editor = pref!!.edit()
        editor.putBoolean("ever", login)
        editor.apply()
    }

    fun setNightMode(mode:Boolean){
        val editor = pref!!.edit()
        editor.putBoolean("night_mode", mode)
        editor.apply()
    }
    fun getNightMode():Boolean{
        return pref!!.getBoolean("night_mode", false)
    }

    fun setLanguage(lan:String){
        val editor = pref!!.edit()
        editor.putString("language", lan)
        editor.apply()
    }
    fun getLanguage():String?{
        return pref!!.getString("language", "en")
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

    fun getUserDiscount(): Long? {
        return pref!!.getLong("user-discount", 0)
    }

    fun getAddressID(): String? {
        return pref!!.getString("address_ip", " ")
    }

    fun getUserInfo(): String? {
        return pref!!.getString("user-info", " ")
    }

    fun getUserStatus(): Boolean {
        Log.i("tasneem",""+pref!!.getBoolean("user-state", false))
        return pref!!.getBoolean("user-state", false)
    }

    fun getEver(): Boolean {
        return pref!!.getBoolean("ever", false)
    }





}