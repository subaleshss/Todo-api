package com.example.todoapp.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class SignUpRequest(
    @field:NotBlank
    val firstName: String,
    @field:NotBlank
    val lastName: String,
    @field:NotBlank
    @field:Email
    val email: String,
    @field:NotBlank
    val password: String,
) {
    override fun toString(): String {
        return "SignUpRequest(" +
                "email='$email', " +
                "password=PROTECTED, " +
                "firstName='$firstName', " +
                "lastName='$lastName', " +
                "email='$email' )"
    }
}
