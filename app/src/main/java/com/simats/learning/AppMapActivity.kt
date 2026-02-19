package com.simats.learning

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class AppMapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_map)

        // Back button
        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            finish()
        }
    }
}
