package com.simats.learning

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("register.php")
    fun registerUser(
        @Body request: RegisterRequest
    ): Call<RegisterResponse>

    @POST("login.php")
    fun loginUser(
        @Body request: LoginRequest
    ): Call<LoginResponse>

    @POST("verify_email.php")
    fun verifyEmail(
        @Body request: VerifyEmailRequest
    ): Call<VerifyEmailResponse>

    @POST("reset_password.php")
    fun resetPassword(
        @Body request: ResetPasswordRequest
    ): Call<ResetPasswordResponse>

    @POST("forgot_password.php")
    fun forgotPassword(
        @Body request: ForgotPasswordRequest
    ): Call<ForgotPasswordResponse>
}
