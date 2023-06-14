package com.example.kampusbackend.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class JwtResponse(
    val token: String,
	val username: String,
	val role: String?,
) {
	private val type: String = "Bearer"
}
