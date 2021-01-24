package de.nykon.productivity


import de.nykon.productivity.value.Project
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository


@Repository
interface ProjectRepository : MongoRepository<Project, String> {

    fun findByEmail(email: String): List<Project>


}