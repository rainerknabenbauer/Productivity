package de.nykon.productivity.domain.value

data class ImminentTask (
    val id: String,
    val projectId: String?,
    val title: String,
    val description: TaskDescription,
    val deadline: String? = null,
    val notifyRelativeDate: String? = null,
    val notifyDateBeforeDeadline: String? = null,
    val icalendar: String? = null
)