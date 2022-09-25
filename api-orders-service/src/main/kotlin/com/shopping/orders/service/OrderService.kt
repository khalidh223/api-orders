package com.shopping.orders.service

import com.shopping.orders.model.OrderRequest

interface OrderService {
    fun placeOrder(orderRequest: OrderRequest)
}