package de.nykon.productivity.authorization

import de.nykon.productivity.authorization.value.Credentials
import de.nykon.productivity.authorization.value.Session
import de.nykon.productivity.domain.ProjectService
import de.nykon.productivity.exceptions.FormattingException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.codec.EncodingException
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.Exception

@RestController
class AuthorizationController(
    private val projectService: ProjectService,
    private val authorizationService: AuthorizationService
) : AbstractAuthorization(projectService, authorizationService) {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping(path = ["/auth"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun authorization(@RequestHeader("Authorization") authorizationBase64: String): ResponseEntity<Session> {

        val credentials: Credentials

        try {
            credentials = Credentials.fromBase64(authorizationBase64)
        } catch (ex: Exception) {
            log.error(ex.message)
            return when(ex) {
                is FormattingException -> {
                    ResponseEntity.badRequest().body(null)
                }
                is EncodingException -> {
                    ResponseEntity.unprocessableEntity().body(null)
                }
                else -> throw ex
            }
        }

        log.info("requested authorization for ${credentials.projectId}")

        val session = authorizationService.authorizePassword(credentials)

        return if (session.isPresent) {
            log.info("Successful authorization for project ${credentials.projectId}")
            ResponseEntity.ok().body(session.get())
        } else {
            log.warn("Unauthorized request received for project ${credentials.projectId}")
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null)
        }
    }

    @PostMapping(path = ["/auth"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun setAuthorization(@RequestBody credentials: Credentials): ResponseEntity<Session> {
        log.info("set credentials for ${credentials.projectId}")

        return ResponseEntity.ok(authorizationService.setCredentials(credentials))
    }

}