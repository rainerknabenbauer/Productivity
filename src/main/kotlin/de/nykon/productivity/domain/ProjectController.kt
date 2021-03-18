package de.nykon.productivity.domain

import de.nykon.productivity.domain.value.*
import de.nykon.productivity.email.MailService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class ProjectController(
    private val projectService: ProjectService,
    private val mailService: MailService
    ) {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    /**
     * The recovery button in the UI gets clicked.
     */
    @PostMapping(path = ["/projects/recovery"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun sendRecoveryEmail(@RequestBody projectId: String): ResponseEntity<String> {

        val recoveredProject = projectService.findById(projectId)

        return if (recoveredProject.isPresent) {
            projectService.save(recoveredProject.get().recover())

            val associatedProjects = mailService.recovery(recoveredProject.get().email)
            log.info("Recovery of $projectId with ${associatedProjects.size} associated projects found.")

            ResponseEntity.ok().body("Recovery eMail sent.")
        } else {
            log.warn("Recovery of non-existing project requested: $projectId")
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
            log.info("Successfully unlocked project with token: $token")
            ResponseEntity.ok().body("Project has been unlocked.")
        } else {
            log.warn("Unlock of non-existing token requested: $token")
            ResponseEntity.status(HttpStatus.GONE).body("No matching project was found.")
        }
    }

    @GetMapping(path = ["/projects/{projectId}"])
    fun getProject(@PathVariable projectId: String): ResponseEntity<Project> {

        val result = projectService.findById(projectId)

        return if (result.isPresent) {
            ResponseEntity.ok(result.get())
        } else {
            log.warn("Recovery of non-existing project requested: $projectId")
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
        val project = projectService.createNewProject()
        log.info("Created new project with ID ${project.projectId}")
        return ResponseEntity.ok(project)
    }



}