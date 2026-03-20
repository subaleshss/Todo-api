package com.example.todoapp.application.command

data class RegisterUserCommand(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)
