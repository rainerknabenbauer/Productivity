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



}