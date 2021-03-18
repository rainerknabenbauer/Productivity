package de.nykon.productivity.domain

import de.nykon.productivity.domain.value.Project
import de.nykon.productivity.domain.value.Task
import de.nykon.productivity.domain.value.TaskDescription
import de.nykon.productivity.domain.value.UI
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*


@Service
class ProjectService(
    private val taskService: TaskService,
    private val projectRepository: ProjectRepository
) {

    fun getProjects(): List<Project> {
        return projectRepository.findAll()
    }

    fun save(projects: List<Project>) {
        projects.forEach(projectRepository::save)
    }

    fun save(project: Project): Project {
        return projectRepository.save(project)
    }

    fun findById(projectId: String): Optional<Project> {
        return projectRepository.findById(projectId)
    }

    fun recoverProjects(email: String): List<Project> {
        return projectRepository.findByEmail(email)
    }

    fun unlock(token: String): Boolean {
        val project = projectRepository.findFirstByUnlockToken(token)

        if (Objects.nonNull(project)) {
            projectRepository.save(project!!.unlock())
            return true
        }
        return false
    }

    /**
     * A user has requested to setup a new project.
     */
    fun createNewProject(): Project {
        val project = Project()
        taskService.setupTasksForNewProject(project)
        return save(project)
    }

}