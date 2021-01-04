package de.nykon.productivity

import de.nykon.productivity.value.Task
import de.nykon.productivity.value.TaskDescription
import de.nykon.productivity.value.UI
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.util.*

@RestController
class TaskController(private val taskService: TaskService) {

    @GetMapping(path = ["/tasks"])
    fun getTasks(): ResponseEntity<MutableList<Task>> {

        println("called getTasks")
        return ResponseEntity.ok(taskService.findAll())
    }

    @PostMapping(path = ["/ui/{id}"])
    fun setPosition(@PathVariable id: UUID, @RequestBody ui: UI): Task {
        println("set UI of $id to x=${ui.xPosition} | y=${ui.yPosition}")
        return taskService.save(Task(id, "updated task",
            TaskDescription("","",""),
            LocalDate.now(), 0, listOf(), listOf(), ui))
    }

}