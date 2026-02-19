package com.simats.learning

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ResourcesLibraryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resources_library)

        // Back button click listener
        findViewById<ImageView>(R.id.ivBack).setOnClickListener {
            finish()
        }

        // Research Papers
        setupResourceItem(R.id.resourcePaper1, "Bioelectronic Medicine (4.2 MB)")
        setupResourceItem(R.id.resourcePaper2, "Electrode Configuration Strategies (6.8 MB)")
        setupResourceItem(R.id.resourcePaper3, "Neural Signal Processing Methods (8.5 MB)")

        setupDownloadButton(R.id.downloadPaper1, "Bioelectronic Medicine")
        setupDownloadButton(R.id.downloadPaper2, "Electrode Configuration Strategies")
        setupDownloadButton(R.id.downloadPaper3, "Neural Signal Processing Methods")

        // Video Tutorials
        setupResourceItem(R.id.resourceVideo1, "Electrode Setup Guide video")
        setupResourceItem(R.id.resourceVideo2, "Signal Quality Troubleshooting video")

        setupDownloadButton(R.id.downloadVideo1, "Setup Guide video")
        setupDownloadButton(R.id.downloadVideo2, "Troubleshooting video")

        // Quick Reference Cards
        setupResourceItem(R.id.resourceCard1, "Electrode Placement Guide (2.1 MB)")
        setupResourceItem(R.id.resourceCard2, "Signal Interpretation Checklist (1.8 MB)")
        setupResourceItem(R.id.resourceCard3, "Troubleshooting Common Issues (3.2 MB)")

        setupDownloadButton(R.id.downloadCard1, "Electrode Placement Guide")
        setupDownloadButton(R.id.downloadCard2, "Signal Interpretation Checklist")
        setupDownloadButton(R.id.downloadCard3, "Troubleshooting Common Issues")
    }

    private fun setupResourceItem(resourceId: Int, resourceName: String) {
        findViewById<LinearLayout>(resourceId).setOnClickListener {
            Toast.makeText(this, "Opening $resourceName...", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupDownloadButton(buttonId: Int, resourceName: String) {
        findViewById<ImageView>(buttonId).setOnClickListener {
            Toast.makeText(this, "Downloading $resourceName...", Toast.LENGTH_SHORT).show()
        }
    }
}
