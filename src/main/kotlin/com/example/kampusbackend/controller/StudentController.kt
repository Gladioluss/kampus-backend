package com.example.kampusbackend.controller

import com.example.kampusbackend.dto.StudentDto
import com.example.kampusbackend.dto.toEntity
import com.example.kampusbackend.entity.StudentEntity
import com.example.kampusbackend.service.StudentEntityService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/student")
class StudentController(
	private val studentEntityService: StudentEntityService,
) {
	@GetMapping("/getStudent/{studentId}")
	fun getStudentById(@PathVariable studentId: Long): StudentEntity {
		return studentEntityService.getStudentById(studentId)
	}

	@GetMapping("/getAllStudents")
	fun getAllStudents(): List<StudentEntity> {
		return studentEntityService.getAllStudents()
	}

	@GetMapping("/getCountStudents")
	fun getCountStudents(): Long {
		return studentEntityService.getCountStudents()
	}

	@GetMapping("/getInfoUniversities")
	fun getInfoUniversities(): Map<String, Int> {
		return studentEntityService.getInfoUniversities()
	}

	@GetMapping("/getInfoEducationForm")
	fun getInfoEducationForm(): Map<String, Int> {
		return studentEntityService.getInfoEducationForm()
	}

	@GetMapping("/getInfoHighEducation")
	fun getInfoHighEducation(): Map<String, Int> {
		return studentEntityService.getInfoHighEducation()
	}

	@GetMapping("/getInfoCourseTitle")
	fun getInfoCourseTitle(): Map<String, Int> {
		return studentEntityService.getInfoCourseTitle()
	}

	@PostMapping("/saveStudent")
	fun saveStudent(@RequestBody studentDto: StudentDto): ResponseEntity<String> {
		studentEntityService.save(studentDto.toEntity())
		return ResponseEntity.ok(
			"Student successfully added to the table"
		)
	}
}