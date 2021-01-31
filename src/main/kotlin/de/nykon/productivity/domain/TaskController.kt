package de.nykon.productivity.domain

import de.nykon.productivity.domain.value.Task
import de.nykon.productivity.domain.value.TaskDescription
import de.nykon.productivity.domain.value.UI
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

@RestController
class TaskController(private val taskService: TaskService) {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping(path = ["/tasks/{projectId}"])
    fun getProject(@PathVariable projectId: String): ResponseEntity<List<Task>> {
        return ResponseEntity.ok(taskService.getByProject(projectId))
    }

    @PostMapping(path = ["/tasks"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun saveTask(@RequestBody task: Task): ResponseEntity<Task> {
        val savedTask = taskService.save(task)
        log.info("saved task: $savedTask")
        return ResponseEntity.ok(savedTask)
    }

    @PostMapping(path = ["/ui/{id}"])
    fun setPosition(@PathVariable id: String, @RequestBody ui: UI): ResponseEntity<Task> {
        log.info("set UI of $id to x=${ui.xposition} | y=${ui.yposition}")
        val savedTask = taskService.save(
            Task(
                id, null, isBeingWorkedOn = false, isDeleted = false,"updated task",
                TaskDescription("", "", ""),
                LocalDate.now(), 0, listOf(), ui
            )
        )
        return ResponseEntity.ok(savedTask)
    }

    @DeleteMapping(path = ["/tasks"])
    fun deleteTask(@RequestBody task: Task) {
        log.info("delete task: $task")
        taskService.delete(task)
    }



    @GetMapping(path = ["/sample"])
    fun getSample(): ResponseEntity<ArrayList<Task>> {
        val tasks = ArrayList<Task>()
        val task1 = Task(UUID.randomUUID().toString(), null, isBeingWorkedOn = false, isDeleted = false, "First Sample",
            TaskDescription("...", "...", "..."),
            LocalDate.now(), 0, null, UI(0, 0)
        )
        val task2 = Task(UUID.randomUUID().toString(), null, isBeingWorkedOn = false, isDeleted = false, "First Sample",
            TaskDescription("...", "...", "..."),
            LocalDate.now(), 0, null, UI(0, 0))

        val task3 = Task(UUID.randomUUID().toString(), null, isBeingWorkedOn = false, isDeleted = false,"First Sample",
            TaskDescription("...", "...", "..."),
            LocalDate.now(), 0, null, UI(0, 0))

        tasks.add(task1)
        tasks.add(task2)
        tasks.add(task3)

        taskService.save(task1)
        taskService.save(task2)
        taskService.save(task3)

        return ResponseEntity.ok(tasks)
    }

}