package de.nykon.productivity

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

    @GetMapping(path = ["/tasks"])
    fun getTasks(): ResponseEntity<MutableList<Task>> {
        println("called getTasks")
        return ResponseEntity.ok(taskService.findAll())
    }

    @CrossOrigin(origins = ["http://localhost:5000"])
    @PostMapping(path = ["/tasks"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun saveTask(@RequestBody task: Task): Task {
        val savedTask = taskService.save(task)
        println("saved task: $savedTask")
        return savedTask
    }

    @PostMapping(path = ["/ui/{id}"])  //TODO probably change to PUT
    fun setPosition(@PathVariable id: String, @RequestBody ui: UI): Task {
        println("set UI of $id to x=${ui.xposition} | y=${ui.yposition}")
        return taskService.save(Task(id, false, "updated task",
            TaskDescription("","",""),
            LocalDate.now(), 0, listOf(), listOf(), ui))
    }



    @GetMapping(path = ["/sample"])
    fun getSample(): ArrayList<Task> {
        val tasks = ArrayList<Task>()
        val task1 = Task(UUID.randomUUID().toString(), false,"First Sample",
            TaskDescription("...", "...", "..."),
            LocalDate.now(), 0, null, null, UI(0, 0)
        )
        val task2 = Task(UUID.randomUUID().toString(), false, "First Sample",
            TaskDescription("...", "...", "..."),
            LocalDate.now(), 0, null, null, UI(0, 0))

        val task3 = Task(UUID.randomUUID().toString(), false,"First Sample",
            TaskDescription("...", "...", "..."),
            LocalDate.now(), 0, null, null, UI(0, 0))

        tasks.add(task1)
        tasks.add(task2)
        tasks.add(task3)

        taskService.save(task1)
        taskService.save(task2)
        taskService.save(task3)

        return tasks
    }

}