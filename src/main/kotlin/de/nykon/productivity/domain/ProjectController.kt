package de.nykon.productivity.domain

import de.nykon.productivity.email.MailService
import de.nykon.productivity.domain.value.Project
import de.nykon.productivity.domain.value.Task
import de.nykon.productivity.domain.value.TaskDescription
import de.nykon.productivity.domain.value.UI
import org.slf4j.Logger
import org.slf4j.LoggerFactory
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

    @GetMapping(path = ["/projects/{id}"])
    fun getProject(@PathVariable id: String): ResponseEntity<Project> {
        log.info("called getProject with $id")
        return ResponseEntity.ok(projectService.findById(id).orElse(null))
    }

    @PostMapping(path = ["/projects"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun setProject(@RequestBody project: Project): ResponseEntity<Project> {
        log.info("called setProject")
        return ResponseEntity.ok(projectService.save(project));
    }

    @PostMapping(path = ["/email"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun sendEmail(@RequestBody email: String): ResponseEntity<List<Project>> {
        log.info("called send Email $email")
        return ResponseEntity.ok(mailService.recoverProjects(email.replace("\"", "")));
    }

    @GetMapping(path = ["/projects/new"])
    fun createProject(): ResponseEntity<Project> {
        log.info("create project")
        val project = Project()
        val task = Task(UUID.randomUUID().toString(), project.projectId, isBeingWorkedOn = true, isDeleted = false,"First steps",
            TaskDescription(
                """
                    Click the pen in the lower right corner to see details.
                    
                    You can edit the details as you wish.
                """.trimIndent(),
                """
                    Hello and welcome. 
                    
                    If you are new here, consider these next steps:
                    
                    1. Save the project link to your favorites
                    2. Set a reminder so you can access your projects even if you lose the link
                    3. Add a new task
                    4. Start to organize
                """.trimIndent(),
                """
                    You can edit this task, save it and view the changes again.
                    
                    You can also use the 'X' in the upper right corner of the task to delete it.
                """.trimIndent()
            ),
            null, 0, emptyList(), UI(350, 200), LocalDateTime.now())
        taskService.save(task)
        return ResponseEntity.ok(projectService.save(project))
    }



}