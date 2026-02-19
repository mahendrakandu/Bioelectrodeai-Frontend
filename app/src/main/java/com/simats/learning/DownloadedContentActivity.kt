package com.simats.learning

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DownloadedContentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_downloaded_content)

        // Back button click listener
        findViewById<ImageView>(R.id.ivBack).setOnClickListener {
            finish()
        }

        // Individual delete buttons
        findViewById<ImageView>(R.id.deleteModule1).setOnClickListener {
            Toast.makeText(this, "Module deleted", Toast.LENGTH_SHORT).show()
        }

        findViewById<ImageView>(R.id.deleteCase1).setOnClickListener {
            Toast.makeText(this, "Clinical case deleted", Toast.LENGTH_SHORT).show()
        }

        findViewById<ImageView>(R.id.deleteGlossary1).setOnClickListener {
            Toast.makeText(this, "Glossary deleted", Toast.LENGTH_SHORT).show()
        }

        // Downloaded item click listeners (to open content)
        findViewById<LinearLayout>(R.id.downloadedModule1).setOnClickListener {
            Toast.makeText(this, "Opening Bipolar Electrode Basics module...", Toast.LENGTH_SHORT).show()
        }

        findViewById<LinearLayout>(R.id.downloadedCase1).setOnClickListener {
            Toast.makeText(this, "Opening Cardiac Monitoring case...", Toast.LENGTH_SHORT).show()
        }

        findViewById<LinearLayout>(R.id.downloadedGlossary1).setOnClickListener {
            Toast.makeText(this, "Opening Medical Terminology glossary...", Toast.LENGTH_SHORT).show()
        }

        // Delete all button
        findViewById<TextView>(R.id.btnDeleteAll).setOnClickListener {
            Toast.makeText(this, "All downloads deleted (67.6 MB freed)", Toast.LENGTH_LONG).show()
        }
    }
}
