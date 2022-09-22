package com.shopping.orders.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "order_line_items")
data class OrderLineItem (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1,
    val skuCode: String,
    val price: Long,
    val quantity: Int
)
