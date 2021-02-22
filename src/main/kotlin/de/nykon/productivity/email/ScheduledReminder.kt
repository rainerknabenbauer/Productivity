package de.nykon.productivity.email

import de.nykon.productivity.domain.ProjectService
import de.nykon.productivity.domain.TaskService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.*

@Component
class ScheduledReminder(
    private val mailService: MailService,
    private val taskService: TaskService,
    private val projectService: ProjectService
) {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Scheduled(cron ="0 0 3 * * *")
    fun sendDeadlineReminder() {
        // find all tasks that have a reminder set for today
        val dueTasks = taskService.getAllDueTodayTasks()

        log.info("Found ${dueTasks.size} due tasks")

        // for each task send email reminder
        dueTasks.forEach { task ->
            if (Objects.nonNull(task.projectId)) {
                val project = projectService.findById(task.projectId!!)

                if (project.isPresent) {
                    mailService.sendDeadlineReminder(project.get().email, task)
                }
            }
        }
    }

}