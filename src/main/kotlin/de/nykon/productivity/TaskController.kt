package de.nykon.productivity

import de.nykon.productivity.value.Project
import de.nykon.productivity.value.Task
import de.nykon.productivity.value.TaskDescription
import de.nykon.productivity.value.UI
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

@RestController
class TaskController(private val taskService: TaskService) {

    @GetMapping(path = ["/tasks/{projectId}"])
    fun getProject(@PathVariable projectId: String): ResponseEntity<List<Task>> {
        return ResponseEntity.ok(taskService.getByProject(projectId))
    }

    @PostMapping(path = ["/tasks"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun saveTask(@RequestBody task: Task): ResponseEntity<Task> {
        val savedTask = taskService.save(task)
        println("saved task: $savedTask")
        return ResponseEntity.ok(savedTask)
    }

    @PostMapping(path = ["/ui/{id}"])  //TODO probably change to PUT
    fun setPosition(@PathVariable id: String, @RequestBody ui: UI): ResponseEntity<Task> {
        println("set UI of $id to x=${ui.xposition} | y=${ui.yposition}")
        val savedTask = taskService.save(
            Task(
                id, null, false, "updated task",
                TaskDescription("", "", ""),
                LocalDate.now(), 0, listOf(), ui
            )
        )
        return ResponseEntity.ok(savedTask)
    }

    @DeleteMapping(path = ["/tasks"])
    fun deleteTask(@RequestBody task: Task) {
        println("delete task: $task")
        taskService.delete(task)
    }



    @GetMapping(path = ["/sample"])
    fun getSample(): ResponseEntity<ArrayList<Task>> {
        val tasks = ArrayList<Task>()
        val task1 = Task(UUID.randomUUID().toString(), null,false,"First Sample",
            TaskDescription("...", "...", "..."),
            LocalDate.now(), 0, null, UI(0, 0)
        )
        val task2 = Task(UUID.randomUUID().toString(), null, false, "First Sample",
            TaskDescription("...", "...", "..."),
            LocalDate.now(), 0, null, UI(0, 0))

        val task3 = Task(UUID.randomUUID().toString(), null, false,"First Sample",
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