package com.example.kampusbackend.dto

import com.example.kampusbackend.entity.HrEntity
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.Column

@Schema(description = "Сущность hr`a")
data class HrDto(
	var username: String,
	var password: String,
)

fun HrDto.toEntity() = HrEntity(
	username = username,
	password = password,
)