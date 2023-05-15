package com.example.kampusbackend.config.jwt

import com.example.kampusbackend.config.CustomUserDetails
import org.springframework.security.core.userdetails.UserDetails
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KLogger
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

val logger: KLogger
    get() = KotlinLogging.logger {}

class AuthTokenFilter(
    private val jwtUtils: JwtUtils,
    private val customUserDetails: CustomUserDetails
) : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val jwt = parseJwt(request)
            when {
                jwt != null && jwtUtils.validateJwtToken(jwt) -> {
                    val username: String = jwtUtils.getUserNameFromJwtToken(jwt)
                    val userDetails: UserDetails = customUserDetails.loadUserByUsername(username)
                    val authenticationToken =
                        UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                    authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = authenticationToken
                }
            }
        } catch (e: Exception) {
            logger.error{"Cannot set user authentication: ${e.message}"}
        }
        filterChain.doFilter(request, response)
    }

    private fun parseJwt(request: HttpServletRequest): String? {
        val headerAuth = request.getHeader("Authorization")
        return when {
            StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ") -> {
                headerAuth.substring(7, headerAuth.length)
            }
            else -> null
        }
    }
}