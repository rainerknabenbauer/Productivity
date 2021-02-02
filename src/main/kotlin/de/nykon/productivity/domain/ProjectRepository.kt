package de.nykon.productivity.domain


import de.nykon.productivity.domain.value.Project
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface ProjectRepository : MongoRepository<Project, String> {

    fun findByEmail(email: String): List<Project>
    fun findFirstByUnlockToken(token: String): Project?


}