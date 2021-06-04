package com.example.graduationapp.data

data class CreatedOrder(
    val order : Order
)

data class Order(
    val email: String,
    val fulfillment_status: String,
    val total_price : String,
    val line_items: List<LineItems>?
)


data class LineItems (
    val title : String,
    val price : String,
    val quantity : Int,
    val variant_id : String

)