package de.nykon.productivity.value

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Encapsulates multiple tasks and manages related users.
 */
@Document(collection = "projects")
data class Project(
    @Id val projectId: String,
    val name: String,
    val description: String,
    val userIds: List<String>,
    val taskIds: List<String>
)