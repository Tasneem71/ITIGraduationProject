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

    @Test
    fun `empty phone returns false`() {
        val result = Validation.validatePhone(
            ""
        )
        assertThat(result).isFalse()
    }
    @Test
    fun `phone less than 11 number returns false`() {
        val result = Validation.validatePhone(
            "+20109950855"
        )
        assertThat(result).isFalse()
    }
    @Test
    fun `phone more than 11 number returns false`() {
        val result = Validation.validatePhone(
            "+2010995085555"
        )
        assertThat(result).isFalse()
    }
    @Test
    fun `phone without country code number returns false`() {
        val result = Validation.validatePhone(
            "01099500855"
        )
        assertThat(result).isFalse()
    }
    @Test
    fun `phone without + in the beginning returns false`() {
        val result = Validation.validatePhone(
            "201099500855"
        )
        assertThat(result).isFalse()
    }
    @Test
    fun `phone validate returns True`() {
        val result = Validation.validatePhone(
            "+201099500855"
        )
        assertThat(result).isTrue()
    }

}