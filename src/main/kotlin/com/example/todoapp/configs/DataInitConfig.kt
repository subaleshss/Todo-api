package com.example.todoapp.configs

import com.example.todoapp.entity.Permission
import com.example.todoapp.entity.Role
import com.example.todoapp.repository.PermissionsRepository
import com.example.todoapp.repository.RolesRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DataInitConfig(
    private val rolesRepository: RolesRepository,
    private val permissionsRepository: PermissionsRepository
) {

    @Bean
    fun initRolesAndPermissions() = CommandLineRunner {

        val create = permissionsRepository.save(Permission(null, "CREATE_TODO"))
        val update = permissionsRepository.save(Permission(null, "UPDATE_TODO"))
        val delete = permissionsRepository.save(Permission(null, "DELETE_TODO"))
        val viewAllTodo = permissionsRepository.save(Permission(null, "VIEW_ALL_TODO"))
        val viewAllUsers = permissionsRepository.save(Permission(null, "VIEW_ALL_USERS"))
        val modifyUsers = permissionsRepository.save(Permission(null, "MODIFY_USERS"))
        val deleteUser = permissionsRepository.save(Permission(null, "DELETE_USERS"))

        val adminRole = Role(
            name = "ADMIN",
            permissions = mutableSetOf(
                create,
                update,
                delete,
                viewAllTodo,
                viewAllUsers,
                modifyUsers,
                deleteUser
            )
        )

        val userRole = Role(
            name = "USER",
            permissions = mutableSetOf(
                create,
                update,
                delete,
                viewAllTodo
            )
        )

        rolesRepository.saveAll(listOf(adminRole, userRole))
    }
}