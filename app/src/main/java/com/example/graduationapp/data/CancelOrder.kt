package com.example.graduationapp.data

 class CancelOrder(){

}

data class CanceledOrder(
    val email: String,
    val fulfillment_status: String?,
    val amount : Double,
    val currency:  String
)
