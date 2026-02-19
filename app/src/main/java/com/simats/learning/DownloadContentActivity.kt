package com.simats.learning

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DownloadContentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download_content)

        // Back button click listener
        findViewById<ImageView>(R.id.ivBack).setOnClickListener {
            finish()
        }

        // Download categories click listeners
        findViewById<LinearLayout>(R.id.downloadModules).setOnClickListener {
            Toast.makeText(this, "Downloading Learning Modules...", Toast.LENGTH_SHORT).show()
        }

        findViewById<LinearLayout>(R.id.downloadCases).setOnClickListener {
            Toast.makeText(this, "Downloading Clinical Cases...", Toast.LENGTH_SHORT).show()
        }

        findViewById<LinearLayout>(R.id.downloadGlossary).setOnClickListener {
            Toast.makeText(this, "Downloading Glossary Terms...", Toast.LENGTH_SHORT).show()
        }

        findViewById<LinearLayout>(R.id.downloadResources).setOnClickListener {
            Toast.makeText(this, "Downloading Resources Library...", Toast.LENGTH_SHORT).show()
        }

        // View downloads button
        findViewById<TextView>(R.id.btnViewDownloads).setOnClickListener {
            startActivity(Intent(this, DownloadedContentActivity::class.java))
        }

        // Download all button
        findViewById<TextView>(R.id.btnDownloadAll).setOnClickListener {
            Toast.makeText(this, "Downloading all content (541 MB)...", Toast.LENGTH_LONG).show()
        }
    }
}
