package com.example.todoapp.infrastructure.configs

    import com.example.todoapp.infrastructure.persistance.entity.PermissionEntity
    import com.example.todoapp.infrastructure.persistance.entity.RoleEntity
    import com.example.todoapp.infrastructure.persistance.repository.JpaPermissionsRepository
    import com.example.todoapp.infrastructure.persistance.repository.JpaRolesRepository
    import org.springframework.boot.CommandLineRunner
    import org.springframework.context.annotation.Bean
    import org.springframework.context.annotation.Configuration

    @Configuration
    class DataInitConfig(
        private val rolesRepository: JpaRolesRepository,
        private val permissionsRepository: JpaPermissionsRepository
    ) {

        @Bean
        fun initRolesAndPermissions() = CommandLineRunner {

            val create = permissionsRepository.save(PermissionEntity(null, "CREATE_TODO"))
            val update = permissionsRepository.save(PermissionEntity(null, "UPDATE_TODO"))
            val delete = permissionsRepository.save(PermissionEntity(null, "DELETE_TODO"))
            val viewAllTodo = permissionsRepository.save(PermissionEntity(null, "VIEW_ALL_TODO"))
            val viewAllUsers = permissionsRepository.save(PermissionEntity(null, "VIEW_ALL_USERS"))
            val modifyUsers = permissionsRepository.save(PermissionEntity(null, "MODIFY_USERS"))
            val deleteUser = permissionsRepository.save(PermissionEntity(null, "DELETE_USERS"))

            val adminRoleEntity = RoleEntity(
                name = "ADMIN",
                permissionEntities = mutableSetOf(
                    create,
                    update,
                    delete,
                    viewAllTodo,
                    viewAllUsers,
                    modifyUsers,
                    deleteUser
                )
            )

            val userRoleEntity = RoleEntity(
                name = "USER",
                permissionEntities = mutableSetOf(
                    create,
                    update,
                    delete,
                    viewAllTodo
                )
            )

            rolesRepository.saveAll(listOf(adminRoleEntity, userRoleEntity))
        }
    }