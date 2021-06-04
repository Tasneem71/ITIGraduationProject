package com.example.graduationapp.data

import com.google.gson.annotations.SerializedName




data class ApiCustomers (

    @SerializedName("customers") val customers : List<Customers>?,
    @SerializedName("customer") val customer : Customers?
)
data class Customers (

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
    @SerializedName("last_order_id") val last_order_id : String,
    @SerializedName("note") val note : String,
    @SerializedName("verified_email") val verified_email : Boolean,
    @SerializedName("multipass_identifier") val multipass_identifier : String,
    @SerializedName("tax_exempt") val tax_exempt : Boolean,
    @SerializedName("phone") val phone : String,
    @SerializedName("tags") val tags : String,
    @SerializedName("last_order_name") val last_order_name : String,
    @SerializedName("currency") val currency : String,
    @SerializedName("addresses") val addresses : List<Addresse>,
    @SerializedName("accepts_marketing_updated_at") val accepts_marketing_updated_at : String,
    @SerializedName("marketing_opt_in_level") val marketing_opt_in_level : String,
    @SerializedName("tax_exemptions") val tax_exemptions : List<String>,
    @SerializedName("admin_graphql_api_id") val admin_graphql_api_id : String,
    @SerializedName("default_address") val default_address : Default_address
)


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

data class AddressData (

    @SerializedName("customer_address") val address : Addresse?,
    @SerializedName("addresses") val addressList : List<Addresse>?
)

data class Addresse (

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