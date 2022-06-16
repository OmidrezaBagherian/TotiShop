package com.omidrezabagherian.totishop.domain.model.order

data class Order(
    val _links: Links,
    val billing: Billing,
    val cart_hash: String,
    val cart_tax: String,
    val coupon_lines: List<CouponLine>,
    val created_via: String,
    val currency: String,
    val currency_symbol: String,
    val customer_id: Int,
    val customer_ip_address: String,
    val customer_note: String,
    val customer_user_agent: String,
    val date_completed: String,
    val date_completed_gmt: String,
    val date_created: String,
    val date_created_gmt: String,
    val date_modified: String,
    val date_modified_gmt: String,
    val date_paid: String,
    val date_paid_gmt: String,
    val discount_tax: String,
    val discount_total: String,
    val fee_lines: List<Any>,
    val id: Int,
    val line_items: List<LineItem>,
    val meta_data: List<MetaDataX>,
    val number: String,
    val order_key: String,
    val parent_id: Int,
    val payment_method: String,
    val payment_method_title: String,
    val prices_include_tax: Boolean,
    val refunds: List<Any>,
    val shipping: Shipping,
    val shipping_lines: List<Any>,
    val shipping_tax: String,
    val shipping_total: String,
    val status: String,
    val tax_lines: List<Any>,
    val total: String,
    val total_tax: String,
    val transaction_id: String,
    val version: String
)