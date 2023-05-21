package com.example.kampusbackend.dto

import com.example.kampusbackend.entity.StudentEntity
import com.example.kampusbackend.utils.LocalDateSerializer
import jakarta.persistence.Column
import kotlinx.serialization.Serializable
import java.time.LocalDate


data class DynamicProgressDto(
	@Serializable(LocalDateSerializer::class)
	var progressDate: LocalDate? = null,
	var numberStudents: Long? = null,
	var totalAmountScholarship: Long? = null,
)
fun DynamicProgressDto.toEntity() = DynamicProgressDto(
	progressDate = progressDate,
	numberStudents = numberStudents,
	totalAmountScholarship = totalAmountScholarship,
)