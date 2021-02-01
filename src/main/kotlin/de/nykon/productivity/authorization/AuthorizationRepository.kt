package de.nykon.productivity.authorization

import de.nykon.productivity.authorization.value.Authorization
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AuthorizationRepository : MongoRepository<Authorization, String> {

    override fun findById(id: String): Optional<Authorization>

}