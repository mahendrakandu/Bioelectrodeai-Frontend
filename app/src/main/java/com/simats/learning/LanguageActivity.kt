package com.simats.learning

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LanguageActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private var selectedLanguage = "en" // Default English

    // Check marks for each language
    private lateinit var checkEnglish: ImageView
    private lateinit var checkSpanish: ImageView
    private lateinit var checkFrench: ImageView
    private lateinit var checkGerman: ImageView
    private lateinit var checkChinese: ImageView
    private lateinit var checkJapanese: ImageView
    private lateinit var checkArabic: ImageView
    private lateinit var checkHindi: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        selectedLanguage = sharedPreferences.getString("language", "en") ?: "en"

        // Initialize check marks
        checkEnglish = findViewById(R.id.checkEnglish)
        checkSpanish = findViewById(R.id.checkSpanish)
        checkFrench = findViewById(R.id.checkFrench)
        checkGerman = findViewById(R.id.checkGerman)
        checkChinese = findViewById(R.id.checkChinese)
        checkJapanese = findViewById(R.id.checkJapanese)
        checkArabic = findViewById(R.id.checkArabic)
        checkHindi = findViewById(R.id.checkHindi)

        // Update UI based on saved language
        updateCheckMarks()

        // Back button click listener
        findViewById<ImageView>(R.id.ivBack).setOnClickListener {
            finish()
        }

        // Language selection click listeners
        findViewById<LinearLayout>(R.id.langEnglish).setOnClickListener {
            selectLanguage("en", checkEnglish, "English")
        }

        findViewById<LinearLayout>(R.id.langSpanish).setOnClickListener {
            selectLanguage("es", checkSpanish, "Español")
        }

        findViewById<LinearLayout>(R.id.langFrench).setOnClickListener {
            selectLanguage("fr", checkFrench, "Français")
        }

        findViewById<LinearLayout>(R.id.langGerman).setOnClickListener {
            selectLanguage("de", checkGerman, "Deutsch")
        }

        findViewById<LinearLayout>(R.id.langChinese).setOnClickListener {
            selectLanguage("zh", checkChinese, "中文")
        }

        findViewById<LinearLayout>(R.id.langJapanese).setOnClickListener {
            selectLanguage("ja", checkJapanese, "日本語")
        }

        findViewById<LinearLayout>(R.id.langArabic).setOnClickListener {
            selectLanguage("ar", checkArabic, "العربية")
        }

        findViewById<LinearLayout>(R.id.langHindi).setOnClickListener {
            selectLanguage("hi", checkHindi, "हिन्दी")
        }
    }

    private fun selectLanguage(langCode: String, checkMark: ImageView, langName: String) {
        // Save selected language
        selectedLanguage = langCode
        sharedPreferences.edit().putString("language", langCode).apply()

        // Update check marks
        updateCheckMarks()

        // Show confirmation
        Toast.makeText(this, "$langName selected", Toast.LENGTH_SHORT).show()
    }

    private fun updateCheckMarks() {
        // Hide all check marks first
        checkEnglish.visibility = View.GONE
        checkSpanish.visibility = View.GONE
        checkFrench.visibility = View.GONE
        checkGerman.visibility = View.GONE
        checkChinese.visibility = View.GONE
        checkJapanese.visibility = View.GONE
        checkArabic.visibility = View.GONE
        checkHindi.visibility = View.GONE

        // Show check mark for selected language
        when (selectedLanguage) {
            "en" -> checkEnglish.visibility = View.VISIBLE
            "es" -> checkSpanish.visibility = View.VISIBLE
            "fr" -> checkFrench.visibility = View.VISIBLE
            "de" -> checkGerman.visibility = View.VISIBLE
            "zh" -> checkChinese.visibility = View.VISIBLE
            "ja" -> checkJapanese.visibility = View.VISIBLE
            "ar" -> checkArabic.visibility = View.VISIBLE
            "hi" -> checkHindi.visibility = View.VISIBLE
        }
    }
}
