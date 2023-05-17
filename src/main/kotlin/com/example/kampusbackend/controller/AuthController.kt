package com.example.kampusbackend.controller

import com.example.kampusbackend.config.jwt.JwtUtils
import com.example.kampusbackend.dto.JwtResponse
import com.example.kampusbackend.dto.LoginRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "AuthController", description = "Отвечает за аутентификацию")
@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
	private val authenticationManager: AuthenticationManager,
	private val jwtUtils: JwtUtils,
) {
	@Value("\${user.username}")
	private lateinit var username: String

	@PostMapping("/login")
	@Operation(summary = "Аутентификация", description = "Аутентификация пользователя и создание jwt")
	fun authUser(@RequestBody loginRequest: LoginRequest): ResponseEntity<JwtResponse> {

		val authentication: Authentication = authenticationManager.authenticate(
			UsernamePasswordAuthenticationToken(
				username,
				loginRequest.password
			)
		)
		SecurityContextHolder.getContext().authentication = authentication
		val jwt: String = jwtUtils.generateJwtToken(authentication)

		return ResponseEntity.ok(
			JwtResponse(jwt)
		)
	}
}