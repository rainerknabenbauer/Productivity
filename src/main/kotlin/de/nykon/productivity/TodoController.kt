package de.nykon.productivity

import de.nykon.productivity.value.Task
import de.nykon.productivity.value.TaskDescription
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

@RestController
class TodoController {

    @GetMapping(path = ["/mocks"])
    fun getMocks(): ResponseEntity<ArrayList<Task>> {

        println("called getMock")

        val mocks = ArrayList<Task>()
        mocks.add(Task(
            UUID.randomUUID(),
            "Mock",
            TaskDescription("shortDescription", "long description", null),
            LocalDate.now(),
            0,
            null,
            null))

        mocks.add(Task(
            UUID.randomUUID(),
            "Mock 2",
            TaskDescription("shortDescription2", "long description2", null),
            LocalDate.now(),
            0,
            null,
            null))

        return ResponseEntity.ok(mocks);
    }

    @GetMapping(path = ["/mock"])
    fun getMock() = ResponseEntity.ok(Task(
        UUID.randomUUID(),
        "Mock",
        TaskDescription("shortDescription", "long description", null),
        LocalDate.now(),
        0,
        null,
        null))

    @GetMapping(path = ["", "/"])
    fun getDefault() : String {
        Thread.sleep(1000)
        return "Demo string"
    }
}