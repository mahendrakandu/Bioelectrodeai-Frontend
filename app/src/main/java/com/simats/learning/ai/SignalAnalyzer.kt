package com.simats.learning.ai

import com.simats.learning.models.AIRecommendation
import com.simats.learning.models.SignalData
import com.simats.learning.models.SignalQuality
import kotlin.math.abs
import kotlin.math.roundToInt

class SignalAnalyzer {

    companion object {
        // Weights for scoring (Total = 1.0)
        private const val WEIGHT_SNR = 0.40
        private const val WEIGHT_STABILITY = 0.30
        private const val WEIGHT_NOISE = 0.20
        private const val WEIGHT_AMPLITUDE = 0.10
    }

    fun analyze(signalData: SignalData): AIRecommendation {
        val bipolarScore = calculateScore(signalData.bipolar)
        val monopolarScore = calculateScore(signalData.monopolar)
        
        val scoreDifference = abs(bipolarScore - monopolarScore)
        val confidence = calculateConfidence(scoreDifference)
        
        val isBipolarBetter = bipolarScore > monopolarScore
        
        val recommendedTechnique = if (isBipolarBetter) "Bipolar Recording" else "Monopolar Recording"
        val reason = generateReason(signalData, isBipolarBetter)

        return AIRecommendation(
            recommendedTechnique = recommendedTechnique,
            reason = reason,
            bipolarScore = bipolarScore,
            monopolarScore = monopolarScore,
            confidenceLevel = confidence
        )
    }

    private fun calculateScore(quality: SignalQuality): Double {
        // Normalize noise (lower is better, so we invert its impact or subtract)
        // We start with a base score and add/subtract based on metrics
        
        // Simple weighted sum approach:
        // SNR: Higher is better
        // Stability: Higher is better
        // Noise: Lower is better (we subtract)
        // Amplitude: Higher is better (generally, for detection)
        
        var score = 0.0
        
        // SNR contribution (0-40 points)
        // Assuming max typical SNR is around 50dB
        score += (quality.snr.coerceIn(0.0, 50.0) / 50.0) * 100 * WEIGHT_SNR
        
        // Stability contribution (0-30 points)
        score += (quality.stability.coerceIn(0.0, 100.0) / 100.0) * 100 * WEIGHT_STABILITY
        
        // Noise penalty (0-20 points)
        // Assuming max typical noise is around 20uV
        // If noise is 0, penalty is 0. If noise is high, penalty is high.
        // We want score to be higher for lower noise.
        // So we add (1 - normalized_noise) * weight
        val normalizedNoise = quality.noiseLevel.coerceIn(0.0, 20.0) / 20.0
        score += (1.0 - normalizedNoise) * 100 * WEIGHT_NOISE
        
        // Amplitude contribution (0-10 points)
        // Assuming max meaningful amplitude difference is around 200uV for surface
        score += (quality.amplitude.coerceIn(0.0, 200.0) / 200.0) * 100 * WEIGHT_AMPLITUDE
        
        return score.coerceIn(0.0, 100.0)
    }

    private fun calculateConfidence(scoreDiff: Double): Int {
        // Map score difference to confidence percentage
        // If diff > 20, confidence is high (>90%)
        // If diff < 5, confidence is low (<60%)
        
        return when {
            scoreDiff > 20 -> 90 + ((scoreDiff - 20) / 2).toInt().coerceAtMost(9)
            scoreDiff > 10 -> 80 + (scoreDiff - 10).toInt()
            scoreDiff > 5 -> 70 + (scoreDiff - 5).toInt() * 2
            else -> 50 + (scoreDiff * 2).toInt()
        }
    }

    private fun generateReason(data: SignalData, isBipolarBetter: Boolean): String {
        val comparison = if (isBipolarBetter) "Preferred" else "Better"
        
        return if (isBipolarBetter) {
            when {
                data.bipolar.noiseLevel < data.monopolar.noiseLevel && data.bipolar.snr > data.monopolar.snr -> 
                    "Bipolar recording offers significantly better noise rejection and higher SNR, making it ideal for this signal type."
                data.bipolar.stability > data.monopolar.stability ->
                    "Bipolar recording provides greater stability, which is critical for accurate analysis of ${data.signalType}."
                else ->
                    "Bipolar recording shows a better overall balance of signal quality metrics."
            }
        } else {
            when {
                data.monopolar.amplitude > data.bipolar.amplitude * 1.5 ->
                    "Monopolar recording captures much higher amplitude, which is beneficial for detecting low-level ${data.signalType} signals."
                else ->
                    "Monopolar recording is suitable here due to its specific sensitivity characteristics."
            }
        }
    }
}
