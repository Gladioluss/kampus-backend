package com.example.kampusbackend.dto

import com.example.kampusbackend.entity.HrEntity
import com.example.kampusbackend.entity.InternshipCardEntity
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Сущность карточки стажировки")
data class InternshipCardDto(
	var internshipTitle: String,
	var organizationName: String,
	var internshipDescription: String,
	var internshipSpecialization: String,
	var internshipSchedule: String,
	var internshipType: String,
)

fun InternshipCardDto.toEntity(hr: HrEntity) = InternshipCardEntity(
	internshipTitle = internshipTitle,
	organizationName = organizationName,
	internshipDescription = internshipDescription,
	internshipSpecialization = internshipSpecialization,
	internshipSchedule= internshipSchedule,
	internshipType = internshipType,
	hr = hr,
	hrEmail = hr.email,
)