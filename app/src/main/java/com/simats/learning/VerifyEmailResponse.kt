package com.simats.learning

data class VerifyEmailResponse(
    val status: String,
    val message: String,
    val exists: Boolean,
    val user_name: String? = null
)
