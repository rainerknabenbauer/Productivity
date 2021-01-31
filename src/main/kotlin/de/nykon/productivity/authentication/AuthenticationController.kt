package de.nykon.productivity.authentication

import com.sun.mail.util.DecodingException
import de.nykon.productivity.authentication.value.Authentication
import de.nykon.productivity.exceptions.FormattingException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.codec.EncodingException
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.Exception
import java.util.*

@RestController
class AuthenticationController(
    private val authenticationService: AuthenticationService
) {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping(path = ["/auth"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun authenticate(@RequestHeader("Authorization") authorization: String): ResponseEntity<String> {

        val authentication: Authentication
        try {
            authentication = Authentication.fromBase64(authorization)
        } catch (ex: Exception) {
            log.error(ex.message)
            return when(ex) {
                is FormattingException -> {
                    ResponseEntity.badRequest().body(ex.message)
                }
                is EncodingException -> {
                    ResponseEntity.unprocessableEntity().body(ex.message)
                }
                else -> throw ex
            }
        }

        log.info("requested authentication with ${authentication.token} for ${authentication.projectId}")

        val result = authenticationService.authenticate(authentication)

        return if (result.isPresent) {
            ResponseEntity.ok().body("Authentication successful")
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed")
        }
    }

    @PostMapping(path = ["/auth"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun setAuthentication(@RequestBody authentication: Authentication): ResponseEntity<Authentication> {
        log.info("set authentication")

        return ResponseEntity.ok(authenticationService.setAuthentication(authentication))
    }

}