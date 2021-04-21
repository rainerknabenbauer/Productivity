package de.nykon.productivity.domain

import de.nykon.productivity.domain.value.Project
import de.nykon.productivity.domain.value.Task
import de.nykon.productivity.domain.value.TaskDescription
import de.nykon.productivity.domain.value.UI
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


@Service
class TaskService(
    private val taskRepository: TaskRepository,
    private val mongoTemplate: MongoTemplate) {

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
        val sqlDate = getCurrentSqlDate()

        return getAllDueTodayTasks(sqlDate)
    }

    fun getAllDueTodayTasks(sqlDate: String): List<Task> {

        val relativeDate = taskRepository.findByNotifyRelativeDate(sqlDate)
        val deadline = taskRepository.findByNotifyDateBeforeDeadline(sqlDate)

        // condense to unique tasks only

        val dueTasks = ArrayList<Task>()
        dueTasks.addAll(relativeDate)
        dueTasks.addAll(deadline)

        return dueTasks
    }

    fun getImminentTasks(projects: List<Project>): List<Task> {

        // 1. Find all tasks for all projects that are not deleted
        val sqlDate = getCurrentSqlDate()

        val imminentTasks = ArrayList<Task>()
        for (project in projects) {

            val tasks = taskRepository.findByProjectId(project.projectId)
                .filter { task -> !task.isDeleted }
                .filter { task ->
                    task.notifyDateBeforeDeadline == sqlDate || task.notifyRelativeDate == sqlDate
                }


            imminentTasks.addAll(
                tasks     //TODO add date sensitive query
            )
        }

        return imminentTasks
    }

    // Get today in SQL format
    private fun getCurrentSqlDate(): String {
        val date = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd")
        return formatter.format(date)
    }

    /**
     * When a new project is created it gets a default first task injected.
     */
    fun setupTasksForNewProject(project: Project) {
        val task = Task(UUID.randomUUID().toString(), project.projectId, isBeingWorkedOn = true, isDeleted = false,"First steps",
            TaskDescription(
                """
                    Click the pen in the lower right corner to see details.
                    
                    You can edit the details as you wish.
                """.trimIndent(),
                """
                    Hello and welcome. 
                    
                    If you are new here, consider these next steps:
                    
                    1. Save the project link to your favorites
                    2. Set a reminder so you can access your projects even if you lose the link
                    3. Add a new task
                    4. Start to organize
                """.trimIndent(),
                """
                    You can edit this task, save it and view the changes again.
                    
                    You can also use the 'X' in the upper right corner of the task to delete it.
                """.trimIndent()
            ),
            null, null, null,0, emptyList(), UI(350, 200), LocalDateTime.now())

        save(task)
    }

}