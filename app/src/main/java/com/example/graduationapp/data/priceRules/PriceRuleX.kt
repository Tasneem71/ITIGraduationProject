package com.example.graduationapp.data.priceRules

data class PriceRuleX(

    val id: Long,
    val starts_at: String,
    val ends_at: Any,
    val customer_selection: String,
    val target_selection: String,
    val target_type: String,
    val title: String,
    val value: String,
    val value_type: String
)