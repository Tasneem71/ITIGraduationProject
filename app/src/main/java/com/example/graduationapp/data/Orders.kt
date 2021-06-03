package com.example.graduationapp.data

import com.google.gson.annotations.SerializedName

data class Orders(

	@SerializedName("orders") val orders: List<OrdersItem?>? = null
)

data class CurrentTotalDiscountsSet(

	@SerializedName("shop_money") val shopMoney: ShopMoney? = null,
	@SerializedName("presentment_money") val presentmentMoney: PresentmentMoney? = null
)

data class CurrentTotalTaxSet(

	@SerializedName("shop_money") val shopMoney: ShopMoney? = null,
	@SerializedName("presentment_money") val presentmentMoney: PresentmentMoney? = null
)

data class TotalPriceSet(

	@SerializedName("shop_money") val shopMoney: ShopMoney? = null,
	@SerializedName("presentment_money") val presentmentMoney: PresentmentMoney? = null
)

data class ShippingAddress(

	@SerializedName("zip") val zip: String? = null,
	@SerializedName("country") val country: String? = null,
	@SerializedName("city") val city: String? = null,
	@SerializedName("address2") val address2: Any? = null,
	@SerializedName("address1") val address1: String? = null,
	@SerializedName("latitude") val latitude: Any? = null,
	@SerializedName("last_name") val lastName: String? = null,
	@SerializedName("province_code") val provinceCode: String? = null,
	@SerializedName("country_code") val countryCode: String? = null,
	@SerializedName("province") val province: String? = null,
	@SerializedName("phone") val phone: String? = null,
	@SerializedName("name") val name: String? = null,
	@SerializedName("company") val company: Any? = null,
	@SerializedName("first_name") val firstName: String? = null,
	@SerializedName("longitude") val longitude: Any? = null
)

data class CurrentTotalPriceSet(

	@SerializedName("shop_money") val shopMoney: ShopMoney? = null,
	@SerializedName("presentment_money") val presentmentMoney: PresentmentMoney? = null
)

data class PriceSet(

	@SerializedName("shop_money") val shopMoney: ShopMoney? = null,
	@SerializedName("presentment_money") val presentmentMoney: PresentmentMoney? = null
)

data class TotalDiscountSet(

	@SerializedName("shop_money") val shopMoney: ShopMoney? = null,
	@SerializedName("presentment_money") val presentmentMoney: PresentmentMoney? = null
)

data class TotalTaxSet(

	@SerializedName("shop_money") val shopMoney: ShopMoney? = null,
	@SerializedName("presentment_money") val presentmentMoney: PresentmentMoney? = null
)

data class PresentmentMoney(

	@SerializedName("amount") val amount: String? = null,
	@SerializedName("currency_code") val currencyCode: String? = null
)

data class OrdersItem(

	@SerializedName("cancelled_at") val cancelledAt: Any? = null,
	@SerializedName("fulfillment_status") val fulfillmentStatus: Any? = null,
	@SerializedName("total_price_usd") val totalPriceUsd: String? = null,
	@SerializedName("current_total_discounts_set") val currentTotalDiscountsSet: CurrentTotalDiscountsSet? = null,
	@SerializedName("billing_address") val billingAddress: BillingAddress? = null,
	@SerializedName("line_items") val lineItems: List<LineItemsItem?>? = null,
	@SerializedName("original_total_duties_set") val originalTotalDutiesSet: Any? = null,
	@SerializedName("presentment_currency") val presentmentCurrency: String? = null,
	@SerializedName("total_discounts_set") val totalDiscountsSet: TotalDiscountsSet? = null,
	@SerializedName("location_id") val locationId: Any? = null,
	@SerializedName("source_url") val sourceUrl: Any? = null,
	@SerializedName("landing_site") val landingSite: Any? = null,
	@SerializedName("source_identifier") val sourceIdentifier: Any? = null,
	@SerializedName("reference") val reference: Any? = null,
	@SerializedName("number") val number: Int? = null,
	@SerializedName("checkout_id") val checkoutId: Any? = null,
	@SerializedName("checkout_token") val checkoutToken: Any? = null,
	@SerializedName("tax_lines") val taxLines: List<Any?>? = null,
	@SerializedName("current_total_discounts") val currentTotalDiscounts: String? = null,
	@SerializedName("customer_locale") val customerLocale: Any? = null,
	@SerializedName("id") val id: String? = null,
	@SerializedName("app_id") val appId: Int? = null,
	@SerializedName("subtotal_price") val subtotalPrice: String? = null,
	@SerializedName("closed_at") val closedAt: Any? = null,
	@SerializedName("order_status_url") val orderStatusUrl: String? = null,
	@SerializedName("current_total_price_set") val currentTotalPriceSet: CurrentTotalPriceSet? = null,
	@SerializedName("device_id") val deviceId: Any? = null,
	@SerializedName("test") val test: Boolean? = null,
	@SerializedName("total_shipping_price_set") val totalShippingPriceSet: TotalShippingPriceSet? = null,
	@SerializedName("subtotal_price_set") val subtotalPriceSet: SubtotalPriceSet? = null,
	@SerializedName("payment_gateway_names") val paymentGatewayNames: List<Any?>? = null,
	@SerializedName("total_tax") val totalTax: String? = null,
	@SerializedName("tags") val tags: String? = null,
	@SerializedName("current_subtotal_price_set") val currentSubtotalPriceSet: CurrentSubtotalPriceSet? = null,
	@SerializedName("processing_method") val processingMethod: String? = null,
	@SerializedName("current_total_tax") val currentTotalTax: String? = null,
	@SerializedName("shipping_lines") val shippingLines: List<Any?>? = null,
	@SerializedName("phone") val phone: Any? = null,
	@SerializedName("user_id") val userId: Any? = null,
	@SerializedName("note_attributes") val noteAttributes: List<Any?>? = null,
	@SerializedName("name") val name: String? = null,
	@SerializedName("cart_token") val cartToken: Any? = null,
	@SerializedName("total_tax_set") val totalTaxSet: TotalTaxSet? = null,
	@SerializedName("landing_site_ref") val landingSiteRef: Any? = null,
	@SerializedName("discount_codes") val discountCodes: List<Any?>? = null,
	@SerializedName("note") val note: Any? = null,
	@SerializedName("current_subtotal_price") val currentSubtotalPrice: String? = null,
	@SerializedName("current_total_tax_set") val currentTotalTaxSet: CurrentTotalTaxSet? = null,
	@SerializedName("total_outstanding") val totalOutstanding: String? = null,
	@SerializedName("order_number") val orderNumber: Int? = null,
	@SerializedName("discount_applications") val discountApplications: List<Any?>? = null,
	@SerializedName("created_at") val createdAt: String? = null,
	@SerializedName("total_line_items_price_set") val totalLineItemsPriceSet: TotalLineItemsPriceSet? = null,
	@SerializedName("taxes_included") val taxesIncluded: Boolean? = null,
	@SerializedName("buyer_accepts_marketing") val buyerAcceptsMarketing: Boolean? = null,
	@SerializedName("confirmed") val confirmed: Boolean? = null,
	@SerializedName("total_weight") val totalWeight: Int? = null,
	@SerializedName("contact_email") val contactEmail: String? = null,
	@SerializedName("refunds") val refunds: List<Any?>? = null,
	@SerializedName("total_discounts") val totalDiscounts: String? = null,
	@SerializedName("fulfillments") val fulfillments: List<Any?>? = null,
	@SerializedName("referring_site") val referringSite: Any? = null,
	@SerializedName("updated_at") val updatedAt: String? = null,
	@SerializedName("processed_at") val processedAt: String? = null,
	@SerializedName("currency") val currency: String? = null,
	@SerializedName("shipping_address") val shippingAddress: ShippingAddress? = null,
	@SerializedName("browser_ip") val browserIp: Any? = null,
	@SerializedName("email") val email: String? = null,
	@SerializedName("source_name") val sourceName: String? = null,
	@SerializedName("total_price_set") val totalPriceSet: TotalPriceSet? = null,
	@SerializedName("current_total_duties_set") val currentTotalDutiesSet: Any? = null,
	@SerializedName("total_price") val totalPrice: String? = null,
	@SerializedName("total_line_items_price") val totalLineItemsPrice: String? = null,
	@SerializedName("total_tip_received") val totalTipReceived: String? = null,
	@SerializedName("token") val token: String? = null,
	@SerializedName("cancel_reason") val cancelReason: Any? = null,
	@SerializedName("current_total_price") val currentTotalPrice: String? = null,
	@SerializedName("admin_graphql_api_id") val adminGraphqlApiId: String? = null,
	@SerializedName("financial_status") val financialStatus: String? = null,
	@SerializedName("gateway") val gateway: String? = null,
	@SerializedName("customer") val customer: Customers? = null
)

data class TotalDiscountsSet(

	@SerializedName("shop_money") val shopMoney: ShopMoney? = null,
	@SerializedName("presentment_money") val presentmentMoney: PresentmentMoney? = null
)

data class CurrentSubtotalPriceSet(

	@field:SerializedName("shop_money")
	val shopMoney: ShopMoney? = null,

	@field:SerializedName("presentment_money")
	val presentmentMoney: PresentmentMoney? = null
)

data class TotalShippingPriceSet(

	@SerializedName("shop_money") val shopMoney: ShopMoney? = null,
	@SerializedName("presentment_money") val presentmentMoney: PresentmentMoney? = null
)

data class ShopMoney(
	@SerializedName("amount") val amount: String? = null,
	@SerializedName("currency_code") val currencyCode: String? = null
)

data class LineItemsItem(

	@SerializedName("variant_title") val variantTitle: String? = null,
	@SerializedName("fulfillment_status") val fulfillmentStatus: Any? = null,
	@SerializedName("total_discount") val totalDiscount: String? = null,
	@SerializedName("gift_card") val giftCard: Boolean? = null,
	@SerializedName("requires_shipping") val requiresShipping: Boolean? = null,
	@SerializedName("total_discount_set") val totalDiscountSet: TotalDiscountSet? = null,
	@SerializedName("title") val title: String? = null,
	@SerializedName("product_exists") val productExists: Boolean? = null,
	@SerializedName("variant_id") val variantId: String? = null,
	@SerializedName("tax_lines") val taxLines: List<Any?>? = null,
	@SerializedName("price") val price: String? = null,
	@SerializedName("vendor") val vendor: String? = null,
	@SerializedName("product_id") val productId: Long? = null,
	@SerializedName("id") val id: String? = null,
	@SerializedName("grams") val grams: Int? = null,
	@SerializedName("sku") val sku: String? = null,
	@SerializedName("fulfillable_quantity") val fulfillableQuantity: Int? = null,
	@SerializedName("quantity") val quantity: Int? = null,
	@SerializedName("fulfillment_service") val fulfillmentService: String? = null,
	@SerializedName("taxable") val taxable: Boolean? = null,
	@SerializedName("variant_inventory_management") val variantInventoryManagement: String? = null,
	@SerializedName("discount_allocations") val discountAllocations: List<Any?>? = null,
	@SerializedName("admin_graphql_api_id") val adminGraphqlApiId: String? = null,
	@SerializedName("name") val name: String? = null,
	@SerializedName("price_set") val priceSet: PriceSet? = null,
	@SerializedName("properties") val properties: List<Any?>? = null,
	@SerializedName("duties") val duties: List<Any?>? = null
)

data class BillingAddress(
	@SerializedName("zip") val zip: String? = null,
	@SerializedName("country") val country: String? = null,
	@SerializedName("city") val city: String? = null,
	@SerializedName("address2") val address2: Any? = null,
	@SerializedName("address1") val address1: String? = null,
	@SerializedName("latitude") val latitude: Any? = null,
	@SerializedName("last_name") val lastName: String? = null,
	@SerializedName("province_code") val provinceCode: String? = null,
	@SerializedName("country_code") val countryCode: String? = null,
	@SerializedName("province") val province: String? = null,
	@SerializedName("phone") val phone: String? = null,
	@SerializedName("name") val name: String? = null,
	@SerializedName("company") val company: Any? = null,
	@SerializedName("first_name") val firstName: String? = null,
	@SerializedName("longitude") val longitude: Any? = null
)

data class SubtotalPriceSet(

	@SerializedName("shop_money") val shopMoney: ShopMoney? = null,
	@SerializedName("presentment_money") val presentmentMoney: PresentmentMoney? = null
)

data class TotalLineItemsPriceSet(

	@SerializedName("shop_money") val shopMoney: ShopMoney? = null,
	@SerializedName("presentment_money") val presentmentMoney: PresentmentMoney? = null
)
