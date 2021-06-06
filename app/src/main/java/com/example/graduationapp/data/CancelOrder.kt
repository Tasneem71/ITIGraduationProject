package com.example.graduationapp.data

data class CancelOrder(
    val canceledOrder : CanceledOrder
)

data class CanceledOrder(
    val email: String,
    val fulfillment_status: String?,
    val amount : String,
    val currency:  String
)
