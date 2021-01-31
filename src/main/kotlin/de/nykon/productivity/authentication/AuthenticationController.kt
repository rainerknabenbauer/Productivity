package de.nykon.productivity.authentication

import de.nykon.productivity.authentication.value.Authentication
import org.slf4j.Logger
import org.slf4j.LoggerFactory
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
    fun authenticate(@RequestHeader("Authorization") authentication: String): ResponseEntity<String> {

        val base64Authorization: String
        try {
            base64Authorization = authentication.split(" ")[1]
        } catch (e: Exception) {
            return ResponseEntity.badRequest()
                .body("Authorization is not properly formatted. Should be 'Authorization: Basic projectId:token'")
        }

        val authentication: Authentication
        try {
            val decodedBytes = Base64.getDecoder().decode(base64Authorization)
            val decodedString = String(decodedBytes).split(":")
            
            authentication = Authentication(decodedString[0], decodedString[1])
        } catch (e: Exception) {
            return ResponseEntity.unprocessableEntity()
                .body("Authorization could not be decoded.")
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