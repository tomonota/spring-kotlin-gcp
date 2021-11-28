package com.spring.gcp.controller

import com.google.gson.Gson
import com.spring.gcp.infrastructure.repository.ProductRepository
import com.spring.gcp.infrastructure.repository.UserRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ApiController(
    private val userRepository: UserRepository,
    private val productRepository: ProductRepository
) {

    data class User(
        val id: Int,
        val name: String
    )

    data class Product(
        val productId: Int,
        val productName: String
    )

    @GetMapping("/api/users")
    fun users(): String {
        val gson = Gson()
        val users = userRepository.findAll().map {
            User(
                it.id.value,
                it.name.value
            )
        }
        return gson.toJson(users)
    }

    @GetMapping("/api/products")
    fun products(): String {
        val gson = Gson()
        val products = productRepository.findAll().map {
            Product(
                it.productId.value,
                it.productName.value
            )
        }
        return gson.toJson(products)
    }

}
