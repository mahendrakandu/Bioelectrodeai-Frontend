package com.simats.learning

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class StorageUsageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage_usage)

        // Back button click listener
        findViewById<ImageView>(R.id.ivBack).setOnClickListener {
            finish()
        }

        // Clear cache button
        findViewById<TextView>(R.id.btnClearCache).setOnClickListener {
            Toast.makeText(this, "Cache cleared successfully", Toast.LENGTH_SHORT).show()
        }
    }
}
