package de.nykon.productivity

import de.nykon.productivity.value.Project
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.collections.ArrayList

@RestController
class ProjectController(
    private val projectService: ProjectService) {

    @CrossOrigin(origins = ["http://localhost:5000"])
    @GetMapping(path = ["/projects/samples"])
    fun sampleProjects(): ArrayList<Project> {
        val projects: ArrayList<Project> = ArrayList()
        projects.add(Project(UUID.randomUUID().toString(), "test 1", "keine beschreibung", null, listOf(), listOf()))
        projects.add(Project(UUID.randomUUID().toString(), "test 2", "eine beschreibung", null, listOf(), listOf()))
        projectService.save(projects)
        return projects
    }

    @CrossOrigin(origins = ["http://localhost:5000"])
    @GetMapping(path = ["/projects/{id}"])
    fun getProject(@PathVariable id: String): ResponseEntity<Project> {
        return ResponseEntity.ok(projectService.findById(id));
    }

    @CrossOrigin
    @PostMapping(path = ["/projects"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun setProject(@RequestBody project: Project): ResponseEntity<Project> {
        println("called setProject")
        return ResponseEntity.ok(projectService.save(project));
    }

    @CrossOrigin
    @GetMapping(path = ["/projects/new"])
    fun createProject(): ResponseEntity<Project> {
        println("create project")
        return ResponseEntity.ok(projectService.save(Project()))
    }

}