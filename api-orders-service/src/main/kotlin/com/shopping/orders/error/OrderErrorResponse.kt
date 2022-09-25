package com.shopping.orders.error

data class OrderErrorResponse(
    val status: Int,
    val message: String?,
    val timestamp: Long
)