package com.simats.learning

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class HelpSupportActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help_support)

        // Back button click listener
        findViewById<ImageView>(R.id.ivBack).setOnClickListener {
            finish()
        }

        // Support Categories Click Listeners
        findViewById<LinearLayout>(R.id.categoryGettingStarted).setOnClickListener {
            // Navigate to Onboarding/Tutorial
            startActivity(Intent(this, OnboardingActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.categoryAccount).setOnClickListener {
            // Navigate to Profile/Edit Profile
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.categoryLearning).setOnClickListener {
            // Navigate to Learning Topics
            startActivity(Intent(this, LearningTopicsActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.categoryTechnical).setOnClickListener {
            // Navigate to Settings for technical options
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        // Contact Options Click Listeners
        findViewById<LinearLayout>(R.id.contactEmail).setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:support@bioelectrode.app")
                putExtra(Intent.EXTRA_SUBJECT, "BioElectrode App Support Request")
            }
            try {
                startActivity(Intent.createChooser(emailIntent, "Send email via"))
            } catch (e: Exception) {
                Toast.makeText(this, "No email app found", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<LinearLayout>(R.id.contactWebsite).setOnClickListener {
            val websiteIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.bioelectrode.app"))
            try {
                startActivity(websiteIntent)
            } catch (e: Exception) {
                Toast.makeText(this, "No browser found", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
