package com.example.todoapp.application.service

import com.example.todoapp.application.command.RegisterUserCommand
import com.example.todoapp.domain.model.User

interface UserCommandService {
    fun handle(command: RegisterUserCommand) : User
}