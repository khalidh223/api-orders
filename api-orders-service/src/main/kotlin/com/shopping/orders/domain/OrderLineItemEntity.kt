package com.shopping.orders.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "order_line_items")
data class OrderLineItemEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1,
    @Column(name = "sku_code")
    val skuCode: String,
    @Column(name = "price")
    val price: Long,
    @Column(name = "quantity")
    val quantity: Int,
    @Column(name = "order_id")
    val orderId: Long
)