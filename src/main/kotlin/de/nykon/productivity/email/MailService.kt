package de.nykon.productivity.email

import de.nykon.productivity.domain.ProjectService
import de.nykon.productivity.domain.value.Project
import org.springframework.stereotype.Service


@Service
class MailService(
    private val projectService: ProjectService,
    private val mailer: Mailer
) {

    fun recovery(email: String): List<Project> {
        val linkedProjects = projectService.recoverProjects(email)
        sendRecoveryEmail(email, linkedProjects)
        return linkedProjects
    }

    fun sendRecoveryEmail(email: String, linkedProjects: List<Project>) {
        return mailer.sendRecoveryEmail(email, linkedProjects)
    }

}