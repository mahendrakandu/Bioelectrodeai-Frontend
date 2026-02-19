package com.simats.learning

import android.content.Context
import android.content.SharedPreferences

/**
 * Manages topic completion progress using SharedPreferences
 */
class TopicProgressManager(context: Context) {

    companion object {
        private const val PREFS_NAME = "topic_progress"
        private const val KEY_BIPOLAR_PREFIX = "bipolar_"
        private const val KEY_MONOPOLAR_PREFIX = "monopolar_"
    }

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    /**
     * Mark a topic as completed
     */
    fun markTopicCompleted(electrodeType: String, topicIndex: Int) {
        val key = getKey(electrodeType, topicIndex)
        prefs.edit().putBoolean(key, true).apply()
    }

    /**
     * Check if a topic is completed
     */
    fun isTopicCompleted(electrodeType: String, topicIndex: Int): Boolean {
        val key = getKey(electrodeType, topicIndex)
        return prefs.getBoolean(key, false)
    }

    /**
     * Get the count of completed topics for an electrode type
     */
    fun getCompletedCount(electrodeType: String): Int {
        var count = 0
        for (i in 0..4) {
            if (isTopicCompleted(electrodeType, i)) {
                count++
            }
        }
        return count
    }

    /**
     * Get total completed topics across both electrode types
     */
    fun getTotalCompletedCount(): Int {
        return getCompletedCount(TopicDetailActivity.TYPE_BIPOLAR) + 
               getCompletedCount(TopicDetailActivity.TYPE_MONOPOLAR)
    }

    /**
     * Clear all topic progress (used on fresh login)
     */
    fun clearAllProgress() {
        prefs.edit().clear().apply()
    }

    private fun getKey(electrodeType: String, topicIndex: Int): String {
        val prefix = if (electrodeType == TopicDetailActivity.TYPE_BIPOLAR) {
            KEY_BIPOLAR_PREFIX
        } else {
            KEY_MONOPOLAR_PREFIX
        }
        return "$prefix$topicIndex"
    }
}
