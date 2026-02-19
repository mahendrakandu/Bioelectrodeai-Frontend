package com.simats.learning

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DisadvantagesActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ELECTRODE_TYPE = "electrode_type"
    }

    private lateinit var progressManager: TopicProgressManager
    private var electrodeType: String = TopicDetailActivity.TYPE_BIPOLAR

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_disadvantages)

        progressManager = TopicProgressManager(this)
        electrodeType = intent.getStringExtra(EXTRA_ELECTRODE_TYPE) ?: TopicDetailActivity.TYPE_BIPOLAR

        setContentView(R.layout.activity_topic_disadvantages)

        // Update subtitle based on electrode type
        val subtitleText = if (electrodeType == TopicDetailActivity.TYPE_BIPOLAR) {
            "Bipolar Recording Limitations"
        } else {
            "Monopolar Recording Limitations"
        }
        findViewById<TextView>(R.id.tvTopicSubtitle).text = subtitleText

        // Back button
        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            // Mark Disadvantages as completed when leaving
            progressManager.markTopicCompleted(electrodeType, 3) // Index 3 = Disadvantages
            finish()
        }

        // Continue button - goes to Applications
        val btnContinue = findViewById<LinearLayout>(R.id.btnContinue)
        val tvContinueText = findViewById<TextView>(R.id.tvContinueText)
        
        // Update button text
        tvContinueText.text = "Continue to Applications"
        
        btnContinue.setOnClickListener {
            // Mark Disadvantages as completed
            progressManager.markTopicCompleted(electrodeType, 3) // Index 3 = Disadvantages
            
            // Navigate to Applications
            val intent = Intent(this, ApplicationsActivity::class.java).apply {
                putExtra(ApplicationsActivity.EXTRA_ELECTRODE_TYPE, electrodeType)
            }
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
        // Mark as completed when back is pressed
        progressManager.markTopicCompleted(electrodeType, 3)
        super.onBackPressed()
    }
}
