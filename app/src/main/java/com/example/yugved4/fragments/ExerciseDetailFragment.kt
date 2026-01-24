package com.example.yugved4.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.yugved4.database.DatabaseHelper
import com.example.yugved4.databinding.FragmentExerciseDetailBinding

/**
 * Exercise Detail Fragment showing video placeholder and exercise instructions
 * Uses SQL database to fetch exercise details by exerciseId
 */
class ExerciseDetailFragment : Fragment() {

    private var _binding: FragmentExerciseDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExerciseDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbHelper = DatabaseHelper(requireContext())

        // Get exercise ID from arguments
        val exerciseId = arguments?.getInt("exerciseId") ?: 1

        loadExerciseDetail(exerciseId)
    }

    /**
     * Load exercise detail from database using RAW SQL query
     * SELECT * FROM gym_exercises WHERE exercise_id = ?
     */
    private fun loadExerciseDetail(exerciseId: Int) {
        val exercise = dbHelper.getExerciseDetail(exerciseId)

        exercise?.let {
            // Display exercise title
            binding.tvExerciseTitle.text = it.name

            // Display exercise description
            binding.tvExerciseDescription.text = it.description

            // TODO: Replace with VideoView/ExoPlayer when video files are provided
            // The video placeholder is already set in the XML layout
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
