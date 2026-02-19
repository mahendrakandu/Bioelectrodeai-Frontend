package com.simats.learning.models

/**
 * Represents a complete signal comparison entry
 */
data class SignalData(
    val signalType: String,      // e.g., "ECG", "EEG", "EMG"
    val bipolar: SignalQuality,  // Metrics for bipolar recording
    val monopolar: SignalQuality // Metrics for monopolar recording
)
