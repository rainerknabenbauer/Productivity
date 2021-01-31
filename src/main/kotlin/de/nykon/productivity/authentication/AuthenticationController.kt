package de.nykon.productivity.authentication

import de.nykon.productivity.authentication.value.Authentication
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class AuthenticationController(
    private val authenticationService: AuthenticationService
) {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @PostMapping(path = ["/auth/{id}"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun authenticate(@RequestBody authentication: Authentication): ResponseEntity<String> {
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