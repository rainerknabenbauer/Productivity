package de.nykon.productivity.email

import de.nykon.productivity.domain.ProjectService
import de.nykon.productivity.domain.value.Project
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MailController(
    private val mailService: MailService
    ) {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @PostMapping(path = ["/email"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun sendProjects(@RequestBody email: String): ResponseEntity<String> {
        log.info("called send Email $email")

        mailService.recovery(email)

        return ResponseEntity.ok("Send eMail to $email")
    }

}