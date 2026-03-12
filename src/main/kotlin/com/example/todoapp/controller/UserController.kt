package com.example.todoapp.controller

import com.example.todoapp.common.ApiResponse
import com.example.todoapp.dto.SignUpRequest
import com.example.todoapp.dto.UserResponse
import com.example.todoapp.entity.User
import com.example.todoapp.repository.RolesRepository
import com.example.todoapp.repository.UserRepository
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val rolesRepository: RolesRepository,
) {

    @PostMapping("/register")
    fun registerUser(
        @Valid @RequestBody request: SignUpRequest
    ): ResponseEntity<ApiResponse<UserResponse?>> {
        if (userRepository.existsByEmail(request.email)) {
            return ResponseEntity.badRequest().body(
                ApiResponse(success = false,message = "Email already in exit")
            )
        }

        val userRole = rolesRepository.findByName("USER") ?: throw IllegalStateException("USER role not found")

        val user = User(
            firstName = request.firstName,
            lastName = request.lastName,
            email = request.email,
            password = passwordEncoder.encode(request.password),
            isActive = true,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            role = userRole,
        )

        val savedUser = userRepository.save(user)

        val userResponse = UserResponse(
            id = savedUser.userId,
            firstName = savedUser.firstName,
            lastName = savedUser.lastName,
            email = savedUser.email,
            isActive = savedUser.isActive
        )

        return ResponseEntity.ok(ApiResponse(
            success = true,
            message = "Registration successful",
            data = userResponse
        ))
    }
}