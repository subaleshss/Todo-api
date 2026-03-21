package com.example.todoapp.presentation.controller

import com.example.todoapp.application.service.EmailAlreadyExistsException
import com.example.todoapp.application.service.UserCommandService
import com.example.todoapp.domain.model.User
import com.example.todoapp.domain.model.UserRole
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import tools.jackson.databind.ObjectMapper

@WebMvcTest(UserController::class, excludeAutoConfiguration = [SecurityAutoConfiguration::class])
class UserControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockkBean
    private lateinit var userCommandService: UserCommandService

    private val validRequest = mapOf(
        "firstName" to "John",
        "lastName" to "Doe",
        "email" to "john@example.com",
        "password" to "secret123"
    )

    private val savedUser = User(
        id = 1L,
        firstName = "John",
        lastName = "Doe",
        email = "john@example.com",
        password = "encoded",
        isActive = true,
        roleName = UserRole.USER
    )

    @Test
    fun `POST register should return 200 when registration succeeds`() {
        every { userCommandService.handle(any()) } returns savedUser

        mockMvc.post("/api/user/register") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(validRequest)
        }.andExpect {
            status { isOk() }
            jsonPath("$.success") { value(true) }
            jsonPath("$.message") { value("User registered successfully") }
            jsonPath("$.data.email") { value("john@example.com") }
        }
    }

    @Test
    fun `POST register should return 400 when email already exists`() {
        every { userCommandService.handle(any()) } throws
                EmailAlreadyExistsException("Email already exists")

        mockMvc.post("/api/user/register") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(validRequest)
        }.andExpect {
            status { isBadRequest() }
            jsonPath("$.success") { value(false) }
            jsonPath("$.message") { value("Email already exists") }
        }
    }

    @Test
    fun `POST register should return 400 when request body is invalid`() {
        val invalidRequest = mapOf(
            "firstName" to "",       // blank — violates @NotBlank
            "lastName" to "Doe",
            "email" to "not-an-email", // invalid email
            "password" to ""         // blank — violates @NotBlank
        )

        mockMvc.post("/api/user/register") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(invalidRequest)
        }.andExpect {
            status { isBadRequest() }
            jsonPath("$.success") { value(false) }
        }
    }
}