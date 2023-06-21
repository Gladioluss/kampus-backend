package com.example.kampusbackend.config

import com.example.kampusbackend.config.jwt.AuthEntryPointJwt
import com.example.kampusbackend.config.jwt.AuthTokenFilter
import com.example.kampusbackend.config.jwt.JwtUtils
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
@EnableWebSecurity
class WebSecurityConfig(
	private val jwtUtils: JwtUtils,
	private val customUserDetails: CustomUserDetails,
	private val authEntryPointJwt: AuthEntryPointJwt,
) {

	@Bean
	fun authenticationJwtTokenFilter() = AuthTokenFilter(jwtUtils, customUserDetails)

	@Bean
	fun configure(http: HttpSecurity): SecurityFilterChain {
		http.invoke {
			csrf { disable() }
			cors { }
			exceptionHandling { authenticationEntryPoint = authEntryPointJwt }
			authorizeRequests {
				authorize("api/v1/auth/**", permitAll)
//				authorize("api/v1/api-docs/**", permitAll)
				authorize("swagger-ui/**", permitAll)
				authorize("v3/**", permitAll)
				authorize(anyRequest, authenticated)
			}
			sessionManagement { sessionCreationPolicy = SessionCreationPolicy.STATELESS }
			addFilterBefore<UsernamePasswordAuthenticationFilter>(authenticationJwtTokenFilter())
		}
		return http.build()
	}

	@Bean
	fun corsConfigurationSource(): CorsConfigurationSource {
		val configuration = CorsConfiguration().apply {
			allowedOrigins = listOf(
				"http://localhost:5173", "http://95.163.241.71:5173",
				"http://localhost:80", "http://localhost",
				"http://51.250.20.57:8080",
				"https://localhost:5173", "https://95.163.241.71:5173",
				"https://localhost:80", "https://localhost",
				"https://51.250.20.57:8080", "https://trustee-kampus.ru",
				"http://51.250.20.57", "http://51.250.20.57:80", "http://51.250.20.57:8080"
			)
			allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
			allowedHeaders = listOf("Authorization", "Cache-Control", "Content-Type")
			allowCredentials = true
			maxAge = 3600
		}
		val source = UrlBasedCorsConfigurationSource().apply {
			registerCorsConfiguration("/**", configuration)
		}
		return source
	}

	@Bean
	fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager =
		authenticationConfiguration.authenticationManager


	@Bean
	fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()


}