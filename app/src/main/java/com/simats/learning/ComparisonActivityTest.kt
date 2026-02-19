package com.simats.learning

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ComparisonActivityTest : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comparison_test)
        android.util.Log.d("ComparisonTest", "Activity created successfully!")
    }
}
