package com.example.graduationapp.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ValidationPhoneTest {

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