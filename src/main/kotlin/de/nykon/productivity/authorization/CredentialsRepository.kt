package de.nykon.productivity.authorization

import de.nykon.productivity.authorization.value.Credentials
import de.nykon.productivity.authorization.value.Session
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CredentialsRepository : MongoRepository<Credentials, String> {

    override fun findById(id: String): Optional<Credentials>

}