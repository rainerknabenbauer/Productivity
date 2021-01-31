package de.nykon.productivity.domain

import de.nykon.productivity.domain.value.Project
import org.springframework.stereotype.Service
import java.util.*


@Service
class ProjectService(
    private val projectRepository: ProjectRepository
) {

    fun getProjects(): MutableList<Project> {
        return projectRepository.findAll()
    }

    fun save(projects: ArrayList<Project>) {
        projects.forEach(projectRepository::save)
    }

    fun save(project: Project): Project {
        return projectRepository.save(project)
    }

    fun findById(id: String): Optional<Project> {
        return projectRepository.findById(id)
    }

    fun recoverProjects(email: String): List<Project> {
        return projectRepository.findByEmail(email)
    }

}