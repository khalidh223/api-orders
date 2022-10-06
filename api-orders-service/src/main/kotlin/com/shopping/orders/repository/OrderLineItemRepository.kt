package com.shopping.orders.repository

import com.shopping.orders.domain.OrderLineItemEntity
import org.springframework.data.jpa.repository.JpaRepository

interface OrderLineItemRepository : JpaRepository<OrderLineItemEntity, Long> {
    fun findAllByOrderId(orderId: Long): List<OrderLineItemEntity>
}
