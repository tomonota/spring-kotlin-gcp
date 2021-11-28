package com.spring.gcp.infrastructure.repository

import com.spring.gcp.domain.product.Product
import com.spring.gcp.domain.product.ProductId
import com.spring.gcp.domain.product.ProductName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

@Repository
class ProductRepository(
    @Autowired
    private val jdbcTemplate: JdbcTemplate
) {
    private val rowMapper = RowMapper { rs, _ ->
        Product(
            ProductId(rs.getInt("id")),
            ProductName(rs.getString("name"))
        )
    }

    fun findAll(): List<Product> {
        return jdbcTemplate.query(
            """
            select * from product
            """.trimIndent(),
            rowMapper
        )
    }
}