package com.shopping.orders.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.shopping.orders.domain.OrderLineItemEntity
import com.shopping.orders.model.OrderLineItemDto
import com.shopping.orders.repository.OrderLineItemRepository
import com.shopping.orders.repository.OrderRepository
import io.kotlintest.inspectors.forAll
import io.kotlintest.inspectors.forOne
import io.kotlintest.matchers.collections.shouldHaveSize
import io.kotlintest.properties.Gen
import io.kotlintest.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.testcontainers.junit.jupiter.Testcontainers
import tests.createOrderRequest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@AutoConfigureMockMvc
class OrderControllerComponentTests(
    @Autowired private val orderRepository: OrderRepository,
    @Autowired private val orderLineItemRepository: OrderLineItemRepository,
    @Autowired private val objectMapper: ObjectMapper,
    @Autowired private val context: WebApplicationContext,
) {
    private val mvc: MockMvc = (MockMvcBuilders.webAppContextSetup(context).build())

    @BeforeEach
    fun setup() {
        orderLineItemRepository.deleteAll()
        orderRepository.deleteAll()
    }

    @Test
    fun `POST an order should return 201 Created and creates an order with one line item`() {
        //given
        val request = Gen.createOrderRequest().random().first()

        //when
        mvc.perform(
            MockMvcRequestBuilders.post("/api/order")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isCreated)

        //then
        orderRepository.findAll() shouldHaveSize 1
        val orderSaved = orderRepository.findAll().first()
        orderLineItemRepository.findAll().forEach { orderLineItemEntity ->
            orderLineItemEntity.orderId shouldBe orderSaved.id
        }

        val requestOrderLineItemEntities = request.orderLineItemsDtoList.map { orderLineItemDto ->
            OrderLineItemEntity(
                skuCode = orderLineItemDto.skuCode,
                price = orderLineItemDto.price,
                quantity = orderLineItemDto.quantity,
                orderId = orderSaved.id
            )
        }

        requestOrderLineItemEntities.forAll { requestOrderLineItemEntity ->
            orderLineItemRepository.findAll().forOne { orderLineItemEntity ->
                requestOrderLineItemEntity shouldBe OrderLineItemEntity(
                    id = requestOrderLineItemEntity.id,
                    skuCode = orderLineItemEntity.skuCode,
                    price = orderLineItemEntity.price,
                    orderId = orderLineItemEntity.orderId,
                    quantity = orderLineItemEntity.quantity
                )
            }
        }

    }

    @Test
    fun `POST an order should return 400 Bad Request if a duplicate order line item (by skuCode) was provided in the request`() {
        //given
        val request = Gen.createOrderRequest().random().first()
        val modifiedRequest = request.copy(orderLineItemsDtoList = request.orderLineItemsDtoList.plus(OrderLineItemDto(
            skuCode = request.orderLineItemsDtoList.first().skuCode,
            price = 100,
            quantity = 2
        )))

        //when
        mvc.perform(
            MockMvcRequestBuilders.post("/api/order")
                .content(objectMapper.writeValueAsString(modifiedRequest))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest)

        //then
        orderRepository.findAll() shouldHaveSize 0
        orderLineItemRepository.findAll() shouldHaveSize 0

    }
}