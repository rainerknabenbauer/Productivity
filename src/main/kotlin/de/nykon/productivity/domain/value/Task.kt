package de.nykon.productivity.domain.value

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.TextIndexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

/**
 * A single task.
 */
@Document(collection = "tasks")
data class Task(
    @Id val id: String = UUID.randomUUID().toString(),
    @TextIndexed val projectId: String?,
    val isBeingWorkedOn: Boolean = false,
    val isDeleted: Boolean = false,
    val title: String,
    val description: TaskDescription,
    val endDate: LocalDate?,
    val priority: Int,
    val parentTasks: List<String>?,
    val ui: UI,
    val createTime: LocalDateTime = LocalDateTime.now()

)