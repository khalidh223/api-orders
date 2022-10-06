package com.shopping.orders.service

import com.shopping.orders.domain.OrderEntity
import com.shopping.orders.domain.OrderLineItemEntity
import com.shopping.orders.model.OrderRequest

interface OrderService {
    fun placeOrder(orderRequest: OrderRequest)
    fun getOrder(orderNumber: String): List<OrderLineItemEntity>
}