package com.example.yugved4.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.yugved4.R
import com.example.yugved4.database.DatabaseHelper
import com.google.android.material.chip.Chip

/**
 * Fragment displaying detailed information about a specific yoga asana
 * Updated to use SQL database instead of YogaDataProvider
 */
class AsanaDetailFragment : Fragment() {

    private lateinit var tvSanskritName: TextView
    private lateinit var tvEnglishName: TextView
    private lateinit var tvDescription: TextView
    private lateinit var tvBenefits: TextView
    private lateinit var chipDuration: Chip
    private lateinit var chipDifficulty: Chip
    private lateinit var chipCategory: Chip
    private lateinit var dbHelper: DatabaseHelper
    
    private var asanaId: Int = 0
    private var asana: DatabaseHelper.YogaAsana? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_asana_detail, container, false)
        
        // Get asana ID from arguments (now expecting integer)
        asanaId = arguments?.getInt("asanaId") ?: 0
        
        // Initialize views
        tvSanskritName = view.findViewById(R.id.tvSanskritName)
        tvEnglishName = view.findViewById(R.id.tvEnglishName)
        tvDescription = view.findViewById(R.id.tvDescription)
        tvBenefits = view.findViewById(R.id.tvBenefits)
        chipDuration = view.findViewById(R.id.chipDuration)
        chipDifficulty = view.findViewById(R.id.chipDifficulty)
        chipCategory = view.findViewById(R.id.chipCategory)
        
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        dbHelper = DatabaseHelper(requireContext())
        
        loadAsanaData()
        displayAsanaDetails()
    }

    /**
     * Load asana data from database by ID
     */
    private fun loadAsanaData() {
        // Fetch from database using SQL query
        asana = dbHelper.getAsanaById(asanaId)
    }

    /**
     * Display asana details in UI
     */
    private fun displayAsanaDetails() {
        asana?.let { yogaAsana ->
            // Display names
            tvSanskritName.text = yogaAsana.sanskritName
            tvEnglishName.text = yogaAsana.name
            
            // Display description
            tvDescription.text = yogaAsana.description
            
            // Display metadata
            chipDuration.text = yogaAsana.duration
            chipDifficulty.text = yogaAsana.difficultyLevel
            chipCategory.text = yogaAsana.category
            
            // Display benefits as bulleted list
            val benefitsText = yogaAsana.benefits.joinToString("\n") { "• $it" }
            tvBenefits.text = benefitsText
        }
    }
}
