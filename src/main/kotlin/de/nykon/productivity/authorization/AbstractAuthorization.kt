package de.nykon.productivity.authorization

import de.nykon.productivity.authorization.value.Session
import de.nykon.productivity.domain.ProjectService
import de.nykon.productivity.domain.value.Task
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

abstract class AbstractAuthorization(
    private val projectService: ProjectService,
    private val authorizationService: AuthorizationService
) {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    fun isAuthorized(task: Task, authorizationBase64: String): Boolean {
        return if (task.projectId != null) {
            isAuthorized(task.projectId, authorizationBase64)
        } else {
            false
        }
    }

    fun isAuthorized(projectId: String, authorizationBase64: String): Boolean {
        val project = projectService.findById(projectId)

        return if (project.isPresent) {
            if (project.get().isProtected) {
                authorize(authorizationBase64).isPresent
            } else {
                true
            }
        } else {
            log.error("Project $projectId not found.")
            false
        }
    }

    private fun authorize(authorizationBase64: String): Optional<Session> {
        val authorization = transform(authorizationBase64)

        if (authorization.isEmpty) {
            return Optional.empty()
        }

        return authorizationService.authorizeSession(authorization.get())

    }

    private fun transform(authorization: String): Optional<Session> {
        log.info("authorization is $authorization")
        return try {
            Optional.of(Session.fromBase64(authorization))
        } catch (ex: Exception) {
            log.error(ex.message)
            Optional.ofNullable(null)
        }
    }

}