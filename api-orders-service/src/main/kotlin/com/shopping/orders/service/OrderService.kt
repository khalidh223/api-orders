package com.shopping.orders.service

import com.shopping.orders.domain.OrderLineItemEntity
import com.shopping.orders.model.OrderRequest
import com.shopping.orders.model.OrderResponse

interface OrderService {
    fun placeOrder(orderRequest: OrderRequest): OrderResponse
    fun getOrder(orderNumber: String): List<OrderLineItemEntity>
}