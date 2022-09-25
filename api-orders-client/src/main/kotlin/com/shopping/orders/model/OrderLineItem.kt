package com.shopping.orders.model

import com.shopping.orders.domain.OrderEntity
import java.util.UUID

data class OrderLineItemDto(
    val skuCode: String,
    val price: Long,
    val quantity: Int
)