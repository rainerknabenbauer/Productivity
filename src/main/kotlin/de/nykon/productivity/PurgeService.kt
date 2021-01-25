package de.nykon.productivity

import de.nykon.productivity.value.Project
import de.nykon.productivity.value.Task
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.Period
import java.util.concurrent.atomic.AtomicInteger

@Service
class PurgeService(
    private val projectRepository: ProjectRepository,
    private val taskRepository: TaskRepository
) {

    fun purgeUnusedProjects(): Int {

        val purgableProjects = getPurgableProjects()
        val numberOfDeletedProjects = AtomicInteger(0)

        purgableProjects.forEach { project ->
            run {
                val tasks = taskRepository.findByProjectId(project.projectId)

                when {
                    tasks.isEmpty() -> {
                        purge(project, null)
                        numberOfDeletedProjects.incrementAndGet()
                    }
                    containsOnlyStarterTask(tasks) -> {
                        purge(project, tasks[0])
                    }
                    containsOnlyDeleted(tasks) -> {
                        numberOfDeletedProjects.incrementAndGet()
                        println("purge ${project.projectId} with ${tasks.size} tasks")
                        // purge(project, tasks)
                    }
                }
            }
        }

        return numberOfDeletedProjects.get()
    }

    private fun containsOnlyDeleted(tasks: List<Task>): Boolean {
        return tasks.stream().map { task -> !task.isDeleted }.count() > 0
    }

    private fun containsOnlyStarterTask(tasks: List<Task>) =
        tasks.size == 1 && tasks[0].title.toLowerCase() == "first steps"

    private fun purge(project: Project, task: Task?) {
        projectRepository.delete(project)
        if (task != null) taskRepository.delete(task)
    }

    /**
     *  Find projects that match the default project.
     *   - Project title == New Project
     *   - eMail == null
     *   - createdTime is older than 1 day
     */
    fun getPurgableProjects(): MutableList<Project> {
        val matcher: ExampleMatcher = ExampleMatcher.matching()
            .withIgnorePaths("projectId")
            .withIgnorePaths("createTime")

        val example: Example<Project> = Example.of(Project(), matcher)

        val candidates = projectRepository.findAll(example)

        val deleteMe = mutableListOf<Project>()
        candidates.forEach {
                candidate -> if(olderThanOneDay(candidate)) deleteMe.add(candidate)
        }

        return candidates
    }

    private fun olderThanOneDay(project: Project): Boolean {
        val period = Period.between(project.createTime.toLocalDate(), LocalDate.now());

        return period.days > 1
    }

}