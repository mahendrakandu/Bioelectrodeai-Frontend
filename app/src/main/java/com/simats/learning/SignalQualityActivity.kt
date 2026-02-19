package com.simats.learning

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.simats.learning.ai.SignalAnalyzer
import com.simats.learning.models.AIRecommendation
import com.simats.learning.models.SignalData
import com.simats.learning.utils.SignalDatasetReader

class SignalQualityActivity : AppCompatActivity() {

    private lateinit var signalDatasetReader: SignalDatasetReader
    private lateinit var signalAnalyzer: SignalAnalyzer
    
    // UI Elements
    private lateinit var spinnerSignalType: Spinner
    
    // Bipolar UI
    private lateinit var tvBipolarAmplitude: TextView
    private lateinit var tvBipolarNoise: TextView
    private lateinit var tvBipolarSNR: TextView
    // private lateinit var ivBipolarWaveform: ImageView
    
    // Monopolar UI
    private lateinit var tvMonopolarAmplitude: TextView
    private lateinit var tvMonopolarNoise: TextView
    private lateinit var tvMonopolarSNR: TextView
    // private lateinit var ivMonopolarWaveform: ImageView
    
    // Quality Metrics
    private lateinit var tvBipolarClarity: TextView
    private lateinit var pbBipolarClarity: ProgressBar
    private lateinit var tvMonopolarClarity: TextView
    private lateinit var pbMonopolarClarity: ProgressBar
    
    // AI Analysis
    private lateinit var tvAIRecommendation: TextView
    private lateinit var tvAIReason: TextView
    private lateinit var tvConfidenceLevel: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            setContentView(R.layout.activity_signal_quality)
        } catch (e: Exception) {
            e.printStackTrace()
            finish()
            return
        }

        // Initialize helpers
        signalDatasetReader = SignalDatasetReader(this)
        signalAnalyzer = SignalAnalyzer()

        // Initialize UI components
        initializeViews()
        
        // Setup Spinner
        setupSignalSpinner()

        // Back button
        findViewById<LinearLayout>(R.id.btnBack)?.setOnClickListener {
            finish()
        }
    }

    private fun initializeViews() {
        spinnerSignalType = findViewById(R.id.spinnerSignalType)
        
        tvBipolarAmplitude = findViewById(R.id.tvBipolarAmplitude)
        tvBipolarNoise = findViewById(R.id.tvBipolarNoise)
        tvBipolarSNR = findViewById(R.id.tvBipolarSNR)
        
        tvMonopolarAmplitude = findViewById(R.id.tvMonopolarAmplitude)
        tvMonopolarNoise = findViewById(R.id.tvMonopolarNoise)
        tvMonopolarSNR = findViewById(R.id.tvMonopolarSNR)
        
        tvBipolarClarity = findViewById(R.id.tvBipolarClarity)
        pbBipolarClarity = findViewById(R.id.pbBipolarClarity)
        tvMonopolarClarity = findViewById(R.id.tvMonopolarClarity)
        pbMonopolarClarity = findViewById(R.id.pbMonopolarClarity)
        
        tvAIRecommendation = findViewById(R.id.tvAIRecommendation)
        tvAIReason = findViewById(R.id.tvAIReason)
        tvConfidenceLevel = findViewById(R.id.tvConfidenceLevel)
    }

    private fun setupSignalSpinner() {
        val signals = listOf("ECG", "EEG", "EMG")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, signals)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSignalType.adapter = adapter

        spinnerSignalType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedSignal = signals[position]
                loadAndAnalyzeSignal(selectedSignal)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }
        
        // Check for Intent Extra
        val preSelectedSignal = intent.getStringExtra("EXTRA_SIGNAL_TYPE")
        if (preSelectedSignal != null) {
            val position = signals.indexOfFirst { it.equals(preSelectedSignal, ignoreCase = true) }
            if (position >= 0) {
                spinnerSignalType.setSelection(position)
            }
        }
    }

    private fun loadAndAnalyzeSignal(signalType: String) {
        val signalData = signalDatasetReader.getSignalByType(signalType)
        
        if (signalData != null) {
            updateUIWithData(signalData)
            performAIAnalysis(signalData)
        }
    }

    private fun updateUIWithData(data: SignalData) {
        // Bipolar Updates
        tvBipolarAmplitude.text = "±${data.bipolar.amplitude} μV"
        tvBipolarNoise.text = "${data.bipolar.noiseLevel} μV"
        tvBipolarSNR.text = "${data.bipolar.snr}:1"
        
        // Monopolar Updates
        tvMonopolarAmplitude.text = "±${data.monopolar.amplitude} μV"
        tvMonopolarNoise.text = "${data.monopolar.noiseLevel} μV"
        tvMonopolarSNR.text = "${data.monopolar.snr}:1"
        
        // Quality Metrics (Using stability as a proxy for clarity)
        val bipolarClarity = data.bipolar.stability.toInt()
        val monopolarClarity = data.monopolar.stability.toInt()
        
        tvBipolarClarity.text = "$bipolarClarity%"
        pbBipolarClarity.progress = bipolarClarity
        
        tvMonopolarClarity.text = "$monopolarClarity%"
        pbMonopolarClarity.progress = monopolarClarity
    }

    private fun performAIAnalysis(data: SignalData) {
        val recommendation = signalAnalyzer.analyze(data)
        
        tvAIRecommendation.text = "Recommendation: ${recommendation.recommendedTechnique}"
        tvAIReason.text = recommendation.reason
        tvConfidenceLevel.text = "Confidence: ${recommendation.confidenceLevel}%"
        
        // Color coding based on recommendation
        if (recommendation.recommendedTechnique.contains("Bipolar")) {
            tvAIRecommendation.setTextColor(ContextCompat.getColor(this, android.R.color.white))
        } else {
             // Keep white for now, or change if needed
             tvAIRecommendation.setTextColor(ContextCompat.getColor(this, android.R.color.white))
        }
    }
}
