package de.nykon.productivity.authorization

import de.nykon.productivity.authorization.value.Session
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SessionRepository : MongoRepository<Session, String> {

    override fun findById(id: String): Optional<Session>

}
