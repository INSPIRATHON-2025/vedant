package com.example.yugved4.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yugved4.R
import com.example.yugved4.adapters.SimpleExerciseAdapter
import com.example.yugved4.database.DatabaseHelper
import com.example.yugved4.databinding.FragmentExerciseListBinding

/**
 * Exercise List Fragment displaying exercises for a specific muscle group
 * Uses SQL database to fetch exercises based on muscleId
 */
class ExerciseListFragment : Fragment() {

    private var _binding: FragmentExerciseListBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var exerciseAdapter: SimpleExerciseAdapter
    private lateinit var dbHelper: DatabaseHelper
    private val exercises = mutableListOf<DatabaseHelper.GymExercise>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExerciseListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        dbHelper = DatabaseHelper(requireContext())
        
        // Get muscle ID and name from arguments
        val muscleId = arguments?.getInt("muscleId") ?: 1
        val muscleName = arguments?.getString("muscleName") ?: "Unknown"
        
        // Update header
        binding.tvMuscleName.text = muscleName
        
        loadExercises(muscleId)
        setupRecyclerView()
    }

    /**
     * Load exercises from database using RAW SQL query
     */
    private fun loadExercises(muscleId: Int) {
        exercises.clear()
        
        // Fetch exercises from database using SELECT * FROM exercises WHERE muscle_id = ?
        val exercisesFromDb = dbHelper.getExercisesByMuscleId(muscleId)
        exercises.addAll(exercisesFromDb)
        
        // Update exercise count
        binding.tvExerciseCount.text = "${exercises.size} Exercises"
        
        // Show empty state if no exercises
        if (exercises.isEmpty()) {
            binding.tvEmptyState.visibility = View.VISIBLE
            binding.rvExercises.visibility = View.GONE
        } else {
            binding.tvEmptyState.visibility = View.GONE
            binding.rvExercises.visibility = View.VISIBLE
        }
    }

    /**
     * Setup RecyclerView with exercises
     */
    private fun setupRecyclerView() {
        exerciseAdapter = SimpleExerciseAdapter(exercises) { exercise ->
            // Navigate to ExerciseDetailFragment with exerciseId
            val bundle = Bundle().apply {
                putInt("exerciseId", exercise.id)
            }
            findNavController().navigate(
                R.id.action_exerciseListFragment_to_exerciseDetailFragment,
                bundle
            )
        }

        binding.rvExercises.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = exerciseAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
