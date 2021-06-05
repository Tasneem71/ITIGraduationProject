
package com.example.graduationapp.data.orders

import com.google.gson.annotations.SerializedName

data class Orders (

	@SerializedName("id") val id : String,
	@SerializedName("admin_graphql_api_id") val admin_graphql_api_id : String,
	@SerializedName("app_id") val app_id : String,
	@SerializedName("browser_ip") val browser_ip : String,
	@SerializedName("buyer_accepts_marketing") val buyer_accepts_marketing : Boolean,
	@SerializedName("cancel_reason") val cancel_reason : String,
	@SerializedName("cancelled_at") val cancelled_at : String,
	@SerializedName("cart_token") val cart_token : String,
	@SerializedName("checkout_id") val checkout_id : String,
	@SerializedName("checkout_token") val checkout_token : String,
	@SerializedName("closed_at") val closed_at : String,
	@SerializedName("confirmed") val confirmed : Boolean,
	@SerializedName("contact_email") val contact_email : String,
	@SerializedName("created_at") val created_at : String,
	@SerializedName("currency") val currency : String,
	@SerializedName("current_subtotal_price") val current_subtotal_price : Double,
	@SerializedName("current_subtotal_price_set") val current_subtotal_price_set : Current_subtotal_price_set,
	@SerializedName("current_total_discounts") val current_total_discounts : Double,
	@SerializedName("current_total_discounts_set") val current_total_discounts_set : Current_total_discounts_set,
	@SerializedName("current_total_duties_set") val current_total_duties_set : String,
	@SerializedName("current_total_price") val current_total_price : Double,
	@SerializedName("current_total_price_set") val current_total_price_set : Current_total_price_set,
	@SerializedName("current_total_tax") val current_total_tax : Double,
	@SerializedName("current_total_tax_set") val current_total_tax_set : Current_total_tax_set,
	@SerializedName("customer_locale") val customer_locale : String,
	@SerializedName("device_id") val device_id : String,
	@SerializedName("discount_codes") val discount_codes : List<String>,
	@SerializedName("email") val email : String,
	@SerializedName("financial_status") val financial_status : String,
	@SerializedName("fulfillment_status") val fulfillment_status : String,
	@SerializedName("gateway") val gateway : String,
	@SerializedName("landing_site") val landing_site : String,
	@SerializedName("landing_site_ref") val landing_site_ref : String,
	@SerializedName("location_id") val location_id : String,
	@SerializedName("name") val name : String,
	@SerializedName("note") val note : String,
	@SerializedName("note_attributes") val note_attributes : List<String>,
	@SerializedName("number") val number : Int,
	@SerializedName("order_number") val order_number : Int,
	@SerializedName("order_status_url") val order_status_url : String,
	@SerializedName("original_total_duties_set") val original_total_duties_set : String,
	@SerializedName("payment_gateway_names") val payment_gateway_names : List<String>,
	@SerializedName("phone") val phone : String,
	@SerializedName("presentment_currency") val presentment_currency : String,
	@SerializedName("processed_at") val processed_at : String,
	@SerializedName("processing_method") val processing_method : String,
	@SerializedName("reference") val reference : String,
	@SerializedName("referring_site") val referring_site : String,
	@SerializedName("source_identifier") val source_identifier : String,
	@SerializedName("source_name") val source_name : Int,
	@SerializedName("source_url") val source_url : String,
	@SerializedName("subtotal_price") val subtotal_price : Double,
	@SerializedName("subtotal_price_set") val subtotal_price_set : Subtotal_price_set,
	@SerializedName("tags") val tags : String,
	@SerializedName("tax_lines") val tax_lines : List<String>,
	@SerializedName("taxes_included") val taxes_included : Boolean,
	@SerializedName("test") val test : Boolean,
	@SerializedName("token") val token : String,
	@SerializedName("total_discounts") val total_discounts : Double,
	@SerializedName("total_discounts_set") val total_discounts_set : Total_discounts_set,
	@SerializedName("total_line_items_price") val total_line_items_price : Double,
	@SerializedName("total_line_items_price_set") val total_line_items_price_set : Total_line_items_price_set,
	@SerializedName("total_outstanding") val total_outstanding : Double,
	@SerializedName("total_price") val total_price : Double,
	@SerializedName("total_price_set") val total_price_set : Total_price_set,
	@SerializedName("total_price_usd") val total_price_usd : Double,
	@SerializedName("total_shipping_price_set") val total_shipping_price_set : Total_shipping_price_set,
	@SerializedName("total_tax") val total_tax : Double,
	@SerializedName("total_tax_set") val total_tax_set : Total_tax_set,
	@SerializedName("total_tip_received") val total_tip_received : Double,
	@SerializedName("total_weight") val total_weight : Int,
	@SerializedName("updated_at") val updated_at : String,
	@SerializedName("user_id") val user_id : String,
	@SerializedName("customer") val customer : Customer,
	@SerializedName("discount_applications") val discount_applications : List<String>,
	@SerializedName("fulfillments") val fulfillments : List<Fulfillments>,
	@SerializedName("line_items") val line_items : List<Line_items>,
	@SerializedName("refunds") val refunds : List<String>,
	@SerializedName("shipping_lines") val shipping_lines : List<String>
)