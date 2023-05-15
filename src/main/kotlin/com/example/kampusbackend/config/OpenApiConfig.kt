package com.example.kampusbackend.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info

@OpenAPIDefinition(
	info = Info(
		title = "Kampus information for trustees api",
		description = "Kampus information for trustees", version = "1.0.0",
		contact = Contact(
			name = "Emurashin Danil",
		)
	)
)
class OpenApiConfig {
}