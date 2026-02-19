package com.simats.learning

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DecisionGuideActivity : AppCompatActivity() {

    private var selectedEnvironment: String? = null
    private var selectedPriority: String? = null
    private var selectedBudget: String? = null
    private var selectedSkill: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            setContentView(R.layout.activity_decision_guide)
        } catch (e: Exception) {
            android.util.Log.e("DecisionGuideActivity", "Failed to inflate layout", e)
            finish()
            return
        }

        // Back button
        findViewById<LinearLayout>(R.id.btnBack)?.setOnClickListener { finish() }

        // Question 1: Environment
        val optionNoisy = findViewById<LinearLayout>(R.id.optionNoisy)
        val optionQuiet = findViewById<LinearLayout>(R.id.optionQuiet)
        optionNoisy.setOnClickListener {
            selectedEnvironment = "noisy"
            selectOption(optionNoisy, optionQuiet)
            checkAndShowRecommendation()
        }
        optionQuiet.setOnClickListener {
            selectedEnvironment = "quiet"
            selectOption(optionQuiet, optionNoisy)
            checkAndShowRecommendation()
        }

        // Question 2: Priority
        val optionPrecision = findViewById<LinearLayout>(R.id.optionPrecision)
        val optionSpeed = findViewById<LinearLayout>(R.id.optionSpeed)
        val optionCoverage = findViewById<LinearLayout>(R.id.optionCoverage)
        optionPrecision.setOnClickListener {
            selectedPriority = "precision"
            selectOption(optionPrecision, optionSpeed, optionCoverage)
            checkAndShowRecommendation()
        }
        optionSpeed.setOnClickListener {
            selectedPriority = "speed"
            selectOption(optionSpeed, optionPrecision, optionCoverage)
            checkAndShowRecommendation()
        }
        optionCoverage.setOnClickListener {
            selectedPriority = "coverage"
            selectOption(optionCoverage, optionPrecision, optionSpeed)
            checkAndShowRecommendation()
        }

        // Question 3: Budget
        val optionHighBudget = findViewById<LinearLayout>(R.id.optionHighBudget)
        val optionLimitedBudget = findViewById<LinearLayout>(R.id.optionLimitedBudget)
        optionHighBudget.setOnClickListener {
            selectedBudget = "high"
            selectOption(optionHighBudget, optionLimitedBudget)
            checkAndShowRecommendation()
        }
        optionLimitedBudget.setOnClickListener {
            selectedBudget = "limited"
            selectOption(optionLimitedBudget, optionHighBudget)
            checkAndShowRecommendation()
        }

        // Question 4: Skill Level
        val optionAdvanced = findViewById<LinearLayout>(R.id.optionAdvanced)
        val optionBasic = findViewById<LinearLayout>(R.id.optionBasic)
        optionAdvanced.setOnClickListener {
            selectedSkill = "advanced"
            selectOption(optionAdvanced, optionBasic)
            checkAndShowRecommendation()
        }
        optionBasic.setOnClickListener {
            selectedSkill = "basic"
            selectOption(optionBasic, optionAdvanced)
            checkAndShowRecommendation()
        }
    }

    private fun selectOption(selected: LinearLayout, vararg others: LinearLayout) {
        selected.setBackgroundResource(R.drawable.card_selected_option)
        others.forEach { it.setBackgroundResource(R.drawable.card_rounded) }
    }

    private fun checkAndShowRecommendation() {
        if (selectedEnvironment != null && selectedPriority != null &&
            selectedBudget != null && selectedSkill != null) {

            // Calculate bipolar score
            var bipolarScore = 0
            if (selectedEnvironment == "noisy") bipolarScore += 2
            if (selectedPriority == "precision") bipolarScore += 2
            if (selectedBudget == "high") bipolarScore += 1
            if (selectedSkill == "advanced") bipolarScore += 2

            val recommendation = if (bipolarScore >= 4) {
                "Based on your answers, we recommend Bipolar Recording.\n\n" +
                "Your clinical scenario favors bipolar electrodes because of the need for " +
                "precision, noise rejection, and your team's capability to handle the setup complexity. " +
                "Bipolar recording will give you 94% diagnostic accuracy with excellent artifact rejection."
            } else {
                "Based on your answers, we recommend Monopolar Recording.\n\n" +
                "Your clinical scenario favors monopolar electrodes for their simplicity, " +
                "broad coverage, and cost-effectiveness. Monopolar recording offers faster setup " +
                "and is ideal for routine screenings in controlled environments."
            }

            findViewById<LinearLayout>(R.id.infoCard).visibility = View.GONE
            findViewById<LinearLayout>(R.id.recommendationCard).visibility = View.VISIBLE
            findViewById<TextView>(R.id.tvRecommendation).text = recommendation
        }
    }
}
