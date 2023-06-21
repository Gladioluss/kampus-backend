package com.example.kampusbackend.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl

@Configuration
class MailSenderConfig {

	@Value("\${spring.mail.host}")
	private lateinit var hostsProperties: String

	@Value("\${spring.mail.port}")
	private var portProperties: Int = 0

	@Value("\${spring.mail.username}")
	private lateinit var usernameProperties: String

	@Value("\${spring.mail.password}")
	private lateinit var passwordProperties: String
	@Bean
	fun getJavaMailSender(): JavaMailSender? {
		val mailSender = JavaMailSenderImpl().apply {
			host = hostsProperties
			port = portProperties
			username = usernameProperties
			password = passwordProperties
			val props = javaMailProperties
			props["mail.transport.protocol"] = "smtp"
			props["mail.smtp.auth"] = "true"
			props["mail.smtp.starttls.enable"] = "true"
		}

		return mailSender
	}
}