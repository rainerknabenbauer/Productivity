package de.nykon.productivity.authentication

import de.nykon.productivity.authentication.value.Authentication
import org.springframework.data.domain.Example
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthenticationService(
    private val authenticationRepository: AuthenticationRepository
) {
    fun authenticate(authentication: Authentication): Optional<Authentication> {
        val example: Example<Authentication> = Example.of(authentication)
        return authenticationRepository.findOne(example)
    }

    fun setAuthentication(authentication: Authentication): Authentication {
        return authenticationRepository.save(authentication)
    }


}