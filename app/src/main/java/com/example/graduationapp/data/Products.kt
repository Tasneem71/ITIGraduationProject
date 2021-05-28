package com.example.graduationapp.data

import com.google.gson.annotations.SerializedName



data class CollectionProducts (

    @SerializedName("products") val products : List<Products>
)


data class Products (

    @SerializedName("id") val id : String,
    @SerializedName("title") val title : String,   //
    @SerializedName("body_html") val body_html : String,
    @SerializedName("vendor") val vendor : String,    //
    @SerializedName("product_type") val product_type : String,   //
    @SerializedName("created_at") val created_at : String,
    @SerializedName("handle") val handle : String,
    @SerializedName("updated_at") val updated_at : String,
    @SerializedName("published_at") val published_at : String,
    @SerializedName("template_suffix") val template_suffix : String,
    @SerializedName("published_scope") val published_scope : String,
    @SerializedName("tags") val tags : String,    //
    @SerializedName("variants") val variants : List<Variants>?,  //
    @SerializedName("admin_graphql_api_id") val admin_graphql_api_id : String,
    @SerializedName("options") val options : List<Options>,
    @SerializedName("images") val images : List<Images>,  //
    @SerializedName("image") val image : Image
)
data class Variants (

    @SerializedName("id") val id : Int,
    @SerializedName("product_id") val product_id : Int,
    @SerializedName("title") val title : String,
    @SerializedName("price") val price : Double,    //
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
    @SerializedName("inventory_quantity") val inventory_quantity : Int,   //
    @SerializedName("old_inventory_quantity") val old_inventory_quantity : Int,
    @SerializedName("requires_shipping") val requires_shipping : Boolean,
    @SerializedName("admin_graphql_api_id") val admin_graphql_api_id : String
)
data class Options (

    @SerializedName("id") val id : Long,
    @SerializedName("product_id") val product_id : Long,
    @SerializedName("name") val name : String,
    @SerializedName("position") val position : Int
)
data class Images (

    @SerializedName("id") val id : Long,
    @SerializedName("product_id") val product_id : Long,
    @SerializedName("position") val position : Int,
    @SerializedName("created_at") val created_at : String,
    @SerializedName("updated_at") val updated_at : String,
    @SerializedName("alt") val alt : String,
    @SerializedName("width") val width : Int,
    @SerializedName("height") val height : Int,
    @SerializedName("src") val src : String,   //
    @SerializedName("variant_ids") val variant_ids : List<String>,
    @SerializedName("admin_graphql_api_id") val admin_graphql_api_id : String
)
data class Image (

    @SerializedName("id") val id : Long,
    @SerializedName("product_id") val product_id : Long,
    @SerializedName("position") val position : Int,
    @SerializedName("created_at") val created_at : String,
    @SerializedName("updated_at") val updated_at : String,
    @SerializedName("alt") val alt : String,
    @SerializedName("width") val width : Int,
    @SerializedName("height") val height : Int,
    @SerializedName("src") val src : String,
    @SerializedName("variant_ids") val variant_ids : List<String>,
    @SerializedName("admin_graphql_api_id") val admin_graphql_api_id : String
)