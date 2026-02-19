package com.simats.learning

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TopicDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ELECTRODE_TYPE = "electrode_type"
        const val EXTRA_TOPIC_NAME = "topic_name"
        const val EXTRA_TOPIC_INDEX = "topic_index"
        
        const val TYPE_BIPOLAR = "bipolar"
        const val TYPE_MONOPOLAR = "monopolar"
        
        val TOPICS = listOf(
            "Introduction",
            "How It Works",
            "Advantages",
            "Disadvantages",
            "Applications"
        )
    }

    private lateinit var progressManager: TopicProgressManager
    private var electrodeType: String = TYPE_BIPOLAR
    private var topicIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_detail)

        progressManager = TopicProgressManager(this)

        // Get extras
        electrodeType = intent.getStringExtra(EXTRA_ELECTRODE_TYPE) ?: TYPE_BIPOLAR
        topicIndex = intent.getIntExtra(EXTRA_TOPIC_INDEX, 0)

        // Back button - mark as completed when leaving
        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            markCurrentTopicCompleted()
            finish()
        }

        // Setup title based on electrode type
        val titleText = if (electrodeType == TYPE_BIPOLAR) "Bipolar Electrodes" else "Monopolar Electrodes"
        findViewById<TextView>(R.id.tvTopicTitle).text = titleText
        findViewById<TextView>(R.id.tvTopicSubtitle).text = TOPICS[topicIndex]

        // Continue button
        val btnContinue = findViewById<LinearLayout>(R.id.btnContinue)
        val tvContinueText = findViewById<TextView>(R.id.tvContinueText)

        if (topicIndex < TOPICS.size - 1) {
            val nextTopic = TOPICS[topicIndex + 1]
            tvContinueText.text = "Continue to $nextTopic"
            
            btnContinue.setOnClickListener {
                // Mark Introduction as completed before navigating
                markCurrentTopicCompleted()
                
                // Navigate to How It Works activity
                if (topicIndex == 0) {
                    val intent = Intent(this, HowItWorksActivity::class.java).apply {
                        putExtra(HowItWorksActivity.EXTRA_ELECTRODE_TYPE, electrodeType)
                    }
                    startActivity(intent)
                    finish() // Close Introduction page
                }
            }
        } else {
            tvContinueText.text = "Complete Section"
            btnContinue.setOnClickListener {
                markCurrentTopicCompleted()
                finish()
            }
        }
    }

    private fun markCurrentTopicCompleted() {
        progressManager.markTopicCompleted(electrodeType, topicIndex)
    }

    override fun onBackPressed() {
        markCurrentTopicCompleted()
        super.onBackPressed()
    }
}
