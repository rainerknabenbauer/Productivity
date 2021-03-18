package de.nykon.productivity.domain

import de.nykon.productivity.domain.value.*
import de.nykon.productivity.email.MailService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.util.*

@RestController
class ProjectController(
    private val projectService: ProjectService,
    private val taskService: TaskService,
    private val mailService: MailService
    ) {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    /**
     * The recovery button in the UI gets clicked.
     */
    @PostMapping(path = ["/projects/recovery"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun sendRecoveryEmail(@RequestBody projectId: String): ResponseEntity<String> {
        log.info("Recovery of $projectId")

        val recoveredProject = projectService.findById(projectId)

        return if (recoveredProject.isPresent) {
            projectService.save(recoveredProject.get().recover())

            val associatedProjects = mailService.recovery(recoveredProject.get().email)
            log.info("${associatedProjects.size} associated projects found.")
            ResponseEntity.ok().body("Recovery eMail sent.")
        } else {
            ResponseEntity.badRequest().body("Project could not be located.")
        }
    }

    /**
     * The link in the recovery email gets clicked and unlocks the project.
     */
    @GetMapping(path = ["/projects/unlock/{token}"])
    fun unlock(@PathVariable token: String): ResponseEntity<String> {
        val unlockSuccessful = projectService.unlock(token)

        return if (unlockSuccessful) {
            ResponseEntity.ok().body("Project has been unlocked.")
        } else {
            ResponseEntity.status(HttpStatus.GONE).body("No matching project was found.")
        }
    }

    @GetMapping(path = ["/projects/{id}"])
    fun getProject(@PathVariable id: String): ResponseEntity<Project> {
        log.info("Called getProject with $id")

        val result = projectService.findById(id)

        return if (result.isPresent) {
            ResponseEntity.ok(result.get())
        } else {
            ResponseEntity.status(404).body(null)
        }
    }

    @PostMapping(path = ["/projects"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun setProject(@RequestBody project: Project): ResponseEntity<Project> {
        log.info("Called POST projects: $project")
        return ResponseEntity.ok(projectService.save(project));
    }

    @GetMapping(path = ["/projects/new"])
    fun createProject(): ResponseEntity<Project> {
        log.info("Create new project")
        val project = projectService.createNewProject()
        return ResponseEntity.ok(project)
    }



}