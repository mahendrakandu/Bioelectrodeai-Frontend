package com.simats.learning

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class EditProfileActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager

    private lateinit var etFullName: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etRole: TextInputEditText
    private lateinit var etBio: TextInputEditText
    private lateinit var etNewPassword: TextInputEditText
    private lateinit var etConfirmPassword: TextInputEditText
    private lateinit var cbChangePassword: CheckBox
    private lateinit var layoutPasswordFields: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        sessionManager = SessionManager(this)

        initViews()
        loadCurrentProfile()
        setupListeners()
    }

    private fun initViews() {
        etFullName = findViewById(R.id.etFullName)
        etEmail = findViewById(R.id.etEmail)
        etRole = findViewById(R.id.etRole)
        etBio = findViewById(R.id.etBio)
        etNewPassword = findViewById(R.id.etNewPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        cbChangePassword = findViewById(R.id.cbChangePassword)
        layoutPasswordFields = findViewById(R.id.layoutPasswordFields)
    }

    private fun loadCurrentProfile() {
        // Load saved profile data
        etFullName.setText(sessionManager.getUserName() ?: "")
        etEmail.setText(sessionManager.getUserEmail() ?: "")
        etRole.setText(sessionManager.getUserRole() ?: "")
        
        // Load bio from SharedPreferences
        val prefs = getSharedPreferences("profile_prefs", MODE_PRIVATE)
        etBio.setText(prefs.getString("bio", ""))
    }

    private fun setupListeners() {
        // Back button
        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            finish()
        }

        // Change photo button
        findViewById<Button>(R.id.btnChangePhoto).setOnClickListener {
            Toast.makeText(this, "Photo change coming soon!", Toast.LENGTH_SHORT).show()
        }

        // Change password checkbox
        cbChangePassword.setOnCheckedChangeListener { _, isChecked ->
            layoutPasswordFields.visibility = if (isChecked) View.VISIBLE else View.GONE
            if (!isChecked) {
                etNewPassword.text?.clear()
                etConfirmPassword.text?.clear()
            }
        }

        // Also allow clicking the card to toggle
        findViewById<LinearLayout>(R.id.cardChangePassword).setOnClickListener {
            cbChangePassword.isChecked = !cbChangePassword.isChecked
        }

        // Save changes button
        findViewById<Button>(R.id.btnSaveChanges).setOnClickListener {
            saveChanges()
        }

        // Cancel button
        findViewById<Button>(R.id.btnCancel).setOnClickListener {
            finish()
        }
    }

    private fun saveChanges() {
        val fullName = etFullName.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val role = etRole.text.toString().trim()
        val bio = etBio.text.toString().trim()

        // Validation
        if (fullName.isEmpty()) {
            etFullName.error = "Name is required"
            etFullName.requestFocus()
            return
        }

        if (email.isEmpty()) {
            etEmail.error = "Email is required"
            etEmail.requestFocus()
            return
        }

        // Password validation if changing
        if (cbChangePassword.isChecked) {
            val newPassword = etNewPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()

            if (newPassword.isEmpty()) {
                etNewPassword.error = "Enter new password"
                etNewPassword.requestFocus()
                return
            }

            if (newPassword.length < 6) {
                etNewPassword.error = "Password must be at least 6 characters"
                etNewPassword.requestFocus()
                return
            }

            if (newPassword != confirmPassword) {
                etConfirmPassword.error = "Passwords do not match"
                etConfirmPassword.requestFocus()
                return
            }
        }

        // Save to SharedPreferences
        sessionManager.saveUserDetails(
            fullName,
            email,
            sessionManager.getUserId() ?: 0,
            role
        )

        // Save bio separately
        val prefs = getSharedPreferences("profile_prefs", MODE_PRIVATE)
        prefs.edit().putString("bio", bio).apply()

        Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show()
        
        // Return to previous screen
        setResult(RESULT_OK)
        finish()
    }
}
