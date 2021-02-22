package de.nykon.productivity.domain

import de.nykon.productivity.domain.value.Project
import de.nykon.productivity.domain.value.Task
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter
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

    fun delete(task: Task) {
        return taskRepository.delete(task)
    }

    fun getByProject(projectId: String): List<Task> {
        return taskRepository.findByProjectId(projectId)
    }

    fun getAllDueTodayTasks(): List<Task> {

        val date = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd")
        val sqlDate = formatter.format(date)

        val relativeDate = taskRepository.findByNotifyRelativeDate(sqlDate)
        val deadline = taskRepository.findByNotifyDateBeforeDeadline(sqlDate)

        // condense to unique tasks only

        val dueTasks = ArrayList<Task>()
        dueTasks.addAll(relativeDate)
        dueTasks.addAll(deadline)

        return dueTasks
    }

}