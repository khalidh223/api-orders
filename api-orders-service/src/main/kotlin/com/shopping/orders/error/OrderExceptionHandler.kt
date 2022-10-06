package com.shopping.orders.error

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class OrderExceptionHandler {
    @ExceptionHandler
    fun handleException(exc: DuplicateLineItemRequestException): ResponseEntity<OrderErrorResponse> {
        val error = OrderErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            message = exc.message,
            timestamp = System.currentTimeMillis()
        )
        return ResponseEntity<OrderErrorResponse>(error, HttpStatus.BAD_REQUEST)
    }
    @ExceptionHandler
    fun handleException(exc: OrderNotFoundException): ResponseEntity<OrderErrorResponse> {
        val error = OrderErrorResponse(
            status = HttpStatus.NOT_FOUND.value(),
            message = exc.message,
            timestamp = System.currentTimeMillis()
        )
        return ResponseEntity<OrderErrorResponse>(error, HttpStatus.NOT_FOUND)
    }

}