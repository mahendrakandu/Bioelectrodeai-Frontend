package com.simats.learning

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        // Apply saved theme BEFORE super.onCreate()
        ThemeManager.applyTheme(this)
        
        super.onCreate(savedInstanceState)

        sessionManager = SessionManager(this)

        // If already logged in, go to MainActivity
        if (sessionManager.isLoggedIn()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        setContentView(R.layout.activity_login)

        val etEmail = findViewById<TextInputEditText>(R.id.etEmail)
        val etPassword = findViewById<TextInputEditText>(R.id.etPassword)
        val cbRemember = findViewById<CheckBox>(R.id.cbRemember)
        val tvForgotPassword = findViewById<TextView>(R.id.tvForgotPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvCreateAccount = findViewById<TextView>(R.id.tvCreateAccount)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString()

            // Validation
            if (email.isEmpty()) {
                etEmail.error = "Email is required"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                etPassword.error = "Password is required"
                return@setOnClickListener
            }

            if (password.length < 8) {
                etPassword.error = "Password must be at least 8 characters"
                return@setOnClickListener
            }

            // Disable button and show loading
            btnLogin.isEnabled = false
            btnLogin.text = "Signing in..."

            val request = LoginRequest(email, password)

            Log.d("LoginActivity", "Sending login request for: $email")

            ApiClient.apiService.loginUser(request)
                .enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        btnLogin.isEnabled = true
                        btnLogin.text = "Sign In"

                        Log.d("LoginActivity", "Response code: ${response.code()}")
                        Log.d("LoginActivity", "Response body: ${response.body()}")

                        if (response.isSuccessful) {
                            val loginResponse = response.body()
                            Log.d("LoginActivity", "Status: ${loginResponse?.status}")

                            if (loginResponse?.status == "success" && loginResponse.user != null) {
                                // Save user session
                                sessionManager.saveUser(loginResponse.user)

                                // Clear topic progress for fresh login
                                TopicProgressManager(this@LoginActivity).clearAllProgress()

                                Toast.makeText(
                                    this@LoginActivity,
                                    "Welcome back, ${loginResponse.user.name}!",
                                    Toast.LENGTH_SHORT
                                ).show()

                                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                                finish()
                            } else {
                                Toast.makeText(
                                    this@LoginActivity,
                                    loginResponse?.message ?: "Login failed",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Log.e("LoginActivity", "Response not successful: ${response.code()}")
                            Toast.makeText(
                                this@LoginActivity,
                                "Login failed: ${response.code()}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        btnLogin.isEnabled = true
                        btnLogin.text = "Sign In"
                        Log.e("LoginActivity", "Network error: ${t.message}", t)
                        Toast.makeText(
                            this@LoginActivity,
                            "Network Error: ${t.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
        }

        // Sign Up link
        val tvSignUp = findViewById<TextView>(R.id.tvSignUp)
        tvSignUp.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        tvCreateAccount.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        tvForgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }
    }
}
