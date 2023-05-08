package com.example.kampusbackend.repository

import com.example.kampusbackend.entity.StudentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface StudentRepository : JpaRepository<StudentEntity, Long> {
    fun findByFirstName(firstname: String): StudentEntity?
}