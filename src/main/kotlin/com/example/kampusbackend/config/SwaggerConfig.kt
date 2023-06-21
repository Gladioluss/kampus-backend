package com.example.kampusbackend.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

	@Value("\${swagger.securitySchemeName}")
	private lateinit var securitySchemeName: String

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