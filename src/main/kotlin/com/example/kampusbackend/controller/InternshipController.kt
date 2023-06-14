package com.example.kampusbackend.controller

import com.example.kampusbackend.dto.InternshipCardDto
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

	@Operation(summary = "Получить список всех карточек со стажировкой")
	@SecurityRequirement(name = "JWT")
	@GetMapping("/getAllInternships")
	fun getAllInternshipCard(): List<InternshipCardEntity> = internshipCardEntityService.getAllInternshipCard()

	@Operation(summary = "Создать карточку со стажировкой по id hr")
	@SecurityRequirement(name = "JWT")
	@PostMapping("/createInternshipById/{hrID}")
	fun createInternshipById(@PathVariable hrID: Long, @RequestBody internshipCardDto: InternshipCardDto) {
		val hr: HrEntity? = hrEntityService.getHrById(hrID)
		createInternship(hr, internshipCardDto)
	}

	@Operation(summary = "Создать карточку со стажировкой по username hr")
	@SecurityRequirement(name = "JWT")
	@PostMapping("/createInternshipByUsername/{username}")
	fun createInternshipByUsername(@PathVariable username: String, @RequestBody internshipCardDto: InternshipCardDto) {
		val hr: HrEntity? = hrEntityService.getHrByUsername(username)
		createInternship(hr, internshipCardDto)
	}

	@Operation(summary = "Получить список всех карточек со стажировкой для hr по его id")
	@SecurityRequirement(name = "JWT")
	@GetMapping("/getAllInternshipsForID/{hrID}")
	fun getAllInternshipsForID(@PathVariable hrID: Long): String {
		return objectMapper.writeValueAsString(hrEntityService.getAllInternshipsById(hrID))
	}

	@Operation(summary = "Получить список всех карточек со стажировкой для hr по его username")
	@SecurityRequirement(name = "JWT")
	@GetMapping("/getAllInternshipsForUsername/{username}")
	fun getAllInternshipsForUsername(@PathVariable username: String): String {
		return objectMapper.writeValueAsString(hrEntityService.getAllInternshipsByUsername(username))
	}

	private fun createInternship(hr: HrEntity?, internshipCardDto: InternshipCardDto){
		hr?.let {
			val internshipCardEntity: InternshipCardEntity = internshipCardDto.toEntity(hr)
			hr.addNewInternshipCard(internshipCardEntity)
			internshipCardEntityService.saveInternshipCard(internshipCardEntity)
			hrEntityService.updateHrInternships(hr)
		}
	}
}