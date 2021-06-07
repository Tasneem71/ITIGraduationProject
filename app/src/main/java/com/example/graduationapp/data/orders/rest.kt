package com.example.graduationapp.data.orders

import com.example.domain.core.feature.transactionsFeature.entitiy.Transactions
import com.google.gson.annotations.SerializedName

data class Refunds (

    @SerializedName("id") val id : String,
    @SerializedName("admin_graphql_api_id") val admin_graphql_api_id : String,
    @SerializedName("created_at") val created_at : String,
    @SerializedName("note") val note : String,
    @SerializedName("order_id") val order_id : String,
    @SerializedName("processed_at") val processed_at : String,
    @SerializedName("restock") val restock : Boolean,
    @SerializedName("total_duties_set") val total_duties_set : Total_duties_set,
    @SerializedName("user_id") val user_id : String,
    @SerializedName("order_adjustments") val order_adjustments : List<Order_adjustments>,
    @SerializedName("transactions") val transactions : List<Transactions>,
    @SerializedName("refund_line_items") val refund_line_items : List<String>,
    @SerializedName("duties") val duties : List<String>
)

data class Total_duties_set (

    @SerializedName("shop_money") val shop_money : Shop_money,
    @SerializedName("presentment_money") val presentment_money : Presentment_money
)

data class Discount_allocations (

    @SerializedName("amount") val amount : Double,
    @SerializedName("amount_set") val amount_set : Amount_set,
    @SerializedName("discount_application_index") val discount_application_index : Int
)

data class Amount_set (

    @SerializedName("shop_money") val shop_money : Shop_money,
    @SerializedName("presentment_money") val presentment_money : Presentment_money
)


data class Discount_applications (

    @SerializedName("target_type") val target_type : String,
    @SerializedName("type") val type : String,
    @SerializedName("value") val value : Double,
    @SerializedName("value_type") val value_type : String,
    @SerializedName("allocation_method") val allocation_method : String,
    @SerializedName("target_selection") val target_selection : String,
    @SerializedName("title") val title : String,
    @SerializedName("description") val description : String
)

data class Order_adjustments (

    @SerializedName("id") val id : String,
    @SerializedName("amount") val amount : Double,
    @SerializedName("amount_set") val amount_set : Amount_set,
    @SerializedName("kind") val kind : String,
    @SerializedName("order_id") val order_id : String,
    @SerializedName("reason") val reason : String,
    @SerializedName("refund_id") val refund_id : String,
    @SerializedName("tax_amount") val tax_amount : Double,
    @SerializedName("tax_amount_set") val tax_amount_set : Tax_amount_set
)

data class Tax_amount_set (

    @SerializedName("shop_money") val shop_money : Shop_money,
    @SerializedName("presentment_money") val presentment_money : Presentment_money
)