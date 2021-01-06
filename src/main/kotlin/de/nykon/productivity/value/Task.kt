package de.nykon.productivity.value

import com.fasterxml.jackson.annotation.JsonIgnore
import org.bson.types.ObjectId
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
    @JsonIgnore @Id val databaseId: ObjectId?,
    val id: UUID = UUID.randomUUID(),
    val active: Boolean = false,
    val title: String,
    val description: TaskDescription,
    val endDate: LocalDate?,
    val priority: Int,
    val preDependency: List<UUID>?,
    val postDependency: List<UUID>?,
    val ui: UI
)