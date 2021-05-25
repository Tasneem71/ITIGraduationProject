package com.example.graduationapp.data

import com.google.gson.annotations.SerializedName



data class CollectionProducts (

    @SerializedName("products") val products : List<Products>
)


data class Products (

    @SerializedName("id") val id : String,
    @SerializedName("title") val title : String,
    @SerializedName("body_html") val body_html : String,
    @SerializedName("vendor") val vendor : String,
    @SerializedName("product_type") val product_type : String,
    @SerializedName("created_at") val created_at : String,
    @SerializedName("handle") val handle : String,
    @SerializedName("updated_at") val updated_at : String,
    @SerializedName("published_at") val published_at : String,
    @SerializedName("template_suffix") val template_suffix : String,
    @SerializedName("published_scope") val published_scope : String,
    @SerializedName("tags") val tags : String,
    @SerializedName("admin_graphql_api_id") val admin_graphql_api_id : String,
    @SerializedName("options") val options : List<Options>,
    @SerializedName("images") val images : List<Images>,
    @SerializedName("image") val image : Image
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
    @SerializedName("src") val src : String,
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