package com.shopping.orders.service

import com.shopping.orders.domain.OrderEntity
import com.shopping.orders.domain.OrderLineItemEntity
import com.shopping.orders.error.DuplicateLineItemRequestException
import com.shopping.orders.error.OrderNotFoundException
import com.shopping.orders.model.OrderLineItemDto
import com.shopping.orders.model.OrderRequest
import com.shopping.orders.model.OrderResponse
import com.shopping.orders.repository.OrderLineItemRepository
import com.shopping.orders.repository.OrderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class OrderServiceImpl(
    private val orderRepository: OrderRepository,
    private val orderLineItemRepository: OrderLineItemRepository
) : OrderService {

    @Transactional
    override fun placeOrder(orderRequest: OrderRequest): OrderResponse {
        val order = orderRepository.save(OrderEntity(orderNumber = UUID.randomUUID().toString()))
        val orderLineItemEntities = orderRequest.orderLineItemsDtoList.map { it.toEntity(order) }
        val orderLineItemEntitiesSkuCodes = orderLineItemEntities.map { it.skuCode }
        if (orderLineItemEntitiesSkuCodes.size != orderLineItemEntitiesSkuCodes.distinct().count()) {
            throw DuplicateLineItemRequestException("Request contains duplicate line items by sku code")
        }
        orderLineItemRepository.saveAll(orderLineItemEntities)
        return order.toResponse()
    }

    override fun getOrder(orderNumber: String): List<OrderLineItemEntity> {
        val orderEntity =  orderRepository.getByOrderNumber(orderNumber)
            ?: throw OrderNotFoundException("Order with order number $orderNumber is not found")

        return orderLineItemRepository.findAllByOrderId(orderEntity.id)
    }

    private fun OrderLineItemDto.toEntity(order: OrderEntity) =
        OrderLineItemEntity(
            skuCode = this.skuCode,
            price = this.price,
            quantity = this.quantity,
            orderId = order.id
        )

    private fun OrderEntity.toResponse() =
        OrderResponse(
            id = this.id,
            orderNumber = this.orderNumber
        )
}