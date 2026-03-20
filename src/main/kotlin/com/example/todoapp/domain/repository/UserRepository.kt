package com.example.todoapp.domain.repository

import com.example.todoapp.domain.model.User

interface UserRepository {
    fun findByEmail(email: String): User?
    fun existsByEmail(email: String): Boolean
    fun save(user: User) : User
}