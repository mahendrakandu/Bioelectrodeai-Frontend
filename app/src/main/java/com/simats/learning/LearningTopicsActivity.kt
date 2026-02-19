package com.simats.learning

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LearningTopicsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learning_topics)

        // Back button
        findViewById<LinearLayout>(R.id.btnBack).setOnClickListener {
            finish()
        }

        // ===== Biomedical Signals =====
        findViewById<LinearLayout>(R.id.cardECG).setOnClickListener {
            startActivity(Intent(this, EcgDetailActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.cardEEG).setOnClickListener {
            startActivity(Intent(this, EegDetailActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.cardEMG).setOnClickListener {
            startActivity(Intent(this, EmgDetailActivity::class.java))
        }

        // ===== Electrode Techniques =====
        findViewById<LinearLayout>(R.id.cardElectrodePlacement).setOnClickListener {
            startActivity(Intent(this, ElectrodePlacementActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.cardRecordingTechniques).setOnClickListener {
            startActivity(Intent(this, ComparisonActivity::class.java))
        }

        // ===== Core Learning Modules =====
        findViewById<LinearLayout>(R.id.cardBipolarRecording).setOnClickListener {
            val intent = Intent(this, TopicDetailActivity::class.java).apply {
                putExtra(TopicDetailActivity.EXTRA_ELECTRODE_TYPE, TopicDetailActivity.TYPE_BIPOLAR)
                putExtra(TopicDetailActivity.EXTRA_TOPIC_INDEX, 0)
            }
            startActivity(intent)
        }

        findViewById<LinearLayout>(R.id.cardMonopolarRecording).setOnClickListener {
            startActivity(Intent(this, MonopolarIntroActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.cardComparison).setOnClickListener {
            startActivity(Intent(this, ComparisonActivity::class.java))
        }

        // ===== Advanced Analysis =====
        findViewById<LinearLayout>(R.id.cardAIAnalysis).setOnClickListener {
            Toast.makeText(this, "AI Analysis System - Coming soon!", Toast.LENGTH_SHORT).show()
        }
    }
}
