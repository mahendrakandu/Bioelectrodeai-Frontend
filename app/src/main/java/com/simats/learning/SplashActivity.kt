package com.simats.learning

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // Apply saved theme BEFORE super.onCreate()
        ThemeManager.applyTheme(this)
        
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Fade in animation for text
        val fadeIn = AlphaAnimation(0.0f, 1.0f)
        fadeIn.duration = 1500
        
        findViewById<TextView>(R.id.appName).startAnimation(fadeIn)
        findViewById<TextView>(R.id.subtitle1).startAnimation(fadeIn)

        // Delay for 3 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            // Check if user has completed onboarding is not necessary here if OnboardingActivity handles it,
            // BUT OnboardingActivity checks on onCreate. 
            // So we can just navigate to OnboardingActivity and let it decide.
            
            val intent = Intent(this, OnboardingActivity::class.java)
            startActivity(intent)
            finish() // Prevents user from going back to splash screen
        }, 3000)
    }
}
