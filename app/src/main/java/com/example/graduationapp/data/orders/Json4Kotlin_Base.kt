package com.example.graduationapp.data.orders
import com.google.gson.annotations.SerializedName

data class OrderAPI (

	@SerializedName("orders") val orders : List<Orders>?,
	@SerializedName("order") val order : Orders?
)