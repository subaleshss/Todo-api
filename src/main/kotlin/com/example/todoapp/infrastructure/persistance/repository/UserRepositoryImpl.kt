package com.example.todoapp.infrastructure.persistance.repository

import com.example.todoapp.domain.model.User
import com.example.todoapp.domain.model.UserRole
import com.example.todoapp.domain.repository.UserRepository
import com.example.todoapp.infrastructure.persistance.entity.RoleEntity
import com.example.todoapp.infrastructure.persistance.entity.UserEntity
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class UserRepositoryImpl(
    private val jpaUserRepository: JpaUserRepository,
    private val jpaRolesRepository: JpaRolesRepository
) : UserRepository {
    override fun findByEmail(email: String): User? {
        return jpaUserRepository.findByEmail(email)?.toUser()
    }

    override fun existsByEmail(email: String): Boolean {
        return jpaUserRepository.existsByEmail(email)
    }

    override fun save(user: User): User {
        val userRole = jpaRolesRepository.findByName(user.roleName.name)
            ?: throw IllegalStateException("Role not found: ${user.roleName}")

        val  entity = user.toEntity(userRole)

        return jpaUserRepository.save(entity).toUser()
    }

    fun UserEntity.toUser() = User(
        id = this.userId,
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        password = this.password ?: "",
        isActive = this.isActive,
        roleName = UserRole.valueOf(this.roleEntity.name)
    )
    
    fun User.toEntity(roleEntity: RoleEntity) = UserEntity(
        userId = this.id,
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        password = this.extractPassword(),
        isActive = this.isActive,
        createdAt = LocalDateTime.now(),
        updatedAt = this.updatedAt ?: LocalDateTime.now(),
        roleEntity = roleEntity
    )
}