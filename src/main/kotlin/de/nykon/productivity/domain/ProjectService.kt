package de.nykon.productivity.domain

import de.nykon.productivity.domain.value.Project
import org.springframework.stereotype.Service
import java.util.*


@Service
class ProjectService(
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

}