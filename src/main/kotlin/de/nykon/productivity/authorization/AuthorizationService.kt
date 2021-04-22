package de.nykon.productivity.authorization

import de.nykon.productivity.authorization.value.AuthorizationStatus
import de.nykon.productivity.authorization.value.Credentials
import de.nykon.productivity.authorization.value.Session
import de.nykon.productivity.domain.ProjectService
import de.nykon.productivity.domain.value.Project
import de.nykon.productivity.domain.value.Task
import org.passay.CharacterRule
import org.passay.EnglishCharacterData
import org.passay.PasswordGenerator
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.security.SecureRandom
import java.util.*

@Service
class AuthorizationService(
    private val credentialsRepository: CredentialsRepository,
    private val sessionRepository: SessionRepository,
    private val passwordEncoder: PasswordEncoder,
    private val passwordGenerator: PasswordGenerator,
    private val projectService: ProjectService
) {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    fun authorizeSession(session: Session): AuthorizationStatus {
        val project = sessionRepository.findById(session.projectId)

        if (project.isPresent) {
            return validateToken(project.get(), session)
        }
        log.info("project not present == false with ID ${session.projectId}")
        return AuthorizationStatus.FAILED
    }

    private fun validateToken(expected: Session, actual: Session): AuthorizationStatus {
        return if (expected.token == actual.token) {
            AuthorizationStatus.SUCCESSFUL
        } else {
            AuthorizationStatus.FAILED
        }
    }

    fun authorizePassword(credentials: Credentials): Optional<Session> {
        // find project credentials
        val project = credentialsRepository.findById(credentials.projectId)

        if (project.isPresent) {
            // compare hashes
            val valid = verifyHash(credentials, project)

            if (valid) {
                // generate access token
                return Optional.of(generateSession(credentials.projectId))
            }
        }

        return Optional.empty()
    }

    private fun verifyHash(
        securedCredentials: Credentials,
        project: Optional<Credentials>
    ) = passwordEncoder.matches(securedCredentials.password, project.get().password)

    fun setCredentials(credentials: Credentials): Session {
        // encrypt password
        val securedCredentials = encryptPassword(credentials)
        credentialsRepository.save(securedCredentials)

        // generate access token
        return generateSession(credentials.projectId)
    }

    private fun generateSession(projectId: String): Session {
        val session = Session(projectId, generateToken())
        return sessionRepository.save(session)
    }

    private fun encryptPassword(credentials: Credentials): Credentials {
        val encryptedPassword = passwordEncoder.encode(credentials.password)
        return Credentials(credentials.projectId, encryptedPassword)
    }

    private fun generateToken(): String {

        val characterRule = CharacterRule(EnglishCharacterData.Alphabetical)

        val random = SecureRandom()
        val bytes = ByteArray(20)
        random.nextBytes(bytes)
        return passwordGenerator.generatePassword(20, characterRule)
    }

    /**
     * Tier 1: Verify that projectID is given.
     */
    fun isAuthorized(task: Task, authorizationBase64: String): AuthorizationStatus {
        return if (task.projectId != null) {
            isAuthorized(task.projectId, authorizationBase64)
        } else {
            AuthorizationStatus.FAILED
        }
    }

    /**
     * Tier 2: Verify that project with given projectID exists.
     */
    fun isAuthorized(projectId: String, authorizationBase64: String): AuthorizationStatus {
        val project = projectService.findById(projectId)

        return if (project.isPresent) {
            isAuthorized(project.get(), authorizationBase64)
        } else {
            log.error("Project $projectId not found.")
            AuthorizationStatus.FAILED
        }
    }

    /**
     * Tier 3: Verify that token matches the project's token.
     */
    fun isAuthorized(project: Project, authorizationBase64: String): AuthorizationStatus {
        return if (project.isProtected) {
            authorize(authorizationBase64)
        } else {
            AuthorizationStatus.SUCCESSFUL
        }
    }

    /**
     * Tier 3.b: Verify that given token is valid and gives access to all associated projects.
     */
    fun isAuthorized(projects: List<Project>, authorizationBase64: String): AuthorizationStatus {
        return isAuthorized(projects.first().projectId, authorizationBase64)
    }

    private fun authorize(authorizationBase64: String): AuthorizationStatus {
        val authorization = transform(authorizationBase64)

        if (authorization.isEmpty) {
            return AuthorizationStatus.FAILED
        }

        return authorizeSession(authorization.get())
    }

    /**
     * Extract the session information from a Base64 encoded string.
     */
    private fun transform(authorization: String): Optional<Session> {
        log.info("authorization is $authorization")
        return try {
            Optional.of(Session.fromBase64(authorization))
        } catch (ex: Exception) {
            log.error(ex.message)
            Optional.ofNullable(null)
        }
    }

}
