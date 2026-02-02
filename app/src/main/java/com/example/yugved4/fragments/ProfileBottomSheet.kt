package com.example.yugved4.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.yugved4.LoginActivity
import com.example.yugved4.R
import com.example.yugved4.database.DatabaseHelper
import com.example.yugved4.databinding.BottomSheetProfileBinding
import com.example.yugved4.utils.AuthHelper
import com.example.yugved4.utils.ThemeHelper
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.pow

/**
 * Profile Bottom Sheet
 * Displays user information: name, age, BMI, profile photo, and theme toggle
 */
class ProfileBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetProfileBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        dbHelper = DatabaseHelper(requireContext())
        auth = FirebaseAuth.getInstance()
        
        loadUserProfile()
        setupThemeToggle()
        setupLogout()
    }
    
    /**
     * Load user profile data from database and Firebase
     */
    private fun loadUserProfile() {
        val profile = dbHelper.getUserProfile()
        
        if (profile != null) {
            // Set user name
            binding.tvUserName.text = profile.name ?: "User"
            
            // Set age
            val ageText = if (profile.age != null) {
                "${profile.age} years"
            } else {
                "Age not set"
            }
            binding.tvAge.text = ageText
            
            // Calculate and display BMI
            displayBMI(profile.height, profile.currentWeight)
            
            // Load profile photo from Firebase
            loadProfilePhoto()
        } else {
            Toast.makeText(requireContext(), "Profile not found", Toast.LENGTH_SHORT).show()
        }
    }
    
    /**
     * Calculate and display BMI with category
     */
    private fun displayBMI(height: Double?, weight: Double) {
        if (height != null && height > 0) {
            // Convert height from cm to meters
            val heightInMeters = height / 100.0
            
            // Calculate BMI: weight (kg) / height (m)²
            val bmi = weight / heightInMeters.pow(2)
            
            // Display BMI with 1 decimal place
            binding.tvBMI.text = String.format("%.1f", bmi)
            
            // Determine BMI category and color
            val (category, colorRes) = when {
                bmi < 18.5 -> Pair("Underweight", R.color.orange)
                bmi < 25.0 -> Pair("Normal Weight", R.color.emerald_green)
                bmi < 30.0 -> Pair("Overweight", R.color.orange)
                else -> Pair("Obese", R.color.red_primary)
            }
            
            binding.tvBMICategory.text = category
            binding.tvBMICategory.setTextColor(resources.getColor(colorRes, null))
            binding.tvBMI.setTextColor(resources.getColor(colorRes, null))
        } else {
            binding.tvBMI.text = "N/A"
            binding.tvBMICategory.text = "Height not set"
        }
    }
    
    /**
     * Load user's Google profile photo using Firebase Auth and Glide
     */
    private fun loadProfilePhoto() {
        val currentUser = auth.currentUser
        val photoUrl = currentUser?.photoUrl
        
        if (photoUrl != null) {
            // Load photo with Glide
            Glide.with(this)
                .load(photoUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_profile)
                .error(R.drawable.ic_profile)
                .circleCrop()
                .into(binding.ivProfilePhoto)
        } else {
            // Use default icon
            binding.ivProfilePhoto.setImageResource(R.drawable.ic_profile)
        }
    }
    
    /**
     * Setup theme toggle switch
     */
    private fun setupThemeToggle() {
        // Set initial switch state
        val isDarkMode = ThemeHelper.isDarkMode(requireContext())
        
        // Remove listener before setting state to avoid triggering it
        binding.switchTheme.setOnCheckedChangeListener(null)
        binding.switchTheme.isChecked = isDarkMode
        
        // Handle theme toggle
        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            val newTheme = if (isChecked) {
                ThemeHelper.THEME_DARK
            } else {
                ThemeHelper.THEME_LIGHT
            }
            
            // Only apply if changed to prevent loops
            if (newTheme != ThemeHelper.getTheme(requireContext())) {
                // Save and apply theme
                ThemeHelper.saveTheme(requireContext(), newTheme)
                ThemeHelper.applyThemeMode(newTheme)
                
                // Show confirmation
                val message = if (isChecked) "Dark mode enabled" else "Light mode enabled"
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    /**
     * Setup logout button click listener
     */
    private fun setupLogout() {
        binding.btnLogout.setOnClickListener {
            showLogoutConfirmation()
        }
    }
    
    /**
     * Show confirmation dialog before logging out
     */
    private fun showLogoutConfirmation() {
        AlertDialog.Builder(requireContext())
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout? You will need to sign in again to access the app.")
            .setPositiveButton("Logout") { _, _ ->
                performLogout()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    /**
     * Perform logout - sign out from Firebase and clear local data
     */
    private fun performLogout() {
        try {
            // Sign out from Firebase
            AuthHelper.signOut()
            
            // Clear local user profile data
            dbHelper.clearUserProfile()
            
            Toast.makeText(
                requireContext(),
                "Logged out successfully",
                Toast.LENGTH_SHORT
            ).show()
            
            // Dismiss bottom sheet
            dismiss()
            
            // Navigate to LoginActivity and clear the back stack
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            
            // Finish current activity
            requireActivity().finish()
        } catch (e: Exception) {
            Toast.makeText(
                requireContext(),
                "Error logging out: ${e.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
