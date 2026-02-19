package com.simats.learning.models

/**
 * Represents quality metrics for a specific recording technique
 */
data class SignalQuality(
    val amplitude: Double,    // Signal amplitude in μV or mV
    val noiseLevel: Double,   // Background noise level in μV
    val snr: Double,          // Signal-to-Noise Ratio (higher is better)
    val stability: Double     // Signal stability percentage (0-100)
)
