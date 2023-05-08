package com.example.kampusbackend.service

import com.example.kampusbackend.entity.StudentEntity
import com.example.kampusbackend.exception.StudentNotFoundException
import com.example.kampusbackend.repository.StudentRepository
import org.springframework.data.jpa.domain.AbstractPersistable_
import org.springframework.stereotype.Service


@Service
class StudentEntityService(
    private val studentRepository: StudentRepository,
){

    fun getStudent(id : Long) : StudentEntity {
        return studentRepository.findById(id).orElseThrow {
            StudentNotFoundException("Student with id $id not found")
        }
    }

    fun getAllStudents() : List<StudentEntity>{
        return studentRepository.findAll()
    }

    fun save(studentEntity: StudentEntity) {
        studentRepository.save(studentEntity)
    }
}