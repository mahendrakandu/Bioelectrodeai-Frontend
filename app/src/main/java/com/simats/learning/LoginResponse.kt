package com.simats.learning

data class LoginResponse(
    val status: String,
    val message: String,
    val user: User?
)

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val role: String
)
