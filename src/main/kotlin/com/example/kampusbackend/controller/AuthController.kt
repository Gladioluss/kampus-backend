package com.example.kampusbackend.controller

import com.example.kampusbackend.config.jwt.JwtUtils
import com.example.kampusbackend.dto.JwtResponse
import com.example.kampusbackend.dto.LoginRequest
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
	private val authenticationManager: AuthenticationManager,
	private val jwtUtils: JwtUtils,
) {

	@PostMapping("/login")
	fun authUser(@RequestBody loginRequest: LoginRequest): ResponseEntity<JwtResponse> {

		val authentication: Authentication = authenticationManager.authenticate(
			UsernamePasswordAuthenticationToken(
				loginRequest.username,
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