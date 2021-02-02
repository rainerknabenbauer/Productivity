package de.nykon.productivity.domain

import de.nykon.productivity.domain.value.Task
import org.springframework.stereotype.Service
import java.util.*

@Service
class TaskService(
    private val taskRepository: TaskRepository) {

    fun save(task: Task): Task {
        return taskRepository.save(task)
    }

    fun get(id: String): Optional<Task> {
        return taskRepository.findById(id)
    }

    fun findAll(): MutableList<Task> {
        return taskRepository.findAll()
    }

    fun delete(task: Task) {
        return taskRepository.delete(task)
    }

    fun getByProject(projectId: String): List<Task> {

        return taskRepository.findByProjectId(projectId)
    }

}