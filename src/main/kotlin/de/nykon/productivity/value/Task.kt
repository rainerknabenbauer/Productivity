package de.nykon.productivity.value

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.util.*

/**
 * A single task.
 *
 * @param preDependency Tasks, that need to be completed before
 * @param postDependency Following tasks
 */
@Document(collection = "tasks")
data class Task(
    @Id val id: String = UUID.randomUUID().toString(),
    val isActive: Boolean = false,
    val title: String,
    val description: TaskDescription,
    val endDate: LocalDate?,
    val priority: Int,
    val preDependency: List<UUID>?,
    val postDependency: List<UUID>?,
    val ui: UI
)