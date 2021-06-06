/* 
Copyright (c) 2021 Kotlin Data Classes Generated from JSON powered by http://www.json2kotlin.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For support, please feel free to contact me at https://www.linkedin.com/in/syedabsar */
package com.example.graduationapp.data.orders
import com.google.gson.annotations.SerializedName

data class Default_address (

	@SerializedName("id") val id : String,
	@SerializedName("customer_id") val customer_id : String,
	@SerializedName("first_name") val first_name : String,
	@SerializedName("last_name") val last_name : String,
	@SerializedName("company") val company : String,
	@SerializedName("address1") val address1 : String,
	@SerializedName("address2") val address2 : String,
	@SerializedName("city") val city : String,
	@SerializedName("province") val province : String,
	@SerializedName("country") val country : String,
	@SerializedName("zip") val zip : String,
	@SerializedName("phone") val phone : String,
	@SerializedName("name") val name : String,
	@SerializedName("province_code") val province_code : String,
	@SerializedName("country_code") val country_code : String,
	@SerializedName("country_name") val country_name : String,
	@SerializedName("default") val default : Boolean
)