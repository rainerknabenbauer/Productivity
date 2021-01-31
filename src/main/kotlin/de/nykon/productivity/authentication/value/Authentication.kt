package de.nykon.productivity.authentication.value

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Credentials for a protected project.
 */
@Document(collection = "credentials")
data class Authentication(
    @Id val projectId: String,
    val token: String
)