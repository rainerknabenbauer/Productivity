package de.nykon.productivity

import de.nykon.productivity.value.Project
import de.nykon.productivity.value.Task
import de.nykon.productivity.value.TaskDescription
import de.nykon.productivity.value.UI
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

@RestController
@CrossOrigin
class ProjectController(
    private val projectService: ProjectService,
    private val taskService: TaskService,
    private val emailService: EmailService) {

    @GetMapping(path = ["/projects/samples"])
    fun sampleProjects(): ArrayList<Project> {
        val projects: ArrayList<Project> = ArrayList()
        projects.add(Project(UUID.randomUUID().toString(), "test 1", "keine beschreibung", null, listOf(), listOf()))
        projects.add(Project(UUID.randomUUID().toString(), "test 2", "eine beschreibung", null, listOf(), listOf()))
        projectService.save(projects)
        return projects
    }

    @GetMapping(path = ["/projects/{id}"])
    fun getProject(@PathVariable id: String): ResponseEntity<Project> {
        return ResponseEntity.ok(projectService.findById(id));
    }

    @PostMapping(path = ["/projects"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun setProject(@RequestBody project: Project): ResponseEntity<Project> {
        println("called setProject")
        return ResponseEntity.ok(projectService.save(project));
    }

    @PostMapping(path = ["/email"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun sendEmail(@RequestBody email: String): ResponseEntity<List<Project>> {
        println("called send Email $email")
        return ResponseEntity.ok(emailService.recoverProjects(email.replace("\"", "")));
    }

    @GetMapping(path = ["/projects/new"])
    fun createProject(): ResponseEntity<Project> {
        println("create project")
        val project = Project()
        val task = Task(UUID.randomUUID().toString(), project.projectId, true, "First steps",
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
        println("save welcome task")
        taskService.save(task)
        return ResponseEntity.ok(projectService.save(project))
    }



}