package com.simats.learning

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HowItWorksActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ELECTRODE_TYPE = "electrode_type"
    }

    private lateinit var progressManager: TopicProgressManager
    private var electrodeType: String = TopicDetailActivity.TYPE_BIPOLAR

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_how_it_works)

        progressManager = TopicProgressManager(this)
        electrodeType = intent.getStringExtra(EXTRA_ELECTRODE_TYPE) ?: TopicDetailActivity.TYPE_BIPOLAR

        // Update subtitle based on electrode type
        val subtitleText = if (electrodeType == TopicDetailActivity.TYPE_BIPOLAR) {
            "Bipolar Recording Principle"
        } else {
            "Monopolar Recording Principle"
        }
        findViewById<TextView>(R.id.tvTopicSubtitle).text = subtitleText

        // Back button
        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            // Mark How It Works as completed when leaving
            progressManager.markTopicCompleted(electrodeType, 1) // Index 1 = How It Works
            finish()
        }

        // Continue button - goes to Advantages
        val btnContinue = findViewById<LinearLayout>(R.id.btnContinue)
        btnContinue.setOnClickListener {
            // Mark How It Works as completed
            progressManager.markTopicCompleted(electrodeType, 1) // Index 1 = How It Works
            
            // Navigate to Advantages
            val intent = Intent(this, AdvantagesActivity::class.java).apply {
                putExtra(AdvantagesActivity.EXTRA_ELECTRODE_TYPE, electrodeType)
            }
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
        // Mark as completed when back is pressed
        progressManager.markTopicCompleted(electrodeType, 1)
        super.onBackPressed()
    }
}
