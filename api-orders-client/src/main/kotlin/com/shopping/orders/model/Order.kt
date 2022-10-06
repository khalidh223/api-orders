package com.shopping.orders.model

data class OrderRequest(val orderLineItemsDtoList: List<OrderLineItemDto>)

data class OrderResponse(
    val id: Long,
    val orderNumber: String
)

