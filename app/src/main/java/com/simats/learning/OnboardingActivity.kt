package com.simats.learning

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.button.MaterialButton

class OnboardingActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var btnContinue: MaterialButton
    private lateinit var rootLayout: LinearLayout

    // Colors matching each fragment background
    private val pageColors = arrayOf(
        "#1565C0", // Blue - Intro page
        "#00897B", // Teal - Techniques page
        "#7B1FA2"  // Purple - Features page
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        // Apply saved theme BEFORE super.onCreate()
        ThemeManager.applyTheme(this)
        
        super.onCreate(savedInstanceState)

        // Check if onboarding is completed
        val sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        val isFirstTime = sharedPreferences.getBoolean("isFirstTime", true)

        // Temporarily commented out to verify Splash -> Onboarding flow
        // if (!isFirstTime) {
        //     navigateToLogin()
        //     return
        // }

        setContentView(R.layout.activity_onboarding)

        rootLayout = findViewById(R.id.rootLayout)
        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabLayout)
        btnContinue = findViewById(R.id.btnContinue)

        val adapter = OnboardingAdapter(this)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()

        // Handle button click
        btnContinue.setOnClickListener {
            if (viewPager.currentItem < adapter.itemCount - 1) {
                viewPager.currentItem = viewPager.currentItem + 1
            } else {
                completeOnboarding()
            }
        }
        
        // Change button text and background color on page change
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                
                // Update button text
                if (position == adapter.itemCount - 1) {
                    btnContinue.text = "Get Started"
                } else {
                    btnContinue.text = "Continue"
                }
                
                // Update background color to match the current page
                if (position < pageColors.size) {
                    rootLayout.setBackgroundColor(Color.parseColor(pageColors[position]))
                }
            }
        })
    }

    private fun completeOnboarding() {
        val sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isFirstTime", false)
        editor.apply()
        navigateToLogin()
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
