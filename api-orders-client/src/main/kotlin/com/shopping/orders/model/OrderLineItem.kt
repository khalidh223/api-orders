package com.shopping.orders.model

data class OrderLineItemDto(
    val skuCode: String,
    val price: Long,
    val quantity: Int
)