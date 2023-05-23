package com.example.kampusbackend.controller

import com.example.kampusbackend.entity.DynamicProgressEntity
import com.example.kampusbackend.repository.DynamicProgressRepository
import com.example.kampusbackend.service.DynamicProgressEntityService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@SecurityRequirement(name = "JWT")
@Tag(name = "DynamicProgressController", description = "Взаимодействие с таблицей 'dynamic_progress'")
@RestController
@RequestMapping("/api/v1/dynamicProgress")
class DynamicProgressController(
	private val dynamicProgressEntityService : DynamicProgressEntityService
) {

	@SecurityRequirement(name = "JWT")
	@Operation(summary = "Получение статистики прогресса за все время")
	@GetMapping("/getAllDynamicProgress")
	fun getAllDynamicProgress(): List<DynamicProgressEntity> = dynamicProgressEntityService.getAllDynamicProgress()

	@SecurityRequirement(name = "JWT")
	@Operation(summary = "Получение статистики прогресса за конкретную дату")
	@GetMapping("/getDynamicProgress/{date}")
	fun getDynamicProgressByDate(@PathVariable date: LocalDate): DynamicProgressEntity = dynamicProgressEntityService.getDynamicProgressByDate(date)

}