package de.nykon.productivity.authorization

import de.nykon.productivity.authorization.value.Authorization
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
    fun authorization(@RequestHeader("Authorization") authorizationBase64: String): ResponseEntity<String> {

        val authorization: Authorization
        try {
            authorization = Authorization.fromBase64(authorizationBase64)
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

        log.info("requested authorization with ${authorization.token} for ${authorization.projectId}")

        val result = authorizationService.authorization(authorization)

        return if (result.isPresent) {
            ResponseEntity.ok().body("Authorization successful")
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization failed")
        }
    }

    @PostMapping(path = ["/auth"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun setAuthorization(@RequestBody authorization: Authorization): ResponseEntity<Authorization> {
        log.info("set authorization")

        return ResponseEntity.ok(authorizationService.setAuthorization(authorization))
    }

}