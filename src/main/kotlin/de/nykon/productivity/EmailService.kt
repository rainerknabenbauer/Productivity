package de.nykon.productivity

import de.nykon.productivity.value.Project
import org.springframework.stereotype.Service


@Service
class EmailService(
    private val projectService: ProjectService,
    private val mailer: Mailer) {

    fun recoverProjects(email: String): List<Project> {
        val linkedProjects = projectService.recoverProjects(email)
        mailer.sendRecoveryEmail(email, linkedProjects)
        return linkedProjects
    }

}