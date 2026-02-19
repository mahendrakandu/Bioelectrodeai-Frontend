package com.simats.learning

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class TermsConditionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_conditions)

        // Back button click listener
        findViewById<ImageView>(R.id.ivBack).setOnClickListener {
            finish()
        }

        // View Privacy Policy button click listener
        findViewById<TextView>(R.id.btnViewPrivacyPolicy).setOnClickListener {
            startActivity(Intent(this, PrivacyPolicyActivity::class.java))
        }

        // I Agree to Terms button click listener
        findViewById<TextView>(R.id.btnAgreeToTerms).setOnClickListener {
            Toast.makeText(this, "Thank you for accepting the terms!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
