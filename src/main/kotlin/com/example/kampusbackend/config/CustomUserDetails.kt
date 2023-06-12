package com.example.kampusbackend.config

import com.example.kampusbackend.entity.ERole
import com.example.kampusbackend.entity.HrEntity
import com.example.kampusbackend.entity.StudentEntity
import com.example.kampusbackend.service.HrEntityService
import com.example.kampusbackend.service.StudentEntityService
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetails(
	private val studentEntityService: StudentEntityService,
	private val hrEntityService: HrEntityService,
) : UserDetailsService {

	@Value("\${user.username}")
	private lateinit var username: String

	@Value("\${user.password}")
	private lateinit var password: String

	override fun loadUserByUsername(username: String): UserDetails {

		if (username == this.username)
			return buildUser(this.username, this.password, ERole.TRUSTEE.name)

		val student: StudentEntity? = studentEntityService.getStudentByUsername(username)
		when {
			student != null -> {
				return buildUser(student.username, student.password, ERole.STUDENT.name)
			}
			else -> {
				val hr : HrEntity? = hrEntityService.getHrByUsername(username)
				if (hr != null) {
					return buildUser(hr.username, hr.password, ERole.HR.name)
				}
			}
		}
		throw UsernameNotFoundException("User Not Found with username: $username")
	}

	private fun buildUser(username: String?, password : String?, role: String?) : UserDetails {
		return User
			.withUsername(username)
			.accountExpired(false)
			.accountLocked(false)
			.credentialsExpired(false)
			.disabled(false)
			.password(password)
			.roles(role)
			.build()
	}
}