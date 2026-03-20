package com.example.todoapp.common

import org.slf4j.LoggerFactory

class ApiLogger(clazz: Class<*>) {
    private val logger = LoggerFactory.getLogger(clazz)

    fun info(message: String) = logger.info(message)
    fun info(message: String, vararg args: Any?) = logger.info(message, *args)
    fun error(message: String) = logger.error(message)
    fun error(message: String, throwable: Throwable) = logger.error(message, throwable)
    fun warn(message: String) = logger.warn(message)
    fun warn(message: String, throwable: Throwable) = logger.warn(message, throwable)
    fun debug(message: String) = logger.debug(message)
    fun debug(message: String, vararg args: Any?) = logger.debug(message, *args)

    companion object {
        fun of(clazz: Class<*>) = ApiLogger(clazz)
    }
}