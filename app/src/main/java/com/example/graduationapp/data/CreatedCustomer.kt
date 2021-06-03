package com.example.graduationapp.data







data class CreatedCustomer (

    val customer : Customer
)


data class Customer(

    val first_name: String,
    val last_name: String,
    val email: String,
    val phone: String?,
    val note: String?,
    val verified_email: Boolean,
    val addresses: List<Addresses>?,
    val password : String?,
    val password_confirmation : String?,
    val send_email_welcome : Boolean?
)


data class Addresses (

    val address1 : String,
    val city : String,
    val province : String,
    val phone : String,
    val zip : String,
    val last_name : String,
    val first_name : String,
    val country : String
)