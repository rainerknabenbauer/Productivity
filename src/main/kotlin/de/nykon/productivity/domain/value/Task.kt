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
    val deadline: String?,
    @Indexed val notifyRelativeDate: String?,
    @Indexed val notifyDateBeforeDeadline: String?,
    val priority: Int,
    val parentTasks: List<String>?,
    val ui: UI,
    val createTime: LocalDateTime = LocalDateTime.now()

)