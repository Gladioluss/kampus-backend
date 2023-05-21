package com.example.kampusbackend.service

import com.example.kampusbackend.entity.DynamicProgressEntity
import com.example.kampusbackend.entity.StudentEntity
import com.example.kampusbackend.exception.DynamicProgressNotFoundException
import com.example.kampusbackend.exception.StudentNotFoundException
import com.example.kampusbackend.repository.DynamicProgressRepository
import org.springframework.stereotype.Service

@Service
class DynamicProgressEntityService(
	private val dynamicProgressRepository: DynamicProgressRepository
) {
	fun getStudentById(id: Long): DynamicProgressEntity = dynamicProgressRepository.findById(id).orElseThrow {
		DynamicProgressNotFoundException("Student with id $id not found")
	}

	fun getAllDynamicProgress(): List<DynamicProgressEntity> = dynamicProgressRepository.findAll()
}