package com.example.kampusbackend.service

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
	 * @return [StudentEntity]
	 * @throws [StudentNotFoundException] if nothing was found for the specified [id]
	 */
	fun getStudentById(id: Long): StudentEntity = studentRepository.findById(id).orElseThrow {
		StudentNotFoundException("Student with id $id not found")
	}

	/**
	 * Return a list of all instances of type [StudentEntity] from the database.
	 * @return a  List<[StudentEntity]>
	 */
	fun getAllStudents(): List<StudentEntity> = studentRepository.findAll()

	fun getCountStudents(): Long = studentRepository.count()

	fun getInfoUniversities(): Map<String, Int> = groupingBy(studentRepository.getAllUniversities())

	fun getInfoEducationForm(): Map<String, Int> = groupingBy(studentRepository.getAllEducationForm())

	fun getInfoHighEducation(): Map<String, Int> = groupingBy(studentRepository.getAllTypeHighEducation())

	fun getInfoCourseTitle(): Map<String, Int> = groupingBy(studentRepository.getAllCourseTitle())

	/**
	 * Save an object of type [StudentEntity] to the database.
	 */
	fun save(studentEntity: StudentEntity) {
		studentRepository.save(studentEntity)
	}

	private fun groupingBy(data: List<String>): Map<String, Int> = data.groupingBy { it }.eachCount()
}