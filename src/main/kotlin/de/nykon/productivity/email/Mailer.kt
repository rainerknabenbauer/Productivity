package de.nykon.productivity.email

import de.nykon.productivity.domain.value.Project
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

    private val URI: String = "sirsmokealot.de"

    /**
     * Sends a recovery eMail to the user containing all linked projects.
     */
    fun sendRecoveryEmail(recipient: String, projects: List<Project>) {

        var projectLinks = ""
        projects.forEach { project ->
            run {
                projectLinks += if (Objects.nonNull(project.unlockToken)) {
                    getProjectLine()
                        .replace("{{link}}", "https://productivity.to/${project.projectId}")
                        .replace("{{text}}", project.name)
                        .replace("{{unlock}}",
                            " | <a href=\"https://productivity.to:8443/projects/unlock/${project.unlockToken}\">Unlock project</a><br>")
                } else {
                    getProjectLine()
                        .replace("{{link}}", "https://productivity.to/${project.projectId}")
                        .replace("{{text}}", project.name)
                        .replace("{{unlock}}", "<br>")
                }
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
        msg.subject = "Recover your PRODUCTIVITY projects"
        msg.setContent(message, "text/html; charset=UTF-8")
        msg.sentDate = Date()

        Transport.send(msg)
    }

    private fun getProjectLine(): String {
        return """
            <a href="{{link}}">{{text}}</a>{{unlock}}
        """.trimIndent()
    }

}