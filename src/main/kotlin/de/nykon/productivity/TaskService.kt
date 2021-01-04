package de.nykon.productivity

import de.nykon.productivity.value.Task
import org.springframework.stereotype.Service
import java.util.*

@Service
class TaskService(private val taskRepository: TaskRepository) {

    fun save(task: Task): Task {
        return taskRepository.save(task)
    }

    fun get(uuid: UUID): Optional<Task> {
        return taskRepository.findById(uuid)
    }

    fun findAll(): MutableList<Task> {
        return taskRepository.findAll()
    }

}