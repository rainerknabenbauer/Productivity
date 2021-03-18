package de.nykon.productivity.domain.value

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.aggregation.DateOperators
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
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
    val ui: UI = UI(0,0),
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
            UI(0,0),
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
            UI(0,0),
            null,
            this.createTime
        )
    }
}