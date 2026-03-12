package com.example.todoapp.repository

import com.example.todoapp.entity.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RolesRepository: JpaRepository<Role, Long> {
    fun findByName(name: String): Role?
}