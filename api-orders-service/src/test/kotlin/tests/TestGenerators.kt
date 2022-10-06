package tests

import com.shopping.orders.model.OrderLineItemDto
import com.shopping.orders.model.OrderRequest
import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.long
import io.kotest.property.arbitrary.next
import io.kotlintest.properties.Gen
import kotlin.random.Random

fun Gen.Companion.createOrderRequest() = object : Gen<OrderRequest> {
    override fun constants() = emptyList<OrderRequest>()

    override fun random(): Sequence<OrderRequest> = generateSequence {
        OrderRequest(
            orderLineItemsDtoList = listOf(Gen.orderLineItemDto().random().first())
        )
    }
}

fun Gen.Companion.orderLineItemDto() = object : Gen<OrderLineItemDto> {
    override fun constants() = emptyList<OrderLineItemDto>()

    override fun random(): Sequence<OrderLineItemDto> = generateSequence {
        OrderLineItemDto(
            skuCode = string().random().first().take(8),
            price = Arb.long(1, 32767).next(),
            quantity = Arb.int(1, 127).next()
        )
    }
}