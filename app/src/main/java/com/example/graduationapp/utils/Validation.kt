package com.example.graduationapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import java.util.regex.Matcher
import java.util.regex.Pattern

class Validation {

    companion object{
        fun validateRegistration(userEmail: String, password: String):Boolean{
            var result:Boolean
            val patternEmail: Pattern = Pattern.compile("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
            val matcherEmail: Matcher = patternEmail.matcher(userEmail)
            val emailMatchResult: Boolean = matcherEmail.matches()

            //Minimum eight characters, at least one letter and one number
            val patternPassword: Pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
            val matcherPassword: Matcher = patternPassword.matcher(password)
            val passowordMatchResult : Boolean = matcherPassword.matches()

            result = emailMatchResult && passowordMatchResult
            return result
        }
        fun isOnline(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        Log.i("Menna", "NetworkCapabilities.TRANSPORT_CELLULAR")
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        Log.i("Menna", "NetworkCapabilities.TRANSPORT_WIFI")
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        Log.i("Menna", "NetworkCapabilities.TRANSPORT_ETHERNET")
                        return true
                    }
                }
            }
            return false
        }

    }


}