package com.simats.learning

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CaseDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_case_detail)

        // Get case data from intent
        val caseTitle = intent.getStringExtra("CASE_TITLE") ?: ""
        val caseNumber = intent.getStringExtra("CASE_NUMBER") ?: ""
        val caseType = intent.getStringExtra("CASE_TYPE") ?: ""
        val specialty = intent.getStringExtra("CASE_SPECIALTY") ?: ""
        val difficulty = intent.getStringExtra("CASE_DIFFICULTY") ?: ""
        val patient = intent.getStringExtra("CASE_PATIENT") ?: ""
        val challenge = intent.getStringExtra("CASE_CHALLENGE") ?: ""
        val why = intent.getStringExtra("CASE_WHY") ?: ""
        val outcome = intent.getStringExtra("CASE_OUTCOME") ?: ""
        val learning = intent.getStringExtra("CASE_LEARNING") ?: ""

        // Back button
        findViewById<ImageView>(R.id.ivBack).setOnClickListener {
            finish()
        }

        // Populate data
        findViewById<TextView>(R.id.tvCaseTitle).text = caseTitle
        findViewById<TextView>(R.id.tvCaseNumber).text = caseNumber
        findViewById<TextView>(R.id.tvType).text = caseType
        findViewById<TextView>(R.id.tvSpecialty).text = specialty
        findViewById<TextView>(R.id.tvDifficulty).text = difficulty
        findViewById<TextView>(R.id.tvPatientProfile).text = patient
        findViewById<TextView>(R.id.tvChallenge).text = challenge
        findViewById<TextView>(R.id.tvWhyRecorded).text = why
        findViewById<TextView>(R.id.tvOutcome).text = outcome
        findViewById<TextView>(R.id.tvKeyLearning).text = learning

        // Set type badge color
        val typeBadge = findViewById<TextView>(R.id.tvType)
        when (caseType) {
            "Bipolar" -> typeBadge.setBackgroundResource(R.drawable.badge_bipolar)
            "Monopolar" -> typeBadge.setBackgroundResource(R.drawable.badge_monopolar)
        }

        // Set difficulty color
        val difficultyView = findViewById<TextView>(R.id.tvDifficulty)
        val difficultyColor = when (difficulty) {
            "Critical" -> "#E53935"
            "Advanced" -> "#FB8C00"
            "Intermediate" -> "#1976D2"
            else -> "#388E3C"
        }
        difficultyView.setTextColor(android.graphics.Color.parseColor(difficultyColor))

        // Update "Why X Recorded?" title
        findViewById<TextView>(R.id.tvWhyTitle).text = "Why $caseType Recorded?"
    }
}
