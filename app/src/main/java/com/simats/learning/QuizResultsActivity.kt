package com.simats.learning

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class QuizResultsActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_results)

        sharedPreferences = getSharedPreferences("QuizProgress", MODE_PRIVATE)

        val score = intent.getIntExtra("SCORE", 0)
        val totalQuestions = intent.getIntExtra("TOTAL_QUESTIONS", 5)
        val percentage = (score.toFloat() / 100 * 100).toInt()

        // Update views
        findViewById<TextView>(R.id.tvScore).text = "$score"
        findViewById<TextView>(R.id.tvPercentage).text = "$percentage%"
        findViewById<TextView>(R.id.tvCorrect).text = "${score / 20} / $totalQuestions"
        
        // Performance message
        val performanceMessage = findViewById<TextView>(R.id.tvPerformance)
        val performanceIcon = findViewById<ImageView>(R.id.ivPerformanceIcon)
        
        when {
            percentage >= 80 -> {
                performanceMessage.text = "Excellent! You have a strong understanding of bioelectrode concepts."
                performanceIcon.setImageResource(R.drawable.ic_star)
            }
            percentage >= 60 -> {
                performanceMessage.text = "Good job! Review the concepts to strengthen your knowledge."
                performanceIcon.setImageResource(R.drawable.ic_check_circle)
            }
            else -> {
                performanceMessage.text = "Keep learning! Review the topics and try again."
                performanceIcon.setImageResource(R.drawable.ic_bulb)
            }
        }

        // Best score
        val bestScore = sharedPreferences.getInt("best_quiz_score", 0)
        findViewById<TextView>(R.id.tvBestScore).text = "$bestScore"

        // Total quizzes
        val totalQuizzes = sharedPreferences.getInt("total_quizzes", 0)
        findViewById<TextView>(R.id.tvTotalQuizzes).text = "$totalQuizzes"

        // Buttons
        findViewById<TextView>(R.id.btnRetakeQuiz).setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            startActivity(intent)
            finish()
        }

        findViewById<TextView>(R.id.btnBackHome).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }
}
