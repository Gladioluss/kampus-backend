package com.example.kampusbackend.service

import com.example.kampusbackend.entity.StudentEntity
import com.example.kampusbackend.exception.StudentNotFoundException
import com.example.kampusbackend.repository.StudentRepository
import org.springframework.data.jpa.domain.AbstractPersistable_
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
	fun getStudentById(id: Long): StudentEntity {
		return studentRepository.findById(id).orElseThrow {
			StudentNotFoundException("Student with id $id not found")
		}
	}

	/**
	 * Return a list of all instances of type [StudentEntity] from the database.
	 * @return a  List<[StudentEntity]>
	 */
	fun getAllStudents(): List<StudentEntity> {
		return studentRepository.findAll()
	}

	fun getCountStudents(): Long {
		return studentRepository.count()
	}

	fun getInfoUniversities(): Map<String, Int> {
		val universities = studentRepository.getAllUniversities()
		return universities.groupingBy { it }.eachCount()
	}

	fun getInfoEducationForm(): Map<String, Int> {
		val universities = studentRepository.getAllEducationForm()
		return universities.groupingBy { it }.eachCount()
	}

	/**
	 * Save an object of type [StudentEntity] to the database.
	 */
	fun save(studentEntity: StudentEntity) {
		studentRepository.save(studentEntity)
	}
}