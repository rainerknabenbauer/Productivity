package de.nykon.productivity.email

import de.nykon.productivity.domain.ProjectService
import de.nykon.productivity.domain.value.Project
import de.nykon.productivity.domain.value.Task
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
        val message = mailer.createRecoveryEmail(linkedProjects)
        mailer.send(email, message)
    }

    fun sendDeadlineReminder(email: String, task: Task) {
        val message = mailer.createDeadlineEmail(task)
        mailer.send(email, message)
    }



}