package com.example.todoapp.application.service

import com.example.todoapp.application.command.RegisterUserCommand
import com.example.todoapp.domain.model.User
import com.example.todoapp.domain.model.UserRole
import com.example.todoapp.domain.repository.UserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.assertThrows
import org.springframework.security.crypto.password.PasswordEncoder
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UserCommandServiceImplTest {

    private val passwordEncoder: PasswordEncoder = mockk()
    private val userRepository = mockk<UserRepository>()

    private val userCommandService = UserCommandServiceImpl(userRepository, passwordEncoder)

    @Test
    fun `should create new user successfully`() {
        val command = RegisterUserCommand(
            firstName = "John",
            lastName = "Doe",
            email = "john@example.com",
            password = "secret123"
        )

        val encodedPassword = "encoded_secret123"

        val savedUser = User(
            id = 1L,
            firstName = command.firstName,
            lastName = command.lastName,
            email = command.email,
            password = encodedPassword,
            isActive = true,
            roleName = UserRole.USER
        )

        every { userRepository.existsByEmail(command.email) } returns false
        every { passwordEncoder.encode(command.password) } returns encodedPassword
        every { userRepository.save(any()) } returns savedUser

        val user = userCommandService.handle(command)

        assertEquals(savedUser.email, user.email)
        assertEquals(savedUser.firstName, user.firstName)
        assertEquals(savedUser.lastName, user.lastName)
        assertEquals(savedUser.roleName, user.roleName)
        assertTrue(user.isActive)
    }

    @Test
    fun `should throw EmailAlreadyExistsException when email already exists`() {
        val command = RegisterUserCommand(
            firstName = "John",
            lastName = "Doe",
            email = "john@example.com",
            password = "secret123"
        )

        every { userRepository.existsByEmail(command.email) } returns true

        assertThrows<EmailAlreadyExistsException> { userCommandService.handle(command) }
        verify(exactly = 0) { userRepository.save(any()) }
        verify(exactly = 0) { passwordEncoder.encode(any()) }
    }

    @Test
    fun `should encode password before saving`() {

        val command = RegisterUserCommand(
            firstName = "John",
            lastName = "Doe",
            email = "john@example.com",
            password = "plaintext"
        )
        val encodedPassword = "encoded_plaintext"
        val savedUser = User(
            id = 1L,
            firstName = command.firstName,
            lastName = command.lastName,
            email = command.email,
            password = encodedPassword,
            isActive = true,
            roleName = UserRole.USER
        )

        every { userRepository.existsByEmail(command.email) } returns false
        every { passwordEncoder.encode(command.password) } returns encodedPassword
        every { userRepository.save(any()) } returns savedUser

        userCommandService.handle(command)

        verify {
            userRepository.save(match { user ->
                user.extractPassword() == encodedPassword
            })
        }
    }
}