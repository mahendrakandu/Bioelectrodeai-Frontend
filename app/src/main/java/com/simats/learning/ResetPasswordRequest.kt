package com.simats.learning

data class ResetPasswordRequest(
    val email: String,
    val new_password: String
)
