package com.spring.gcp.infrastructure.repository

import com.spring.gcp.domain.user.User
import com.spring.gcp.domain.user.UserId
import com.spring.gcp.domain.user.UserName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class UserRepository (
    @Autowired
    private val jdbcTemplate: JdbcTemplate
) {
    private val rowMapper = RowMapper<User> { rs, _ ->
        User(
            UserId(rs.getInt("id")),
            UserName(rs.getString("name"))
        )
    }

    fun findAll(): List<User> {
        return jdbcTemplate.query(
            """
            select * from user
            """.trimIndent(),
            rowMapper
        )
    }

}