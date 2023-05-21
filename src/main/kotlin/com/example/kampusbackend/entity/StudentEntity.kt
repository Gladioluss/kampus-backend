package com.example.kampusbackend.entity

import jakarta.persistence.*
import org.hibernate.Hibernate
import java.time.LocalDate


@Entity
@Table(name = "students")
class StudentEntity(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "students_id", nullable = false)
	var id: Long? = null,

	@Column(name = "first_name", nullable = true)
	var firstName: String? = null,

	@Column(name = "last_name", nullable = true)
	var lastName: String? = null,

	@Column(name = "middle_name", nullable = true)
	var middleName: String? = null,

	@Column(name = "date_birth", nullable = true)
	var dateBirth: LocalDate? = null,

	@Column(name = "receipt_date", nullable = true)
	var receiptDate: LocalDate? = null,

	@Column(name = "university_name", nullable = true)
	var universityName: String? = null,

	@Column(name = "course_title", nullable = true)
	var courseTitle: String? = null,

	@Column(name = "course_number", nullable = true)
	var courseNumber: Int? = null,

	@Column(name = "average_grade", nullable = true)
	var averageGrade: Double? = null,
	/** Form of study: full-time/part-time*/
	@Column(name = "study_form", nullable = true)
	var studyForm: String? = null,
	/** Form of education: budget/contract/targeted */
	@Column(name = "education_form", nullable = true)
	var educationForm: String? = null,

	@Column(name = "type_high_education", nullable = true)
	var typeHighEducation: String? = null,

	@Column(name = "stipend_availability", nullable = true)
	var stipendAvailability: Boolean? = null,

	@Column(name = "local_resident", nullable = true)
	var localResident: Boolean? = null,
) {
	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
		other as StudentEntity
		return id != null && id == other.id
	}

	override fun hashCode(): Int = javaClass.hashCode()
	override fun toString(): String {
		return "StudentEntity(" +
				"id=$id, " +
				"firstName=$firstName, " +
				"lastName=$lastName, " +
				"middleName=$middleName, " +
				"dateBirth=$dateBirth, " +
				"receiptDate=$receiptDate, " +
				"universityName=$universityName, " +
				"courseTitle=$courseTitle, " +
				"courseNumber=$courseNumber, " +
				"averageGrade=$averageGrade, " +
				"studyForm=$studyForm, " +
				"educationForm=$educationForm, " +
				"typeHighEducation=$typeHighEducation, " +
				"stipendAvailability=$stipendAvailability, " +
				"localResident=$localResident)"
	}

}