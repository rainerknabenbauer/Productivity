package de.nykon.productivity.domain

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.ninjasquad.springmockk.MockkBean
import de.nykon.productivity.domain.value.Project
import de.nykon.productivity.email.MailService
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
internal class ProjectControllerTest(
    @Autowired val gson: Gson,
    @Autowired val mockMvc: MockMvc
) {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @MockkBean
    private lateinit var projectService: ProjectService

    @MockkBean
    private lateinit var mailService: MailService

    @Test
    fun `valid token successfully unlocks project`() {
        // arrange
        val unlockToken = "mockUnlockToken"

        every { projectService.unlock(unlockToken) } returns true

        // act
        val actual = mockMvc.perform(get("/projects/unlock/$unlockToken"))

        // assert
        actual.andExpect(status().isOk)
            .andExpect(content().string("Project has been unlocked."))
    }

    @Test
    fun `invalid token gets rejected`() {
        // arrange
        val unlockToken = "mockUnlockToken"

        every { projectService.unlock(unlockToken) } returns false

        // act
        val actual = mockMvc.perform(get("/projects/unlock/$unlockToken"))

        // assert
        actual.andExpect(status().isGone)
            .andExpect(content().string("No matching project was found."))
    }

    @Test
    fun `receive project for valid projectID`() {
        // arrange
        val projectId = "mockProjectId"
        val createTime = LocalDateTime.now()
        val project = Project(projectId = projectId, createTime = createTime)

        every { projectService.findById(projectId) } returns Optional.of(project)

        // act
        val actual = mockMvc.perform(get("/projects/$projectId"))

        // assert
        actual.andExpect(status().isOk)
    }

    @Test
    fun `receive nothing for invalid projectID`() {
        // arrange
        val projectId = "mockProjectId"

        every { projectService.findById(projectId) } returns Optional.empty()

        // act
        val actual = mockMvc.perform(get("/projects/$projectId"))

        // assert
        actual.andExpect(status().isNotFound)
    }

    @Test
    fun `successfully update project`() {
        // arrange
        val project = Project()
        val projectJson = gson.toJson(project)

        every { projectService.save(project) } returns project
        
        // act
        val actual = mockMvc.perform(post("/projects")
            .header("Content-Type", MediaType.APPLICATION_JSON)
            .content(projectJson)
        )

        // assert
        actual.andExpect(status().isOk)
    }

    @Test
    fun `successfully create new project`() {
        //TODO Not implemented
    }

    @Test
    fun `successfully activate recovery mode for a project`() {
        //TODO Not implemented
    }
    
}