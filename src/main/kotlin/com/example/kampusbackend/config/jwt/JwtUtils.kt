package com.example.kampusbackend.config.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import java.util.*


@Component
class JwtUtils {
    @Value("\${jwt.secret}")
    private lateinit var jwtSecret: String

    @Value("\${jwt.expiration}")
    private lateinit var jwtExpiration: String

    fun generateJwtToken(authentication: Authentication): String {
        val userPrincipal: User = authentication.principal as User
        val expirationTimeMillis = Date().time + jwtExpiration.toLong()
        val expirationDate = Date(expirationTimeMillis)
        return Jwts.builder()
            .setSubject(userPrincipal.username)
            .setIssuedAt(Date())
            .setExpiration(expirationDate)
            .signWith(Keys.hmacShaKeyFor(jwtSecret.toByteArray()), SignatureAlgorithm.HS256)
            .compact()
    }

    fun validateJwtToken(jwt: String): Boolean {
        try {
            Jwts.parserBuilder().setSigningKey(jwtSecret.toByteArray()).build()
                .parseClaimsJws(jwt)
            return true
        } catch (e: MalformedJwtException) {
            System.err.println(e.message)
        } catch (e: IllegalArgumentException) {
            System.err.println(e.message)
        }
        return false
    }

    fun getUserNameFromJwtToken(jwt: String): String {
        return Jwts.parserBuilder().setSigningKey(jwtSecret.toByteArray())
            .build().parseClaimsJws(jwt).body.subject
    }
}