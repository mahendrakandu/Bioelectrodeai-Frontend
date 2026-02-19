package com.simats.learning

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var emailSection: LinearLayout
    private lateinit var passwordSection: LinearLayout
    private lateinit var etEmail: TextInputEditText
    private lateinit var etNewPassword: TextInputEditText
    private lateinit var etConfirmPassword: TextInputEditText
    private lateinit var btnVerifyEmail: Button
    private lateinit var btnResetPassword: Button
    private lateinit var tvTitle: TextView
    private lateinit var tvSubtitle: TextView

    private var verifiedEmail: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        // Apply saved theme BEFORE super.onCreate()
        ThemeManager.applyTheme(this)
        
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        // Initialize views
        emailSection = findViewById(R.id.emailSection)
        passwordSection = findViewById(R.id.passwordSection)
        etEmail = findViewById(R.id.etEmail)
        etNewPassword = findViewById(R.id.etNewPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        btnVerifyEmail = findViewById(R.id.btnVerifyEmail)
        btnResetPassword = findViewById(R.id.btnResetPassword)
        tvTitle = findViewById(R.id.tvTitle)
        tvSubtitle = findViewById(R.id.tvSubtitle)

        // Back button
        findViewById<LinearLayout>(R.id.btnBack).setOnClickListener {
            finish()
        }

        // Sign In link
        findViewById<TextView>(R.id.tvSignIn).setOnClickListener {
            finish()
        }

        // STEP 1: Verify Email
        btnVerifyEmail.setOnClickListener {
            val email = etEmail.text.toString().trim()

            if (email.isEmpty()) {
                etEmail.error = "Email is required"
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.error = "Please enter a valid email address"
                return@setOnClickListener
            }

            // Verify email exists in database
            verifyEmail(email)
        }

        // STEP 2: Reset Password
        btnResetPassword.setOnClickListener {
            val newPassword = etNewPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()

            if (newPassword.isEmpty()) {
                etNewPassword.error = "Password is required"
                return@setOnClickListener
            }

            if (newPassword.length < 8) {
                etNewPassword.error = "Password must be at least 8 characters"
                return@setOnClickListener
            }

            if (confirmPassword.isEmpty()) {
                etConfirmPassword.error = "Please confirm your password"
                return@setOnClickListener
            }

            if (newPassword != confirmPassword) {
                etConfirmPassword.error = "Passwords do not match"
                return@setOnClickListener
            }

            // Reset password
            resetPassword(verifiedEmail, newPassword)
        }
    }

    private fun verifyEmail(email: String) {
        btnVerifyEmail.isEnabled = false
        btnVerifyEmail.text = "Verifying..."

        val request = VerifyEmailRequest(email)

        ApiClient.apiService.verifyEmail(request).enqueue(object : Callback<VerifyEmailResponse> {
            override fun onResponse(
                call: Call<VerifyEmailResponse>,
                response: Response<VerifyEmailResponse>
            ) {
                btnVerifyEmail.isEnabled = true
                btnVerifyEmail.text = "Verify Email"

                if (response.isSuccessful && response.body() != null) {
                    val verifyResponse = response.body()!!

                    if (verifyResponse.status == "success" && verifyResponse.exists) {
                        // Email verified successfully
                        verifiedEmail = email
                        showPasswordResetSection()
                        Toast.makeText(
                            this@ForgotPasswordActivity,
                            "Email verified! Please enter your new password",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        // Email not found
                        Toast.makeText(
                            this@ForgotPasswordActivity,
                            verifyResponse.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@ForgotPasswordActivity,
                        "Failed to verify email. Error: ${response.code()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<VerifyEmailResponse>, t: Throwable) {
                btnVerifyEmail.isEnabled = true
                btnVerifyEmail.text = "Verify Email"
                Toast.makeText(
                    this@ForgotPasswordActivity,
                    "Network error: ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun resetPassword(email: String, newPassword: String) {
        btnResetPassword.isEnabled = false
        btnResetPassword.text = "Resetting..."

        val request = ResetPasswordRequest(email, newPassword)

        ApiClient.apiService.resetPassword(request).enqueue(object : Callback<ResetPasswordResponse> {
            override fun onResponse(
                call: Call<ResetPasswordResponse>,
                response: Response<ResetPasswordResponse>
            ) {
                btnResetPassword.isEnabled = true
                btnResetPassword.text = "Reset Password"

                if (response.isSuccessful && response.body() != null) {
                    val resetResponse = response.body()!!

                    if (resetResponse.status == "success") {
                        Toast.makeText(
                            this@ForgotPasswordActivity,
                            resetResponse.message,
                            Toast.LENGTH_LONG
                        ).show()

                        // Navigate back to login after delay
                        android.os.Handler(mainLooper).postDelayed({
                            finish()
                        }, 2000)
                    } else {
                        Toast.makeText(
                            this@ForgotPasswordActivity,
                            resetResponse.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@ForgotPasswordActivity,
                        "Failed to reset password. Error: ${response.code()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResetPasswordResponse>, t: Throwable) {
                btnResetPassword.isEnabled = true
                btnResetPassword.text = "Reset Password"
                Toast.makeText(
                    this@ForgotPasswordActivity,
                    "Network error: ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun showPasswordResetSection() {
        // Hide email section
        emailSection.visibility = View.GONE
        
        // Show password section
        passwordSection.visibility = View.VISIBLE
        
        // Update title and subtitle
        tvTitle.text = "Create New Password"
        tvSubtitle.text = "Enter your new password below"
    }
}
