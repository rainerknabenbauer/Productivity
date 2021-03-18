package de.nykon.productivity.email

import de.nykon.productivity.domain.value.Project
import de.nykon.productivity.domain.value.Task
import de.nykon.productivity.domain.value.TaskDescription
import de.nykon.productivity.domain.value.UI
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
internal class MailerTest(
    @Autowired private val mailer: Mailer
) {

    @Test
    fun `successfully send deadline reminder`() {
        // arrange
        val task = Task(
            title = "Bloho",
            projectId = UUID.randomUUID().toString(),
            description = TaskDescription(shortDescription = "",longDescription = ""),
            priority = 0,
            ui = UI(2,2),
        )

        // act
        val deadlineEmail = mailer.createDeadlineEmail(task)

        // assert
        assertAll("mailer",
            { assertEquals("A task is due today! from PRODUCTIVITY", deadlineEmail.title) },
        )
    }

    @Test
    fun `successfully send recovery email`() {
        // arrange
        val project = Project()

        // act
        val deadlineEmail = mailer.createRecoveryEmail(listOf(project))

        // assert
        assertAll("mailer",
            { assertEquals("Recovery of your projects from PRODUCTIVITY", deadlineEmail.title) },
        )
    }

}