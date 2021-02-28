package de.nykon.productivity.authorization

import com.fasterxml.jackson.core.JsonEncoding
import com.ninjasquad.springmockk.MockkBean
import de.nykon.productivity.authorization.value.Credentials
import de.nykon.productivity.authorization.value.Session
import io.mockk.every
import org.json.JSONObject
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
internal class AuthorizationControllerTest(
    @Autowired val mockMvc: MockMvc
) {

    @MockkBean
    private lateinit var authorizationService: AuthorizationService

    @Test
    fun `missing basic auth header returns bad request`() {
        // arrange

        // act
        val actual = mockMvc.perform(get("/auth")
            .header("Content-Type", MediaType.APPLICATION_JSON)
        )

        // assert
        actual.andExpect(status().isBadRequest)
    }

    @Test
    fun `basic auth header is valid and returns session token`() {
        // arrange
        val projectId = "mockProjectId"
        val password = "mockPassword"
        val credentials = Credentials(projectId, password)
        val expected = Session("mockProjectId", "mockSessionToken")

        every { authorizationService.authorizePassword(credentials) } returns Optional.of(expected)

        // act
        val actual = mockMvc.perform(get("/auth")
            .header("Authorization", "Basic bW9ja1Byb2plY3RJZDptb2NrUGFzc3dvcmQ=")
            .header("Content-Type", MediaType.APPLICATION_JSON)
        )

        // assert
        actual.andExpect(status().isOk)
            .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON.toString()))
            .andExpect(content().string("""{"projectId":"mockProjectId","token":"mockSessionToken"}"""))
    }

    @Test
    fun session() {
        // arrange
        val projectId = "mockProjectId"
        val sessionToken = "mockSessionToken"
        val session = Session(projectId, sessionToken)

        every { authorizationService.authorizeSession(session) } returns true

        // act
        val actual = mockMvc.perform(get("/session")
            .header("Authorization", "Basic bW9ja1Byb2plY3RJZDptb2NrU2Vzc2lvblRva2Vu")
            .header("Content-Type", MediaType.APPLICATION_JSON)
        )

        // assert
        actual.andExpect(status().isOk)
    }

    @Test
    fun `setting new credentials returns new session`() {
        // arrange
        val projectId = "mockProjectId"
        val password = "mockPassword"
        val credentials = Credentials(projectId, password)
        val expected = Session("mockProjectId", "mockSessionToken")

        every { authorizationService.setCredentials(credentials) } returns expected

        // act
        val actual = mockMvc.perform(post("/auth")
            .header("Content-Type", MediaType.APPLICATION_JSON)
            .content("""{"projectId":"mockProjectId","password":"mockPassword"}""")
        )

        // assert
        actual.andExpect(status().isOk)
            .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON.toString()))
            .andExpect(content().string("""{"projectId":"mockProjectId","token":"mockSessionToken"}"""))
    }
}