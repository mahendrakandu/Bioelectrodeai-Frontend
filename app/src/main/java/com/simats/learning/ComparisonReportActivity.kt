package com.simats.learning

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class ComparisonReportActivity : AppCompatActivity() {

    private lateinit var btnSignalECG: LinearLayout
    private lateinit var btnSignalEEG: LinearLayout
    private lateinit var btnSignalEMG: LinearLayout
    private lateinit var btnGenerateReport: MaterialButton

    private var selectedSignal = "ECG" // Default

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            setContentView(R.layout.activity_comparison_report)
        } catch (e: Exception) {
            e.printStackTrace()
            finish()
            return
        }

        // Initialize Views
        btnSignalECG = findViewById(R.id.btnSignalECG)
        btnSignalEEG = findViewById(R.id.btnSignalEEG)
        btnSignalEMG = findViewById(R.id.btnSignalEMG)
        btnGenerateReport = findViewById(R.id.btnGenerateReport)

        // Set Listeners
        btnSignalECG.setOnClickListener { selectSignal("ECG") }
        btnSignalEEG.setOnClickListener { selectSignal("EEG") }
        btnSignalEMG.setOnClickListener { selectSignal("EMG") }

        btnGenerateReport.setOnClickListener {
            val intent = Intent(this, SignalQualityActivity::class.java)
            intent.putExtra("EXTRA_SIGNAL_TYPE", selectedSignal)
            startActivity(intent)
        }

        findViewById<LinearLayout>(R.id.btnBack)?.setOnClickListener {
            finish()
        }

        // Default selection
        selectSignal(selectedSignal)
    }

    private fun selectSignal(signal: String) {
        selectedSignal = signal

        // Reset all backgrounds
        btnSignalECG.setBackgroundResource(R.drawable.card_rounded_border)
        btnSignalEEG.setBackgroundResource(R.drawable.card_rounded_border)
        btnSignalEMG.setBackgroundResource(R.drawable.card_rounded_border)

        // Highlight selected
        when (signal) {
            "ECG" -> btnSignalECG.setBackgroundResource(R.drawable.card_bipolar_bg) // Reusing existing drawable for highlight
            "EEG" -> btnSignalEEG.setBackgroundResource(R.drawable.card_bipolar_bg)
            "EMG" -> btnSignalEMG.setBackgroundResource(R.drawable.card_bipolar_bg)
        }
    }
}
