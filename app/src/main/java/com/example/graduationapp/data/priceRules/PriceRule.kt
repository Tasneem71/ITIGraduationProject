package com.example.graduationapp.data.priceRules

data class PriceRule(
    val price_rules: List<PriceRuleX>
)
data class DiscountCode(
    val discount_code:DiscountCodeClass
)

data class DiscountCodeClass(
    val id : Long,
    val price_rule_id: Long,
    val code: String,
    val usage_count: Int,
    val created_at :String,
    val updated_at: String

)