package com.simats.learning

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ClinicalCasesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clinical_cases)

        // Setup toolbar
        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            finish()
        }

        // Setup RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.rvClinicalCases)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ClinicalCasesAdapter(getClinicalCases()) { clinicalCase ->
            // Navigate to case detail page
            val intent = Intent(this, CaseDetailActivity::class.java)
            intent.putExtra("CASE_TITLE", clinicalCase.title)
            intent.putExtra("CASE_NUMBER", clinicalCase.caseNumber)
            intent.putExtra("CASE_TYPE", clinicalCase.type)
            intent.putExtra("CASE_SPECIALTY", clinicalCase.specialty)
            intent.putExtra("CASE_DIFFICULTY", clinicalCase.difficulty)
            intent.putExtra("CASE_PATIENT", clinicalCase.patientProfile)
            intent.putExtra("CASE_CHALLENGE", clinicalCase.challenge)
            intent.putExtra("CASE_WHY", clinicalCase.whyRecorded)
            intent.putExtra("CASE_OUTCOME", clinicalCase.outcome)
            intent.putExtra("CASE_LEARNING", clinicalCase.keyLearning)
            startActivity(intent)
        }
    }

    private fun getClinicalCases(): List<ClinicalCase> {
        return listOf(
            ClinicalCase(
                type = "Bipolar",
                title = "Alzheimer's Disease: DBS Mapping",
                caseNumber = "Case #012-2024",
                specialty = "Neurology",
                difficulty = "Advanced",
                patientProfile = "76-year-old male with moderate AD, MMSE 18/30, referred for DBS mapping to assess fornix stimulation candidacy.",
                challenge = "Map fornix region for potential DBS without triggering seizures. Bilateral recording needed to identify optimal target location.",
                whyRecorded = "• Wide inter-electrode spacing (10-15 mm)\n• High spatial resolution in hippocampal regions\n• Large differential reduced motion artifacts\n• Needed to avoid 2-8 Hz delta contamination",
                outcome = "Successfully mapped bilateral fornix with 82% accuracy. Patient enrolled in Phase II DBS trial. No adverse events in 6-month post-op follow-up.",
                keyLearning = "Bipolar DBS Mapping optimal because it provides superior spatial mapping in deep brain structures while minimizing motion and delta frequency artifacts during awake procedures.",
                iconRes = R.drawable.ic_brain
            ),
            ClinicalCase(
                type = "Monopolar",
                title = "Acute Myocardial Infarction",
                caseNumber = "Case #201-2024",
                specialty = "Cardiology - Emergency",
                difficulty = "Critical",
                patientProfile = "53-year-old male presenting with chest pain (8/10), dyspnea, diaphoresis, ST-elevation in V1-V4. Suspected anterior wall MI.",
                challenge = "Detect anterior wall ischemia in 2-5 mV range. Standard differential amplification missing cardiac signal during initial ECG.",
                whyRecorded = "• Reference potential measured against Wilson's Central Terminal\n• Detects absolute voltage changes in 2-5 mV range\n• No signal cancellation from differential amplification\n• Critical for detecting subtle ST-segment elevation",
                outcome = "Monopolar limb leads confirmed ST elevation (3.8 mm V2, 4.2 mm V3). Emergency coronary angiography: 95% stenosis LAD. PCI done within 90 minutes.",
                keyLearning = "Monopolar recording critical for detecting true ST elevation where amplifier saturation or bipolar cancellation may mask life-threatening MI in low-amplitude cardiac events.",
                iconRes = R.drawable.ic_heart
            ),
            ClinicalCase(
                type = "Bipolar",
                title = "Carpal Tunnel Syndrome",
                caseNumber = "Case #089-2024",
                specialty = "Hand & Peripheral Nerve",
                difficulty = "Intermediate",
                patientProfile = "41-year-old software engineer with 18-month history of bilateral hand paresthesia, nocturnal symptoms, positive Phalen's sign @ 30 sec.",
                challenge = "Map carpal tunnel with spatial accuracy to guide surgical release. Needed isolation of median nerve sensory/motor components.",
                whyRecorded = "• High spatial resolution needed over 3 cm median nerve length\n• Isolated muscle/nerve signals from adjacent forearm muscles\n• Minimized electrical interference from office computer equipment\n• Only requires 1.8-3.0 mV amplitude sensitivity",
                outcome = "Median motor latency 5.8 ms (R), 6.1 ms (L), sensory velocity 38 m/s. Surgical release scheduled. Full recovery achieved at 12-week follow-up.",
                keyLearning = "Bipolar configuration essential for peripheral nerve studies requiring high spatial resolution and artifact rejection in electrically noisy environments.",
                iconRes = R.drawable.ic_muscle
            ),
            ClinicalCase(
                type = "Monopolar",
                title = "Epileptic Seizure Localization",
                caseNumber = "Case #176-2024",
                specialty = "Neurology - Epilepsy",
                difficulty = "Advanced",
                patientProfile = "28-year-old female with medically refractory temporal lobe epilepsy. 3-4 complex partial seizures per month despite medication.",
                challenge = "Precisely localize seizure focus for surgical resection. Required long-term video-EEG monitoring with accurate voltage mapping.",
                whyRecorded = "• Absolute voltage measurement critical for seizure focus localization\n• Wilson's Central Terminal provides stable reference\n• Detects low-amplitude ictal patterns (20-50 µV)\n• Needed for surgical planning and outcome prediction",
                outcome = "Left mesial temporal seizure focus identified. Selective amygdalohippocampectomy performed. Patient seizure-free for 18 months post-op.",
                keyLearning = "Monopolar EEG recording essential for surgical epilepsy evaluation, providing absolute voltage measurements necessary for precise seizure focus localization.",
                iconRes = R.drawable.ic_brain
            ),
            ClinicalCase(
                type = "Bipolar",
                title = "Muscle Fatigue in ALS",
                caseNumber = "Case #134-2024",
                specialty = "Neurology - Motor Neuron",
                difficulty = "Advanced",
                patientProfile = "62-year-old male with suspected ALS. Progressive weakness in right hand over 8 months, fasciculations, difficulty with fine motor tasks.",
                challenge = "Differentiate motor neuron disease from peripheral neuropathy. Required detection of fasciculation potentials and motor unit action potentials.",
                whyRecorded = "• Superior noise rejection for detecting small amplitude fasciculations\n• High spatial resolution to isolate individual motor units\n• Differential recording eliminates EMG cross-talk\n• Needed to measure motor unit recruitment patterns",
                outcome = "Widespread fasciculations and fibrillation potentials detected in multiple muscle groups. Diagnosis of ALS confirmed. Patient started on riluzole therapy.",
                keyLearning = "Bipolar EMG critical for ALS diagnosis, providing high spatial resolution needed to detect fasciculations and abnormal motor unit recruitment patterns.",
                iconRes = R.drawable.ic_muscle
            )
        )
    }
}

data class ClinicalCase(
    val type: String,
    val title: String,
    val caseNumber: String,
    val specialty: String,
    val difficulty: String,
    val patientProfile: String,
    val challenge: String,
    val whyRecorded: String,
    val outcome: String,
    val keyLearning: String,
    val iconRes: Int
)

class ClinicalCasesAdapter(
    private val cases: List<ClinicalCase>,
    private val onCaseClick: (ClinicalCase) -> Unit
) : RecyclerView.Adapter<ClinicalCasesAdapter.CaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CaseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_clinical_case, parent, false)
        return CaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CaseViewHolder, position: Int) {
        holder.bind(cases[position], onCaseClick)
    }

    override fun getItemCount() = cases.size

    class CaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val caseContainer: LinearLayout = itemView.findViewById(R.id.caseContainer)
        private val ivIcon: ImageView = itemView.findViewById(R.id.ivCaseIcon)
        private val tvType: TextView = itemView.findViewById(R.id.tvType)
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val tvCaseNumber: TextView = itemView.findViewById(R.id.tvCaseNumber)
        private val tvSpecialty: TextView = itemView.findViewById(R.id.tvSpecialty)
        private val tvDifficulty: TextView = itemView.findViewById(R.id.tvDifficulty)
        private val tvPatientPreview: TextView = itemView.findViewById(R.id.tvPatientPreview)

        fun bind(case: ClinicalCase, onClick: (ClinicalCase) -> Unit) {
            ivIcon.setImageResource(case.iconRes)
            tvType.text = case.type
            tvTitle.text = case.title
            tvCaseNumber.text = case.caseNumber
            tvSpecialty.text = case.specialty
            tvDifficulty.text = case.difficulty
            tvPatientPreview.text = case.patientProfile

            // Set type badge color
            when (case.type) {
                "Bipolar" -> tvType.setBackgroundResource(R.drawable.badge_bipolar)
                "Monopolar" -> tvType.setBackgroundResource(R.drawable.badge_monopolar)
            }

            // Set difficulty color
            val difficultyColor = when (case.difficulty) {
                "Critical" -> "#E53935"
                "Advanced" -> "#FB8C00"
                "Intermediate" -> "#1976D2"
                else -> "#388E3C"
            }
            tvDifficulty.setTextColor(android.graphics.Color.parseColor(difficultyColor))

            caseContainer.setOnClickListener {
                onClick(case)
            }
        }
    }
}
