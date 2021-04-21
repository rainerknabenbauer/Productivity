package de.nykon.productivity.domain

import de.nykon.productivity.domain.value.Task
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface TaskRepository : MongoRepository<Task, String> {

    fun findByProjectId(projectId: String): List<Task>

    fun findByNotifyRelativeDate(sqlDate: String): List<Task>
    fun findByNotifyDateBeforeDeadline(sqlDate: String): List<Task>

}