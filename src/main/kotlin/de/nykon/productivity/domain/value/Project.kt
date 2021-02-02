package de.nykon.productivity.domain.value

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
    @Indexed val email: String = "",
    val isProtected : Boolean = false,
    val unlockToken: String? = null,
    val createTime: LocalDateTime = LocalDateTime.now()
) {

    fun recover(): Project {
        return Project(
            this.projectId,
            this.name,
            this.description,
            this.email,
            true,
            UUID.randomUUID().toString(),
            this.createTime
        )
    }

    fun unlock(): Project {
        return Project(
            this.projectId,
            this.name,
            this.description,
            this.email,
            false,
            null,
            this.createTime
        )
    }
}