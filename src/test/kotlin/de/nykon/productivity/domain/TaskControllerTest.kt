package de.nykon.productivity.domain

import com.google.gson.Gson
import com.ninjasquad.springmockk.MockkBean
import de.nykon.productivity.authorization.AuthorizationService
import de.nykon.productivity.authorization.value.AuthorizationStatus
import de.nykon.productivity.domain.value.ImminentTasksQuery
import de.nykon.productivity.domain.value.Project
import de.nykon.productivity.domain.value.Task
import de.nykon.productivity.domain.value.TaskDescription
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
internal class TaskControllerTest(
    @Autowired val gson: Gson,
    @Autowired val mockMvc: MockMvc
) {

    @MockkBean
    private lateinit var projectService: ProjectService

    @MockkBean
    private lateinit var taskService: TaskService

    @MockkBean
    private lateinit var authorizationService: AuthorizationService

    @Test
    fun `successfully retrieve all tasks associated with a project`() {
        // arrange
        val authorizationBase64 = "mockBase64String"
        val projectId = "mockProjectId"
        val tasks = mutableListOf<Task>()
        tasks.add(
            Task(
            title = "mock task 1",
            projectId = projectId,
            description = TaskDescription("","")
        )
        )

        every { authorizationService.isAuthorized(projectId, authorizationBase64) } returns AuthorizationStatus.SUCCESSFUL
        every { taskService.getByProject(projectId) } returns tasks

        // act
        val actual = mockMvc.perform(
            MockMvcRequestBuilders.get("/tasks/$projectId")
            .header("Content-Type", MediaType.APPLICATION_JSON)
            .header("Authorization", authorizationBase64)
        )

        // assert
        actual.andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `failed authorization to retrieve all tasks`() {
        // arrange
        val authorizationBase64 = "mockBase64String"
        val projectId = "mockProjectId"
        val tasks = mutableListOf<Task>()
        tasks.add(
            Task(
                title = "mock task 1",
                projectId = projectId,
                description = TaskDescription("","")
            )
        )

        every { authorizationService.isAuthorized(projectId, authorizationBase64) } returns AuthorizationStatus.FAILED
        every { taskService.getByProject(projectId) } returns tasks

        // act
        val actual = mockMvc.perform(
            MockMvcRequestBuilders.get("/tasks/$projectId")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", authorizationBase64)
        )

        // assert
        actual.andExpect(MockMvcResultMatchers.status().isUnauthorized)
    }

    @Test
    fun `successfully save a task`() {
        // arrange
        val authorizationBase64 = "mockBase64String"
        val projectId = "mockProjectId"
        val task = Task(
            title = "mock task 1",
            projectId = projectId,
            description = TaskDescription("","")
        )

        every { authorizationService.isAuthorized(task, authorizationBase64) } returns AuthorizationStatus.SUCCESSFUL
        every { taskService.save(task) } returns task

        // act
        val actual = mockMvc.perform(
            MockMvcRequestBuilders.post("/tasks")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", authorizationBase64)
                .content(gson.toJson(task))
        )

        // assert
        actual.andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `failed authorization to save a task`() {
        // arrange
        val authorizationBase64 = "mockBase64String"
        val projectId = "mockProjectId"
        val task = Task(
            title = "mock task 1",
            projectId = projectId,
            description = TaskDescription("","")
        )

        every { authorizationService.isAuthorized(task, authorizationBase64) } returns AuthorizationStatus.FAILED
        every { taskService.save(task) } returns task

        // act
        val actual = mockMvc.perform(
            MockMvcRequestBuilders.post("/tasks")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", authorizationBase64)
                .content(gson.toJson(task))
        )

        // assert
        actual.andExpect(MockMvcResultMatchers.status().isUnauthorized)
    }

    @Test
    fun `successfully remove a task from the database`() {
        // arrange
        val authorizationBase64 = "mockBase64String"
        val projectId = "mockProjectId"
        val task = Task(
            title = "mock task 1",
            projectId = projectId,
            description = TaskDescription("","")
        )

        every { authorizationService.isAuthorized(task, authorizationBase64) } returns AuthorizationStatus.SUCCESSFUL
        every { taskService.delete(task) } just runs

        // act
        val actual = mockMvc.perform(
            MockMvcRequestBuilders.delete("/tasks")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", authorizationBase64)
                .content(gson.toJson(task))
        )

        // assert
        actual.andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `failed authorization to remove a task from the database`() {
        // arrange
        val authorizationBase64 = "mockBase64String"
        val projectId = "mockProjectId"
        val task = Task(
            title = "mock task 1",
            projectId = projectId,
            description = TaskDescription("","")
        )

        every { authorizationService.isAuthorized(task, authorizationBase64) } returns AuthorizationStatus.FAILED
        every { taskService.delete(task) } just runs

        // act
        val actual = mockMvc.perform(
            MockMvcRequestBuilders.delete("/tasks")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", authorizationBase64)
                .content(gson.toJson(task))
        )

        // assert
        actual.andExpect(MockMvcResultMatchers.status().isUnauthorized)
    }

    @Test
    fun `successfully query imminent tasks using email as identifier`() {
        // arrange
        val authorizationBase64 = "mockAuthorization"
        val query = ImminentTasksQuery("mock@email.com", null)
        val projects = ArrayList<Project>()
        projects.add(Project())
        projects.add(Project())
        val tasks = ArrayList<Task>()
        tasks.add(Task(
            projectId = projects.first().projectId,
            title = "Hello moto",
            description = TaskDescription("You better", "read a book", null)))
        tasks.add(Task(
            projectId = projects.first().projectId,
            title = "Roboto",
            description = TaskDescription("I build", "recursively", null)))

        every { projectService.findByEmail(query.email) } returns projects
        every { authorizationService.isAuthorized(projects, authorizationBase64) } returns AuthorizationStatus.SUCCESSFUL
        every { taskService.getImminentTasks(projects) } returns tasks

        // act
        val actual = mockMvc.perform(
            MockMvcRequestBuilders.get("/v1/tasks/imminent")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", authorizationBase64)
                .content(gson.toJson(query))
        )


        // assert
        actual.andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `failed to query imminent tasks with invalid authentication`() {
        // arrange
        val authorizationBase64 = "mockAuthorization"
        val query = ImminentTasksQuery("mock@email.com", null)
        val projects = ArrayList<Project>()
        projects.add(Project())
        projects.add(Project())
        val tasks = ArrayList<Task>()
        tasks.add(Task(
            projectId = projects.first().projectId,
            title = "Hello moto",
            description = TaskDescription("You better", "read a book", null)))
        tasks.add(Task(
            projectId = projects.first().projectId,
            title = "Roboto",
            description = TaskDescription("I build", "recursively", null)))

        every { projectService.findByEmail(query.email) } returns projects
        every { authorizationService.isAuthorized(projects, authorizationBase64) } returns AuthorizationStatus.FAILED
        every { taskService.getImminentTasks(projects) } returns tasks

        // act
        val actual = mockMvc.perform(
            MockMvcRequestBuilders.get("/v1/tasks/imminent")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", authorizationBase64)
                .content(gson.toJson(query))
        )


        // assert
        actual.andExpect(MockMvcResultMatchers.status().isUnauthorized)
    }


}