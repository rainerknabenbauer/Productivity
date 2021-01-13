package de.nykon.productivity

import de.nykon.productivity.value.Task
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface TaskRepository : MongoRepository<Task, String> {

    fun findByProjectId(projectId: String): List<Task>


}