package de.nykon.productivity.domain

import com.google.gson.Gson
import com.ninjasquad.springmockk.MockkBean
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
internal class TaskControllerTest(
    @Autowired val gson: Gson,
    @Autowired val mockMvc: MockMvc
) {

    @MockkBean
    private lateinit var taskService: TaskService

    @Test
    fun `successfully retrieve all tasks associated with a project`() {
        // arrange

        // act


        // assert

    }

    @Test
    fun `failed authorization to retrieve all tasks`() {
        //TODO Not implemented
    }

    @Test
    fun `successfully save a task`() {
        //TODO Not implemented
    }

    @Test
    fun `failed authorization to save a task`() {
        //TODO Not implemented
    }

    @Test
    fun `successfully store new UI location of task`() {
        //TODO Not implemented
    }

    @Test
    fun `successfully remove a task from the database`() {
        //TODO Not implemented
    }

    @Test
    fun `failed authorization to remove a task from the database`() {
        //TODO Not implemented
    }
}