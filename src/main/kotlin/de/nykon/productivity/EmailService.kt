package de.nykon.productivity

import de.nykon.productivity.value.Project
import org.springframework.stereotype.Service

@Service
class EmailService(
    private val projectService: ProjectService) {

    fun recoverProjects(email: String): List<Project> {
        return projectService.recoverProjects(email)
    }

}