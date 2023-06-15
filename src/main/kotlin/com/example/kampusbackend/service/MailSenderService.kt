package com.example.kampusbackend.service

import com.example.kampusbackend.dto.InternshipCardDto
import com.example.kampusbackend.dto.ReplyInternshipDto
import com.example.kampusbackend.entity.InternshipCardEntity
import com.example.kampusbackend.entity.StudentEntity
import com.example.kampusbackend.service.db.InternshipCardEntityService
import mu.KLogger
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import kotlin.concurrent.thread

@Service
class MailSenderService(
	private val javaMailSender: JavaMailSender,
) {
	val logger: KLogger
		get() = KotlinLogging.logger {}

	@Value("\${spring.mail.username}")
	private lateinit var emailUser: String

	fun sendMail(internshipCardEntity: InternshipCardEntity, studentEntity: StudentEntity) {
		val message = SimpleMailMessage().apply {
			from = emailUser
			setTo(internshipCardEntity.hrEmail)
			this.subject = "Стажировка"
			text = createEmailText(internshipCardEntity, studentEntity)
		}
		try {
			thread {
				javaMailSender.send(message)
				logger.info("Mail sent")
			}
		} catch (e: Exception) {
			logger.error { "MailSenderService Error: ${e.message}" }
		}
	}

	private fun createEmailText(internshipCardEntity: InternshipCardEntity, studentEntity: StudentEntity): String {
		return "Отклик на стажировку: ${internshipCardEntity.internshipTitle}\n" +
				"Описание стажировки: ${internshipCardEntity.internshipDescription}\n" +
				"Данные о студенте:\n" +
				"Фио - ${studentEntity.lastName} ${studentEntity.firstName} ${studentEntity.middleName}\n" +
				"телефон - ${studentEntity.phoneNumber}\n" +
				"почта - ${studentEntity.email}\n" +
				"средний балл - ${studentEntity.averageGrade}\n" +
				"Вуз - ${studentEntity.universityName}\n" +
				"Направление - ${studentEntity.courseTitle}\n" +
				"Курс - ${studentEntity.courseNumber}\n" +
				"О студенте- ${studentEntity.about}\n"
	}
}