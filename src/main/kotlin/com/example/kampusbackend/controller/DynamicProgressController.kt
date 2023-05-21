package com.example.kampusbackend.controller

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@SecurityRequirement(name = "JWT")
@Tag(name = "DynamicProgressController", description = "Взаимодействие с таблицей 'dynamic_progress'")
@RestController
@RequestMapping("/api/v1/dynamicProgress")
class DynamicProgressController {

}