package de.nykon.productivity.email

import de.nykon.productivity.domain.value.Project
import de.nykon.productivity.domain.value.Task
import de.nykon.productivity.email.value.Mail
import org.springframework.stereotype.Component
import java.util.*
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

/**
 * Sends an eMail containing relevant information to stakeholders.
 */
@Component
class Mailer(
    private val config: MailConfig) {

    private val FRONTEND: String = "https://productivity.to/"
    private val BACKEND: String = "https://productivity.to:8443/"

    /**
     * Creates an eMail reminder for a deadline of a task.
     */
    fun createDeadlineEmail(task: Task): Mail {
        val title = "A task is due today! from PRODUCTIVITY"
        val content = this::class.java.getResource("/deadline_email.html")
            .readText(Charsets.UTF_8)
            .replace("{{TASK_TITLE}}", task.title)
            .replace("{{FRONTEND}}", FRONTEND)
            .replace("{{PROJECT_ID}}", task.projectId!!)

        return Mail(title, content)
    }

    /**
     * Creates a recovery eMail to the user containing all linked projects.
     */
    fun createRecoveryEmail(projects: List<Project>): Mail {

        var projectLinks = ""
        projects.forEach { project ->
            run {
                projectLinks += if (Objects.nonNull(project.unlockToken)) {
                    getProjectLine()
                        .replace("{{link}}", "${FRONTEND}?id=${project.projectId}")
                        .replace("{{text}}", project.name)
                        .replace("{{unlock}}",
                            " | <a href=\"${BACKEND}projects/unlock/${project.unlockToken}\">Unlock project</a><br>")
                } else {
                    getProjectLine()
                        .replace("{{link}}", "${FRONTEND}?id=${project.projectId}")
                        .replace("{{text}}", project.name)
                        .replace("{{unlock}}", "<br>")
                }
            }
        }

        val title = "Recovery of your projects from PRODUCTIVITY"
        val content = this::class.java.getResource("/recovery_mail.html")
            .readText(Charsets.UTF_8)
            .replace("{{PROJECTS}}", projectLinks)

        return Mail(title, content)
    }

    fun send(recipient: String, mail: Mail) {
        val props = Properties()
        props["mail.smtp.auth"] = "true"
        //props["mail.smtp.starttls.enable"] = config.starttls
        props["mail.smtp.host"] = config.host
        props["mail.smtp.port"] = "587"

        val session: Session = Session.getInstance(props, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(config.email, config.password)
            }
        })
        val msg: Message = MimeMessage(session)
        msg.setFrom(InternetAddress("recovery@sirsmokealot.de", false))

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient))
        msg.subject = mail.title
        msg.setContent(mail.content, "text/html; charset=UTF-8")
        msg.sentDate = Date()

        Transport.send(msg)
    }

    private fun getProjectLine(): String {
        return """
            <a href="{{link}}">{{text}}</a>{{unlock}}
        """.trimIndent()
    }

}