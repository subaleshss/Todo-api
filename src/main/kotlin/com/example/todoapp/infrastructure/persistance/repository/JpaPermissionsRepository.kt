package com.example.todoapp.infrastructure.persistance.repository

import com.example.todoapp.infrastructure.persistance.entity.PermissionEntity
import org.springframework.data.jpa.repository.JpaRepository

interface JpaPermissionsRepository: JpaRepository<PermissionEntity, Long> {
    fun findByName(name: String): PermissionEntity?
}