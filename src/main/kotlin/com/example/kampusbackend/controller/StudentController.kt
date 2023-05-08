package com.example.kampusbackend.controller

import com.example.kampusbackend.dto.StudentDto
import com.example.kampusbackend.dto.toEntity
import com.example.kampusbackend.entity.StudentEntity
import com.example.kampusbackend.exception.StudentNotFoundException
import com.example.kampusbackend.service.StudentEntityService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/student")
class StudentController(
    private val studentEntityService: StudentEntityService,
) {

    @GetMapping("/getStudent/{studentId}")
    fun getStudent(@PathVariable studentId: Long): StudentEntity {
        return studentEntityService.getStudent(studentId)
    }

    @GetMapping("/getAllStudents")
    fun getAllStudents(): List<StudentEntity> {
        return studentEntityService.getAllStudents()
    }

    @PostMapping("/saveStudent")
    fun saveStudent(@RequestBody studentDto: StudentDto): ResponseEntity<String> {
        studentEntityService.save(studentDto.toEntity())
        return ResponseEntity.ok(
            "Student successfully added to the table"
        )
    }
}