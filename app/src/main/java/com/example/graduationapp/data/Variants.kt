import com.google.gson.annotations.SerializedName

/*
Copyright (c) 2021 Kotlin Data Classes Generated from JSON powered by http://www.json2kotlin.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For support, please feel free to contact me at https://www.linkedin.com/in/syedabsar */


data class Variants (

	@SerializedName("id") val id : Int,
	@SerializedName("product_id") val product_id : Int,
	@SerializedName("title") val title : String,
	@SerializedName("price") val price : Double,
	@SerializedName("sku") val sku : String,
	@SerializedName("position") val position : Int,
	@SerializedName("inventory_policy") val inventory_policy : String,
	@SerializedName("compare_at_price") val compare_at_price : String,
	@SerializedName("fulfillment_service") val fulfillment_service : String,
	@SerializedName("inventory_management") val inventory_management : String,
	@SerializedName("option1") val option1 : String,
	@SerializedName("option2") val option2 : String,
	@SerializedName("option3") val option3 : String,
	@SerializedName("created_at") val created_at : String,
	@SerializedName("updated_at") val updated_at : String,
	@SerializedName("taxable") val taxable : Boolean,
	@SerializedName("barcode") val barcode : String,
	@SerializedName("grams") val grams : Int,
	@SerializedName("image_id") val image_id : String,
	@SerializedName("weight") val weight : Int,
	@SerializedName("weight_unit") val weight_unit : String,
	@SerializedName("inventory_item_id") val inventory_item_id : Int,
	@SerializedName("inventory_quantity") val inventory_quantity : Int,
	@SerializedName("old_inventory_quantity") val old_inventory_quantity : Int,
	@SerializedName("requires_shipping") val requires_shipping : Boolean,
	@SerializedName("admin_graphql_api_id") val admin_graphql_api_id : String
)