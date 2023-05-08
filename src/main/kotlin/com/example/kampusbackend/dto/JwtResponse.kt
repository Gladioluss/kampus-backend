package com.example.kampusbackend.dto

import kotlinx.serialization.Serializable

@Serializable
data class JwtResponse(
    val token: String,
) {

	private val type: String = "Bearer"
}
