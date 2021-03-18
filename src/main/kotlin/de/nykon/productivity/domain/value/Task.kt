package de.nykon.productivity.domain.value

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.*

/**
 * A single task.
 */
@Document(collection = "tasks")
data class Task(
    @Id val id: String = UUID.randomUUID().toString(),
    @Indexed val projectId: String?,
    val isBeingWorkedOn: Boolean = false,
    val isDeleted: Boolean = false,
    val title: String,
    val description: TaskDescription,
    val deadline: String? = null,
    @Indexed val notifyRelativeDate: String? = null,
    @Indexed val notifyDateBeforeDeadline: String? = null,
    val priority: Int = 0,
    val parentTasks: List<String>? = null,
    val ui: UI = UI(25,25),
    val createTime: LocalDateTime = LocalDateTime.now()

)