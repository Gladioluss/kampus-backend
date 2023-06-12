package com.example.kampusbackend.controller

import com.example.kampusbackend.dto.InternshipCardDto
import com.example.kampusbackend.dto.StudentDto
import com.example.kampusbackend.dto.toEntity
import com.example.kampusbackend.entity.HrEntity
import com.example.kampusbackend.entity.InternshipCardEntity
import com.example.kampusbackend.service.HrEntityService
import com.example.kampusbackend.service.InternshipCardEntityService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@SecurityRequirement(name = "JWT")
@Tag(name = "InternshipController", description = "Взаимодействие с таблицей 'internship_card'")
@RestController
@RequestMapping("/api/v1/internship")
class InternshipController(
	private val internshipCardEntityService: InternshipCardEntityService,
	private val hrEntityService: HrEntityService,
) {
	val objectMapper = ObjectMapper().apply {
		registerModule(JavaTimeModule())
	}

	@Operation(
		summary = "Получить список всех карточек со стажировкой",
		description = "Получить список всех карточек со стажировкой")
	@GetMapping("/getAllInternships")
	fun getAllInternshipCard(): List<InternshipCardEntity> = internshipCardEntityService.getAllInternshipCard()

	@Operation(
		summary = "Создать карточку со стажировкой",
		description = "Создать карточку со стажировкой")
	@PostMapping("/createInternship/{hrID}")
	fun createInternship(@PathVariable hrID: Long, @RequestBody internshipCardDto: InternshipCardDto) {
		val hr : HrEntity = hrEntityService.getHrById(hrID)
		hr.addNewInternshipCard(internshipCardDto.toEntity(hr))
		internshipCardEntityService.saveInternshipCard(internshipCardDto.toEntity(hr))
		hrEntityService.updateHrInternships(hrID, hr)
	}

	@Operation(
		summary = "Получить список всех карточек со стажировкой для hr по его id",
		description = "Получить список всех карточек со стажировкой для hr по его id")
	@SecurityRequirement(name = "JWT")
	@GetMapping("/getAllInternshipsFor/{hrID}")
	fun getAllInternshipsFor(@PathVariable hrID: Long) : String{
		val a = objectMapper.writeValueAsString(hrEntityService.getAllInternships(hrID))
		println(a)
		return a
	}
}