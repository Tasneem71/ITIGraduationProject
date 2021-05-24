package com.example.graduationapp.data

import com.google.gson.annotations.SerializedName



data class ApiCollections (

	@SerializedName("custom_collections") val custom_collections : List<Custom_collections>
)

data class Custom_collections (

		@SerializedName("id") val id : Long,
		@SerializedName("handle") val handle : String,
		@SerializedName("updated_at") val updated_at : String,
		@SerializedName("published_at") val published_at : String,
		@SerializedName("sort_order") val sort_order : String,
		@SerializedName("template_suffix") val template_suffix : String,
		@SerializedName("published_scope") val published_scope : String,
		@SerializedName("title") val title : String,
		@SerializedName("body_html") val body_html : String,
		@SerializedName("admin_graphql_api_id") val admin_graphql_api_id : String
)