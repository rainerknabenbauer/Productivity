package de.nykon.productivity.authentication

import de.nykon.productivity.authentication.value.Authentication
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AuthenticationRepository : MongoRepository<Authentication, String> {

    override fun findById(id: String): Optional<Authentication>

}