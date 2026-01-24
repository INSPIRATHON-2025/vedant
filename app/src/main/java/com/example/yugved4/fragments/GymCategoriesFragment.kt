package com.example.yugved4.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.yugved4.R
import com.example.yugved4.adapters.MuscleGroupAdapter
import com.example.yugved4.database.DatabaseHelper
import com.example.yugved4.databinding.FragmentGymCategoriesBinding
import com.example.yugved4.models.MuscleGroup

/**
 * Fragment displaying muscle group categories in a 2-column grid
 */
class GymCategoriesFragment : Fragment() {

    private var _binding: FragmentGymCategoriesBinding? = null
    private val binding get() = _binding!!

    private lateinit var muscleGroupAdapter: MuscleGroupAdapter
    private lateinit var dbHelper: DatabaseHelper
    private val muscleGroups = mutableListOf<MuscleGroup>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGymCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbHelper = DatabaseHelper(requireContext())
        loadMuscleGroups()
        setupRecyclerView()
    }

    /**
     * Load muscle groups from database
     */
    private fun loadMuscleGroups() {
        muscleGroups.clear()
        muscleGroups.addAll(dbHelper.getAllMuscleGroups())
    }

    /**
     * Setup RecyclerView with GridLayoutManager for 2-column layout
     */
    private fun setupRecyclerView() {
        muscleGroupAdapter = MuscleGroupAdapter(muscleGroups) { muscleGroup ->
            // Navigate to ExerciseListFragment with muscleId
            val bundle = Bundle().apply {
                putInt("muscleId", muscleGroup.id)
                putString("muscleName", muscleGroup.name)
            }
            findNavController().navigate(
                R.id.action_gymCategoriesFragment_to_exerciseListFragment,
                bundle
            )
        }

        binding.rvMuscleGroups.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = muscleGroupAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
