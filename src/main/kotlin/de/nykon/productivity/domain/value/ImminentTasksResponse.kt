package de.nykon.productivity.domain.value

import de.nykon.productivity.calendar.Calendar

class ImminentTasksResponse private constructor(builder: ImminentTasksResponse.Builder) {

    val email: String?
    val tasks: List<ImminentTask>?

    init {
        this.email = builder.email
        this.tasks = builder.tasks
    }

    class Builder() {
        var email: String? = null
            private set
        var tasks: ArrayList<ImminentTask> = ArrayList()
            private set

        fun email(email: String) = apply { this.email = email }
        fun tasks(fullTasks: List<Task>, calendar: Calendar) = apply {
            fullTasks.forEach {
                task -> tasks.add(
                    ImminentTask(
                        task.id,
                        task.projectId,
                        task.title,
                        task.description,
                        task.deadline,
                        task.notifyRelativeDate,
                        task.notifyDateBeforeDeadline,
                        calendar.build(listOf(task))
                    )
                )
            }
        }


        fun build() = ImminentTasksResponse(this)
    }
}