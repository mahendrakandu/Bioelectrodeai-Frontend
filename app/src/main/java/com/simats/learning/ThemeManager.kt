package com.simats.learning

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

/**
 * Manages theme preferences and application across the app
 */
object ThemeManager {
    private const val PREFS_NAME = "theme_prefs"
    private const val KEY_THEME_MODE = "theme_mode"
    
    // Theme modes
    const val MODE_LIGHT = 0
    const val MODE_DARK = 1
    const val MODE_SYSTEM = 2
    
    /**
     * Save theme preference
     */
    fun saveThemeMode(context: Context, mode: Int) {
        val prefs = getPreferences(context)
        prefs.edit().putInt(KEY_THEME_MODE, mode).apply()
    }
    
    /**
     * Get saved theme preference
     */
    fun getThemeMode(context: Context): Int {
        val prefs = getPreferences(context)
        return prefs.getInt(KEY_THEME_MODE, MODE_LIGHT)
    }
    
    /**
     * Apply theme based on saved preference
     */
    fun applyTheme(context: Context) {
        val mode = getThemeMode(context)
        applyThemeMode(mode)
    }
    
    /**
     * Apply specific theme mode
     */
    fun applyThemeMode(mode: Int) {
        when (mode) {
            MODE_LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            MODE_DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            MODE_SYSTEM -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
    
    /**
     * Check if dark mode is currently enabled
     */
    fun isDarkModeEnabled(context: Context): Boolean {
        return getThemeMode(context) == MODE_DARK
    }
    
    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }
}
