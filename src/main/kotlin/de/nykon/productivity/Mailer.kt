package de.nykon.productivity

import de.nykon.productivity.value.Project
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import java.util.Date
import java.util.Properties

/**
 * Sends an eMail containing relevant information to stakeholders.
 */
@Component
class Mailer(private val config: MailerConfig) {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)
    private val URI: String = "sirsmokealot.de"

    /**
     * Sends a recovery eMail to the user containing all linked projects.
     */
    @Scheduled(cron = "0 0 5 * * *")
    @Async
    fun sendRecoveryEmail(recipient: String, projects: List<Project>) {

        var projectLinks = ""
        projects.forEach { project ->
            run {
                projectLinks += getProjectLine()
                    .replace("{{link}}", "http://localhost:8080/${project.projectId}")
                    .replace("{{text}}", project.name) + "<br>"
            }
        }

        val message = this::class.java.getResource("/recovery_mail.html").readText(Charsets.UTF_8)
            .replace("{{PROJECTS}}", projectLinks)

        send(recipient, message)
    }

    private fun send(recipient: String, message: String) {
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
        msg.setFrom(InternetAddress("recovery@$URI", false))

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient))
        msg.subject = "Recover all PRODUCTIVITY projects"
        msg.setContent(message, "text/html; charset=UTF-8")
        msg.sentDate = Date()

        Transport.send(msg)
    }

    private fun getProjectLine(): String {
        return """
            <a href="{{link}}">{{text}}</a>
        """.trimIndent()
    }

}