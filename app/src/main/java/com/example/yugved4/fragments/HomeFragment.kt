package com.example.yugved4.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yugved4.R
import com.example.yugved4.adapters.WorkoutAdapter
import com.example.yugved4.databinding.FragmentHomeBinding
import com.example.yugved4.models.Workout

/**
 * Home Dashboard Fragment
 * Displays user's fitness overview, workouts, quick actions, and mindfulness section
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var workoutAdapter: WorkoutAdapter
    private val workouts = mutableListOf<Workout>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupWorkouts()
        setupQuickActions()
        setupMindfulness()
    }

    /**
     * Setup workouts RecyclerView with dummy data
     */
    private fun setupWorkouts() {
        // Create dummy workout data
        workouts.apply {
            add(
                Workout(
                    id = 1,
                    title = "Morning Yoga",
                    duration = "20 min",
                    imageResId = R.drawable.ic_activity, // Using placeholder icon
                    category = "Yoga"
                )
            )
            add(
                Workout(
                    id = 2,
                    title = "HIIT Cardio",
                    duration = "15 min",
                    imageResId = R.drawable.ic_activity, // Using placeholder icon
                    category = "Cardio"
                )
            )
        }

        // Setup RecyclerView
        workoutAdapter = WorkoutAdapter(workouts)
        binding.rvWorkouts.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = workoutAdapter
        }
    }

    /**
     * Setup quick action chip click listeners
     */
    private fun setupQuickActions() {
        binding.chipWater.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Log Water clicked",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.chipMeals.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Log Meals clicked",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.chipActivity.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Log Activity clicked",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    /**
     * Setup mindfulness card button listener
     */
    private fun setupMindfulness() {
        binding.btnStartMindfulness.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Start Mindfulness clicked",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
