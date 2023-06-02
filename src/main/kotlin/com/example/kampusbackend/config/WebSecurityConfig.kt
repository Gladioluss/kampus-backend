package com.example.kampusbackend.config

import com.example.kampusbackend.config.jwt.AuthEntryPointJwt
import com.example.kampusbackend.config.jwt.AuthTokenFilter
import com.example.kampusbackend.config.jwt.JwtUtils
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.beans.factory.annotation.Value
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
	private val unauthorizedHandler: AuthEntryPointJwt,
) {

	@Value("\${swagger.securitySchemeName}")
	private lateinit var securitySchemeName: String

	@Bean
	fun authenticationJwtTokenFilter() = AuthTokenFilter(jwtUtils, customUserDetails)

	@Bean
	fun configure(http: HttpSecurity): SecurityFilterChain {
		http.invoke {
			csrf { disable() }
			cors { }
			exceptionHandling { authenticationEntryPoint = unauthorizedHandler }
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
			allowedOrigins = listOf("http://localhost:5173", "http://95.163.241.71:5173",
				"https://bbadl60abq0f2c6oo4en.containers.yandexcloud.net", "https://kampus-bucket.website.yandexcloud.net")
			allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
			allowedHeaders = listOf("*")
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

	@Bean
	fun customizeOpenAPI(): OpenAPI? {
		return OpenAPI()
			.addSecurityItem(
				SecurityRequirement()
					.addList(securitySchemeName)
			)
			.components(
				Components()
					.addSecuritySchemes(
						securitySchemeName, SecurityScheme()
							.name(securitySchemeName)
							.type(SecurityScheme.Type.HTTP)
							.scheme("bearer")
							.bearerFormat("JWT")

					)
			).info(
				Info()
					.title("Kampus information for trustees api")
					.description("Kampus information for trustees")
					.version("1.0.0")
					.contact(Contact().name("Emurashin Danil"))
			)
	}
}