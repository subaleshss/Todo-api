package com.example.todoapp.presentation.advice

import com.example.todoapp.application.service.EmailAlreadyExistsException
import com.example.todoapp.common.ApiLogger
import com.example.todoapp.common.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest

@RestControllerAdvice
class GlobalExceptionHandler {

    private val log = ApiLogger.of(javaClass)

    @ExceptionHandler(EmailAlreadyExistsException::class)
    fun handleEmailExistException(e: EmailAlreadyExistsException) = ResponseEntity.badRequest().body(
        ApiResponse<Nothing>(success = false, message = e.message)
    )

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(e: MethodArgumentNotValidException): ResponseEntity<ApiResponse<Nothing>> {
        val errors = e.bindingResult.fieldErrors.joinToString(",") {
            "${it.field} :  ${it.defaultMessage}"
        }
        return ResponseEntity.badRequest().body(
            ApiResponse(success = false, message = errors)
        )
    }

    @ExceptionHandler(IllegalStateException::class)
    fun handleIllegalState(e: IllegalStateException) : ResponseEntity<ApiResponse<Nothing>> {
        log.error("Unexpected exception", e)
        return ResponseEntity.internalServerError().body(
            ApiResponse<Nothing>(success = false, message = e.message)
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneric(e: Exception): ResponseEntity<ApiResponse<Nothing>> {
        log.error("Unexpected exception", e)
        return ResponseEntity.internalServerError().body(
            ApiResponse<Nothing>(success = false, message = "An unexpected error occurred")
        )
    }
}