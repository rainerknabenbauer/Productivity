package de.nykon.productivity.domain.value

class TaskDescription(
    val shortDescription: String,
    val longDescription: String,
    val additionalNotes: String? = null
)