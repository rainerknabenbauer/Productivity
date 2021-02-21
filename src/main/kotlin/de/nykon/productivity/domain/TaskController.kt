package de.nykon.productivity.domain

import de.nykon.productivity.authorization.AbstractAuthorization
import de.nykon.productivity.authorization.AuthorizationService
import de.nykon.productivity.domain.value.Task
import de.nykon.productivity.domain.value.TaskDescription
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
    private val authorizationService: AuthorizationService,
    private val projectService: ProjectService
) : AbstractAuthorization(projectService, authorizationService)
{

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping(path = ["/tasks/{projectId}"])
    fun getProject(
        @RequestHeader("Authorization") authorizationBase64: String,
        @PathVariable projectId: String): ResponseEntity<List<Task>> {

        return if (isAuthorized(projectId, authorizationBase64)) {
            return ResponseEntity.ok(taskService.getByProject(projectId))
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(listOf())
        }
    }

    @PostMapping(path = ["/tasks"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun saveTask(
        @RequestHeader("Authorization") authorizationBase64: String,
        @RequestBody task: Task): ResponseEntity<Task> {

        log.info("save task: $task with $authorizationBase64")

        return if (isAuthorized(task, authorizationBase64)) {
            val savedTask = taskService.save(task)
            return ResponseEntity.ok(savedTask)
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(task)
        }
    }

    @PostMapping(path = ["/ui/{id}"])
    fun setPosition(@PathVariable id: String, @RequestBody ui: UI): ResponseEntity<Task> {
        log.info("set UI of $id to x=${ui.xposition} | y=${ui.yposition}")
        val savedTask = taskService.save(
            Task(
                id, null, isBeingWorkedOn = false, isDeleted = false,"updated task",
                TaskDescription("", "", ""),
                getSqlDate(LocalDate.now()), null, null,0, listOf(), ui
            )
        )
        return ResponseEntity.ok(savedTask)
    }

    @DeleteMapping(path = ["/tasks"])
    fun deleteTask(
        @RequestHeader("Authorization") authorizationBase64: String,
        @RequestBody task: Task): ResponseEntity<Task> {

        return if (isAuthorized(task, authorizationBase64)) {
            log.info("delete task: $task")
            taskService.delete(task)
            ResponseEntity.ok(task)
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(task)
        }
    }

    private fun getSqlDate(localDate: LocalDate): String? {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return localDate.format(formatter)
    }

}