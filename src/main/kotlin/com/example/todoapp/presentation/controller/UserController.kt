    package com.example.todoapp.presentation.controller

    import com.example.todoapp.application.command.RegisterUserCommand
    import com.example.todoapp.application.service.UserCommandService
    import com.example.todoapp.common.ApiResponse
    import com.example.todoapp.domain.model.User
    import com.example.todoapp.presentation.dto.LoginRequest
    import com.example.todoapp.presentation.dto.SignUpRequest
    import com.example.todoapp.presentation.dto.UserResponse
    import jakarta.validation.Valid
    import org.springframework.http.ResponseEntity
    import org.springframework.web.bind.annotation.PostMapping
    import org.springframework.web.bind.annotation.RequestBody
    import org.springframework.web.bind.annotation.RequestMapping
    import org.springframework.web.bind.annotation.RestController

    @RestController
    @RequestMapping("/api/user")
    class UserController(
        private val userCommandService: UserCommandService,
    ) {

        @PostMapping("/register")
        fun registerUser(
            @Valid @RequestBody request: SignUpRequest
        ): ResponseEntity<ApiResponse<UserResponse?>> {
            val user =  userCommandService.handle(request.toCommand())
            return ResponseEntity.ok().body(
                ApiResponse(
                    success = true,
                    message = "User registered successfully",
                    data = user.toUserResponse()
                )
            )
        }

        @PostMapping("/login")
        fun loginUser(
            @Valid @RequestBody request: LoginRequest
        ) {

        }

        fun SignUpRequest.toCommand() = RegisterUserCommand(
            firstName = this.firstName,
            lastName = this.lastName,
            email = this.email,
            password = this.password
        )

        fun User.toUserResponse() = UserResponse(
            id = this.id,
            firstName = this.firstName,
            lastName = this.lastName,
            email = this.email,
            isActive = this.isActive
        )
    }