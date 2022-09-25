package com.shopping.orders.repository
import org.springframework.data.jpa.repository.JpaRepository
import com.shopping.orders.domain.OrderEntity

interface OrderRepository: JpaRepository<OrderEntity, Long> {}