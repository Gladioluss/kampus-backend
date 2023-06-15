package com.example.kampusbackend.controller

import com.example.kampusbackend.config.jwt.JwtUtils
import com.example.kampusbackend.dto.response.JwtResponse
import com.example.kampusbackend.dto.request.LoginRequest
import com.example.kampusbackend.dto.request.SignupRequest
import com.example.kampusbackend.entity.HrEntity
import com.example.kampusbackend.entity.StudentEntity
import com.example.kampusbackend.exception.ErrorMessageModel
import com.example.kampusbackend.service.db.HrEntityService
import com.example.kampusbackend.service.db.StudentEntityService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@Tag(name = "AuthController", description = "Отвечает за аутентификацию")
@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
	private val authenticationManager: AuthenticationManager,
	private val jwtUtils: JwtUtils,
	private val studentEntityService: StudentEntityService,
	private val hrEntityService: HrEntityService,
	private var encoder: PasswordEncoder,
) {

	@Value("\${user.username}")
	private lateinit var username: String

	@PostMapping("/login")
	@Operation(summary = "Аутентификация пользователя и создание jwt", description = "Принимает: username, password")
	fun authUser(@RequestBody loginRequest: LoginRequest): ResponseEntity<JwtResponse> {
		val authentication = authenticationManager.authenticate(
			UsernamePasswordAuthenticationToken(
				loginRequest.username,
				loginRequest.password,
			)
		)
		SecurityContextHolder.getContext().authentication = authentication
		val jwt: String = jwtUtils.generateJwtToken(authentication)
		val username: String = authentication.name
		val role: String? = authentication.authorities.firstOrNull()?.authority?.substringAfter("ROLE_")
		return ResponseEntity.ok(
			JwtResponse(jwt, username, role)
		)
	}

	@PostMapping("/student/signup")
	@Operation(summary = "Регистрация студента", description = "Принимает: username, password")
	fun registerStudent(@RequestBody signupRequest: SignupRequest): ResponseEntity<*> {
		if (studentEntityService.existsByUsername(signupRequest.username) || signupRequest.username == username) {
			return ResponseEntity
				.badRequest()
				.body<Any>(ErrorMessageModel(401,"Error: Username is already taken!"))
		}

		val user = StudentEntity(
			username = signupRequest.username,
			password = encoder.encode(signupRequest.password),
		)
		studentEntityService.saveStudent(user)
		return ResponseEntity.ok(
			ErrorMessageModel(200,"Student registered successfully!")
		)
	}

	@PostMapping("/hr/signup")
	@Operation(summary = "Регистрация hr", description = "Принимает: username, password")
	fun registerHr(@RequestBody signupRequest: SignupRequest): ResponseEntity<*> {
		if (hrEntityService.existsByUsername(signupRequest.username) || signupRequest.username == username) {
			return ResponseEntity
				.badRequest()
				.body<Any>(ErrorMessageModel(401,"Error: Username is already taken!"))
		}

		val user = HrEntity(
			username = signupRequest.username,
			password = encoder.encode(signupRequest.password),
		)
		hrEntityService.saveHr(user)
		return ResponseEntity.ok(
			ErrorMessageModel(200,"Hr registered successfully!")
		)
	}
}