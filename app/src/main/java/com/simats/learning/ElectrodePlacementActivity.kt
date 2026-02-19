package com.simats.learning

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class ElectrodePlacementActivity : AppCompatActivity() {

    private var selectedType = "ECG"

    // Header
    private lateinit var headerLayout: LinearLayout

    // Tab views
    private lateinit var tabECG: LinearLayout
    private lateinit var tabEEG: LinearLayout
    private lateinit var tabEMG: LinearLayout

    // ECG sections
    private lateinit var sectionECG: CardView
    private lateinit var sectionECGTips: LinearLayout

    // EEG sections
    private lateinit var sectionEEG: CardView
    private lateinit var sectionEEGTips: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_electrode_placement)

        // Back button
        findViewById<LinearLayout>(R.id.btnBack).setOnClickListener { finish() }

        // Header
        headerLayout = findViewById(R.id.headerLayout)

        // Tabs
        tabECG = findViewById(R.id.tabECG)
        tabEEG = findViewById(R.id.tabEEG)
        tabEMG = findViewById(R.id.tabEMG)

        // Sections
        sectionECG = findViewById(R.id.sectionECG)
        sectionECGTips = findViewById(R.id.sectionECGTips)
        sectionEEG = findViewById(R.id.sectionEEG)
        sectionEEGTips = findViewById(R.id.sectionEEGTips)

        tabECG.setOnClickListener { selectTab("ECG") }
        tabEEG.setOnClickListener { selectTab("EEG") }
        tabEMG.setOnClickListener { selectTab("EMG") }

        selectTab("ECG")
    }

    private fun selectTab(type: String) {
        selectedType = type

        // Reset all tabs to unselected
        tabECG.setBackgroundResource(R.drawable.tab_unselected_bg)
        tabEEG.setBackgroundResource(R.drawable.tab_unselected_bg)
        tabEMG.setBackgroundResource(R.drawable.tab_unselected_bg)

        // Reset tab label colors
        (tabECG.getChildAt(1) as? android.widget.TextView)?.setTextColor(0xFF888888.toInt())
        (tabEEG.getChildAt(1) as? android.widget.TextView)?.setTextColor(0xFF888888.toInt())
        (tabEMG.getChildAt(1) as? android.widget.TextView)?.setTextColor(0xFF888888.toInt())

        // Hide all sections
        sectionECG.visibility = View.GONE
        sectionECGTips.visibility = View.GONE
        sectionEEG.visibility = View.GONE
        sectionEEGTips.visibility = View.GONE

        when (type) {
            "ECG" -> {
                tabECG.setBackgroundResource(R.drawable.tab_selected_bg)
                (tabECG.getChildAt(1) as? android.widget.TextView)?.setTextColor(0xFFE53935.toInt())
                headerLayout.setBackgroundResource(R.drawable.electrode_header_gradient)
                sectionECG.visibility = View.VISIBLE
                sectionECGTips.visibility = View.VISIBLE
            }
            "EEG" -> {
                tabEEG.setBackgroundResource(R.drawable.tab_selected_bg)
                (tabEEG.getChildAt(1) as? android.widget.TextView)?.setTextColor(0xFF7B2FFE.toInt())
                headerLayout.setBackgroundResource(R.drawable.electrode_header_purple)
                sectionEEG.visibility = View.VISIBLE
                sectionEEGTips.visibility = View.VISIBLE
            }
            "EMG" -> {
                tabEMG.setBackgroundResource(R.drawable.tab_selected_bg)
                (tabEMG.getChildAt(1) as? android.widget.TextView)?.setTextColor(0xFF43A047.toInt())
                headerLayout.setBackgroundResource(R.drawable.emg_header_gradient)
                // EMG section can be added later; for now show ECG as fallback
                sectionECG.visibility = View.VISIBLE
                sectionECGTips.visibility = View.VISIBLE
            }
        }
    }
}
