package com.example.todoapp.dto

data class UserResponse(
    val id: Long?,
    val firstName: String,
    val lastName: String,
    val email: String,
    val isActive: Boolean,
)
