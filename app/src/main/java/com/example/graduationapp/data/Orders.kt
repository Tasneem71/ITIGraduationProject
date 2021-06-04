package com.example.graduationapp.data
//
//import com.google.gson.annotations.SerializedName
//import java.util.*
//
//data class Orders(
//
//	@SerializedName("orders") val orders: List<OrdersItem?>?,
//	@SerializedName("order") val orderObject: OrdersItem
//)
//
//data class CurrentTotalDiscountsSet(
//
//	@SerializedName("shop_money") val shopMoney: ShopMoney? ,
//	@SerializedName("presentment_money") val presentmentMoney: PresentmentMoney?
//)
//
//data class CurrentTotalTaxSet(
//
//	@SerializedName("shop_money") val shopMoney: ShopMoney?,
//	@SerializedName("presentment_money") val presentmentMoney: PresentmentMoney?
//)
//
//data class TotalPriceSet(
//
//	@SerializedName("shop_money") val shopMoney: ShopMoney? ,
//	@SerializedName("presentment_money") val presentmentMoney: PresentmentMoney?
//)
//
//data class ShippingAddress(
//
//	@SerializedName("zip") val zip: String?,
//	@SerializedName("country") val country: String? ,
//	@SerializedName("city") val city: String? ,
//	@SerializedName("address2") val address2: Any? ,
//	@SerializedName("address1") val address1: String? ,
//	@SerializedName("latitude") val latitude: Any? ,
//	@SerializedName("last_name") val lastName: String? ,
//	@SerializedName("province_code") val provinceCode: String? ,
//	@SerializedName("country_code") val countryCode: String? ,
//	@SerializedName("province") val province: String? ,
//	@SerializedName("phone") val phone: String? ,
//	@SerializedName("name") val name: String? ,
//	@SerializedName("company") val company: Any? ,
//	@SerializedName("first_name") val firstName: String? ,
//	@SerializedName("longitude") val longitude: Any?
//)
//
//data class CurrentTotalPriceSet(
//
//	@SerializedName("shop_money") val shopMoney: ShopMoney? ,
//	@SerializedName("presentment_money") val presentmentMoney: PresentmentMoney?
//)
//
//data class PriceSet(
//
//	@SerializedName("shop_money") val shopMoney: ShopMoney? ,
//	@SerializedName("presentment_money") val presentmentMoney: PresentmentMoney?
//)
//
//data class TotalDiscountSet(
//
//	@SerializedName("shop_money") val shopMoney: ShopMoney? ,
//	@SerializedName("presentment_money") val presentmentMoney: PresentmentMoney?
//)
//
//data class TotalTaxSet(
//
//	@SerializedName("shop_money") val shopMoney: ShopMoney?,
//	@SerializedName("presentment_money") val presentmentMoney: PresentmentMoney?
//)
//
//data class PresentmentMoney(
//
//	@SerializedName("amount") val amount: String? ,
//	@SerializedName("currency_code") val currencyCode: String?
//)
//
//data class OrdersItem(
//
//	@SerializedName("cancelled_at") val cancelledAt: Any? ,
//	@SerializedName("fulfillment_status") val fulfillmentStatus: Any? ,
//	@SerializedName("total_price_usd") val totalPriceUsd: String?,
//	@SerializedName("current_total_discounts_set") val currentTotalDiscountsSet: CurrentTotalDiscountsSet? ,
//	@SerializedName("billing_address") val billingAddress: BillingAddress? ,
//	@SerializedName("line_items") val lineItems: List<LineItemsItem?>?  ,
//	@SerializedName("original_total_duties_set") val originalTotalDutiesSet: Any?,
//	@SerializedName("presentment_currency") val presentmentCurrency: String? ,
//	@SerializedName("total_discounts_set") val totalDiscountsSet: TotalDiscountsSet? ,
//	@SerializedName("location_id") val locationId: Any? ,
//	@SerializedName("source_url") val sourceUrl: Any? ,
//	@SerializedName("landing_site") val landingSite: Any? ,
//	@SerializedName("source_identifier") val sourceIdentifier: Any? ,
//	@SerializedName("reference") val reference: Any? ,
//	@SerializedName("number") val number: Int?  ,
//	@SerializedName("checkout_id") val checkoutId: Any? ,
//	@SerializedName("checkout_token") val checkoutToken: Any?,
//	@SerializedName("tax_lines") val taxLines: List<Any?>?,
//	@SerializedName("current_total_discounts") val currentTotalDiscounts: String? ,
//	@SerializedName("customer_locale") val customerLocale: Any? ,
//	@SerializedName("id") val id: String?,
//	@SerializedName("app_id") val appId: Int? ,
//	@SerializedName("subtotal_price") val subtotalPrice: String? ,
//	@SerializedName("closed_at") val closedAt: Any? ,
//	@SerializedName("order_status_url") val orderStatusUrl: String? ,
//	@SerializedName("current_total_price_set") val currentTotalPriceSet: CurrentTotalPriceSet? ,
//	@SerializedName("device_id") val deviceId: Any? ,
//	@SerializedName("test") val test: Boolean? ,
//	@SerializedName("total_shipping_price_set") val totalShippingPriceSet: TotalShippingPriceSet? ,
//	@SerializedName("subtotal_price_set") val subtotalPriceSet: SubtotalPriceSet? ,
//	@SerializedName("payment_gateway_names") val paymentGatewayNames: List<Any?>? ,
//	@SerializedName("total_tax") val totalTax: String? ,
//	@SerializedName("tags") val tags: String? ,
//	@SerializedName("current_subtotal_price_set") val currentSubtotalPriceSet: CurrentSubtotalPriceSet?,
//	@SerializedName("processing_method") val processingMethod: String? ,
//	@SerializedName("current_total_tax") val currentTotalTax: String? ,
//	@SerializedName("shipping_lines") val shippingLines: List<Any?>? ,
//	@SerializedName("phone") val phone: Any? ,
//	@SerializedName("user_id") val userId: Any? ,
//	@SerializedName("note_attributes") val noteAttributes: List<Any?>?,
//	@SerializedName("name") val name: String? ,
//	@SerializedName("cart_token") val cartToken: Any? ,
//	@SerializedName("total_tax_set") val totalTaxSet: TotalTaxSet? ,
//	@SerializedName("landing_site_ref") val landingSiteRef: Any? ,
//	@SerializedName("discount_codes") val discountCodes: List<Any?>? ,
//	@SerializedName("note") val note: Any? ,
//	@SerializedName("current_subtotal_price") val currentSubtotalPrice: String? ,
//	@SerializedName("current_total_tax_set") val currentTotalTaxSet: CurrentTotalTaxSet?,
//	@SerializedName("total_outstanding") val totalOutstanding: String? ,
//	@SerializedName("order_number") val orderNumber: Int? ,
//	@SerializedName("discount_applications") val discountApplications: List<Any?>?,
//	@SerializedName("created_at") val createdAt: String? ,
//	@SerializedName("total_line_items_price_set") val totalLineItemsPriceSet: TotalLineItemsPriceSet? ,
//	@SerializedName("taxes_included") val taxesIncluded: Boolean? ,
//	@SerializedName("buyer_accepts_marketing") val buyerAcceptsMarketing: Boolean?,
//	@SerializedName("confirmed") val confirmed: Boolean?,
//	@SerializedName("total_weight") val totalWeight: Int? ,
//	@SerializedName("contact_email") val contactEmail: String? ,
//	@SerializedName("refunds") val refunds: List<Any?>? ,
//	@SerializedName("total_discounts") val totalDiscounts: String? ,
//	@SerializedName("fulfillments") val fulfillments: List<Any?>? ,
//	@SerializedName("referring_site") val referringSite: Any?,
//	@SerializedName("updated_at") val updatedAt: String? ,
//	@SerializedName("processed_at") val processedAt: String?,
//	@SerializedName("currency") val currency: String? ,
//	@SerializedName("shipping_address") val shippingAddress: ShippingAddress? ,
//	@SerializedName("browser_ip") val browserIp: Any?,
//	@SerializedName("email") val email: String? ,
//	@SerializedName("source_name") val sourceName: String? ,
//	@SerializedName("total_price_set") val totalPriceSet: TotalPriceSet? ,
//	@SerializedName("current_total_duties_set") val currentTotalDutiesSet: Any? ,
//	@SerializedName("total_price") val totalPrice: String?,
//	@SerializedName("total_line_items_price") val totalLineItemsPrice: String? ,
//	@SerializedName("total_tip_received") val totalTipReceived: String?,
//	@SerializedName("token") val token: String? ,
//	@SerializedName("cancel_reason") val cancelReason: Any?,
//	@SerializedName("current_total_price") val currentTotalPrice: String?,
//	@SerializedName("admin_graphql_api_id") val adminGraphqlApiId: String? ,
//	@SerializedName("financial_status") val financialStatus: String? ,
//	@SerializedName("gateway") val gateway: String? ,
//	@SerializedName("customer") val customer: Customers?
//)
//
//data class TotalDiscountsSet(
//
//	@SerializedName("shop_money") val shopMoney: ShopMoney? ,
//	@SerializedName("presentment_money") val presentmentMoney: PresentmentMoney?
//)
//
//data class CurrentSubtotalPriceSet(
//
//	@field:SerializedName("shop_money")
//	val shopMoney: ShopMoney? ,
//
//	@field:SerializedName("presentment_money")
//	val presentmentMoney: PresentmentMoney?
//)
//
//data class TotalShippingPriceSet(
//
//	@SerializedName("shop_money") val shopMoney: ShopMoney? ,
//	@SerializedName("presentment_money") val presentmentMoney: PresentmentMoney?
//)
//
//data class ShopMoney(
//	@SerializedName("amount") val amount: String? ,
//	@SerializedName("currency_code") val currencyCode: String?
//)
//
//data class LineItemsItem(
//
//	@SerializedName("variant_title") val variantTitle: String? ,
//	@SerializedName("fulfillment_status") val fulfillmentStatus: Any? ,
//	@SerializedName("total_discount") val totalDiscount: String? ,
//	@SerializedName("gift_card") val giftCard: Boolean? ,
//	@SerializedName("requires_shipping") val requiresShipping: Boolean? ,
//	@SerializedName("total_discount_set") val totalDiscountSet: TotalDiscountSet?,
//	@SerializedName("title") val title: String?,
//	@SerializedName("product_exists") val productExists: Boolean? ,
//	@SerializedName("variant_id") val variantId: String?,
//	@SerializedName("tax_lines") val taxLines: List<Any?>?,
//	@SerializedName("price") val price: String? ,
//	@SerializedName("vendor") val vendor: String? ,
//	@SerializedName("product_id") val productId: Long? ,
//	@SerializedName("id") val id: String? ,
//	@SerializedName("grams") val grams: Int?,
//	@SerializedName("sku") val sku: String?,
//	@SerializedName("fulfillable_quantity") val fulfillableQuantity: Int? ,
//	@SerializedName("quantity") val quantity: Int?,
//	@SerializedName("fulfillment_service") val fulfillmentService: String? ,
//	@SerializedName("taxable") val taxable: Boolean?,
//	@SerializedName("variant_inventory_management") val variantInventoryManagement: String? ,
//	@SerializedName("discount_allocations") val discountAllocations: List<Any?>? ,
//	@SerializedName("admin_graphql_api_id") val adminGraphqlApiId: String?,
//	@SerializedName("name") val name: String? ,
//	@SerializedName("price_set") val priceSet: PriceSet? ,
//	@SerializedName("properties") val properties: List<Any?>? ,
//	@SerializedName("duties") val duties: List<Any?>?
//)
//
//data class BillingAddress(
//	@SerializedName("zip") val zip: String? ,
//	@SerializedName("country") val country: String? ,
//	@SerializedName("city") val city: String? ,
//	@SerializedName("address2") val address2: Any? ,
//	@SerializedName("address1") val address1: String? ,
//	@SerializedName("latitude") val latitude: Any? ,
//	@SerializedName("last_name") val lastName: String?,
//	@SerializedName("province_code") val provinceCode: String? ,
//	@SerializedName("country_code") val countryCode: String? ,
//	@SerializedName("province") val province: String? ,
//	@SerializedName("phone") val phone: String?,
//	@SerializedName("name") val name: String?,
//	@SerializedName("company") val company: Any? ,
//	@SerializedName("first_name") val firstName: String?,
//	@SerializedName("longitude") val longitude: Any?
//)
//
//data class SubtotalPriceSet(
//
//	@SerializedName("shop_money") val shopMoney: ShopMoney? ,
//	@SerializedName("presentment_money") val presentmentMoney: PresentmentMoney?
//)
//
//data class TotalLineItemsPriceSet(
//
//	@SerializedName("shop_money") val shopMoney: ShopMoney? ,
//	@SerializedName("presentment_money") val presentmentMoney: PresentmentMoney?
//)
