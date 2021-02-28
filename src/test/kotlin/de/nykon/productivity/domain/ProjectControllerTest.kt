package de.nykon.productivity.domain

import com.ninjasquad.springmockk.MockkBean
import de.nykon.productivity.domain.value.Project
import de.nykon.productivity.email.MailService
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
internal class ProjectControllerTest(
    @Autowired val mockMvc: MockMvc
) {

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
        val createTime = LocalDateTime.now()

        every { projectService.findById(projectId) } returns Optional.empty()

        // act
        val actual = mockMvc.perform(get("/projects/$projectId"))

        // assert
        actual.andExpect(status().isNotFound)
    }
    
}