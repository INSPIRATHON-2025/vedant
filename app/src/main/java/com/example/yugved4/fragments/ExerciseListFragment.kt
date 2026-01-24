package com.example.yugved4.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yugved4.adapters.ExerciseAdapter
import com.example.yugved4.databinding.FragmentExerciseListBinding
import com.example.yugved4.models.Exercise

/**
 * Exercise List Fragment displaying exercises for a specific category
 */
class ExerciseListFragment : Fragment() {

    private var _binding: FragmentExerciseListBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var exerciseAdapter: ExerciseAdapter
    private val exercises = mutableListOf<Exercise>()

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
        
        // Get category name from arguments
        val categoryName = arguments?.getString("categoryName") ?: "Unknown"
        binding.tvCategoryHeader.text = "$categoryName Exercises"
        
        loadExercises(categoryName)
        setupRecyclerView()
    }

    /**
     * Load exercises based on category (dummy data for now)
     */
    private fun loadExercises(categoryName: String) {
        exercises.clear()
        
        // Add dummy exercises based on category
        when (categoryName) {
            "Chest" -> {
                exercises.add(
                    Exercise(
                        exerciseId = "1",
                        name = "Push-ups",
                        targetMuscles = listOf("Chest", "Triceps", "Shoulders"),
                        difficultyLevel = "Beginner",
                        videoUrl = "https://example.com/pushups",
                        description = "Classic push-up exercise",
                        category = "Chest",
                        duration = "3 sets x 12 reps",
                        caloriesBurned = 50
                    )
                )
                exercises.add(
                    Exercise(
                        exerciseId = "2",
                        name = "Bench Press",
                        targetMuscles = listOf("Chest", "Triceps"),
                        difficultyLevel = "Intermediate",
                        videoUrl = "https://example.com/benchpress",
                        description = "Barbell bench press",
                        category = "Chest",
                        duration = "4 sets x 10 reps",
                        caloriesBurned = 80
                    )
                )
                exercises.add(
                    Exercise(
                        exerciseId = "3",
                        name = "Dumbbell Flyes",
                        targetMuscles = listOf("Chest"),
                        difficultyLevel = "Intermediate",
                        videoUrl = "https://example.com/flyes",
                        description = "Dumbbell chest flyes",
                        category = "Chest",
                        duration = "3 sets x 12 reps",
                        caloriesBurned = 60
                    )
                )
            }
            "Back" -> {
                exercises.add(
                    Exercise(
                        exerciseId = "4",
                        name = "Pull-ups",
                        targetMuscles = listOf("Back", "Biceps"),
                        difficultyLevel = "Intermediate",
                        videoUrl = "https://example.com/pullups",
                        description = "Classic pull-up exercise",
                        category = "Back",
                        duration = "3 sets x 8 reps",
                        caloriesBurned = 70
                    )
                )
                exercises.add(
                    Exercise(
                        exerciseId = "5",
                        name = "Bent-over Rows",
                        targetMuscles = listOf("Back", "Biceps"),
                        difficultyLevel = "Intermediate",
                        videoUrl = "https://example.com/rows",
                        description = "Barbell bent-over rows",
                        category = "Back",
                        duration = "4 sets x 10 reps",
                        caloriesBurned = 75
                    )
                )
            }
            else -> {
                // Generic exercises for other categories
                exercises.add(
                    Exercise(
                        exerciseId = "100",
                        name = "$categoryName Exercise 1",
                        targetMuscles = listOf(categoryName),
                        difficultyLevel = "Beginner",
                        videoUrl = "https://example.com/exercise",
                        description = "Sample exercise for $categoryName",
                        category = categoryName,
                        duration = "3 sets x 12 reps",
                        caloriesBurned = 50
                    )
                )
            }
        }
        
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
        exerciseAdapter = ExerciseAdapter(exercises) { exercise ->
            // Handle exercise click (will implement details later)
            Toast.makeText(
                requireContext(),
                "Clicked: ${exercise.name}",
                Toast.LENGTH_SHORT
            ).show()
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
