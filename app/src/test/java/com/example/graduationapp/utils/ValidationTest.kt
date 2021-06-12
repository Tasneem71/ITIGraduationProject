package com.example.graduationapp.utils

import com.example.graduationapp.utils.Validation.Companion.validateRegistration
import com.google.common.truth.Truth.assertThat
import junit.framework.Assert.assertEquals
import org.junit.Assert
import org.junit.Test

class ValidationTest {

    @Test
    fun `empty userEmail return false`(){
        val userEmail = ""
        val password = "Aa123456"
        val result = validateRegistration(userEmail, password)
        assertThat(result).isFalse()


    }
    @Test
    fun `empty password return false`(){
        val userEmail = "alshimaamuhammad@gmail.com"
        val password = ""
        val result = validateRegistration(userEmail, password)
        assertThat(result).isFalse()


    }
    @Test
    fun `correct userEmail and password return true`(){
        val userEmail = "alshimaamuhammad@gmail.com"
        val password = "Aa123456"
        val result = validateRegistration(userEmail, password)
        assertThat(result).isTrue()


    }
    @Test
    fun `correct userEmail and uncorrect password return false`(){
        val userEmail = "alshimaamuhammad@gmail.com"
        val password = "Aa123"
        val result = validateRegistration(userEmail, password)
        assertThat(result).isFalse()


    }
    @Test
    fun `uncorrect userEmail and correct password return fals`(){
        val userEmail = "alshimaamuhammadgmail.com"
        val password = "Aa123456"
        val result = validateRegistration(userEmail, password)
        assertThat(result).isFalse()

    }

}