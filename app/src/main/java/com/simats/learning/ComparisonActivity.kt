package com.simats.learning

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class ComparisonActivity : AppCompatActivity() {

    private lateinit var btnTabTechnical: LinearLayout
    private lateinit var btnTabClinical: LinearLayout
    private lateinit var btnTabPractical: LinearLayout
    
    private lateinit var tvTabTechnical: TextView
    private lateinit var tvTabClinical: TextView
    private lateinit var tvTabPractical: TextView

    private lateinit var contentTechnical: LinearLayout
    private lateinit var contentClinical: LinearLayout
    private lateinit var contentPractical: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        try {
            setContentView(R.layout.activity_comparison)
        } catch (e: Exception) {
            android.util.Log.e("ComparisonActivity", "Failed to inflate layout", e)
            // If layout fails, just finish
            finish()
            return
        }

        // Initialize tab buttons safely
        btnTabTechnical = findViewById(R.id.btnTabTechnical) ?: run {
            android.util.Log.e("ComparisonActivity", "btnTabTechnical not found")
            finish()
            return
        }
        btnTabClinical = findViewById(R.id.btnTabClinical) ?: run {
            android.util.Log.e("ComparisonActivity", "btnTabClinical not found") 
            finish()
            return
        }
        btnTabPractical = findViewById(R.id.btnTabPractical) ?: run {
            android.util.Log.e("ComparisonActivity", "btnTabPractical not found")
            finish()
            return
        }
        
        tvTabTechnical = findViewById(R.id.tvTabTechnical) ?: run {
            android.util.Log.e("ComparisonActivity", "tvTabTechnical not found")
            finish()
            return
        }
        tvTabClinical = findViewById(R.id.tvTabClinical) ?: run {
            android.util.Log.e("ComparisonActivity", "tvTabClinical not found")
            finish()
            return
        }
        tvTabPractical = findViewById(R.id.tvTabPractical) ?: run {
            android.util.Log.e("ComparisonActivity", "tvTabPractical not found")
            finish()
            return
        }

        // Initialize content containers
        contentTechnical = findViewById(R.id.contentTechnical) ?: run {
            android.util.Log.e("ComparisonActivity", "contentTechnical not found")
            finish()
            return
        }
        contentClinical = findViewById(R.id.contentClinical) ?: run {
            android.util.Log.e("ComparisonActivity", "contentClinical not found")
            finish()
            return
        }
        contentPractical = findViewById(R.id.contentPractical) ?: run {
            android.util.Log.e("ComparisonActivity", "contentPractical not found")
            finish()
            return
        }

        // Set default tab (Technical)
        selectTab(btnTabTechnical, tvTabTechnical)
        showContent("technical")

        // Back button
        findViewById<LinearLayout>(R.id.btnBack)?.setOnClickListener {
            finish()
        }

        // Tab click listeners
        btnTabTechnical.setOnClickListener {
            selectTab(btnTabTechnical, tvTabTechnical)
            deselectTab(btnTabClinical, tvTabClinical)
            deselectTab(btnTabPractical, tvTabPractical)
            showContent("technical")
        }

        btnTabClinical.setOnClickListener {
            selectTab(btnTabClinical, tvTabClinical)
            deselectTab(btnTabTechnical, tvTabTechnical)
            deselectTab(btnTabPractical, tvTabPractical)
            showContent("clinical")
        }

        btnTabPractical.setOnClickListener {
            selectTab(btnTabPractical, tvTabPractical)
            deselectTab(btnTabTechnical, tvTabTechnical)
            deselectTab(btnTabClinical, tvTabClinical)
            showContent("practical")
        }
        
        // Navigation button click handlers
        findViewById<LinearLayout>(R.id.btnDetailedPros)?.setOnClickListener {
            startActivity(android.content.Intent(this, ProsConsActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.btnDecisionGuide)?.setOnClickListener {
            startActivity(android.content.Intent(this, DecisionGuideActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.btnSignalQuality)?.setOnClickListener {
            startActivity(android.content.Intent(this, SignalQualityActivity::class.java))
        }

        android.util.Log.d("ComparisonActivity", "Activity initialized successfully")
    }

    private fun showContent(tabType: String) {
        when (tabType) {
            "technical" -> {
                contentTechnical.visibility = android.view.View.VISIBLE
                contentClinical.visibility = android.view.View.GONE
                contentPractical.visibility = android.view.View.GONE
            }
            "clinical" -> {
                contentTechnical.visibility = android.view.View.GONE
                contentClinical.visibility = android.view.View.VISIBLE
                contentPractical.visibility = android.view.View.GONE
            }
            "practical" -> {
                contentTechnical.visibility = android.view.View.GONE
                contentClinical.visibility = android.view.View.GONE
                contentPractical.visibility = android.view.View.VISIBLE
            }
        }
    }

    private fun selectTab(button: LinearLayout, textView: TextView) {
        button.setBackgroundResource(R.drawable.button_rounded_blue)
        textView.setTextColor(ContextCompat.getColor(this, android.R.color.white))
    }

    private fun deselectTab(button: LinearLayout, textView: TextView) {
        button.setBackgroundResource(R.drawable.tab_inactive_bg)
        textView.setTextColor(android.graphics.Color.parseColor("#666666"))
    }
}
