package com.simats.learning

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AdvantagesActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ELECTRODE_TYPE = "electrode_type"
    }

    private lateinit var progressManager: TopicProgressManager
    private var electrodeType: String = TopicDetailActivity.TYPE_BIPOLAR

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_advantages)

        progressManager = TopicProgressManager(this)
        electrodeType = intent.getStringExtra(EXTRA_ELECTRODE_TYPE) ?: TopicDetailActivity.TYPE_BIPOLAR

        // Update subtitle based on electrode type
        val subtitleText = if (electrodeType == TopicDetailActivity.TYPE_BIPOLAR) {
            "Bipolar Recording Benefits"
        } else {
            "Monopolar Recording Benefits"
        }
        findViewById<TextView>(R.id.tvTopicSubtitle).text = subtitleText

        // Back button
        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            // Mark Advantages as completed when leaving
            progressManager.markTopicCompleted(electrodeType, 2) // Index 2 = Advantages
            finish()
        }

        // Continue button - goes to Disadvantages
        val btnContinue = findViewById<LinearLayout>(R.id.btnContinue)
        btnContinue.setOnClickListener {
            // Mark Advantages as completed
            progressManager.markTopicCompleted(electrodeType, 2) // Index 2 = Advantages
            
            // Navigate to Disadvantages
            val intent = Intent(this, DisadvantagesActivity::class.java).apply {
                putExtra(DisadvantagesActivity.EXTRA_ELECTRODE_TYPE, electrodeType)
            }
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
        // Mark as completed when back is pressed
        progressManager.markTopicCompleted(electrodeType, 2)
        super.onBackPressed()
    }
}
