package com.shopping.orders

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
class ApiOrdersApplication

fun main(args: Array<String>) {
	runApplication<ApiOrdersApplication>(*args)
}
