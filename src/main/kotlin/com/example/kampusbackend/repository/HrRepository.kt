package com.example.kampusbackend.repository

import com.example.kampusbackend.entity.HrEntity
import com.example.kampusbackend.entity.StudentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface HrRepository: JpaRepository<HrEntity, Long> {
	fun findByUsername(username: String) : HrEntity?

	fun existsByUsername(username: String): Boolean
}