/* 
Copyright (c) 2021 Kotlin Data Classes Generated from JSON powered by http://www.json2kotlin.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For support, please feel free to contact me at https://www.linkedin.com/in/syedabsar */
package com.example.graduationapp.data.orders

import com.google.gson.annotations.SerializedName
data class Fulfillments (

	@SerializedName("id") val id : String,
	@SerializedName("admin_graphql_api_id") val admin_graphql_api_id : String,
	@SerializedName("created_at") val created_at : String,
	@SerializedName("location_id") val location_id : Int,
	@SerializedName("name") val name : String,
	@SerializedName("order_id") val order_id : String,
	@SerializedName("service") val service : String,
	@SerializedName("shipment_status") val shipment_status : String,
	@SerializedName("status") val status : String,
	@SerializedName("tracking_company") val tracking_company : String,
	@SerializedName("tracking_number") val tracking_number : String,
	@SerializedName("tracking_numbers") val tracking_numbers : List<String>,
	@SerializedName("tracking_url") val tracking_url : String,
	@SerializedName("tracking_urls") val tracking_urls : List<String>,
	@SerializedName("updated_at") val updated_at : String,
	@SerializedName("line_items") val line_items : List<Line_items>
)