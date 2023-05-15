package com.example.kampusbackend.repository

import com.example.kampusbackend.entity.StudentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface StudentRepository : JpaRepository<StudentEntity, Long> {

    @Query("SELECT universityName FROM StudentEntity")
    fun getAllUniversities(): List<String>

    @Query("SELECT educationForm FROM StudentEntity")
    fun getAllEducationForm(): List<String>
}