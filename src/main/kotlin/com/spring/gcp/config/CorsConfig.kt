package com.spring.gcp.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class CorsConfig(
    @Autowired
    private val environment: Environment
) {

    @Value("#{'\${cors.origins}'.split(',')}")
    private val origins: List<String> = emptyList()

    @Bean
    fun corsFilter(): CorsFilter {
        val config = CorsConfiguration()
        config.allowedOrigins = origins
        config.allowCredentials = true
        config.addAllowedMethod("*")
        config.addAllowedHeader("*")
        config.addExposedHeader("Set-Cookie")

        val configSource = UrlBasedCorsConfigurationSource()
        configSource.registerCorsConfiguration("/**", config)

        return CorsFilter(configSource)
    }

}