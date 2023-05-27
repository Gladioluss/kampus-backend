package com.example.kampusbackend.service

import com.example.kampusbackend.dto.StudentDataDto
import com.example.kampusbackend.entity.StudentEntity
import com.example.kampusbackend.exception.StudentNotFoundException
import com.example.kampusbackend.repository.StudentRepository
import org.springframework.stereotype.Service


@Service
class StudentEntityService(
	private val studentRepository: StudentRepository,
) {

	/**
	 * Returns [StudentEntity] from the database by the incoming [id].
	 * @param id unique identifier for the [StudentEntity]
	 * @throws [StudentNotFoundException] if nothing was found for the specified [id]
	 */
	fun getStudentById(id: Long): StudentEntity = studentRepository.findById(id).orElseThrow {
		StudentNotFoundException("Student with id $id not found")
	}
	/**
	 * Return a list of all instances of type [StudentEntity] from the database.
	 */
	fun getAllStudents(): List<StudentEntity> = studentRepository.findAll()

	fun getCountStudents(): Long = studentRepository.count()

	fun getInfoUniversities(): MutableList<StudentDataDto> = groupingBy(studentRepository.getAllUniversities())

	fun getInfoEducationForm(): MutableList<StudentDataDto> = groupingBy(studentRepository.getAllEducationForm())

	fun getInfoHighEducation(): MutableList<StudentDataDto> = groupingBy(studentRepository.getAllTypeHighEducation())

	fun getInfoCourseTitle(): MutableList<StudentDataDto> = groupingBy(studentRepository.getAllCourseTitle())

	/**
	 * Save an object of type [StudentEntity] to the database.
	 */
	fun save(studentEntity: StudentEntity) {
		studentRepository.save(studentEntity)
	}


	private fun groupingBy(data: List<String>): MutableList<StudentDataDto> {
		val list = mutableListOf<StudentDataDto>()
		data.groupingBy { it }.eachCount().forEach { entry ->
			list.add(StudentDataDto(entry.key, entry.value.toString()))
		}
		return list
	}
}