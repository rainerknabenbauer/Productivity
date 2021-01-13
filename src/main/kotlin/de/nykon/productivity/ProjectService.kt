package de.nykon.productivity

import de.nykon.productivity.value.Project
import org.springframework.stereotype.Service
import java.util.ArrayList

@Service
class ProjectService(
    private val projectRepository: ProjectRepository) {

    fun getProjects(): MutableList<Project> {
        return projectRepository.findAll()
    }

    fun save(projects: ArrayList<Project>) {
        projects.forEach(projectRepository::save)
    }

    fun findById(id: String): Project {
        return projectRepository.findById(id).orElse(Project())
    }

}