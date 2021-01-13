package de.nykon.productivity

import de.nykon.productivity.value.Project
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import kotlin.collections.ArrayList

@RestController
class ProjectController(
    private val projectService: ProjectService) {

    @CrossOrigin(origins = ["http://localhost:5000"])
    @GetMapping(path = ["/projects"])
    fun getProjects(): ResponseEntity<MutableList<Project>> {
        return ResponseEntity.ok(projectService.getProjects())
    }

    @CrossOrigin(origins = ["http://localhost:5000"])
    @GetMapping(path = ["/projects/samples"])
    fun createProjects(): ArrayList<Project> {
        val projects: ArrayList<Project> = ArrayList()
        projects.add(Project(UUID.randomUUID().toString(), "test 1", "keine beschreibung", listOf(), listOf()))
        projects.add(Project(UUID.randomUUID().toString(), "test 2", "eine beschreibung", listOf(), listOf()))
        projectService.save(projects)
        return projects
    }

}