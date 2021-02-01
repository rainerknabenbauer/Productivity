package de.nykon.productivity.authorization

import de.nykon.productivity.authorization.value.Authorization
import org.springframework.data.domain.Example
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthorizationService(
    private val authorizationRepository: AuthorizationRepository
) {

    fun authorization(authorization: Authorization): Optional<Authorization> {
        val example: Example<Authorization> = Example.of(authorization)
        return authorizationRepository.findOne(example)
    }

    fun setAuthorization(authorization: Authorization): Authorization {
        return authorizationRepository.save(authorization)
    }


}