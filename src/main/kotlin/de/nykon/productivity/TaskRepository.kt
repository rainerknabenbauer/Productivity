package de.nykon.productivity

import de.nykon.productivity.value.Task
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TaskRepository : MongoRepository<Task, UUID> {

}