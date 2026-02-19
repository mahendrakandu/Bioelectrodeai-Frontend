package com.simats.learning.models

/**
 * Holds the results of the AI analysis
 */
data class AIRecommendation(
    val recommendedTechnique: String, // "Bipolar" or "Monopolar"
    val reason: String,               // Explanation for the recommendation
    val bipolarScore: Double,         // Calculated score for bipolar
    val monopolarScore: Double,       // Calculated score for monopolar
    val confidenceLevel: Int          // Confidence percentage (0-100)
)
