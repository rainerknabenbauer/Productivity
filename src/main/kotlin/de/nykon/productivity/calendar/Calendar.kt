package de.nykon.productivity.calendar

import de.nykon.productivity.domain.value.InfrastructureConfig
import de.nykon.productivity.domain.value.CurrentDate
import de.nykon.productivity.domain.value.Task
import org.springframework.stereotype.Component

/**
 *
 *
 *
 * For full day event use the DATE data type, there's no need for a DTEND:
 *      DTSTART;VALUE=DATE:20100101
 */
@Component
class Calendar(
    private val currentDate: CurrentDate,
    private val infrastructureConfig: InfrastructureConfig
) {

    val CALENDAR = """
         BEGIN:VCALENDAR
         VERSION:2.0
         PRODID://to/productivity
         {{COMPONENTS}}
         END:VCALENDAR
    """.trimIndent()

    val COMPONENT_EVENT = """
         BEGIN:VEVENT
         UID:{{UID}}@productivity.to
         DTSTAMP:{{DTSTAMP}}
         DTSTART;VALUE=DATE:{{DATE}}
         SUMMARY:{{SUMMARY}}
         DESCRIPTION:{{DESCRIPTION}}
         URL:{{PROJECT_URL}}
         END:VEVENT
    """.trimIndent()

    fun build(tasks: List<Task>): String {
        return CALENDAR.replace("{{COMPONENTS}}",
            buildComponents(tasks, currentDate.toMySql(), infrastructureConfig.frontendUri))
    }

    private fun buildComponents(tasks: List<Task>, today: String, uri: String): String {
        var components = ""
        for (task in tasks) {
            components += COMPONENT_EVENT
                .replace("{{UID}}", task.id)
                .replace("{{DTSTAMP}}", currentDate.toMySql().replace("-", "") + "T000000Z")
                .replace("{{DATE}}", today.replace("-", ""))
                .replace("{{SUMMARY}}", task.title)
                .replace("{{DESCRIPTION}}",
                    task.description.shortDescription
                            + " | " + task.description.longDescription
                            + " | " + task.description.additionalNotes
                )
                .replace("{{PROJECT_URL}}", uri + "?id=" + task.projectId)
        }
        return components
    }

}