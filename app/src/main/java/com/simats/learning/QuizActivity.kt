package com.simats.learning

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class QuizActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private var currentQuestionIndex = 0
    private var score = 0
    private val questions = mutableListOf<QuizQuestion>()

    private lateinit var tvQuestionNumber: TextView
    private lateinit var tvQuestion: TextView
    private lateinit var ivQuestionIcon: ImageView
    private lateinit var rgOptions: RadioGroup
    private lateinit var rbOption1: RadioButton
    private lateinit var rbOption2: RadioButton
    private lateinit var rbOption3: RadioButton
    private lateinit var rbOption4: RadioButton
    private lateinit var btnSubmitAnswer: TextView
    private lateinit var tvProgress: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        sharedPreferences = getSharedPreferences("QuizProgress", MODE_PRIVATE)

        // Initialize views
        findViewById<ImageView>(R.id.ivBack).setOnClickListener {
            finish()
        }

        tvQuestionNumber = findViewById(R.id.tvQuestionNumber)
        tvQuestion = findViewById(R.id.tvQuestion)
        ivQuestionIcon = findViewById(R.id.ivQuestionIcon)
        rgOptions = findViewById(R.id.rgOptions)
        rbOption1 = findViewById(R.id.rbOption1)
        rbOption2 = findViewById(R.id.rbOption2)
        rbOption3 = findViewById(R.id.rbOption3)
        rbOption4 = findViewById(R.id.rbOption4)
        btnSubmitAnswer = findViewById(R.id.btnSubmitAnswer)
        tvProgress = findViewById(R.id.tvProgress)

        // Load questions
        loadQuestions()

        // Display first question
        displayQuestion()

        // Submit answer button
        btnSubmitAnswer.setOnClickListener {
            submitAnswer()
        }
    }

    private fun loadQuestions() {
        questions.addAll(
            listOf(
                QuizQuestion(
                    question = "What is the PRIMARY advantage of bipolar electrode recording over monopolar?",
                    options = listOf(
                        "Higher signal amplitude",
                        "Better spatial resolution and noise rejection",
                        "Lower equipment cost",
                        "Easier patient setup"
                    ),
                    correctAnswer = 1,
                    explanation = "Bipolar recording provides superior spatial resolution by measuring the potential difference between two nearby electrodes, effectively rejecting common-mode noise and distant interference.",
                    iconRes = R.drawable.ic_brain,
                    category = "Electrode Basics"
                ),
                QuizQuestion(
                    question = "In cardiac monitoring, why is monopolar recording preferred for detecting ST-segment elevation?",
                    options = listOf(
                        "It amplifies the signal more",
                        "It measures absolute voltage against a reference",
                        "It reduces electrical interference",
                        "It is cheaper to implement"
                    ),
                    correctAnswer = 1,
                    explanation = "Monopolar recording measures absolute voltage changes against Wilson's Central Terminal, crucial for detecting subtle ST-elevation (2-5 mV) that could be cancelled out in bipolar differential amplification.",
                    iconRes = R.drawable.ic_heart,
                    category = "Clinical Applications"
                ),
                QuizQuestion(
                    question = "What is the typical inter-electrode spacing used in bipolar EMG to maximize spatial resolution?",
                    options = listOf(
                        "1-2 cm",
                        "5-10 cm",
                        "15-20 cm",
                        "Over 25 cm"
                    ),
                    correctAnswer = 0,
                    explanation = "Bipolar EMG typically uses 1-2 cm inter-electrode spacing to achieve high spatial resolution, allowing isolation of individual muscle fibers and motor units while minimizing cross-talk.",
                    iconRes = R.drawable.ic_muscle,
                    category = "Technical Specifications"
                ),
                QuizQuestion(
                    question = "Which recording method is MORE susceptible to motion artifacts during ambulatory monitoring?",
                    options = listOf(
                        "Bipolar recording",
                        "Monopolar recording",
                        "Both equally susceptible",
                        "Neither is affected"
                    ),
                    correctAnswer = 1,
                    explanation = "Monopolar recording is more susceptible to motion artifacts because it measures absolute voltage. Bipolar recording's differential measurement helps cancel out motion-induced common-mode interference.",
                    iconRes = R.drawable.ic_warning,
                    category = "Artifact Management"
                ),
                QuizQuestion(
                    question = "In epilepsy surgery evaluation, why is monopolar EEG critical for seizure localization?",
                    options = listOf(
                        "It's faster to set up",
                        "It provides absolute voltage mapping for precise focus identification",
                        "It costs less than bipolar",
                        "It requires fewer electrodes"
                    ),
                    correctAnswer = 1,
                    explanation = "Monopolar EEG provides absolute voltage measurements necessary for creating precise topographic maps of seizure foci, essential for surgical planning. Bipolar would lose this spatial information through differential recording.",
                    iconRes = R.drawable.ic_brain,
                    category = "Advanced Clinical"
                )
            )
        )
    }

    private fun displayQuestion() {
        val question = questions[currentQuestionIndex]

        // Update question number and progress
        tvQuestionNumber.text = "Question ${currentQuestionIndex + 1} of ${questions.size}"
        tvProgress.text = "${currentQuestionIndex + 1}/${questions.size}"
        tvQuestion.text = question.question
        ivQuestionIcon.setImageResource(question.iconRes)

        // Set options
        rbOption1.text = question.options[0]
        rbOption2.text = question.options[1]
        rbOption3.text = question.options[2]
        rbOption4.text = question.options[3]

        // Clear previous selection
        rgOptions.clearCheck()

        // Reset option colors
        resetOptionColors()
    }

    private fun resetOptionColors() {
        val defaultColor = ContextCompat.getColor(this, R.color.black)
        rbOption1.setTextColor(defaultColor)
        rbOption2.setTextColor(defaultColor)
        rbOption3.setTextColor(defaultColor)
        rbOption4.setTextColor(defaultColor)
    }

    private fun submitAnswer() {
        val selectedId = rgOptions.checkedRadioButtonId

        if (selectedId == -1) {
            Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show()
            return
        }

        // Determine which option was selected
        val selectedIndex = when (selectedId) {
            R.id.rbOption1 -> 0
            R.id.rbOption2 -> 1
            R.id.rbOption3 -> 2
            R.id.rbOption4 -> 3
            else -> -1
        }

        val question = questions[currentQuestionIndex]
        val isCorrect = selectedIndex == question.correctAnswer

        // Show feedback
        if (isCorrect) {
            score += 20 // 20 points per question (5 questions = 100 total)
            highlightAnswer(selectedIndex, true)
            Toast.makeText(this, "Correct! +20 points", Toast.LENGTH_SHORT).show()
        } else {
            highlightAnswer(selectedIndex, false)
            highlightAnswer(question.correctAnswer, true)
            Toast.makeText(this, "Incorrect. No points deducted.", Toast.LENGTH_LONG).show()
        }

        // Disable options after submission
        rgOptions.isEnabled = false
        for (i in 0 until rgOptions.childCount) {
            rgOptions.getChildAt(i).isEnabled = false
        }

        // Change button text
        btnSubmitAnswer.text = if (currentQuestionIndex < questions.size - 1) {
            "Next Question →"
        } else {
            "View Results"
        }

        // Update button click listener
        btnSubmitAnswer.setOnClickListener {
            moveToNext()
        }
    }

    private fun highlightAnswer(index: Int, isCorrect: Boolean) {
        val color = if (isCorrect) {
            ContextCompat.getColor(this, R.color.green)
        } else {
            ContextCompat.getColor(this, R.color.red)
        }

        when (index) {
            0 -> rbOption1.setTextColor(color)
            1 -> rbOption2.setTextColor(color)
            2 -> rbOption3.setTextColor(color)
            3 -> rbOption4.setTextColor(color)
        }
    }

    private fun moveToNext() {
        if (currentQuestionIndex < questions.size - 1) {
            currentQuestionIndex++
            
            // Re-enable options
            rgOptions.isEnabled = true
            for (i in 0 until rgOptions.childCount) {
                rgOptions.getChildAt(i).isEnabled = true
            }
            
            // Reset button
            btnSubmitAnswer.text = "Submit Answer"
            btnSubmitAnswer.setOnClickListener {
                submitAnswer()
            }
            
            displayQuestion()
        } else {
            // Quiz completed - save score and show results
            saveQuizScore()
            showResults()
        }
    }

    private fun saveQuizScore() {
        val editor = sharedPreferences.edit()
        editor.putInt("last_quiz_score", score)
        editor.putLong("last_quiz_time", System.currentTimeMillis())
        
        // Update total quizzes taken
        val totalQuizzes = sharedPreferences.getInt("total_quizzes", 0)
        editor.putInt("total_quizzes", totalQuizzes + 1)
        
        // Update best score
        val bestScore = sharedPreferences.getInt("best_quiz_score", 0)
        if (score > bestScore) {
            editor.putInt("best_quiz_score", score)
        }
        
        editor.apply()
    }

    private fun showResults() {
        val intent = Intent(this, QuizResultsActivity::class.java)
        intent.putExtra("SCORE", score)
        intent.putExtra("TOTAL_QUESTIONS", questions.size)
        startActivity(intent)
        finish()
    }
}

data class QuizQuestion(
    val question: String,
    val options: List<String>,
    val correctAnswer: Int, // Index of correct option
    val explanation: String,
    val iconRes: Int,
    val category: String
)
