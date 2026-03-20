package com.example.todoapp.domain.model

import java.time.LocalDateTime

class User(
    val id: Long? = null,
    val firstName: String,
    val lastName: String,
    val email: String,
    password: String,
    isActive: Boolean = true,
    val roleName: UserRole = UserRole.USER
) {
    var password: String = password
        private set

    var isActive: Boolean = isActive
        private set

    var updatedAt: LocalDateTime? = null
        private set

    fun activate() : User {
        isActive = true
        updatedAt = LocalDateTime.now()
        return this
    }

    fun deactivate(): User {
        isActive = false
        updatedAt = LocalDateTime.now()
        return this
    }

    fun updatePassword(newPassword: String) : User {
        require(newPassword.isNotBlank()) { "Password cannot be blank" }
        password = newPassword
        updatedAt = LocalDateTime.now()
        return this
    }

    fun extractPassword(): String = password

    override fun toString(): String =
        "User(id=$id, email=$email, isActive=$isActive, roleName=$roleName)"

}