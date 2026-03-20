package com.example.todoapp.infrastructure.persistance.repository

import com.example.todoapp.infrastructure.persistance.entity.RoleEntity
import org.springframework.data.jpa.repository.JpaRepository

interface JpaRolesRepository: JpaRepository<RoleEntity, Long> {
    fun findByName(name: String): RoleEntity?
}