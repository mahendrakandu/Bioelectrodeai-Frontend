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

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // Apply saved theme BEFORE super.onCreate()
        ThemeManager.applyTheme(this)
        
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val etFullName = findViewById<TextInputEditText>(R.id.etFullName)
        val etEmail = findViewById<TextInputEditText>(R.id.etEmail)
        val etPassword = findViewById<TextInputEditText>(R.id.etPassword)
        val etConfirmPassword = findViewById<TextInputEditText>(R.id.etConfirmPassword)
        val spRole = findViewById<AutoCompleteTextView>(R.id.spRole)
        val cbTerms = findViewById<CheckBox>(R.id.cbTerms)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val tvLogin = findViewById<TextView>(R.id.tvLogin)

        // Setup Role Dropdown
        val roles = arrayOf(
            "Biomedical Engineering Student",
            "Medical Student",
            "Neuroscience Researcher",
            "Neurology Resident",
            "Clinical Engineer",
            "Healthcare Professional",
            "Others"
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, roles)
        spRole.setAdapter(adapter)

        // Back button click
        btnBack.setOnClickListener {
            finish()
        }

        btnRegister.setOnClickListener {
            val fullname = etFullName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val role = spRole.text.toString().trim()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()

            // Validation
            if (fullname.isEmpty()) {
                etFullName.error = "Full name is required"
                return@setOnClickListener
            }

            if (email.isEmpty()) {
                etEmail.error = "Email is required"
                return@setOnClickListener
            }

            if (role.isEmpty()) {
                spRole.error = "Please select a role"
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

            if (password != confirmPassword) {
                etConfirmPassword.error = "Passwords do not match"
                return@setOnClickListener
            }

            if (!cbTerms.isChecked) {
                Toast.makeText(this, "Please agree to Terms and Privacy Policy", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Disable button and show loading
            btnRegister.isEnabled = false
            btnRegister.text = "Creating account..."

            val request = RegisterRequest(fullname, email, role, password, confirmPassword)

            Log.d("RegisterActivity", "Sending registration request for: $email")

            ApiClient.apiService.registerUser(request)
                .enqueue(object : Callback<RegisterResponse> {
                    override fun onResponse(
                        call: Call<RegisterResponse>,
                        response: Response<RegisterResponse>
                    ) {
                        btnRegister.isEnabled = true
                        btnRegister.text = "Create Account"

                        Log.d("RegisterActivity", "Response code: ${response.code()}")
                        Log.d("RegisterActivity", "Response body: ${response.body()}")

                        if (response.isSuccessful) {
                            val registerResponse = response.body()
                            Log.d("RegisterActivity", "Status: ${registerResponse?.status}, Message: ${registerResponse?.message}")

                            if (registerResponse?.status == "success") {
                                Toast.makeText(
                                    this@RegisterActivity,
                                    "Registration successful! Please login.",
                                    Toast.LENGTH_LONG
                                ).show()
                                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                                finish()
                            } else {
                                // Show specific error message from server
                                val errorMsg = registerResponse?.message ?: "Registration failed - Unknown error"
                                Log.e("RegisterActivity", "Server error: $errorMsg")
                                Toast.makeText(
                                    this@RegisterActivity,
                                    errorMsg,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        } else {
                            // Get error details from response
                            val errorBody = response.errorBody()?.string()
                            Log.e("RegisterActivity", "HTTP ${response.code()} - Error body: $errorBody")
                            
                            val errorMessage = when (response.code()) {
                                400 -> "Bad Request - Please check your input"
                                404 -> "Server endpoint not found - Check XAMPP and DevTunnel"
                                500 -> "Server error - Check database and PHP logs"
                                else -> "Registration failed (HTTP ${response.code()})"
                            }
                            
                            Toast.makeText(
                                this@RegisterActivity,
                                "$errorMessage\nDetails: ${errorBody ?: "No details"}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                        btnRegister.isEnabled = true
                        btnRegister.text = "Create Account"
                        
                        Log.e("RegisterActivity", "Network error: ${t.javaClass.simpleName} - ${t.message}", t)
                        
                        val errorMessage = when {
                            t.message?.contains("Unable to resolve host") == true -> 
                                "Cannot connect to server\n• Check your internet connection\n• Verify DevTunnel is running"
                            t.message?.contains("timeout") == true -> 
                                "Connection timeout\n• Server is not responding\n• Check XAMPP is running"
                            t.message?.contains("Connection refused") == true -> 
                                "Connection refused\n• XAMPP Apache might not be running\n• Check port 80 is available"
                            else -> "Network Error: ${t.message}"
                        }
                        
                        Toast.makeText(
                            this@RegisterActivity,
                            errorMessage,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
        }

        tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
