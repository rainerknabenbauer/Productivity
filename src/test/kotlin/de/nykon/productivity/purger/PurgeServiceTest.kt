package de.nykon.productivity.purger

import com.ninjasquad.springmockk.MockkBean
import de.nykon.productivity.domain.ProjectRepository
import de.nykon.productivity.domain.TaskRepository
import de.nykon.productivity.domain.value.Project
import de.nykon.productivity.domain.value.Task
import de.nykon.productivity.domain.value.TaskDescription
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class PurgeServiceTest(
    @Autowired private val purgeService: PurgeService
) {

    @MockkBean
    private lateinit var projectRepository: ProjectRepository

    @MockkBean
    private lateinit var taskRepository: TaskRepository

    @Test
    fun `successfully purge project when it has no child tasks`() {
        // arrange
        val project = Project()

        every { taskRepository.findByProjectId(project.projectId) } returns emptyList()
        every { projectRepository.delete(project) } just runs
        every { taskRepository.deleteAll(emptyList()) } just runs

        // act
        purgeService.purgeUnusedProjects(listOf(project))

        // assert
        verify(exactly=1) { projectRepository.delete(project) }
        verify(exactly=1) { taskRepository.deleteAll(emptyList()) }
    }

    @Test
    fun `successfully purge project when it only contains starter tasks`() {
        // arrange
        val project = Project()
        val tasks = ArrayList<Task>()
        val task = Task(
            projectId = project.projectId,
            title = "first steps",
            description = TaskDescription("","",null),
            isDeleted = true
        )
        tasks.add(task)

        every { taskRepository.findByProjectId(project.projectId) } returns tasks
        every { projectRepository.delete(project) } just runs
        every { taskRepository.deleteAll(tasks) } just runs

        // act
        purgeService.purgeUnusedProjects(listOf(project))

        // assert
        verify(exactly=1) { projectRepository.delete(project) }
        verify(exactly=1) { taskRepository.deleteAll(tasks) }
    }

    @Test
    fun `successfully purge project when it only contains deleted tasks`() {
        // arrange
        val project = Project()
        val tasks = ArrayList<Task>()
        val task = Task(
            projectId = project.projectId,
            title = "demo1",
            description = TaskDescription("","",null),
            isDeleted = true
        )
        tasks.add(task)

        every { taskRepository.findByProjectId(project.projectId) } returns tasks
        every { projectRepository.delete(project) } just runs
        every { taskRepository.deleteAll(tasks) } just runs

        // act
        purgeService.purgeUnusedProjects(listOf(project))

        // assert
        verify(exactly=1) { projectRepository.delete(project) }
        verify(exactly=1) { taskRepository.deleteAll(tasks) }
    }

    @Test
    fun `failed purge project when it still contains active custom tasks`() {
        // arrange
        val project = Project()
        val tasks = ArrayList<Task>()
        tasks.add(Task(
            projectId = project.projectId,
            title = "demo1",
            description = TaskDescription("","",null),
            isDeleted = true
        ))
        tasks.add(Task(
            projectId = project.projectId,
            title = "demo2",
            description = TaskDescription("","",null),
            isDeleted = false
        ))

        every { taskRepository.findByProjectId(project.projectId) } returns tasks
        every { projectRepository.delete(project) } just runs
        every { taskRepository.deleteAll(tasks) } just runs

        // act
        purgeService.purgeUnusedProjects(listOf(project))

        // assert
        verify(exactly=0) { projectRepository.delete(project) }
        verify(exactly=0) { taskRepository.deleteAll(tasks) }
    }

    @Test
    fun `failed purge project when it contains only active custom tasks`() {
        // arrange
        val project = Project()
        val tasks = ArrayList<Task>()
        tasks.add(Task(
            projectId = project.projectId,
            title = "demo1",
            description = TaskDescription("","",null),
            isDeleted = false
        ))
        tasks.add(Task(
            projectId = project.projectId,
            title = "demo2",
            description = TaskDescription("","",null),
            isDeleted = false
        ))

        every { taskRepository.findByProjectId(project.projectId) } returns tasks
        every { projectRepository.delete(project) } just runs
        every { taskRepository.deleteAll(tasks) } just runs

        // act
        purgeService.purgeUnusedProjects(listOf(project))

        // assert
        verify(exactly=0) { projectRepository.delete(project) }
        verify(exactly=0) { taskRepository.deleteAll(tasks) }
    }

}