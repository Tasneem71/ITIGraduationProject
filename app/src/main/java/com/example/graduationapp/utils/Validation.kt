package com.example.graduationapp.utils

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

    }


}