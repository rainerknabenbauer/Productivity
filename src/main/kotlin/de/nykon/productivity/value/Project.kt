package de.nykon.productivity.value

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.*

/**
 * Encapsulates multiple tasks and manages related users.
 */
@Document(collection = "projects")
data class Project(
    @Id val projectId: String = UUID.randomUUID().toString(),
    val name: String = "New project",
    val description: String? = null,
    @Indexed val email: String? = null,
    val userIds: List<String> = emptyList(),
    val createTime: LocalDateTime = LocalDateTime.now()
)