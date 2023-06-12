package com.example.kampusbackend.entity

import com.example.kampusbackend.dto.HrDto
import com.example.kampusbackend.dto.StudentDto
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.hibernate.Hibernate

@Entity

@Table(name = "hrs")
class HrEntity(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "hrs_id", nullable = false)
	var id: Long? = null,

	@Column(name = "username", nullable = false, unique = true)
	var username: String? = null,

	@Column(name = "password", nullable = false)
	var password: String? = null,

	@JsonIgnore
	@OneToMany(mappedBy = "hr", cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
	var internshipCards: MutableList<InternshipCardEntity> = mutableListOf(),

) {
	fun addNewInternshipCard(internshipCardEntity: InternshipCardEntity) =
		internshipCards.add(internshipCardEntity)


	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
		other as HrEntity
		return id != null && id == other.id
	}

	override fun hashCode(): Int = javaClass.hashCode()
}
fun HrEntity.update(data: HrDto) = apply {
	username = data.username ?: username
	password = data.password ?: password
}