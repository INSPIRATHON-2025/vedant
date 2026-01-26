package com.example.yugved4.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.yugved4.R
import com.example.yugved4.models.UserDietData
import com.example.yugved4.utils.DietPreferencesManager
import com.example.yugved4.database.DatabaseHelper
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputEditText

/**
 * Diet Fragment with multi-step questionnaire using ViewPager2
 */
class DietFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tvStepIndicator: TextView
    private lateinit var btnPrevious: Button
    private lateinit var btnNext: Button
    
    private lateinit var prefsManager: DietPreferencesManager
    private lateinit var dbHelper: DatabaseHelper
    
    // Step 1 views - Activity Level
    private var actvActivityLevel: AutoCompleteTextView? = null
    private var selectedActivityLevel: String = ""
    
    // Step 2 views - Fitness Goal
    private var cardWeightLoss: MaterialCardView? = null
    private var cardMaintainWeight: MaterialCardView? = null
    private var cardWeightGain: MaterialCardView? = null
    private var selectedFitnessGoal: String = ""
    
    // Step 3 views - Diet Preference
    private var cardVegetarian: MaterialCardView? = null
    private var cardNonVeg: MaterialCardView? = null
    private var selectedDietPreference: String = ""
    
    private var currentStep = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_diet_survey, container, false)
        
        prefsManager = DietPreferencesManager(requireContext())
        dbHelper = DatabaseHelper(requireContext())
        
        viewPager = view.findViewById(R.id.viewPagerSteps)
        tvStepIndicator = view.findViewById(R.id.tvStepIndicator)
        btnPrevious = view.findViewById(R.id.btnPrevious)
        btnNext = view.findViewById(R.id.btnNext)
        
        setupViewPager()
        setupButtons()
        
        return view
    }

    private fun setupViewPager() {
        // Create the 4 step views
        val stepViews = listOf(
            createActivityLevelView(),
            createFitnessGoalView(),
            createDietPreferenceView()
        )
        
        val adapter = object : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
                return object : androidx.recyclerview.widget.RecyclerView.ViewHolder(stepViews[viewType]) {}
            }
            
            override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {}
            
            override fun getItemCount(): Int = 3
            
            override fun getItemViewType(position: Int): Int = position
        }
        
        viewPager.adapter = adapter
        viewPager.isUserInputEnabled = false // Disable swipe navigation
        
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                currentStep = position
                updateUI()
            }
        })
    }

    private fun createActivityLevelView(): View {
        val view = layoutInflater.inflate(R.layout.step_activity_level, viewPager, false)
        
        actvActivityLevel = view.findViewById(R.id.actvActivityLevel)
        
        val activityLevels = arrayOf(
            getString(R.string.sedentary),
            getString(R.string.lightly_active),
            getString(R.string.moderately_active),
            getString(R.string.very_active),
            getString(R.string.extra_active)
        )
        
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, activityLevels)
        actvActivityLevel?.setAdapter(adapter)
        
        actvActivityLevel?.setOnItemClickListener { _, _, position, _ ->
            selectedActivityLevel = activityLevels[position]
        }
        
        return view
    }

    private fun createFitnessGoalView(): View {
        val view = layoutInflater.inflate(R.layout.step_fitness_goal, viewPager, false)
        
        cardWeightLoss = view.findViewById(R.id.cardWeightLoss)
        cardMaintainWeight = view.findViewById(R.id.cardMaintainWeight)
        cardWeightGain = view.findViewById(R.id.cardWeightGain)
        
        cardWeightLoss?.setOnClickListener {
            selectedFitnessGoal = "Weight Loss"
            updateFitnessGoalCards()
        }
        
        cardMaintainWeight?.setOnClickListener {
            selectedFitnessGoal = "Maintain Weight"
            updateFitnessGoalCards()
        }
        
        cardWeightGain?.setOnClickListener {
            selectedFitnessGoal = "Weight Gain"
            updateFitnessGoalCards()
        }
        
        return view
    }

    private fun createDietPreferenceView(): View {
        val view = layoutInflater.inflate(R.layout.step_diet_preference, viewPager, false)
        
        cardVegetarian = view.findViewById(R.id.cardVegetarian)
        cardNonVeg = view.findViewById(R.id.cardNonVeg)
        
        cardVegetarian?.setOnClickListener {
            selectedDietPreference = "Vegetarian"
            updateDietPreferenceCards()
        }
        
        cardNonVeg?.setOnClickListener {
            selectedDietPreference = "Non-Veg"
            updateDietPreferenceCards()
        }
        
        return view
    }

    private fun updateDietPreferenceCards() {
        val emeraldGreen = resources.getColor(R.color.emerald_green, null)
        val lightGray = resources.getColor(R.color.background_light, null)
        
        cardVegetarian?.strokeColor = if (selectedDietPreference == "Vegetarian") emeraldGreen else lightGray
        cardNonVeg?.strokeColor = if (selectedDietPreference == "Non-Veg") emeraldGreen else lightGray
    }
    
    private fun updateFitnessGoalCards() {
        val emeraldGreen = resources.getColor(R.color.emerald_green, null)
        val lightGray = resources.getColor(R.color.background_light, null)
        
        cardWeightLoss?.strokeColor = if (selectedFitnessGoal == "Weight Loss") emeraldGreen else lightGray
        cardMaintainWeight?.strokeColor = if (selectedFitnessGoal == "Maintain Weight") emeraldGreen else lightGray
        cardWeightGain?.strokeColor = if (selectedFitnessGoal == "Weight Gain") emeraldGreen else lightGray
    }

    private fun setupButtons() {
        btnPrevious.setOnClickListener {
            if (currentStep > 0) {
                viewPager.currentItem = currentStep - 1
            }
        }
        
        btnNext.setOnClickListener {
            if (validateCurrentStep()) {
                if (currentStep < 2) {
                    viewPager.currentItem = currentStep + 1
                } else {
                    // Calculate and navigate to result
                    calculateAndNavigate()
                }
            }
        }
        
        updateUI()
    }

    private fun updateUI() {
        // Update step indicator
        tvStepIndicator.text = when (currentStep) {
            0 -> "Step 1 of 3"
            1 -> "Step 2 of 3"
            2 -> "Step 3 of 3"
            else -> ""
        }
        
        // Update button visibility and text
        btnPrevious.visibility = if (currentStep > 0) View.VISIBLE else View.GONE
        btnNext.text = if (currentStep == 2) getString(R.string.calculate) else getString(R.string.next)
    }

    private fun validateCurrentStep(): Boolean {
        return when (currentStep) {
            0 -> {
                if (selectedActivityLevel.isEmpty()) {
                    Toast.makeText(requireContext(), "Please select your activity level", Toast.LENGTH_SHORT).show()
                    false
                } else true
            }
            1 -> {
                if (selectedFitnessGoal.isEmpty()) {
                    Toast.makeText(requireContext(), "Please select your fitness goal", Toast.LENGTH_SHORT).show()
                    false
                } else true
            }
            2 -> {
                if (selectedDietPreference.isEmpty()) {
                    Toast.makeText(requireContext(), "Please select your dietary preference", Toast.LENGTH_SHORT).show()
                    false
                } else true
            }
            else -> true
        }
    }

    private fun calculateAndNavigate() {
        // Fetch user profile from database
        val userProfile = dbHelper.getUserProfile()
        
        if (userProfile == null) {
            Toast.makeText(requireContext(), "User profile not found. Please complete profile in Home.", Toast.LENGTH_LONG).show()
            return
        }
        
        val age = userProfile.age ?: 30
        val gender = userProfile.gender ?: "Male"
        val height = userProfile.height?.toInt() ?: 170
        val weight = userProfile.currentWeight.toFloat()
        
        val calories = calculateCalories(age, gender, height, weight, selectedActivityLevel)
        
        val dietData = UserDietData(
            age = age,
            gender = gender,
            height = height,
            weight = weight,
            activityLevel = selectedActivityLevel,
            dietPreference = selectedDietPreference,
            calculatedCalories = calories
        )
        
        // Save to SharedPreferences
        prefsManager.saveDietData(dietData)
        
        // Navigate to result fragment with calories and diet type
        val bundle = Bundle().apply {
            putInt("calories", calories)
            putString("dietType", if (selectedDietPreference == "Vegetarian") "Veg" else "Non-Veg")
        }
        findNavController().navigate(R.id.action_dietFragment_to_dietResultFragment, bundle)
    }

    /**
     * Calculate daily calorie requirements using Mifflin-St Jeor formula
     * Adjusts based on fitness goal
     */
    private fun calculateCalories(age: Int, gender: String, height: Int, weight: Float, activityLevel: String): Int {
        // Calculate BMR (Basal Metabolic Rate)
        val bmr = if (gender == "Male") {
            (10 * weight) + (6.25 * height) - (5 * age) + 5
        } else {
            (10 * weight) + (6.25 * height) - (5 * age) - 161
        }
        
        // Activity level multipliers
        val multiplier = when {
            activityLevel.contains("Sedentary") -> 1.2
            activityLevel.contains("Lightly") -> 1.375
            activityLevel.contains("Moderately") -> 1.55
            activityLevel.contains("Very") -> 1.725
            activityLevel.contains("Extra") -> 1.9
            else -> 1.2
        }
        
        // Calculate maintenance calories (TDEE)
        val maintenanceCalories = (bmr * multiplier).toInt()
        
        // Adjust based on fitness goal
        return when (selectedFitnessGoal) {
            "Weight Loss" -> maintenanceCalories - 500  // Calorie deficit for weight loss
            "Weight Gain" -> maintenanceCalories + 500  // Calorie surplus for weight gain
            "Maintain Weight" -> maintenanceCalories     // No adjustment for maintenance
            else -> maintenanceCalories
        }
    }
}
