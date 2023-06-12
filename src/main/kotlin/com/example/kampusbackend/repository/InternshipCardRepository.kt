package com.example.kampusbackend.repository

import com.example.kampusbackend.entity.InternshipCardEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface InternshipCardRepository: JpaRepository<InternshipCardEntity, Long> {
}