package com.example.kampusbackend.service

import com.example.kampusbackend.entity.HrEntity
import com.example.kampusbackend.entity.InternshipCardEntity
import com.example.kampusbackend.exception.UserNotFoundException
import com.example.kampusbackend.repository.HrRepository
import org.springframework.stereotype.Service

@Service
class HrEntityService(
	private val hrRepository: HrRepository,
) {
	fun getHrById(id: Long): HrEntity? = hrRepository.findById(id).orElseThrow {
		UserNotFoundException("Hr with id $id not found")
	}

	fun getHrByUsername(username: String): HrEntity? = hrRepository.findByUsername(username)

	fun existsByUsername(username: String): Boolean = hrRepository.existsByUsername(username)

	fun saveHr(hrEntity: HrEntity) {
		hrRepository.save(hrEntity)
	}

	fun getAllInternshipsById(id: Long): MutableList<InternshipCardEntity>? {
		val hr = getHrById(id)
		return hr?.internshipCards
	}

	fun getAllInternshipsByUsername(username: String): MutableList<InternshipCardEntity>? {
		val hr = getHrByUsername(username)
		return hr?.internshipCards
	}

	fun updateHrInternships(hrEntity: HrEntity) {
		hrRepository.save(hrEntity)
	}
}