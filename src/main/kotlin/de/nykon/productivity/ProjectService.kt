package de.nykon.productivity

import de.nykon.productivity.value.Project
import org.springframework.stereotype.Service
import java.util.ArrayList
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import java.time.LocalDate
import java.time.Period


@Service
class ProjectService(
    private val projectRepository: ProjectRepository) {

    fun getProjects(): MutableList<Project> {
        return projectRepository.findAll()
    }

    fun save(projects: ArrayList<Project>) {
        projects.forEach(projectRepository::save)
    }

    fun save(project: Project): Project {
        return projectRepository.save(project)
    }

    fun findById(id: String): Project {
        return projectRepository.findById(id).orElse(Project())
    }

    fun recoverProjects(email: String): List<Project> {
        val findByEmail = projectRepository.findByEmail(email)
        println(findByEmail)
        return findByEmail
    }

}