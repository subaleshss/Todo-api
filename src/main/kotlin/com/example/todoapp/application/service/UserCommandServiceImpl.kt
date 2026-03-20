package com.example.todoapp.application.service

import com.example.todoapp.application.command.RegisterUserCommand
import com.example.todoapp.domain.model.User
import com.example.todoapp.domain.model.UserRole
import com.example.todoapp.domain.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserCommandServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
): UserCommandService {

    override fun handle(command: RegisterUserCommand): User {
        if (userRepository.existsByEmail(command.email)) {
            throw EmailAlreadyExistsException("Email already exists")

        }

        val user = User(
            firstName = command.firstName,
            lastName = command.lastName,
            email = command.email,
            password = passwordEncoder.encode(command.password)
                ?: throw IllegalStateException("Password encoding failed"),
            isActive = true,
            roleName = UserRole.USER
        )

        return userRepository.save(user)
    }
}

class EmailAlreadyExistsException(message: String) : RuntimeException(message)