package de.nykon.productivity.value

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.TextIndexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.util.*

/**
 * A single task.
 */
@Document(collection = "tasks")
data class Task(
    @Id val id: String = UUID.randomUUID().toString(),
    @TextIndexed val projectId: String?,
    val isBeingWorkedOn: Boolean = false,
    val title: String,
    val description: TaskDescription,
    val endDate: LocalDate?,
    val priority: Int,
    val followUps: List<String>?,
    val ui: UI

)