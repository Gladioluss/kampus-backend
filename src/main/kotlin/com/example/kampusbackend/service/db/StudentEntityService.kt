package com.example.kampusbackend.service.db

import com.example.kampusbackend.dto.response.StudentDataDto
import com.example.kampusbackend.dto.StudentDto
import com.example.kampusbackend.entity.StudentEntity
import com.example.kampusbackend.entity.update
import com.example.kampusbackend.exception.UserNotFoundException
import com.example.kampusbackend.repository.StudentRepository
import org.springframework.stereotype.Service


@Service
class StudentEntityService(
	private val studentRepository: StudentRepository,
) {

	/**
	 * Returns [StudentEntity] from the database by the incoming [id].
	 * @param id unique identifier for the [StudentEntity]
	 * @throws [UserNotFoundException] if nothing was found for the specified [id]
	 */
	fun getStudentById(id: Long): StudentEntity = studentRepository.findById(id).orElseThrow {
		UserNotFoundException("Student with id $id not found")
	}

	/**
	 * Returns [StudentEntity] from the database by the incoming [username].
	 * @param username unique identifier for the [StudentEntity]
	 * @throws [UserNotFoundException] if nothing was found for the specified [username]
	 */
	fun getStudentByUsername(username: String): StudentEntity? = studentRepository.findByUsername(username)
		?: throw UserNotFoundException("Student with username $username not found")


	fun existsByUsername(username: String): Boolean = studentRepository.existsByUsername(username)

	/**
	 * Return a list of all instances of type [StudentEntity] from the database.
	 * @param username unique identifier for the [StudentEntity]
	 */
	fun getAllStudents(): List<StudentEntity> = studentRepository.findAll()

	/**
	 * Returns the number ([Long]) of students from the database.
	 */
	fun getCountStudents(): Long = studentRepository.count()

	/**
	 * Returns the number of students for each university as a mutableList of [StudentDataDto]
	 */
	fun getInfoUniversities(): MutableList<StudentDataDto> = groupingData(studentRepository.getAllUniversities())

	/**
	 * Returns the number of students for each form of education (budget/contract/target)
	 * as a mutableList of [StudentDataDto]
	 */
	fun getInfoEducationForm(): MutableList<StudentDataDto> = groupingData(studentRepository.getAllEducationForm())

	/**
	 * Returns the number of students for each form of education (full-time/part-time)
	 * as a mutableList of [StudentDataDto]
	 */
	fun getInfoHighEducation(): MutableList<StudentDataDto> = groupingData(studentRepository.getAllTypeHighEducation())

	/**
	 * Returns the number of students for each course as a mutableList of [StudentDataDto]
	 */
	fun getInfoCourseTitle(): MutableList<StudentDataDto> = groupingData(studentRepository.getAllCourseTitle())

	/**
	 * Save an object of type [StudentEntity] to the database.
	 * @param studentEntity
	 */
	fun saveStudent(studentEntity: StudentEntity) {
		studentRepository.save(studentEntity)
	}

	fun updateStudent(id: Long, data: StudentDto) {
		val student = getStudentById(id)
		studentRepository.save(student.update(data))
	}

	fun deleteStudentById(studentId: Long) {
		studentRepository.deleteById(studentId)
	}

	private fun groupingData(data: List<String>): MutableList<StudentDataDto> {
		val list = mutableListOf<StudentDataDto>()
		data.groupingBy { it }.eachCount().forEach { entry ->
			list.add(StudentDataDto(entry.key, entry.value.toString()))
		}
		return list
	}
}