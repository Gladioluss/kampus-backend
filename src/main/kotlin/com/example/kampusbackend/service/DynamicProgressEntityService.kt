package com.example.kampusbackend.service

import com.example.kampusbackend.entity.DynamicProgressEntity
import com.example.kampusbackend.entity.StudentEntity
import com.example.kampusbackend.exception.DynamicProgressNotFoundException
import com.example.kampusbackend.exception.StudentNotFoundException
import com.example.kampusbackend.repository.DynamicProgressRepository
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import java.time.LocalDate
import kotlin.jvm.Throws

@Service
class DynamicProgressEntityService(
	private val dynamicProgressRepository: DynamicProgressRepository
) {
	fun getAllDynamicProgress(): List<DynamicProgressEntity> = dynamicProgressRepository.findAll()

	fun getDynamicProgressByDate(date: LocalDate): DynamicProgressEntity {
		try {
			return dynamicProgressRepository.findByProgressDate(date)
		}
		catch (e: EmptyResultDataAccessException) {
			throw DynamicProgressNotFoundException("It was not possible to find statistics for $date")
		}
	}
}