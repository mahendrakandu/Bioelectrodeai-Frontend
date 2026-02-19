package com.simats.learning

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GlossaryActivity : AppCompatActivity() {

    private lateinit var rvGlossaryTerms: RecyclerView
    private lateinit var glossaryAdapter: GlossaryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_glossary)

        // Initialize RecyclerView
        rvGlossaryTerms = findViewById(R.id.rvGlossaryTerms)
        rvGlossaryTerms.layoutManager = LinearLayoutManager(this)

        // Set up glossary data
        val glossaryTerms = getGlossaryTerms()
        glossaryAdapter = GlossaryAdapter(glossaryTerms)
        rvGlossaryTerms.adapter = glossaryAdapter

        // Back button click listener
        findViewById<ImageView>(R.id.ivBack).setOnClickListener {
            finish()
        }

        // Ready Up button click listener
        findViewById<TextView>(R.id.btnReadyUp).setOnClickListener {
            Toast.makeText(this, "Ready to learn!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun getGlossaryTerms(): List<GlossaryTerm> {
        return listOf(
            GlossaryTerm(
                1,
                "Active Electrode",
                "The electrode placed directly over the target muscle or recording site",
                R.drawable.ic_electrode_monopolar
            ),
            GlossaryTerm(
                2,
                "Anode/Cathode",
                "Positive and negative electrodes used in electrical stimulation",
                R.drawable.ic_bolt
            ),
            GlossaryTerm(
                3,
                "Artifact",
                "Interference or noise in the recorded signal not originating from the target source",
                R.drawable.ic_signal_wave
            ),
            GlossaryTerm(
                4,
                "Bipolar Recording",
                "Recording technique using two active electrodes to measure the potential difference",
                R.drawable.ic_electrode_bipolar
            ),
            GlossaryTerm(
                5,
                "Concentric Needle",
                "A needle electrode with one active recording surface",
                R.drawable.ic_clinical
            ),
            GlossaryTerm(
                6,
                "Common Mode Rejection",
                "The ability of a differential amplifier to reject signals common to both inputs",
                R.drawable.ic_shield_check
            ),
            GlossaryTerm(
                7,
                "Conducting Medium/Potential",
                "The substance that allows electrical current flow between electrode and tissue",
                R.drawable.ic_muscle
            ),
            GlossaryTerm(
                8,
                "Differential Amplifier",
                "An amplifier that amplifies the difference between two input signals",
                R.drawable.ic_chart
            ),
            GlossaryTerm(
                9,
                "SFAP (Single Fiber Action Potential)",
                "The electrical signal from a single muscle fiber",
                R.drawable.ic_speed
            ),
            GlossaryTerm(
                10,
                "ECG (Electrocardiography)",
                "Recording of the electrical activity of the heart",
                R.drawable.ic_heart
            ),
            GlossaryTerm(
                11,
                "EEG (Electroencephalography)",
                "Recording of electrical activity of the brain",
                R.drawable.ic_brain
            ),
            GlossaryTerm(
                12,
                "Monopolar Recording",
                "Recording technique using one active electrode and a distant reference",
                R.drawable.ic_monopolar
            ),
            GlossaryTerm(
                13,
                "Motor Unit",
                "A motor neuron and all the muscle fibers it innervates",
                R.drawable.ic_medical_teal
            ),
            GlossaryTerm(
                14,
                "Electrode Selection Methods",
                "Criteria for choosing appropriate electrodes for a specific application",
                R.drawable.ic_help
            ),
            GlossaryTerm(
                15,
                "Depolarization Velocity",
                "The speed at which the action potential propagates along a nerve or muscle fiber",
                R.drawable.ic_rocket
            ),
            GlossaryTerm(
                16,
                "Digital Quantile Point (DQP)",
                "Digital measurement point in signal analysis",
                R.drawable.ic_storage
            ),
            GlossaryTerm(
                17,
                "Latency/Stimulation",
                "Time delay between stimulus and response",
                R.drawable.ic_time
            ),
            GlossaryTerm(
                18,
                "Voltage Amplitude",
                "The magnitude of the electrical potential",
                R.drawable.ic_lightning_yellow
            ),
            GlossaryTerm(
                19,
                "Waveform",
                "The shape and form of the electrical signal",
                R.drawable.ic_signal_wave
            )
        )
    }
}
