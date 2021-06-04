/* 
Copyright (c) 2021 Kotlin Data Classes Generated from JSON powered by http://www.json2kotlin.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For support, please feel free to contact me at https://www.linkedin.com/in/syedabsar */
package com.example.graduationapp.data.orders

import com.google.gson.annotations.SerializedName
data class Customer (

	@SerializedName("id") val id : String,
	@SerializedName("email") val email : String,
	@SerializedName("accepts_marketing") val accepts_marketing : Boolean,
	@SerializedName("created_at") val created_at : String,
	@SerializedName("updated_at") val updated_at : String,
	@SerializedName("first_name") val first_name : String,
	@SerializedName("last_name") val last_name : String,
	@SerializedName("orders_count") val orders_count : Int,
	@SerializedName("state") val state : String,
	@SerializedName("total_spent") val total_spent : Double,
	@SerializedName("last_order_id") val last_order_id : Int,
	@SerializedName("note") val note : String,
	@SerializedName("verified_email") val verified_email : Boolean,
	@SerializedName("multipass_identifier") val multipass_identifier : String,
	@SerializedName("tax_exempt") val tax_exempt : Boolean,
	@SerializedName("phone") val phone : String,
	@SerializedName("tags") val tags : String,
	@SerializedName("last_order_name") val last_order_name : String,
	@SerializedName("currency") val currency : String,
	@SerializedName("accepts_marketing_updated_at") val accepts_marketing_updated_at : String,
	@SerializedName("marketing_opt_in_level") val marketing_opt_in_level : String,
	@SerializedName("tax_exemptions") val tax_exemptions : List<String>,
	@SerializedName("admin_graphql_api_id") val admin_graphql_api_id : String,
	@SerializedName("default_address") val default_address : Default_address
)