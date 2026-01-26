package com.example.yugved4.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.yugved4.R
import com.example.yugved4.database.DatabaseHelper
import com.example.yugved4.models.UserDietPlan
import com.example.yugved4.utils.AuthHelper
import com.example.yugved4.utils.FirestoreHelper
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Diet Result Fragment - Displays meal plan from database based on calorie requirements
 * Updated to use SQL database with getMealPlan() query
 */
class DietResultFragment : Fragment() {

    private lateinit var tvCaloriesValue: TextView
    private lateinit var tvCalorieRange: TextView
    private lateinit var tvDietTypeValue: TextView
    private lateinit var tvBreakfast: TextView
    private lateinit var tvLunch: TextView
    private lateinit var tvDinner: TextView
    private lateinit var tvSnacks: TextView

    private lateinit var btnRecalculate: MaterialButton
    private lateinit var btnSavePlan: MaterialButton
    
    private lateinit var dbHelper: DatabaseHelper
    private var targetCalories: Int = 0
    private var dietType: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_diet_result, container, false)
        
        dbHelper = DatabaseHelper(requireContext())
        
        // Get arguments from navigation
        targetCalories = arguments?.getInt("calories") ?: 2000
        dietType = arguments?.getString("dietType") ?: "Veg"
        
        // Initialize views
        tvCaloriesValue = view.findViewById(R.id.tvCaloriesValue)
        tvCalorieRange = view.findViewById(R.id.tvCalorieRange)
        tvDietTypeValue = view.findViewById(R.id.tvDietTypeValue)
        tvBreakfast = view.findViewById(R.id.tvBreakfast)
        tvLunch = view.findViewById(R.id.tvLunch)
        tvDinner = view.findViewById(R.id.tvDinner)
        tvSnacks = view.findViewById(R.id.tvSnacks)

        btnRecalculate = view.findViewById(R.id.btnRecalculate)
        btnSavePlan = view.findViewById(R.id.btnSavePlan)
        
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        loadAndDisplayMealPlan()
        setupButtons()
    }

    /**
     * Fetch meal plan from database and display it
     */
    private fun loadAndDisplayMealPlan() {
        // Query database for meal plan
        val mealPlan = dbHelper.getMealPlan(targetCalories, dietType)
        
        if (mealPlan != null) {
            // Display calorie information
            tvCaloriesValue.text = "$targetCalories kcal"
            tvCalorieRange.text = "Plan Range: ${mealPlan.minCalories}-${mealPlan.maxCalories} kcal"
            tvDietTypeValue.text = "Diet Type: ${mealPlan.dietType}"
            
            // Display meal plan details
            tvBreakfast.text = mealPlan.breakfast
            tvLunch.text = mealPlan.lunch
            tvDinner.text = mealPlan.dinner
            tvSnacks.text = mealPlan.snacks
            
        } else {
            // Fallback if no plan found (should not happen due to fallback logic in getMealPlan)
            tvCaloriesValue.text = "$targetCalories kcal"
            tvCalorieRange.text = "No plan available"
            tvDietTypeValue.text = "Diet Type: $dietType"
            
            tvBreakfast.text = "Unable to load meal plan"
            tvLunch.text = "Please try recalculating"
            tvDinner.text = "Or contact support"
            tvSnacks.text = "-"
            
            Toast.makeText(
                requireContext(),
                "No meal plan found for these parameters",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    /**
     * Setup button click listeners
     */
    private fun setupButtons() {
        btnRecalculate.setOnClickListener {
            // Navigate back to diet survey
            findNavController().popBackStack()
        }
        
        btnSavePlan.setOnClickListener {
            saveDietPlanToCloud()
        }
    }
    
    private fun saveDietPlanToCloud() {
        val currentUser = AuthHelper.getCurrentUser()
        if (currentUser == null) {
            Toast.makeText(requireContext(), "Please login to save your plan", Toast.LENGTH_SHORT).show()
            return
        }
        
        val breakfast = tvBreakfast.text.toString()
        val lunch = tvLunch.text.toString()
        val dinner = tvDinner.text.toString()
        val snacks = tvSnacks.text.toString()
        
        if (breakfast == "Loading..." || breakfast.isEmpty()) {
            Toast.makeText(requireContext(), "Wait for plan to load completely", Toast.LENGTH_SHORT).show()
            return
        }
        
        val dietPlan = UserDietPlan(
            userId = currentUser.uid,
            targetCalories = targetCalories,
            dietType = dietType,
            breakfast = breakfast,
            lunch = lunch,
            dinner = dinner,
            snacks = snacks
        )
        
        // Show loading state
        btnSavePlan.isEnabled = false
        btnSavePlan.text = "Saving..."
        
        CoroutineScope(Dispatchers.IO).launch {
            try {
                FirestoreHelper.saveDietPlan(currentUser.uid, dietPlan)
                
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Diet plan saved to cloud!", Toast.LENGTH_SHORT).show()
                    btnSavePlan.text = "Saved ✓"
                    btnSavePlan.isEnabled = true
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Failed to save: ${e.message}", Toast.LENGTH_SHORT).show()
                    btnSavePlan.text = "Save Diet Plan"
                    btnSavePlan.isEnabled = true
                }
            }
        }
    }
}
