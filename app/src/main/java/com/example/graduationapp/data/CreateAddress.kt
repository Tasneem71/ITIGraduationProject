package com.example.graduationapp.data


data class CreateAddress (

    val address : Address
)

data class Address(
    val address1: String,
    val city: String,
    val first_name: String,
    val phone: String,
    val province: String,
    val country: String,
    val zip: String

)


