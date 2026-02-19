package com.simats.learning

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class MonopolarIntroActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ELECTRODE_TYPE = "electrode_type"
    }

    private lateinit var progressManager: TopicProgressManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monopolar_intro)

        progressManager = TopicProgressManager(this)

        // Back button
        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            markIntroCompleted()
            finish()
        }

        // Continue button - navigate to How It Works
        findViewById<LinearLayout>(R.id.btnContinue).setOnClickListener {
            markIntroCompleted()
            
            val intent = Intent(this, HowItWorksActivity::class.java).apply {
                putExtra(HowItWorksActivity.EXTRA_ELECTRODE_TYPE, TopicDetailActivity.TYPE_MONOPOLAR)
            }
            startActivity(intent)
            finish()
        }
    }

    private fun markIntroCompleted() {
        // Mark Monopolar Introduction (index 0) as completed
        progressManager.markTopicCompleted(TopicDetailActivity.TYPE_MONOPOLAR, 0)
    }

    override fun onBackPressed() {
        markIntroCompleted()
        super.onBackPressed()
    }
}
