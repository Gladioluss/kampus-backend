package com.example.kampusbackend.entity

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.*
import org.hibernate.Hibernate
import java.time.LocalDate

@Schema(description = "Данные о проекте кампуса за какое-то число")
@Entity
@Table(name = "dynamic_progress")
class DynamicProgressEntity(

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "progress_id")
	var id: Long? = null,

	@Column(name = "progress_date")
	var progressDate: LocalDate? = null,

	@Column(name = "number_students")
	var numberStudents: Long? = null,

	@Column(name = "total_amount_scholarship")
	var totalAmountScholarship: Long? = null,
) {
	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
		other as StudentEntity
		return id != null && id == other.id
	}

	override fun hashCode(): Int = javaClass.hashCode()

	override fun toString(): String {
		return "DynamicProgressEntity(" +
				"id=$id, " +
				"progressDate=$progressDate, " +
				"numberStudents=$numberStudents, " +
				"totalAmountScholarship=$totalAmountScholarship)"
	}
}