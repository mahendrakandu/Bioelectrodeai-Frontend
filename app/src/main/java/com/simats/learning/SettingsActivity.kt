package com.simats.learning

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        // Apply saved theme BEFORE super.onCreate()
        ThemeManager.applyTheme(this)
        
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        sessionManager = SessionManager(this)

        // Back button
        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            finish()
        }

        // Edit Profile
        findViewById<LinearLayout>(R.id.llEditProfile).setOnClickListener {
            startActivity(Intent(this, EditProfileActivity::class.java))
        }

        // Switches
        val switchNotifications = findViewById<SwitchMaterial>(R.id.switchNotifications)
        val switchDarkMode = findViewById<SwitchMaterial>(R.id.switchDarkMode)
        val switchSound = findViewById<SwitchMaterial>(R.id.switchSound)

        // Helper function to update switch color
        fun updateSwitchColor(switch: SwitchMaterial, isChecked: Boolean, onColor: Int) {
            if (isChecked) {
                switch.trackTintList = android.content.res.ColorStateList.valueOf(onColor)
            } else {
                switch.trackTintList = android.content.res.ColorStateList.valueOf(0xFFBDBDBD.toInt())
            }
        }

        // Set initial colors
        updateSwitchColor(switchNotifications, switchNotifications.isChecked, 0xFF1565C0.toInt())
        updateSwitchColor(switchDarkMode, switchDarkMode.isChecked, 0xFF1565C0.toInt())
        updateSwitchColor(switchSound, switchSound.isChecked, 0xFFFF9800.toInt())

        // Notifications toggle
        switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            updateSwitchColor(switchNotifications, isChecked, 0xFF1565C0.toInt())
            // TODO: Save notification preference
        }

        // Dark Mode toggle
        switchDarkMode.isChecked = ThemeManager.isDarkModeEnabled(this)
        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            updateSwitchColor(switchDarkMode, isChecked, 0xFF1565C0.toInt())
            // Save and apply theme
            val themeMode = if (isChecked) ThemeManager.MODE_DARK else ThemeManager.MODE_LIGHT
            ThemeManager.saveThemeMode(this, themeMode)
            ThemeManager.applyThemeMode(themeMode)
            // Recreate activity to apply theme immediately
            recreate()
        }

        // Sound Effects toggle
        switchSound.setOnCheckedChangeListener { _, isChecked ->
            updateSwitchColor(switchSound, isChecked, 0xFFFF9800.toInt())
            // TODO: Save sound preference
        }

        // Language
        findViewById<LinearLayout>(R.id.llLanguage).setOnClickListener {
            startActivity(Intent(this, LanguageActivity::class.java))
        }

        // Download Content
        findViewById<LinearLayout>(R.id.llDownloadContent).setOnClickListener {
            startActivity(Intent(this, DownloadContentActivity::class.java))
        }

        // Storage Usage
        findViewById<LinearLayout>(R.id.llStorageUsage).setOnClickListener {
            startActivity(Intent(this, StorageUsageActivity::class.java))
        }

        // App Map
        findViewById<LinearLayout>(R.id.llAppMap).setOnClickListener {
            startActivity(Intent(this, AppMapActivity::class.java))
        }

        // Privacy Policy
        findViewById<LinearLayout>(R.id.llPrivacyPolicy).setOnClickListener {
            startActivity(Intent(this, PrivacyPolicyActivity::class.java))
        }

        // Terms & Conditions
        findViewById<LinearLayout>(R.id.llTermsConditions).setOnClickListener {
            startActivity(Intent(this, TermsConditionsActivity::class.java))
        }

        // Help & Support
        findViewById<LinearLayout>(R.id.llHelpSupport).setOnClickListener {
            startActivity(Intent(this, HelpSupportActivity::class.java))
        }

        // Switch Account
        findViewById<Button>(R.id.btnSwitchAccount).setOnClickListener {
            sessionManager.logout()
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        }

        // Sign Out & Switch Account
        findViewById<Button>(R.id.btnSignOut).setOnClickListener {
            sessionManager.logout()
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        }
    }
}
