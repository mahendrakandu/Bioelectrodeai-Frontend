package com.simats.learning

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        // Apply saved theme BEFORE super.onCreate()
        ThemeManager.applyTheme(this)
        
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        sessionManager = SessionManager(this)

        // Set user info
        val tvUserName = findViewById<TextView>(R.id.tvUserName)
        val tvUserRole = findViewById<TextView>(R.id.tvUserRole)
        
        tvUserName.text = sessionManager.getUserName() ?: "User"
        tvUserRole.text = sessionManager.getUserRole() ?: "Student"

        // Back button
        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            finish()
        }

        // Settings button
        findViewById<ImageView>(R.id.btnSettings).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        // Edit Profile button
        findViewById<Button>(R.id.btnEditProfile).setOnClickListener {
            startActivity(Intent(this, EditProfileActivity::class.java))
        }

        // Switch Account button (logout)
        findViewById<Button>(R.id.btnSwitchAccount).setOnClickListener {
            sessionManager.logout()
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        }
    }
}
