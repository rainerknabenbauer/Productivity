package de.nykon.productivity.authorization

import de.nykon.productivity.authorization.value.Credentials
import de.nykon.productivity.authorization.value.Session
import org.passay.CharacterData
import org.passay.CharacterRule
import org.passay.EnglishCharacterData
import org.passay.PasswordGenerator
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.security.SecureRandom
import java.util.*

@Service
class AuthorizationService(
    private val credentialsRepository: CredentialsRepository,
    private val sessionRepository: SessionRepository,
    private val passwordEncoder: PasswordEncoder,
    private val passwordGenerator: PasswordGenerator
) {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    fun authorizeSession(session: Session): Boolean {
        val project = sessionRepository.findById(session.projectId)

        if (project.isPresent) {
            log.info("project is present == true")
            return project.get().token == session.token
        }
        log.info("project not present == false with ID ${session.projectId}")
        return false
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

}
