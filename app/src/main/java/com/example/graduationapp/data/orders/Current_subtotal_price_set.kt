
package com.example.graduationapp.data.orders


import com.google.gson.annotations.SerializedName
data class Current_subtotal_price_set (

	@SerializedName("shop_money") val shop_money : Shop_money,
	@SerializedName("presentment_money") val presentment_money : Presentment_money
)