package com.example.yugved4.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.yugved4.R
import com.google.android.material.card.MaterialCardView

/**
 * Workout Hub Fragment
 * Provides unified entry point for both Gym and Yoga workouts
 */
class WorkoutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_workout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupGymCard(view)
        setupYogaCard(view)
    }
    
    /**
     * Setup Gym card with navigation to gym categories
     */
    private fun setupGymCard(view: View) {
        val cardGym = view.findViewById<MaterialCardView>(R.id.cardGym)
        val btnExploreGym = view.findViewById<View>(R.id.btnExploreGym)
        
        // Both card and button navigate to gym categories
        val gymClickListener = View.OnClickListener {
            navigateToGymCategories()
        }
        
        cardGym.setOnClickListener(gymClickListener)
        btnExploreGym.setOnClickListener(gymClickListener)
    }
    
    /**
     * Setup Yoga card with navigation to yoga asanas
     */
    private fun setupYogaCard(view: View) {
        val cardYoga = view.findViewById<MaterialCardView>(R.id.cardYoga)
        val btnExploreYoga = view.findViewById<View>(R.id.btnExploreYoga)
        
        // Both card and button navigate to yoga asanas
        val yogaClickListener = View.OnClickListener {
            navigateToYogaAsanas()
        }
        
        cardYoga.setOnClickListener(yogaClickListener)
        btnExploreYoga.setOnClickListener(yogaClickListener)
    }
    
    /**
     * Navigate to Gym categories (shows muscle group selection)
     */
    private fun navigateToGymCategories() {
        try {
            // Navigate to GymCategoriesFragment to show muscle group selection
            findNavController().navigate(
                R.id.action_workoutFragment_to_gymCategoriesFragment
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    /**
     * Navigate to Yoga categories (shows difficulty level selection)
     */
    private fun navigateToYogaAsanas() {
        try {
            // Navigate to YogaFragment to show category selection
            findNavController().navigate(
                R.id.action_workoutFragment_to_yogaFragment
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
