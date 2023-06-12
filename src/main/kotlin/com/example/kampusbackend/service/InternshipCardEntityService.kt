package com.example.kampusbackend.service

import com.example.kampusbackend.entity.InternshipCardEntity
import com.example.kampusbackend.entity.StudentEntity
import com.example.kampusbackend.repository.InternshipCardRepository
import org.springframework.stereotype.Service

@Service
class InternshipCardEntityService(
	private val internshipCardRepository: InternshipCardRepository
) {
	fun getAllInternshipCard(): List<InternshipCardEntity> = internshipCardRepository.findAll()

	fun saveInternshipCard(internshipCardEntity: InternshipCardEntity) {
		internshipCardRepository.save(internshipCardEntity)
	}

}