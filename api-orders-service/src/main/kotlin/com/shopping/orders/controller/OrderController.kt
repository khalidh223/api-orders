package com.shopping.orders.controller

import com.shopping.orders.domain.OrderEntity
import com.shopping.orders.domain.OrderLineItemEntity
import com.shopping.orders.model.OrderRequest
import com.shopping.orders.model.OrderResponse
import com.shopping.orders.service.OrderService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/order")
class OrderController(private val orderService: OrderService) {

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    fun placeOrder(@RequestBody orderRequest: OrderRequest): OrderResponse {
        return orderService.placeOrder(orderRequest)
    }

    @GetMapping("{order_number}")
    @ResponseStatus(HttpStatus.OK)
    fun getOrder(@PathVariable("order_number") orderNumber: String): List<OrderLineItemEntity> {
        return orderService.getOrder(orderNumber)
    }

}