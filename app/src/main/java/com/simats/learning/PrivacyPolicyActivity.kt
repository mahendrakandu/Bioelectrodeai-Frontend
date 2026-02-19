package com.simats.learning

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PrivacyPolicyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)

        // Back button click listener
        findViewById<ImageView>(R.id.ivBack).setOnClickListener {
            finish()
        }

        // View Terms & Conditions button click listener
        findViewById<TextView>(R.id.btnViewTermsConditions).setOnClickListener {
            startActivity(Intent(this, TermsConditionsActivity::class.java))
        }

        // I Understand button click listener
        findViewById<TextView>(R.id.btnIUnderstand).setOnClickListener {
            Toast.makeText(this, "Thank you for reviewing our privacy policy!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
