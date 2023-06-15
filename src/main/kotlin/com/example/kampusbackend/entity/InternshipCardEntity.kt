package com.example.kampusbackend.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*
import org.hibernate.Hibernate

@Entity
@Table(name = "internship_card")
@JsonIgnoreProperties("hr")
class InternshipCardEntity(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "internship_card_id", nullable = false)
	var id: Long? = null,

	@Column(name = "internship_title", nullable = false)
	var internshipTitle: String? = null,

	@Column(name = "organization_name", nullable = false)
	var organizationName: String? = null,

	@Column(name = "internship_short_description", nullable = false)
	var internshipShortDescription: String? = null,

	@Column(name = "internship_description", nullable = false)
	var internshipDescription: String? = null,

	@Column(name = "internship_specialization", nullable = false)
	var internshipSpecialization: String? = null,

	@Column(name = "internship_schedule", nullable = false)
	var internshipSchedule: String? = null,

	@Column(name = "internship_type", nullable = false)
	var internshipType: String? = null,

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hrs_id")
	var hr: HrEntity,

	@Column(name = "hr_email", nullable = false)
	var hrEmail: String? = hr.email,
) {
	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
		other as InternshipCardEntity
		return id != null && id == other.id
	}

	override fun hashCode(): Int = javaClass.hashCode()
}