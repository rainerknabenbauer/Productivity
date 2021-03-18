package de.nykon.productivity.domain.value

data class TaskDescription(
    val shortDescription: String,
    val longDescription: String,
    val additionalNotes: String? = null
) {
    override fun toString(): String {
        return String.format("[shortdescription=%s, longdescription=%s, additionalNotes=%s]",
            shortDescription, longDescription, additionalNotes)
    }
}