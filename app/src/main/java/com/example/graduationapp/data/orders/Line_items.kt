/* 
Copyright (c) 2021 Kotlin Data Classes Generated from JSON powered by http://www.json2kotlin.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For support, please feel free to contact me at https://www.linkedin.com/in/syedabsar */
package com.example.graduationapp.data.orders

import com.google.gson.annotations.SerializedName
data class Line_items (

	@SerializedName("id") val id : String,
	@SerializedName("admin_graphql_api_id") val admin_graphql_api_id : String,
	@SerializedName("fulfillable_quantity") val fulfillable_quantity : Int,
	@SerializedName("fulfillment_service") val fulfillment_service : String,
	@SerializedName("fulfillment_status") val fulfillment_status : String,
	@SerializedName("gift_card") val gift_card : Boolean,
	@SerializedName("grams") val grams : Int,
	@SerializedName("name") val name : String,
	@SerializedName("price") val price : Double,
	@SerializedName("price_set") val price_set : Price_set,
	@SerializedName("product_exists") val product_exists : Boolean,
	@SerializedName("product_id") val product_id : String,
	@SerializedName("properties") val properties : List<String>,
	@SerializedName("quantity") val quantity : Int,
	@SerializedName("requires_shipping") val requires_shipping : Boolean,
	@SerializedName("sku") val sku : String,
	@SerializedName("taxable") val taxable : Boolean,
	@SerializedName("title") val title : String,
	@SerializedName("total_discount") val total_discount : Double,
	@SerializedName("total_discount_set") val total_discount_set : Total_discount_set,
	@SerializedName("variant_id") val variant_id : String,
	@SerializedName("variant_inventory_management") val variant_inventory_management : String,
	@SerializedName("variant_title") val variant_title : String,
	@SerializedName("vendor") val vendor : String,
	@SerializedName("duties") val duties : List<String>,
	@SerializedName("discount_allocations") val discount_allocations : List<String>
)