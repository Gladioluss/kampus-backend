package com.example.kampusbackend.service.db

import com.example.kampusbackend.entity.InternshipCardEntity
import com.example.kampusbackend.entity.StudentEntity
import com.example.kampusbackend.exception.UserNotFoundException
import com.example.kampusbackend.repository.InternshipCardRepository
import org.springframework.stereotype.Service

@Service
class InternshipCardEntityService(
	private val internshipCardRepository: InternshipCardRepository
) {
	fun getInternshipCardById(id: Long): InternshipCardEntity? = internshipCardRepository.findById(id)
		.orElseThrow {
			UserNotFoundException("Internship card with id $id not found")
		}

	fun getAllInternshipCard(): List<InternshipCardEntity> = internshipCardRepository.findAll()

	fun saveInternshipCard(internshipCardEntity: InternshipCardEntity) {
		internshipCardRepository.save(internshipCardEntity)
	}

}