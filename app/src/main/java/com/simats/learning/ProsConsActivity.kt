package com.simats.learning

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class ProsConsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            setContentView(R.layout.activity_pros_cons)
        } catch (e: Exception) {
            android.util.Log.e("ProsConsActivity", "Failed to inflate layout", e)
            finish()
            return
        }

        // Back button
        findViewById<LinearLayout>(R.id.btnBack)?.setOnClickListener {
            finish()
        }

        android.util.Log.d("ProsConsActivity", "Activity initialized successfully")
    }
}
