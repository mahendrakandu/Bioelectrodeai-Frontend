package com.simats.learning
data class RegisterRequest(
    val fullname: String,
    val email: String,
    val role: String,
    val password: String,
    val confirm_password: String
)
