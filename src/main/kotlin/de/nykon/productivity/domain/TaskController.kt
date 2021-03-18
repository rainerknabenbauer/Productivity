package de.nykon.productivity.domain

import de.nykon.productivity.authorization.AuthorizationService
import de.nykon.productivity.authorization.value.AuthorizationStatus
import de.nykon.productivity.domain.value.Task
import de.nykon.productivity.domain.value.UI
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RestController
class TaskController(
    private val taskService: TaskService,
    private val authorizationService: AuthorizationService
)
{

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping(path = ["/tasks/{projectId}"])
    fun getProjectTasks(
        @RequestHeader("Authorization") authorizationBase64: String,
        @PathVariable projectId: String): ResponseEntity<List<Task>> {


        val verified = authorizationService.isAuthorized(projectId, authorizationBase64)

        return if (verified == AuthorizationStatus.SUCCESSFUL) {
            return ResponseEntity.ok(taskService.getByProject(projectId))
        } else {
            log.warn("Unauthorized retrieval of tasks requested: $projectId")
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(listOf())
        }
    }

    @PostMapping(path = ["/tasks"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun saveTask(
        @RequestHeader("Authorization") authorizationBase64: String,
        @RequestBody task: Task): ResponseEntity<Task> {

        log.info("save task: $task with $authorizationBase64")

        val verified = authorizationService.isAuthorized(task, authorizationBase64)

        return if (verified == AuthorizationStatus.SUCCESSFUL) {
            val savedTask = taskService.save(task)
            return ResponseEntity.ok(savedTask)
        } else {
            log.warn("Unauthorized saving of task requested: $task")
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(task)
        }
    }

    @DeleteMapping(path = ["/tasks"])
    fun deleteTask(
        @RequestHeader("Authorization") authorizationBase64: String,
        @RequestBody task: Task): ResponseEntity<Task> {

        val verified = authorizationService.isAuthorized(task, authorizationBase64)

        return if (verified == AuthorizationStatus.SUCCESSFUL) {
            log.info("delete task: $task")
            taskService.delete(task)
            ResponseEntity.ok(task)
        } else {
            log.warn("Unauthorized deletion of task requested: $task")
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(task)
        }
    }

}