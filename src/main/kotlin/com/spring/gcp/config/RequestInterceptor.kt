package com.spring.gcp.config

import com.google.firebase.auth.FirebaseAuthException
import com.spring.gcp.config.firebase.FirebaseAuthInstance
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class RequestInterceptor(
    private val firebaseAuthInstance: FirebaseAuthInstance
) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val token: String? = request.getHeader("IdToken")
        val result = isValidToken(token)
        if (!result) {
            response.status = HttpStatus.FORBIDDEN.value()
        }
        return result
    }

    private fun isValidToken(token: String?): Boolean {
        if (token == null) return false
        val firebaseAuth = firebaseAuthInstance.instance

        return try {
            firebaseAuth.verifyIdToken(token)
            true
        } catch (e: FirebaseAuthException) {
            false
        }
    }
}
