package de.nykon.productivity.domain.value

data class ImminentTasksQuery(
    val email: String,
    val untilDate: String?
)