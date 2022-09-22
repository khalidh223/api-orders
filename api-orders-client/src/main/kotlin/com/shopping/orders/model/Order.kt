package com.shopping.orders.model

import javax.persistence.*

@Entity
@Table(name = "orders")
data class Order (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = -1,
    val orderNumber: String,
    @OneToMany(cascade = [CascadeType.ALL])
    val orderLineItemsList: List<OrderLineItem>
)