package com.simats.learning

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ApplicationsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ELECTRODE_TYPE = "electrode_type"
    }

    private lateinit var progressManager: TopicProgressManager
    private var electrodeType: String = TopicDetailActivity.TYPE_BIPOLAR

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_applications)

        progressManager = TopicProgressManager(this)
        electrodeType = intent.getStringExtra(EXTRA_ELECTRODE_TYPE) ?: TopicDetailActivity.TYPE_BIPOLAR

        setContentView(R.layout.activity_topic_applications)

        // Update subtitle based on electrode type
        val subtitleText = if (electrodeType == TopicDetailActivity.TYPE_BIPOLAR) {
            "Clinical Uses of Bipolar Recording"
        } else {
            "Clinical Uses of Monopolar Recording"
        }
        findViewById<TextView>(R.id.tvTopicSubtitle).text = subtitleText

        // Back button
        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            // Mark Applications as completed when leaving
            progressManager.markTopicCompleted(electrodeType, 4) // Index 4 = Applications
            finish()
        }

        // Continue to Monopolar button (only show for bipolar)
        val btnContinueMonopolar = findViewById<LinearLayout>(R.id.btnContinueMonopolar)
        val tvContinueMonopolarText = findViewById<TextView>(R.id.tvContinueMonopolarText)
        
        // Back to Topics button
        val btnContinue = findViewById<LinearLayout>(R.id.btnContinue)
        val tvContinueText = findViewById<TextView>(R.id.tvContinueText)
        
        // Show/hide buttons based on electrode type
        if (electrodeType == TopicDetailActivity.TYPE_BIPOLAR) {
            // For bipolar, show "Continue to Monopolar" button
            btnContinueMonopolar.visibility = android.view.View.VISIBLE
            btnContinueMonopolar.setOnClickListener {
                // Mark Bipolar Applications as completed
                progressManager.markTopicCompleted(electrodeType, 4) // Index 4 = Applications
                
                // Navigate to Monopolar Introduction
                val intent = Intent(this, TopicDetailActivity::class.java).apply {
                    putExtra(TopicDetailActivity.EXTRA_ELECTRODE_TYPE, TopicDetailActivity.TYPE_MONOPOLAR)
                    putExtra(TopicDetailActivity.EXTRA_TOPIC_INDEX, 0) // Introduction
                }
                startActivity(intent)
                finish()
            }
            
            // Set "Back to Topics" text
            tvContinueText.text = "Back to Topics"
        } else {
            // For monopolar, hide "Continue to Monopolar" button and show "Compare Both Techniques"
            btnContinueMonopolar.visibility = android.view.View.GONE
            tvContinueText.text = "Compare Both Techniques"
        }
        
        // Back to Topics / Compare Both Techniques button
        btnContinue.setOnClickListener {
            // Mark Applications as completed
            progressManager.markTopicCompleted(electrodeType, 4) // Index 4 = Applications
            
            if (electrodeType == TopicDetailActivity.TYPE_MONOPOLAR) {
                // For monopolar, navigate to Comparison screen
                val intent = Intent(this, ComparisonActivity::class.java)
                startActivity(intent)
            } else {
                // For bipolar, go back to Learning Topics
                val intent = Intent(this, LearningTopicsActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onBackPressed() {
        // Mark as completed when back is pressed
        progressManager.markTopicCompleted(electrodeType, 4)
        super.onBackPressed()
    }
}
