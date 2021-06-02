package com.example.graduationapp.data

import android.text.Editable


data class CreateAddress (

    val address : Address
)

data class Address(
    val address1: String,
    val address2: String,
    val city: String,
    val company: String,
    val first_name: String,
    val last_name: String,
    val phone: String,
    val province: String,
    val country: String,
    val zip: String,
    val name: String,
    val province_code: String,
    val country_code: String,
    val country_name: String

)


