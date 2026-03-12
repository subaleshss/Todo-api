package com.example.todoapp.repository

import com.example.todoapp.entity.Permission
import org.springframework.data.jpa.repository.JpaRepository

interface PermissionsRepository: JpaRepository<Permission, Long> {
    fun findByName(name: String): Permission?
}