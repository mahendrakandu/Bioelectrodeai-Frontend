package com.simats.learning

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class EegDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eeg_detail)

        // Back button
        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            finish()
        }
    }
}
