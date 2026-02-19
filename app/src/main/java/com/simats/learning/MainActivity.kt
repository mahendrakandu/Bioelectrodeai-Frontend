package com.simats.learning

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        // Apply saved theme BEFORE super.onCreate()
        ThemeManager.applyTheme(this)
        
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sessionManager = SessionManager(this)

        // Set user name
        val tvUserName = findViewById<TextView>(R.id.tvUserName)
        val userName = sessionManager.getUserName()
        tvUserName.text = userName ?: "User"

        // Explore Topics click listeners
        findViewById<LinearLayout>(R.id.cardLearn).setOnClickListener {
            startActivity(Intent(this, LearningTopicsActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.cardCompare).setOnClickListener {
        startActivity(Intent(this, ComparisonActivity::class.java))
    }

        findViewById<LinearLayout>(R.id.cardSimulator).setOnClickListener {
            Toast.makeText(this, "Simulator - Coming soon!", Toast.LENGTH_SHORT).show()
        }

        findViewById<LinearLayout>(R.id.cardAI).setOnClickListener {
            Toast.makeText(this, "AI Analysis - Coming soon!", Toast.LENGTH_SHORT).show()
        }

        findViewById<LinearLayout>(R.id.cardVisualizations).setOnClickListener {
            Toast.makeText(this, "Visualizations - Coming soon!", Toast.LENGTH_SHORT).show()
        }

        // Quick Actions click listeners
        findViewById<LinearLayout>(R.id.btnComparisonReport).setOnClickListener {
            startActivity(Intent(this, ComparisonReportActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.btnQuiz).setOnClickListener {
            startActivity(Intent(this, QuizActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.btnClinical).setOnClickListener {
            startActivity(Intent(this, ClinicalCasesActivity::class.java))
        }


        findViewById<LinearLayout>(R.id.btnResources).setOnClickListener {
            startActivity(Intent(this, ResourcesLibraryActivity::class.java))
        }


        // Bottom Navigation click listeners
        findViewById<LinearLayout>(R.id.navHome).setOnClickListener {
            // Already on home
        }

        findViewById<LinearLayout>(R.id.navGlossary).setOnClickListener {
            startActivity(Intent(this, GlossaryActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.navSettings).setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        // Profile click
        findViewById<android.widget.ImageView>(R.id.ivProfile).setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}